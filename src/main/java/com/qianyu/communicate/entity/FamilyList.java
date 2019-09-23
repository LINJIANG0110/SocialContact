package com.qianyu.communicate.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class FamilyList implements Serializable {

    /**
     * size : 20
     * totalPages : 1
     * content : [{"createTime":1553571634000,"details":"个性签名","groupId":9,"groupName":"群聊1","headUrl":"www.baidu.com","joinType":1,"qrcodeUrl":"www.hao123.com","sort":1,"state":1,"updateTime":1553581841000}]
     * totalElements : 1
     */

    private int size;
    private int totalPages;
    private int totalElements;
    private List<ContentBean> content;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable {
        /**
         * createTime : 1553571634000
         * details : 个性签名
         * groupId : 9
         * groupName : 群聊1
         * headUrl : www.baidu.com
         * joinType : 1
         * qrcodeUrl : www.hao123.com
         * sort : 1
         * state : 1
         * updateTime : 1553581841000
         */
        private String address;
        private double goldNum;
        private double goupScore;
        private double fubaoNum;
        private long createTime;
        private String details;
        private long groupId;
        private String groupName;
        private String headUrl;
        private int joinType;
        private String qrcodeUrl;
        private int sort;
        private int state;
        private long updateTime;
        private long score;
        private String content;

        public long getScore() {
            return score;
        }

        public void setScore(long score) {
            this.score = score;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getGoldNum() {
            return goldNum;
        }

        public void setGoldNum(double goldNum) {
            this.goldNum = goldNum;
        }

        public double getGoupScore() {
            return goupScore;
        }

        public void setGoupScore(double goupScore) {
            this.goupScore = goupScore;
        }

        public double getFubaoNum() {
            return fubaoNum;
        }

        public void setFubaoNum(double fubaoNum) {
            this.fubaoNum = fubaoNum;
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

        public long getGroupId() {
            return groupId;
        }

        public void setGroupId(long groupId) {
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

        public int getJoinType() {
            return joinType;
        }

        public void setJoinType(int joinType) {
            this.joinType = joinType;
        }

        public String getQrcodeUrl() {
            return qrcodeUrl;
        }

        public void setQrcodeUrl(String qrcodeUrl) {
            this.qrcodeUrl = qrcodeUrl;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
