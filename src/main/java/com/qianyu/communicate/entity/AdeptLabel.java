package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class AdeptLabel {

    /**
     * detailsId : 1
     * lalelName : 高富帅
     */

    private long aId;
    private String adeptName;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getaId() {
        return aId;
    }

    public void setaId(long aId) {
        this.aId = aId;
    }

    public String getAdeptName() {
        return adeptName;
    }

    public void setAdeptName(String adeptName) {
        this.adeptName = adeptName;
    }
}
