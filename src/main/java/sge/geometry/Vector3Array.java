package sge.geometry;

import java.nio.FloatBuffer;
import java.util.List;

import sge.math.Vector3;
import sge.util.DirectBuffer;

/**
 * Contiguously allocated Vector3 Array for storing a list of Vector3 objects
 * as an interleaved float array. Permits modification of contained vertices but
 * not resizing the array.
 *
 * This is a utility class for emulating C++ style contiguous buffers. Useful
 * for preparing data for, e.g. streaming to the GPU.
 */
public class Vector3Array {

    public FloatBuffer buffer;

    public Vector3Array (final int capacity) {
        buffer = DirectBuffer.createFloatBuffer(capacity * Vector3.SIZE);
    }

    /**
     * Convert a List<Vertex> into a contiguous interleaved float array.
     * See {@link Vertex} for data layout.
     */
    public Vector3Array (final List<Vector3> data) {
        this(data.size());

        for (Vector3 v : data) {
            buffer.put(v.toFloatArray());
        }

        buffer.flip();
    }

    /**
     * Get the Vector3 at position `index' in the array.
     */
    public Vector3 get (final int index) {
        float[] vecData = new float[Vector3.SIZE];
        buffer.get(vecData, index * Vector3.SIZE, Vector3.SIZE);
        return new Vector3(vecData);
    }

    /**
     * Set the float data beginning at `index' to be the
     * value of vec.
     */
    public void set (final int index, final Vector3 vec) {
        float[] data = vec.toFloatArray();

        buffer.put(data, index * Vector3.SIZE, Vector3.SIZE);
    }
}
