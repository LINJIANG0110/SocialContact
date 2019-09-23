package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-7-24.
 */

public class MyLevel {

    /**
     * userGold : 9963042
     * needExp : 37552
     * needGold : 7611
     * level : 13
     * userExp : 335930603
     */

    private long userGold;
    private long needExp;
    private long needGold;
    private int level;
    private long userExp;

    public long getUserGold() {
        return userGold;
    }

    public void setUserGold(long userGold) {
        this.userGold = userGold;
    }

    public long getNeedExp() {
        return needExp;
    }

    public void setNeedExp(long needExp) {
        this.needExp = needExp;
    }

    public long getNeedGold() {
        return needGold;
    }

    public void setNeedGold(long needGold) {
        this.needGold = needGold;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getUserExp() {
        return userExp;
    }

    public void setUserExp(long userExp) {
        this.userExp = userExp;
    }
}
