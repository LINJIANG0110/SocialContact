package com.qianyu.communicate.bukaSdk.entity;


import com.qianyu.communicate.entity.EnterGroup;

import org.haitao.common.utils.AppLog;

import java.io.Serializable;

import tv.buka.sdk.utils.JsonUtils;

public class UserBean implements Serializable {
    //通用参数
    private String userflag;//0第一次进群
    private String groupId;     //房间id
    private long userId;  //用户id
    private int sex;
    private int level;
    private int age;
    private String nickName;
    private String phone;
    private String headUrl;
    private String address;
    private String userNum;  //用户帐号
    private String text;     //聊天内容
    private String messageExt;// 拓展字段 新版本新增红包功能后添加 该字段为json
    private int mstype;  //0正常聊天消息  1 禁言消息 2取消禁言消息 3禁入消息 4取消禁入消息 5设置管理员消息  6踢出消息
    private int mike;// 1上一麦 2上二麦  3下一麦 4下二麦
    private int userType;  // 1群主 2管理 3成员 4游客
    private int confusionNum;//胡言乱语剩余次数
    private long jinyantime;//禁言的剩余时间
    private long tichutime;//踢出的剩余时间
    private EnterGroup.ManagerUser jinYanInfo;//禁言人的信息
    private EnterGroup.ManagerUser tichunInfo;//踢出人的信息
    private String designationUrl;//称号的url
    private String mountName;//坐骑名字
    private String mountPicPath;//坐骑图片
    private double expand;//代理图标

    public UserBean(String json) {
        if (isJsonObject(json)) {
            try {
                UserBean userBean = JsonUtils.getBean(json, getClass());
                setUserflag(userBean.getUserflag());
                setUserId(userBean.getUserId());
                setGroupId(userBean.getGroupId());
                setNickName(userBean.getNickName());
                setPhone(userBean.getPhone());
                setHeadUrl(userBean.getHeadUrl());
                setSex(userBean.getSex());
                setLevel(userBean.getLevel());
                setAddress(userBean.getAddress());
                setAge(userBean.getAge());
                setUserType(userBean.getUserType());
                setText(userBean.getText());
                setMessageExt(userBean.getMessageExt());
                setUserNum(userBean.getUserNum());
                setMstype(userBean.getMstype());
                setMike(userBean.getMike());
                setConfusionNum(userBean.getConfusionNum());
                setJinyantime(userBean.getJinyantime());
                setTichutime(userBean.getTichutime());
                setJinYanInfo(userBean.getJinYanInfo());
                setTichunInfo(userBean.getTichunInfo());
                setDesignationUrl(userBean.getDesignationUrl());
                setMountName(userBean.getMountName());
                setMountPicPath(userBean.getMountPicPath());
            } catch (Exception e) {
                AppLog.e("========userBean========" + e.getMessage());
            }
        }
    }

    public UserBean() {
    }

    public String getUserflag() {
        return userflag;
    }

    public void setUserflag(String userflag) {
        this.userflag = userflag;
    }

    public String getMessageExt() {
        return messageExt;
    }

    public void setMessageExt(String messageExt) {
        this.messageExt = messageExt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMstype() {
        return mstype;
    }

    public void setMstype(int mstype) {
        this.mstype = mstype;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public int getMike() {
        return mike;
    }

    public void setMike(int mike) {
        this.mike = mike;
    }

    public int getConfusionNum() {
        return confusionNum;
    }

    public void setConfusionNum(int confusionNum) {
        this.confusionNum = confusionNum;
    }

    public long getJinyantime() {
        return jinyantime;
    }

    public void setJinyantime(long jinyantime) {
        this.jinyantime = jinyantime;
    }

    public long getTichutime() {
        return tichutime;
    }

    public void setTichutime(long tichutime) {
        this.tichutime = tichutime;
    }

    public EnterGroup.ManagerUser getJinYanInfo() {
        return jinYanInfo;
    }

    public void setJinYanInfo(EnterGroup.ManagerUser jinYanInfo) {
        this.jinYanInfo = jinYanInfo;
    }

    public EnterGroup.ManagerUser getTichunInfo() {
        return tichunInfo;
    }

    public void setTichunInfo(EnterGroup.ManagerUser tichunInfo) {
        this.tichunInfo = tichunInfo;
    }

    public String getDesignationUrl() {
        return designationUrl;
    }

    public void setDesignationUrl(String designationUrl) {
        this.designationUrl = designationUrl;
    }

    public String getMountName() {
        return mountName;
    }

    public void setMountName(String mountName) {
        this.mountName = mountName;
    }

    public String getMountPicPath() {
        return mountPicPath;
    }

    public void setMountPicPath(String mountPicPath) {
        this.mountPicPath = mountPicPath;
    }

    public double getExpand() {
        return expand;
    }

    public void setExpand(double expand) {
        this.expand = expand;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "groupId='" + groupId + '\'' +
                ", userId=" + userId +
                ", sex=" + sex +
                ", level=" + level +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", address='" + address + '\'' +
                ", userNum='" + userNum + '\'' +
                ", text='" + text + '\'' +
                ", mstype=" + mstype +
                ", mike=" + mike +
                ", userType=" + userType +
                ", confusionNum=" + confusionNum +
                ", jinyantime=" + jinyantime +
                ", tichutime=" + tichutime +
                ", jinYanInfo=" + jinYanInfo +
                ", tichunInfo=" + tichunInfo +
                ", designationUrl=" + designationUrl +
                ", mountName=" + mountName +
                ", mountPicPath=" + mountPicPath +
                ", expand=" + expand +
                '}';
    }

    public boolean isJsonObject(String str) {
        final char[] strChar = str.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];
        return firstChar == '{';
    }

}
