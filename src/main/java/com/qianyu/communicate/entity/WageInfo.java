package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-7-9.
 */

public class WageInfo {

    /**
     * wages : {"gold":1803000,"addgold":3000,"fubao":1803000,"basicfubao":1800000,"addfubao":3000,"basicgold":1800000}
     * groupScoreStage : [{"configDetail":"{\"JINBI\":1800000,\"FUBAO\":1800000}","configId":10,"configName":"GROUP_WAGES_ONE","configValue":"500","createTime":1555725570000,"state":1,"updateTime":1555815446000},{"configDetail":"{\"JINBI\":1800000,\"FUBAO\":1800000}","configId":11,"configName":"GROUP_WAGES_TWO","configValue":"1500","createTime":1555725588000,"state":1,"updateTime":1555815447000},{"configDetail":"{\"JINBI\":1800000,\"FUBAO\":1800000}","configId":12,"configName":"GROUP_WAGES_THREE","configValue":"3000","createTime":1555725602000,"state":1,"updateTime":1555815449000}]
     * userWeekScore : 150
     * groupScore : 500
     * userDayScore : 0
     * wagelist : [{"id":1,"jifenNum":50,"rewardName":"x1800w金币,x600w福袋","rewards":"{\"JINBI\":1800000,\"FUBAO\":1800000}","userType":2,"userTypeName":"管理员"},{"id":2,"jifenNum":50,"rewardName":"x1800w金币,x600w福袋","rewards":"{\"JINBI\":1800000,\"FUBAO\":1800000}","userType":1,"userTypeName":"群主"}]
     * userName : 刘诗诗
     */

    private WagesBean wages;
    private long userWeekScore;
    private long groupScore;
    private long userDayScore;
    private String userName;
    private List<GroupScoreStageBean> groupScoreStage;
    private List<WagelistBean> wagelist;
    private List<UserScoreInfoBean> userScoreInfo;

    public WagesBean getWages() {
        return wages;
    }

    public void setWages(WagesBean wages) {
        this.wages = wages;
    }

    public long getUserWeekScore() {
        return userWeekScore;
    }

    public void setUserWeekScore(long userWeekScore) {
        this.userWeekScore = userWeekScore;
    }

    public long getGroupScore() {
        return groupScore;
    }

    public void setGroupScore(long groupScore) {
        this.groupScore = groupScore;
    }

    public long getUserDayScore() {
        return userDayScore;
    }

    public void setUserDayScore(long userDayScore) {
        this.userDayScore = userDayScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<GroupScoreStageBean> getGroupScoreStage() {
        return groupScoreStage;
    }

    public void setGroupScoreStage(List<GroupScoreStageBean> groupScoreStage) {
        this.groupScoreStage = groupScoreStage;
    }

    public List<WagelistBean> getWagelist() {
        return wagelist;
    }

    public void setWagelist(List<WagelistBean> wagelist) {
        this.wagelist = wagelist;
    }

    public List<UserScoreInfoBean> getUserScoreInfo() {
        return userScoreInfo;
    }

    public void setUserScoreInfo(List<UserScoreInfoBean> userScoreInfo) {
        this.userScoreInfo = userScoreInfo;
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

        private long gold;
        private long addgold;
        private long fubao;
        private long basicfubao;
        private long addfubao;
        private long basicgold;

        public long getGold() {
            return gold;
        }

        public void setGold(long gold) {
            this.gold = gold;
        }

        public long getAddgold() {
            return addgold;
        }

        public void setAddgold(long addgold) {
            this.addgold = addgold;
        }

        public long getFubao() {
            return fubao;
        }

        public void setFubao(long fubao) {
            this.fubao = fubao;
        }

        public long getBasicfubao() {
            return basicfubao;
        }

        public void setBasicfubao(long basicfubao) {
            this.basicfubao = basicfubao;
        }

        public long getAddfubao() {
            return addfubao;
        }

        public void setAddfubao(long addfubao) {
            this.addfubao = addfubao;
        }

        public long getBasicgold() {
            return basicgold;
        }

        public void setBasicgold(long basicgold) {
            this.basicgold = basicgold;
        }
    }

    public static class GroupScoreStageBean {
        /**
         * configDetail : {"JINBI":1800000,"FUBAO":1800000}
         * configId : 10
         * configName : GROUP_WAGES_ONE
         * configValue : 500
         * createTime : 1555725570000
         * state : 1
         * updateTime : 1555815446000
         */

        private String configDetail;
        private long configId;
        private String configName;
        private String configValue;
        private long createTime;
        private int state;
        private long updateTime;

        public String getConfigDetail() {
            return configDetail;
        }

        public void setConfigDetail(String configDetail) {
            this.configDetail = configDetail;
        }

        public long getConfigId() {
            return configId;
        }

        public void setConfigId(long configId) {
            this.configId = configId;
        }

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }

        public String getConfigValue() {
            return configValue;
        }

        public void setConfigValue(String configValue) {
            this.configValue = configValue;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
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
        private long jifenNum;
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

        public long getJifenNum() {
            return jifenNum;
        }

        public void setJifenNum(long jifenNum) {
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

    public static class UserScoreInfoBean {
        /**
         * week : 0
         * nickName : Ethan
         * day : 0
         */

        private long week;
        private String nickName;
        private long day;

        public long getWeek() {
            return week;
        }

        public void setWeek(long week) {
            this.week = week;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getDay() {
            return day;
        }

        public void setDay(long day) {
            this.day = day;
        }
    }
}
