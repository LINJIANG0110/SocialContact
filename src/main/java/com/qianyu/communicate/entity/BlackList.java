package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class BlackList {

    /**
     * size : 20
     * totalPages : 1
     * content : [{"phone":"13683557635","level":10,"nickName":"哈哈","sex":1,"headUrl":"http://qianyubk.oss-cn-shanghai.aliyuncs.com/5956393998880718.jpg","userId":82}]
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
         * phone : 13683557635
         * level : 10
         * nickName : 哈哈
         * sex : 1
         * headUrl : http://qianyubk.oss-cn-shanghai.aliyuncs.com/5956393998880718.jpg
         * userId : 82
         */

        private String phone;
        private int level;
        private String nickName;
        private int sex;
        private String headUrl;
        private long userId;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
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
    }
}
