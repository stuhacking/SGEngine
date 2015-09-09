package sge.importer.obj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObjDocument_Test {

    @Test
    public void untitledObj () throws Exception {
        ObjDocument doc = new ObjDocument("src/test/data/basic.obj");

        assertEquals("untitled_obj", doc.getName());
        assertEquals(3, doc.getSize());
    }

    @Test
    public void simpleObj () throws Exception {
        ObjDocument doc = new ObjDocument("src/test/data/triangle.obj");

        assertEquals("SimpleTriangle", doc.getName());
        assertEquals(3, doc.getSize());
    }

    @Test
    public void cube () throws Exception {
        ObjDocument doc = new ObjDocument("src/test/data/cube.obj");

        assertEquals("cube", doc.getName());
        assertEquals(8, doc.getSize());
        assertEquals(12, doc.getFaceCount());
    }
}
