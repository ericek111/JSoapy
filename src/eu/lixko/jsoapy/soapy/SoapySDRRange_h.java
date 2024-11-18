package eu.lixko.jsoapy.soapy;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct {
 *     double minimum;
 *     double maximum;
 *     double step;
 * }
 * }
 */
public class SoapySDRRange_h {

    SoapySDRRange_h() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_DOUBLE.withName("minimum"),
        ValueLayout.JAVA_DOUBLE.withName("maximum"),
        ValueLayout.JAVA_DOUBLE.withName("step")
    ).withName("$anon$26:9");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfDouble minimum$LAYOUT = (OfDouble)$LAYOUT.select(groupElement("minimum"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * double minimum
     * }
     */
    public static final OfDouble minimum$layout() {
        return minimum$LAYOUT;
    }

    private static final long minimum$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * double minimum
     * }
     */
    public static final long minimum$offset() {
        return minimum$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * double minimum
     * }
     */
    public static double minimum(MemorySegment struct) {
        return struct.get(minimum$LAYOUT, minimum$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * double minimum
     * }
     */
    public static void minimum(MemorySegment struct, double fieldValue) {
        struct.set(minimum$LAYOUT, minimum$OFFSET, fieldValue);
    }

    private static final OfDouble maximum$LAYOUT = (OfDouble)$LAYOUT.select(groupElement("maximum"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * double maximum
     * }
     */
    public static final OfDouble maximum$layout() {
        return maximum$LAYOUT;
    }

    private static final long maximum$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * double maximum
     * }
     */
    public static final long maximum$offset() {
        return maximum$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * double maximum
     * }
     */
    public static double maximum(MemorySegment struct) {
        return struct.get(maximum$LAYOUT, maximum$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * double maximum
     * }
     */
    public static void maximum(MemorySegment struct, double fieldValue) {
        struct.set(maximum$LAYOUT, maximum$OFFSET, fieldValue);
    }

    private static final OfDouble step$LAYOUT = (OfDouble)$LAYOUT.select(groupElement("step"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * double step
     * }
     */
    public static final OfDouble step$layout() {
        return step$LAYOUT;
    }

    private static final long step$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * double step
     * }
     */
    public static final long step$offset() {
        return step$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * double step
     * }
     */
    public static double step(MemorySegment struct) {
        return struct.get(step$LAYOUT, step$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * double step
     * }
     */
    public static void step(MemorySegment struct, double fieldValue) {
        struct.set(step$LAYOUT, step$OFFSET, fieldValue);
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

