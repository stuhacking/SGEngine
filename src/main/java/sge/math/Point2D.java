package sge.math;

/**
 * 2 Dimensional Point class for Integer coordinates. Bit like a Vector2.
 */
public final class Point2D {

    public final int x;
    public final int y;

    /** Default Constructor. */
    public Point2D () {
        x = y = 0;
    }

    /** Fill Constructor */
    public Point2D (final int val) {
        x = y = val;
    }

    /** Value Constructor. */
    public Point2D (final int xx, final int yy) {
        x = xx;
        y = yy;
    }

    /** Copy Constructor. */
    public Point2D (final Point2D p) {
        x = p.x;
        y = p.y;
    }

    /** Array Constructor. */
    public Point2D (final int[] values) {
        x = values[0];
        y = values[1];
    }

    /**
     * Clamp Point2D within minimum and maximum bounds, given by other
     * Point2D values.
     * Destructive.
     *
     * @param min Point2D minimum bound
     * @param max Point2D maximum bound
     */
    public Point2D clamp (final Point2D min, final Point2D max) {
        return new Point2D(FMath.clamp(x, min.x, max.x), FMath.clamp(y, min.y, max.y));
    }

    // POINT OPERATIONS

    public Point2D add (final Point2D other) {
        return new Point2D(x + other.x, y + other.y);
    }

    public Point2D sub (final Point2D other) {
        return new Point2D(x - other.x, y - other.y);
    }

    public Point2D mult (final Point2D other) {
        return new Point2D(x * other.x, y * other.y);
    }

    public Point2D div (final int divisor) {
        return new Point2D(x / divisor, y / divisor);
    }

    public Point2D scale (final int scale) {
        return new Point2D(x * scale, y * scale);
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("<Point2D %d %d>", x, y);
    }

    @Override
    public boolean equals (final Object other) {
        if (this == other) return true;

        if (other instanceof Point2D) {
            return compare((Point2D) other);
        }

        return false;
    }

    public boolean compare (final Point2D other) {

        return x == other.x && y == other.y;
    }

    public boolean compare (final Point2D other, final int threshold) {

        if (Math.abs(x - other.x) > threshold)
            return false;

        if (Math.abs(y - other.y) > threshold)
            return false;

        return true;
    }

    /**
     * Pack values into raw int[].
     */
    public int[] toArray () {
        return new int[]{x, y};
    }

    public Vector2 toVector2 () {
        return new Vector2(x, y);
    }
}
