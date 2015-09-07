package sge.renderer.gl4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

class GLSLShaderFile extends GLSLShader {

    private static final Logger logger = Logger.getLogger(GLSLShaderFile.class);

    private String filename;

    /**
     * Construct a GLSL shader giving the path to the source file. This constructor
     * attempts to identify the shader type using the file extension.
     *
     * @param filename Path to the shader source.
     */
    public GLSLShaderFile (String filename) {
        this(filename, detectShaderType(filename));
    }

    /**
     * Construct a GLSL shader giving the path to the source file and specifying
     * the shader type manually.
     *
     * @param filename Path to the shader source.
     * @param shaderType Shader type value of GL_{VERTEX,FRAGMENT,GEOMETRY}_SHADER.
     */
    public GLSLShaderFile (String filename, int shaderType) {
        this.filename = filename;
        this.shaderType = shaderType;
    }

    private static int detectShaderType (String filename) {
        if (filename.endsWith(".vs") || filename.endsWith(".vert"))
            return GL20.GL_VERTEX_SHADER;

        if (filename.endsWith(".fs") || filename.endsWith(".frag"))
            return GL20.GL_FRAGMENT_SHADER;

        if (filename.endsWith(".gs") || filename.endsWith(".geom"))
            return GL32.GL_GEOMETRY_SHADER;

        logger.warn("Unable to determine shader type from extension. Defaulting to Vertex Shader.");
        return GL20.GL_VERTEX_SHADER;
    }

    @Override
    public int compile () {
        String shaderCode = loadShaderSource(filename);
        if (shaderCode.isEmpty()) {
            logger.error("Shader source is empty: " + filename);
            return 0;
        }

        int a_id = GL20.glCreateShader(shaderType);

        if (0 >= a_id) {
            logger.error("Unable to assign Shader ID: " + filename);
            return 0;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Compiling Shader: " + filename + "(" + a_id + ")...");
        }

        GL20.glShaderSource(a_id, shaderCode);
        GL20.glCompileShader(a_id);

        if (!GLSLUtil.checkCompileStatus(a_id)) {
            logger.error("Compilation error in " + GLSLUtil.shaderTypeName(shaderType) +
                    " shader (" + a_id + "): " + filename
                    + "\n" + GLSLUtil.getInfoLogText(a_id));

            GL20.glDeleteShader(a_id);
            id = 0;
        } else {
            id = a_id;
        }

        return id;
    }

    private static String loadShaderSource (final String filename) {
        StringBuilder shaderCode = new StringBuilder(1024);
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename));) {
            while ((line = reader.readLine()) != null) {
                shaderCode.append(line).append('\n');
            }

            return shaderCode.toString();

        } catch (FileNotFoundException e) {
            logger.error("Unable to locate shader file: " + filename, e);
            return "";
        } catch (IOException e) {
            logger.error("IOException reading Shader File: " + filename, e);
            return "";
        }
    }

    @Override
    public String toString () {
        return String.format("<GLSL %s(%d) %s%s", GLSLUtil.shaderTypeName(shaderType), id, filename, (isCompiled() ? " C>" : ">"));
    }
}
