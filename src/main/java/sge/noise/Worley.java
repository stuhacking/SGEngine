package sge.noise;

import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeSet;

import sge.math.FMath;
import sge.math.Vector2;

/**
 * Worley Voronoi/Cellular Noise.
 */
public class Worley {

    private static final int MAX_FEATURE_POINTS = 8;

    public int seed;

    public float frequency;
    public float amplitude;

    private DistanceFunction distanceFunc;
    private DistanceCombinator distanceCombinator;

    public Worley () {
        this(1.0f, 1.0f, System.currentTimeMillis());
    }

    public Worley (final long seed) {
        this(1.0f, 1.0f, seed);
    }

    public Worley (final float p_freq, final float p_amp) {
        this(p_freq, p_amp, System.currentTimeMillis());
    }

    public Worley (final float p_freq, final float p_amp, final long seed) {
        this.seed = (int) (seed << 16);
        frequency = p_freq;
        amplitude = p_amp;

        distanceFunc = new EuclidianDistanceFunction();
        distanceCombinator = new F1Combinator();
    }

    public Worley setDistanceFunction (DistanceFunction d) {
        distanceFunc = d;
        return this;
    }

    public Worley setDistanceCombinator (DistanceCombinator d) {
        distanceCombinator = d;
        return this;
    }

    /**
     * 1D Worley noise.
     */
    public float get (final float x) {
        return FMath.clamp(amplitude * worley(x * frequency), -1f, 1f);
    }

    /**
     * @return Result of applying Worley function to x, y.
     */
    private float worley (final float x) {
        int sqX;
        NavigableSet<Float> distanceArray = new TreeSet<Float>();

        for (int p = -1; p <= 1; p++) {
            for (int q = -1; q <= 1; q++) {
                sqX = (int) x + p;
                Random r = new Random(Noise.hash(sqX + seed, 0));
                int featurePoints = r.nextInt(MAX_FEATURE_POINTS / 2 - 1) + 1;

                for (int i = 0; i < featurePoints; i++) {
                    float fp = sqX + r.nextFloat();

                    float distance = distanceFunc.apply(x, fp);
                    if (distanceArray.isEmpty() || distance < distanceArray.last()) {
                        distanceArray.add(distance);
                    }
                }
            }
        }

        return (distanceCombinator.apply(distanceArray) - 0.5f) * 2.0f;
    }

    /**
     * 2D Worley noise.
     */
    public float get (final float x, final float y) {
        return FMath.clamp(amplitude * worley(x * frequency, y * frequency), -1f, 1f);
    }

    /**
     * @return Result of applying Worley function to x, y.
     */
    private float worley (final float x, final float y) {
        int sqX, sqY;
        NavigableSet<Float> distanceArray = new TreeSet<Float>();

        for (int p = -1; p <= 1; p++) {
            for (int q = -1; q <= 1; q++) {
                sqX = (int) x + p;
                sqY = (int) y + q;
                Random r = new Random(Noise.hash(sqX + seed, sqY + seed));
                int featurePoints = r.nextInt(MAX_FEATURE_POINTS - 1) + 1;

                for (int i = 0; i < featurePoints; i++) {
                    Vector2 fp = new Vector2(sqX + r.nextFloat(), sqY + r.nextFloat());

                    float distance = distanceFunc.apply(new Vector2(x, y), fp);
                    if (distanceArray.isEmpty() || distance < distanceArray.last()) {
                        distanceArray.add(distance);
                    }
                }

            }
        }

        return (distanceCombinator.apply(distanceArray) - 0.5f) * 2.0f;
    }
}
