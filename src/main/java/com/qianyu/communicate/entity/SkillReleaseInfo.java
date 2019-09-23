package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-6-11.
 */

public class SkillReleaseInfo {

    /**
     * consumePop : {"JINBI":5000,"FUBAO":50,"DIAMOND":20,"SKILLNUMBER":5}
     * toUserSkillInfo : {"skillDescribe":"使对方魅力-0.15%(额外-20)，最高-1569","level":2,"rate":"90"}
     * userSkillInfo : {"skillDescribe":"使对方魅力-0.15%(额外-20)，最高-1569","level":2,"rate":"90"}
     */

    private ConsumePopBean consumePop;
    private ConsumePopBean holdPop;
    private ToUserSkillInfoBean toUserSkillInfo;
    private UserSkillInfoBean userSkillInfo;

    public ConsumePopBean getConsumePop() {
        return consumePop;
    }

    public void setConsumePop(ConsumePopBean consumePop) {
        this.consumePop = consumePop;
    }

    public ConsumePopBean getHoldPop() {
        return holdPop;
    }

    public void setHoldPop(ConsumePopBean holdPop) {
        this.holdPop = holdPop;
    }

    public ToUserSkillInfoBean getToUserSkillInfo() {
        return toUserSkillInfo;
    }

    public void setToUserSkillInfo(ToUserSkillInfoBean toUserSkillInfo) {
        this.toUserSkillInfo = toUserSkillInfo;
    }

    public UserSkillInfoBean getUserSkillInfo() {
        return userSkillInfo;
    }

    public void setUserSkillInfo(UserSkillInfoBean userSkillInfo) {
        this.userSkillInfo = userSkillInfo;
    }

    public static class ConsumePopBean {
        /**
         * JINBI : 5000
         * FUBAO : 50
         * DIAMOND : 20
         * SKILLNUMBER : 5
         */

        private long JINBI;
        private long FUBAO;
        private long DIAMOND;
        private long SKILLNUMBER;

        public long getJINBI() {
            return JINBI;
        }

        public void setJINBI(long JINBI) {
            this.JINBI = JINBI;
        }

        public long getFUBAO() {
            return FUBAO;
        }

        public void setFUBAO(long FUBAO) {
            this.FUBAO = FUBAO;
        }

        public long getDIAMOND() {
            return DIAMOND;
        }

        public void setDIAMOND(long DIAMOND) {
            this.DIAMOND = DIAMOND;
        }

        public long getSKILLNUMBER() {
            return SKILLNUMBER;
        }

        public void setSKILLNUMBER(long SKILLNUMBER) {
            this.SKILLNUMBER = SKILLNUMBER;
        }
    }

    public static class ToUserSkillInfoBean {
        /**
         * skillDescribe : 使对方魅力-0.15%(额外-20)，最高-1569
         * level : 2
         * rate : 90
         */

        private String skillDescribe;
        private int level;
        private String rate;

        public String getSkillDescribe() {
            return skillDescribe;
        }

        public void setSkillDescribe(String skillDescribe) {
            this.skillDescribe = skillDescribe;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }

    public static class UserSkillInfoBean {
        /**
         * skillDescribe : 使对方魅力-0.15%(额外-20)，最高-1569
         * level : 2
         * rate : 90
         */

        private String skillDescribe;
        private int level;
        private String rate;

        public String getSkillDescribe() {
            return skillDescribe;
        }

        public void setSkillDescribe(String skillDescribe) {
            this.skillDescribe = skillDescribe;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }
}
