package sge.geometry.primitive;

import sge.geometry.Mesh;

/**
 * Primitive objects are producers of Meshes based on
 * simple shapes, e.g. Plane, Cube, Polyhedra
 */
public interface Primitive {

    /**
     * Produce the Mesh.
     *
     * @return A new {@link Mesh} object representing the primitive shape.
     */
    Mesh toMesh ();
}
