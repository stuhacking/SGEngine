package sge.geometry;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import sge.color.RGBAColor;
import sge.math.Vector3;
import sge.util.DirectBuffer;

/**
 * Mesh stores vertex information for meshes that are
 * not expected to be altered in any way other than global
 * orientation.
 */
public class Mesh {

    public final ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    public final ArrayList<Integer> indices = new ArrayList<Integer>();

    public int getVertexCount () {
        return vertices.size();
    }

    public int getIndexCount () {
        return indices.size();
    }

    public int getFaceCount () {
        return indices.size() / 3;
    }

    public Vertex getVertex (int index) {
        return vertices.get(index);
    }

    public int getIndex (int index) {
        return indices.get(index);
    }

    public Triangle getFace (int index) {
        return new Triangle(vertices.get(indices.get(index)),
                            vertices.get(indices.get(index + 1)),
                            vertices.get(indices.get(index + 2)));
    }

    public void addVert (Vertex vert) {
        vertices.add(vert);
    }

    public void addFace (int v1, int v2, int v3) {
        indices.add(v1);
        indices.add(v2);
        indices.add(v3);
    }

    /**
     * Add a Quad to the primitive, made from two triangles. The vectors forming the
     * corners should be arranged as follows:
     * <p/>
     * 1 --- 2
     * |   / |
     * | /   |
     * 4 --- 3
     * <p/>
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

        v1.setNormal(normal);
        v2.setNormal(normal);
        v3.setNormal(normal);
        v4.setNormal(normal);

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
            v.setNormal(Vector3.ZERO);
        }

        for (int k = 0; k < getFaceCount(); k++) {
            Triangle f = getFace(k);

            Vector3 v1 = f.normal();

            // Average shared normals - smooth shading
            Vertex current;

            current = f.getFirst();
            current.setNormal(current.getNormal().add(v1).normalize());

            current = f.getSecond();
            current.setNormal(current.getNormal().add(v1).normalize());

            current = f.getThird();
            current.setNormal(current.getNormal().add(v1).normalize());
        }
    }

    public void clearColor (float r, float g, float b, float a) {
        clearColor(new RGBAColor(r, g, b, a));
    }

    public void clearColor (RGBAColor color) {
        for (Vertex v : vertices) {
            v.setColor(color);
        }
    }

    public long vertCount () {
        return vertices.size();
    }

    @Override
    public Mesh clone () {
        Mesh clone = new Mesh();
        for (Vertex v : vertices) {
            clone.addVert(new Vertex(v));
        }
        for (int k = 0; k < getFaceCount(); k++) {
            clone.addFace(indices.get(k), indices.get(k + 1), indices.get(k + 2));
        }

        return clone;
    }

    public void translate (Vector3 offset) {
        for (Vertex v : vertices) {
            v.setPosition(v.getPosition().add(offset));
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
     * Create an interleaved vertex array from a list of faces. This will create an array in a format that
     * OpenGL can interpret as GL_TRIANGLES. Vertex Colors may be altered depending on the value of
     * solidifyColors. - If false, do not adjust vertex color attribute. - If true, use the color of v1 for
     * each vertex in the face which will result in solid face colors.
     */
    public FloatBuffer faceArray (boolean solidFaceColors) {
        FloatBuffer buffer = DirectBuffer.createFloatBuffer(getFaceCount() * Vertex.SIZE * 3);

        for (int k = 0; k < getFaceCount(); k++) {
            Triangle f = getFace(k);
            Vertex v1, v2, v3;
            if (solidFaceColors) {
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

            buffer.put(v1.toFloatArray());
            buffer.put(v2.toFloatArray());
            buffer.put(v3.toFloatArray());
        }

        buffer.flip();

        return buffer;
    }
}
