package sge.renderer;

/**
 * Enforce Renderable operations.
 */
public interface Renderable {

    public void render ();

    /**
     * Provide an estimate how much much GPU memory will
     * be used by the render object.
     */
    public long byteSize();
}
