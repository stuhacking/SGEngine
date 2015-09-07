package sge.util;

public class Time {

    public static final long SECOND = 1000000000L;

    private static long lastTime;
    private static double unprocessedTime;

    /** The desired time for a single frame (sec). */
    private static double frameTime;

    /** The actual amount of time elapsed since last update (sec). */
    private static long delta;

    public static void init () {
        init(1000);
    }

    public static void init (int fpsCap) {
        lastTime = getTime();
        delta = lastTime;
        unprocessedTime = 0;
        frameTime = 1.0 / (double)fpsCap;
    }

    public static long getTime () {
        return System.nanoTime();
    }

    public static void update () {
        long currentTime = getTime();
        delta = (currentTime - lastTime);

        unprocessedTime += delta / (double) SECOND;

        lastTime = currentTime;
    }

    /**
     * Get Frame Time
     */
    public static double getFrameTime () {
        return frameTime;
    }

    /**
     * Get Time Delta since last update (frame).
     *
     * @return
     */
    public static long getDelta () {
        return delta;
    }

    public static double getDeltaSeconds () {
        return delta / (double) SECOND;
    }

    public static double getUnprocessedTime () {
        return unprocessedTime;
    }

    public static void processTime (double duration) {
        unprocessedTime -= duration;
    }
}
