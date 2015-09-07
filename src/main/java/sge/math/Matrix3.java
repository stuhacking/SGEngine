package sge.math;

/**
 * 3 Dimensional Col-Major Matrix
 *
 *     0 1 2
 *   +------
 *  x| 0 3 6
 *  y| 1 4 7
 *  z| 2 5 8
 */
public final class Matrix3 {

    public static final int SIZE = 9;
    public static final int BYTE_SIZE = SIZE * FMath.FLOAT_SIZE;

    public static final Matrix3 ZERO = new Matrix3(
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f);

    public static final Matrix3 ONE = new Matrix3(
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f);

    public static final Matrix3 IDENTITY = new Matrix3(
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f);

    /** Matrix Data */
    public final float[] mat = {
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f};

    /** Default Constructor. */
    public Matrix3 () {}

    /** Column Major Vector Constructor. */
    public Matrix3 (final Vector3 a, final Vector3 b, final Vector3 c) {
        mat[0] = a.x;
        mat[1] = a.y;
        mat[2] = a.z;
        mat[3] = b.x;
        mat[4] = b.y;
        mat[5] = b.z;
        mat[6] = c.x;
        mat[7] = c.y;
        mat[8] = c.z;
    }

    /** Value Constructor. */
    public Matrix3 (
            final float a1, final float b1, final float c1,
            final float a2, final float b2, final float c2,
            final float a3, final float b3, final float c3) {
        mat[0] = a1;
        mat[1] = b1;
        mat[2] = c1;
        mat[3] = a2;
        mat[4] = b2;
        mat[5] = c2;
        mat[6] = a3;
        mat[7] = b3;
        mat[8] = c3;
    }

    /** Copy Constructor. */
    public Matrix3 (final Matrix3 m) {
        for (int i = 0; i < 9; i++) {
            mat[i] = m.mat[i];
        }
    }

    /** 2D Array Constructor. */
    public Matrix3 (final float[][] data) {
        for (int i = 0, iMax = data.length; i < iMax; i++) {
            int idx = i * 3;
            mat[idx] = data[i][0];
            mat[idx + 1] = data[i][1];
            mat[idx + 2] = data[i][2];
        }
    }

    /**
     * 1D Array Constructor. Write the contents of data into the columns
     * of this Matrix3 until we run out of data, or space.
     */
    public Matrix3 (final float[] data) {
        for (int i = 0, iMax = Math.min(mat.length, data.length); i < iMax; i++) {
            mat[i] = data[i];
        }
    }

    public float get (final int col, final int row) {
        return mat[col * 3 + row];
    }

    public Vector3 row (final int row) {
        return new Vector3(mat[row], mat[row + 3], mat[row + 6]);
    }

    public Vector3 column (final int col) {
        int offset = col * 3;
        return new Vector3(mat[offset], mat[offset + 1], mat[offset + 2]);
    }

    /**
     * Write the column data into 3 Vectors.
     */
    public void columns (final MVector3 a, final MVector3 b, final MVector3 c) {
        a.x = mat[0];
        a.y = mat[1];
        a.z = mat[2];
        b.x = mat[3];
        b.y = mat[4];
        b.z = mat[5];
        c.x = mat[6];
        c.y = mat[7];
        c.z = mat[8];
    }

    public void set_ (
            final float a1, final float b1, final float c1,
            final float a2, final float b2, final float c2,
            final float a3, final float b3, final float c3) {
        mat[0] = a1;
        mat[1] = b1;
        mat[2] = c1;
        mat[3] = a2;
        mat[4] = b2;
        mat[5] = c2;
        mat[6] = a3;
        mat[7] = b3;
        mat[8] = c3;
    }

    /**
     * Set all the values of this Matrix3 using another Matrix3.
     * Destructive.
     *
     * @param m
     */
    public void set_ (final Matrix3 m) {
        System.arraycopy(m.mat, 0, mat, 0, SIZE);
    }

    /**
     * Set all the values of this Matrix3 using a float[]. Write the contents of
     * data into the columns of this Matrix3 until we run out of data, or space.
     * Destructive.
     *
     * @param data
     */
    public void set_ (final float[] data) {
        System.arraycopy(data, 0, mat, 0, Math.min(SIZE, data.length));
    }

    /**
     * Set all the values of this Matrix3 using a float[][]. Data must be in the order[3][3].
     * Destructive.
     *
     * @param data
     */
    public void set_ (final float[][] data) {
        for (int i = 0, iMax = data.length; i < iMax; i++) {
            int idx = i * 3;
            mat[idx] = data[i][0];
            mat[idx + 1] = data[i][1];
            mat[idx + 2] = data[i][2];
        }
    }

    public void set_ (final int row, final int col, final float value) {
        mat[row * 3 + col] = value;
    }

    public void zero_ () {
        for (int i = 0; i < SIZE; i++) {
            mat[i] = 0f;
        }
    }

    public void identity_ () {
        set_(IDENTITY);
    }

    public boolean isIdentity () {
        return IDENTITY.compare(this);
    }

    public float getDeterminant () {
        // Laplace Expansion method:
        return mat[0] * (mat[4] * mat[8] - mat[7] * mat[5])
                - mat[1] * (mat[3] * mat[8] - mat[6] * mat[5])
                + mat[2] * (mat[3] * mat[7] - mat[6] * mat[4]);
    }

    public Matrix3 transpose () {
        return new Matrix3(
                mat[0], mat[3], mat[6],
                mat[1], mat[4], mat[7],
                mat[2], mat[5], mat[8]);
    }

    private final void swapIndices (final int index1, final int index2) {
        float temp = mat[index2];
        mat[index2] = mat[index1];
        mat[index1] = temp;
    }

    public Matrix3 transpose_ () {
        swapIndices(1, 3);
        swapIndices(2, 6);
        swapIndices(5, 7);

        return this;
    }

    public boolean hasInverse () {
        return 0 != getDeterminant();
    }

    /**
     * Return a new Matrix3 which is the inverse of this Matrix3.
     *
     * @return Inverse of this Matrix3, or null if there is no inverse.
     */
    public Matrix3 inverse () {
        Matrix3 inv = new Matrix3(this);

        return inv.inverse_() ? inv : null;
    }

    /**
     * Update this Matrix to be the Inverse of itself.
     * Destructive
     *
     * @return Flag indicating if the inversion operation was successful, or
     *   that the Matrix3 has no possible inverse.
     */
    public boolean inverse_ () {
        float det = getDeterminant();

        if (0 == det) {
            return false;
        }

        // Sub Determinants
        float a = new Matrix2(mat[4], mat[5], mat[7], mat[8]).getDeterminant();
        float b = new Matrix2(mat[3], mat[5], mat[6], mat[8]).getDeterminant();
        float c = new Matrix2(mat[3], mat[4], mat[6], mat[7]).getDeterminant();

        float d = new Matrix2(mat[1], mat[2], mat[7], mat[8]).getDeterminant();
        float e = new Matrix2(mat[0], mat[2], mat[6], mat[8]).getDeterminant();
        float f = new Matrix2(mat[0], mat[1], mat[6], mat[7]).getDeterminant();

        float g = new Matrix2(mat[1], mat[2], mat[4], mat[5]).getDeterminant();
        float h = new Matrix2(mat[0], mat[2], mat[3], mat[5]).getDeterminant();
        float i = new Matrix2(mat[0], mat[1], mat[3], mat[4]).getDeterminant();

        float invd = 1.0f / det;
        mat[0] = a * invd;
        mat[1] = -d * invd;
        mat[2] = g * invd;
        mat[3] = -b * invd;
        mat[4] = e * invd;
        mat[5] = -h * invd;
        mat[6] = c * invd;
        mat[7] = -f * invd;
        mat[8] = i * invd;

        return true;
    }

    /**
     * Matrix Multiplication.
     * +-+-+-+
     * Dot prod  |X| |Y|
     * of rows   +-+-+-+
     * x cols    |X| |Y|
     * +-+-+-+
     * |X| |Y|
     * +-+-+-+
     * v v v
     * +-+-+-+   +-+-+-+
     * |X|X|X|-> |X| | |
     * +-+-+-+   +-+-+-+
     * |Y|Y|Y|-> | | |Y|
     * +-+-+-+   +-+-+-+
     * | | | |-> | | | |
     * +-+-+-+   +-+-+-+
     */
    public Matrix3 mult (final Matrix3 other) {
        Matrix3 mult = new Matrix3(this);
        mult.mult_(other);

        return mult;
    }

    public Matrix3 mult_ (final Matrix3 other) {
        Vector3 c0 = other.column(0);
        Vector3 c1 = other.column(1);
        Vector3 c2 = other.column(2);

        Vector3 r0 = row(0);
        Vector3 r1 = row(1);
        Vector3 r2 = row(2);

        mat[0] = r0.dot(c0);
        mat[3] = r0.dot(c1);
        mat[6] = r0.dot(c2);
        mat[1] = r1.dot(c0);
        mat[4] = r1.dot(c1);
        mat[7] = r1.dot(c2);
        mat[2] = r2.dot(c0);
        mat[5] = r2.dot(c1);
        mat[8] = r2.dot(c2);

        return this;
    }

    public Vector3 mult (final Vector3 vector) {
        return new Vector3(
                mat[0] * vector.x + mat[3] * vector.y + mat[6] * vector.z,
                mat[1] * vector.x + mat[4] * vector.y + mat[7] * vector.z,
                mat[2] * vector.x + mat[5] * vector.y + mat[8] * vector.z);
    }

    /**
     * Static Rotation Constructor
     */
    public static Matrix3 rotation (final float xDeg, final float yDeg, final float zDeg) {
        Matrix3 id = new Matrix3();
        id.rotateX_(xDeg);
        id.rotateY_(yDeg);
        id.rotateZ_(zDeg);

        return id;
    }

    public Matrix3 rotateX (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        Matrix3 Rx = new Matrix3(
                1.0f, 0.0f, 0.0f,
                0.0f, cosTheta, sinTheta,
                0.0f, -sinTheta, cosTheta);

        return mult(Rx);
    }

    public Matrix3 rotateX_ (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        Matrix3 Rx = new Matrix3(
                1.0f, 0.0f, 0.0f,
                0.0f, cosTheta, sinTheta,
                0.0f, -sinTheta, cosTheta);

        return mult_(Rx);
    }

    public Matrix3 rotateY (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        Matrix3 Ry = new Matrix3(
                cosTheta, 0.0f, -sinTheta,
                0.0f, 1.0f, 0.0f,
                sinTheta, 0.0f, cosTheta);

        return mult(Ry);
    }

    public Matrix3 rotateY_ (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        Matrix3 Ry = new Matrix3(
                cosTheta, 0.0f, -sinTheta,
                0.0f, 1.0f, 0.0f,
                sinTheta, 0.0f, cosTheta);

        return mult_(Ry);
    }

    public Matrix3 rotateZ (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        Matrix3 Rz = new Matrix3(
                cosTheta, -sinTheta, 0.0f,
                sinTheta, cosTheta, 0.0f,
                0.0f, 0.0f, 1.0f);

        return mult(Rz);
    }

    public Matrix3 rotateZ_ (final float theta) {
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        Matrix3 Rz = new Matrix3(
                cosTheta, -sinTheta, 0.0f,
                sinTheta, cosTheta, 0.0f,
                0.0f, 0.0f, 1.0f);

        return mult_(Rz);
    }

    /**
     * Non-destructive uniform scale.
     */
    public Matrix3 scale (final float scale) {
        Matrix3 S = new Matrix3(
                scale, 0.0f, 0.0f,
                0.0f, scale, 0.0f,
                0.0f, 0.0f, scale);

        return mult(S);
    }

    /**
     * Destructive uniform scale.
     */
    public Matrix3 scale_ (final float scale) {
        Matrix3 S = new Matrix3(
                scale, 0.0f, 0.0f,
                0.0f, scale, 0.0f,
                0.0f, 0.0f, scale);

        return mult_(S);
    }

    /**
     * Rotate this Matrix3 about `vector' by `angle' degrees.
     *
     * @param vector The axis of rotation as a unit vector.
     * @param angle  The angle in degrees.
     * @return new rotated Matrix3
     */
    public static Matrix3 rotateAboutVector (final Vector3 vector, final float angle) {
        float a = FMath.toRadians(angle);
        float s = (float) Math.sin(a);
        float c = (float) Math.cos(a);

        float x = vector.x * s;
        float y = vector.y * s;
        float z = vector.z * s;

        float x2 = x + x;
        float y2 = y + y;
        float z2 = z + z;

        float xx = x * x2;
        float xy = x * y2;
        float xz = x * z2;

        float yy = y * y2;
        float yz = y * z2;
        float zz = z * z2;

        float wx = c * x2;
        float wy = c * y2;
        float wz = c * z2;

        return new Matrix3(
                1f - (yy + zz), xy - wz, xz + wy,
                xy + wz, 1f - (xx + zz), yz - wx,
                xz - wy, yz + wx, 1f - (xx + yy)
        );

    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("<Matrix3 [%s %s %s][%s %s %s][%s %s %s]>",
                             mat[0], mat[1], mat[2], mat[3], mat[4], mat[5], mat[6], mat[7], mat[8]);
    }

    @Override
    public boolean equals (final Object other) {
        if (this == other) return true;

        if (other instanceof Matrix3) {
            return compare((Matrix3) other);
        }

        return false;
    }

    /**
     * Compare this Matrix3 against another Matrix3 exactly.
     *
     * @return true if this Matrix3 exactly equals the other, false otherwise
     */
    public boolean compare (final Matrix3 other) {

        for (int i = 0, limit = mat.length; i < limit; ++i) {
            if (mat[i] != other.mat[i])
                return false;
        }
        return true;
    }

    /**
     * Compare this Matrix3 against another Matrix3 within a given tolerance.
     *
     * @param epsilon Tolerance within which Matrix3 are considered equal
     * @return true if this Matrix3 equals the other within given
     * tolerance, false otherwise
     */
    public boolean compare (final Matrix3 other, final float epsilon) {

        for (int i = 0, limit = mat.length; i < limit; ++i) {
            if (Math.abs(mat[i] - other.mat[i]) > epsilon)
                return false;
        }
        return true;
    }
}
