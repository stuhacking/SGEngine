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

    /**
     * Create an interleaved vertex array from a list of vertices. Doesn't use an index buffer, assumes all
     * vertex references exist in the list, in order and duplicated. This will create an array in a format
     * that OpenGL can interpret as GL_TRIANGLES.
     */
    public static VertexArray fromVertices (Mesh data) {
        return fromVertices(data.vertices);
    }

    public static VertexArray fromVertices (List<Vertex> vertices) {
        VertexArray array = new VertexArray(vertices.size());

        for (Vertex v : vertices) {
            array.buffer.put(v.toFloatArray());
        }

        array.buffer.flip();

        return array;
    }

    /**
     * Create an interleaved vertex array from a list of faces. This will create an array in a format that
     * OpenGL can interpret as GL_TRIANGLES. Vertex information will not be modified in any way.
     */
    public static VertexArray fromFaces (Mesh data) {
        return fromFaces(data, false);
    }

    /**
     * Create an interleaved vertex array from a list of faces. This will create an array in a format that
     * OpenGL can interpret as GL_TRIANGLES. Vertex Colors may be altered depending on the value of
     * solidifyColors. - If false, do not adjust vertex color attribute. - If true, use the color of v1 for
     * each vertex in the face which will result in solid face colors.
     */
    public static VertexArray fromFaces (Mesh data, boolean solidifyColors) {
        VertexArray array = new VertexArray(data.getFaceCount() * 3);

        for (int k = 0; k < data.getFaceCount(); k++) {
            Triangle f = data.getFace(k);
            Vertex v1, v2, v3;
            if (solidifyColors) {
                RGBAColor faceColor = f.getFirst().getColor();

                v1 = new Vertex(f.getFirst());
                v1.setColor(faceColor);
                v2 = new Vertex(f.getSecond());
                v2.setColor(faceColor);
                v3 = new Vertex(f.getThird());
                v3.setColor(faceColor);

            } else {
                v1 = f.getFirst();
                v2 = f.getSecond();
                v3 = f.getThird();
            }

            array.buffer.put(v1.toFloatArray());
            array.buffer.put(v2.toFloatArray());
            array.buffer.put(v3.toFloatArray());
        }

        array.buffer.flip();

        return array;
    }

    /**
     * Construct a Vertex object from the floats beginning from ``index'' up to Vertex.SIZE. ``index'' is the
     * Vertex position into the float array.
     */
    public Vertex getVertex (int index) {
        float[] vertData = new float[Vertex.SIZE];
        buffer.get(vertData, index * Vertex.SIZE, Vertex.SIZE);
        return new Vertex(vertData);
    }

    /**
     * Set the float data beginning at ``index'' to be the values contained in Vertex ``vert''. ``index'' is
     * the Vertex position into the float array.
     */
    public void setVertex (int index, Vertex vert) {
        float[] vertData = vert.toFloatArray();
        float[] buf = buffer.array();
        int i = index * Vertex.SIZE;

        for (int x = 0; x < Vertex.SIZE; x++) {
            buf[i++] = vertData[x];
        }
    }
}
