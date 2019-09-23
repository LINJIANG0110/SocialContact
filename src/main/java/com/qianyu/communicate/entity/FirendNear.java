package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-4-2.
 */

public class FirendNear {

    /**
     * size : 10
     * totalPages : 1
     * content : [{"role":"1","lastLoginLatitude":39.977773,"distance":3247,"lastLoginLongitude":116.420079,"experience":0,"gold":10000,"huanxinDetail":"{\"duration\":29,\"path\":\"/users\",\"application\":\"58b0d870-40af-11e9-9e8e-dff4478d9617\",\"entities\":[{\"created\":1552380431074,\"modified\":1552380431074,\"type\":\"user\",\"uuid\":\"6d025c2a-44a3-11e9-8db6-33f5a3527d7e\",\"username\":\"17611038903\",\"activated\":true}],\"organization\":\"1121190307181214\",\"action\":\"post\",\"uri\":\"https://a1.easemob.com/1121190307181214/qianyu/users\",\"applicationName\":\"qianyu\",\"timestamp\":1552380431074}","state":1,"yuanbao":10000,"level":1,"sex":0,"updateTime":1553679761000,"huanxinId":"6d025c2a-44a3-11e9-8db6-33f5a3527d7e","stars":10000,"userId":29,"diamond":10000,"charm":0,"createTime":1552380431000,"phone":"17611038903","plute":0}]
     * totalElements : 10
     */

    private int size;
    private int totalPages;
    private List<ContentBean> content;
    private int totalElements;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public static class ContentBean {
        /**
         * role : 1
         * lastLoginLatitude : 39.977773
         * distance : 3247
         * lastLoginLongitude : 116.420079
         * experience : 0
         * gold : 10000
         * huanxinDetail : {"duration":29,"path":"/users","application":"58b0d870-40af-11e9-9e8e-dff4478d9617","entities":[{"created":1552380431074,"modified":1552380431074,"type":"user","uuid":"6d025c2a-44a3-11e9-8db6-33f5a3527d7e","username":"17611038903","activated":true}],"organization":"1121190307181214","action":"post","uri":"https://a1.easemob.com/1121190307181214/qianyu/users","applicationName":"qianyu","timestamp":1552380431074}
         * state : 1
         * yuanbao : 10000
         * level : 1
         * sex : 0
         * updateTime : 1553679761000
         * huanxinId : 6d025c2a-44a3-11e9-8db6-33f5a3527d7e
         * stars : 10000
         * userId : 29
         * diamond : 10000
         * charm : 0
         * createTime : 1552380431000
         * phone : 17611038903
         * plute : 0
         */

        private String role;
        private double lastLoginLatitude;
        private int distance;
        private double lastLoginLongitude;
        private int experience;
        private int gold;
        private String huanxinDetail;
        private int state;
        private int yuanbao;
        private int level;
        private int sex;
        private long updateTime;
        private String huanxinId;
        private int stars;
        private long userId;
        private int diamond;
        private int charm;
        private long createTime;
        private String phone;
        private int plute;
        private String headUrl;
        private String nickName;
        private String details;
        private int age;
        private long lastLoginTime;
        private double expand;

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public double getExpand() {
            return expand;
        }

        public void setExpand(double expand) {
            this.expand = expand;
        }

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public double getLastLoginLatitude() {
            return lastLoginLatitude;
        }

        public void setLastLoginLatitude(double lastLoginLatitude) {
            this.lastLoginLatitude = lastLoginLatitude;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public double getLastLoginLongitude() {
            return lastLoginLongitude;
        }

        public void setLastLoginLongitude(double lastLoginLongitude) {
            this.lastLoginLongitude = lastLoginLongitude;
        }

        public int getExperience() {
            return experience;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public String getHuanxinDetail() {
            return huanxinDetail;
        }

        public void setHuanxinDetail(String huanxinDetail) {
            this.huanxinDetail = huanxinDetail;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getYuanbao() {
            return yuanbao;
        }

        public void setYuanbao(int yuanbao) {
            this.yuanbao = yuanbao;
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

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getHuanxinId() {
            return huanxinId;
        }

        public void setHuanxinId(String huanxinId) {
            this.huanxinId = huanxinId;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getDiamond() {
            return diamond;
        }

        public void setDiamond(int diamond) {
            this.diamond = diamond;
        }

        public int getCharm() {
            return charm;
        }

        public void setCharm(int charm) {
            this.charm = charm;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPlute() {
            return plute;
        }

        public void setPlute(int plute) {
            this.plute = plute;
        }
    }
}
