package sge.bounds;

import org.junit.Assert;
import org.junit.Test;
import sge.math.Vector2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Axis Aligned Bounding Box.
 */
public class Circle_Test {

    // Basic Circles
    //     8       .......
    //                    ....              .....
    //     7              D   ...          .  C  .
    //                           ..         .....
    //     6                     .......
    //                        ...    .  ...
    //     5          ........        ..   ..
    //              ..     . ..         .    .
    //     4       .       .   .        .    .
    //            .       .     .        .    .
    //     3      .   A   .     .     B   .   .
    //            .       .     .         .   .
    //     2       .       .   .          .  .
    //              ..     . ..           .  .
    //     1          ........            ...
    //                        ...       ...
    //     0    1    2    3    4    5    6    7    8
    //
    final Circle A = new Circle(new Vector2(3.0f, 3.0f), 1.0f);
    final Circle B = new Circle(new Vector2(5.0f, 3.0f), 2.0f);
    final Circle C = new Circle(new Vector2(7.0f, 7.0f), 0.5f);
    final Circle D = new Circle(new Vector2(2.0f, 3.0f), 4.0f);

    @Test
    public void intersects () throws Exception {
        assertTrue(A.intersects(B));
        assertTrue(B.intersects(A));
        assertTrue(B.intersects(D));
        assertTrue(D.intersects(B));
        assertTrue(B.intersects(A));
        assertTrue(A.intersects(D));

        assertFalse(A.intersects(C));
        assertFalse(C.intersects(D));
    }

    @Test
    public void containsPoint () throws Exception {
        assertTrue(A.contains(new Vector2(2.0f, 3.0f)));
        assertTrue(C.contains(new Vector2(6.8f, 7.0f)));

        assertFalse(B.contains(new Vector2(2.0f, 6.0f)));
    }

    @Test
    public void containsCircle () throws Exception {
        assertTrue(D.contains(A));
        assertFalse(D.contains(C));
        assertFalse(D.contains(B));
        assertFalse(A.contains(D));
    }

    final Rectangle R = new Rectangle(2.0f, 0.0f, 4.0f, 2.0f);
    final Rectangle S = new Rectangle(4.0f, 3.0f, 5.0f, 8.0f);

    @Test
    public void containsRect () throws Exception {
        assertTrue(A.intersects(R));
        assertTrue(D.contains(R));
        assertFalse(D.contains(S));
    }
}
