package ie.ait.models.classes;

import java.util.Arrays;

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

    public boolean isValidAlphabet(){
        String [] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
                "T","U","V", "W","X","Y","Z"};
        return Arrays.asList(alphabets).contains(this.getKey());
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
