package sge.renderer.gl4;

import org.lwjgl.opengl.GL20;

/**
 * Created by shacking on 28/08/15.
 */
abstract class GLSLShader {

    protected int id;
    protected int shaderType;

    public int getId () {
        return id;
    }

    public boolean isCompiled () {
        return id > 0;
    }

    /**
     * Delete this shader and free up any GPU memory allocated to it.
     */
    public void delete () {
        if (isCompiled()) {
            GL20.glDeleteShader(id);
            id = 0;
        }
    }

    /**
     * Reserve an id for this shader and compile the source. Store the
     * id if the compilation is successful, otherwise free the id and
     * delete any other resources.
     *
     * @return Shader ID if the compilation was successful, otherwise -1.
     */
    abstract int compile ();
}
