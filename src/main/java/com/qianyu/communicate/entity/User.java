package com.qianyu.communicate.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者 ： dyj
 * 时间 ： 2016/3/29.
 */

public class User implements Serializable {

    private String token;
    private long userId;
    private String userNum;  //用户帐号
    private String nickName; //昵称
    private int sex;   //性别
    private int age; //生日
    private String address;  //地址
    private String headUrl;  //头像存储地址
    private String jobTitle;  //职称
    private String phone;
    private double charm;
    private long createTime;
    private double diamond;
    private double experience;
    private double gold;
    private String huanxinDetail;
    private String huanxinId;
    private String jiguangId;
    private int level;
    private double plute;
    private String role;
    private double stars;
    private int state;
    private long updateTime;
    private double yuanbao;
    private List<UserOtherListBean> userOtherList;
    private int isBind;
    private double lastLoginLatitude;
    private double lastLoginLongitude;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getCharm() {
        return charm;
    }

    public void setCharm(double charm) {
        this.charm = charm;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public double getDiamond() {
        return diamond;
    }

    public void setDiamond(double diamond) {
        this.diamond = diamond;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public String getHuanxinDetail() {
        return huanxinDetail;
    }

    public void setHuanxinDetail(String huanxinDetail) {
        this.huanxinDetail = huanxinDetail;
    }

    public String getHuanxinId() {
        return huanxinId;
    }

    public void setHuanxinId(String huanxinId) {
        this.huanxinId = huanxinId;
    }

    public String getJiguangId() {
        return jiguangId;
    }

    public void setJiguangId(String jiguangId) {
        this.jiguangId = jiguangId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPlute() {
        return plute;
    }

    public void setPlute(double plute) {
        this.plute = plute;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
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

    public double getYuanbao() {
        return yuanbao;
    }

    public void setYuanbao(double yuanbao) {
        this.yuanbao = yuanbao;
    }

    public List<UserOtherListBean> getUserOtherList() {
        return userOtherList;
    }

    public void setUserOtherList(List<UserOtherListBean> userOtherList) {
        this.userOtherList = userOtherList;
    }

    public int getIsBind() {
        return isBind;
    }

    public void setIsBind(int isBind) {
        this.isBind = isBind;
    }

    public double getLastLoginLatitude() {
        return lastLoginLatitude;
    }

    public void setLastLoginLatitude(double lastLoginLatitude) {
        this.lastLoginLatitude = lastLoginLatitude;
    }

    public double getLastLoginLongitude() {
        return lastLoginLongitude;
    }

    public void setLastLoginLongitude(double lastLoginLongitude) {
        this.lastLoginLongitude = lastLoginLongitude;
    }

    public static class UserOtherListBean {
        /**
         * createTime : 1552636376000
         * otherId : 60
         * otherToken : tyttttttttthh1235551
         * otherType : 2
         * state : 1
         * updateTime : 1552636407000
         * userId : 52
         */

        private long createTime;
        private long otherId;
        private String otherToken;
        private int otherType;
        private int state;
        private long updateTime;
        @SerializedName("userId")
        private long userIdX;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getOtherId() {
            return otherId;
        }

        public void setOtherId(long otherId) {
            this.otherId = otherId;
        }

        public String getOtherToken() {
            return otherToken;
        }

        public void setOtherToken(String otherToken) {
            this.otherToken = otherToken;
        }

        public int getOtherType() {
            return otherType;
        }

        public void setOtherType(int otherType) {
            this.otherType = otherType;
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

        public long getUserIdX() {
            return userIdX;
        }

        public void setUserIdX(long userIdX) {
            this.userIdX = userIdX;
        }
    }
}
