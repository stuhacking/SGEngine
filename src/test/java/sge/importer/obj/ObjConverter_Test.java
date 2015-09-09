package sge.importer.obj;

import org.junit.Test;
import sge.geometry.Mesh;

import static org.junit.Assert.assertEquals;

/**
 * Created by shacking on 09/09/15.
 */
public class ObjConverter_Test {

    @Test
    public void cube () throws Exception {
        Mesh m = ObjConverter.objDocumentToMesh("src/test/data/cube.obj");

        assertEquals(12, m.getFaceCount());
        assertEquals(36, m.getVertexCount());
    }
}
