package sge.geometry;

import sge.math.Vector3;

/**
 * Triangle using 3 Vertex references.
 */
public class Triangle {

    public Vertex v1;
    public Vertex v2;
    public Vertex v3;

    /**
     * Vertex Constructor.
     */
    public Triangle (final Vertex v1, final Vertex v2, final Vertex v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    /**
     * Copy Constructor.
     */
    public Triangle (final Triangle other) {
        v1 = other.v1;
        v2 = other.v2;
        v3 = other.v3;
    }

    /**
     * Determine natural normal formed by face.
     */
    public Vector3 normal () {

        Vector3 a = v2.position.sub(v1.position).normalize();
        Vector3 b = v3.position.sub(v1.position).normalize();

        return a.cross(b);
    }
}
