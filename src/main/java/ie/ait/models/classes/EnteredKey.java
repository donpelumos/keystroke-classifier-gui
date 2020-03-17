package ie.ait.models.classes;

/**
 * Created by Pelumi.Oyefeso on 14-Mar-2020
 */
public class EnteredKey {
    private String key;
    private long timePressed;
    private long timeReleased;
    /**
     * The dwell time it takes for a key to be pressed and released in milli-seconds.
     */
    private double dwellTime = 0;

    public EnteredKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDwellTime(double dwellTime){
        this.dwellTime = dwellTime;
    }

    public double getDwellTime() {
        return dwellTime;
    }

    public long getTimePressed() {
        return timePressed;
    }

    public void setTimePressed(long timePressed) {
        this.timePressed = timePressed;
    }

    public long getTimeReleased() {
        return timeReleased;
    }

    public void setTimeReleased(long timeReleased) {
        this.timeReleased = timeReleased;
    }

    @Override
    public String toString() {
        return "EnteredKey{" +
                "key='" + key + '\'' +
                ", timePressed=" + timePressed +
                ", timeReleased=" + timeReleased +
                ", dwellTime=" + dwellTime +
                '}';
    }
}
