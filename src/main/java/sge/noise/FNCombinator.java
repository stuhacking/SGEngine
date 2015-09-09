package sge.noise;

import java.util.NavigableSet;

public class FNCombinator implements DistanceCombinator {

    @Override
    public float apply (NavigableSet<Float> distances) {
        return distances.last();
    }

}
