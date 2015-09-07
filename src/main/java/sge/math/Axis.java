package sge.math;


/**
 * Rotation data structure storing a forward vector, and up vector,
 * and deriving left/right vectors from this
 *
 * @author smh
 */
public final class Axis {

    public static final Vector3 GLOBAL_UP = new Vector3(0f, 1f, 0f);
    public static final Vector3 GLOBAL_FORWARD = new Vector3(0f, 0f, 1f);

    private Vector3 forward;
    private Vector3 up;

    public Axis () {
        this(GLOBAL_FORWARD, GLOBAL_UP);
    }

    /**
     * Value Constructor
     *
     * @param forward Forward pointing vector
     * @param up      Up pointing vector
     */
    public Axis (Vector3 forward, Vector3 up) {
        this.forward = forward.normalize();
        this.up = up.normalize();
    }

    public void reset_ () {
        this.forward = GLOBAL_FORWARD;
        this.up = GLOBAL_UP;
    }

    /**
     * Get forward pointing vector.
     */
    public Vector3 getForward () {
        return forward;
    }

    public void setForward (final Vector3 forward) {
        this.forward = forward;
    }

    /**
     * Get up pointing vector.
     */
    public Vector3 getUp () {
        return up;
    }

    public void setUp (final Vector3 up) {
        this.up = up;
    }

    /**
     * Get left pointing vector.
     */
    public Vector3 getLeft () {
        return up.cross(forward).normalize();
    }

    /**
     * Get right pointing vector.
     */
    public Vector3 getRight () {
        return forward.cross(up).normalize();
    }

    /**
     * Rotate Axis `angle' degrees about the X axis.
     *
     * @param angle angle in degrees
     */
    public void rotateX (float angle) {
        Vector3 hAxis = GLOBAL_UP.cross(forward).normalize();

        forward = forward.rotate(angle, hAxis).normalize();
        up = forward.cross(hAxis).normalize();
    }

    /**
     * Rotate Axis `angle' degrees about the Y axis.
     *
     * @param angle angle in degrees
     */
    public void rotateY (float angle) {
        Vector3 hAxis = GLOBAL_UP.cross(forward).normalize();

        forward = forward.rotate(angle, GLOBAL_UP).normalize();
        up = forward.cross(hAxis).normalize();
    }

    /**
     * Rotate Axis `angle' degrees about the Z axis.
     *
     * @param angle angle in degrees
     */
    public void rotateZ (float angle) {
        up = up.rotate(angle, forward).normalize();
    }

    public Matrix4 toMatrix4 () {
        Vector3 f = forward.normalize();
        Vector3 r = up.normalize().cross(f);
        Vector3 u = f.cross(r);

        return new Matrix4(
                r.x, u.x, f.x, 0.0f,
                r.y, u.y, f.y, 0.0f,
                r.z, u.z, f.z, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        );
    }
}
