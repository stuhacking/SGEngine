package sge.bounds;

import org.junit.Assert;
import org.junit.Test;

import sge.bounds.AABB;
import sge.math.Vector3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Axis Aligned Bounding Box.
 */
public class AABB_Test {

    // Basic Tests
    //     8                        +--------------5
    //                              |              |
    //     7              +----7    |    +----4    |
    //                    |    |    |    |    |    |
    //     6    +----4   F5----+----5   H3----+    |
    //          |    |         |    |              |
    //     5   E3----+         |   G2--------------+
    //                         |    |
    //     4         +---------+----+---------7
    //               |         |    |         |
    //     3    +----+----3    |    |    +----+----8
    //          |    |    |    |    |    |    |    |
    //     2    |   B2----+----+----+----+----+    |
    //          |         |    |    |    |         |
    //     1   A1---------+   C3----+   D5---------+
    //
    //     0    1    2    3    4    5    6    7    8
    //
    final AABB A = new AABB(1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 3.0f);
    final AABB B = new AABB(2.0f, 2.0f, 2.0f, 7.0f, 4.0f, 7.0f);
    final AABB C = new AABB(4.0f, 1.0f, 3.0f, 5.0f, 6.0f, 5.0f);
    final AABB D = new AABB(6.0f, 1.0f, 5.0f, 8.0f, 3.0f, 8.0f);
    final AABB E = new AABB(1.0f, 5.0f, 3.0f, 2.0f, 6.0f, 4.0f);
    final AABB F = new AABB(3.0f, 6.0f, 5.0f, 4.0f, 7.0f, 7.0f);
    final AABB G = new AABB(5.0f, 5.0f, 2.0f, 8.0f, 8.0f, 5.0f);
    final AABB H = new AABB(6.0f, 6.0f, 3.0f, 7.0f, 7.0f, 4.0f);

    @Test
    public void intersects () throws Exception {
        assertTrue(A.intersects(A));
        assertTrue(A.intersects(B));
        assertTrue(B.intersects(A));
        assertTrue(B.intersects(C));
        assertTrue(C.intersects(B));
        assertTrue(B.intersects(D));
        assertTrue(F.intersects(C));
        assertTrue(C.intersects(F));
        assertTrue(C.intersects(G));
        assertTrue(G.intersects(H));
        assertTrue(H.intersects(G));

        assertFalse(E.intersects(A));
        assertFalse(A.intersects(E));
    }

    @Test
    public void getCentre () throws Exception {
        Assert.assertEquals(new Vector3(2.0f, 2.0f, 2.0f), A.getCenter());
        assertEquals(new Vector3(4.5f, 3.0f, 4.5f), B.getCenter());
    }

    @Test
    public void getArea () throws Exception {
        assertEquals(8.0f, A.getVolume(), 0.0f);
        assertEquals(27.0f, G.getVolume(), 0.0f);
        assertEquals(10.0f, C.getVolume(), 0.0f);
        assertEquals(1.0f, E.getVolume(), 0.0f);

    }

    @Test
    public void containsPoint () throws Exception {
        assertTrue(A.contains(new Vector3(2.0f, 2.0f, 2.0f)));
        assertTrue(C.contains(new Vector3(5.0f, 6.0f, 5.0f)));

        assertFalse(E.contains(new Vector3(0.0f, 0.0f, 0.0f)));
    }

    @Test
    public void containsRect () throws Exception {
        assertTrue(G.contains(H));

        assertFalse(H.contains(G));
        assertFalse(A.contains(B));
    }

    @Test
    public void shouldExpandToIncludeAddedPoint () {
        AABB aabb = new AABB();

        Vector3 v = new Vector3(5.0f, 5.0f, 5.0f);
        aabb.addPoint_(v);

        assertTrue(aabb.contains(v));
        assertEquals("Volume of 5x5x5 bounds should be 125", 125.0f, aabb.getVolume(), 0f);
    }

    @Test
    public void shouldExpandToIncludeSubsequentAddedPoints () {
        AABB aabb = new AABB();

        Vector3 v1 = new Vector3(1.0f, 4.0f, 2.0f);
        Vector3 v2 = new Vector3(-2.0f, -2.0f, -2.0f);

        aabb.addPoint_(v1);
        aabb.addPoint_(v2);

        assertTrue(aabb.contains(v1));
        assertTrue(aabb.contains(v2));
        assertEquals("Volume of 3x6x4 bounds should be 72", 72.0f, aabb.getVolume(), 0f);
    }

    @Test
    public void shouldContainPointsWithinBoundedArea () {
        AABB aabb = new AABB();

        aabb.addPoint_(new Vector3(1.0f, 4.0f, 2.0f));
        aabb.addPoint_(new Vector3(-2.0f, -2.0f, -2.0f));

        assertTrue(aabb.contains(new Vector3(1f, 1f, 1f)));
        assertTrue(aabb.contains(new Vector3(0f, -2f, 0f)));
        assertTrue(aabb.contains(new Vector3(1f, 4f, 2f)));
        assertTrue(aabb.contains(new Vector3(0f, 1f, 1f)));
    }
}
