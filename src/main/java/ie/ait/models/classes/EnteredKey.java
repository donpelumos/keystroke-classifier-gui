package ie.ait.models.classes;

/**
 * Created by Pelumi.Oyefeso on 14-Mar-2020
 */
public class EnteredKey {
    private String key;
    private long timeValue;
    /**
     * The dwell time for a pressed key in milli-seconds.
     */
    private double dwellTime = 0;

    public EnteredKey(String key, long timeValue) {
        this.key = key;
        this.timeValue = timeValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(long timeValue) {
        this.timeValue = timeValue;
    }

    public void setDwellTime(double dwellTime){
        this.dwellTime = dwellTime;
    }

    public double getDwellTime() {
        return dwellTime;
    }

    @Override
    public String toString() {
        return "EnteredKey{" +
                "key='" + key + '\'' +
                ", timeValue=" + timeValue +
                '}';
    }
}
