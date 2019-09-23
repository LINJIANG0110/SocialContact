package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-4-26.
 */

public class SearchFriend {

    /**
     * size : 20
     * totalPages : 1
     * content : [{"lastLoginLatitude":39.971138,"level":5,"phone":"17611038965","nickName":"AngleBaby","lastLoginLongitude":116.178614,"sex":2,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","details":"各种嗨~起来嗨~","userId":23,"age":27}]
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
         * lastLoginLatitude : 39.971138
         * level : 5
         * phone : 17611038965
         * nickName : AngleBaby
         * lastLoginLongitude : 116.178614
         * sex : 2
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg
         * details : 各种嗨~起来嗨~
         * userId : 23
         * age : 27
         */

        private double lastLoginLatitude;
        private int level;
        private String phone;
        private String nickName;
        private double lastLoginLongitude;
        private int sex;
        private String headUrl;
        private String details;
        private long userId;
        private int age;
        private long lastLoginTime;

        public double getLastLoginLatitude() {
            return lastLoginLatitude;
        }

        public void setLastLoginLatitude(double lastLoginLatitude) {
            this.lastLoginLatitude = lastLoginLatitude;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public double getLastLoginLongitude() {
            return lastLoginLongitude;
        }

        public void setLastLoginLongitude(double lastLoginLongitude) {
            this.lastLoginLongitude = lastLoginLongitude;
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

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }
    }
}
