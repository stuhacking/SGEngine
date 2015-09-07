package sge.math;

/**
 * 4 Dimensional Vector class
 */
public final class Vector4 {

    public static final int SIZE = 4;
    public static final int BYTE_SIZE = SIZE * FMath.FLOAT_SIZE;

    public static final Vector4 ZERO = new Vector4(0.0f);
    public static final Vector4 ONE = new Vector4(1.0f);

    /** Vector4 X component. */
    public final float x;

    /** Vector4 Y component. */
    public final float y;

    /** Vector4 Z component. */
    public final float z;

    /** Vector4 W component. */
    public final float w;

    /** Default Constructor. */
    public Vector4 () {
        x = y = z = w = 0.0f;
    }

    /** Fill Constructor. */
    public Vector4 (final float val) {
        x = y = z = w = val;
    }

    /** Value Constructor. */
    public Vector4 (final float xx, final float yy, final float zz, final float ww) {
        x = xx;
        y = yy;
        z = zz;
        w = ww;
    }

    /** Copy Constructor. */
    public Vector4 (final Vector4 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
    }

    /** Vector2 -> Vector4. */
    public Vector4 (final Vector2 v, final float zz, final float ww) {
        x = v.x;
        y = v.y;
        z = zz;
        w = ww;
    }

    /** Vector3 -> Vector4. */
    public Vector4 (final Vector3 vec, final float ww) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
        w = ww;
    }

    /** Mutable Vector4 -> Vector4. */
    public Vector4 (final MVector4 vec) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
        w = vec.w;
    }

    /** Array Constructor. */
    public Vector4 (final float[] values) {
        x = values[0];
        y = values[1];
        z = values[2];
        w = values[3];
    }

    public Vector4 setX (final float xx) {
        return new Vector4(xx, y, z, w);
    }

    public Vector4 setY (final float yy) {
        return new Vector4(x, yy, z, w);
    }

    public Vector4 setZ (final float zz) {
        return new Vector4(x, y, zz, w);
    }

    public Vector4 setW (final float ww) {
        return new Vector4(x, y, z, ww);
    }

    // MAGNITUDE OPERATIONS

    public float getLengthSqr () {
        return x * x + y * y + z * z + w * w;
    }

    public float getLength () {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * Set length. If Length is zero, we can't determine
     * direction.
     */
    public Vector4 setLength (final float newLength) {
        float currentLength = getLength();
        if (currentLength == 0f) {
            return this;
        } else {
            return this.scale(newLength / currentLength);
        }
    }

    /**
     * To Unit Vector. If Length is zero, we can't determine
     * direction.
     */
    public Vector4 normalize () {
        float length = this.getLength();
        if (length != 0f) {
            return this.div(length);
        } else {
            return this;
        }
    }

    /**
     * Clamp the length of the vector if greater than `length'.
     *
     * @param length Maximum Length
     */
    public Vector4 clampLength (final float length) {
        return (getLengthSqr() <= (length * length)) ? this : this.setLength(length);
    }

    /**
     * Return a new Vector4 which has been truncated if its length
     * exceeds the max limit, or extended if its length is less
     * than the min limit.
     *
     * @param min Minimum length of Vector4.
     * @param max Maximum length of Vector4.
     * @return Vector4 clamped between min and max length.
     */
    public Vector4 clampLength (final float min, final float max) {
        float length = getLengthSqr();
        if (length < (min * min)) {
            return setLength(min);
        } else if (length > (max * max)) {
            return setLength(max);
        } else {
            return this;
        }
    }

    /**
     * Return the Vector with all values absolute.
     */
    public Vector4 abs () {
        return new Vector4(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }

    /**
     * Negate Vector.
     */
    public Vector4 negate () {
        return new Vector4(-x, -y, -z, -w);
    }

    // 4D VECTOR OPERATIONS

    /**
     * Add this Vector4 to another Vector4.
     *
     * @return Result of Vector4 addition
     */
    public Vector4 add (final Vector4 other) {
        return new Vector4(x + other.x, y + other.y, z + other.z, w + other.w);
    }

    /**
     * Subtract another Vector4 from this Vector4.
     *
     * @return Result of Vector4 subtraction
     */
    public Vector4 sub (final Vector4 other) {
        return new Vector4(x - other.x, y - other.y, z - other.z, w - other.w);
    }

    public Vector4 mult (final Vector4 other) {
        return new Vector4(x * other.x, y * other.y, z * other.z, w * other.w);
    }

    public Vector4 div (final float divisor) {
        float invd = 1.0f / divisor;
        return new Vector4(x * invd, y * invd, z * invd, w * invd);
    }

    public Vector4 scale (final float scale) {
        return new Vector4(x * scale, y * scale, z * scale, w * scale);
    }

    public float dot (final Vector4 other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    /**
     * Linear interpolation between two vectors.
     */
    public static Vector4 lerp (final Vector4 lhs, final Vector4 rhs, final float ratio) {
        return new Vector4(FMath.lerp(lhs.x, rhs.x, ratio),
                           FMath.lerp(lhs.y, rhs.y, ratio),
                           FMath.lerp(lhs.z, rhs.z, ratio),
                           FMath.lerp(lhs.w, rhs.w, ratio));
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("<Vector4 %s %s %s %s>", x, y, z, w);
    }

    @Override
    public int hashCode () {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
        return result;
    }

    @Override
    public boolean equals (final Object other) {
        if (this == other) return true;

        if (other instanceof Vector4) {
            return compare((Vector4) other);
        }

        return false;
    }

    /**
     * Compare this Vector4 against another Vector4 exactly.
     *
     * @return true if this Vector4 exactly equals the other, false otherwise
     */
    public boolean compare (final Vector4 other) {
        return x == other.x && y == other.y && z == other.z && w == other.w;
    }

    /**
     * Compare this Vector4 against another Vector4 within a given tolerance.
     *
     * @param other Another Vector4 to compare against.
     * @param epsilon Tolerance within which values are considered equal.
     * @return true if this Vector4 equals the other within given
     * tolerance, false otherwise
     */
    public boolean compare (final Vector4 other, final float epsilon) {
        if (Math.abs(x - other.x) > epsilon)
            return false;

        if (Math.abs(y - other.y) > epsilon)
            return false;

        if (Math.abs(z - other.z) > epsilon)
            return false;

        if (Math.abs(w - other.w) > epsilon)
            return false;

        return true;
    }

    /**
     * Convert object to raw float[].
     */
    public float[] toFloatArray () {
        return new float[]{x, y, z, w};
    }

    // Swizzling

    /**
     * Convert Vector4 -> Vector3 losing w component.
     */
    public Vector3 xyz () {
        return new Vector3(x, y, z);
    }

    public Vector2 xy () {
        return new Vector2(x, y);
    }

    public Vector2 yz () {
        return new Vector2(y, z);
    }

    public Vector2 xz () {
        return new Vector2(x, z);
    }
}
