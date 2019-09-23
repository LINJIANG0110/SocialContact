package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class UserConfig {

    /**
     * blacklistId : {"userId":48}
     * configId : 1
     * contactAddReject : 1
     * refuseStranger : 1
     * refuseToTalk : 1
     * refusedInvitation : 1
     * users : {"userId":48}
     */

    private BlacklistIdBean blacklistId;
    private int configId;
    private String contactAddReject;
    private String refuseStranger;
    private String refuseToTalk;
    private String refusedInvitation;
    private UsersBean users;

    public BlacklistIdBean getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(BlacklistIdBean blacklistId) {
        this.blacklistId = blacklistId;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getContactAddReject() {
        return contactAddReject;
    }

    public void setContactAddReject(String contactAddReject) {
        this.contactAddReject = contactAddReject;
    }

    public String getRefuseStranger() {
        return refuseStranger;
    }

    public void setRefuseStranger(String refuseStranger) {
        this.refuseStranger = refuseStranger;
    }

    public String getRefuseToTalk() {
        return refuseToTalk;
    }

    public void setRefuseToTalk(String refuseToTalk) {
        this.refuseToTalk = refuseToTalk;
    }

    public String getRefusedInvitation() {
        return refusedInvitation;
    }

    public void setRefusedInvitation(String refusedInvitation) {
        this.refusedInvitation = refusedInvitation;
    }

    public UsersBean getUsers() {
        return users;
    }

    public void setUsers(UsersBean users) {
        this.users = users;
    }

    public static class BlacklistIdBean {
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

    public static class UsersBean {
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
}
