package sge.renderer.gl;

import sge.math.Matrix4;

public class GLOrthographicProjection implements GLProjection {

    public float nearPlane = 1.0f;
    public float farPlane = 100.0f;

    public GLOrthographicProjection () {
        this(1.0f, 100.0f);
    }

    public GLOrthographicProjection (float near, float far) {
        this.nearPlane = near;
        this.farPlane = far;
    }

    /* (non-javadoc)
     * @see sge.renderer.gl.GLProjection#getProjectionMatrix(int, int)
     */
    @Override
    public Matrix4 getProjectionMatrix (final int width, final int height) {

        float f_ = farPlane;
        float n_ = nearPlane;
        float aspectRatio = (float) width / (float) height;
        float size = Math.min(width / 2.0f, height / 2.0f);

        float left = -size, right = size, top = size / aspectRatio, bottom = -size / aspectRatio;

        return new Matrix4(
                2 / (right - left), 0.0f, 0.0f, 0.0f,
                0.0f, 2 / (top - bottom), 0.0f, 0.0f,
                0.0f, 0.0f, -2 / (f_ - n_), 0.0f,
                -(right + left) / (right - left), -(top + bottom) / (top - bottom), -(f_ + n_) / (f_ - n_), 1.0f
        );
    }

}
