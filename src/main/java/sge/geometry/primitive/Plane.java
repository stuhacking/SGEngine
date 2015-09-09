package sge.geometry.primitive;

import sge.geometry.Mesh;
import sge.math.Vector2;
import sge.math.Vector3;

public class Plane implements Primitive {

    /** Plane center point. */
    private Vector3 center = Vector3.ZERO;

    /** Half-size of plane. */
    private Vector2 hSize = Vector2.ONE;

    public Plane () { }

    /**
     * Create a Plane aligned to the World XZ plane with  a center and
     * size.
     *
     * TODO Draw Arbitrary rotated planes.
     *
     * @param center
     * @param size
     */
    public Plane (final Vector3 center, final Vector2 size) {
        this.center = center;
        this.hSize = size.scale(0.5f);
    }

    @Override
    public Mesh toMesh () {
        Mesh mesh = new Mesh();

        Vector3 v1 = new Vector3(center.x - hSize.x, center.y, center.z - hSize.y);
        Vector3 v2 = new Vector3(center.x + hSize.x, center.y, center.z - hSize.y);
        Vector3 v3 = new Vector3(center.x + hSize.x, center.y, center.z + hSize.y);
        Vector3 v4 = new Vector3(center.x - hSize.x, center.y, center.z + hSize.y);

        mesh.addQuadFace(v1, v2, v3, v4, new Vector3(0f, 1f, 0f));

        return mesh;
    }

}
