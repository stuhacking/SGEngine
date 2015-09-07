package sge.geometry;

import java.nio.FloatBuffer;
import java.util.List;

import sge.math.Vector3;
import sge.util.DirectBuffer;

/**
 * Contiguously allocated Vertex Array for storing a list of Vertex objects
 * as an interleaved float array. Permits modification of contained vertices but
 * not resizing the array.
 * <p/>
 * This is a utility class for emulating C++ style contiguous buffers. Useful
 * for preparing data for, e.g. streaming to the GPU.
 */
public class Vector3Array {

    public FloatBuffer buffer;

    public Vector3Array (int capacity) {
        buffer = DirectBuffer.createFloatBuffer(capacity * Vector3.SIZE);
    }

    /**
     * Convert a List<Vertex> into a contiguous interleaved float array.
     * See {@link Vertex} for data layout.
     */
    public Vector3Array (List<Vector3> data) {
        this(data.size());
        for (Vector3 v : data) {
            buffer.put(v.toFloatArray());
        }
        buffer.rewind();
    }

    /**
     * Construct a Vertex object from the floats beginning
     * from ``index'' up to Vector3.SIZE.
     * ``index'' is the Vertex position into the float array.
     */
    public Vector3 getVertex (int index) {
        float[] vecData = new float[Vector3.SIZE];
        buffer.get(vecData, index * Vector3.SIZE, Vector3.SIZE);
        return new Vector3(vecData);
    }

    /**
     * Set the float data beginning at ``index'' to be the
     * values contained in Vertex ``vert''.
     * ``index'' is the Vertex position into the float array.
     */
    public void setVertex (int index, Vector3 vec) {
        float[] vecData = vec.toFloatArray();
        float[] buf = buffer.array();
        int i = index * Vector3.SIZE;

        for (int x = 0; x < Vector3.SIZE; x++) {
            buf[i++] = vecData[x];
        }
    }
}
