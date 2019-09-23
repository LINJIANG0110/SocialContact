package com.qianyu.communicate.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/15 0015.
 */

public class FansList implements Serializable{

    /**
     * contribution : 11111
     * souvenirModels : [{"createTime":1514205556000,"headPath":"http://p.3761.com/pic/25991417567978.jpg","nickName":"通途","totalMoney":11111,"userid":48}]
     */

    private double contribution;
    private List<SouvenirModelsBean> souvenirModels;
    private String whether;

    public String getWhether() {
        return whether;
    }

    public void setWhether(String whether) {
        this.whether = whether;
    }

    public double getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public List<SouvenirModelsBean> getSouvenirModels() {
        return souvenirModels;
    }

    public void setSouvenirModels(List<SouvenirModelsBean> souvenirModels) {
        this.souvenirModels = souvenirModels;
    }

    public static class SouvenirModelsBean implements Serializable{
        /**
         * createTime : 1514205556000
         * headPath : http://p.3761.com/pic/25991417567978.jpg
         * nickName : 通途
         * totalMoney : 11111
         * userid : 48
         */

        private long createTime;
        private String headPath;
        private String nickName;
        private double totalMoney;
        private long userid;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getHeadPath() {
            return headPath;
        }

        public void setHeadPath(String headPath) {
            this.headPath = headPath;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            this.totalMoney = totalMoney;
        }

        public long getUserid() {
            return userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }
    }
}
