package com.ubaza.domain;

public class Ringtone {

    String mName;
    String mUri;

    public Ringtone(String name, String uri) {
        mName = name;
        mUri = uri;
    }

    public String toString() {
        return mName + " -> " + mUri;
    }
}
