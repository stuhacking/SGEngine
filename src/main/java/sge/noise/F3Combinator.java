package sge.noise;

import java.util.NavigableSet;

public class F3Combinator implements DistanceCombinator {

    @Override
    public float apply (NavigableSet<Float> distances) {
        float value = distances.first();

        for (int i = 0; i < 2; i++) {
            if (null != distances.higher(value))
                value = distances.higher(value);
        }

        return value;
    }

}
