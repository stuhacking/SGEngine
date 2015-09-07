package sge.math;

import org.junit.Test;

import sge.math.Vector3;
import static org.junit.Assert.assertEquals;

public class Vector3_Test {

    @Test
    public void testLength () {
        assertEquals(5.0f, new Vector3(3.0f, 4.0f, 0.0f).getLength(), 0f);
    }

    @Test
    public void testsetMag () {
        assertEquals(5.0f, new Vector3(0.0f, 6.0f, 0.0f).setLength(5.0f).getLength(), 0f);
        assertEquals(5.0f, new Vector3(2.0f, 3.0f, 1.0f).setLength(5.0f).getLength(), 0f);
        assertEquals(5.0f, new Vector3(1.2f, 10.3f, 2.3f).setLength(5.0f).getLength(), 0f);
    }

    @Test
    public void testLimit () {
        assertEquals(5.0f, new Vector3(3.0f, 12.0f, 7.0f).clampLength(5.0f).getLength(), 0f);
    }

    @Test
    public void testAdd () {
        Vector3 vec = new Vector3(1.0f, 1.0f, 1.0f);
        assertEquals(new Vector3(3.0f, 4.0f, 5.0f), vec.add(new Vector3(2.0f, 3.0f, 4.0f)));
    }

    @Test
    public void testSub () {
        Vector3 vec = new Vector3(1.0f, 1.0f, 1.0f);
        assertEquals(new Vector3(0.0f, 0.0f, 0.0f), vec.sub(new Vector3(1.0f, 1.0f, 1.0f)));
    }

    @Test
    public void testNegate () {
        Vector3 vec = new Vector3(1.0f, 1.0f, 1.0f);
        assertEquals(new Vector3(-1.0f, -1.0f, -1.0f), vec.negate());
    }

    @Test
    public void testScale () {
        Vector3 vec = new Vector3(1.0f, 1.0f, 1.0f);
        assertEquals(new Vector3(2.0f, 2.0f, 2.0f), vec.scale(2));
    }

    @Test
    public void testDot () {
        Vector3 xyz = new Vector3(3.0f, 5.0f, 4.0f);
        Vector3 norm = new Vector3(0.0f, 1.0f, 0.0f);

        assertEquals(5.0f, xyz.dot(norm), 0);
    }
}
