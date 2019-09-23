package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-9-9.
 */

public class AnswerDelBean {

    public List<TopicEvalueBean> commentList;// 评论列表
    public String countNum;
    public int fabulousFlag;
    public AnswerChildBean topicComment;
    public MainUser createUserInfo;

    public class MainUser {
        public String nickName;
        public String headUrl;
    }

    // 帖子详情
    public class AnswerChildBean {
        public String commentId;
        public String commentNum;
        public String commentTitle;
        public String content;
        public long createTime;
        public String fabulousNum;
        public String hotNum;
        public String state;
        public String topicId;
        public String userId;

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getFabulousNum() {
            return fabulousNum;
        }

        public void setFabulousNum(String fabulousNum) {
            this.fabulousNum = fabulousNum;
        }
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }

    public int getFabulousFlag() {
        return fabulousFlag;
    }

    public void setFabulousFlag(int fabulousFlag) {
        this.fabulousFlag = fabulousFlag;
    }
}
