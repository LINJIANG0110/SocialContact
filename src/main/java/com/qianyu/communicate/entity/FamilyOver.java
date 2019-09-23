package com.qianyu.communicate.entity;

import java.io.Serializable;

/**
 * Created by JavaDog on 2018-2-2.
 */

public class FamilyOver implements Serializable {

    /**
     * countPerson : 1
     * family : {"createTime":1517565113000,"familyName":"快乐旅途喇叭","familyPath":"http://word-file.oss-cn-beijing.aliyuncs.com/a900dfb282a70906aa79c66dcbcd2d98.png","fid":81,"freeType":"1","liveType":"1","price":0}
     * totalIncome : 0
     * totalTime : 0
     */

    private double countPerson;
    private FamilyBean family;
    private double totalIncome;
    private long totalTime;

    public double getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(double countPerson) {
        this.countPerson = countPerson;
    }

    public FamilyBean getFamily() {
        return family;
    }

    public void setFamily(FamilyBean family) {
        this.family = family;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public static class FamilyBean implements Serializable{
        /**
         * createTime : 1517565113000
         * familyName : 快乐旅途喇叭
         * familyPath : http://word-file.oss-cn-beijing.aliyuncs.com/a900dfb282a70906aa79c66dcbcd2d98.png
         * fid : 81
         * freeType : 1
         * liveType : 1
         * price : 0
         */

        private long createTime;
        private String familyName;
        private String familyPath;
        private int fid;
        private String freeType;
        private String liveType;
        private int price;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public String getFamilyPath() {
            return familyPath;
        }

        public void setFamilyPath(String familyPath) {
            this.familyPath = familyPath;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getFreeType() {
            return freeType;
        }

        public void setFreeType(String freeType) {
            this.freeType = freeType;
        }

        public String getLiveType() {
            return liveType;
        }

        public void setLiveType(String liveType) {
            this.liveType = liveType;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
