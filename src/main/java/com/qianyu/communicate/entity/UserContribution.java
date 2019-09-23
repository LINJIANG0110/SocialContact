package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class UserContribution {

    /**
     * contribution : 56567
     * countFans : 2
     * countMoney : 0
     */

    private int contribution;
    private int totalFans;
    private int countMoney;
    private int totalConcerns;
    private int totalCollects;

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }

    public int getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(int countMoney) {
        this.countMoney = countMoney;
    }

    public int getTotalConcerns() {
        return totalConcerns;
    }

    public void setTotalConcerns(int totalConcerns) {
        this.totalConcerns = totalConcerns;
    }

    public int getTotalCollects() {
        return totalCollects;
    }

    public void setTotalCollects(int totalCollects) {
        this.totalCollects = totalCollects;
    }
}
