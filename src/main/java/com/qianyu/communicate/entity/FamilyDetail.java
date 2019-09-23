package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-4-22.
 */

public class FamilyDetail {

    /**
     * wages : {"gold":1803000,"addgold":3000,"fubao":1803000,"basicfubao":1800000,"addfubao":3000,"basicgold":1800000}
     * members : [{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=93fa906c27e80289e248afd4ada6e6bc&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F0%2Fw1200h1200%2F20180519%2F9966-haturfs7204243.jpg","userId":26,"userType":1,"nickName":"刘诗诗"},{"userId":29,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg","userType":2,"nickName":"马尔扎哈"},{"userId":30,"userType":3,"nickName":"古力娜扎","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=0c9f1e2f3cd0b0244fbe55fb4aefad87&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004151127_MkXA5.thumb.700_0.jpeg"}]
     * applyList : [{},{},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390935&di=69d9cc665950d61b7137e4cecf10fed0&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201402%2F22%2F20140222105859_wJzTQ.thumb.700_0.jpeg","userId":32,"nickName":"独孤求败"}]
     * userType : 1
     * groupInfo : {"address":"北京","createTime":1555494223000,"details":"sd","fubaoNum":3000,"goldNum":3000,"goupScore":500,"groupId":13,"groupName":"123","headUrl":"http://img.zcool.cn/community/0158e25983c6c50000002129f1d823.jpg@1280w_1l_2o_100sh.png","sort":1,"state":1,"updateTime":1555822160000}
     * wagelist : [{"id":1,"jifenNum":50,"rewardName":"x1800w金币,x600w福袋","rewards":"{\"JINBI\":1800000,\"FUBAO\":1800000}","userType":2,"userTypeName":"管理员"},{"id":2,"jifenNum":50,"rewardName":"x1800w金币,x600w福袋","rewards":"{\"JINBI\":1800000,\"FUBAO\":1800000}","userType":1,"userTypeName":"群主"}]
     * groupBossState : {"isallowcall":0,"donum":0,"allownum":1}
     */

    private WagesBean wages;
    private int userType;
    private GroupInfoBean groupInfo;
    private List<MembersBean> members;
    private List<ApplyListBean> applyList;
    private List<WagelistBean> wagelist;
    private List<GroupBossStateBean> groupBossState;

    public WagesBean getWages() {
        return wages;
    }

    public void setWages(WagesBean wages) {
        this.wages = wages;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public GroupInfoBean getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfoBean groupInfo) {
        this.groupInfo = groupInfo;
    }

    public List<MembersBean> getMembers() {
        return members;
    }

    public void setMembers(List<MembersBean> members) {
        this.members = members;
    }

    public List<ApplyListBean> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<ApplyListBean> applyList) {
        this.applyList = applyList;
    }

    public List<WagelistBean> getWagelist() {
        return wagelist;
    }

    public void setWagelist(List<WagelistBean> wagelist) {
        this.wagelist = wagelist;
    }

    public List<GroupBossStateBean> getGroupBossState() {
        return groupBossState;
    }

    public void setGroupBossState(List<GroupBossStateBean> groupBossState) {
        this.groupBossState = groupBossState;
    }

    public static class WagesBean {
        /**
         * gold : 1803000
         * addgold : 3000
         * fubao : 1803000
         * basicfubao : 1800000
         * addfubao : 3000
         * basicgold : 1800000
         */

        private double gold;
        private double addgold;
        private double fubao;
        private double basicfubao;
        private double addfubao;
        private double basicgold;

        public double getGold() {
            return gold;
        }

        public void setGold(double gold) {
            this.gold = gold;
        }

        public double getAddgold() {
            return addgold;
        }

        public void setAddgold(double addgold) {
            this.addgold = addgold;
        }

        public double getFubao() {
            return fubao;
        }

        public void setFubao(double fubao) {
            this.fubao = fubao;
        }

        public double getBasicfubao() {
            return basicfubao;
        }

        public void setBasicfubao(double basicfubao) {
            this.basicfubao = basicfubao;
        }

        public double getAddfubao() {
            return addfubao;
        }

        public void setAddfubao(double addfubao) {
            this.addfubao = addfubao;
        }

        public double getBasicgold() {
            return basicgold;
        }

        public void setBasicgold(double basicgold) {
            this.basicgold = basicgold;
        }
    }

    public static class GroupInfoBean {
        /**
         * address : 北京
         * createTime : 1555494223000
         * details : sd
         * fubaoNum : 3000
         * goldNum : 3000
         * goupScore : 500
         * groupId : 13
         * groupName : 123
         * headUrl : http://img.zcool.cn/community/0158e25983c6c50000002129f1d823.jpg@1280w_1l_2o_100sh.png
         * sort : 1
         * state : 1
         * updateTime : 1555822160000
         */

        private String address;
        private long createTime;
        private String details;
        private String introduce;
        private double fubaoNum;
        private double goldNum;
        private double goupScore;
        private long groupId;
        private String groupName;
        private String headUrl;
        private int sort;
        private int state;
        private long updateTime;
        private int joinType;

        public int getJoinType() {
            return joinType;
        }

        public void setJoinType(int joinType) {
            this.joinType = joinType;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public double getFubaoNum() {
            return fubaoNum;
        }

        public void setFubaoNum(double fubaoNum) {
            this.fubaoNum = fubaoNum;
        }

        public double getGoldNum() {
            return goldNum;
        }

        public void setGoldNum(double goldNum) {
            this.goldNum = goldNum;
        }

        public double getGoupScore() {
            return goupScore;
        }

        public void setGoupScore(double goupScore) {
            this.goupScore = goupScore;
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

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class MembersBean {
        /**
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=93fa906c27e80289e248afd4ada6e6bc&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F0%2Fw1200h1200%2F20180519%2F9966-haturfs7204243.jpg
         * userId : 26
         * userType : 1
         * nickName : 刘诗诗
         */

        private String headUrl;
        private long userId;
        private int userType;
        private String nickName;
        private long lastLoginTime;
        private boolean isSelected=false;

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

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public static class ApplyListBean {
        /**
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390935&di=69d9cc665950d61b7137e4cecf10fed0&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201402%2F22%2F20140222105859_wJzTQ.thumb.700_0.jpeg
         * userId : 32
         * nickName : 独孤求败
         */

        private String headUrl;
        private long userId;
        private String nickName;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }

    public static class WagelistBean {
        /**
         * id : 1
         * jifenNum : 50
         * rewardName : x1800w金币,x600w福袋
         * rewards : {"JINBI":1800000,"FUBAO":1800000}
         * userType : 2
         * userTypeName : 管理员
         */

        private long id;
        private double jifenNum;
        private String rewardName;
        private String rewards;
        private int userType;
        private String userTypeName;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public double getJifenNum() {
            return jifenNum;
        }

        public void setJifenNum(double jifenNum) {
            this.jifenNum = jifenNum;
        }

        public String getRewardName() {
            return rewardName;
        }

        public void setRewardName(String rewardName) {
            this.rewardName = rewardName;
        }

        public String getRewards() {
            return rewards;
        }

        public void setRewards(String rewards) {
            this.rewards = rewards;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUserTypeName() {
            return userTypeName;
        }

        public void setUserTypeName(String userTypeName) {
            this.userTypeName = userTypeName;
        }
    }

    public static class GroupBossStateBean {
        /**
         * isallowcall : 0
         * bossUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg
         * bossName : 召唤小妖
         * donum : 0
         * allownum : 1
         */

        private int isallowcall;
        private String bossUrl;
        private String bossName;
        private int donum;
        private int allownum;

        public int getIsallowcall() {
            return isallowcall;
        }

        public void setIsallowcall(int isallowcall) {
            this.isallowcall = isallowcall;
        }

        public String getBossUrl() {
            return bossUrl;
        }

        public void setBossUrl(String bossUrl) {
            this.bossUrl = bossUrl;
        }

        public String getBossName() {
            return bossName;
        }

        public void setBossName(String bossName) {
            this.bossName = bossName;
        }

        public int getDonum() {
            return donum;
        }

        public void setDonum(int donum) {
            this.donum = donum;
        }

        public int getAllownum() {
            return allownum;
        }

        public void setAllownum(int allownum) {
            this.allownum = allownum;
        }
    }
}
