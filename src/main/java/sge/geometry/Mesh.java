package sge.geometry;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import sge.color.RGBAColor;
import sge.math.Vector3;
import sge.util.DirectBuffer;

/**
 * Mesh stores vertex information for meshes which are
 * not expected to be altered in any way other than global
 * orientation.
 */
public class Mesh {

    public final ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    public final ArrayList<Integer> indices = new ArrayList<Integer>();

    /**
     * Return the number of vertices in this Mesh.
     */
    public int getVertexCount () {
        return vertices.size();
    }

    /**
     * Return the number of indices in this Mesh.
     */
    public int getIndexCount () {
        return indices.size();
    }

    /**
     * Return the number of triangular faces in this Mesh.
     */
    public int getFaceCount () {
        return indices.size() / 3;
    }

    public Vertex getVertex (final int index) {
        return vertices.get(index);
    }

    public int getIndex (final int index) {
        return indices.get(index);
    }

    public Triangle getFace (final int index) {
        return new Triangle(vertices.get(indices.get(index)),
                            vertices.get(indices.get(index + 1)),
                            vertices.get(indices.get(index + 2)));
    }

    public void addVert (final Vertex vert) {
        vertices.add(vert);
    }

    public void addFace (final int v1, final int v2, final int v3) {
        indices.add(v1);
        indices.add(v2);
        indices.add(v3);
    }

    /**
     * Add a Quad to the primitive, made from two triangles. The vectors forming the
     * corners should be arranged as follows:
     *
     *   1 --- 2
     *   |   / |
     *   | /   |
     *   4 --- 3
     *
     * TODO: Would be better if Vectors were arranged counter-clockwise
     * to maintain consistency with OpenGL frontface order.
     *
     * @param xy
     * @param x1y
     * @param x1y1
     * @param xy1
     * @param normal
     */
    public void addQuadFace (
            final Vector3 xy, final Vector3 x1y,
            final Vector3 x1y1, final Vector3 xy1,
            final Vector3 normal) {
        /**
         * Vertex Layout format
         *
         *  1 --- 2
         *  |   / |
         *  | /   |
         *  3 --- 4
         *
         *  TODO: This too should be made consistent... no reason for this to differ
         *        from the parameter order.
         */
        Vertex v1 = new Vertex(xy);
        Vertex v2 = new Vertex(x1y);
        Vertex v3 = new Vertex(xy1);
        Vertex v4 = new Vertex(x1y1);

        v1.normal = normal;
        v2.normal = normal;
        v3.normal = normal;
        v4.normal = normal;

        // Set default Texture coordinates of (0,0)..(1,1) for each Quad.
        v3.setTexCoords(0f, 0f);
        v4.setTexCoords(1f, 0f);
        v1.setTexCoords(0f, 1f);
        v2.setTexCoords(1f, 1f);

        v1.setColor(0f, 0f, 1f, 1f);
        v2.setColor(1f, 0f, 1f, 1f);
        v3.setColor(0f, 1f, 0f, 1f);
        v4.setColor(1f, 0f, 0f, 1f);

        addVert(v1);
        addVert(v2);
        addVert(v3);
        addVert(v4);
        addFace(3, 2, 1);
        addFace(3, 4, 2);
    }

    public void smoothNormals () {
        // Clear existing normals
        for (Vertex v : vertices) {
            v.normal = Vector3.ZERO;
        }

        for (int k = 0, kMax = getFaceCount(); k < kMax; k++) {
            Triangle f = getFace(k);
            Vector3 normal = f.normal();

            // Average shared normals - smooth shading
            f.v1.normal = f.v1.normal.add(normal).normalize();
            f.v2.normal = f.v2.normal.add(normal).normalize();
            f.v3.normal = f.v3.normal.add(normal).normalize();
        }
    }

    /**
     * Set all the Vertex Colors in this Mesh to a single color given by
     * (r, g, b, a) values.
     *
     * @param r Red value.
     * @param g Green value.
     * @param b Blue value.
     * @param a Alpha value.
     */
    public void clearColor (final float r, final float g, final float b, final float a) {
        clearColor(new RGBAColor(r, g, b, a));
    }

    /**
     * Set all the Vertex Colors in this Mesh to a single color given by
     * an RGBAColor value..
     *
     * @param color Combined color values.
     */
    public void clearColor (final RGBAColor color) {
        for (Vertex v : vertices) {
            v.color = color;
        }
    }

    /**
     * Apply a translation to the vertices in this Mesh.
     * Destructive.
     *
     * @param offset
     */
    public void translate (final Vector3 offset) {
        for (Vertex v : vertices) {
            v.position = v.position.add(offset);
        }
    }

    /**
     * Create an interleaved vertex array from a list of vertices.
     * Doesn't use an index buffer, assumes all vertex references exist
     * in the list, in order and duplicated. This will create an
     * array in a format that OpenGL can interpret as GL_TRIANGLES.
     */
    public IntBuffer indexArray() {
        IntBuffer buffer = DirectBuffer.createIntBuffer(indices.size());

        for (int i : indices) {
            buffer.put(i);
        }

        buffer.flip();

        return buffer;
    }

    public FloatBuffer vertexArray () {
        FloatBuffer buffer = DirectBuffer.createFloatBuffer(vertices.size() * Vertex.SIZE);

        for (Vertex v : vertices) {
            buffer.put(v.toFloatArray());
        }

        buffer.flip();

        return buffer;
    }

     /**
     * Create an interleaved vertex array from a list of faces.
     *
     * Creates an array in a format that OpenGL can interpret as GL_TRIANGLES. Vertex
     * Colors may be altered depending on the value of solidFaceColors.
     *
     * @param solidFaceColors If true each triangle will inherit the vertex color of the first Vertex, resulting in
     *                       flat shading. Otherwise, vertices will retain their own color.
     *
     */
    public FloatBuffer faceArray (final boolean solidFaceColors) {
        FloatBuffer buffer = DirectBuffer.createFloatBuffer(getFaceCount() * Vertex.SIZE * 3);
        Vertex v1, v2, v3;

        if (solidFaceColors) {
            for (int k = 0, kMax = getFaceCount(); k < kMax; k++) {
                Triangle f = getFace(k);
                RGBAColor faceColor = f.v1.color;

                v1 = new Vertex(f.v1);
                v1.color = faceColor;

                v2 = new Vertex(f.v2);
                v2.color = faceColor;

                v3 = new Vertex(f.v3);
                v3.color = faceColor;

                buffer.put(v1.toFloatArray());
                buffer.put(v2.toFloatArray());
                buffer.put(v3.toFloatArray());
            }
        } else {
            for (int k = 0, kMax = getFaceCount(); k < kMax; k++) {
                Triangle f = getFace(k);

                v1 = f.v1;
                v2 = f.v2;
                v3 = f.v3;

                buffer.put(v1.toFloatArray());
                buffer.put(v2.toFloatArray());
                buffer.put(v3.toFloatArray());
            }
        }

        buffer.flip();

        return buffer;
    }
}
