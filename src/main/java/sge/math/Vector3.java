package sge.math;

import java.util.Random;

/**
 * 3 Dimensional Vector class
 */
public final class Vector3 {

    public static final int SIZE = 3;
    public static final int BYTE_SIZE = SIZE * FMath.FLOAT_SIZE;

    public static final Vector3 ZERO = new Vector3(0.0f);
    public static final Vector3 ONE = new Vector3(1.0f);

    /** Vector3 X component. */
    public final float x;

    /** Vector3 Y component. */
    public final float y;

    /** Vector3 Z component. */
    public final float z;

    /** Default Constructor. */
    public Vector3 () {
        x = y = z = 0.0f;
    }

    /** Fill Constructor. */
    public Vector3 (final float val) {
        x = y = z = val;
    }

    /** Value Constructor. */
    public Vector3 (final float xx, final float yy, final float zz) {
        x = xx;
        y = yy;
        z = zz;
    }

    /** Copy Constructor. */
    public Vector3 (final Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    /** Vector2 -> Vector3. */
    public Vector3 (final Vector2 v, final float zz) {
        x = v.x;
        y = v.y;
        z = zz;
    }

    /** Mutable Vector3 -> Vector3. */
    public Vector3 (final MVector3 vec) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }

    /** Array Constructor. */
    public Vector3 (final float[] values) {
        x = values[0];
        y = values[1];
        z = values[2];
    }

    public Vector3 setX (final float xx) {
        return new Vector3(xx, y, z);
    }

    public Vector3 setY (final float yy) {
        return new Vector3(x, yy, z);
    }

    public Vector3 setZ (final float zz) {
        return new Vector3(x, y, zz);
    }

    // MAGNITUDE OPERATIONS

    /**
     * Get the squared length of this Vector3.
     *
     * @return The Vector's squared length
     */
    public float getLengthSqr () {
        return x * x + y * y + z * z;
    }

    /**
     * Get the length of this Vector3.
     *
     * @return The Vector's length
     */
    public float getLength () {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Set the length of this Vector3, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     *
     * @param length The desired new length
     */
    public Vector3 setLength (final float length) {
        float currentLength = getLength();
        if (currentLength == 0.0f) {
            return this;
        } else {
            return scale(length / currentLength);
        }
    }

    /**
     * Set the length of this Vector3 to 1.0f, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     */
    public Vector3 normalize () {
        float length = this.getLength();
        if (length == 0.0f) {
            return this;
        } else {
            return div(length);
        }
    }

    /**
     * Return a new Vector3 which has been truncated if its length
     * exceeds the limit.
     *
     * @param length Maximum length of Vector3
     * @return New (possibly truncated) Vector3
     */
    public Vector3 clampLength (final float length) {
        return (getLengthSqr() <= (length * length)) ? this : setLength(length);
    }

    /**
     * Return a new Vector3 which has been truncated if its length
     * exceeds the max limit, or extended if its length is less
     * than the min limit.
     *
     * @param min Minimum length of Vector3.
     * @param max Maximum length of Vector3.
     * @return Vector3 clamped between min and max length.
     */
    public Vector3 clampLength (final float min, final float max) {
        float sqlength = getLengthSqr();
        if (sqlength < (min * min)) {
            return setLength(min);
        } else if (sqlength > (max * max)) {
            return setLength(max);
        } else {
            return this;
        }
    }

    /**
     * Clamp Vector3 within minimum and maximum bounds, given by other
     * Vector3 values.
     * Destructive.
     *
     * @param min Vector3 minimum bound
     * @param max Vector3 maximum bound
     */
    public Vector3 clamp (final Vector3 min, final Vector3 max) {
        return new Vector3(FMath.clamp(x, min.x, max.x), FMath.clamp(y, min.y, max.y), FMath.clamp(z, min.z, max.z));
    }

    /**
     * Return vector with absolute values.
     */
    public Vector3 abs () {
        return new Vector3(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    /**
     * Negate Vector.
     */
    public Vector3 negate () {
        return new Vector3(-x, -y, -z);
    }

    // 3D VECTOR OPERATIONS

    /**
     * Add this Vector3 to another Vector3.
     *
     * @return Result of Vector3 addition
     */
    public Vector3 add (final Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Subtract another Vector3 from this Vector3.
     *
     * @return Result of Vector3 subtraction
     */
    public Vector3 sub (final Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Multiply this Vector3 by another Vector3 componentwise.
     *
     * @return Result of Vector3 multiplication
     */
    public Vector3 mult (final Vector3 other) {
        return new Vector3(x * other.x, y * other.y, z * other.z);
    }

    /**
     * Divide this Vector3 by a scalar value.
     *
     * @param divisor Divisor
     * @return Result of Vector3 division
     */
    public Vector3 div (final float divisor) {
        float invd = 1.0f / divisor;
        return new Vector3(x * invd, y * invd, z * invd);
    }

    /**
     * Return a scaled copy of this Vector3.
     *
     * @param scale Scale factor.
     */
    public Vector3 scale (final float scale) {
        return new Vector3(x * scale, y * scale, z * scale);
    }

    /**
     * Return the dot product of this Vector3 and another Vector3.
     *
     * @return Vector3 Dot Product
     */
    public float dot (final Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Return the cross product of this Vector3 and another Vector3. The
     * resulting Vector3 is perpendicular to A and B.
     * <p/>
     * AxB
     * ^
     * |  B
     * | /
     * |/
     * +--------> A
     * |
     * v
     * BxA
     *
     * @return Vector2 Cross Product
     */
    public Vector3 cross (final Vector3 other) {
        return new Vector3(y * other.z - z * other.y,
                           z * other.x - x * other.z,
                           x * other.y - y * other.x);
    }

    /**
     * Mirror this vector over an arbitrary axis of symmetry.
     *
     * @param axis Axis of symmetry.
     * @return The result of mirroring this Vector3 about `axis'
     */
    public Vector3 mirror (final Vector3 axis) {
        return axis.scale(dot(axis) * 2.0f).sub(this);
    }

    /**
     * Linear interpolation between two vectors.
     */
    public static Vector3 lerp (final Vector3 lhs, final Vector3 rhs, final float ratio) {
        return new Vector3(FMath.lerp(lhs.x, rhs.x, ratio),
                           FMath.lerp(lhs.y, rhs.y, ratio),
                           FMath.lerp(lhs.z, rhs.z, ratio));
    }

    /**
     * Return the result of rotation this Vector3 by an angle around an
     * axis of rotation.
     *
     * @param angle Angle of rotation in degrees
     * @param axis  Axis of rotation as a Unit Vector3
     * @return New rotated Vector3
     */
    @Deprecated
    public Vector3 rotate (final float angle, final Vector3 axis) {
        float a = FMath.toRadians(angle / 2.0f);
        float sinHalfAngle = (float) Math.sin(a);
        float cosHalfAngle = (float) Math.cos(a);

        Quaternion rotation = new Quaternion(
                axis.x * sinHalfAngle, axis.y * sinHalfAngle, axis.z * sinHalfAngle,
                cosHalfAngle);

        Quaternion w = rotation.mult(this).mult(rotation.conjugate());

        return new Vector3(w.i, w.j, w.k);
    }

    /**
     * Create a unit Vector3 with a random orientation.
     * TODO Should rather have a deterministic 'generate' function that takes a seed.
     * TODO Move to a library that generates various data instances.
     */
    public static Vector3 random () {
        Random rnd = FRandom.getRandom();
        float max = FMath.TWO_PI;
        float inclination = rnd.nextFloat() * max;
        float azimuth = rnd.nextFloat() * max;
        return new Vector3(
                (float) Math.sin(inclination) * (float) Math.cos(azimuth),
                (float) Math.cos(inclination) * (float) Math.sin(azimuth),
                (float) Math.cos(inclination)
        );
    }

    public static void setSeed (final long seed) {
        Random rnd = FRandom.getRandom();
        rnd.setSeed(seed);
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("<Vector3 %s %s %s>", x, y, z);
    }

    @Override
    public int hashCode () {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }

    @Override
    public boolean equals (final Object other) {
        if (this == other) return true;

        if (other instanceof Vector3) {
            return compare((Vector3) other);
        }

        return false;
    }

    /**
     * Compare this Vector3 against another Vector3 exactly.
     *
     * @return true if this Vector3 exactly equals the other, false otherwise
     */
    public boolean compare (final Vector3 other) {

        return x == other.x && y == other.y && z == other.z;
    }

    /**
     * Compare this Vector3 against another Vector3 within a given tolerance.
     *
     * @param other Vector3 to compare against.
     * @param epsilon Tolerance within which Vector3 are considered equal.
     * @return true if this Vector3 equals the other within given
     * tolerance, false otherwise.
     */
    public boolean compare (final Vector3 other, final float epsilon) {

        if (Math.abs(x - other.x) > epsilon)
            return false;

        if (Math.abs(y - other.y) > epsilon)
            return false;

        if (Math.abs(z - other.z) > epsilon)
            return false;

        return true;
    }

    /**
     * Pack object values into float[].
     */
    public float[] toFloatArray () {
        return new float[]{x, y, z};
    }

    // Swizzling

    public Vector2 xy () {
        return new Vector2(x, y);
    }

    public Vector2 yz () {
        return new Vector2(y, z);
    }

    public Vector2 xz () {
        return new Vector2(x, z);
    }

    /**
     * Convert Vector3 into Homogeneous Point Vector4.
     */
    public Vector4 toPoint () {
        return new Vector4(x, y, z, 1.0f);
    }

    /**
     * Convert Vector3 into Homogeneous Direction Vector4.
     */
    public Vector4 toDirection () {
        return new Vector4(x, y, z, 0.0f);
    }
}
