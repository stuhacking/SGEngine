package sge.geometry.primitive;

import sge.geometry.Mesh;
import sge.math.Vector3;

public class Cube implements Primitive {

    private Vector3 center = Vector3.ZERO;
    private Vector3 hSize = Vector3.ONE;

    public Cube () { }

    public Cube (final Vector3 center, final Vector3 size) {
        this.center = center;
        this.hSize = size.scale(0.5f);
    }

    @Override
    public Mesh toMesh () {
        Mesh mesh = new Mesh();
        
        // Top Vecs
        Vector3 v1 = new Vector3(center.x - hSize.x, center.y + hSize.y, center.z - hSize.z);
        Vector3 v2 = new Vector3(center.x + hSize.x, center.y + hSize.y, center.z - hSize.z);
        Vector3 v3 = new Vector3(center.x + hSize.x, center.y + hSize.y, center.z + hSize.z);
        Vector3 v4 = new Vector3(center.x - hSize.x, center.y + hSize.y, center.z + hSize.z);

        // Bottom Vecs
        Vector3 v5 = new Vector3(center.x - hSize.x, center.y + hSize.y, center.z - hSize.z);
        Vector3 v6 = new Vector3(center.x + hSize.x, center.y - hSize.y, center.z - hSize.z);
        Vector3 v7 = new Vector3(center.x + hSize.x, center.y - hSize.y, center.z + hSize.z);
        Vector3 v8 = new Vector3(center.x - hSize.x, center.y - hSize.y, center.z + hSize.z);

        mesh.addQuadFace(v1, v2, v3, v4, new Vector3(0f, 1f, 0f)); // top
        mesh.addQuadFace(v5, v8, v7, v6, new Vector3(0f, -1f, 0f)); // bottom

        mesh.addQuadFace(v1, v4, v8, v5, new Vector3(-1f, 0f, 0f)); // left
        mesh.addQuadFace(v3, v2, v6, v7, new Vector3(1f, 0f, 0f)); // right

        mesh.addQuadFace(v4, v3, v7, v8, new Vector3(0f, 0f, 1f)); // front
        mesh.addQuadFace(v2, v1, v5, v6, new Vector3(0f, 0f, -1f)); // back

        return mesh;
    }
}
