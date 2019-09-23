package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-4-26.
 */

public class FriendList {

    private List<ApplyListBean> applyList;
    private List<FriendListBean> friendList;

    public List<ApplyListBean> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<ApplyListBean> applyList) {
        this.applyList = applyList;
    }

    public List<FriendListBean> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<FriendListBean> friendList) {
        this.friendList = friendList;
    }

    public static class ApplyListBean {
        /**
         * phone : 17611038904
         * nickName : 古力娜扎
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=0c9f1e2f3cd0b0244fbe55fb4aefad87&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004151127_MkXA5.thumb.700_0.jpeg
         * userId : 30
         */

        private String phone;
        private String nickName;
        private String headUrl;
        private int userId;
        /**
         * level : 10
         * sex : 1
         */

        private int level;
        private int sex;
        private double expand;

        public double getExpand() {
            return expand;
        }

        public void setExpand(double expand) {
            this.expand = expand;
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

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }

    public static class FriendListBean {
        /**
         * phone : 17611038901
         * nickName : 颖宝宝
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=fc9959df94077f72096fd4325cadae5f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201406%2F29%2F20140629020323_YuzUt.jpeg
         * userId : 27
         */

        private String phone;
        private String nickName;
        private String headUrl;
        private int userId;
        /**
         * level : 10
         * sex : 1
         */

        private int level;
        private int sex;
        private double expand;

        public double getExpand() {
            return expand;
        }

        public void setExpand(double expand) {
            this.expand = expand;
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

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }
}
