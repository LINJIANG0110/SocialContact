package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2018-1-29.
 */

public class ChatRoom {
    private int familyUserId;   //聊天室内id
    private String manager;     //0普通用户 1管理员 2主播
    private String silence;     //0 未禁言  1已禁言
    private String kick;        //0未被踢   1已被踢
    private String machineCode; //游客进入的时候传的机器码
    /**
     * family : {"fid":60}
     * user : {"userId":48}
     */

    private FamilyBean family;
    private UserBean user;

    public int getFamilyUserId() {
        return familyUserId;
    }

    public void setFamilyUserId(int familyUserId) {
        this.familyUserId = familyUserId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSilence() {
        return silence;
    }

    public void setSilence(String silence) {
        this.silence = silence;
    }

    public String getKick() {
        return kick;
    }

    public void setKick(String kick) {
        this.kick = kick;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }


    public FamilyBean getFamily() {
        return family;
    }

    public void setFamily(FamilyBean family) {
        this.family = family;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class FamilyBean {
        /**
         * fid : 60
         */

        private int fid;

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }
    }

    public static class UserBean {
        /**
         * userId : 48
         */

        private int userId;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "familyUserId=" + familyUserId +
                ", manager='" + manager + '\'' +
                ", silence='" + silence + '\'' +
                ", kick='" + kick + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", family=" + family +
                ", user=" + user +
                '}';
    }
}
