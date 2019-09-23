package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-6-11.
 */

public class SkillList {

    /**
     * createTime : 1554201224000
     * level : 2
     * number : 460
     * skillDescribe : 使对方魅力-0.15%(额外-20)，最高-1569
     * skillEnergy : 0
     * skillName : 六脉神剑
     * skillPicPath : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg
     * skillType : KILLFACE
     * skillUserId : 4
     * state : 1
     * updateTime : 1556442105000
     * userId : 23
     */

    private long createTime;
    private int level;
    private int number;
    private String skillDescribe;
    private int skillEnergy;
    private String skillName;
    private String skillPicPath;
    private String staticPath;
    private String skillType;
    private long skillUserId;
    private int state;
    private long updateTime;
    private long userId;
    private boolean isSelected=false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSkillDescribe() {
        return skillDescribe;
    }

    public void setSkillDescribe(String skillDescribe) {
        this.skillDescribe = skillDescribe;
    }

    public int getSkillEnergy() {
        return skillEnergy;
    }

    public void setSkillEnergy(int skillEnergy) {
        this.skillEnergy = skillEnergy;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillPicPath() {
        return skillPicPath;
    }

    public void setSkillPicPath(String skillPicPath) {
        this.skillPicPath = skillPicPath;
    }

    public String getStaticPath() {
        return staticPath;
    }

    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public long getSkillUserId() {
        return skillUserId;
    }

    public void setSkillUserId(long skillUserId) {
        this.skillUserId = skillUserId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
