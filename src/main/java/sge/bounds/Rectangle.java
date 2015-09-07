package sge.bounds;

import sge.math.MVector2;
import sge.math.Vector2;
import sge.math.Vectors;

/**
 * 2D Axis-Aligned Rectangle for bounds testing.
 */
public final class Rectangle implements Bounds2D {

    private float xMin, yMin, xMax, yMax;

    /**
     * Default Constructor
     * Single point at origin.
     */
    public Rectangle () {
        this(Vector2.ZERO, Vector2.ZERO);
    }

    /** Copy Constructor */
    public Rectangle (final Rectangle copy) {
        this(copy.xMin, copy.yMin, copy.xMax, copy.yMax);
    }

    /**
     * Create a zero sized Rectangle using a single point of origin.
     *
     * @param point
     */
    public Rectangle (final Vector2 point) {
        this(point, point);
    }

    /**
     * Create an Rectangle using minimum/maximum points.
     */
    public Rectangle (final Vector2 min, final Vector2 max) {
        xMin = min.x;
        yMin = min.y;
        xMax = max.x;
        yMax = max.y;
    }

    /**
     * Value Constructor.
     *
     * @param xMin
     * @param yMin
     * @param xMax
     * @param yMax
     */
    public Rectangle (final float xMin, final float yMin, final float xMax, final float yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Get the min corner of this Rectangle.
     *
     * @return
     */
    public Vector2 min () {
        return new Vector2(xMin, yMin);
    }

    /**
     * Get the max corner of this Rectangle.
     *
     * @return
     */
    public Vector2 max () {
        return new Vector2(xMax, yMax);
    }

    /**
     * Get the centre point of this Rectangle.
     *
     * @return
     */
    public Vector2 getCenter () {
        return new Vector2((xMin + xMax) * 0.5f, (yMin + yMax) * 0.5f);
    }

    /**
     * Get the area of this Rectangle.
     *
     * @return
     */
    public float getArea () {
        return (xMax - xMin) * (yMax - yMin);
    }

    /**
     * Clear this rectangle to a single point at (0,0).
     * Destructive.
     */
    public void clear_ () {
        xMin = yMin = xMax = yMax = 0.0f;
    }

    /**
     * Expand this Rectangle to encompass everything.
     * Destructive.
     */
    public void maximize_ () {
        xMin = yMin = Float.NEGATIVE_INFINITY;
        xMax = yMax = Float.POSITIVE_INFINITY;
    }

    /**
     * Translate this Rectangle by offset.
     */
    public Rectangle translate (final Vector2 offset) {
        return new Rectangle(xMin + offset.x, yMin + offset.y, xMax + offset.x, yMax + offset.y);
    }

    /**
     * Translate this Rectangle by offset.
     * Destructive.
     */
    public Rectangle translate_ (final Vector2 offset) {
        xMin += offset.x;
        yMin += offset.y;
        xMax += offset.x;
        yMax += offset.y;

        return this;
    }

    /**
     * Expand the boundaries of this Rectangle by amount on each side.
     *
     * @param amount
     * @return
     */
    public Rectangle expand (final float amount) {
        return new Rectangle(xMin - amount, yMin - amount, xMax + amount, yMax + amount);
    }

    /**
     * Expand the boundaries of this Rectangle by amount on each side.
     *
     * @param amount
     * @return
     */
    public Rectangle expand_ (final float amount) {
        xMin -= amount;
        yMin -= amount;
        xMax += amount;
        yMax += amount;

        return this;
    }

    public Rectangle intersect (final Rectangle other) {
        Rectangle r = new Rectangle(this);
        r.intersect_(other);

        return r;
    }

    public void intersect_ (final Rectangle other) {
        xMin = Math.max(xMin, other.xMin);
        yMin = Math.max(yMin, other.yMin);
        xMax = Math.min(xMax, other.xMax);
        yMax = Math.min(yMax, other.yMax);
    }

    /**
     * Add a new point to the bounding region. Return true if the
     * bounds have been extended.
     */
    public boolean addPoint_ (final Vector2 point) {
        boolean expanded = false;

        if (point.x < xMin) {
            xMin = point.x;
            expanded = true;
        }
        if (point.y < yMin) {
            yMin = point.y;
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

        return expanded;
    }

    public boolean intersects (final Rectangle other) {
        if (other.xMax < xMin || other.yMax < yMin ||
                other.xMin > xMax || other.yMin > yMax) {
            return false;
        }

        return true;
    }

    public boolean intersects (final Circle circle) {
        Vector2 nearest = circle.getOrigin().clamp(min(), max());
        nearest = nearest.sub(circle.getOrigin());

        return nearest.getLengthSqr() <= (circle.radius * circle.radius);
    }

    /**
     * Test if a point lies within the bounding region. Includes touching.
     *
     * @return true if point is contained in this Rectangle.
     */
    @Override
    public boolean contains (final Vector2 point) {
        return ((point.x >= xMin && point.x <= xMax) &&
                (point.y >= yMin && point.y <= yMax));
    }

    public boolean contains (final Rectangle other) {
        return contains(other.min()) && contains(other.max());
    }

    public boolean contains (final Circle other) {
        return contains(other.toRectangle());
    }

    @Override
    public boolean contains (Bounds2D other) {
        if (other instanceof Rectangle) {
            return contains((Rectangle) other);
        }

        if (other instanceof Circle) {
            return contains((Circle) other);
        }

        return false;
    }

    /**
     * Test if another Rectangle intersects this.
     *
     * @return true if intersects other bounding object.
     */
    @Override
    public boolean intersects (Bounds2D other) {
        if (other instanceof Rectangle) {
            return intersects((Rectangle)other);
        }

        if (other instanceof Circle) {
            return intersects((Circle)other);
        }

        return false;
    }

    /**
     * Minimum bounding box for a set of points.
     * Destructive.
     *
     * @param points
     */
    public void fromPoints (final Vector2[] points) {
        MVector2 min = new MVector2();
        MVector2 max = new MVector2();

        Vectors.minMax(min, max, points);

        xMin = min.x;
        yMin = min.y;
        xMax = max.x;
        yMax = max.y;
    }

    public Vector2[] toPoints () {
        return new Vector2[] {
                new Vector2(xMin, yMin),
                new Vector2(xMax, yMin),
                new Vector2(xMax, yMax),
                new Vector2(xMin, yMax)
        };
    }

    public boolean compare (final Rectangle other) {
        return xMin == other.xMin && yMin == other.yMin &&
                xMax == other.xMax && yMax == other.yMax;
    }

    public boolean compare (final Rectangle other, final float epsilon) {
        if (Math.abs(xMin - other.xMin) > epsilon)
            return false;

        if (Math.abs(yMin - other.yMin) > epsilon)
            return false;

        if (Math.abs(xMax - other.xMax) > epsilon)
            return false;

        if (Math.abs(yMax - other.yMax) > epsilon)
            return false;

        return true;
    }

    // Java Housekeeping

    @Override
    public boolean equals (final Object other) {
        if (this == other) return true;

        if (other instanceof Rectangle)
            return compare((Rectangle)other);

        return false;
    }

    @Override
    public int hashCode () {
        int result = (xMin != +0.0f ? Float.floatToIntBits(xMin) : 0);
        result = 31 * result + (yMin != +0.0f ? Float.floatToIntBits(yMin) : 0);
        result = 31 * result + (xMax != +0.0f ? Float.floatToIntBits(xMax) : 0);
        result = 31 * result + (yMax != +0.0f ? Float.floatToIntBits(yMax) : 0);
        return result;
    }

    @Override
    public String toString () {
        return String.format("<Rectangle (%s, %s) -> (%s, %s)>", xMin, yMin, xMax, yMax);
    }
}
