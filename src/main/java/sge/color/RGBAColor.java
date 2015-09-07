package sge.color;

import sge.math.FMath;
import sge.math.Vector3;
import sge.math.Vector4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * RGBAColor
 * Red, Green, Blue, Alpha values stored as float values in the range 0..1.
 */
public class RGBAColor {

    public static final int SIZE = 4;
    public static final int BYTE_SIZE = SIZE * FMath.FLOAT_SIZE;

    public static final RGBAColor BLACK = new RGBAColor(0.0f, 0.0f, 0.0f);
    public static final RGBAColor WHITE = new RGBAColor(1.0f, 1.0f, 1.0f);
    public static final RGBAColor GRAY = new RGBAColor(0.7f, 0.7f, 0.7f);
    public static final RGBAColor RED = new RGBAColor(1.0f, 0.0f, 0.0f);
    public static final RGBAColor GREEN = new RGBAColor(0.0f, 1.0f, 0.0f);
    public static final RGBAColor BLUE = new RGBAColor(0.0f, 0.0f, 1.0f);
    public static final RGBAColor CYAN = new RGBAColor(0.0f, 1.0f, 1.0f);
    public static final RGBAColor MAGENTA = new RGBAColor(1.0f, 0.0f, 1.0f);
    public static final RGBAColor YELLOW = new RGBAColor(1.0f, 1.0f, 0.0f);

    public final float r;
    public final float g;
    public final float b;
    public final float a;

    /**
     * RGB Value Constructor.
     * Constructs an Opaque Color.
     */
    public RGBAColor (float rr, float gg, float bb) {
        this(rr, gg, bb, 1.0f);
    }

    /**
     * RGBA Value Constructor.
     * Constructs Color with Alpha channel.
     */
    public RGBAColor (float rr, float gg, float bb, float aa) {
        r = rr;
        g = gg;
        b = bb;
        a = aa;
    }

    /**
     * Copy Constructor.
     *
     * @param color
     */
    public RGBAColor (RGBAColor color) {
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
    }

    /**
     * Mix two colors by linear interpolation.
     *
     * @param other another RGBAColor
     * @param ratio How much of other color to mix.
     */
    public RGBAColor lerp (RGBAColor other, float ratio) {
        float red = FMath.lerp(r, other.r, ratio);
        float gre = FMath.lerp(g, other.g, ratio);
        float blu = FMath.lerp(b, other.b, ratio);
        float alp = FMath.lerp(a, other.a, ratio);

        return new RGBAColor(red, gre, blu, alp);
    }

    /**
     * Add another color's values without exceeding 1.0.
     *
     * @param other
     * @return
     */
    public RGBAColor add (RGBAColor other) {
        float red = FMath.clamp(r + other.r, 0.0f, 1.0f);
        float gre = FMath.clamp(g + other.g, 0.0f, 1.0f);
        float blu = FMath.clamp(b + other.b, 0.0f, 1.0f);
        float alp = FMath.clamp(a + other.a, 0.0f, 1.0f);

        return new RGBAColor(red, gre, blu, alp);
    }

    /**
     * Convert the color to int values in the Range 0..255.
     * {@link RGBAColor32}
     */
    public RGBAColor32 to32Bit () {
        return new RGBAColor32(
                (short) Math.floor(FMath.lerp(0.0f, 255.0f, r)),
                (short) Math.floor(FMath.lerp(0.0f, 255.0f, g)),
                (short) Math.floor(FMath.lerp(0.0f, 255.0f, b)),
                (short) Math.floor(FMath.lerp(0.0f, 255.0f, a)));
    }

    public boolean isOpaque () {
        return a == 1.0f;
    }

    public boolean isHidden () {
        return a == 0.0f;
    }

    private static String expandColorCode (String code) {
        code = code.startsWith("#") ? code.substring(1).toUpperCase() : code.toUpperCase();
        code = code.replaceAll("[^0123456789ABCDEF]", "");

        if (code.length() == 3) {
            return "" + code.charAt(0) + code.charAt(0) + code.charAt(1) + code.charAt(1) +
                    code.charAt(2) + code.charAt(2) + "FF";
        }

        if (code.length() == 4) {
            return "" + code.charAt(0) + code.charAt(0) + code.charAt(1) + code.charAt(1) +
                    code.charAt(2) + code.charAt(2) + code.charAt(3) + code.charAt(3);
        }

        if (code.length() == 6) {
            return code + "FF";
        }

        if (code.length() == 8) {
            return code;
        }

        return "000000FF";
    }

    /**
     * Create a new RGBA Color value by parsing a Hexadecimal color string.
     * <p/>
     * The string may begin with a leading #, though this is optional. Accepted formats are:
     * #RGB, #RGBA, #RRGGBB, #RRGGBBAA.
     * In each case, if an alpha value is not given, it will default to 1.0 (fully opaque).
     *
     * @param {String} hex A color value encoded as a hexadecimal string.
     */
    public static RGBAColor fromHexCode (final String hex) {
        int r, g, b, a;
        String code = expandColorCode(hex);

        int val = (int) Long.parseLong(code, 16);

        r = (val >> 24) & 0xFF;
        g = (val >> 16) & 0xFF;
        b = (val >> 8) & 0xFF;
        a = val & 0xFF;

        return new RGBAColor(FMath.toRatio(r, 0, 255), FMath.toRatio(g, 0, 255),
                             FMath.toRatio(b, 0, 255), FMath.toRatio(a, 0, 255));
    }

    public static RGBAColor fromResource (final String filename, final String color) {
        try {
            Scanner s = new Scanner(new File(filename));

            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.startsWith(color)) {
                    String[] words = line.split(" ", 4);
                    if (words.length >= 4) {
                        return new RGBAColor(Float.parseFloat(words[1]), Float.parseFloat(words[2]), Float.parseFloat(words[3]));
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Create an RGBA color from hue, saturation, lightness values.
     *
     * @param hue
     * @param sat
     * @param value
     * @return RGBAColor New color
     */
    public static RGBAColor fromHSL (final float hue, final float sat, final float value) {
        float chroma = (1f - Math.abs(2f * value - 1f)) * sat;
        float hue_split = hue / HSLColor.HUE_STEP;
        float x = chroma * (1f - Math.abs(hue_split % 2f - 1f));
        float r = 0.0f, g = 0.0f, b = 0.0f;

        if (hue_split >= 0 && hue_split < 1) {
            r = chroma;
            g = x;
            b = 0.0f;
        } else if (hue_split >= 1 && hue_split < 2) {
            r = x;
            g = chroma;
            b = 0.0f;
        } else if (hue_split >= 2 && hue_split < 3) {
            r = 0.0f;
            g = chroma;
            b = x;
        } else if (hue_split >= 3 && hue_split < 4) {
            r = 0.0f;
            g = x;
            b = chroma;
        } else if (hue_split >= 4 && hue_split < 5) {
            r = x;
            g = 0.0f;
            b = chroma;
        } else if (hue_split >= 5 && hue_split < 6) {
            r = chroma;
            g = 0.0f;
            b = x;
        }

        float m = value - 0.5f * chroma;

        return new RGBAColor(r + m, g + m, b + m);
    }

    public HSLColor toHSL () {
        float min = Math.min(r, Math.min(g, b));
        float max = Math.max(r, Math.max(g, b));
        float delta = max - min;

        float lightness = max / 2f;
        float saturation = 0f;
        float hue = 0f;

        if (max == 0) {
            return new HSLColor(-1.0f, 0.0f, lightness);
        }

        saturation = delta / max;

        if (r == max) {
            hue = (g - b) / delta;
        } else if (g == max) {
            hue = 2 + (b - r) / delta;
        } else {
            hue = 4 + (r - g) / delta;
        }

        hue *= HSLColor.HUE_STEP;
        if (hue < 0) hue += HSLColor.MAX_HUE;

        hue = hue % HSLColor.MAX_HUE;

        return new HSLColor(hue, saturation, lightness);
    }

    /**
     * Pack RGB values into raw float[].
     */
    public float[] toFloatRGB () {
        return new float[]{r, g, b};
    }

    /**
     * Pack RGBA values into raw float[].
     */
    public float[] toFloatRGBA () {
        return new float[]{r, g, b, a};
    }

    /**
     * Pack RGB values into Vector3.
     */
    public Vector3 toVectorRGB () {
        return new Vector3(r, g, b);
    }

    /**
     * Pack RGBA values into Vector4.
     */
    public Vector4 toVectorRGBA () {
        return new Vector4(r, g, b, a);
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("%s,%s,%s,%s", r, g, b, a);
    }

    @Override
    public boolean equals (Object other) {
        if (this == other) return true;

        if (other instanceof RGBAColor) {
            return compare((RGBAColor) other);
        }

        return false;
    }

    public boolean compare (RGBAColor other) {
        return r == other.r && g == other.g && b == other.b && a == other.a;
    }

    /**
     * Approximate Comparison.
     */
    public boolean compare (RGBAColor other, float threshold) {
        if (Math.abs(r - other.r) > threshold)
            return false;

        if (Math.abs(g - other.g) > threshold)
            return false;

        if (Math.abs(b - other.b) > threshold)
            return false;

        if (Math.abs(a - other.a) > threshold)
            return false;

        return true;
    }
}
