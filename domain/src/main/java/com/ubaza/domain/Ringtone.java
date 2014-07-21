package com.ubaza.domain;

public class Ringtone {

    String mName;
    String mUri;
    int mPriceCents;

    public Ringtone() {
    }

    public Ringtone(String name, String uri, int price) {
        mName = name;
        mUri = uri;
        mPriceCents = price;
    }

    public String getName() {
        return mName;
    }

    public String getUri() {
        return mUri;
    }

    public String toString() {
        return mName + " -> " + mUri  + " " + getPriceString();
    }

    public int getPrice()
    {
        return mPriceCents;
    }
    public String getPriceString()
    {
        return String.format("%d.%d€", getPrice() / 100, getPrice() % 100);
    }

    public void setPrice(int price)
    {
        this.mPriceCents = price;
    }
}
