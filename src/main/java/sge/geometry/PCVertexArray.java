package sge.geometry;

import java.nio.FloatBuffer;
import java.util.List;

import sge.util.DirectBuffer;

/**
 * Contiguously allocated Vertex Array for storing a list of Vertex objects as an interleaved float array.
 * Permits modification of contained vertices but not resizing the array. This is a utility class for
 * emulating C style contiguous buffers. Useful for preparing data for, e.g. streaming to the GPU.
 */
public class PCVertexArray {

    public FloatBuffer buffer;

    private PCVertexArray (int capacity) {
        buffer = DirectBuffer.createFloatBuffer(capacity * PCVertex.SIZE);
    }

    public PCVertexArray (final List<PCVertex> vertices) {
        this(vertices.size());

        for (PCVertex v : vertices) {
            buffer.put(v.toFloatArray());
        }

        buffer.flip();
    }

    /**
     * Get the Vertex at position `index'.
     */
    public PCVertex get (final int index) {
        float[] vertData = new float[PCVertex.SIZE];
        buffer.get(vertData, index * PCVertex.SIZE, PCVertex.SIZE);
        return new PCVertex(vertData);
    }

    /**
     * Set the float data beginning at `index' to be the values of vert.
     */
    public void set (final int index, final PCVertex vert) {
        float[] data = vert.toFloatArray();

        buffer.put(data, index * PCVertex.SIZE, PCVertex.SIZE);
    }
}
