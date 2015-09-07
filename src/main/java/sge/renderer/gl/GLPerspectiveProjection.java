package sge.renderer.gl;

import sge.math.Matrix4;

public class GLPerspectiveProjection implements GLProjection {

    public float fov = 45.0f;
    public float near = 0.1f;
    public float far = 100.0f;

    public GLPerspectiveProjection (float fov) {
        this(fov, 0.01f, 100.0f);
    }

    public GLPerspectiveProjection (float fov, float near, float far) {
        this.fov = fov;
        this.near = near;
        this.far = far;
    }

    /* (non-Javadoc)
     * @see sge.renderer.gl.GLProjection#getProjectionMatrix(int, int)
     */
    @Override
    public Matrix4 getProjectionMatrix (final int width, final int height) {

        final float f_ = far;
        final float n_ = near;
        final float aspectRatio = (float) width / (float) height;
        final float size = n_ * (float) (Math.tan(Math.toRadians(fov)) / 2.0f);
        final float left = -size,
                right = size,
                top = size / aspectRatio,
                bottom = -size / aspectRatio;

        return new Matrix4(
                (2.0f * n_) / (right - left), 0.0f, 0.0f, 0.0f,
                0.0f, (2.0f * n_) / (top - bottom), 0.0f, 0.0f,
                (right + left) / (right - left), (top + bottom) / (top - bottom), -(f_ + n_) / (f_ - n_), -1.0f,
                0.0f, 0.0f, -(2.0f * f_ * n_) / (f_ - n_), 0.0f
        );
    }
}
