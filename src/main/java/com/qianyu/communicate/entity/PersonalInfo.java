package com.qianyu.communicate.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class PersonalInfo implements Serializable{

    /**
     * needExperience : 1189
     * level : 5
     * needGold : 1930
     * nickName : AngleBaby
     * sex : 2
     * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg
     * weight : 65.32
     * label : 电影,音乐,游戏,赛马
     * experience : 412800
     * userId : 23
     * gold : 998795900
     * diamond : 57952
     * fubao : 99250
     * userState : 1
     * details : 各种嗨~起来嗨~
     * age : 27
     * height : 173
     * professionId : 1
     */
//    private String expand;
    private String examinePic;
    private String unExaminePic;
    private long needExperience;
    private int level;
    private long needGold;
    private String nickName;
    private int sex;
    private String headUrl;
    private double weight;
    private String label;
    private long experience;
    private long userId;
    private long gold;
    private long diamond;
    private long fubao;
    private int userState;
    private String details;
    private String professionName;
    private int age;
    private double height;
    private int professionId;
    private double expand;

    public String getExaminePic() {
        return examinePic;
    }

    public void setExaminePic(String examinePic) {
        this.examinePic = examinePic;
    }

    public String getUnExaminePic() {
        return unExaminePic;
    }

    public void setUnExaminePic(String unExaminePic) {
        this.unExaminePic = unExaminePic;
    }

    public double getExpand() {
        return expand;
    }

    public void setExpand(double expand) {
        this.expand = expand;
    }

    public long getNeedExperience() {
        return needExperience;
    }

    public void setNeedExperience(long needExperience) {
        this.needExperience = needExperience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getNeedGold() {
        return needGold;
    }

    public void setNeedGold(long needGold) {
        this.needGold = needGold;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getDiamond() {
        return diamond;
    }

    public void setDiamond(long diamond) {
        this.diamond = diamond;
    }

    public long getFubao() {
        return fubao;
    }

    public void setFubao(long fubao) {
        this.fubao = fubao;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getProfessionId() {
        return professionId;
    }

    public void setProfessionId(int professionId) {
        this.professionId = professionId;
    }
}
