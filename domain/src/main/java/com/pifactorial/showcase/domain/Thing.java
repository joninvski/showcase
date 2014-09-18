package com.pifactorial.showcase.domain;

public class Thing {

    String mName;
    String mLogoUrl;
    String mProductUrl;
    String mCategory;
    String mText;
    String mHomepage;
    int mLogoWidth;
    int mLogoHeight;
    int mProductWidth;
    int mProductHeight;

    public Thing() {
    }

    public Thing(String name, String logoUrl, String productUrl, String category,
                 String text, String homepage, int logoWidth, int logoHeight,
                 int productWidth, int productHeight) {
        mName = name;
        mLogoUrl = logoUrl;
        mProductUrl = productUrl;
        mCategory = category;
        mText = text;
        mHomepage = homepage;
        mLogoWidth = logoWidth;
        mLogoHeight = logoHeight;
        mProductWidth = productWidth;
        mProductHeight = productHeight;
    }

    public String getName() {
        return mName;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }

    public String toString() {
        return mName + " -> " + mLogoUrl + " " + getCategory();
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
     * Get LogoWidth.
     *
     * @return LogoWidth as String.
     */
    public int getLogoWidth() {
        return mLogoWidth;
    }

    /**
     * Set LogoWidth.
     *
     * @param LogoWidth the value to set.
     */
    public void setLogoWidth(int logoWidth) {
        this.mLogoWidth = logoWidth;
    }

    /**
     * Get LogoHeight.
     *
     * @return LogoHeight as String.
     */
    public int getLogoHeight() {
        return mLogoHeight;
    }

    /**
     * Set LogoHeight.
     *
     * @param LogoHeight the value to set.
     */
    public void setLogoHeight(int logoHeight) {
        this.mLogoHeight = logoHeight;
    }

    /**
     * Get mProductWidth.
     *
     * @return mProductWidth as int.
     */
    public int getProductWidth()
    {
        return mProductWidth;
    }

    /**
     * Set mProductWidth.
     *
     * @param mProductWidth the value to set.
     */
    public void setProductWidth(int mProductWidth)
    {
        this.mProductWidth = mProductWidth;
    }

    /**
     * Get productHeight.
     *
     * @return mProductHeight as int.
     */
    public int getProductHeight()
    {
        return mProductHeight;
    }

    /**
     * Set productHeight.
     *
     * @param mProductHeight the value to set.
     */
    public void setProductHeight(int productHeight)
    {
        this.mProductHeight = productHeight;
    }

    /**
     * Get productUrl.
     *
     * @return productUrl as String.
     */
    public String getProductUrl()
    {
        return mProductUrl;
    }

    /**
     * Set productUrl.
     *
     * @param productUrl the value to set.
     */
    public void setMProductUrl(String productUrl)
    {
        this.mProductUrl = productUrl;
    }
}
