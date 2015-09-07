package sge.geometry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import sge.color.RGBAColor;
import sge.math.FMath;
import sge.math.Vector2;
import sge.math.Vector3;

/**
 * Typical drawable vertex object comprising:
 * position  - vec3      (float 3)
 * normal    - vec3      (float 3)
 * texCoords - vec2      (float 2)
 * color     - RGBAColor (float 4)
 * <p/>
 * Data size - 12 floats, Expect 48 bytes (but calculate from Java float size.)
 */
public class Vertex {

    public static final int SIZE = Vector3.SIZE + Vector3.SIZE + Vector2.SIZE + RGBAColor.SIZE;
    public static final int BYTE_SIZE = SIZE * FMath.FLOAT_SIZE;

    /** Vertex Position. */
    private Vector3 position = Vector3.ZERO;

    /** Vertex Normal. */
    private Vector3 normal = new Vector3(0.0f, 1.0f, 0.0f);

    /** Vertex Texture Coordinates (S, T). */
    private Vector2 texCoords = Vector2.ZERO;

    /** Vertex Color. */
    private RGBAColor color = RGBAColor.WHITE;

    /**
     * Default Constructor.
     */
    public Vertex () { }

    /**
     * Copy Constructor.
     */
    public Vertex (final Vertex copy) {
        position = copy.position;
        normal = copy.normal;
        texCoords = copy.texCoords;
        color = copy.color;
    }

    /**
     * Position Value Constructor.
     */
    public Vertex (final float x, final float y, final float z) {
        position = new Vector3(x, y, z);
    }

    /**
     * Position Vector Constructor.
     */
    public Vertex (final Vector3 pPos) {
        position = pPos;
    }

    /**
     * Value Constructor.
     *
     * @param pPos Position of Vertex
     * @param pNorm Normal Direction
     * @param st Texture Coordinates
     * @param pCol Vertex Color
     */
    public Vertex (final Vector3 pPos, final Vector3 pNorm, final Vector2 st, final RGBAColor pCol) {
        position = pPos;
        normal = pNorm;
        texCoords = st;
        color = pCol;
    }

    /**
     * Array Constructor.
     * <p/>
     * Construct a vertex from a float[], attempt to consume as much
     * data as possible from the data array.
     *
     * @param data Array of float data which will be interpreted as follows:
     *             {x, y, z}                               - position only
     *             {x, y, z, s, t}                         - position and texture
     *             {x, y, v, nx, ny, nz, s, t}             - position, normal, texture
     *             {x, y, v, nx, ny, nz, s, t, r, g, b, a} - position, normal, texCoords, color
     */
    public Vertex (final float[] data) {
        if (data.length >= 3) {
            position = new Vector3(data[0], data[1], data[2]);
        }

        if (data.length >= 12) {
            color = new RGBAColor(data[8], data[9], data[10], data[11]);
        }
        if (data.length >= 8) {
            normal = new Vector3(data[3], data[4], data[5]);
            texCoords = new Vector2(data[6], data[7]);
        } else if (data.length >= 5) {
            texCoords = new Vector2(data[3], data[4]);
        }
    }

    public Vector3 getPosition () {
        return position;
    }

    public void setPosition (final float x, final float y, final float z) {
        position = new Vector3(x, y, z);
    }

    public void setPosition (final Vector3 position) {
        this.position = position;
    }

    public Vector3 getNormal () {
        return normal;
    }

    public void setNormal (final float x, final float y, final float z) {
        normal = new Vector3(x, y, z);
    }

    public void setNormal (final Vector3 normal) {
        this.normal = normal;
    }

    public Vector2 getTexCoords () {
        return texCoords;
    }

    public void setTexCoords (final float s, final float t) {
        texCoords = new Vector2(s, t);
    }

    public void setTexCoords (final Vector2 st) {
        this.texCoords = st;
    }

    public RGBAColor getColor () {
        return color;
    }

    public void setColor (final float r, final float g, final float b, final float a) {
        color = new RGBAColor(r, g, b, a);
    }

    public void setColor (final RGBAColor color) {
        this.color = color;
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        StringBuilder str = new StringBuilder();
        str.append("<Vertex P=").append(position.toString())
                .append(", N=").append(normal.toString())
                .append(", T=").append(texCoords.toString())
                .append(", C=").append(color.toString())
                .append(">");

        return str.toString();
    }

    /**
     * Pack Data into raw float[].
     * TODO: Might need to consider default values.
     */
    public float[] toFloatArray () {
        float[] data = new float[SIZE];
        data[0] = position.x;
        data[1] = position.y;
        data[2] = position.z;

        data[3] = normal.x;
        data[4] = normal.y;
        data[5] = normal.z;

        data[6] = texCoords.x;
        data[7] = texCoords.y;

        data[8] = color.r;
        data[9] = color.g;
        data[10] = color.b;
        data[11] = color.a;

        return data;
    }
}
