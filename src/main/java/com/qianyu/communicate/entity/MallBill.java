package com.qianyu.communicate.entity;

import java.io.Serializable;

/**
 * Created by JavaDog on 2019-6-5.
 */

public class MallBill implements Serializable{
    private int type=1;
    private long userId=0;
    private long startTime=0;
    private long endTime=0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
