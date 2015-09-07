package sge.math;

import org.junit.Test;

import sge.math.FMath;
import sge.math.Matrix4;
import sge.math.Vector4;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Matrix4_Test {

    @Test
    public void constructors () throws Exception {
        Matrix4 test = new Matrix4(
                1.0f, 2.0f, 3.0f, 4.0f,
                5.0f, 6.0f, 7.0f, 8.0f,
                9.0f, 10.0f, 11.0f, 12.0f,
                13.0f, 14.0f, 15.0f, 16.0f);
        Matrix4 m = new Matrix4();

        assertTrue(m.isIdentity());

        m = new Matrix4(Matrix4.IDENTITY);

        assertTrue(m.isIdentity());

        m = new Matrix4(1.0f, 2.0f, 3.0f, 4.0f,
                5.0f, 6.0f, 7.0f, 8.0f,
                9.0f, 10.0f, 11.0f, 12.0f,
                13.0f, 14.0f, 15.0f, 16.0f);

        assertEquals(test, m);

        m = new Matrix4(new float[][]{{1.0f, 2.0f, 3.0f, 4.0f},
                {5.0f, 6.0f, 7.0f, 8.0f},
                {9.0f, 10.0f, 11.0f, 12.0f},
                {13.0f, 14.0f, 15.0f, 16.0f}});

        assertEquals(test, m);

        m = new Matrix4(new float[]{1.0f, 2.0f, 3.0f, 4.0f,
                5.0f, 6.0f, 7.0f, 8.0f,
                9.0f, 10.0f, 11.0f, 12.0f,
                13.0f, 14.0f, 15.0f, 16.0f});

        assertEquals(test, m);
    }

    @Test
    public void testIdentitySelfMultiplication () {
        Matrix4 m = Matrix4.IDENTITY.mult(Matrix4.IDENTITY);

        assertTrue(m.isIdentity());
    }

    @Test
    public void testMatrixMultiplyIdentity () {
        Matrix4 m = new Matrix4(2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f, 5.0f,
                                5.0f, 5.0f, 5.0f);

        assertEquals(m, m.mult(Matrix4.IDENTITY));
    }

    @Test
    public void testMatrixMultiply () {
        Matrix4 a = new Matrix4(
                1.0f, 5.0f, 9.0f, 13.0f,
                2.0f, 6.0f, 10.0f, 14.0f,
                3.0f, 7.0f, 11.0f, 15.0f,
                4.0f, 8.0f, 12.0f, 16.0f);

        Matrix4 b = new Matrix4(
                1.0f, 1.0f, 1.0f, 1.0f,
                2.0f, 2.0f, 2.0f, 2.0f,
                3.0f, 3.0f, 3.0f, 3.0f,
                4.0f, 4.0f, 4.0f, 4.0f);

        Matrix4 ans = new Matrix4(
                10.0f, 26.0f, 42.0f, 58.0f,
                20.0f, 52.0f, 84.0f, 116.0f,
                30.0f, 78.0f, 126.0f, 174.0f,
                40.0f, 104.0f, 168.0f, 232.0f);

        assertEquals(ans, a.mult(b));
    }

    @Test
    public void testMatrixEquality () {
        Matrix4 a = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        Matrix4 b = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);

        assertEquals(a, b);
    }

    @Test
    public void testMatrixTranspose () {
        Matrix4 a = new Matrix4(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);

        Matrix4 b = new Matrix4(1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16);

        assertEquals(b, a.transpose());
    }

    @Test
    public void testApplyMatrixOps () {
        Matrix4 mat = new Matrix4();
        Matrix4 mat2 = mat.scale(2).scale(3).translate(1.0f, 1.0f, 0.0f);
        Vector4 vec = new Vector4(1.0f, 1.0f, 1.0f, 1.0f);

        assertFalse(mat.equals(mat2));
        assertEquals(new Vector4(12.0f, 12.0f, 6.0f, 1.0f), mat2.mult(vec));
    }

    @Test
    public void testRotationMats () {
        Matrix4 Rz = Matrix4.IDENTITY.rotateZ(FMath.toRadians(-45.0f));
        Vector4 vec = new Vector4(0.0f, 5.0f, 0.0f, 1.0f);
        Vector4 vecRotated = Rz.mult(vec);

        assertEquals(vec.getLength(), vecRotated.getLength(), 0.0f);
        assertTrue(new Vector4(-3.5f, 3.5f, 0.0f, 1.0f).compare(vecRotated, 0.1f)); // Rough
    }

    @Test
    public void testMatrixDeterminant () {
        assertEquals(1f, Matrix4.IDENTITY.getDeterminant(), 0.0001f);

        Matrix4 A = new Matrix4(2f, 2f, 3f, 5f, 5f, 3f, 3f, 0f, 0f, 4f, 5f, 3f, 3f, 2f, -2f, 1f);
        assertEquals(370f, A.getDeterminant(), 0.0001f);
    }

    @Test
    public void testMatrixInverse () {
        Matrix4 A = new Matrix4(2f, 2f, 3f, 5f, 4f, 3f, 1f, 1f, 4f, 4f, 5f, 3f, 1f, 1f, 1f, 1f);
        Matrix4 B = new Matrix4(1f, 1f, 1f, -9f, -1.5f, -1f, -1.5f, 13f, 0.25f, 0f, 0.75f, -3.5f, 0.25f, 0f,
                                -0.25f, 0.5f);
        assertEquals(B, A.inverse());

        assertEquals(Matrix4.IDENTITY, A.mult(B));
    }
}
