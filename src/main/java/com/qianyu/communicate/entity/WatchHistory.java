package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-4-23.
 */

public class WatchHistory {

    private List<HistoryBean> historylist;
    private List<HistoryBean> todaylist;

    public List<HistoryBean> getHistorylist() {
        return historylist;
    }

    public void setHistorylist(List<HistoryBean> historylist) {
        this.historylist = historylist;
    }

    public List<HistoryBean> getTodaylist() {
        return todaylist;
    }

    public void setTodaylist(List<HistoryBean> todaylist) {
        this.todaylist = todaylist;
    }

    public static class HistoryBean {
        /**
         * address : 北京
         * createTime : 1554695397000
         * details : 请问请问水电费第三方第三个大范甘迪发群翁群无
         * fubaoNum : 0
         * goldNum : 0
         * goupScore : 0
         * groupId : 1
         * groupName : 胜多负少
         * headUrl : http://s1.sinaimg.cn/mw690/006J529gzy7dh3YeV4490&690
         * sort : 1
         * state : 1
         * updateTime : 1555900137000
         */

        private String address;
        private long createTime;
        private String details;
        private double fubaoNum;
        private double goldNum;
        private int goupScore;
        private int groupId;
        private String groupName;
        private String headUrl;
        private int sort;
        private int state;
        private long updateTime;
        private boolean isSelected=false;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public double getFubaoNum() {
            return fubaoNum;
        }

        public void setFubaoNum(double fubaoNum) {
            this.fubaoNum = fubaoNum;
        }

        public double getGoldNum() {
            return goldNum;
        }

        public void setGoldNum(double goldNum) {
            this.goldNum = goldNum;
        }

        public int getGoupScore() {
            return goupScore;
        }

        public void setGoupScore(int goupScore) {
            this.goupScore = goupScore;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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
    }

}
