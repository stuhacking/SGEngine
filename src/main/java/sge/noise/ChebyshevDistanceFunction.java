package sge.noise;

import sge.math.Vector2;
import sge.math.Vector3;

public class ChebyshevDistanceFunction implements DistanceFunction {

    @Override
    public float apply (float v1, float v2) {
        return v2 - v1;
    }

    @Override
    public float apply (Vector2 v1, Vector2 v2) {
        Vector2 diff = v1.sub(v2);
        return Math.max(Math.abs(diff.x), Math.abs(diff.y));
    }

    @Override
    public float apply (Vector3 v1, Vector3 v2) {
        Vector3 diff = v1.sub(v2);
        return Math.max(Math.max(Math.abs(diff.x), Math.abs(diff.y)), Math.abs(diff.z));
    }

}
