package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

public class FamilyInfo extends User {
    private String familyName; //房间的标题
    private String family_describe; //主题描述
    private List<FansList.SouvenirModelsBean> ranking; //排行榜的列表
    private String whether;         //是否关注
    private String media_url;         //视频地址
    private double countPerson;
    private long fid;
    private long timeMillis;
    private String familyPath;
    private String freeType;
    private double price;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamily_describe() {
        return family_describe;
    }

    public void setFamily_describe(String family_describe) {
        this.family_describe = family_describe;
    }

    public List<FansList.SouvenirModelsBean> getRanking() {
        return ranking;
    }

    public void setRanking(List<FansList.SouvenirModelsBean> ranking) {
        this.ranking = ranking;
    }

    public String getWhether() {
        return whether;
    }

    public void setWhether(String whether) {
        this.whether = whether;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public double getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(double countPerson) {
        this.countPerson = countPerson;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public String getFamilyPath() {
        return familyPath;
    }

    public void setFamilyPath(String familyPath) {
        this.familyPath = familyPath;
    }

    public String getFreeType() {
        return freeType;
    }

    public void setFreeType(String freeType) {
        this.freeType = freeType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
