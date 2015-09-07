package sge.math;

import org.junit.Test;

import sge.math.MVector2;
import static org.junit.Assert.assertEquals;

public class MVector2_Test {

    @Test
    public void testLength () {
        assertEquals(5.0f, new MVector2(3.0f, 4.0f).getLength(), 0f);
    }

    @Test
    public void testsetMag () {
        assertEquals(5.0f, new MVector2(0.0f, 6.0f).setLength(5.0f).getLength(), 0f);
        assertEquals(5.0f, new MVector2(2.0f, 3.0f).setLength(5.0f).getLength(), 0f);
        assertEquals(5.0f, new MVector2(1.2f, 10.5f).setLength(5.0f).getLength(), 0f);
    }

    @Test
    public void testClampLength () {
        assertEquals(5.0f, new MVector2(3.0f, 12.0f).clampLength(5.0f).getLength(), 0f);
    }

    @Test
    public void testAdd () {
        MVector2 vec = new MVector2(1.0f, 1.0f);
        assertEquals(new MVector2(3.0f, 4.0f), vec.add(new MVector2(2.0f, 3.0f)));
    }

    @Test
    public void testSub () {
        MVector2 vec = new MVector2(1.0f, 1.0f);
        assertEquals(new MVector2(0.0f, 0.0f), vec.sub(new MVector2(1.0f, 1.0f)));
    }

    @Test
    public void testNegate () {
        MVector2 vec = new MVector2(1.0f, 1.0f);
        assertEquals(new MVector2(-1.0f, -1.0f), vec.negate());
    }

    @Test
    public void testScale () {
        MVector2 vec = new MVector2(1.0f, 1.0f);
        assertEquals(new MVector2(2.0f, 2.0f), vec.scale(2));
    }

    @Test
    public void testDot () {
        MVector2 xyz = new MVector2(3.0f, 5.0f);
        MVector2 norm = new MVector2(0, 1.0f);

        assertEquals(5.0f, xyz.dot(norm), 0);
    }
}
