package sge.noise;

import java.util.NavigableSet;

public class F1Combinator implements DistanceCombinator {

    @Override
    public float apply (NavigableSet<Float> distances) {
        return distances.first();
    }

}
