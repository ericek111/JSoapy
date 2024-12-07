package eu.lixko.jsoapy.soapy;

import java.lang.foreign.*;
import java.util.function.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct {
 *     char *key;
 *     char *value;
 *     char *name;
 *     char *description;
 *     char *units;
 *     SoapySDRArgInfoType type;
 *     SoapySDRRange range;
 *     size_t numOptions;
 *     char **options;
 *     char **optionNames;
 * }
 * }
 */
public class SoapySDRArgInfo_h {

    SoapySDRArgInfo_h() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        ValueLayout.ADDRESS.withName("key"),
        ValueLayout.ADDRESS.withName("value"),
        ValueLayout.ADDRESS.withName("name"),
        ValueLayout.ADDRESS.withName("description"),
        ValueLayout.ADDRESS.withName("units"),
        ValueLayout.JAVA_INT.withName("type"),
        MemoryLayout.paddingLayout(4),
        SoapySDRRange_h.layout().withName("range"),
        ValueLayout.JAVA_LONG.withName("numOptions"),
        ValueLayout.ADDRESS.withName("options"),
        ValueLayout.ADDRESS.withName("optionNames")
    ).withName("$anon$63:9");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final AddressLayout key$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("key"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char *key
     * }
     */
    public static final AddressLayout key$layout() {
        return key$LAYOUT;
    }

    private static final long key$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char *key
     * }
     */
    public static final long key$offset() {
        return key$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char *key
     * }
     */
    public static MemorySegment key(MemorySegment struct) {
        return struct.get(key$LAYOUT, key$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char *key
     * }
     */
    public static void key(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(key$LAYOUT, key$OFFSET, fieldValue);
    }

    private static final AddressLayout value$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("value"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char *value
     * }
     */
    public static final AddressLayout value$layout() {
        return value$LAYOUT;
    }

    private static final long value$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char *value
     * }
     */
    public static final long value$offset() {
        return value$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char *value
     * }
     */
    public static MemorySegment value(MemorySegment struct) {
        return struct.get(value$LAYOUT, value$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char *value
     * }
     */
    public static void value(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(value$LAYOUT, value$OFFSET, fieldValue);
    }

    private static final AddressLayout name$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("name"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char *name
     * }
     */
    public static final AddressLayout name$layout() {
        return name$LAYOUT;
    }

    private static final long name$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char *name
     * }
     */
    public static final long name$offset() {
        return name$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char *name
     * }
     */
    public static MemorySegment name(MemorySegment struct) {
        return struct.get(name$LAYOUT, name$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char *name
     * }
     */
    public static void name(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(name$LAYOUT, name$OFFSET, fieldValue);
    }

    private static final AddressLayout description$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("description"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char *description
     * }
     */
    public static final AddressLayout description$layout() {
        return description$LAYOUT;
    }

    private static final long description$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char *description
     * }
     */
    public static final long description$offset() {
        return description$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char *description
     * }
     */
    public static MemorySegment description(MemorySegment struct) {
        return struct.get(description$LAYOUT, description$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char *description
     * }
     */
    public static void description(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(description$LAYOUT, description$OFFSET, fieldValue);
    }

    private static final AddressLayout units$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("units"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char *units
     * }
     */
    public static final AddressLayout units$layout() {
        return units$LAYOUT;
    }

    private static final long units$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char *units
     * }
     */
    public static final long units$offset() {
        return units$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char *units
     * }
     */
    public static MemorySegment units(MemorySegment struct) {
        return struct.get(units$LAYOUT, units$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char *units
     * }
     */
    public static void units(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(units$LAYOUT, units$OFFSET, fieldValue);
    }

    private static final OfInt type$LAYOUT = (OfInt)$LAYOUT.select(groupElement("type"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * SoapySDRArgInfoType type
     * }
     */
    public static final OfInt type$layout() {
        return type$LAYOUT;
    }

    private static final long type$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * SoapySDRArgInfoType type
     * }
     */
    public static final long type$offset() {
        return type$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * SoapySDRArgInfoType type
     * }
     */
    public static int type(MemorySegment struct) {
        return struct.get(type$LAYOUT, type$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * SoapySDRArgInfoType type
     * }
     */
    public static void type(MemorySegment struct, int fieldValue) {
        struct.set(type$LAYOUT, type$OFFSET, fieldValue);
    }

    private static final GroupLayout range$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("range"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * SoapySDRRange range
     * }
     */
    public static final GroupLayout range$layout() {
        return range$LAYOUT;
    }

    private static final long range$OFFSET = 48;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * SoapySDRRange range
     * }
     */
    public static final long range$offset() {
        return range$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * SoapySDRRange range
     * }
     */
    public static MemorySegment range(MemorySegment struct) {
        return struct.asSlice(range$OFFSET, range$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * SoapySDRRange range
     * }
     */
    public static void range(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, range$OFFSET, range$LAYOUT.byteSize());
    }

    private static final OfLong numOptions$LAYOUT = (OfLong)$LAYOUT.select(groupElement("numOptions"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * size_t numOptions
     * }
     */
    public static final OfLong numOptions$layout() {
        return numOptions$LAYOUT;
    }

    private static final long numOptions$OFFSET = 72;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * size_t numOptions
     * }
     */
    public static final long numOptions$offset() {
        return numOptions$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * size_t numOptions
     * }
     */
    public static long numOptions(MemorySegment struct) {
        return struct.get(numOptions$LAYOUT, numOptions$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * size_t numOptions
     * }
     */
    public static void numOptions(MemorySegment struct, long fieldValue) {
        struct.set(numOptions$LAYOUT, numOptions$OFFSET, fieldValue);
    }

    private static final AddressLayout options$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("options"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char **options
     * }
     */
    public static final AddressLayout options$layout() {
        return options$LAYOUT;
    }

    private static final long options$OFFSET = 80;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char **options
     * }
     */
    public static final long options$offset() {
        return options$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char **options
     * }
     */
    public static MemorySegment options(MemorySegment struct) {
        return struct.get(options$LAYOUT, options$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char **options
     * }
     */
    public static void options(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(options$LAYOUT, options$OFFSET, fieldValue);
    }

    private static final AddressLayout optionNames$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("optionNames"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char **optionNames
     * }
     */
    public static final AddressLayout optionNames$layout() {
        return optionNames$LAYOUT;
    }

    private static final long optionNames$OFFSET = 88;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char **optionNames
     * }
     */
    public static final long optionNames$offset() {
        return optionNames$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char **optionNames
     * }
     */
    public static MemorySegment optionNames(MemorySegment struct) {
        return struct.get(optionNames$LAYOUT, optionNames$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char **optionNames
     * }
     */
    public static void optionNames(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(optionNames$LAYOUT, optionNames$OFFSET, fieldValue);
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

