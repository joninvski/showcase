package com.ubaza.domain;

public class Ringtone {

    String mName;
    String mUri;

    public Ringtone() {
    }

    public Ringtone(String name, String uri) {
        mName = name;
        mUri = uri;
    }

    public String getName() {
        return mName;
    }

    public String getUri() {
        return mUri;
    }

    public String toString() {
        return mName + " -> " + mUri;
    }
}
