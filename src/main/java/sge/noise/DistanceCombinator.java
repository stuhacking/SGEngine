package sge.noise;

import java.util.NavigableSet;

public interface DistanceCombinator {

    public float apply (NavigableSet<Float> distances);
}
