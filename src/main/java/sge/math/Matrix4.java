package sge.math;

/**
 * 4 Dimensional Col-Major Matrix
 *
 *     0 1  2  3
 *   +----------
 *  x| 0 4  8 12
 *  y| 1 5  9 13
 *  z| 2 6 10 14
 *  w| 3 7 11 15
 */
public final class Matrix4 {

    public static final int SIZE = 16;
    public static final int BYTE_SIZE = SIZE * FMath.FLOAT_SIZE;

    public static final Matrix4 ZERO = new Matrix4(
            0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f);

    public static final Matrix4 ONE = new Matrix4(
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f);

    public static final Matrix4 IDENTITY = new Matrix4(
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f);

    /** Matrix Data */
    public final float[] mat = {
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f};

    /** Default Constructor. */
    public Matrix4 () {}

    /** Column major Vector constructor. */
    public Matrix4 (final Vector4 a, final Vector4 b, final Vector4 c, final Vector4 d) {
        mat[0] = a.x;
        mat[1] = a.y;
        mat[2] = a.z;
        mat[3] = a.w;
        mat[4] = b.x;
        mat[5] = b.y;
        mat[6] = b.z;
        mat[7] = b.w;
        mat[8] = c.x;
        mat[9] = c.y;
        mat[10] = c.z;
        mat[11] = c.w;
        mat[12] = d.x;
        mat[12] = d.y;
        mat[14] = d.z;
        mat[15] = d.w;
    }

    /** Value Constructor. */
    public Matrix4 (
            final float a1, final float b1, final float c1, final float d1,
            final float a2, final float b2, final float c2, final float d2,
            final float a3, final float b3, final float c3, final float d3,
            final float a4, final float b4, final float c4, final float d4) {
        mat[0] = a1;
        mat[1] = b1;
        mat[2] = c1;
        mat[3] = d1;
        mat[4] = a2;
        mat[5] = b2;
        mat[6] = c2;
        mat[7] = d2;
        mat[8] = a3;
        mat[9] = b3;
        mat[10] = c3;
        mat[11] = d3;
        mat[12] = a4;
        mat[13] = b4;
        mat[14] = c4;
        mat[15] = d4;
    }

    /** Array Constructor. */
    public Matrix4 (final float[][] data) {
        for (int i = 0, iMax = data.length; i < iMax; i++) {
            int idx = i * 4;
            mat[idx] = data[i][0];
            mat[idx + 1] = data[i][1];
            mat[idx + 2] = data[i][2];
            mat[idx + 3] = data[i][3];
        }
    }

    /**
     * 1D Array Constructor. Write the contents of data into the columns
     * of this Matrix4 until we run out of data, or space.
     */
    public Matrix4 (final float[] data) {
        for (int i = 0, iMax = Math.min(mat.length, data.length); i < iMax; i++) {
            mat[i] = data[i];
        }
    }

    /** Copy Constructor. */
    public Matrix4 (final Matrix4 other) {
        for (int i = 0; i < 16; i++) {
            mat[i] = other.mat[i];
        }
    }

    public float get (final int col, final int row) {
        return mat[col * 4 + row];
    }

    public Vector4 row (final int row) {
        return new Vector4(mat[row], mat[row + 4], mat[row + 8], mat[row + 12]);
    }

    public Vector4 column (final int col) {
        int offset = col * 4;
        return new Vector4(mat[offset], mat[offset + 1], mat[offset + 2], mat[offset + 3]);
    }

    /**
     * Write the column data into 4 Vectors.
     */
    public void columns (final MVector4 a, final MVector4 b, final MVector4 c, final MVector4 d) {
        a.x = mat[0];
        a.y = mat[1];
        a.z = mat[2];
        a.w = mat[3];
        b.x = mat[4];
        b.y = mat[5];
        b.z = mat[6];
        b.w = mat[7];
        c.x = mat[8];
        c.y = mat[9];
        c.z = mat[10];
        c.w = mat[11];
        d.x = mat[12];
        d.y = mat[13];
        d.z = mat[14];
        d.w = mat[15];
    }

    /**
     * Set the values of this Matrix4 in place.
     * Destructive.
     */
    public void set_ (final float a1, final float b1, final float c1, final float d1,
                      final float a2, final float b2, final float c2, final float d2,
                      final float a3, final float b3, final float c3, final float d3,
                      final float a4, final float b4, final float c4, final float d4) {
        mat[0] = a1;
        mat[1] = b1;
        mat[2] = c1;
        mat[3] = d1;
        mat[4] = a2;
        mat[5] = b2;
        mat[6] = c2;
        mat[7] = d2;
        mat[8] = a3;
        mat[9] = b3;
        mat[10] = c3;
        mat[11] = d3;
        mat[12] = a4;
        mat[13] = b4;
        mat[14] = c4;
        mat[15] = d4;
    }

    /**
     * Set all the values of this Matrix4 using another Matrix4.
     * Destructive.
     *
     * @param m
     */
    public void set_ (final Matrix4 m) {
        System.arraycopy(m.mat, 0, mat, 0, SIZE);
    }

    /**
     * Set all the values of this Matrix4 using a float[]. Write the contents of
     * data into the columns of this Matrix4 until we run out of data, or space.
     * Destructive.
     *
     * @param data
     */
    public void set_ (final float[] data) {
        System.arraycopy(data, 0, mat, 0, Math.min(SIZE, data.length));
    }

    /**
     * Set all the values of this Matrix4 using a float[][]. Data must be in the order[4][4].
     * Destructive.
     *
     * @param data
     */
    public void set_ (final float[][] data) {
        for (int i = 0, iMax = data.length; i < iMax; i++) {
            int idx = i * 4;
            mat[idx] = data[i][0];
            mat[idx + 1] = data[i][1];
            mat[idx + 2] = data[i][2];
            mat[idx + 3] = data[i][3];
        }
    }

    /**
     * Set the value of a single cell in this Matrix4 in place.
     * Destructive.
     *
     * @param col Column Index
     * @param row Row Index
     * @param value New value for cell(col, row).
     */
    public void set_ (final int col, final int row, final float value) {
        mat[col * 4 + row] = value;
    }

    /**
     * Set the elements of this Matrix4 to be 0.0f.
     * Destructive.
     */
    public void zero_ () {
        for (int i = 0; i < SIZE; i++) {
            mat[i] = 0f;
        }
    }

    /**
     * Set the elements of this Matrix4 to be the identity Matrix.
     * Destructive.
     */
    public void identity_ () {
        set_(IDENTITY);
    }

    /**
     * Test if this Matrix is the identity matrix.
     *
     * @return true if this Matrix is the identity matrix, otherwise false.
     */
    public boolean isIdentity () {
        return IDENTITY.compare(this);
    }

    /**
     * Find the determinant of this Matrix4.
     *
     * @return Matrix determinant.
     */
    public float getDeterminant () {
        // Laplace expansion method:
        // Sub-Determinants
        Matrix3 A = new Matrix3(mat[5], mat[9], mat[13],
                                mat[6], mat[10], mat[14],
                                mat[7], mat[11], mat[15]);

        Matrix3 B = new Matrix3(mat[4], mat[8], mat[12],
                                mat[6], mat[10], mat[14],
                                mat[7], mat[11], mat[15]);

        Matrix3 C = new Matrix3(mat[4], mat[8], mat[12],
                                mat[5], mat[9], mat[13],
                                mat[7], mat[11], mat[15]);

        Matrix3 D = new Matrix3(mat[4], mat[8], mat[12],
                                mat[5], mat[9], mat[13],
                                mat[6], mat[10], mat[14]);

        return mat[0] * A.getDeterminant()
                - mat[1] * B.getDeterminant()
                + mat[2] * C.getDeterminant()
                - mat[3] * D.getDeterminant();
    }

    /**
     * Return a new Matrix4 which is the transpose of this Matrix4.
     *
     * @return Result of Matrix transposition
     */
    public Matrix4 transpose () {
        return new Matrix4(
                mat[0], mat[4], mat[8], mat[12],
                mat[1], mat[5], mat[9], mat[13],
                mat[2], mat[6], mat[10], mat[14],
                mat[3], mat[7], mat[11], mat[15]);
    }

    private final void swapIndices (final int index1, final int index2) {
        float temp = mat[index2];
        mat[index2] = mat[index1];
        mat[index1] = temp;
    }

    /**
     * Transpose the elements of this Matrix4 in place.
     * Destructive
     */
    public Matrix4 transpose_ () {
        swapIndices(1, 4);
        swapIndices(2, 8);
        swapIndices(3, 12);
        swapIndices(7, 13);
        swapIndices(11, 14);
        swapIndices(6, 9);

        return this;
    }

    /**
     * Determine if this Matrix has an inverse.
     *
     * @return true if the Matrix has an inverse, otherwise false.
     */
    public boolean hasInverse () {
        return 0 != getDeterminant();
    }

    /**
     * Return a new Matrix4 which is the inverse of this Matrix4.
     *
     * @return Inverse of this Matrix, or null if there is no inverse.
     */
    public Matrix4 inverse () {
        Matrix4 inv = new Matrix4(this);

        return inv.inverse_() ? inv : null;
    }

    /**
     * Update this Matrix to be the Inverse of itself.
     * Destructive
     *
     * @return Flag indicating if the inversion operation was successful, or
     *   that the Matrix4 has no possible inverse.
     */
    public boolean inverse_ () {
        float det = getDeterminant();

        if (0 == det) {
            return false;
        };

        // Sub Determinants
        float a = new Matrix3(mat[5], mat[6], mat[7], mat[9], mat[10], mat[11], mat[13], mat[14], mat[15]).getDeterminant();
        float b = new Matrix3(mat[4], mat[6], mat[7], mat[8], mat[10], mat[11], mat[12], mat[14], mat[15]).getDeterminant();
        float c = new Matrix3(mat[4], mat[5], mat[7], mat[8], mat[9], mat[11], mat[12], mat[13], mat[15]).getDeterminant();
        float d = new Matrix3(mat[4], mat[5], mat[6], mat[8], mat[9], mat[10], mat[12], mat[13], mat[14]).getDeterminant();

        float e = new Matrix3(mat[1], mat[2], mat[3], mat[9], mat[10], mat[11], mat[13], mat[14], mat[15]).getDeterminant();
        float f = new Matrix3(mat[0], mat[2], mat[3], mat[8], mat[10], mat[11], mat[12], mat[14], mat[15]).getDeterminant();
        float g = new Matrix3(mat[0], mat[1], mat[3], mat[8], mat[9], mat[11], mat[12], mat[13], mat[15]).getDeterminant();
        float h = new Matrix3(mat[0], mat[1], mat[2], mat[8], mat[9], mat[10], mat[12], mat[13], mat[14]).getDeterminant();

        float i = new Matrix3(mat[1], mat[2], mat[3], mat[5], mat[6], mat[7], mat[13], mat[14], mat[15]).getDeterminant();
        float j = new Matrix3(mat[0], mat[2], mat[3], mat[4], mat[6], mat[7], mat[12], mat[14], mat[15]).getDeterminant();
        float k = new Matrix3(mat[0], mat[1], mat[3], mat[4], mat[5], mat[7], mat[12], mat[13], mat[15]).getDeterminant();
        float l = new Matrix3(mat[0], mat[1], mat[2], mat[4], mat[5], mat[6], mat[12], mat[13], mat[14]).getDeterminant();

        float m = new Matrix3(mat[1], mat[2], mat[3], mat[5], mat[6], mat[7], mat[9], mat[10], mat[11]).getDeterminant();
        float n = new Matrix3(mat[0], mat[2], mat[3], mat[4], mat[6], mat[7], mat[8], mat[10], mat[11]).getDeterminant();
        float o = new Matrix3(mat[0], mat[1], mat[3], mat[4], mat[5], mat[7], mat[8], mat[9], mat[11]).getDeterminant();
        float p = new Matrix3(mat[0], mat[1], mat[2], mat[4], mat[5], mat[6], mat[8], mat[9], mat[10]).getDeterminant();

        float invd = 1.0f / det;
        mat[0] = a * invd;
        mat[1] = -e * invd;
        mat[2] = i * invd;
        mat[3] = -m * invd;
        mat[4] = -b * invd;
        mat[5] = f * invd;
        mat[6] = -j * invd;
        mat[7] = n * invd;
        mat[8] = c * invd;
        mat[9] = -g * invd;
        mat[10] = k * invd;
        mat[11] = -o * invd;
        mat[12] = -d * invd;
        mat[13] = h * invd;
        mat[14] = -l * invd;
        mat[15] = p * invd;

        return true;
    }

    /**
     * Matrix Multiplication returning a copy. {@see Matrix4.mult_}
     *
     * @param other Matrix 4 with which to multiply.
     * @return New Matrix4 representing this * other.
     */
    public Matrix4 mult (final Matrix4 other) {
        Matrix4 m = new Matrix4(this);
        m.mult_(other);

        return m;
    }

    /**
     * Multiply this Matrix4 by another Matrix4 and insert the
     * resulting value into this Matrix4 in place.
     * Destructive.
     *
     *             +-+-+-+
     *  Dot prod   |X| |Y|
     *  of rows    +-+-+-+
     *  x cols     |X| |Y|
     *             +-+-+-+
     *             |X| |Y|
     *             +-+-+-+
     *              v v v
     *  +-+-+-+    +-+-+-+
     *  |X|X|X| -> |X| | |
     *  +-+-+-+    +-+-+-+
     *  |Y|Y|Y| -> | | |Y|
     *  +-+-+-+    +-+-+-+
     *  | | | | -> | | | |
     *  +-+-+-+    +-+-+-+
     *
     * @param other Matrix 4 with which to multiply.
     * @return Reference to this updated Matrix4.
     */
    public Matrix4 mult_ (final Matrix4 other) {
        Vector4 c0 = other.column(0);
        Vector4 c1 = other.column(1);
        Vector4 c2 = other.column(2);
        Vector4 c3 = other.column(3);

        Vector4 r0 = row(0);
        Vector4 r1 = row(1);
        Vector4 r2 = row(2);
        Vector4 r3 = row(3);

        mat[0] = r0.dot(c0);
        mat[4] = r0.dot(c1);
        mat[8] = r0.dot(c2);
        mat[12] = r0.dot(c3);
        mat[1] = r1.dot(c0);
        mat[5] = r1.dot(c1);
        mat[9] = r1.dot(c2);
        mat[13] = r1.dot(c3);
        mat[2] = r2.dot(c0);
        mat[6] = r2.dot(c1);
        mat[10] = r2.dot(c2);
        mat[14] = r2.dot(c3);
        mat[3] = r3.dot(c0);
        mat[7] = r3.dot(c1);
        mat[11] = r3.dot(c2);
        mat[15] = r3.dot(c3);

        return this;
    }

    /**
     * Multiply this Matrix4 with a Vector4 returning a new Vector4.
     */
    public Vector4 mult (final Vector4 vector) {
        return new Vector4(
                mat[0] * vector.x + mat[4] * vector.y + mat[8] * vector.z + mat[12] * vector.w,
                mat[1] * vector.x + mat[5] * vector.y + mat[9] * vector.z + mat[13] * vector.w,
                mat[2] * vector.x + mat[6] * vector.y + mat[10] * vector.z + mat[14] * vector.w,
                mat[3] * vector.x + mat[7] * vector.y + mat[11] * vector.z + mat[15] * vector.w);
    }

    // Matrices for 3D Transformations

    /**
     * Initialize translation matrix.
     */
    public static Matrix4 initTranslation (final float x, final float y, final float z) {
        return new Matrix4(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                x,    y,    z,    1.0f);
    }

    /**
     * Initialize rotation matrix.
     */
    public static Matrix4 initRotation (final float x, final float y, final float z) {
        return Matrix4.IDENTITY
                .rotateX(z)
                .rotateY_(y)
                .rotateZ_(x);
    }

    /**
     * Initialize scale matrix.
     */
    public static Matrix4 initScale (final Vector3 scale) {
        return new Matrix4(
                scale.x, 0.0f,    0.0f,    0.0f,
                0.0f,    scale.y, 0.0f,    0.0f,
                0.0f,    0.0f,    scale.z, 0.0f,
                0.0f,    0.0f,    0.0f,    1.0f);
    }

    public Matrix4 translate (final float x, final float y, final float z) {
        return mult(new Matrix4(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                x, y, z, 1.0f));
    }

    public Matrix4 translate_ (final float x, final float y, final float z) {
        return mult_(new Matrix4(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                x, y, z, 1.0f));
    }

    public Matrix4 rotateX (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        return mult(new Matrix4(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, cosTheta, sinTheta, 0.0f,
                0.0f, -sinTheta, cosTheta, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f));
    }

    public Matrix4 rotateX_ (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        return mult_(new Matrix4(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, cosTheta, sinTheta, 0.0f,
                0.0f, -sinTheta, cosTheta, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f));
    }

    public Matrix4 rotateY (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        return mult(new Matrix4(
                cosTheta, 0.0f, -sinTheta, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                sinTheta, 0.0f, cosTheta, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f));
    }

    public Matrix4 rotateY_ (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        return mult_(new Matrix4(
                cosTheta, 0.0f, -sinTheta, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                sinTheta, 0.0f, cosTheta, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f));
    }

    public Matrix4 rotateZ (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        return mult(new Matrix4(
                cosTheta, -sinTheta, 0.0f, 0.0f,
                sinTheta, cosTheta, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f));
    }

    public Matrix4 rotateZ_ (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        return mult_(new Matrix4(
                cosTheta, -sinTheta, 0.0f, 0.0f,
                sinTheta, cosTheta, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f));
    }

    /**
     * Non-destructive uniform scale.
     */
    public Matrix4 scale (final float scale) {
        return mult(new Matrix4(
                scale, 0.0f, 0.0f, 0.0f,
                0.0f, scale, 0.0f, 0.0f,
                0.0f, 0.0f, scale, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f));
    }

    /**
     * Destructive uniform scale.
     */
    public Matrix4 scale_ (final float scale) {
        return mult_(new Matrix4(
                scale, 0.0f, 0.0f, 0.0f,
                0.0f, scale, 0.0f, 0.0f,
                0.0f, 0.0f, scale, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f));
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("<Matrix4 [%s %s %s %s][%s %s %s %s][%s %s %s %s][%s %s %s %s]>",
                             mat[0], mat[1], mat[2], mat[3], mat[4], mat[5], mat[6], mat[7],
                             mat[8], mat[9], mat[10], mat[11], mat[12], mat[13], mat[14], mat[15]);
    }

    @Override
    public boolean equals (final Object other) {

        if (this == other)
            return true;

        if (other instanceof Matrix4) {
            return compare((Matrix4)other);
        }

        return false;
    }

    /**
     * Compare this Matrix4 against another Matrix4 exactly.
     *
     * @return true if this Matrix4 exactly equals the other, false otherwise.
     */
    public boolean compare (final Matrix4 other) {

        for (int i = 0, iMax = mat.length; i < iMax; ++i) {
            if (mat[i] != other.mat[i])
                return false;
        }

        return true;
    }

    /**
     * Compare this Matrix4 against another Matrix4 within a given tolerance.
     *
     * @param other Matrix4 to compare against.
     * @param epsilon Tolerance within which Matrix4 are considered equal.
     * @return true if this Matrix4 equals the other within given
     * tolerance, false otherwise.
     */
    public boolean compare (final Matrix4 other, final float epsilon) {

        for (int i = 0, iMax = mat.length; i < iMax; ++i) {
            if (Math.abs(mat[i] - other.mat[i]) > epsilon)
                return false;
        }
        return true;
    }
}
