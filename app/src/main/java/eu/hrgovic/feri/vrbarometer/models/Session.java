package eu.hrgovic.feri.vrbarometer.models;

public class Session {
    private String mUid;
    private String mName;
    private int mInterval;
    private String mDeviceUid;
    private String mLocationUid;

    public Session() {}

    public Session(String uid, String name, int interval) {
        mUid = uid;
        mName = name;
        mInterval = interval;
    }

    public String getUid() { return mUid; }
    public void setUid(String uid) { mUid = uid; }

    public String getName() { return mName; }
    public void setName(String name) { mName = name; }

    public int getInterval() { return mInterval; }
    public void setInterval(int interval) { mInterval = interval; }

    public String getDeviceUid() { return mDeviceUid; }
    public void setDeviceUid(String deviceUid) { mDeviceUid = deviceUid; }

    public String getLocationUid() { return mLocationUid; }
    public void setLocationUid(String locationUid) { mLocationUid = locationUid; }
}
