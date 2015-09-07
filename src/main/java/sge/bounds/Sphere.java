package sge.bounds;

import sge.math.Vector3;

public class Sphere {

    private Vector3 origin;
    private float radius;

    /**
     * Default Constructor.
     */
    public Sphere () {
        origin = Vector3.ZERO;
        radius = 0.0f;
    }

    /**
     * Copy Constructor.
     */
    public Sphere (final Sphere copy) {
        this(copy.origin, copy.radius);
    }

    /**
     * Constructor with origin.
     *
     * @param origin
     */
    public Sphere (Vector3 origin) {
        this(origin, 0.0f);
    }

    /**
     * Constructor with radius.
     *
     * @param radius
     */
    public Sphere (float radius) {
        this(Vector3.ZERO, radius);
    }

    /**
     * Constructor with origin and radius.
     *
     * @param origin
     * @param radius
     */
    public Sphere (Vector3 origin, float radius) {
        this.radius = radius;
        this.origin = origin;
    }

    public void clear () {
        this.radius = 0.0f;
        this.origin = Vector3.ZERO;
    }

    public Vector3 getOrigin () {
        return origin;
    }

    public void setOrigin (Vector3 origin) {
        this.origin = origin;
    }

    public float getRadius () {
        return radius;
    }

    public void setRadius (float radius) {
        this.radius = radius;
    }

    public void add (Vector3 point) {
        if (contains(point)) return;

        Vector3 line = point.sub(origin);

        radius = line.getLength();
    }

    /**
     * Return true if 'point' is contained in this sphere.
     */
    public boolean contains (Vector3 point) {
        Vector3 relativePoint = point.sub(origin);
        return (radius * radius) >= relativePoint.getLengthSqr();
    }

    /**
     * Return true if 'other' sphere is entirely enclosed by this
     * sphere.
     */
    public boolean contains (Sphere other) {
        Vector3 relativePoint = other.origin.sub(origin);
        float otherLength = relativePoint.getLength() + other.radius;
        return radius >= otherLength;
    }

    /**
     * Return true if 'other' sphere is partially enclosed by this
     * sphere.
     */
    public boolean intersects (Sphere other) {
        Vector3 relativePoint = other.origin.sub(origin);
        float otherLength = relativePoint.getLength() - other.radius;
        return radius > otherLength;
    }

    /**
     * Return an Axis-aligned Bounding Box that perfectly encapsulates
     * this Sphere.
     *
     * @return
     */
    public AABB toAABB () {
        return new AABB(origin.x - radius, origin.y - radius, origin.z - radius,
                        origin.x + radius, origin.y + radius, origin.z + radius);
    }
}
