package eu.hrgovic.feri.vrbarometer.Models;

public class Measurement {
    private int mPressure;
    private int mTemperature;
    private float mHumidity;
    private double mTimestamp;
    private String mSessionUid;

    public Measurement() {}

    public int getPressure() { return mPressure; }
    public void setPressure(int pressure) { mPressure = pressure; }

    public int getTemperature() { return mTemperature; }
    public void setTemperature(int temperature) { mTemperature = temperature; }

    public float getHumidity() { return mHumidity; }
    public void setHumidity(int humidity) { mHumidity = humidity; }

    public double getTimestamp() { return mTimestamp; }
    public void setTimestamp(double timestamp) { mTimestamp = timestamp; }

    public String getSessionUid() { return mSessionUid; }
    public void setSessionUid(String sessionUid) { mSessionUid = sessionUid; }
}
