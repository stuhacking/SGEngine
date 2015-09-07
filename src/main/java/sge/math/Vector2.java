package sge.math;

import java.util.Random;

/**
 * 2 Dimensional Vector class
 */
public final class Vector2 {

    public static final int SIZE = 2;
    public static final int BYTE_SIZE = SIZE * FMath.FLOAT_SIZE;

    public static final Vector2 ZERO = new Vector2(0.0f);
    public static final Vector2 ONE = new Vector2(1.0f);

    /** Vector2 X component. */
    public final float x;

    /** Vector2 Y component. */
    public final float y;

    /** Default Constructor. */
    public Vector2 () {
        x = y = 0f;
    }

    /** Fill Constructor. */
    public Vector2 (final float val) {
        x = y = val;
    }

    /** Value Constructor. */
    public Vector2 (final float xx, final float yy) {
        x = xx;
        y = yy;
    }

    /** Copy Constructor. */
    public Vector2 (final Vector2 v) {
        x = v.x;
        y = v.y;
    }

    /** Mutable Vector2 -> Vector2. */
    public Vector2 (final MVector2 v) {
        x = v.x;
        y = v.y;
    }

    /** Array Constructor. */
    public Vector2 (final float[] values) {
        x = values[0];
        y = values[1];
    }

    public Vector2 setX (final float xx) {
        return new Vector2(xx, y);
    }

    public Vector2 setY (final float yy) {
        return new Vector2(x, yy);
    }

    // MAGNITUDE OPERATIONS

    public float getLengthSqr () {
        return x * x + y * y;
    }

    public float getLength () {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Set the length of this Vector2, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     *
     * @param length The desired new length
     */
    public Vector2 setLength (final float length) {
        float currentLength = getLength();
        if (currentLength == 0f) {
            return this;
        } else {
            return scale(length / currentLength);
        }
    }

    /**
     * Set the length of this Vector2 to 1.0f, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     */
    public Vector2 normalize () {
        float length = this.getLength();
        if (length != 0f) {
            return div(length);
        } else {
            return this;
        }
    }

    /**
     * Return a new Vector2 which has been truncated if its length
     * exceeds the limit.
     *
     * @param length Maximum length of Vector2
     * @return New (possibly truncated) Vector2
     */
    public Vector2 clampLength (final float length) {
        return (getLengthSqr() <= (length * length)) ? this : setLength(length);
    }

    /**
     * Return a new Vector2 which has been truncated if its length
     * exceeds the max limit, or extended if its length is less
     * than the min limit.
     *
     * @param min Minimum length of Vector2.
     * @param max Maximum length of Vector2.
     * @return Vector2 clamped between min and max length.
     */
    public Vector2 clampLength (final float min, final float max) {
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
     * Clamp Vector2 within minimum and maximum bounds, given by other
     * Vector2 values.
     * Destructive.
     *
     * @param min Vector2 minimum bound
     * @param max Vector2 maximum bound
     */
    public Vector2 clamp (final Vector2 min, final Vector2 max) {
        return new Vector2(FMath.clamp(x, min.x, max.x), FMath.clamp(y, min.y, max.y));
    }

    /**
     * Return vector with absolute values.
     */
    public Vector2 abs () {
        return new Vector2(Math.abs(x), Math.abs(y));
    }

    /**
     * Negate Vector.
     */
    public Vector2 negate () {
        return new Vector2(-x, -y);
    }

    // 2D VECTOR OPERATIONS

    /**
     * Add this Vector2 to another Vector2.
     *
     * @return Result of Vector2 addition
     */
    public Vector2 add (final Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    /**
     * Subtract another Vector2 from this Vector2.
     *
     * @return Result of Vector2 subtraction
     */
    public Vector2 sub (final Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    /**
     * Multiply this Vector2 by another Vector2 componentwise.
     *
     * @return Result of Vector2 multiplication
     */
    public Vector2 mult (final Vector2 other) {
        return new Vector2(x * other.x, y * other.y);
    }

    /**
     * Divide this Vector2 by a scalar value.
     *
     * @param divisor Divisor
     * @return Result of Vector2 division
     */
    public Vector2 div (final float divisor) {
        float invd = 1.0f / divisor;
        return new Vector2(x * invd, y * invd);
    }

    /**
     * Return a scaled copy of this Vector2.
     *
     * @param scale Scale factor
     */
    public Vector2 scale (final float scale) {
        return new Vector2(x * scale, y * scale);
    }

    /**
     * Return the dot product of this Vector2 and another Vector2.
     *
     * @return Vector2 Dot Product
     */
    public float dot (final Vector2 other) {
        return x * other.x + y * other.y;
    }

    /**
     * Return the cross product of this Vector2 and another Vector2.
     *
     * @return Vector2 Cross Product
     */
    public float cross (final Vector2 other) {
        return x * other.y - y * other.x;
    }

    /**
     * Mirror this vector over an arbitary axis of symmetry.
     *
     * A   N    A.mirror(N)
     * \  |    /
     * \ |  /
     * \|/
     *
     * @param axis Axis of symmetry.
     * @return The result of mirroring this Vector2 about `axis'
     */
    public Vector2 mirror (final Vector2 axis) {
        return axis.scale(dot(axis) * 2.0f).sub(this);
    }

    /**
     * Linear interpolate between two vectors.
     */
    public static Vector2 lerp (final Vector2 lhs, final Vector2 rhs, final float ratio) {
        return new Vector2(FMath.lerp(lhs.x, rhs.x, ratio), FMath.lerp(lhs.y, rhs.y, ratio));
    }

    /**
     * Rotate vector about angle.
     *
     * @param angle Angle in degrees.
     */
    public Vector2 rotate (final float angle) {
        float rad = FMath.toRadians(angle);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);

        return new Vector2(x * cos - y * sin, x * sin + y * cos);
    }

    /**
     * Create a randomly oriented 2D unit vector.
     */
    public static Vector2 random () {
        Random rnd = FRandom.getRandom();
        float max = FMath.TWO_PI;
        float theta = rnd.nextFloat() * max;

        return new Vector2((float) Math.cos(theta), (float) Math.sin(theta));
    }

    /**
     * Set the seed of the internal random number generator.
     */
    public static void setSeed (final long seed) {
        Random rnd = FRandom.getRandom();
        rnd.setSeed(seed);
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("<Vector2 %s %s>", x, y);
    }

    @Override
    public int hashCode () {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public boolean equals (final Object other) {
        if (this == other) return true;

        if (other instanceof Vector2) {
            return compare((Vector2) other);
        }

        return false;
    }

    /**
     * Compare this Vector2 against another Vector2 exactly.
     *
     * @return true if this Vector2 exactly equals the other, false otherwise
     */
    public boolean compare (final Vector2 other) {
        return x == other.x && y == other.y;
    }

    /**
     * Compare this Vector2 against another Vector2 within a given tolerance.
     *
     * @param epsilon Tolerance within which Vector2 are considered equal
     * @return true if this Vector2 equals the other within given
     * tolerance, false otherwise
     */
    public boolean compare (final Vector2 other, final float epsilon) {
        if (Math.abs(x - other.x) > epsilon)
            return false;

        if (Math.abs(y - other.y) > epsilon)
            return false;

        return true;
    }

    /**
     * Pack values into float[].
     */
    public float[] toFloatArray () {
        return new float[]{x, y};
    }
}
