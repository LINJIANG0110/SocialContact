package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-5-17.
 */

public class BannerList {

    /**
     * bannerName : 活动1
     * bannerOrder : 1
     * bannerUrl : http://thirdqq.qlogo.cn/g?b=oidb&k=tnJ3t6gMlsXRUGO4icZAXsA&s=100
     * createTime : 1558083148000
     * id : 1
     * picUrl : http://thirdqq.qlogo.cn/g?b=oidb&k=tnJ3t6gMlsXRUGO4icZAXsA&s=100
     * state : 1
     */

    private String bannerName;
    private int bannerOrder;
    private String bannerUrl;
    private long createTime;
    private long id;
    private String picUrl;
    private int state;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public int getBannerOrder() {
        return bannerOrder;
    }

    public void setBannerOrder(int bannerOrder) {
        this.bannerOrder = bannerOrder;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
