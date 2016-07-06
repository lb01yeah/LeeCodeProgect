package com.lee.android.nohttps.entity;

/**
 * Created by android on 16-7-4.
 */
public class ListItem {

    private String mTitle;
    private String mSubTitle;

    public ListItem(String mTitle,String mSubTitle) {
        this.mTitle = mTitle;
        this.mSubTitle = mSubTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmSubTitle() {
        return mSubTitle;
    }

    public void setmSubTitle(String mSubTitle) {
        this.mSubTitle = mSubTitle;
    }
}
