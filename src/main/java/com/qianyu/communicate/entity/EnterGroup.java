package com.qianyu.communicate.entity;

import com.google.gson.annotations.SerializedName;
import com.qianyu.communicate.bukaSdk.entity.UserBean;

import java.io.Serializable;

/**
 * Created by JavaDog on 2019-4-22.
 */

public class EnterGroup implements Serializable{


    /**
     * confusionNum : 0
     * jinYanInfo : {"phone":"17699999995","nickName":"蔡徐坤","headUrl":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2774849680,1109054498&fm=26&gp=0.jpg","userId":50}
     * userType : 3
     * tichunInfo : {}
     * jinyantime : 987
     * tichutime : 0
     */
    private String userflag;// 0首次进群（系统发红包）1非首次
    private int confusionNum;
    private ManagerUser jinYanInfo;
    private int userType;
    private ManagerUser tichunInfo;
    private long jinyantime;
    private long tichutime;
    /**
     * introduce :
     * groupOwnerInfo : {"nickName":"敏敏后","phone":"18513713530","level":1,"headUrl":"http://qianyubk.oss-cn-shanghai.aliyuncs.com/6458712748164029.png","sex":1,"userType":1,"userId":86,"age":34,"address":"北京"}
     */

    private String introduce;
    private String groupName;
    private String headUrl;
    private UserBean groupOwnerInfo;
    private int applyNum;//>0表示有人申请入群
    private int bossStatus;//>0表示正在打boss
    private String designationUrl;//称号的url
    private String mountName;//坐骑名字
    private String mountPicPath;//坐骑图片
    private double expand;//代理图标

    public String getUserflag() {
        return userflag;
    }

    public void setUserflag(String userflag) {
        this.userflag = userflag;
    }

    public double getExpand() {
        return expand;
    }

    public void setExpand(double expand) {
        this.expand = expand;
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

    public int getBossStatus() {
        return bossStatus;
    }

    public void setBossStatus(int bossStatus) {
        this.bossStatus = bossStatus;
    }

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    public String getDesignationUrl() {
        return designationUrl;
    }

    public void setDesignationUrl(String designationUrl) {
        this.designationUrl = designationUrl;
    }

    public int getConfusionNum() {
        return confusionNum;
    }

    public void setConfusionNum(int confusionNum) {
        this.confusionNum = confusionNum;
    }

    public ManagerUser getJinYanInfo() {
        return jinYanInfo;
    }

    public void setJinYanInfo(ManagerUser jinYanInfo) {
        this.jinYanInfo = jinYanInfo;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public ManagerUser getTichunInfo() {
        return tichunInfo;
    }

    public void setTichunInfo(ManagerUser tichunInfo) {
        this.tichunInfo = tichunInfo;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public UserBean getGroupOwnerInfo() {
        return groupOwnerInfo;
    }

    public void setGroupOwnerInfo(UserBean groupOwnerInfo) {
        this.groupOwnerInfo = groupOwnerInfo;
    }

    public static class ManagerUser implements Serializable{
        /**
         * phone : 17699999995
         * nickName : 蔡徐坤
         * headUrl : https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2774849680,1109054498&fm=26&gp=0.jpg
         * userId : 50
         */

        private String phone;
        private String nickName;
        private String headUrl;
        private long userId;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }

}
