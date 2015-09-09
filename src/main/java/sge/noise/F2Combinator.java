package sge.noise;

import java.util.NavigableSet;

public class F2Combinator implements DistanceCombinator {

    @Override
    public float apply (NavigableSet<Float> distances) {
        float first = distances.first();

        if (null != distances.higher(first))
            return distances.higher(first);

        return first;
    }

}
