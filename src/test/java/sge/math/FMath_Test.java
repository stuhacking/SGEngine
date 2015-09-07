package sge.math;

import org.junit.Test;

import sge.math.FMath;
import static org.junit.Assert.assertEquals;

public class FMath_Test {

    @Test
    public void testClampFloat () {
        assertEquals(3.0f, FMath.clamp(2.0f, 3.0f, 6.0f), 0.0f);
        assertEquals(6.0f, FMath.clamp(8.0f, 3.0f, 6.0f), 0.0f);
        assertEquals(4.0f, FMath.clamp(4.0f, 3.0f, 6.0f), 0.0f);
    }

    @Test
    public void testClampInt () {
        assertEquals(3, FMath.clamp(2, 3, 6), 0);
        assertEquals(6, FMath.clamp(8, 3, 6), 0);
        assertEquals(4, FMath.clamp(4, 3, 6), 0);
    }

    @Test
    public void testClampedLerp () throws Exception {
        assertEquals(6.0f, FMath.clampLerp(4.0f, 6.0f, 1.5f), 0f);
        assertEquals(8.0f, FMath.clampLerp(3.0f, 8.0f, 1.0f), 0f);

        assertEquals(4.0f, FMath.clampLerp(4.0f, 6.0f, 0.0f), 0f);
        assertEquals(5.0f, FMath.clampLerp(5.0f, 10.0f, -1.0f), 0f);
    }

    @Test
    public void testLerp () throws Exception {
        assertEquals(5.0f, FMath.lerp(4.0f, 6.0f, 0.5f), 0f);
        assertEquals(25.0f, FMath.lerp(0.0f, 100.0f, 0.25f), 0f);
        assertEquals(7.0f, FMath.lerp(4.0f, 6.0f, 1.5f), 0f);
    }

    @Test
    public void testLerpWithDescendingRange () throws Exception {
        assertEquals(5.0f, FMath.lerp(6.0f, 4.0f, 0.5f), 0f);
        assertEquals(75.0f, FMath.lerp(100.0f, 0.0f, 0.25f), 0f);
    }

    @Test
    public void testToRatio () throws Exception {
        assertEquals(0.5f, FMath.toRatio(50.0f, 25.0f, 75.0f), 0f);
    }

    @Test
    public void testFit () throws Exception {
        assertEquals(5.0f, FMath.fit(0.5f, 0.0f, 1.0f, 4.0f, 6.0f), 0f);
    }

    @Test
    public void testNearest2Pow () throws Exception {

        assertEquals(FMath.nearest2Pow(5), 8);
        assertEquals(FMath.nearest2Pow(24), 32);
        assertEquals(FMath.nearest2Pow(63), 64);
        assertEquals(FMath.nearest2Pow(513), 1024);

        assertEquals(FMath.nearest2Pow(32), 32);
    }

}
