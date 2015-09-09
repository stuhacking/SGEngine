package sge.importer.obj;

import java.util.ArrayList;

/**
 * An Obj document has one or more Vertex Groups which keep track of the
 * indices of vertices, normals and texture coordinates in the
 * parent document.
 */
class ObjGroup {

    // If Vertex Groups aren't labelled in the document then assign
    // unique names with a counter.
    private static int untitled_counter = 0;

    /** Group Name. */
    private String name;

    private final ArrayList<Integer> positionIndices = new ArrayList<Integer>(ObjDocument.DEFAULT_SIZE);
    private final ArrayList<Integer> normalIndices = new ArrayList<Integer>(ObjDocument.DEFAULT_SIZE);
    private final ArrayList<Integer> texCoordIndices = new ArrayList<Integer>(ObjDocument.DEFAULT_SIZE);

    /**
     * Construct a group without a name.
     *
     * Names aren't required to be unique, but track
     * untitled objects with a global counter for debugging.
     */
    public ObjGroup () {
        this("group." + untitled_counter);
        untitled_counter++;
    }

    /**
     * Construct a subgroup with a given name.
     */
    public ObjGroup (String name) {
        this.name = name;
    }

    /**
     * Return the name of this group.
     */
    public String getName () {
        return name;
    }

    /**
     * Return the number of vertices in this group.
     */
    public int getVertexCount () {
        return positionIndices.size();
    }

    public int getFaceCount () {
        return positionIndices.size() / 3;
    }

    public void addPositionIndex (int value) {
        positionIndices.add(value);
    }

    public int getPositionIndex (int i) {
        return positionIndices.get(i);
    }

    public void addNormalIndex (int value) {
        normalIndices.add(value);
    }

    public int getNormalIndex (int i) {
        return normalIndices.get(i);
    }

    public void addTexCoordIndex (int value) {
        texCoordIndices.add(value);
    }

    public int getTexCoordIndex (int i) {
        return texCoordIndices.get(i);
    }
}
