package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class RechargeList {

    /**
     * createTime : 1557902626000
     * picPath : http://qianyubk.oss-cn-shanghai.aliyuncs.com/4753125196318878.png
     * productDescribe : 游戏基础货币
     * productId : 1
     * productName : 60钻石
     * productOrder : 1
     * productPrice : 6
     * productType : 1
     * reward : 60
     * state : 1
     */

    private long createTime;
    private String picPath;
    private String productDescribe;
    private int productId;
    private String productName;
    private int productOrder;
    private double productPrice;
    private int productType;
    private int reward;
    private int state;
    private int isHot;
    private boolean isSelected;
    private int count=1;

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(int productOrder) {
        this.productOrder = productOrder;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
