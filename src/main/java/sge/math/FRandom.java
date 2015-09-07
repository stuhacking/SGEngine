package sge.math;

import java.util.HashMap;
import java.util.Random;

public final class FRandom {

    private static HashMap<Long, Random> generators =
            new HashMap<Long, Random>();

    public static Random getRandom () {
        long thread = Thread.currentThread().getId();
        if (!generators.containsKey(thread)) {
            generators.put(thread, new Random());
        }

        return generators.get(thread);
    }
}
