package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-3-18.
 */

public class FamilyManage {
    private int id;
    private String manageName;
    private boolean isSelected = false;

    public FamilyManage(int id,String manageName) {
        this.id=id;
        this.manageName = manageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
