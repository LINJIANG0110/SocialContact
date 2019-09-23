package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class ImpressLabel {

    /**
     * detailsId : 1
     * lalelName : 高富帅
     */

    private long detailsId;
    private String labelName;
    private boolean isSelected;

    public ImpressLabel(String labelName) {
        this.labelName = labelName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(long detailsId) {
        this.detailsId = detailsId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
