package sge.math;

/**
 * (Badly-named) Math utility class.
 * Provides some float versions of standard Math functions, along
 * with other utilities.
 */
public abstract class FMath {

    /**
     * Size of a float in bytes.
     * This is mainly used for allocating arrays and buffers for data that is sent to the GPU.
     * (Hopefully it turns out to be 4.)
     */
    public final static int FLOAT_SIZE = Float.SIZE / Byte.SIZE; // Size of Float Representation in Bytes

    public final static float PI = (float) Math.PI;
    public final static float TWO_PI = 2f * PI;
    public final static float HALF_PI = 0.5f * PI;

    public final static float E = (float) Math.E;

    public final static float degreesToRadians = PI / 180.0f;
    public final static float radiansToDegrees = 180.0f / PI;

    /**
     * Convert degrees to radians.
     */
    public static float toRadians (final float degrees) {
        return degrees * degreesToRadians;
    }

    /**
     * Convert radians to degrees.
     */
    public static float toDegrees (final float radians) {
        return radians * radiansToDegrees;
    }

    /**
     * Clamp value to the bounds of a range.
     */
    public static float clamp (final float value, final float min, final float max) {
        if (value <= min)
            return min;

        if (value >= max)
            return max;

        return value;
    }

    /**
     * Clamp value to the bounds of a range.
     */
    public static int clamp (final int value, final int min, final int max) {
        if (value <= min)
            return min;

        if (value >= max)
            return max;

        return value;
    }

    /**
     * Linear interpolation.
     */
    public static float lerp (final float min, final float max, final float ratio) {
        return min + (ratio * (max - min));
    }

    /**
     * Clamped Linear interpolation
     */
    public static float clampLerp (final float min, final float max, final float ratio) {
        return lerp(min, max, clamp(ratio, 0f, 1f));
    }

    /**
     * Cosine Interpolation.
     */
    public static float cosInterpolate (final float min, final float max, final float ratio) {
        float ft = ratio * PI;
        float f = (1.0f - (float) Math.cos(ft)) * 0.5f;
        return min * (1.0f - f) + max * f;
    }

    /**
     * Convert a value along a range to a ratio. Result may lie outside the range of 0..1.
     */
    public static float toRatio (final float value, final float min, final float max) {
        return (value - min) / (max - min);
    }

    /**
     * Convert a value along a range to a ratio. Result will be clamped to 0..1 range.
     */
    public static float toClampedRatio (final float value, final float min, final float max) {
        return clamp(toRatio(value, min, max), 0.0f, 1.0f);
    }

    /**
     * Scale value to fit within a new range maintaining its relative
     * position.
     */
    public static float fit (final float value, final float oMin, final float oMax,
                             final float nMin, final float nMax) {
        return lerp(nMin, nMax, toRatio(value, oMin, oMax));
    }

    /**
     * Find the next even power of 2 greater than or equal to value.
     */
    public static int nearest2Pow (final int value) {
        int aVal = Math.abs(value);
        int result = 2;
        while (result < aVal) {
            result *= 2;
        }
        return result;
    }

}
