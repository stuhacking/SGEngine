package sge.math;

import org.junit.Test;

import sge.math.Vector2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Vector2_Test {

    @Test
    public void constructors () throws Exception {
        Vector2 v = new Vector2();

        assertEquals(Vector2.ZERO, v);

        v = new Vector2(2.0f);

        assertEquals(new Vector2(2.0f, 2.0f), v);

        v = new Vector2(3.0f, 4.0f);

        assertEquals(new Vector2(3.0f, 4.0f), v);

        v = new Vector2(new float[]{5.0f, 6.0f});

        assertEquals(new Vector2(5.0f, 6.0f), v);

        v = new Vector2(new MVector2(1.0f, 1.0f));

        assertEquals(Vector2.ONE, v);

        v = new Vector2(Vector2.ZERO);

        assertEquals(Vector2.ZERO, v);
    }

    @Test
    public void testLength () {
        assertEquals(25.0f, new Vector2(3.0f, 4.0f).getLengthSqr(), 0f);
        assertEquals(5.0f, new Vector2(3.0f, 4.0f).getLength(), 0f);
    }

    @Test
    public void testsetMag () {
        assertEquals(5.0f, new Vector2(0.0f, 6.0f).setLength(5.0f).getLength(), 0f);
        assertEquals(5.0f, new Vector2(2.0f, 3.0f).setLength(5.0f).getLength(), 0f);
        assertEquals(5.0f, new Vector2(1.2f, 10.5f).setLength(5.0f).getLength(), 0f);
    }

    @Test
    public void testNormalize () {
        assertEquals(1.0f, new Vector2(0.0f, 6.0f).normalize().getLength(), 0f);
        assertEquals(1.0f, new Vector2(1.2f, 10.5f).normalize().getLength(), 0f);
    }

    @Test
    public void testClampLength () {
        assertEquals(5.0f, new Vector2(3.0f, 12.0f).clampLength(5.0f).getLength(), 0f);

        assertEquals(5.0f, new Vector2(1.0f, 1.0f).clampLength(5.0f, 12.0f).getLength(), 0f);
        assertEquals(12.0f, new Vector2(30.0f, 12.0f).clampLength(5.0f, 12.0f).getLength(), 0f);
    }

    @Test
    public void testClamp () throws Exception {
        Vector2 min = new Vector2(5.0f, 5.0f);
        Vector2 max = new Vector2(8.0f, 7.0f);
        assertEquals(new Vector2(5.0f, 5.0f), new Vector2(1.0f, 1.0f).clamp(min, max));
        assertEquals(new Vector2(6.0f, 5.0f), new Vector2(6.0f, 1.0f).clamp(min, max));
        assertEquals(new Vector2(8.0f, 7.0f), new Vector2(10.0f, 12.0f).clamp(min, max));
        assertEquals(new Vector2(6.5f, 6.0f), new Vector2(6.5f, 6.0f).clamp(min, max));
    }

    @Test
    public void testAdd () {
        Vector2 vec = new Vector2(1.0f, 1.0f);
        assertEquals(new Vector2(3.0f, 4.0f), vec.add(new Vector2(2.0f, 3.0f)));
    }

    @Test
    public void testSub () {
        Vector2 vec = new Vector2(1.0f, 1.0f);
        assertEquals(new Vector2(0.0f, 0.0f), vec.sub(new Vector2(1.0f, 1.0f)));
    }

    @Test
    public void testMult () {
        Vector2 vec = new Vector2(1.0f, 1.0f);
        assertEquals(new Vector2(2.0f, 0.0f), vec.mult(new Vector2(2.0f, 0.0f)));
    }

    @Test
    public void testNegate () {
        Vector2 vec = new Vector2(1.0f, 1.0f);
        assertEquals(new Vector2(-1.0f, -1.0f), vec.negate());
    }

    @Test
    public void testScale () {
        Vector2 vec = new Vector2(1.0f, 1.0f);
        assertEquals(new Vector2(2.0f, 2.0f), vec.scale(2.0f));
    }

    @Test
    public void testDiv() {
        Vector2 vec = new Vector2(2.0f, 3.0f);
        assertEquals(new Vector2(1.0f, 1.5f), vec.div(2));
    }

    @Test
    public void testMirror () throws Exception {
        Vector2 M1 = new Vector2(0.0f, 1.0f);
        Vector2 M2 = new Vector2(1.0f, 1.0f).normalize();

        assertEquals(new Vector2(-0.5f, 1.0f), new Vector2(0.5f, 1.0f).mirror(M1));
        assertTrue(new Vector2(0.0f, 1.0f).compare(new Vector2(1.0f, 0.0f).mirror(M2), 0.000001f));
    }

    @Test
    public void testDot () {
        Vector2 xyz = new Vector2(3.0f, 5.0f);
        Vector2 norm = new Vector2(0, 1.0f);

        assertEquals(5.0f, xyz.dot(norm), 0.0f);
    }

}
