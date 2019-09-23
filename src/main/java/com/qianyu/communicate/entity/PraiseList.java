package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class PraiseList {

    /**
     * size : 20
     * totalPages : 1
     * content : [{"nickName":"马尔扎哈","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg","userId":29}]
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

    public static class ContentBean {
        /**
         * nickName : 马尔扎哈
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg
         * userId : 29
         */

        private String nickName;
        private String headUrl;
        private long userId;

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

        @Override
        public String toString() {
            return "ContentBean{" +
                    "nickName='" + nickName + '\'' +
                    ", headUrl='" + headUrl + '\'' +
                    ", userId=" + userId +
                    '}';
        }
    }
}
