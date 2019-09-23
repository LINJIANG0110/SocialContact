package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-6-25.
 */

public class AddData {

    /**
     * orderTime : 0
     * orderBuf : 0
     * rankTime : 0
     * popTime : 0
     * rankBuf : 0
     * popBuf : 0
     */

    private long orderTime;
    private long orderBuf;
    private long rankTime;
    private long popTime;
    private long rankBuf;
    private long popBuf;

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public long getOrderBuf() {
        return orderBuf;
    }

    public void setOrderBuf(long orderBuf) {
        this.orderBuf = orderBuf;
    }

    public long getRankTime() {
        return rankTime;
    }

    public void setRankTime(long rankTime) {
        this.rankTime = rankTime;
    }

    public long getPopTime() {
        return popTime;
    }

    public void setPopTime(long popTime) {
        this.popTime = popTime;
    }

    public long getRankBuf() {
        return rankBuf;
    }

    public void setRankBuf(long rankBuf) {
        this.rankBuf = rankBuf;
    }

    public long getPopBuf() {
        return popBuf;
    }

    public void setPopBuf(long popBuf) {
        this.popBuf = popBuf;
    }
}
