package sge.math;

import java.util.Random;

/**
 * 3 Dimensional Mutable Vector class
 * <p/>
 * The members of this class are not enforced to be final. Methods defined on the class
 * will return a new object and will not mutate the original, unless the method name is
 * suffixed with an underscore. (e.g. add_ will destructively update the current object
 * but add will not.)
 * <p/>
 * There are methods for converting between Vectors and Mutable Vectors, but operations
 * on each will not be mixed.
 */
public final class MVector3 {

    /** MVector3 X component. */
    public float x;

    /** MVector3 Y component. */
    public float y;

    /** MVector3 Z component. */
    public float z;

    /** Default Constructor. */
    public MVector3 () {
        x = y = z = 0f;
    }

    /** Fill Constructor. */
    public MVector3 (final float val) {
        x = y = z = val;
    }

    /** Value Constructor. */
    public MVector3 (final float xx, final float yy, final float zz) {
        x = xx;
        y = yy;
        z = zz;
    }

    /** Copy Constructor. */
    public MVector3 (final MVector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    /** MVector2 -> MVector3. */
    public MVector3 (final MVector2 v, final float zz) {
        x = v.x;
        y = v.y;
        z = zz;
    }

    /** Immutable Vector3 -> MVector3. */
    public MVector3 (final Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    /** Array Constructor. */
    public MVector3 (final float[] values) {
        x = values[0];
        y = values[1];
        z = values[2];
    }

    /**
     * Set the components of this MVector3 to zero.
     * Destructive.
     */
    public MVector3 zero_ () {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;

        return this;
    }

    /**
     * Set the values of this MVector3.
     * Destructive.
     */
    public MVector3 set_ (final float xx, final float yy, final float zz) {
        x = xx;
        y = yy;
        z = zz;

        return this;
    }

    /**
     * Set the values of this MVector3 to match another MVector3.
     * Destructive.
     */
    public MVector3 set_ (final MVector3 other) {
        x = other.x;
        y = other.y;
        z = other.z;

        return this;
    }

    /**
     * Set the values of this MVector3 to match another Vector3.
     * Destructive.
     */
    public MVector3 set_ (final Vector3 other) {
        x = other.x;
        y = other.y;
        z = other.z;

        return this;
    }

    // MAGNITUDE OPERATIONS

    public float getLengthSqr () {
        return x * x + y * y + z * z;
    }

    public float getLength () {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Set the length of this MVector3, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     *
     * @param length The desired new length
     */
    public MVector3 setLength (final float length) {
        float currentLength = getLength();
        if (currentLength == 0f) {
            return new MVector3(this);
        } else {
            return this.scale(length / currentLength);
        }
    }

    /**
     * Set the length of this MVector3, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     * Destructive.
     *
     * @param length The desired new length
     */
    public MVector3 setLength_ (final float length) {
        float currentLength = getLength();
        if (currentLength != 0f) {
            float factor = length / currentLength;
            x *= factor;
            y *= factor;
            z *= factor;
        }

        return this;
    }

    /**
     * Set the length of this MVector3 to 1.0f, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     */
    public MVector3 normalize () {
        float length = this.getLength();
        if (length == 0f) {
            return new MVector3(this);
        } else {
            return this.div(length);
        }
    }

    /**
     * Set the length of this MVector3 to 1.0f, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     * Destructive.
     */
    public MVector3 normalize_ () {
        float length = this.getLength();
        if (length != 0f) {
            x /= length;
            y /= length;
            z /= length;
        }

        return this;
    }

    /**
     * Return a new MVector3 which has been truncated if its length
     * exceeds the limit.
     *
     * @param length Maximum length of Vector3
     * @return New (possibly truncated) Vector3
     */
    public MVector3 clampLength (final float length) {
        return (getLengthSqr() <= (length * length)) ? new MVector3(this) : this.setLength(length);
    }

    /**
     * Truncate the length of this MVector3 if the current length exceeds
     * the limit.
     * Destructive.
     *
     * @param length Maximum length of Vector3
     * @return New (possibly truncated) Vector3
     */
    public MVector3 clampLength_ (final float length) {
        return (getLengthSqr() <= (length * length)) ? this : this.setLength_(length);
    }

    /**
     * Return new MVector3 with absolute values.
     */
    public MVector3 abs () {
        return new MVector3(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    /**
     * Make the components of this MVector3 absolute.
     * Destructive
     */
    public MVector3 abs_ () {
        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);

        return this;
    }

    /**
     * Negate MVector3.
     */
    public MVector3 negate () {
        return new MVector3(-x, -y, -z);
    }

    /**
     * Negate this MVector3.
     * Destructive.
     */
    public MVector3 negate_ () {
        x = -x;
        y = -y;
        z = -z;

        return this;
    }

    // 3D VECTOR OPERATIONS

    /**
     * Add this MVector3 to another MVector3.
     *
     * @return Result of Vector3 addition
     */
    public MVector3 add (final MVector3 other) {
        return new MVector3(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Add another MVector3 to this MVector3 in place.
     * Destructive
     *
     * @return Result of Vector3 addition
     */
    public MVector3 add_ (final MVector3 other) {
        x += other.x;
        y += other.y;
        z += other.z;

        return this;
    }

    /**
     * Subtract another MVector3 from this MVector3.
     *
     * @return Result of Vector3 subtraction
     */
    public MVector3 sub (final MVector3 other) {
        return new MVector3(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Subtract another MVector3 from this MVector3 in place.
     * Destructive
     *
     * @return Result of Vector3 subtraction
     */
    public MVector3 sub_ (final MVector3 other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;

        return this;
    }

    /**
     * Multiply another MVector3 with this MVector3 componentwise.
     *
     * @return Result of Vector3 Multiplication
     */
    public MVector3 mult (final MVector3 other) {
        return new MVector3(x * other.x, y * other.y, z * other.z);
    }

    /**
     * Multiply another MVector3 with this MVector3 componentwise in place.
     * Destructive
     *
     * @return Result of Vector3 Multiplication
     */
    public MVector3 mult_ (final MVector3 other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;

        return this;
    }

    /**
     * Divide this MVector3 by a scalar value.
     *
     * @return Result of Scalar division
     */
    public MVector3 div (final float divisor) {
        float invd = 1.0f / divisor;
        return new MVector3(x * invd, y * invd, z * invd);
    }

    /**
     * Divide this MVector3 by a scalar value in place.
     * Destructive
     *
     * @return Result of Scalar division
     */
    public MVector3 div_ (final float divisor) {
        float invd = 1.0f / divisor;
        x *= invd;
        y *= invd;
        z *= invd;

        return this;
    }

    /**
     * Scale this MVector3.
     *
     * @return Result of Scalar Multiplication
     */
    public MVector3 scale (final float scale) {
        return new MVector3(x * scale, y * scale, z * scale);
    }

    /**
     * Scale this MVector3 in place.
     * Destructive
     *
     * @return Result of Scalar Multiplication
     */
    public MVector3 scale_ (final float scale) {
        x *= scale;
        y *= scale;
        z *= scale;

        return this;
    }

    /**
     * Return the dot product of this MVector3 and another MVector3.
     *
     * @return Vector3 Dot Product
     */
    public float dot (final MVector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Return the cross product of this MVector3 and another MVector3.
     *
     * @return Vector3 Cross Product
     */
    public MVector3 cross (final MVector3 other) {
        return new MVector3(y * other.z - z * other.y,
                            z * other.x - x * other.z,
                            x * other.y - y * other.x);
    }

    /**
     * Return the cross product of this MVector3 and another MVector3 in place.
     * Destructive.
     *
     * @return Vector3 Cross Product
     */
    public MVector3 cross_ (final MVector3 other) {
        float _x = x, _y = y, _z = z;

        x = _y * other.z - _z * other.y;
        y = _z * other.x - _x * other.z;
        z = _x * other.y - _y * other.x;

        return this;
    }

    /**
     * Create a unit Vector3 with a random orientation.
     * TODO: Overload with seeded variation.
     */
    public static MVector3 random () {
        Random rnd = FRandom.getRandom();
        float max = FMath.TWO_PI;
        float inclination = rnd.nextFloat() * max;
        float azimuth = rnd.nextFloat() * max;
        return new MVector3(
                (float) Math.sin(inclination) * (float) Math.cos(azimuth),
                (float) Math.cos(inclination) * (float) Math.sin(azimuth),
                (float) Math.cos(inclination)
        );
    }

    public static void setSeed (long seed) {
        Random rnd = FRandom.getRandom();
        rnd.setSeed(seed);
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("<MVector3 %s %s %s>", x, y, z);
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

        if (other instanceof MVector3) {
            return compare((MVector3) other);
        }

        return false;
    }

    /**
     * Compare this MVector3 against another MVector3 exactly.
     *
     * @return true if this MVector3 exactly equals the other, false otherwise
     */
    public boolean compare (final MVector3 other) {

        return x == other.x && y == other.y && z == other.z;
    }

    /**
     * Compare this MVector3 against another MVector3 within a given tolerance.
     *
     * @param epsilon Tolerance within which MVector3 are considered equal
     * @return true if this MVector3 equals the other within given
     * tolerance, false otherwise
     */
    public boolean compare (final MVector3 other, final float epsilon) {

        if (Math.abs(x - other.x) > epsilon)
            return false;

        if (Math.abs(y - other.y) > epsilon)
            return false;

        if (Math.abs(z - other.z) > epsilon)
            return false;

        return true;
    }

    /**
     * Pack values into raw float[].
     */
    public float[] toFloatArray () {
        return new float[]{x, y, z};
    }

    // Swizzling

    public MVector2 xy () {
        return new MVector2(x, y);
    }

    public MVector2 yz () {
        return new MVector2(y, z);
    }

    public MVector2 xz () {
        return new MVector2(x, z);
    }

    /**
     * Convert MVector3 to homogeneous MVector4 point.
     */
    public MVector4 toPoint () {
        return new MVector4(x, y, z, 1.0f);
    }

    /**
     * Convert MVector3 to homogeneous MVector4 direction.
     */
    public MVector4 toDirection () {
        return new MVector4(x, y, z, 0.0f);
    }
}
