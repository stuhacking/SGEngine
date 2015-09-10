package sge.importer.obj;

import sge.geometry.Mesh;
import sge.geometry.Vertex;

/**
 * Interface for Loading a Mesh from an .obj document.
 */
public class ObjConverter {

    /**
     * Convert a parsed .obj document to a Mesh object.
     *
     * @param document A parsed ObjDocument.
     * @return Mesh
     */
    public static Mesh objDocumentToMesh (ObjDocument document) {
        Mesh m = new Mesh();

        // .obj models are organised into mesh subgroups, however,
        // the vertex index numbering is continuous.
        for (ObjGroup g : document.subgroups()) {

            // Read in 3 vertices each time and create a triangle face.
            for (int k = 2, kMax = g.getVertexCount(); k < kMax; k += 3) {
                // Vertex 1
                Vertex v1 = new Vertex(document.getPosition(g.getPositionIndex(k - 2)));

                if (document.hasNormals()) {
                    v1.normal = document.getNormal(g.getNormalIndex(k - 2));
                }

                if (document.hasTexCoords()) {
                    v1.texCoords = document.getTexCoord(g.getTexCoordIndex(k - 2));
                }

                // Vertex 2
                Vertex v2 = new Vertex(document.getPosition(g.getPositionIndex(k - 1)));

                if (document.hasNormals()) {
                    v2.normal = document.getNormal(g.getNormalIndex(k - 1));
                }

                if (document.hasTexCoords()) {
                    v2.texCoords = document.getTexCoord(g.getTexCoordIndex(k - 1));
                }

                // Vertex 3
                Vertex v3 = new Vertex(document.getPosition(g.getPositionIndex(k)));

                if (document.hasNormals()) {
                    v3.normal = document.getNormal(g.getNormalIndex(k));
                }

                if (document.hasTexCoords()) {
                    v3.texCoords = document.getTexCoord(g.getTexCoordIndex(k));
                }

                m.addVert(v1);
                m.addVert(v2);
                m.addVert(v3);
                m.addFace(k - 2, k - 1, k);
            }
        }

        return m;
    }

    /**
     * Convert a .obj document to a Mesh object.
     *
     * @param filename Location of obj
     */
    public static Mesh objDocumentToMesh (final String filename) {
        ObjDocument doc = new ObjDocument(filename);

        return objDocumentToMesh(doc);
    }

}
