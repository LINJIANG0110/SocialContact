package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class UserDetail  {

    /**
     * birthday : -28800000
     * headPath : http://p.3761.com/pic/25991417567978.jpg
     * labelDetailsModels : [{"detailsId":13,"labelName":"楚留香传奇"}]
     * nickName : 小白
     * professionModels : [{"headLine":"空姐","name":"空姐","professionId":11}]
     * signature : 唯有自重，方的尊重。
     * userId : 48
     */

    private String birthday;
    private String headPath;
    private String nickName;
    private String signature;
    private long userId;
    private List<ImpressLabel> labelDetailsModels;
    private List<WorkTag> professionModels;
    private String sex;   //性别

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<ImpressLabel> getLabelDetailsModels() {
        return labelDetailsModels;
    }

    public void setLabelDetailsModels(List<ImpressLabel> labelDetailsModels) {
        this.labelDetailsModels = labelDetailsModels;
    }

    public List<WorkTag> getProfessionModels() {
        return professionModels;
    }

    public void setProfessionModels(List<WorkTag> professionModels) {
        this.professionModels = professionModels;
    }

    public static class LabelDetailsModelsBean {
        /**
         * detailsId : 13
         * labelName : 楚留香传奇
         */

        private long detailsId;
        private String labelName;

        public long getDetailsId() {
            return detailsId;
        }

        public void setDetailsId(long detailsId) {
            this.detailsId = detailsId;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }
    }

    public static class ProfessionModelsBean {
        /**
         * headLine : 空姐
         * name : 空姐
         * professionId : 11
         */

        private String headLine;
        private String name;
        private long professionId;

        public String getHeadLine() {
            return headLine;
        }

        public void setHeadLine(String headLine) {
            this.headLine = headLine;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getProfessionId() {
            return professionId;
        }

        public void setProfessionId(long professionId) {
            this.professionId = professionId;
        }
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
