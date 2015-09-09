package sge.math;

/**
 * Two Dimensional Line Segment (finite).
 */
public class Line2D {

    public final Vector2 start;
    public final Vector2 end;

    public Line2D (Vector2 start, Vector2 end) {
        this.start = start;
        this.end = end;
    }

    public float getLengthSqr () {
        return end.sub(start).getLengthSqr();
    }

    public float getLength () {
        return end.sub(start).getLength();
    }

    public Vector2 getCenter () {
        return start.add(end.scale(0.5f));
    }

    /**
     * Test if this Line2D intersects another Line2D. If there is an
     * intersection, return the point at which the intersects occurs,
     * otherwise return null.
     *
     * @param other Line2D to test for intersection.
     * @return Vector2 Intersection point, or null if no intersection.
     */
    public Vector2 intersects (Line2D other) {
        Vector2 line1 = end.sub(start);
        Vector2 line2 = other.end.sub(other.start);

        // ls1 + line1 * a == ls2 + line2 * b
        float cross = line1.cross(line2);
        if (cross == 0.0f) {
            return null;
        }

        Vector2 dStart = other.start.sub(start);
        float a = dStart.cross(line2) / cross;
        float b = dStart.cross(line1) / cross;

        if (0f < a && a < 1f && 0f < b && b < 1f)
            return start.add(line1.scale(a));

        return null;
    }

    @Override
    public String toString () {
        return String.format("<Line2D %s -> %s>", start, end);
    }
}
