package sge.bounds;

import sge.math.Vector2;

public interface Bounds2D {

    /**
     * Test if an intersection exists between two bounding
     * shapes.
     *
     * @param other
     * @return whether other intersects with this shape.
     */
    boolean intersects (Bounds2D other);

    /**
     * Test if this bounding shape entirely surrounds another
     * bounding shape.
     *
     * @param other
     * @return whether other is contained by this shape.
     */
    boolean contains (Bounds2D other);

    /**
     * Test if a Point is contained by this bounding shape.
     *
     * @param point
     * @return whether point is contained by this shape.
     */
    boolean contains (Vector2 point);
}
