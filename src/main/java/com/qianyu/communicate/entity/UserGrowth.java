package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class UserGrowth {

    /**
     * currentMonth : 1
     * totalFans : 2
     * totalIncome : 300
     * totalPlay : 0
     * yesterdayFans : 2
     * yesterdayGrowth : 0
     */

    private double currentMonth;
    private int totalFans;
    private double totalIncome;
    private int totalPlay;
    private int yesterdayFans;
    private int yesterdayGrowth;
    private User users;

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public double getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(double currentMonth) {
        this.currentMonth = currentMonth;
    }

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalPlay() {
        return totalPlay;
    }

    public void setTotalPlay(int totalPlay) {
        this.totalPlay = totalPlay;
    }

    public int getYesterdayFans() {
        return yesterdayFans;
    }

    public void setYesterdayFans(int yesterdayFans) {
        this.yesterdayFans = yesterdayFans;
    }

    public int getYesterdayGrowth() {
        return yesterdayGrowth;
    }

    public void setYesterdayGrowth(int yesterdayGrowth) {
        this.yesterdayGrowth = yesterdayGrowth;
    }
}
