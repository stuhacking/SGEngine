package sge.bounds;

import org.junit.Test;

import sge.bounds.Sphere;
import sge.math.Vector3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Sphere_Test {

    @Test
    public void testSphereAtOriginContainsPoint () {
        Sphere sphere = new Sphere(5.0f);

        assertTrue(sphere.contains(Vector3.ZERO));
        assertTrue(sphere.contains(new Vector3(0f, 0f, 5f)));
        assertTrue(sphere.contains(new Vector3(2f, 3f, 2f)));

        assertFalse(sphere.contains(new Vector3(6f, 0f, 0f)));
    }

    @Test
    public void testOffsetSphereContainsPoint () {
        Sphere sphere = new Sphere(new Vector3(10f, 5f, 5f), 1.0f);

        assertTrue(sphere.contains(new Vector3(11f, 5f, 5f)));
        assertTrue(sphere.contains(new Vector3(10f, 6f, 5f)));

        assertFalse(sphere.contains(Vector3.ZERO));
        assertFalse(sphere.contains(new Vector3(11f, 6f, 5f)));
    }
}
