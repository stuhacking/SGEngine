package sge.math;

import org.junit.Test;

import sge.math.Quaternion;
import static org.junit.Assert.assertEquals;

public class Quaternion_Test {

    @Test
    public void testConjugate () {
        Quaternion q = new Quaternion(2f, 3f, 4f, 1f);

        assertEquals(q, q.conjugate().conjugate());
    }

    @Test
    public void testConjugateObeysDeMorgan () {
        Quaternion q = new Quaternion(2f, 3f, 4f, 1f);
        Quaternion r = new Quaternion(3f, 4f, 2f, 1f);

        Quaternion a = q.add(r).conjugate();
        Quaternion b = q.conjugate().add(r.conjugate());

        assertEquals(a, b);
    }

}
