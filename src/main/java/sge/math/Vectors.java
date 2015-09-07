package sge.math;

/**
 * Created by shacking on 01/09/15.
 */
public abstract class Vectors {

    /**
     * Find the minimum and maximum points from a list of vectors.
     *
     * @param min
     * @param max
     * @param points
     */
    public static void minMax (final MVector2 min, final MVector2 max, final Vector2[] points) {
        min.x = min.y = Float.POSITIVE_INFINITY;
        max.x = max.y = Float.NEGATIVE_INFINITY;

        for (int k = 0, kMax = points.length; k < kMax; k++) {
            min.x = Math.min(min.x, points[k].x);
            min.y = Math.min(min.y, points[k].y);
            max.x = Math.max(max.x, points[k].x);
            max.y = Math.max(max.y, points[k].y);
        }
    }

    public static void minMax (final MVector3 min, final MVector3 max, final Vector3[] points) {
        min.x = min.y = min.z = Float.POSITIVE_INFINITY;
        max.x = max.y = max.z = Float.NEGATIVE_INFINITY;

        for (int k = 0, kMax = points.length; k < kMax; k++) {
            min.x = Math.min(min.x, points[k].x);
            min.y = Math.min(min.y, points[k].y);
            min.z = Math.min(min.z, points[k].z);
            max.x = Math.max(max.x, points[k].x);
            max.y = Math.max(max.y, points[k].y);
            max.z = Math.max(max.z, points[k].z);
        }
    }
}
