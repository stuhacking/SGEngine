package sge.color;

import sge.math.FMath;

/**
 * RGBAColor32 class.
 * Red, Green, Blue, Alpha values stored as values in the range 0..255
 */
public class RGBAColor32 {

    public final int r;
    public final int g;
    public final int b;
    public final int a;

    /**
     * RGB Value Constructor.
     * Construct an Opaque color.
     */
    public RGBAColor32 (final int rr, final int gg, final int bb) {
        this(rr, gg, bb, 255);
    }

    /**
     * RGBA Value Constructor.
     */
    public RGBAColor32 (final int rr, final int gg, final int bb, final int aa) {
        r = rr;
        g = gg;
        b = bb;
        a = aa;
    }

    /**
     * Copy Constructor.
     */
    public RGBAColor32 (final RGBAColor32 color) {
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
    }

    public RGBAColor toFloatingPoint () {
        return new RGBAColor(
                FMath.toRatio(r, 0, 255),
                FMath.toRatio(g, 0, 255),
                FMath.toRatio(b, 0, 255),
                FMath.toRatio(a, 0, 255));
    }

    @Override
    public String toString () {
        return String.format("<RGBAColor32 %d,%d,%d,%d>", r, g, b, a);
    }
}
