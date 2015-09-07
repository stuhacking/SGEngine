package sge.geometry;

import sge.math.Vector3;

/**
 * 3 Vertex Triangle
 */
public class Triangle {

    private final Vertex[] verts = new Vertex[3];

    /**
     * Vertex Constructor.
     */
    public Triangle (Vertex v1, Vertex v2, Vertex v3) {
        verts[0] = v1;
        verts[1] = v2;
        verts[2] = v3;
    }

    /**
     * Copy Constructor.
     */
    public Triangle (final Triangle other) {
        verts[0] = new Vertex(other.verts[0]);
        verts[1] = new Vertex(other.verts[1]);
        verts[2] = new Vertex(other.verts[2]);
    }

    /**
     * Determine natural normal formed by face.
     */
    public Vector3 normal () {

        Vector3 v1 = verts[1].getPosition().sub(verts[0].getPosition()).normalize();
        Vector3 v2 = verts[2].getPosition().sub(verts[0].getPosition()).normalize();

        return v1.cross(v2);
    }

    public Vertex getFirst () {
        return verts[0];
    }

    public void setFirst (Vertex v) {
        verts[0] = v;
    }

    public Vertex getSecond () {
        return verts[1];
    }

    public void setSecond (Vertex v) {
        verts[1] = v;
    }

    public Vertex getThird () {
        return verts[2];
    }

    public void setThird (Vertex v) {
        verts[2] = v;
    }
}
