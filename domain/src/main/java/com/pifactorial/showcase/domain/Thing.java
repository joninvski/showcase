package com.pifactorial.showcase.domain;

public class Thing {

    String mName;
    String mImageUrl;
    String mCategory;

    public Thing() {
    }

    public Thing(String name, String imageUrl, String category) {
        mName = name;
        mImageUrl = imageUrl;
        mCategory = category;
    }

    public String getName() {
        return mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String toString() {
        return mName + " -> " + mImageUrl + " " + getCategory();
    }

    public String getCategory()
    {
        return mCategory;
    }

    public void setCategory(String category)
    {
        this.mCategory = category;
    }
}
