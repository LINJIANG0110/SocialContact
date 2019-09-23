package com.qianyu.communicate.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JavaDog on 2019-9-5.
 */

public class TopicBean implements Serializable {

    public TopicUnreadBean unreadMap;
    public List<TopicDataBean> topicList;

    public class TopicUnreadBean {
        public String headUrl;
        public int count;
    }

    public class TopicDataBean implements Serializable{
        private String hotNum;
        private String picPath;
        public String crateTime;
        private String state;
        private String title;
        private String topicId;

        public String getCrateTime() {
            return crateTime;
        }

        public void setCrateTime(String crateTime) {
            this.crateTime = crateTime;
        }

        public String getHotNum() {
            return hotNum;
        }

        public void setHotNum(String hotNum) {
            this.hotNum = hotNum;
        }

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }
    }
//    private int comment;
//    private int sex;
//    private String text;
//    private long createTime;
//    private int groupId;
//    private String groupName;
//    private String fileItemUrl;
//    private String title;
//    private long userId;
//    private String headUrl;
//    private String nickName;
//    private int circleId;
//    private int fabulous;
//    private int age;
//    private int level;
//    private String type;
//    private int isClick;
//
//    public int getComment() {
//        return comment;
//    }
//
//    public void setComment(int comment) {
//        this.comment = comment;
//    }
//
//    public int getSex() {
//        return sex;
//    }
//
//    public void setSex(int sex) {
//        this.sex = sex;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public long getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(long createTime) {
//        this.createTime = createTime;
//    }
//
//    public int getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(int groupId) {
//        this.groupId = groupId;
//    }
//
//    public String getGroupName() {
//        return groupName;
//    }
//
//    public void setGroupName(String groupName) {
//        this.groupName = groupName;
//    }
//
//    public String getFileItemUrl() {
//        return fileItemUrl;
//    }
//
//    public void setFileItemUrl(String fileItemUrl) {
//        this.fileItemUrl = fileItemUrl;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }
//
//    public String getHeadUrl() {
//        return headUrl;
//    }
//
//    public void setHeadUrl(String headUrl) {
//        this.headUrl = headUrl;
//    }
//
//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        this.nickName = nickName;
//    }
//
//    public int getCircleId() {
//        return circleId;
//    }
//
//    public void setCircleId(int circleId) {
//        this.circleId = circleId;
//    }
//
//    public int getFabulous() {
//        return fabulous;
//    }
//
//    public void setFabulous(int fabulous) {
//        this.fabulous = fabulous;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public int getLevel() {
//        return level;
//    }
//
//    public void setLevel(int level) {
//        this.level = level;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public int getIsClick() {
//        return isClick;
//    }
//
//    public void setIsClick(int isClick) {
//        this.isClick = isClick;
//    }
}
