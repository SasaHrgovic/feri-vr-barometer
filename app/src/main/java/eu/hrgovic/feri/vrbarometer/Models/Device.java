package eu.hrgovic.feri.vrbarometer.Models;

public class Device {
    private String mUid;
    private String mName;
    private String mMac;
    private String mOwner;
    private boolean mIsMeasuring;
    private String mLastSessionUid;

    public Device() {}

    public Device(String uid, String name, String mac, String owner, boolean isMeasuring, String lastSessionUid) {
        mUid = uid;
        mName = name;
        mMac = mac;
        mOwner = owner;
        mIsMeasuring = isMeasuring;
        mLastSessionUid = lastSessionUid;
    }

    public String getUid() { return mUid; }
    public void setUid(String uid) { mUid = uid; }

    public String getName() { return mName; }
    public void setName(String name) { mName = name; }

    public String getMac() { return mMac; }
    public void setMac(String mac) { mMac = mac; }

    public String getOwner() { return mOwner; }
    public void setOwner(String owner) { mOwner = owner; }

    public boolean getIsMeasuring() { return mIsMeasuring; }
    public void setIsMeasuring(boolean isMeasuring) { mIsMeasuring = isMeasuring; }

    public String getLastSessionUid() { return mLastSessionUid; }
    public void setLastSessionUid(String lastSessionUid) { mLastSessionUid = lastSessionUid; }

    @Override
    public String toString() {
        return mName;
    }
}
