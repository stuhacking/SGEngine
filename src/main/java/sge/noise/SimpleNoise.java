package sge.noise;

import sge.math.FMath;

/**
 * Simple Noise.
 */
public class SimpleNoise {

    public int seed;

    public float frequency;
    public float amplitude;

    /**
     * Default Constructor.
     */
    public SimpleNoise () {
        this(1.0f, 1.0f, System.currentTimeMillis());
    }

    /**
     * Default Constructor with seed.
     */
    public SimpleNoise (final long p_seed) {
        this(1.0f, 1.0f, p_seed);
    }

    /**
     * Value Constructor.
     */
    public SimpleNoise (final float p_freq, final float p_amp) {
        this(p_freq, p_amp, System.currentTimeMillis());
    }

    /**
     * Value Constructor with seed.
     */
    public SimpleNoise (final float p_freq, final float p_amp, final long p_seed) {
        int _seed = (int) (p_seed & 0xFF);
        this.seed = 2 + _seed * _seed;
        this.frequency = p_freq;
        this.amplitude = p_amp;
    }

    /**
     * 1D Simple Noise.
     */
    public float get (final float x) {
        return FMath.clamp(amplitude * Noise.noise((int) (x * frequency) + seed, 0), -1f, 1f);
    }

    /**
     * 2D Simple Noise.
     */
    public float get (final float x, final float y) {
        return FMath.clamp(amplitude * Noise.noise((int) (x * frequency) + seed, (int) (y * frequency) + seed), -1f, 1f);
    }
}
