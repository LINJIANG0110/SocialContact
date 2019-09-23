package com.qianyu.communicate.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class MediaList implements Serializable {

    /**
     * createTime : 1513762170000
     * freeType : 0
     * mediaId : 1
     * mediaName : 李雷小视频
     * mediaPath : http://www.iqiyi.com/pianhua/20120608/0a86533182b4ed99.html?vfrm=23_2_8_1
     * mediaType : 1
     * totalPerson : 0
     * totalTime : -25648000
     * user : {"userId":48}
     */

    private long createTime;
    private String freeType;
    private int mediaId;
    private String mediaName;
    private String mediaPath;
    private String mediaPic;
    private int mediaType;
    private int totalPerson;
    private long totalTime;
    private UserBean user;
    private long payTime;

    public String getMediaPic() {
        return mediaPic;
    }

    public void setMediaPic(String mediaPic) {
        this.mediaPic = mediaPic;
    }

    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getFreeType() {
        return freeType;
    }

    public void setFreeType(String freeType) {
        this.freeType = freeType;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public int getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(int totalPerson) {
        this.totalPerson = totalPerson;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Serializable {
        /**
         * userId : 48
         */

        private int userId;
        private String headPath;
        private String nickName;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getHeadPath() {
            return headPath;
        }

        public void setHeadPath(String headPath) {
            this.headPath = headPath;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }
}
