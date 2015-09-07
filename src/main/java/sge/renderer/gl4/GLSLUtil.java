package sge.renderer.gl4;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

final class GLSLUtil {

    /**
     * Fetch the most recent Info Log text for the given glsl shader id (either a shader
     * component, or a program.) You should call this immediately after checking the status
     * of an operation.
     *
     * @param glslID The GLSL shader id
     * @return String containing the shader info log contents.
     */
    static final String getInfoLogText (final int glslID) {
        // logLength includes the NULL character.
        int logLength = GL20.glGetShaderi(glslID, GL20.GL_INFO_LOG_LENGTH);

        return GL20.glGetShaderInfoLog(glslID, logLength);
    }

    /**
     * Check the status of the previous shader compile call. Print out
     * compiler build log if an error was encountered.
     *
     * @param glslID The Shader ID
     * @return true if compilation was successful, otherwise false.
     */
    static final boolean checkCompileStatus (final int glslID) {
        return GL11.GL_TRUE == GL20.glGetShaderi(glslID, GL20.GL_COMPILE_STATUS);
    }

    /**
     * Check the status of the previous shader program link call. Print out
     * compiler info log if an error was encountered.
     *
     * @param glslID The Shader Program ID
     * @return true if linking was successful, otherwise false.
     */
    static final boolean checkLinkStatus (final int glslID) {
        return GL11.GL_TRUE == GL20.glGetShaderi(glslID, GL20.GL_LINK_STATUS);
    }

    /**
     * Check the status of the previous shader program validate call. Print out
     * compiler info log if an error was encountered.
     *
     * @param glslID The Shader Program ID
     * @return true if validation was successful, otherwise false.
     */
    static final boolean checkValidateStatus (final int glslID) {
        return GL11.GL_TRUE == GL20.glGetShaderi(glslID, GL20.GL_VALIDATE_STATUS);
    }

    static String shaderTypeName (final int shaderType) {
        switch (shaderType) {
            case GL20.GL_VERTEX_SHADER:
                return "vertex";
            case GL20.GL_FRAGMENT_SHADER:
                return "fragment";
            case GL32.GL_GEOMETRY_SHADER:
                return "geometry";
            default:
                return "";

        }
    }
}
