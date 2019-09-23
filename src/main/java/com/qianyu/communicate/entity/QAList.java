package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2018-5-30.
 */

public class QAList {

    /**
     * content : 1111111111111111111111111111111111111111
     * createTime : 1527580144000
     * doctoeNickName : 用户100225
     * doctorHeadPath : aaa
     * doctorId : 244
     * familyName : 123
     * fid : 0
     * id : 1
     * pid : 0
     * praise : 0
     * replyList : [{"content":"1111122222222222","createTime":1527580243000,"doctoeNickName":"用户100225","doctorHeadPath":"","doctorId":244,"familyName":"","fid":0,"id":2,"pid":1,"praise":0,"replys":0,"title":"","type":2,"userHeadPath":"","userId":0,"userNickName":"","views":0}]
     * replys : 1
     * title :
     * type : 1
     * userHeadPath : http://shenqimingyi.oss-cn-beijing.aliyuncs.com/picture/1526028232885file.jpg
     * userId : 243
     * userNickName : 我
     * views : 0
     */

    private String content;
    private long createTime;
    private String doctoeNickName;
    private String doctorHeadPath;
    private int doctorId;
    private String familyName;
    private int fid;
    private int iId;
    private int pid;
    private int praise;
    private int replys;
    private String title;
    private int type;
    private String userHeadPath;
    private int userId;
    private String userNickName;
    private int views;
    private List<ReplyListBean> replyList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDoctoeNickName() {
        return doctoeNickName;
    }

    public void setDoctoeNickName(String doctoeNickName) {
        this.doctoeNickName = doctoeNickName;
    }

    public String getDoctorHeadPath() {
        return doctorHeadPath;
    }

    public void setDoctorHeadPath(String doctorHeadPath) {
        this.doctorHeadPath = doctorHeadPath;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getReplys() {
        return replys;
    }

    public void setReplys(int replys) {
        this.replys = replys;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserHeadPath() {
        return userHeadPath;
    }

    public void setUserHeadPath(String userHeadPath) {
        this.userHeadPath = userHeadPath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<ReplyListBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyListBean> replyList) {
        this.replyList = replyList;
    }

    public static class ReplyListBean {
        /**
         * content : 1111122222222222
         * createTime : 1527580243000
         * doctoeNickName : 用户100225
         * doctorHeadPath :
         * doctorId : 244
         * familyName :
         * fid : 0
         * id : 2
         * pid : 1
         * praise : 0
         * replys : 0
         * title :
         * type : 2
         * userHeadPath :
         * userId : 0
         * userNickName :
         * views : 0
         */

        private String content;
        private long createTime;
        private String doctoeNickName;
        private String doctorHeadPath;
        private int doctorId;
        private String familyName;
        private int fid;
        private int iId;
        private int pid;
        private int praise;
        private int replys;
        private String title;
        private int type;
        private String userHeadPath;
        private int userId;
        private String userNickName;
        private int views;
        private List<ReplyListBean> replyList;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDoctoeNickName() {
            return doctoeNickName;
        }

        public void setDoctoeNickName(String doctoeNickName) {
            this.doctoeNickName = doctoeNickName;
        }

        public String getDoctorHeadPath() {
            return doctorHeadPath;
        }

        public void setDoctorHeadPath(String doctorHeadPath) {
            this.doctorHeadPath = doctorHeadPath;
        }

        public int getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(int doctorId) {
            this.doctorId = doctorId;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getiId() {
            return iId;
        }

        public void setiId(int iId) {
            this.iId = iId;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public int getReplys() {
            return replys;
        }

        public void setReplys(int replys) {
            this.replys = replys;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserHeadPath() {
            return userHeadPath;
        }

        public void setUserHeadPath(String userHeadPath) {
            this.userHeadPath = userHeadPath;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }
    }
}
