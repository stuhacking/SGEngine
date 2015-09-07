package sge.math;

import org.junit.Test;

import sge.math.Matrix3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Matrix3_Test {

    @Test
    public void constructors () throws Exception {
        Matrix3 test = new Matrix3(
                1.0f, 2.0f, 3.0f,
                4.0f, 5.0f, 6.0f,
                7.0f, 8.0f, 9.0f);
        Matrix3 m = new Matrix3();

        assertTrue(m.isIdentity());

        m = new Matrix3(Matrix3.IDENTITY);

        assertTrue(m.isIdentity());

        m = new Matrix3(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f);

        assertEquals(test, m);

        m = new Matrix3(new float[][]{{1.0f, 2.0f, 3.0f}, {4.0f, 5.0f, 6.0f}, {7.0f, 8.0f, 9.0f}});

        assertEquals(test, m);

        m = new Matrix3(new float[]{1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f});

        assertEquals(test, m);
    }

    @Test
    public void shouldReturnSelfForIdentityXIdentity () {
        Matrix3 m = Matrix3.IDENTITY.mult(Matrix3.IDENTITY);

        assertTrue(m.isIdentity());
    }

    @Test
    public void shouldReturnSelfWhenMultiplyingIdentity () {
        Matrix3 m = new Matrix3(2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f);

        assertEquals(m, m.mult(Matrix3.IDENTITY));

    }

    @Test
    public void shouldMultiplyTwoMatrices () {
        Matrix3 a = new Matrix3(1f, 4f, 7f, 2f, 5f, 8f, 3f, 6f, 9f);

        Matrix3 b = new Matrix3(3f, 2f, 1f, 3f, 2f, 1f, 3f, 2f, 1f).transpose();

        Matrix3 ans = new Matrix3(18f, 12f, 6f, 45f, 30f, 15f, 72f, 48f, 24f).transpose();

        assertEquals(ans, a.mult(b));

    }

    @Test
    public void shouldTestEquality () {
        Matrix3 a = new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Matrix3 b = new Matrix3(1, 2, 3, 4, 5, 6, 7, 8, 9);

        assertEquals(a, b);
    }

    @Test
    public void shouldTranspose () {
        Matrix3 a = new Matrix3(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f);

        Matrix3 b = new Matrix3(1f, 4f, 7f, 2f, 5f, 8f, 3f, 6f, 9f);

        assertEquals(b, a.transpose());
    }

    @Test
    public void testMatrixDeterminant () {
        assertEquals(1f, Matrix3.IDENTITY.getDeterminant(), 0f);
        assertEquals(-13f, new Matrix3(2f, 3f, 5f, 4f, 5f, 2f, 3f, 3f, 2f).getDeterminant(), 0f);
        assertEquals(320f, new Matrix3(4f, 2f, 4f, 4f, 12f, 4f, -5f, 4f, 3f).getDeterminant(), 0f);
        assertEquals(0f, new Matrix3(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f).getDeterminant(), 0f);
    }

    @Test
    public void testMatrixInverse () {
        assertEquals(Matrix3.IDENTITY, Matrix3.IDENTITY.inverse());

        Matrix3 a = new Matrix3(3f, 4f, 2f, 5f, 9f, 2f, 2f, 4f, 2f);
        Matrix3 b = new Matrix3(1f, 0f, -1f, -0.6f, 0.2f, 0.4f, 0.2f, -0.4f, 0.7f);
        assertEquals(b, a.inverse());

        assertEquals(null, new Matrix3(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f).inverse());
    }
}
