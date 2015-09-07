package sge.renderer.gl4;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import sge.color.RGBAColor;
import sge.geometry.Mesh;
import sge.geometry.Vertex;
import sge.geometry.VertexArray;
import sge.math.Vector2;
import sge.math.Vector3;
import sge.renderer.Renderable;

/**
 * MeshRenderer handles the interface to OpenGL for
 * drawing {@link Mesh} objects.
 * <p/>
 * TODO: Reify Vertex Attributes?
 */
public class MeshRenderer implements Renderable {

    /** Vertex Array Object ID */
    private int glVaoID = 0;

    /** Vertex Buffer Object ID */
    private int glVboID = 0;

    /** Index Buffer Object ID */
    private int glIboID = 0;

    private Mesh mesh;

    public MeshRenderer (Mesh mesh) {
        this.mesh = mesh;
    }

    public void setMesh (Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh () {
        return mesh;
    }

    public boolean isCompiled () {
        return glVboID > 0;
    }

    /**
     * Create the Vertex and Index buffers and send the data to the GPU.
     */
    public void compile () {
        FloatBuffer vertData = mesh.vertexArray();
        IntBuffer indexData = mesh.indexArray();

        if (glVaoID <= 0) {
            glVaoID = GL30.glGenVertexArrays();
        }

        if (glVboID <= 0) {
            glVboID = GL15.glGenBuffers();
        }

        if (glIboID <= 0) {
            glIboID = GL15.glGenBuffers();
        }

        GL30.glBindVertexArray(glVaoID);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glVboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertData, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, Vector3.SIZE, GL11.GL_FLOAT, false, Vertex.BYTE_SIZE, 0);
        GL20.glVertexAttribPointer(1, Vector3.SIZE, GL11.GL_FLOAT, false, Vertex.BYTE_SIZE, 12);
        GL20.glVertexAttribPointer(2, Vector2.SIZE, GL11.GL_FLOAT, false, Vertex.BYTE_SIZE, 24);
        GL20.glVertexAttribPointer(3, RGBAColor.SIZE, GL11.GL_FLOAT, false, Vertex.BYTE_SIZE, 32);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, glIboID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexData, GL15.GL_STATIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    /**
     * Setup the OpenGL features supported by a mesh and render.
     * If the mesh is not loaded and bound to a buffer then call
     * compile step.
     */
    @Override
    public void render () {
        if (!isCompiled()) {
            compile();
        }

        GL30.glBindVertexArray(glVaoID);
        GL20.glEnableVertexAttribArray(0); // Positions
        GL20.glEnableVertexAttribArray(1); // Normals
        GL20.glEnableVertexAttribArray(2); // Tex Coords
        GL20.glEnableVertexAttribArray(3); // Vertex Colors

        // Debug: Line Rendering
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);
    }

    /**
     * Estimate the size (in bytes) used by a mesh.
     *
     * @return
     */
    @Override
    public long byteCount () {
        return (glVboID > 0) ? mesh.getVertexCount() * Vertex.BYTE_SIZE : 0;
    }

    public long vertCount () {
        return mesh.getVertexCount();
    }
}
