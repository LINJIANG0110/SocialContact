package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class MyIncome {


    /**
     * canWithDraw : 420
     * currentIncome : 76728.4
     * lastMonthClearing : 0
     * totalClearing : 0
     */

    private double canWithDraw;
    private double currentIncome;
    private double lastMonthClearing;
    private double totalClearing;

    public double getCanWithDraw() {
        return canWithDraw;
    }

    public void setCanWithDraw(double canWithDraw) {
        this.canWithDraw = canWithDraw;
    }

    public double getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(double currentIncome) {
        this.currentIncome = currentIncome;
    }

    public double getLastMonthClearing() {
        return lastMonthClearing;
    }

    public void setLastMonthClearing(double lastMonthClearing) {
        this.lastMonthClearing = lastMonthClearing;
    }

    public double getTotalClearing() {
        return totalClearing;
    }

    public void setTotalClearing(double totalClearing) {
        this.totalClearing = totalClearing;
    }
}
