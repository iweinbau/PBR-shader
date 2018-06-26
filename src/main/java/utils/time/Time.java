package utils.time;

/**
 *
 * Help class used for manage Time of the engine.
 *
 */
public class Time {

    private static int delta_time;
    private static long prevTime = System.nanoTime();

    /**
     *
     * Need to call this once each update.
     *
     */
    public static void updateTime(){

        long time = System.nanoTime();
        delta_time = (int) ((time - prevTime) / 1000000);
        prevTime = time;
    }

    /**
     *
     * Can be called infinite times and returns always the same value if in the same update.
     *
     * @return the dt of the last time updateTime is called
     */
    public static float getDeltaTime(){
        return delta_time/1000f;
    }

}
