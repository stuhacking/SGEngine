package sge.importer.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import sge.math.Vector2;
import sge.math.Vector3;

/**
 * This class represents a parsed .obj document in memory.
 * .obj models are represented as a list of vertex positions (v),
 * normal vectors (vn) and texture coordinates (vt). These are
 * referenced by face descriptions (f) (by index) and
 * organised into subgroups (o).
 *
 * e.g.
 * o Triangle
 * # List of vertices:
 * v 0 0 0   # Bottom left.
 * v 5 0 0   # Bottom right.
 * v 5 5 0   # Top    right.
 *
 * # List of normals
 * vn 0 0 1
 *
 * # List of Texture coords
 * vt 0 0
 * vt 1 0
 * vt 1 1
 *
 * # List of faces:
 * f 1/1/1 2/2/1 3/3/1   # Triangle.
 */
public class ObjDocument {

    private static final Logger logger = Logger.getLogger(ObjDocument.class);

    public static final int DEFAULT_SIZE = 128;
    public static final int DEFAULT_GROUP_SIZE = 8;

    private String filename;
    private String name ="untitled_obj";

    private ArrayList<Vector3> positionVectors = new ArrayList<Vector3>(DEFAULT_SIZE);
    private ArrayList<Vector3> normalVectors = new ArrayList<Vector3>(DEFAULT_SIZE);
    private ArrayList<Vector2> textureCoords = new ArrayList<Vector2>(DEFAULT_SIZE);

    private ArrayList<ObjGroup> subgroups = new ArrayList<ObjGroup>(DEFAULT_GROUP_SIZE);

    private ObjGroup currentSubgroup;

    private boolean hasNormals = false;
    private boolean hasTexCoords = false;

    /**
     * Parse an Obj File.
     */
    public ObjDocument (final String filename) {
        this.filename = filename;
        readFromFile(filename);
    }

    public String getName () {
        return name;
    }

    public boolean hasNormals () {
        return hasNormals;
    }

    public boolean hasTexCoords () {
        return hasTexCoords;
    }

    public int getSize () {
        return positionVectors.size();
    }

    public int getFaceCount () {
        int count = 0;

        for (ObjGroup o : subgroups) {
            count += o.getFaceCount();
        }

        return count;
    }

    public Vector3 getPosition (int i) {
        return positionVectors.get(i);
    }

    public void addPosition (Vector3 vec) {
        positionVectors.add(vec);
    }

    public Vector3 getNormal (int i) {
        return normalVectors.get(i);
    }

    public void addNormal (Vector3 vec) {
        normalVectors.add(vec);
    }

    public Vector2 getTexCoord (int i) {
        return textureCoords.get(i);
    }

    public void addTexCoord (Vector2 vec) {
        textureCoords.add(vec);
    }

    public ArrayList<ObjGroup> subgroups () {
        return subgroups;
    }

    public void addSubgroup (ObjGroup group) {
        subgroups.add(group);
    }

    /**
     * Parse a .obj file producing an ObjDocument object.
     */
    private void readFromFile (final String filename) {
        File f = new File(filename);

        try (BufferedReader inStream = new BufferedReader(new FileReader(f))) {
            String line;
            int lineNumber = 1;

            while ((line = inStream.readLine()) != null) {

                if (line.length() <= 1 || line.startsWith("#")) {
                    // Comment or blank line - skip
                    continue;
                }

                String[] tokens = line.split(" +");
                switch (tokens[0]) {
                    case "o":
                        parseObjName(lineNumber, tokens);
                        break;
                    case "g":
                        parseSubgroup(lineNumber, tokens);
                        break;
                    // Line is a description of a vertex attr
                    case "v":
                        parsePositionVector(lineNumber, tokens);
                        break;
                    // Line is a normal
                    case "vn":
                        parseVertexNormals(lineNumber, tokens);
                        break;
                    // Line is a texture coordinate
                    case "vt":
                        parseTexCoords(lineNumber, tokens);
                        break;
                    // Line is a Face/Polygon mapping
                    case "f":
                        parseFace(lineNumber, tokens);
                        break;
                }

                lineNumber++;
            }

            // Add final group.
            if (null != currentSubgroup) {
                addSubgroup(currentSubgroup);
            }

        } catch (FileNotFoundException e) {
            logger.error("File not found at location:" + filename, e);
        } catch (IOException e) {
            logger.error("Error reading file: " + filename, e);
        }
    }

    private void parseObjName (final int lineNumber, final String[] tokens) {
        if (tokens.length < 2) {
            logger.error("Untitled object marker in .obj doc: " + filename + " at line: " + lineNumber);
            return;
        }

        name = tokens[1];
    }

    private void parseSubgroup (final int lineNumber, final String[] tokens) {

        if (null != currentSubgroup) {
            addSubgroup(currentSubgroup);
        }

        if (tokens.length < 2) {
            logger.error("Untitled group marker in .obj doc: " + filename + " at line: " + lineNumber);
            currentSubgroup = new ObjGroup();
        } else {
            currentSubgroup = new ObjGroup(tokens[1]);
        }

    }

    private void parsePositionVector (final int lineNumber, final String[] tokens) {

        if (tokens.length < 4) {
            logger.error("Malformed Vertex Position in .obj doc: " + filename + " at line: " + lineNumber);
            addPosition(Vector3.ZERO);
        } else {
            float x = Float.parseFloat(tokens[1]);
            float y = Float.parseFloat(tokens[2]);
            float z = Float.parseFloat(tokens[3]);

            addPosition(new Vector3(x, y, z));
        }
    }

    private void parseVertexNormals (final int lineNumber, final String[] tokens) {

        if (tokens.length < 4) {
            logger.error("Malformed Normal in .obj doc: " + filename + " at line: " + lineNumber);
            addNormal(Vector3.ZERO);
        } else {
            float x = Float.parseFloat(tokens[1]);
            float y = Float.parseFloat(tokens[2]);
            float z = Float.parseFloat(tokens[3]);

            addNormal(new Vector3(x, y, z));
        }

        hasNormals = true;
    }

    private void parseTexCoords (final int lineNumber, final String[] tokens) {

        if (tokens.length < 3) {
            logger.error("Malformed Texture Coord in .obj doc: " + filename + " at line: " + lineNumber);
            addTexCoord(Vector2.ZERO);
        } else {
            float x = Float.parseFloat(tokens[1]);
            float y = Float.parseFloat(tokens[2]);

            addTexCoord(new Vector2(x, y));
        }

        hasTexCoords = true;
    }

    // Parse lines which describe a face using references to vertex data (1-indexed!)
    // Faces contain 3 or more references to vertex data- this method will converted such
    //  polygons to triangles.
    // Faces have the following formats:
    //  f 1 2 3 4
    //  f 1//1 2//1 3//1 4//1 # with positions & normals
    //  f 1/1/3 2/2/3 3/3/1 4/4/1 # with positions & tex coords & normals
    private void parseFace (final int lineNumber, final String[] tokens) {

        // If there hasn't been a group marker before we hit the first
        // face, we need to create it.
        if (null == currentSubgroup) {
            currentSubgroup = new ObjGroup();
        }

        // Skip malformed faces -will cause holes in output mesh, but not fatal.
        if (tokens.length < 4) {
            logger.error("Malformed Face in .obj doc: " + filename + " at line: " + lineNumber);
            return;
        }

        // Convert n-sided faces to triangles while reading.
        for (int i = 3; i < tokens.length; i++) {
            // TODO: Parse out these index lists
            String[] vec1Indices = tokens[1].split("/");
            String[] vec2Indices = tokens[i - 1].split("/");
            String[] vec3Indices = tokens[i].split("/");

            // Vertex 1
            int xyz = Integer.parseInt(vec1Indices[0]) - 1;
            currentSubgroup.addPositionIndex(xyz);

            if (hasTexCoords) {
                int tex = Integer.parseInt(vec1Indices[1]) - 1;
                currentSubgroup.addTexCoordIndex(tex);
            }

            if (hasNormals) {
                int vn = Integer.parseInt(vec1Indices[2]) - 1;
                currentSubgroup.addNormalIndex(vn);
            }

            // Vertex 2
            xyz = Integer.parseInt(vec2Indices[0]) - 1;
            currentSubgroup.addPositionIndex(xyz);

            if (hasTexCoords) {
                int tex = Integer.parseInt(vec2Indices[1]) - 1;
                currentSubgroup.addTexCoordIndex(tex);
            }

            if (hasNormals) {
                int vn = Integer.parseInt(vec2Indices[2]) - 1;
                currentSubgroup.addNormalIndex(vn);
            }

            // Vertex 3
            xyz = Integer.parseInt(vec3Indices[0]) - 1;
            currentSubgroup.addPositionIndex(xyz);

            if (hasTexCoords) {
                int tex = Integer.parseInt(vec3Indices[1]) - 1;
                currentSubgroup.addTexCoordIndex(tex);
            }

            if (hasNormals) {
                int vn = Integer.parseInt(vec3Indices[2]) - 1;
                currentSubgroup.addNormalIndex(vn);
            }
        }
    }
}
