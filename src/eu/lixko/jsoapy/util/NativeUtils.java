package eu.lixko.jsoapy.util;

import static java.lang.foreign.ValueLayout.JAVA_LONG;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.PaddingLayout;
import java.lang.foreign.SequenceLayout;
import java.lang.foreign.StructLayout;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NativeUtils {
	
    static final Arena LIBRARY_ARENA = Arena.ofAuto();
    public static final boolean TRACE_DOWNCALLS = Boolean.getBoolean("jextract.trace.downcalls");
    static final MemorySegment NULLPTR = MemorySegment.ofAddress(0); 
    
    public static void loadLibrary() {
    	System.loadLibrary("SoapySDR");
    }

    public static void traceDowncall(String name, Object... args) {
         String traceArgs = Arrays.stream(args)
                       .map(Object::toString)
                       .collect(Collectors.joining(", "));
         System.out.printf("%s(%s)\n", name, traceArgs);
    }
    
    public static Object call(ApiMethod apiEntry) {
        try {
            if (TRACE_DOWNCALLS) {
            	traceDowncall(apiEntry.NAME);            	
            }
        	if (apiEntry.DESC.returnLayout().isEmpty()) {
        		return apiEntry.HANDLE.invokeExact();
        	} else {
        		return apiEntry.HANDLE.invoke();
        	}
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }
    
    /*
     * This method is ~20 times slower than invokeExact.
     */
    public static Object call(ApiMethod apiEntry, Object... args) {
        try {
            if (TRACE_DOWNCALLS) {
            	traceDowncall(apiEntry.NAME);
            }
            
            for (int i = 0; i < args.length; i++) {
            	if (args[i] == null) {
            		args[i] = NULLPTR;
            	}
            }
            return apiEntry.HANDLE.invokeWithArguments(args);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    public static MemorySegment findOrThrow(String symbol) {
        return SYMBOL_LOOKUP.find(symbol)
            .orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol: " + symbol));
    }

    public static MethodHandle upcallHandle(Class<?> fi, String name, FunctionDescriptor fdesc) {
        try {
            return MethodHandles.lookup().findVirtual(fi, name, fdesc.toMethodType());
        } catch (ReflectiveOperationException ex) {
            throw new AssertionError(ex);
        }
    }

    public static MemoryLayout align(MemoryLayout layout, long align) {
        return switch (layout) {
            case PaddingLayout p -> p;
            case ValueLayout v -> v.withByteAlignment(align);
            case GroupLayout g -> {
                MemoryLayout[] alignedMembers = g.memberLayouts().stream()
                        .map(m -> align(m, align)).toArray(MemoryLayout[]::new);
                yield g instanceof StructLayout ?
                        MemoryLayout.structLayout(alignedMembers) : MemoryLayout.unionLayout(alignedMembers);
            }
            case SequenceLayout s -> MemoryLayout.sequenceLayout(s.elementCount(), align(s.elementLayout(), align));
        };
    }

    static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup()
            .or(Linker.nativeLinker().defaultLookup());
    
    public static final MemorySegment allocateFrom(String str) {
    	return LIBRARY_ARENA.allocateFrom(str);
    }
    
    public static final MethodHandle handleOrThrow(String name, FunctionDescriptor desc) {
        return Linker.nativeLinker().downcallHandle(findOrThrow(name), desc);
    }

    public static final String segmentToString(MemorySegment seg) {
    	if (seg.address() == 0)
    		return null;
    	return seg.reinterpret(Integer.MAX_VALUE).getString(0);
    }
    
    public static List<String> invokeGetStrArray(ApiMethodWithArrRet cb) {
    	return invokeGetPtrArray(cb, item -> NativeUtils.segmentToString(item));
    }
    
    public static <T> List<T> invokeGetPtrArray(ApiMethodWithArrRet cb, ApiMethodArrTransformer<T> transformer) {
    	try (var arena = Arena.ofConfined()) {
    		MemorySegment lengthOut = arena.allocate(ValueLayout.JAVA_LONG);
            MemorySegment strArr = cb.invoke(lengthOut);
            long length = lengthOut.get(JAVA_LONG, 0);
            
            return getPtrArray(strArr, length, transformer);
    	}
    }
    
    public static <T> List<T> getPtrArray(MemorySegment arr, long length, ApiMethodArrTransformer<T> transformer) {
		arr = arr.reinterpret(ValueLayout.ADDRESS.byteSize() * length);
    	
		ArrayList<T> list = new ArrayList<>((int) length);
    	for (int i = 0; i < length; i++) {
    		MemorySegment itemPtr = arr.getAtIndex(ValueLayout.ADDRESS, i);
    		T item = transformer.invoke(itemPtr);
    		list.add(item);
    	}
    	
    	return list;
    }
    
    /*
     * This is for methods which utilize `toKwargsList`, `toArgInfoList` and similar. These return the address of an array of structs which are packed one after another.
     */
    public static <T> List<T> invokeGetStructs(ApiMethodWithArrRet cb, ApiMethodArrTransformer<T> transformer, MemoryLayout layout) {
    	try (var arena = Arena.ofConfined()) {
    		MemorySegment lengthOut = arena.allocate(ValueLayout.JAVA_LONG);
            MemorySegment strArr = cb.invoke(lengthOut);
            long size = lengthOut.get(JAVA_LONG, 0);
            
        	strArr = strArr.reinterpret(layout.byteSize() * size);
        	
    		ArrayList<T> list = new ArrayList<>((int) size);
        	for (int i = 0; i < size; i++) {
        		MemorySegment itemPtr = strArr.asSlice(layout.byteSize() * i, layout);
        		T item = transformer.invoke(itemPtr);
        		list.add(item);
        	}
        	
        	return list;
    	}
    }
    
    public interface ApiMethodArrTransformer <T> {
    	/**
    	 * @param item Caution! May be a null pointer!
    	 * @return
    	 */
    	public T invoke(MemorySegment item);
    }
    
    public interface ApiMethodWithArrRet {
    	public MemorySegment invoke(MemorySegment lengthOut);
    }
    
    public static class ApiMethod {
    	public final String NAME;
    	public final FunctionDescriptor DESC;
    	public final MemorySegment ADDR;
    	public final MethodHandle HANDLE;
    	
    	public ApiMethod(String name, FunctionDescriptor desc) {
    		this(0, name, desc);
    	}
    	
    	public ApiMethod(int dereferences, String name, FunctionDescriptor desc) {
    		MemorySegment resolvedAddr = findOrThrow(name);
    		for (int i = 0; i < dereferences; i++) {
    			resolvedAddr = resolvedAddr.reinterpret(ValueLayout.ADDRESS.byteSize()).get(ValueLayout.ADDRESS, 0);    			
    		}
    		
    		this.NAME = name;
    		this.DESC = desc;
    		this.ADDR = resolvedAddr;
    		this.HANDLE = Linker.nativeLinker().downcallHandle(resolvedAddr, DESC);
    	}
    	
    	public ApiMethod(MemorySegment address, FunctionDescriptor desc ) {
    		this(0, address, desc);
    	}

    	public ApiMethod(int dereferences, MemorySegment address, FunctionDescriptor desc) {
    		MemorySegment resolvedAddr = address;
    		for (int i = 0; i < dereferences; i++) {
    			resolvedAddr = resolvedAddr.reinterpret(ValueLayout.ADDRESS.byteSize()).get(ValueLayout.ADDRESS, 0);    			
    		}
    		
    		this.NAME = "";
    		this.DESC = desc;
    		this.ADDR = resolvedAddr;
    		this.HANDLE = Linker.nativeLinker().downcallHandle(resolvedAddr, DESC);
    		
    		// removed another constructor with minVersion check -- trying to keep this general
			// System.err.println("Symbol " + name + " should be present (required v. " + minVersion + ", installed API v. " + Version_h.SoapySDR_getAPIVersion() + "), but couldn't be resolved!");
    	}

    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface ApiMinVersion {
    	String value();
    }
}
