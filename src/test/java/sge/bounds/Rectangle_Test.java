package sge.bounds;

import org.junit.Assert;
import org.junit.Test;

import sge.bounds.Circle;
import sge.bounds.Rectangle;
import sge.math.Vector2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Axis Aligned Bounding Box.
 */
public class Rectangle_Test {

    // Basic Tests
    //     8                        +--------------+
    //                              |              |
    //     7              +----+    |    +----+    |
    //                    |    |    |    |    |    |
    //     6    +----+    F----+----+    H----+    |
    //          |    |         |    |              |
    //     5    E----+         |    G--------------+
    //                         |    |
    //     4         +---------+----+---------+
    //               |         |    |         |
    //     3    +----+----+    |    |    +----+----+
    //          |    |    |    |    |    |    |    |
    //     2    |    B----+----+----+----+----+    |
    //          |         |    |    |    |         |
    //     1    A---------+    C----+    D---------+
    //
    //     0    1    2    3    4    5    6    7    8
    //
    final Rectangle A = new Rectangle(1.0f, 1.0f, 3.0f, 3.0f);
    final Rectangle B = new Rectangle(2.0f, 2.0f, 7.0f, 4.0f);
    final Rectangle C = new Rectangle(4.0f, 1.0f, 5.0f, 6.0f);
    final Rectangle D = new Rectangle(6.0f, 1.0f, 8.0f, 3.0f);
    final Rectangle E = new Rectangle(1.0f, 5.0f, 2.0f, 6.0f);
    final Rectangle F = new Rectangle(3.0f, 6.0f, 4.0f, 7.0f);
    final Rectangle G = new Rectangle(5.0f, 5.0f, 8.0f, 8.0f);
    final Rectangle H = new Rectangle(6.0f, 6.0f, 7.0f, 7.0f);

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
        Assert.assertEquals(new Vector2(2.0f, 2.0f), A.getCenter());
        assertEquals(new Vector2(4.5f, 3.0f), B.getCenter());
    }

    @Test
    public void getArea () throws Exception {
        assertEquals(4.0f, A.getArea(), 0.0f);
        assertEquals(9.0f, G.getArea(), 0.0f);
        assertEquals(5.0f, C.getArea(), 0.0f);
        assertEquals(1.0f, E.getArea(), 0.0f);

    }

    @Test
    public void containsPoint () throws Exception {
        assertTrue(A.contains(new Vector2(2.0f, 2.0f)));
        assertTrue(C.contains(new Vector2(5.0f, 6.0f)));

        assertFalse(E.contains(new Vector2(0.0f, 0.0f)));
    }

    @Test
    public void containsRect () throws Exception {
        assertTrue(G.contains(H));

        assertFalse(H.contains(G));
        assertFalse(A.contains(B));
    }

    @Test
    public void expand () throws Exception {
        Rectangle rect = E.expand(3.0f);

        assertTrue(rect.intersects(F));
    }

    @Test
    public void intersection () throws Exception {
        assertEquals(new Rectangle(2.0f, 2.0f, 3.0f, 3.0f), A.intersect(B));
        assertEquals(new Rectangle(4.0f, 2.0f, 5.0f, 4.0f), B.intersect(C));
    }

    @Test
    public void fromPoints () throws Exception {
        Rectangle r = new Rectangle();

        r.fromPoints(new Vector2[]{new Vector2(2.0f, 3.0f), new Vector2(5.0f, 3.0f)});

        assertEquals(0.0f, r.getArea(), 0.0f);

        r.fromPoints(new Vector2[]{new Vector2(2.0f, 3.0f), new Vector2(5.0f, 3.0f), new Vector2(4.0f, 5.0f)});

        assertEquals(6.0f, r.getArea(), 0.0f);
    }

    // Circle Tests
    //     8
    //
    //     7
    //
    //     6    +-------------------+
    //          |                   |
    //     5    |               ----+----
    //          |            --/    |    \--
    //     4    I-----------/-------+       \-
    //            ------- /                   \
    //     3    -/       \+        C1         |
    //         /          \\                  /
    //     2   |     C2   +\               /-
    //         \           / --\         /--
    //     1    -\       /-     ---------
    //            -------
    //     0    1    2    3    4    5    6    7    8
    //
    final Rectangle I = new Rectangle(1.0f, 4.0f, 5.0f, 6.0f);
    final Circle C1 = new Circle(new Vector2(5.0f, 3.0f), 3.0f);
    final Circle C2 = new Circle(new Vector2(2.0f, 2.0f), 1.0f);

    @Test
    public void intersectionWithCircle () {
        assertTrue(I.intersects(C1));

        assertFalse(I.intersects(C2));
    }

    @Test
    public void shouldExpandToIncludeAddedPoint () {
        Rectangle rect = new Rectangle();

        Vector2 v = new Vector2(5.0f, 5.0f);
        rect.addPoint_(v);

        assertTrue(rect.contains(v));
        assertEquals("Area of 5x5 bounds should be 25", 25.0f, rect.getArea(), 0f);
    }

    @Test
    public void shouldExpandToIncludeSubsequentAddedPoints () {
        Rectangle rect = new Rectangle();

        Vector2 v1 = new Vector2(1.0f, 4.0f);
        Vector2 v2 = new Vector2(-2.0f, -2.0f);

        rect.addPoint_(v1);
        rect.addPoint_(v2);

        assertTrue(rect.contains(v1));
        assertTrue(rect.contains(v2));
        assertEquals("Area of 3x6 bounds should be 18", 18.0f, rect.getArea(), 0f);
    }


    @Test
    public void shouldIntersectTranslatedRectangle () throws Exception {
        Rectangle r1 = E.translate(new Vector2(2.0f, 1.0f));

        assertTrue(r1.intersects(F));
    }
}
