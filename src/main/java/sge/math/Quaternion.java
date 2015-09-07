package sge.math;

/**
 * Quaternion
 */
public final class Quaternion {

    public static final Quaternion IDENTITY = new Quaternion(0f, 0f, 0f, 1f);

    /** Quaternion Imaginary I component. */
    public final float i;

    /** Quaternion Imaginary J component. */
    public final float j;

    /** Quaternion Imaginary K component. */
    public final float k;

    /** Quaternion Real W component. */
    public final float w;

    /** Default Constructor. */
    public Quaternion () {
        i = j = k = w = 0.0f;
    }

    /** Value Constructor. */
    public Quaternion (final float ii, final float jj, final float kk, final float ww) {
        i = ii;
        j = jj;
        k = kk;
        w = ww;
    }

    /** Copy Constructor */
    public Quaternion (final Quaternion q) {
        i = q.i;
        j = q.j;
        k = q.k;
        w = q.w;
    }

    /**
     * Array constructor.
     */
    public Quaternion (final float[] values) {
        i = values[0];
        j = values[1];
        k = values[2];
        w = values[3];
    }

    public boolean isIdentity () {
        return IDENTITY.compare(this);
    }

    // MAGNITUDE OPERATIONS

    public float getLengthSqr () {
        return i * i + j * j + k * k + w * w;
    }

    public float getLength () {
        return (float) Math.sqrt(i * i + j * j + k * k + w * w);
    }

    /**
     * Set the length of this Quaternion to 1.0f, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     */
    public Quaternion normalize () {
        float length = this.getLength();
        if (length != 0f) {
            return this.div(length);
        } else {
            return this;
        }
    }

    /**
     * Quaternion Inverse.
     * Use conjugate, if known to be unit quaternion.
     */
    public Quaternion inverse () {
        return conjugate().div(getLengthSqr());
    }

    /**
     * Quaternion Conjugate.
     */
    public Quaternion conjugate () {
        return new Quaternion(-i, -j, -k, w);
    }

    // QUATERNION OPERATIONS

    /**
     * Add this Quaternion to another Quaternion.
     *
     * @return Result of Quaternion addition
     */
    public Quaternion add (final Quaternion other) {
        return new Quaternion(i + other.i, j + other.j, k + other.k, w + other.w);
    }

    /**
     * Subtract another Quaternion from this Quaternion.
     *
     * @return Result of Quaternion subtraction
     */
    public Quaternion sub (final Quaternion other) {
        return new Quaternion(i - other.i, j - other.j, k - other.k, w - other.w);
    }

    /**
     * Divide this Quaternion by a scalar value.
     *
     * @return Result of scalar division
     */
    public Quaternion div (final float divisor) {
        float invd = 1.0f / divisor;
        return new Quaternion(i * invd, j * invd, k * invd, w * invd);
    }

    /**
     * Scale this Quaternion.
     *
     * @return Scaled Quaternion
     */
    public Quaternion scale (final float scale) {
        return new Quaternion(i * scale, j * scale, k * scale, w * scale);
    }

    /**
     * @return Quaternion dot product
     */
    public float dot (final Quaternion other) {
        return i * other.i + j * other.j + k * other.k;
    }

    /**
     * @return Quaternion cross product
     */
    public Quaternion cross (final Quaternion other) {
        return new Quaternion(
                j * other.k - k * other.j,
                k * other.i - i * other.k,
                i * other.j - j * other.i,
                w);
    }

    public Quaternion mult (final Quaternion other) {
        return new Quaternion(
                i * other.w + w * other.i + j * other.k - k * other.j,
                j * other.w + w * other.j + k * other.i - i * other.k,
                k * other.w + w * other.k + i * other.j - j * other.i,
                w * other.w - i * other.i - j * other.j - k * other.k
        );
    }

    public Quaternion mult (final Vector3 vec) {
        return new Quaternion(
                w * vec.x + j * vec.z - k * vec.y,
                w * vec.y + k * vec.x - i * vec.z,
                w * vec.z + i * vec.y - j * vec.x,
                -i * vec.x - j * vec.y - k * vec.z
        );
    }

    // JAVA HOUSEKEEPING

    @Override
    public final String toString () {
        return String.format("<Quaternion (%s,%s,%s) %s>", i, j, k, w);
    }

    @Override
    public boolean equals (final Object other) {
        if (this == other) return true;

        if (other instanceof Quaternion) {
            return compare((Quaternion) other);
        }

        return false;
    }

    public boolean compare (final Quaternion other) {
        return i == other.i && j == other.j && k == other.k && w == other.w;
    }

    public boolean compare (final Quaternion other, float epsilon) {
        if (Math.abs(i - other.i) > epsilon)
            return false;

        if (Math.abs(j - other.j) > epsilon)
            return false;

        if (Math.abs(k - other.k) > epsilon)
            return false;

        if (Math.abs(w - other.w) > epsilon)
            return false;

        return true;
    }

}
