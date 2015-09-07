package sge.bounds;

import sge.math.Vector2;

public final class Circle implements Bounds2D {

    protected float radius = 0.0f;
    protected Vector2 origin = Vector2.ZERO;

    /**
     * Default Constructor.
     */
    public Circle () {
    }

    public Circle (Vector2 origin) {
        this.origin = origin;
    }

    public Circle (float radius) {
        this.radius = radius;
    }

    public Circle (Vector2 origin, float radius) {
        this.radius = radius;
        this.origin = origin;
    }

    public void clear () {
        this.radius = 0.0f;
        this.origin = Vector2.ZERO;
    }

    public Vector2 getOrigin () {
        return origin;
    }

    public void setOrigin (Vector2 origin) {
        this.origin = origin;
    }

    public float getRadius () {
        return radius;
    }

    public void setRadius (float radius) {
        this.radius = radius;
    }

    public void add (Vector2 point) {
        if (contains(point)) return;

        Vector2 line = point.sub(origin);

        radius = line.getLength();
    }

    public boolean intersects (Circle other) {
        Vector2 relativePoint = other.origin.sub(origin);
        float adjustedLength = relativePoint.getLength() - other.radius;
        return radius >= adjustedLength;
    }

    public boolean contains (Circle other) {
        Vector2 relativePoint = other.origin.sub(origin);
        float otherLength = relativePoint.getLength() + other.radius;
        return radius >= otherLength;
    }

    public boolean contains (Rectangle rect) {
        for (Vector2 v : rect.toPoints()) {
            if (!contains(v))
                return false;
        }
        return true;
    }

    /**
     * Return true if 'point' is contained in this sphere.
     */
    @Override
    public boolean contains (Vector2 point) {
        Vector2 relativePoint = point.sub(origin);
        return (radius * radius) >= relativePoint.getLengthSqr();
    }

    /**
     * Return true if 'other' sphere is entirely enclosed by this
     * sphere.
     */
    @Override
    public boolean contains (Bounds2D other) {
        if (other instanceof Circle) {
            return contains((Circle) other);
        }

        if (other instanceof Rectangle) {
            return contains((Rectangle)other);
        }

        System.err.println("Unknown type of bounding object: " + other.getClass());
        return false;
    }

    /**
     * Return true if 'other' sphere is partially enclosed by this
     * sphere.
     */
    @Override
    public boolean intersects (Bounds2D other) {
        if (other instanceof Circle) {
            return intersects((Circle) other);
        }

        if (other instanceof Rectangle) {
            return ((Rectangle) other).intersects(this);
        }

        System.err.println("Unknown type of bounding object: " + other.getClass());
        return false;
    }

    /**
     * Return an Axis-aligned Bounding Box that perfectly encapsulates
     * this Sphere.
     *
     * @return
     */
    public Rectangle toRectangle () {
        return new Rectangle(origin.x - radius, origin.y - radius,
                             origin.x + radius, origin.y + radius);
    }

    public boolean compare (final Circle other) {
        return radius == other.radius && origin.compare(other.origin);
    }

    public boolean compare (final Circle other, final float epsilon) {
        if (Math.abs(radius - other.radius) > epsilon)
            return false;

        if (!origin.compare(other.origin, epsilon))
            return false;

        return true;
    }

    // Java Housekeeping

    @Override
    public String toString () {
        return String.format("<Circle %s r=%s>", origin.toString(), radius);
    }

    public boolean equals (final Object other) {
        if (this == other) return true;

        if (other instanceof Circle) {
            return compare((Circle)other);
        }

        return false;
    }

    @Override
    public int hashCode () {
        int result = origin.hashCode();
        result = 31 * result + (radius != +0.0f ? Float.floatToIntBits(radius) : 0);
        return result;
    }
}
