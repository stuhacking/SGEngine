package sge.renderer.gl4;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class GLSLShaderSource extends GLSLShader {

    private static final Logger logger = Logger.getLogger(GLSLShaderSource.class);

    private String source;

    /**
     * Construct a GLSL shader giving the path to the source file and specifying
     * the shader type manually.
     *
     * @param shaderType GLenum shader type value of GL_{VERTEX,FRAGMENT,GEOMETRY}_SHADER.
     * @param src Shader source .
     */
    public GLSLShaderSource (int shaderType, String src) {
        this.shaderType = shaderType;
        this.source = src;
    }

    @Override
    public int compile () {
        if (source.isEmpty()) {
            logger.error("Shader source is empty. (anonymous shader)");
            return 0;
        }

        int a_id = GL20.glCreateShader(shaderType);

        if (a_id <= 0) {
            logger.error("Unable to assign Shader ID. (anonymous shader)");
            return 0;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Compiling Shader: (anonymous shader " + a_id + ")...");
        }

        GL20.glShaderSource(a_id, source);
        GL20.glCompileShader(a_id);

        if (!GLSLUtil.checkCompileStatus(a_id)) {
            logger.error("Compilation error in " + GLSLUtil.shaderTypeName(shaderType) +
                    " shader (anonymous shader " + a_id + "):\n" +
                    GLSLUtil.getInfoLogText(a_id));

            GL20.glDeleteShader(a_id);
            id = 0;
        } else {
            id = a_id;
        }

        return id;
    }



    @Override
    public String toString () {
        return String.format("<GLSL %s(%d) (anonymous) %s", GLSLUtil.shaderTypeName(shaderType), id, (isCompiled() ? " C>" : ">"));
    }
}
