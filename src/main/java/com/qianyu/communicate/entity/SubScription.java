package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2018-2-7.
 */

public class SubScription {

    /**
     * subscriptions : [{"createTime":1517805231000,"familyName":"阿卡丽","familyPath":"http://word-file.oss-cn-beijing.aliyuncs.com/eeb4a50fd2e5c95ca1a49b264d24f445.png","fid":91,"freeType":"1","headPath":"http://word-file.oss-cn-beijing.aliyuncs.com/727bd3d58c16cd665d7fb9ad9f0e75b8.png","jobTitle":"黎吧啦","liveType":"1","nickName":"冻结了","price":0,"userId":83}]
     * totalSub : 1
     */

    private int totalSub;
    private List<SubscriptionsBean> subscriptions;

    public int getTotalSub() {
        return totalSub;
    }

    public void setTotalSub(int totalSub) {
        this.totalSub = totalSub;
    }

    public List<SubscriptionsBean> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionsBean> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public static class SubscriptionsBean {
        /**
         * createTime : 1517805231000
         * familyName : 阿卡丽
         * familyPath : http://word-file.oss-cn-beijing.aliyuncs.com/eeb4a50fd2e5c95ca1a49b264d24f445.png
         * fid : 91
         * freeType : 1
         * headPath : http://word-file.oss-cn-beijing.aliyuncs.com/727bd3d58c16cd665d7fb9ad9f0e75b8.png
         * jobTitle : 黎吧啦
         * liveType : 1
         * nickName : 冻结了
         * price : 0
         * userId : 83
         */

        private long createTime;
        private String familyName;
        private String familyPath;
        private int fid;
        private int sid;
        private String freeType;
        private String headPath;
        private String jobTitle;
        private String liveType;
        private String nickName;
        private double price;
        private int userId;
        private String bukaCourseId;

        public String getBukaCourseId() {
            return bukaCourseId;
        }

        public void setBukaCourseId(String bukaCourseId) {
            this.bukaCourseId = bukaCourseId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public String getFamilyPath() {
            return familyPath;
        }

        public void setFamilyPath(String familyPath) {
            this.familyPath = familyPath;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getFreeType() {
            return freeType;
        }

        public void setFreeType(String freeType) {
            this.freeType = freeType;
        }

        public String getHeadPath() {
            return headPath;
        }

        public void setHeadPath(String headPath) {
            this.headPath = headPath;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getLiveType() {
            return liveType;
        }

        public void setLiveType(String liveType) {
            this.liveType = liveType;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }
    }
}
