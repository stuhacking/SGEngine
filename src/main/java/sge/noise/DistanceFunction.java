package sge.noise;

import sge.math.Vector2;
import sge.math.Vector3;

/**
 * Interface for calculating distance between two points.
 */
public interface DistanceFunction {

    /**
     * One Dimension distance calculation.
     *
     * @param v1
     * @param v2
     * @return Distance between two floats.
     */
    float apply (float v1, float v2);

    /**
     * Two Dimensional distance calculation.
     *
     * @param v1
     * @param v2
     * @return Distance between two 2D Vectors.
     */
    float apply (Vector2 v1, Vector2 v2);

    /**
     * Three Dimensional distance calculation.
     *
     * @param v1
     * @param v2
     * @return Distance between two 3D Vectors.
     */
    float apply (Vector3 v1, Vector3 v2);
}
