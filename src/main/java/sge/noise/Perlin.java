package sge.noise;

import sge.math.FMath;

/**
 * Perlin2D Noise.
 * <p/>
 * Cloudy looking smoothed noise.
 */
public class Perlin {

    public int seed;

    public float frequency;
    public float amplitude;
    public float persistence;
    public int octaves;

    /** Default Constructor. */
    public Perlin () {
        this(0.0f, 0.0f, 0.0f, 1, System.currentTimeMillis());
    }

    /** Seeded Random Constructor. */
    public Perlin (final long seed) {
        this(0.0f, 0.0f, 0.0f, 1, seed);
    }

    /**
     * Value Constructor.
     *
     * @param p_freq   Scale of the Noise
     * @param p_amp   Exaggerate contrast of the Noise.
     * @param p_pers Amount of Noise scale increase per iteration.
     * @param p_oct     Number of iterations (More tends to produce fluffier results).
     */
    public Perlin (final float p_freq, final float p_amp,
                   final float p_pers, final int p_oct) {
        this(p_freq, p_amp, p_pers, p_oct, System.currentTimeMillis());
    }

    /**
     * Value Constructor.
     *
     * @param p_freq   Scale of the Noise
     * @param p_amp   Contrastiness of the Noise.
     * @param p_pers Amount of Noise scale increase per iteration.
     * @param p_oct     Number of iterations (More tends to produce fluffier results).
     * @param p_seed        Random Number seed
     */
    public Perlin (final float p_freq, final float p_amp,
                   final float p_pers, final int p_oct, final long p_seed) {
        this.frequency = p_freq;
        this.amplitude = p_amp;
        this.persistence = p_pers;
        this.octaves = p_oct;

        int _seed = (int) (p_seed & 0xFF);

        this.seed = 2 + _seed * _seed;
    }

    /**
     * 1D Perlin noise.
     */
    public float get (final float x) {
        float t = 0.0f;
        float freq = frequency;
        float amp = 1.0f;

        for (int o = 0; o < octaves; o++) {
            t += amp * Noise.smoothNoise(x * freq + seed);
            freq *= 2.0f;
            amp *= persistence;
        }

        return FMath.clamp(amplitude * t, -1f, 1f);
    }

    /**
     * 2D Perlin noise.
     */
    public float get (final float x, final float y) {
        float t = 0.0f;
        float freq = frequency;
        float amp = 1.0f;

        for (int o = 0; o < octaves; o++) {
            t += amp * Noise.smoothNoise(x * freq + seed, y * freq + seed);
            freq *= 2.0f;
            amp *= persistence;
        }

        return FMath.clamp(amplitude * t, -1f, 1f);
    }
}
