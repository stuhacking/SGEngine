package sge.geometry;

import java.nio.IntBuffer;

import sge.util.DirectBuffer;

/**
 * Contiguously allocated Vertex Array for storing a list of Vertex objects
 * as an interleaved float array. Permits modification of contained vertices but
 * not resizing the array.
 * <p/>
 * This is a utility class for emulating C style contiguous buffers. Useful
 * for preparing data for, e.g. streaming to the GPU.
 */
public class IndexArray {

    /**
     * Create an interleaved vertex array from a list of vertices.
     * Doesn't use an index buffer, assumes all vertex references exist
     * in the list, in order and duplicated. This will create an
     * array in a format that OpenGL can interpret as GL_TRIANGLES.
     */
    public static IntBuffer fromIndices (Mesh data) {
        IntBuffer buffer = DirectBuffer.createIntBuffer(data.getIndexCount());

        for (int k = 0; k < data.getIndexCount(); k++) {
            buffer.put(data.getIndex(k));
        }

        buffer.flip();

        return buffer;
    }
}
