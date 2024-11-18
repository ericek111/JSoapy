package eu.lixko.jsoapy.soapy;

import java.lang.foreign.*;
import java.util.function.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct {
 *     size_t size;
 *     char **keys;
 *     char **vals;
 * }
 * }
 */
public class SoapySDRKwargs_h {

    SoapySDRKwargs_h() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_LONG.withName("size"),
        ValueLayout.ADDRESS.withName("keys"),
        ValueLayout.ADDRESS.withName("vals")
    ).withName("$anon$34:9");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfLong size$LAYOUT = (OfLong)$LAYOUT.select(groupElement("size"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * size_t size
     * }
     */
    public static final OfLong size$layout() {
        return size$LAYOUT;
    }

    private static final long size$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * size_t size
     * }
     */
    public static final long size$offset() {
        return size$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * size_t size
     * }
     */
    public static long size(MemorySegment struct) {
        return struct.get(size$LAYOUT, size$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * size_t size
     * }
     */
    public static void size(MemorySegment struct, long fieldValue) {
        struct.set(size$LAYOUT, size$OFFSET, fieldValue);
    }

    private static final AddressLayout keys$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("keys"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char **keys
     * }
     */
    public static final AddressLayout keys$layout() {
        return keys$LAYOUT;
    }

    private static final long keys$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char **keys
     * }
     */
    public static final long keys$offset() {
        return keys$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char **keys
     * }
     */
    public static MemorySegment keys(MemorySegment struct) {
        return struct.get(keys$LAYOUT, keys$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char **keys
     * }
     */
    public static void keys(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(keys$LAYOUT, keys$OFFSET, fieldValue);
    }

    private static final AddressLayout vals$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("vals"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char **vals
     * }
     */
    public static final AddressLayout vals$layout() {
        return vals$LAYOUT;
    }

    private static final long vals$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char **vals
     * }
     */
    public static final long vals$offset() {
        return vals$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char **vals
     * }
     */
    public static MemorySegment vals(MemorySegment struct) {
        return struct.get(vals$LAYOUT, vals$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char **vals
     * }
     */
    public static void vals(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(vals$LAYOUT, vals$OFFSET, fieldValue);
    }

    /**
     * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
     * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
     */
    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }

    /**
     * The size (in bytes) of this struct
     */
    public static long sizeof() { return layout().byteSize(); }

    /**
     * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size {@code elementCount} using {@code allocator}.
     * The returned segment has size {@code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction} (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction} (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
    
}

