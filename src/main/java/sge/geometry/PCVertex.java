package sge.geometry;

import sge.color.RGBAColor;
import sge.math.FMath;
import sge.math.Vector2;
import sge.math.Vector3;

/**
 * Position-Color-Vertex
 *
 * Colored Vertex object comprising:
 * position  - vec3      (float 3)
 * color     - RGBAColor (float 4)
 *
 * Data size - 7 floats, Expect 28 bytes (but calculate from Java float size.)
 */
public class PCVertex {

    public static final int SIZE = Vector3.SIZE + RGBAColor.SIZE;
    public static final int BYTE_SIZE = SIZE * FMath.FLOAT_SIZE;

    /** Vertex Position. */
    public Vector3 position = Vector3.ZERO;

    /** Vertex Color. */
    public RGBAColor color = RGBAColor.WHITE;

    /**
     * Default Constructor.
     */
    public PCVertex () { }

    /**
     * Copy Constructor.
     */
    public PCVertex (final PCVertex copy) {
        position = copy.position;
        color = copy.color;
    }

    /**
     * Position Value Constructor.
     */
    public PCVertex (final float x, final float y, final float z) {
        position = new Vector3(x, y, z);
    }

    /**
     * Position Vector Constructor.
     */
    public PCVertex (final Vector3 pPos) {
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
    public PCVertex (final Vector3 pPos, final Vector3 pNorm, final Vector2 st, final RGBAColor pCol) {
        position = pPos;
        color = pCol;
    }

    /**
     * Array Constructor.
     *
     * Construct a vertex from a float[], attempt to consume as much
     * data as possible from the data array.
     *
     * @param data Array of float data which will be interpreted as follows:
     *             {x, y, z}                                    - position only
     *             {x, y, v, r, g, b, a, ...} - position, color
     */
    public PCVertex (final float[] data) {
        if (data.length >= 3) {
            position = new Vector3(data[0], data[1], data[2]);
        }

        if (data.length >= 7) {
            color = new RGBAColor(data[3], data[4], data[5], data[6]);
        }
    }

    public void setPosition (final float x, final float y, final float z) {
        position = new Vector3(x, y, z);
    }

    public void setColor (final float r, final float g, final float b, final float a) {
        color = new RGBAColor(r, g, b, a);
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        StringBuilder str = new StringBuilder();
        str.append("<PCVertex P=").append(position.toString())
                .append(", C=").append(color.toString())
                .append(">");

        return str.toString();
    }

    /**
     * Pack Data into float[].
     */
    public float[] toFloatArray () {
        float[] data = new float[SIZE];
        data[0] = position.x;
        data[1] = position.y;
        data[2] = position.z;

        data[3] = color.r;
        data[4] = color.g;
        data[5] = color.b;
        data[6] = color.a;

        return data;
    }
}
