package sge.geometry;

import java.nio.FloatBuffer;
import java.util.List;

import sge.color.RGBAColor;
import sge.util.DirectBuffer;

/**
 * Contiguously allocated Vertex Array for storing a list of Vertex objects as an interleaved float array.
 * Permits modification of contained vertices but not resizing the array. This is a utility class for
 * emulating C style contiguous buffers. Useful for preparing data for, e.g. streaming to the GPU.
 */
public class VertexArray {

    public FloatBuffer buffer;

    private VertexArray (int capacity) {
        buffer = DirectBuffer.createFloatBuffer(capacity * Vertex.SIZE);
    }

    public VertexArray (final List<Vertex> vertices) {
        this(vertices.size());

        for (Vertex v : vertices) {
            buffer.put(v.toFloatArray());
        }

        buffer.flip();
    }

    /**
     * Create an interleaved vertex array from a list of vertices. Doesn't use an index buffer, assumes all
     * vertex references exist in the list, in order and duplicated. This will create an array in a format
     * that OpenGL can interpret as GL_TRIANGLES.
     */
    public static VertexArray fromVertices (final Mesh data) {
        return new VertexArray(data.vertices);
    }

    /**
     * Create an interleaved vertex array from a list of faces. This will create an array in a format that
     * OpenGL can interpret as GL_TRIANGLES. Vertex information will not be modified in any way.
     */
    public static VertexArray fromFaces (final Mesh data) {
        return fromFaces(data, false);
    }

    /**
     * Create an interleaved vertex array from a list of faces.
     *
     * Creates an array in a format that OpenGL can interpret as GL_TRIANGLES. Vertex
     * Colors may be altered depending on the value of solidFaceColors.
     *
     * @param data Mesh data.
     * @param solidFaceColors If true each triangle will inherit the vertex color of the first Vertex, resulting in
     *                       flat shading. Otherwise, vertices will retain their own color.
     *
     */
    public static VertexArray fromFaces (final Mesh data, final boolean solidFaceColors) {
        VertexArray array = new VertexArray(data.getFaceCount() * 3);
        Vertex v1, v2, v3;

        if (solidFaceColors) {
            for (int k = 0, kMax = data.getFaceCount(); k < kMax; k++) {
                Triangle f = data.getFace(k);
                RGBAColor faceColor = f.v1.color;

                v1 = new Vertex(f.v1);
                v1.color = faceColor;

                v2 = new Vertex(f.v2);
                v2.color = faceColor;

                v3 = new Vertex(f.v3);
                v3.color = faceColor;

                array.buffer.put(v1.toFloatArray());
                array.buffer.put(v2.toFloatArray());
                array.buffer.put(v3.toFloatArray());
            }
        } else {
            for (int k = 0, kMax = data.getFaceCount(); k < kMax; k++) {
                Triangle f = data.getFace(k);

                v1 = f.v1;
                v2 = f.v2;
                v3 = f.v3;

                array.buffer.put(v1.toFloatArray());
                array.buffer.put(v2.toFloatArray());
                array.buffer.put(v3.toFloatArray());
            }
        }

        array.buffer.flip();

        return array;
    }

    /**
     * Get the Vertex at position `index'.
     */
    public Vertex get (final int index) {
        float[] vertData = new float[Vertex.SIZE];
        buffer.get(vertData, index * Vertex.SIZE, Vertex.SIZE);
        return new Vertex(vertData);
    }

    /**
     * Set the float data beginning at `index' to be the values of vert.
     */
    public void set (final int index, final Vertex vert) {
        float[] data = vert.toFloatArray();

        buffer.put(data, index * Vertex.SIZE, Vertex.SIZE);
    }
}
