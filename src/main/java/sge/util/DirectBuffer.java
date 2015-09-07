package sge.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

/**
 * Helper for creating ByteBuffers containing common primitive
 * types allocated directly in memory. Most LWJGL APIs expect
 * data in this format.
 */
public abstract class DirectBuffer {

    /**
     * Create a buffer large enough to hold 'size' bytes.
     *
     * @param size Number of bytes to allocate.
     * @return New ByteBuffer.
     */
    public static ByteBuffer createByteBuffer (final int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }

    /**
     * Create a buffer large enough to hold 'size' floats (4 byte).
     *
     * @param size Number of floats to allocate.
     * @return New ByteBuffer.
     */
    public static FloatBuffer createFloatBuffer (final int size) {
        return createByteBuffer(size << 2).asFloatBuffer();
    }

    /**
     * Create a buffer large enough to hold 'size' doubles (8 byte).
     *
     * @param size Number of doubles to allocate.
     * @return New ByteBuffer.
     */
    public static DoubleBuffer createDoubleBuffer (final int size) {
        return createByteBuffer(size << 3).asDoubleBuffer();
    }

    /**
     * Create a buffer large enough to hold 'size' integers (4 byte).
     *
     * @param size Number of integers to allocate.
     * @return New ByteBuffer.
     */
    public static IntBuffer createIntBuffer (final int size) {
        return createByteBuffer(size << 2).asIntBuffer();
    }

    /**
     * Create a buffer large enough to hold 'size' long ints (8 byte).
     *
     * @param size Number of long ints to allocate.
     * @return New ByteBuffer.
     */
    public static LongBuffer createLongBuffer (final int size) {
        return createByteBuffer(size << 3).asLongBuffer();
    }
}
