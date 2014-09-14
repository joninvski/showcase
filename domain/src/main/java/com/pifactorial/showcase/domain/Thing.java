package com.pifactorial.showcase.domain;

public class Thing {

    String mName;
    String mImageUrl;
    String mCategory;
    String mText;
    String mHomepage;
    String mImageWidth;
    String mImageHeight;

    public Thing() {
    }

    public Thing(String name, String imageUrl, String category,
            String text, String homepage, int width, int height) {
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

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = mText;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public void setHomepage(String homepage) {
        this.mHomepage = homepage;
    }

    /**
     * Get imageWidth.
     *
     * @return imageWidth as String.
     */
    public String getImageWidth()
    {
        return mImageWidth;
    }

    /**
     * Set imageWidth.
     *
     * @param imageWidth the value to set.
     */
    public void setImageWidth(String imageWidth)
    {
        this.mImageWidth = imageWidth;
    }

    /**
     * Get ImageHeight.
     *
     * @return imageHeight as String.
     */
    public String getImageHeight()
    {
        return mImageHeight;
    }

    /**
     * Set imageHeight.
     *
     * @param imageHeight the value to set.
     */
    public void setImageHeight(String imageHeight)
    {
        this.mImageHeight = imageHeight;
    }
}
