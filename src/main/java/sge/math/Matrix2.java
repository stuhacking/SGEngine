package sge.math;

/**
 * 2 Dimensional Col-Major Matrix
 *
 *     0 1
 *   +----
 *  x| 0 2
 *  y| 1 3
 */
public final class Matrix2 {

    public static final int SIZE = 4;
    public static final int BYTE_SIZE = SIZE * FMath.FLOAT_SIZE;

    public static final Matrix2 ZERO = new Matrix2(
            0.0f, 0.0f,
            0.0f, 0.0f);

    public static final Matrix2 ONE = new Matrix2(
            1.0f, 1.0f,
            1.0f, 1.0f);

    public static final Matrix2 IDENTITY = new Matrix2(
            1.0f, 0.0f,
            0.0f, 1.0f);

    /** Matrix Data */
    public final float[] mat = {
            1.0f, 0.0f,
            0.0f, 1.0f};

    /** Default Constructor. */
    public Matrix2 () {}

    /** Column Major Vector Constructor. */
    public Matrix2 (final Vector3 a, final Vector3 b) {
        mat[0] = a.x;
        mat[1] = a.y;
        mat[2] = b.x;
        mat[3] = b.y;
    }

    /** Value Constructor. */
    public Matrix2 (final float a1, final float b1,
                    final float a2, final float b2) {
        mat[0] = a1;
        mat[1] = b1;
        mat[2] = a2;
        mat[3] = b2;
    }

    /** Copy Constructor. */
    public Matrix2 (final Matrix2 m) {
        System.arraycopy(m.mat, 0, mat, 0, SIZE);
    }

    /** 2D Array Constructor. */
    public Matrix2 (final float[][] data) {
        for (int i = 0, iMax = data.length; i < iMax; i++) {
            int idx = i * 2;
            mat[idx] = data[i][0];
            mat[idx + 1] = data[i][1];
        }
    }

    /**
     * 1D Array Constructor. Write the contents of data into the columns
     * of this Matrix2 until we run out of data, or space.
     */
    public Matrix2 (final float[] data) {
        System.arraycopy(data, 0, mat, 0, Math.min(SIZE, data.length));
    }

    /**
     * Get the value of a single location in the matrix indexed by
     * (col, row).
     *
     * @param col
     * @param row
     * @return
     */
    public float get (final int col, final int row) {
        return mat[col * 2 + row];
    }

    /**
     * Return a single row of this Matrix2 as a Vector2.
     *
     * @param row
     * @return
     */
    public Vector2 row (final int row) {
        return new Vector2(mat[row], mat[row + 2]);
    }

    /**
     * Return a single column of this Matrix2 as a Vector2.
     *
     * @param col
     * @return
     */
    public Vector2 column (final int col) {
        int offset = col * 2;
        return new Vector2(mat[offset], mat[offset + 1]);
    }

    /**
     * Write the column data into 2 MVectors.
     */
    public void columns (final MVector2 a, final MVector2 b) {
        a.x = mat[0];
        a.y = mat[1];
        b.x = mat[2];
        b.y = mat[3];
    }

    /**
     * Set all the values of this Matrix2.
     * Destructive.
     *
     * @param a1
     * @param b1
     * @param a2
     * @param b2
     */
    public void set_ (final float a1, final float b1,
                      final float a2, final float b2) {
        mat[0] = a1;
        mat[1] = b1;
        mat[2] = a2;
        mat[3] = b2;
    }

    /**
     * Set all the values of this Matrix2 using another Matrix2.
     * Destructive.
     *
     * @param m
     */
    public void set_ (final Matrix2 m) {
        System.arraycopy(m.mat, 0, mat, 0, SIZE);
    }

    /**
     * Set all the values of this Matrix2 using a float[]. Write the contents of
     * data into the columns of this Matrix2 until we run out of data, or space.
     * Destructive.
     *
     * @param data
     */
    public void set_ (final float[] data) {
        System.arraycopy(data, 0, mat, 0, Math.min(SIZE, data.length));
    }

    /**
     * Set all the values of this Matrix2 using a float[][]. Data must be in the order[2][2].
     * Destructive.
     *
     * @param data
     */
    public void set_ (final float[][] data) {
        for (int i = 0, iMax = data.length; i < iMax; i++) {
            int idx = i * 2;
            mat[idx] = data[i][0];
            mat[idx + 1] = data[i][1];
        }
    }

    /**
     * Set a single value in this Matrix2 at a location indexed by (col, row).
     * Destructive.
     *
     * @param row
     * @param col
     * @param value
     */
    public void set_ (final int col, final int row, final float value) {
        mat[col * 2 + row] = value;
    }

    /**
     * Set all values in this Matrix2 to 0.0f.
     * Destructive.
     */
    public void zero_ () {
        for (int i = 0; i < SIZE; i++) {
            mat[i] = 0.0f;
        }
    }

    /**
     * Set the values of this Matrix2 to be the identity matrix.
     * Destructive.
     */
    public void identity_ () {
        set_(IDENTITY);
    }

    /**
     * Check if this Matrix2 is the identity matrix.
     *
     * @return true if identity, otherwise false.
     */
    public boolean isIdentity () {
        return IDENTITY.compare(this);
    }

    /**
     * Matrix Determinant.
     *
     * @return Determinant of this Matrix2.
     */
    public float getDeterminant () {
        // Laplace Expansion method:
        return mat[0] * mat[3] - mat[1] * mat[2];
    }

    public boolean hasInverse () {
        return 0 != getDeterminant();
    }

    /**
     * Matrix transposition (Mirror along x == y)
     */
    public Matrix2 transpose () {
        return new Matrix2(
                mat[0], mat[2],
                mat[1], mat[3]);
    }

    private final void swapIndices (final int a, final int b) {
        float temp = mat[b];
        mat[b] = mat[a];
        mat[a] = temp;
    }

    /**
     * Mirror the rows and columns of this Matrix2 along the diagonal x == y.
     * Destructive.
     *
     * @return
     */
    public Matrix2 transpose_ () {
        swapIndices(1, 2);
        return this;
    }

    /**
     * Matrix Multiplication.
     * +-+-+
     * Dot prod  |X|Y|
     * of rows   +-+-+
     * x cols    |X|Y|
     * +-+-+
     * v v
     * +-+-+    +-+-+
     * |X|X| -> |X| |
     * +-+-+    +-+-+
     * |Y|Y| -> | |Y|
     * +-+-+    +-+-+
     */
    public Matrix2 mult (final Matrix2 other) {
        Matrix2 mult = new Matrix2(this);
        mult.mult_(other);

        return mult;
    }

    /**
     * Matrix Multiplication.
     * Destructive.
     *
     * @param other
     * @return
     */
    public Matrix2 mult_ (final Matrix2 other) {
        Vector2 c0 = other.column(0);
        Vector2 c1 = other.column(1);

        Vector2 r0 = row(0);
        Vector2 r1 = row(1);

        mat[0] = r0.dot(c0);
        mat[3] = r0.dot(c1);
        mat[1] = r1.dot(c0);
        mat[4] = r1.dot(c1);

        return this;
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("<Matrix2 [%s %s][%s %s]>",
                             mat[0], mat[1], mat[2], mat[3]);
    }

    @Override
    public boolean equals (final Object other) {
        if (this == other) return true;

        if (other instanceof Matrix2) {
            return compare((Matrix2) other);
        }

        return false;
    }

    /**
     * Compare this Matrix2 against another Matrix2 exactly.
     *
     * @return true if this Matrix2 exactly equals the other, false otherwise
     */
    public boolean compare (final Matrix2 other) {

        for (int i = 0, iMax = mat.length; i < iMax; ++i) {
            if (mat[i] != other.mat[i])
                return false;
        }

        return true;
    }

    /**
     * Compare this Matrix2 against another Matrix2 within a given tolerance.
     *
     * @param epsilon Tolerance within which Matrix2 are considered equal
     * @return true if this Matrix2 equals the other within given
     * tolerance, false otherwise
     */
    public boolean compare (final Matrix2 other, final float epsilon) {

        for (int i = 0, iMax = mat.length; i < iMax; ++i) {
            if (Math.abs(mat[i] - other.mat[i]) > epsilon)
                return false;
        }

        return true;
    }
}
