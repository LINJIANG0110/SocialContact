package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class CommentList {

    /**
     * size : 20
     * totalPages : 1
     * content : [{"createTime":1557123958000,"nickName":"迪丽热巴","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","text":"dafhdsa","userId":28}]
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

    public static class ContentBean {
        /**
         * createTime : 1557123958000
         * nickName : 迪丽热巴
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg
         * text : dafhdsa
         * userId : 28
         */

        private long createTime;
        private String nickName;
        private String headUrl;
        private String text;
        private long userId;
        private int commentId;

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

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

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }
}
