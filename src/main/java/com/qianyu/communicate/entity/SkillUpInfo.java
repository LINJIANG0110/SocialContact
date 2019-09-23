package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-6-11.
 */

public class SkillUpInfo {

    /**
     * gold : 999350900
     * suns : 48129
     * diamond : 57952
     * fubao : 99450
     * moons : 10000
     * skillMap : {"skillType":"KILLFACE","skillDescribe":"使对方魅力-0.15%(额外-20)，最高-1569","baseClientRate":10000,"needExp":1000,"skillEnergy":0,"level":2,"diamondNum":20,"fubaoNum":20,"sunNum":1,"moonNum":1,"jinbiNum":5000}
     */

    private long gold;
    private long suns;
    private long diamond;
    private long fubao;
    private long moons;
    private SkillMapBean skillMap;

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getSuns() {
        return suns;
    }

    public void setSuns(long suns) {
        this.suns = suns;
    }

    public long getDiamond() {
        return diamond;
    }

    public void setDiamond(long diamond) {
        this.diamond = diamond;
    }

    public long getFubao() {
        return fubao;
    }

    public void setFubao(long fubao) {
        this.fubao = fubao;
    }

    public long getMoons() {
        return moons;
    }

    public void setMoons(long moons) {
        this.moons = moons;
    }

    public SkillMapBean getSkillMap() {
        return skillMap;
    }

    public void setSkillMap(SkillMapBean skillMap) {
        this.skillMap = skillMap;
    }

    public static class SkillMapBean {
        /**
         * skillType : KILLFACE
         * skillDescribe : 使对方魅力-0.15%(额外-20)，最高-1569
         * baseClientRate : 10000
         * needExp : 1000
         * skillEnergy : 0
         * level : 2
         * diamondNum : 20
         * fubaoNum : 20
         * sunNum : 1
         * moonNum : 1
         * jinbiNum : 5000
         */

        private String skillType;
        private String skillDescribe;
        private int baseClientRate;
        private int needExp;
        private int skillEnergy;
        private int level;
        private long diamondNum;
        private long fubaoNum;
        private long sunNum;
        private long moonNum;
        private long jinbiNum;

        public String getSkillType() {
            return skillType;
        }

        public void setSkillType(String skillType) {
            this.skillType = skillType;
        }

        public String getSkillDescribe() {
            return skillDescribe;
        }

        public void setSkillDescribe(String skillDescribe) {
            this.skillDescribe = skillDescribe;
        }

        public int getBaseClientRate() {
            return baseClientRate;
        }

        public void setBaseClientRate(int baseClientRate) {
            this.baseClientRate = baseClientRate;
        }

        public int getNeedExp() {
            return needExp;
        }

        public void setNeedExp(int needExp) {
            this.needExp = needExp;
        }

        public int getSkillEnergy() {
            return skillEnergy;
        }

        public void setSkillEnergy(int skillEnergy) {
            this.skillEnergy = skillEnergy;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public long getDiamondNum() {
            return diamondNum;
        }

        public void setDiamondNum(long diamondNum) {
            this.diamondNum = diamondNum;
        }

        public long getFubaoNum() {
            return fubaoNum;
        }

        public void setFubaoNum(long fubaoNum) {
            this.fubaoNum = fubaoNum;
        }

        public long getSunNum() {
            return sunNum;
        }

        public void setSunNum(long sunNum) {
            this.sunNum = sunNum;
        }

        public long getMoonNum() {
            return moonNum;
        }

        public void setMoonNum(long moonNum) {
            this.moonNum = moonNum;
        }

        public long getJinbiNum() {
            return jinbiNum;
        }

        public void setJinbiNum(long jinbiNum) {
            this.jinbiNum = jinbiNum;
        }
    }
}
