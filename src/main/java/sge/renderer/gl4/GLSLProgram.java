package sge.renderer.gl4;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

import sge.math.Matrix3;
import sge.math.Matrix4;
import sge.math.Vector3;
import sge.math.Vector4;
import sge.util.DirectBuffer;

/**
 * Simple implementation of a GLSL shader loading library.
 *
 * The public part of this interface is a GLSLProgram which is responsible for
 * managing a number of individual shaders (provided as files, or source strings).
 * This class delegates the shader compilation to GLSLShader, and links the resulting
 * objects if all compilation is successful.
 *
 * TODO Support #include directives
 * TODO Support linking already compiled shaders (Is this possible in GLSL?)
 */
public class GLSLProgram {

    private static final Logger logger = Logger.getLogger(GLSLProgram.class);

    private static final boolean VALIDATE_SHADERS = true;

    /** Cache locations of shader uniform variables. */
    private HashMap<String, Integer> uniforms = new HashMap<String, Integer>(16);

    private int id = 0;

    private final List<GLSLShader> shaders = new ArrayList<GLSLShader>();

    /** Default Constructor. */
    public GLSLProgram () { }

    /**
     * Create a new GLSL Program comprising multiple shader source files.
     */
    public GLSLProgram (final String... sourceFiles) {
        for (String src : sourceFiles) {
            shaders.add(new GLSLShaderFile(src));
        }
    }

    public GLSLProgram addShaderFile (final String sourceFile) {
        shaders.add(new GLSLShaderFile(sourceFile));

        return this;
    }

    public GLSLProgram addShaderSource (final int shaderType, final String source) {
        shaders.add(new GLSLShaderSource(shaderType, source));

        return this;
    }

    /**
     * Bind the shader for rendering.
     */
    public void bind () {
        if (!isCompiled())
            compile();

        logger.debug("Binding GLSL Program: " + id);
        GL20.glUseProgram(id);
    }

    /**
     * Attempt to compile the Complete Shader program using the vertex and
     * fragment files.
     *
     * @return compiled flag true if successful, false if unsuccessful.
     */
    public boolean compile () {
        int a_id = 0;

        if (isCompiled()) {
            logger.warn("Already compiled: " + id);
            a_id = id;
        } else {
            a_id = GL20.glCreateProgram();
        }

        if (0 == a_id) {
            logger.error("Unable to assign GLSL Program ID!");
            return false;
        }

        for (GLSLShader shader : shaders) {
            shader.compile();

            if (!shader.isCompiled()) {
                logger.error("Failed to compile shader program (" + shader.getId() + ")");
                delete();
                GL20.glDeleteProgram(a_id);
                return false;
            }
        }

        for (GLSLShader shader : shaders) {
            GL20.glAttachShader(a_id, shader.getId());
        }

        GL20.glLinkProgram(a_id);

        if (!GLSLUtil.checkLinkStatus(a_id)) {
            logger.error("Link error in shader Program (" + a_id + "): "
                                 + "\n" + GLSLUtil.getInfoLogText(a_id));

            delete();
            GL20.glDeleteProgram(a_id);
            return false;
        }

        if (VALIDATE_SHADERS) {
            GL20.glValidateProgram(a_id);

            if (!GLSLUtil.checkValidateStatus(a_id)) {
                logger.error("Compilation error in shader Program (" + a_id + "): "
                                     + "\n" + GLSLUtil.getInfoLogText(a_id));

                delete();
                GL20.glDeleteProgram(a_id);
                return false;
            }
        }

        id = a_id;

        return true;
    }

    public boolean isCompiled () {
        return id > 0;
    }

    public void delete () {
        for (GLSLShader shader : shaders) {
            shader.delete();
        }

        if (isCompiled()) {
            GL20.glDeleteProgram(id);
            id = 0;
        }
    }

    /**
     * Get the location of the uniform in the current shader by name.
     *
     * @param name Identifier of uniform variable.
     * @return Uniform location, or -1 if uniform doesn't exist.
     */
    public int getUniform (final String name) {
        if (uniforms.containsKey(name))
            return uniforms.get(name);

        int uniformLocation = GL20.glGetUniformLocation(id, name);

        if (-1 == uniformLocation) {
            logger.warn("Warning: Uniform location does not exist: " + name);
        } else {
            uniforms.put(name, uniformLocation);
        }

        return uniformLocation;
    }

    /**
     * Send float data to a shader uniform.
     *
     * @param name Uniform identifier.
     * @param value
     */
    public void setUniform (final String name, final float value) {
        int uniformLocation = getUniform(name);

        if (-1 != uniformLocation) {
            GL20.glUniform1f(uniformLocation, value);
        }
    }

    /**
     * Send 3d vector data to a shader uniform.
     *
     * @param name Uniform identifier.
     * @param value
     */
    public void setUniform (final String name, final Vector3 value) {
        int uniformLocation = getUniform(name);

        if (-1 != uniformLocation) {
            GL20.glUniform3f(uniformLocation, value.x, value.y, value.z);
        }
    }

    /**
     * Send 4d vector data to a shader uniform.
     *
     * @param name Uniform identifier.
     * @param value
     */
    public void setUniform (final String name, final Vector4 value) {
        int uniformLocation = getUniform(name);

        if (-1 != uniformLocation) {
            GL20.glUniform4f(uniformLocation, value.x, value.y, value.z, value.w);
        }
    }

    /**
     * Send 3x3 Matrix data to a shader uniform.
     *
     * @param name Uniform identifier.
     * @param value
     */
    public void setUniform (final String name, final Matrix3 value) {
        int uniformLocation = getUniform(name);
        if (-1 != uniformLocation) {
            FloatBuffer f = DirectBuffer.createFloatBuffer(9);
            f.put(value.mat);
            f.rewind();

            GL20.glUniformMatrix4(uniformLocation, false, f);
        }
    }

    /**
     * Send 4x4 matrix data to a shader uniform.
     *
     * @param name Uniform identifier.
     * @param value
     */
    public void setUniform (final String name, final Matrix4 value) {
        int uniformLocation = getUniform(name);
        if (-1 != uniformLocation) {
            FloatBuffer f = DirectBuffer.createFloatBuffer(16);
            f.put(value.mat);
            f.rewind();

            GL20.glUniformMatrix4(uniformLocation, false, f);
        }
    }

    /**
     * Send float[] data to a shader uniform.
     *
     * @param name Uniform identifier.
     * @param data
     */
    public void setUniform (final String name, final float[] data) {
        int uniformLocation = getUniform(name);
        if (-1 != uniformLocation) {
            FloatBuffer f = DirectBuffer.createFloatBuffer(data.length);
            f.put(data);
            f.rewind();

            GL20.glUniformMatrix4(uniformLocation, false, f);
        }
    }

    /**
     * Send FloatBuffer data to a shader uniform.
     *
     * @param name Uniform identifier.
     * @param value
     */
    public void setUniform (final String name, final FloatBuffer value) {
        int uniformLocation = getUniform(name);

        if (-1 != uniformLocation) {
            GL20.glUniformMatrix4(uniformLocation, false, value);
        }
    }

    // Java Housekeeping

    @Override
    public String toString () {
        return String.format("<GLSL Program(%d) %s", id, (isCompiled() ? " C>" : ">"));
    }
}
