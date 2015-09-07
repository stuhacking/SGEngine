package sge.math;

import org.junit.Test;

import sge.math.MVector3;
import static org.junit.Assert.assertEquals;

public class MVector3_Test {

    @Test
    public void testLength () {
        assertEquals(5.0f, new MVector3(3.0f, 4.0f, 0.0f).getLength(), 0f);
    }

    @Test
    public void testsetMag () {
        assertEquals(5.0f, new MVector3(0.0f, 6.0f, 0.0f).setLength(5.0f).getLength(), 0f);
        assertEquals(5.0f, new MVector3(2.0f, 3.0f, 1.0f).setLength(5.0f).getLength(), 0f);
        assertEquals(5.0f, new MVector3(1.2f, 10.3f, 2.3f).setLength(5.0f).getLength(), 0f);
    }

    @Test
    public void testLimit () {
        assertEquals(5.0f, new MVector3(3.0f, 12.0f, 7.0f).clampLength(5.0f).getLength(), 0f);
    }

    @Test
    public void testAdd () {
        MVector3 vec = new MVector3(1.0f, 1.0f, 1.0f);
        assertEquals(new MVector3(3.0f, 4.0f, 5.0f), vec.add(new MVector3(2.0f, 3.0f, 4.0f)));
    }

    @Test
    public void testSub () {
        MVector3 vec = new MVector3(1.0f, 1.0f, 1.0f);
        assertEquals(new MVector3(0.0f, 0.0f, 0.0f), vec.sub(new MVector3(1.0f, 1.0f, 1.0f)));
    }

    @Test
    public void testNegate () {
        MVector3 vec = new MVector3(1.0f, 1.0f, 1.0f);
        assertEquals(new MVector3(-1.0f, -1.0f, -1.0f), vec.negate());
    }

    @Test
    public void testScale () {
        MVector3 vec = new MVector3(1.0f, 1.0f, 1.0f);
        assertEquals(new MVector3(2.0f, 2.0f, 2.0f), vec.scale(2));
    }

    @Test
    public void testDot () {
        MVector3 xyz = new MVector3(3.0f, 5.0f, 4.0f);
        MVector3 norm = new MVector3(0.0f, 1.0f, 0.0f);

        assertEquals(5.0f, xyz.dot(norm), 0);
    }
}
