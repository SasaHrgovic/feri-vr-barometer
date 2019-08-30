package eu.hrgovic.feri.vrbarometer.Models;

public class Session {
    private String mUid;
    private String mName;

    public Session() {}

    public Session(String uid, String name) {
        mUid = uid;
        mName = name;
    }

    public String getUid() { return mUid; }
    public void setUid(String uid) { mUid = uid; }

    public String getName() { return mName; }
    public void setName(String name) { mName = name; }
}
