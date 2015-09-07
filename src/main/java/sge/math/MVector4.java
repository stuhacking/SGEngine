package sge.math;

/**
 * 4 Dimensional Mutable Vector class
 * <p/>
 * The members of this class are not enforced to be final. Methods defined on the class
 * will return a new object and will not mutate the original, unless the method name is
 * suffixed with an underscore. (e.g. add_ will destructively update the current object
 * but add will not.)
 * <p/>
 * There are methods for converting between Vectors and Mutable Vectors, but operations
 * on each will not be mixed.
 */
public final class MVector4 {

    /** MVector4 X component. */
    public float x;

    /** MVector4 Y component. */
    public float y;

    /** MVector4 Z component. */
    public float z;

    /** MVector4 W component. */
    public float w;

    /** Default Constructor. */
    public MVector4 () {
        x = y = z = w = 0f;
    }

    /** Fill Constructor. */
    public MVector4 (final float val) {
        x = y = z = w = val;
    }

    /** Value Constructor. */
    public MVector4 (final float xx, final float yy, final float zz, final float ww) {
        x = xx;
        y = yy;
        z = zz;
        w = ww;
    }

    /** Copy Constructor. */
    public MVector4 (MVector4 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
    }

    /** MVector2 -> MVector4. */
    public MVector4 (final MVector2 v, final float zz, final float ww) {
        x = v.x;
        y = v.y;
        z = zz;
        w = ww;
    }

    /** MVector3 -> MVector4. */
    public MVector4 (final MVector3 v, final float ww) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = ww;
    }

    /** Immutable Vector4 -> MVector4. */
    public MVector4 (final Vector4 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
    }

    /** Array Constructor. */
    public MVector4 (final float[] values) {
        x = values[0];
        y = values[1];
        z = values[2];
        w = values[3];
    }

    /**
     * Set the components of this MVector4 to zero.
     * Destructive.
     */
    public MVector4 zero_ () {
        x = y = z = w = 0.0f;

        return this;
    }

    /**
     * Set the values of this MVector4.
     * Destructive.
     */
    public MVector4 set_ (final float xx, final float yy, final float zz, final float ww) {
        x = xx;
        y = yy;
        z = zz;
        w = ww;

        return this;
    }

    /**
     * Set the values of this MVector4 to match another MVector4.
     * Destructive.
     */
    public MVector4 set_ (final MVector4 other) {
        x = other.x;
        y = other.y;
        z = other.z;
        w = other.w;

        return this;
    }

    /**
     * Set the values of this MVector4 to match another Vector4.
     * Destructive.
     */
    public MVector4 set_ (final Vector4 other) {
        x = other.x;
        y = other.y;
        z = other.z;
        w = other.w;

        return this;
    }

    // MAGNITUDE OPERATIONS

    public float getLengthSqr () {
        return x * x + y * y + z * z + w * w;
    }

    public float getLength () {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * Set the length of this MVector4, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     *
     * @param length The desired new length
     */
    public MVector4 setLength (final float length) {
        float currentLength = getLength();
        if (currentLength == 0f) {
            return new MVector4(this);
        } else {
            return this.scale(length / currentLength);
        }
    }

    /**
     * Set the length of this MVector4, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     * Destructive.
     *
     * @param length The desired new length
     */
    public MVector4 setLength_ (final float length) {
        float currentLength = getLength();
        if (currentLength != 0f) {
            float factor = length / currentLength;
            x *= factor;
            y *= factor;
            z *= factor;
            w *= factor;
        }

        return this;
    }

    /**
     * Set the length of this MVector4 to 1.0f, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     */
    public MVector4 normalize () {
        float currentLength = this.getLength();
        if (currentLength == 0f) {
            return new MVector4(this);
        } else {
            return this.div(currentLength);
        }
    }

    /**
     * Set the length of this MVector4 to 1.0f, maintaining direction. If the
     * length is currently 0.0f then we don't attempt to guess.
     * Destructive.
     */
    public MVector4 normalize_ () {

        float length = this.getLength();
        if (length != 0f) {
            x /= length;
            y /= length;
            z /= length;
            w /= length;
        }

        return this;
    }

    /**
     * Return a new MVector4 which has been truncated if its length
     * exceeds the limit.
     *
     * @param length Maximum length of Vector4
     * @return New (possibly truncated) Vector4
     */
    public MVector4 clampLength (final float length) {
        return (getLengthSqr() <= (length * length)) ? new MVector4(this) : this.setLength(length);
    }

    /**
     * Truncate the length of this MVector4 if the current length exceeds
     * the limit.
     * Destructive.
     *
     * @param length Maximum length of Vector4
     * @return New (possibly truncated) Vector4
     */
    public MVector4 clampLength_ (final float length) {
        return (getLengthSqr() <= (length * length)) ? this : this.setLength_(length);
    }

    /**
     * Return new MVector4 with absolute values.
     */
    public MVector4 abs () {
        return new MVector4(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }

    /**
     * Make the components of this MVector4 absolute.
     * Destructive
     */
    public MVector4 abs_ () {
        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);
        w = Math.abs(w);

        return this;
    }

    /**
     * Negate MVector4.
     */
    public MVector4 negate () {
        return new MVector4(-x, -y, -z, -w);
    }

    /**
     * Negate this MVector4.
     * Destructive.
     */
    public MVector4 negate_ () {
        x = -x;
        y = -y;
        z = -z;
        w = -w;

        return this;
    }

    // 4D VECTOR OPERATIONS

    /**
     * Add this MVector4 to another MVector4.
     *
     * @return Result of Vector4 addition
     */
    public MVector4 add (final MVector4 other) {
        return new MVector4(x + other.x, y + other.y, z + other.z, w + other.w);
    }

    /**
     * Add another MVector4 to this MVector4 in place.
     * Destructive
     *
     * @return Result of Vector4 addition
     */
    public MVector4 add_ (final MVector4 other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;

        return this;
    }

    /**
     * Subtract another MVector4 from this MVector4.
     *
     * @return Result of Vector4 subtraction
     */
    public MVector4 sub (final MVector4 other) {
        return new MVector4(x - other.x, y - other.y, z - other.z, w - other.w);
    }

    /**
     * Subtract another MVector4 from this MVector4 in place.
     * Destructive
     *
     * @return Result of Vector4 subtraction
     */
    public MVector4 sub_ (final MVector4 other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        w -= other.w;

        return this;
    }

    /**
     * Multiply another MVector4 with this MVector4 componentwise.
     *
     * @return Result of Vector4 Multiplication
     */
    public MVector4 mult (final MVector4 other) {
        return new MVector4(x * other.x, y * other.y, z * other.z, w * other.w);
    }

    /**
     * Multiply another MVector4 with this MVector4 componentwise in place.
     * Destructive
     *
     * @return Result of Vector4 Multiplication
     */
    public MVector4 mult_ (final MVector4 other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        w *= other.w;

        return this;
    }

    /**
     * Divide this MVector4 by a scalar value.
     *
     * @return Result of Scalar division
     */
    public MVector4 div (final float divisor) {
        float invd = 1.0f / divisor;
        return new MVector4(x * invd, y * invd, z * invd, w * invd);
    }

    /**
     * Divide this MVector4 by a scalar value in place.
     * Destructive
     *
     * @return Result of Scalar division
     */
    public MVector4 div_ (final float divisor) {
        float invd = 1.0f / divisor;
        x *= invd;
        y *= invd;
        z *= invd;
        w *= invd;

        return this;
    }

    /**
     * Scale this MVector4.
     *
     * @return Result of Scalar Multiplication
     */
    public MVector4 scale (final float scale) {
        return new MVector4(x * scale, y * scale, z * scale, w * scale);
    }

    /**
     * Scale this MVector4 in place.
     * Destructive
     *
     * @return Result of Scalar Multiplication
     */
    public MVector4 scale_ (final float scale) {
        x *= scale;
        y *= scale;
        z *= scale;
        w *= scale;

        return this;
    }

    /**
     * Return the dot product of this MVector4 and another MVector4.
     *
     * @return Vector4 Dot Product
     */
    public float dot (final MVector4 other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    @Override
    public String toString () {
        return String.format("<MVector4 %s %s %s %s>", x, y, z, w);
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

        if (other instanceof MVector4) {
            return compare((MVector4) other);
        }

        return false;
    }

    /**
     * Compare this MVector4 against another MVector4 exactly.
     *
     * @return true if this MVector4 exactly equals the other, false otherwise
     */
    public boolean compare (final MVector4 other) {
        return x == other.x && y == other.y && z == other.z && w == other.w;
    }

    /**
     * Compare this MVector4 against another MVector4 within a given tolerance.
     *
     * @param epsilon Tolerance within which MVector4 are considered equal
     * @return true if this MVector4 equals the other within given
     * tolerance, false otherwise
     */
    public boolean compare (final MVector4 other, final float epsilon) {
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
     * Pack values into raw float[].
     */
    public float[] toFloatArray () {
        return new float[]{x, y, z, w};
    }

    // Swizzling

    public MVector3 xyz () {
        return new MVector3(x, y, z);
    }

    public MVector2 xy () {
        return new MVector2(x, y);
    }

    public MVector2 yz () {
        return new MVector2(y, z);
    }

    public MVector2 xz () {
        return new MVector2(x, z);
    }
}
