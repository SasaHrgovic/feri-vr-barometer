package eu.hrgovic.feri.vrbarometer.Models;

public class Location {
    private String mUid;
    private String mName;
    private String mAddress;
    private String mCity;

    public Location() {}

    public Location(String name, String address, String city) {
        mName = name;
        mAddress = address;
        mCity = city;
    }

    public String getUid() { return mUid; }
    public void setUid(String uid) { mUid = uid; }

    public String getName() { return mName; }
    public void setName(String name) { mName = name; }

    public String getAddress() { return mAddress; }
    public void setAddress(String address) { mAddress = address; }

    public String getCity() { return mCity; }
    public void setCity(String city) { mCity = city; }

    @Override
    public String toString() {
        return mName;
    }
}
