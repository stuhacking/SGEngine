SGEngine
========

Stuart's Game Engine

This is a work-in-progress library of math and rendering classes intended
for use in simple game or simulation projects. This is a learning project
for my own enjoyment, so I don't intend to release things until I feel that
they are somewhat stable, or at least useful. Many classes are subject to
change often. Please see the License file for licensing details.

Contents
--------

Most of the math libraries can be taken out on their own and used. The rest
of the classes likely depend on LWJGL. ImageIO related stuff, I try and stick
to built in Java platform facilities where possible.

sge.math:
 - Vector[2,3,4]: Immutable value Vectors
 - MVector[2,3,4]: Vectors supporting destructive updates
 - Matrix[2,3,4]: Matrix multiplication
 - Quaternion: Quaternion Rotations

sge.bounds:
 - Line2D: Check intersections of 2D lines
 - Circle: Check intersections and containment of circles
 - Rectangle: Check intersections and containment of rectangles
 - AABB check intersections and containment within Axis
   aligned bounding boxes
 - Sphere check intersections and containment within spheres

sge.color:
 - RGBA color as float ratios (0..1), and int values (0..255)
 - HSL color

sge.geom:
 - Vertex: Drawable Vertex (position, normal, texture coordinate,  color),
 - Triangle: Face composed of 3 Vertices
 - VertexArray: Interleaved float[] with Stride equal to Vertex.SIZE
 - Vector3Array: interleaved float[] with Stride equals to Vector3.SIZE
 - Mesh: Surface object using lists of Vertices and indices

sge.renderer:
 - GLSLProgram: Compile and bind shader resources
 - Image: Loading images from files or from arbitrary producers (tbd)
 - MeshRenderer: Send mesh data to GPU and render objects.
