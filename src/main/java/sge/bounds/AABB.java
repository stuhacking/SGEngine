package sge.bounds;

import sge.math.MVector3;
import sge.math.Vector3;
import sge.math.Vectors;

/**
 * Axis-Aligned Bounding Box
 */
public final class AABB {

    private float xMin, yMin, zMin;
    private float xMax, yMax, zMax;

    /**
     * Create a zero sized AABB at the origin.
     */
    public AABB () {
        this(Vector3.ZERO, Vector3.ZERO);
    }

    /**
     * Copy Constructor.
     */
    public AABB (final AABB copy) {
        this(copy.xMin, copy.yMin, copy.zMin,
             copy.xMax, copy.yMax, copy.zMax);
    }

    /**
     * Create a zero sized AABB using a single point of origin.
     *
     * @param point
     */
    public AABB (final Vector3 point) {
        this(point, point);
    }

    /**
     * Create an AABB using minimum/maximum points.
     */
    public AABB (final Vector3 min, final Vector3 max) {
        xMin = min.x;
        yMin = min.y;
        zMin = min.z;

        xMax = max.x;
        yMax = max.y;
        zMax = max.z;
    }

    public AABB (final float xMin, final float yMin, final float zMin,
                 final float xMax, final float yMax, final float zMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.zMin = zMin;
        this.xMax = xMax;
        this.yMax = yMax;
        this.zMax = zMax;
    }

    public Vector3 min () {
        return new Vector3(xMin, yMin, zMin);
    }

    public Vector3 max () {
        return new Vector3(xMax, yMax, zMax);
    }

    public Vector3 getCenter () {
        return new Vector3((xMin + xMax) * 0.5f, (yMin + yMax) * 0.5f, (zMin + zMax) * 0.5f);
    }

    public float getVolume () {
        return (xMax - xMin) * (yMax - yMin) * (zMax - zMin);
    }

    public void clear_ () {
        xMin = yMin = zMin = 0.0f;
        xMax = yMax = zMax = 0.0f;
    }

    /**
     * Translate this bounding box by offset.
     * Destructive.
     */
    public AABB translate_ (Vector3 offset) {
        xMin += offset.x;
        yMin += offset.y;
        zMin += offset.z;

        xMax += offset.x;
        yMax += offset.y;
        zMax += offset.z;

        return this;
    }

    /**
     * Add a new point to the bounding region. Return true if the
     * bounds have been extended.
     */
    public boolean addPoint_ (Vector3 point) {
        boolean expanded = false;
        if (point.x < xMin) {
            xMin = point.x;
            expanded = true;
        }
        if (point.y < yMin) {
            yMin = point.y;
            expanded = true;
        }
        if (point.z < zMin) {
            zMin = point.z;
            expanded = true;
        }
        if (point.x > xMax) {
            xMax = point.x;
            expanded = true;
        }
        if (point.y > yMax) {
            yMax = point.y;
            expanded = true;
        }
        if (point.z > zMax) {
            zMax = point.z;
            expanded = true;
        }
        return expanded;
    }

    /**
     * Test if a point lies within the bounding region.
     *
     * @return true if point is contained.
     */
    public boolean contains (final Vector3 point) {
        return ((point.x >= xMin && point.x <= xMax) &&
                (point.y >= yMin && point.y <= yMax) &&
                (point.z >= zMin && point.z <= zMax));
    }

    /**
     * Test if another AABB is fulled contained in this AABB.
     *
     * @return true if AABB is contained.
     */
    public boolean contains (final AABB other) {
        return contains(other.min()) && contains(other.max());
    }

    /**
     * Test if another AABB intersects this.
     *
     * @return true if intersects.
     */
    public boolean intersects (final AABB other) {
        if (other.xMax < xMin || other.yMax < yMin || other.zMax < zMin ||
                other.xMin > xMax || other.yMin > yMax || other.zMin > zMax) {
            return false;
        }

        return true;
    }

    /**
     * Minimum bounding box for a set of points.
     * Destructive.
     *
     * @param points
     */
    public void fromPoints (final Vector3[] points) {
        MVector3 min = new MVector3();
        MVector3 max = new MVector3();

        Vectors.minMax(min, max, points);

        xMin = min.x;
        yMin = min.y;
        zMin = min.z;
        xMax = max.x;
        yMax = max.y;
        zMax = max.z;
    }

    public Vector3[] toPoints () {
        return new Vector3[] {
                new Vector3(xMin, yMin, zMin),
                new Vector3(xMax, yMin, zMin),
                new Vector3(xMax, yMin, zMax),
                new Vector3(xMin, yMin, zMax),
                new Vector3(xMin, yMax, zMin),
                new Vector3(xMax, yMax, zMin),
                new Vector3(xMax, yMax, zMax),
                new Vector3(xMin, yMax, zMax)
        };
    }
}
