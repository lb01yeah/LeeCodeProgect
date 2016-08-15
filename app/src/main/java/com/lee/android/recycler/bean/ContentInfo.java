package com.lee.android.recycler.bean;

import com.lee.android.R;

/**
 * Created by android on 16-8-15.
 * 控制Recycler 数据实体
 */
public class ContentInfo {

    private int portrait = R.mipmap.ic_launcher;

    private String nickName;
    private String motto;

    public int getPortrait() {
        return portrait;
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

}
