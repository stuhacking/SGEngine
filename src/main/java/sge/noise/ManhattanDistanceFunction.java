package sge.noise;

import sge.math.Vector2;
import sge.math.Vector3;

public class ManhattanDistanceFunction implements DistanceFunction {

    @Override
    public float apply (float v1, float v2) {
        return v2 - v1;
    }

    @Override
    public float apply (Vector2 v1, Vector2 v2) {
        return Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y);
    }

    @Override
    public float apply (Vector3 v1, Vector3 v2) {
        return Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y) + Math.abs(v1.z - v2.z);
    }

}
