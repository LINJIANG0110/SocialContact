package com.qianyu.communicate.entity;

/**
 * Created by KathLine on 2017/1/8.
 */
public class RideModel {
    private long userId;
    private String nickName;
    private String mountName;//坐骑名字
    private String mountPicPath;//坐骑图片

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

    public String getMountName() {
        return mountName;
    }

    public void setMountName(String mountName) {
        this.mountName = mountName;
    }

    public String getMountPicPath() {
        return mountPicPath;
    }

    public void setMountPicPath(String mountPicPath) {
        this.mountPicPath = mountPicPath;
    }
}
