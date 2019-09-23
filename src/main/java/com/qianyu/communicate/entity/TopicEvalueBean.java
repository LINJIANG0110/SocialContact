package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-9-6.
 */

public class TopicEvalueBean {
    //    "createTime": 1567592082000,
//            "nickName": "星海飞驰",
//            "headUrl": "http://qianyubk.oss-cn-shanghai.aliyuncs.com/14512479268199788.jpg",
//            "id": 1,
//            "userId": 95,
//            "content": "aaaaaa"
    public long createTime;
    public String nickName;
    public String headUrl;
    public String id;
    public long userId;
    public String content;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
