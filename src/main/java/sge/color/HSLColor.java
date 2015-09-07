package sge.color;

import sge.math.FMath;

/**
 * Color class using Hue, Saturation, Lightness components.
 */
public class HSLColor {

    static final float MAX_HUE = 360.0f;
    static final float HUE_STEP = MAX_HUE / 6f;

    private final float hue;
    private final float saturation;
    private final float lightness;

    /** Default Constructor */
    public HSLColor () {
        this(0.0f, 0.0f, 0.0f);
    }

    /**
     * Value Constructor.
     * Hue is in the range 0..360
     */
    public HSLColor (float hue, float saturation, float lightness) {
        this.hue = FMath.clamp(hue, 0.0f, 360.0f);
        this.saturation = saturation;
        this.lightness = lightness;
    }

    /** Copy Constructor. */
    public HSLColor (HSLColor other) {
        this(other.hue, other.saturation, other.lightness);
    }

    /**
     * Linear interpolation to other HSL color.
     */
    public HSLColor lerp (HSLColor other, float ratio) {
        return new HSLColor(FMath.lerp(hue, other.hue, ratio),
                            FMath.lerp(saturation, other.saturation, ratio),
                            FMath.lerp(lightness, other.lightness, ratio));
    }

    /**
     * Convert this color to equivalent RGBA values.
     *
     * @return New RGBA color.
     */
    public RGBAColor toRGBA () {
        return RGBAColor.fromHSL(hue, saturation, lightness);
    }

    @Override
    public String toString () {
        return String.format("<HSLColor %s,%s,%s>", hue, saturation, lightness);
    }
}
