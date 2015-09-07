package sge.math;

import java.util.Random;

/**
 * 2 Dimensional Mutable Vector class
 * <p/>
 * The members of this class are not enforced to be final. Methods defined on the class
 * will return a new object and will not mutate the original, unless the method name is
 * suffixed with an underscore. (e.g. add_ will destructively update the current object
 * but add will not.)
 * <p/>
 * There are methods for converting between Vectors and Mutable Vectors, but operations
 * on each will not be mixed.
 */
public final class MVector2 {

    /** MVector2 X component. */
    public float x;

    /** MVector2 Y component. */
    public float y;

    /** Default Constructor. */
    public MVector2 () {
        x = y = 0f;
    }

    /** Fill Constructor. */
    public MVector2 (final float val) {
        x = y = val;
    }

    /** Value Constructor. */
    public MVector2 (final float xx, final float yy) {
        x = xx;
        y = yy;
    }

    /** Copy Constructor. */
    public MVector2 (final MVector2 v) {
        x = v.x;
        y = v.y;
    }

    /** Vector2 -> MVector2. */
    public MVector2 (final Vector2 v) {
        x = v.x;
        y = v.y;
    }

    /**
     * Array Constructor.
     */
    public MVector2 (final float[] values) {
        x = values[0];
        y = values[1];
    }

    /**
     * Set the components of this MVector2 to zero.
     * Destructive.
     */
    public MVector2 zero_ () {
        x = 0.0f;
        y = 0.0f;

        return this;
    }

    /**
     * Set the values of this MVector2.
     * Destructive.
     */
    public MVector2 set_ (final float xx, final float yy) {
        x = xx;
        y = yy;

        return this;
    }

    /**
     * Set the values of this MVector2 to match another MVector2.
     * Destructive.
     */
    public MVector2 set_ (final MVector2 other) {
        x = other.x;
        y = other.y;

        return this;
    }

    /**
     * Set the values of this MVector2 to match another Vector2.
     * Destructive.
     */
    public MVector2 set_ (final Vector2 other) {
        x = other.x;
        y = other.y;

        return this;
    }

    // MAGNITUDE OPERATIONS

    public float getLengthSqr () {
        return x * x + y * y;
    }

    public float getLength () {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Set the length of this MVector2, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     *
     * @param length The desired new length
     */
    public MVector2 setLength (final float length) {
        float currentLength = getLength();
        if (currentLength == 0f) {
            return new MVector2(this);
        } else {
            return this.scale(length / currentLength);
        }
    }

    /**
     * Set the length of this MVector2, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     * Destructive.
     *
     * @param length The desired new length
     */
    public MVector2 setLength_ (final float length) {
        float currentLength = getLength();
        if (currentLength != 0f) {
            float factor = length / currentLength;
            x *= factor;
            y *= factor;
        }

        return this;
    }

    /**
     * Set the length of this MVector2 to 1.0f, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     */
    public MVector2 normalize () {
        float length = this.getLength();
        if (length != 0f) {
            return this.div(length);
        } else {
            return new MVector2(this);
        }
    }

    /**
     * Set the length of this MVector2 to 1.0f, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     * Destructive.
     */
    public MVector2 normalize_ () {

        float length = this.getLength();
        if (length != 0f) {
            x /= length;
            y /= length;
        }

        return this;
    }

    /**
     * Return a new MVector2 which has been truncated if its length
     * exceeds the limit.
     *
     * @param length Maximum length of Vector2
     * @return New (possibly truncated) Vector2
     */
    public MVector2 clampLength (final float length) {
        return (getLengthSqr() <= (length * length)) ? new MVector2(this) : this.setLength(length);
    }

    /**
     * Truncate the length of this MVector2 if the current length exceeds
     * the limit.
     * Destructive.
     *
     * @param length Maximum length of Vector2
     * @return New (possibly truncated) Vector2
     */
    public MVector2 clampLength_ (final float length) {
        return (getLengthSqr() <= (length * length)) ? this : this.setLength_(length);
    }

    /**
     * Return new MVector2 with absolute values.
     */
    public MVector2 abs () {
        return new MVector2(Math.abs(x), Math.abs(y));
    }

    /**
     * Make the components of this MVector2 absolute.
     * Destructive
     */
    public MVector2 abs_ () {
        x = Math.abs(x);
        y = Math.abs(y);

        return this;
    }

    /**
     * Negate MVector2.
     */
    public MVector2 negate () {
        return new MVector2(-x, -y);
    }

    /**
     * Negate this MVector2.
     * Destructive.
     */
    public MVector2 negate_ () {
        x = -x;
        y = -y;

        return this;
    }

    // 2D VECTOR OPERATIONS

    /**
     * Add this MVector2 to another MVector2.
     *
     * @return Result of Vector2 addition
     */
    public MVector2 add (final MVector2 other) {
        return new MVector2(x + other.x, y + other.y);
    }

    /**
     * Add another MVector2 to this MVector2 in place.
     * Destructive
     *
     * @return Result of Vector2 addition
     */
    public MVector2 add_ (final MVector2 other) {
        x += other.x;
        y += other.y;

        return this;
    }

    /**
     * Subtract another MVector2 from this MVector2.
     *
     * @return Result of Vector2 subtraction
     */
    public MVector2 sub (final MVector2 other) {
        return new MVector2(x - other.x, y - other.y);
    }

    /**
     * Subtract another MVector2 from this MVector2 in place.
     * Destructive
     *
     * @return Result of Vector2 subtraction
     */
    public MVector2 sub_ (final MVector2 other) {
        x -= other.x;
        y -= other.y;

        return this;
    }

    /**
     * Multiply another MVector2 with this MVector2 componentwise.
     *
     * @return Result of Vector2 Multiplication
     */
    public MVector2 mult (final MVector2 other) {
        return new MVector2(x * other.x, y * other.y);
    }

    /**
     * Multiply another MVector2 with this MVector2 componentwise in place.
     * Destructive
     *
     * @return Result of Vector2 Multiplication
     */
    public MVector2 mult_ (final MVector2 other) {
        x *= other.x;
        y *= other.y;

        return this;
    }

    /**
     * Divide this MVector2 by a scalar value.
     *
     * @return Result of Scalar division
     */
    public MVector2 div (final float divisor) {
        float invd = 1.0f / divisor;
        return new MVector2(x * invd, y * invd);
    }

    /**
     * Divide this MVector2 by a scalar value in place.
     * Destructive
     *
     * @return Result of Scalar division
     */
    public MVector2 div_ (final float divisor) {
        float invd = 1.0f / divisor;
        x *= invd;
        y *= invd;

        return this;
    }

    /**
     * Scale this MVector2.
     *
     * @return Result of Scalar Multiplication
     */
    public MVector2 scale (final float scale) {
        return new MVector2(x * scale, y * scale);
    }

    /**
     * Scale this MVector2 in place.
     * Destructive
     *
     * @return Result of Scalar Multiplication
     */
    public MVector2 scale_ (final float scale) {
        x *= scale;
        y *= scale;

        return this;
    }

    /**
     * Return the dot product of this MVector2 and another MVector2.
     *
     * @return Vector2 Dot Product
     */
    public float dot (final MVector2 other) {
        return x * other.x + y * other.y;
    }

    /**
     * Return the cross product of this MVector2 and another MVector2.
     *
     * @return Vector2 Cross Product
     */
    public float cross (final MVector2 other) {
        return x * other.y - y * other.x;
    }

    /**
     * Construct a randomly oriented 2D unit vector.
     */
    public static MVector2 random () {
        Random rnd = FRandom.getRandom();
        float max = FMath.TWO_PI;
        float theta = rnd.nextFloat() * max;

        return new MVector2((float) Math.cos(theta), (float) Math.sin(theta));
    }

    public static void setSeed (final long seed) {
        Random rnd = FRandom.getRandom();
        rnd.setSeed(seed);
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("<MVector2 %s %s>", x, y);
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

        if (other instanceof MVector2) {
            return compare((MVector2) other);
        }

        return false;
    }

    /**
     * Compare this MVector2 against another MVector2 exactly.
     *
     * @return true if this MVector2 exactly equals the other, false otherwise
     */
    public boolean compare (final MVector2 other) {

        return x == other.x && y == other.y;
    }

    /**
     * Compare this MVector2 against another MVector2 within a given tolerance.
     *
     * @param epsilon Tolerance within which MVector2 are considered equal
     * @return true if this MVector2 equals the other within given
     * tolerance, false otherwise
     */
    public boolean compare (final MVector2 other, final float epsilon) {
        if (Math.abs(x - other.x) > epsilon)
            return false;

        if (Math.abs(y - other.y) > epsilon)
            return false;

        return true;
    }

    /**
     * Pack values into raw float[].
     */
    public float[] toFloatArray () {
        return new float[]{x, y};
    }
}
