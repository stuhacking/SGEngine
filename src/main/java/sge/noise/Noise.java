package sge.noise;

import sge.math.FMath;

/**
 * Noise utility functions.
 */
public final class Noise {

    /**
     * Simplex noise function for 2D int arguments.
     */
    public static final float noise (final int x, final int y) {
        int nn = hash(x, y);
        return 1.0f - (nn / 1073741824.0f);
    }

    /**
     * Get the value of a point sampled from the gradient of a square bounded
     * by (x,y) .. (x+1, y+1).
     *
     * @return Smoothed noise value.
     */
    public static float smoothNoise (final float x) {
        int xf = (int) x; // x floor
        float xFrac = x - xf;

        float nw = Noise.noise(xf, 0);
        float sw = Noise.noise(xf + 1, 0);

        return FMath.cosInterpolate(nw, sw, xFrac);
    }

    /**
     * Get the value of a point sampled from the gradient of a square bounded
     * by (x,y) .. (x+1, y+1).
     *
     * @return Smoothed noise value.
     */
    public static float smoothNoise (final float x, final float y) {
        int xf = (int) x; // x floor
        int yf = (int) y; // y floor
        float xFrac = x - xf;
        float yFrac = y - yf;

        float nw = noise(xf, yf);
        float ne = noise(xf, yf + 1);
        float sw = noise(xf + 1, yf);
        float se = noise(xf + 1, yf + 1);

        float v1 = FMath.cosInterpolate(nw, sw, xFrac);
        float v2 = FMath.cosInterpolate(ne, se, xFrac);

        return FMath.cosInterpolate(v1, v2, yFrac);
    }

    /**
     * Hashing function for 2d noise Pseudo-RNG.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return float result of hashing x and y
     */
    public static final int hash (final int x, final int y) {
        int n = x + y * 57;
        n = (n << 13) ^ n;
        int nn = (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;
        return nn;
    }
}
