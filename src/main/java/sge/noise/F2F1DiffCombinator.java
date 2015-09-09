package sge.noise;

import java.util.NavigableSet;

public class F2F1DiffCombinator implements DistanceCombinator {

    @Override
    public float apply (NavigableSet<Float> distances) {
        float first = distances.first();
        float second = 2f * first;

        if (null != distances.higher(first))
            second = distances.higher(first);

        return second - first;
    }

}
