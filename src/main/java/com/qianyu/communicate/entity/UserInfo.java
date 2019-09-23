package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-5-5.
 */

public class UserInfo {

    /**
     * userInfo : {"level":5,"nickName":"AngleBaby","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","groupheadUrl":"http://img.zcool.cn/community/0158e25983c6c50000002129f1d823.jpg@1280w_1l_2o_100sh.png","label":"电影,音乐,游戏,赛马","userId":23,"groupName":"123","charmNum":0,"phone":"17611038965","details":"各种嗨~起来嗨~","richNum":0,"age":27}
     * userCircle : {"fileItemUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554269996515&di=b39086c346403f96aef3a7349f097c26&imgtype=0&src=http%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F20166_28_9%2Fa4q94n6305744425352.jpg","title":"123"}
     * skillMap : [{"name":"六脉神剑","skillType":"KILLFACE","level":2,"skillPicPath":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg"},{"skillPicPath":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","skillType":"KISS","name":"爱情神剑","level":26},{"name":"葵花点穴","skillPicPath":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","level":14,"skillType":"BAN"},{"skillType":"KICK","name":"龟派气功","level":34,"skillPicPath":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg"},{"skillType":"STEAL","level":2,"name":"吸金大法","skillPicPath":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg"},{"skillType":"CONFUSION","level":2,"name":"走火入魔","skillPicPath":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg"}]
     */

    private UserInfoBean userInfo;
    private UserCircleBean userCircle;
    private List<SkillMapBean> skillMap;
    private List<UserInfoBean> fanList;
    private List<GiftListBean> giftList;
    private int friendStatus;
    private String userExaminePic;// 照片墙

    public String getUserExaminePic() {
        return userExaminePic;
    }

    public void setUserExaminePic(String userExaminePic) {
        this.userExaminePic = userExaminePic;
    }

    public int getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public UserCircleBean getUserCircle() {
        return userCircle;
    }

    public void setUserCircle(UserCircleBean userCircle) {
        this.userCircle = userCircle;
    }

    public List<SkillMapBean> getSkillMap() {
        return skillMap;
    }

    public void setSkillMap(List<SkillMapBean> skillMap) {
        this.skillMap = skillMap;
    }

    public List<UserInfoBean> getFanList() {
        return fanList;
    }

    public void setFanList(List<UserInfoBean> fanList) {
        this.fanList = fanList;
    }

    public List<GiftListBean> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<GiftListBean> giftList) {
        this.giftList = giftList;
    }

    public static class UserInfoBean {
        /**
         * level : 5
         * nickName : AngleBaby
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg
         * groupheadUrl : http://img.zcool.cn/community/0158e25983c6c50000002129f1d823.jpg@1280w_1l_2o_100sh.png
         * label : 电影,音乐,游戏,赛马
         * userId : 23
         * groupName : 123
         * charmNum : 0
         * phone : 17611038965
         * details : 各种嗨~起来嗨~
         * richNum : 0
         * age : 27
         */

        private int level;
        private String nickName;
        private String headUrl;
        private String groupheadUrl;
        private String label;
        private long userId;
        private long groupId;
        private String groupName;
        private long charmNum;
        private String phone;
        private String details;
        private String currentAddress;
        private long richNum;
        private int age;
        private long lastLoginTime;
        private double expand;

        public double getExpand() {
            return expand;
        }

        public void setExpand(double expand) {
            this.expand = expand;
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

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getGroupheadUrl() {
            return groupheadUrl;
        }

        public void setGroupheadUrl(String groupheadUrl) {
            this.groupheadUrl = groupheadUrl;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
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

        public long getCharmNum() {
            return charmNum;
        }

        public void setCharmNum(long charmNum) {
            this.charmNum = charmNum;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getCurrentAddress() {
            return currentAddress;
        }

        public void setCurrentAddress(String currentAddress) {
            this.currentAddress = currentAddress;
        }

        public long getRichNum() {
            return richNum;
        }

        public void setRichNum(long richNum) {
            this.richNum = richNum;
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

    public static class UserCircleBean {
        /**
         * fileItemUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554269996515&di=b39086c346403f96aef3a7349f097c26&imgtype=0&src=http%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F20166_28_9%2Fa4q94n6305744425352.jpg
         * title : 123
         */

        private String fileItemUrl;
        private String title;

        public String getFileItemUrl() {
            return fileItemUrl;
        }

        public void setFileItemUrl(String fileItemUrl) {
            this.fileItemUrl = fileItemUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class SkillMapBean {
        /**
         * name : 六脉神剑
         * skillType : KILLFACE
         * level : 2
         * skillPicPath : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg
         */

        private String name;
        private String skillType;
        private int level;
        private String skillPicPath;
        private String staticPath;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSkillType() {
            return skillType;
        }

        public void setSkillType(String skillType) {
            this.skillType = skillType;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getSkillPicPath() {
            return skillPicPath;
        }

        public void setSkillPicPath(String skillPicPath) {
            this.skillPicPath = skillPicPath;
        }

        public String getStaticPath() {
            return staticPath;
        }

        public void setStaticPath(String staticPath) {
            this.staticPath = staticPath;
        }
    }

    public static class GiftListBean {
        /**
         * gift_url : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554960942802&di=3098b0a65d14700a32bfb02882dce5c7&imgtype=0&src=http%3A%2F%2Fimg9.3lian.com%2Fc1%2Fvector%2F4%2F08%2F006.jpg
         * gift_id : 3
         */

        private String gift_url;
        private long gift_id;

        public String getGift_url() {
            return gift_url;
        }

        public void setGift_url(String gift_url) {
            this.gift_url = gift_url;
        }

        public long getGift_id() {
            return gift_id;
        }

        public void setGift_id(long gift_id) {
            this.gift_id = gift_id;
        }
    }
}
