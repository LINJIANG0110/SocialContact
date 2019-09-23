package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class WorkTag {
    private long proId;
    private String headLine;
    private String proName;
    private boolean isChosen=false;

    public long getProId() {
        return proId;
    }

    public void setProId(long proId) {
        this.proId = proId;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }
}
