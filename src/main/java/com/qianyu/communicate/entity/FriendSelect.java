package com.qianyu.communicate.entity;

import java.io.Serializable;

/**
 * Created by JavaDog on 2019-4-12.
 */

public class FriendSelect implements Serializable {
    private int minage=0;//最小年龄
    private int maxage=41;//最大年龄（ps:超过40 就写大于）
    private long constellationId=0;//星座Id
    private int activetime=0;//活跃时长（传分钟就可以，我默认15分钟）
    private int sex=0;//性别（1 男 2女）

    public int getMinage() {
        return minage;
    }

    public void setMinage(int minage) {
        this.minage = minage;
    }

    public int getMaxage() {
        return maxage;
    }

    public void setMaxage(int maxage) {
        this.maxage = maxage;
    }

    public long getConstellationId() {
        return constellationId;
    }

    public void setConstellationId(long constellationId) {
        this.constellationId = constellationId;
    }

    public int getActivetime() {
        return activetime;
    }

    public void setActivetime(int activetime) {
        this.activetime = activetime;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
