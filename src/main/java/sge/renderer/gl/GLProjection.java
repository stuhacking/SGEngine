package sge.renderer.gl;

import sge.math.Matrix4;

/**
 * Interface defining a method for deriving OpenGL projection matrices
 * based on view dimensions.
 */
public interface GLProjection {

    /**
     * Calculate the projection Matrix transforming world space into screen
     * space bounded by width and height.
     *
     * @param width  Width of viewing area.
     * @param height Height of viewing area.
     * @return
     */
    public Matrix4 getProjectionMatrix (final int width, final int height);
}
