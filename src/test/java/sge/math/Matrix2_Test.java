package sge.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Matrix2_Test {

    @Test
    public void constructors () throws Exception {
        Matrix2 test = new Matrix2(1.0f, 2.0f, 3.0f, 4.0f);
        Matrix2 m = new Matrix2();

        assertTrue(m.isIdentity());

        m = new Matrix2(Matrix2.IDENTITY);

        assertTrue(m.isIdentity());

        m = new Matrix2(1.0f, 2.0f, 3.0f, 4.0f);

        assertEquals(test, m);

        m = new Matrix2(new float[][]{{1.0f, 2.0f}, {3.0f, 4.0f}});

        assertEquals(test, m);

        m = new Matrix2(new float[]{1.0f, 2.0f, 3.0f, 4.0f, 5.0f});

        assertEquals(test, m);
    }

    @Test
    public void zero () throws Exception {
        Matrix2 m = new Matrix2(1.0f, 2.0f, 3.0f, 4.0f);

        m.zero_();

        assertEquals(Matrix2.ZERO, m);
    }

    @Test
    public void identity () throws Exception {
        Matrix2 m = new Matrix2(1.0f, 2.0f, 3.0f, 4.0f);

        m.identity_();

        assertEquals(Matrix2.IDENTITY, m);
    }

    @Test
    public void transpose () throws Exception {
        Matrix2 m = new Matrix2(1.0f, 2.0f, 3.0f, 4.0f);
        Matrix2 n = new Matrix2(1.0f, 3.0f, 2.0f, 4.0f);

        assertEquals(n, m.transpose());

        m.transpose_();

        assertEquals(n, m);
    }
}
