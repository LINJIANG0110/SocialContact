package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-8-21.
 */

public class RedPackageBean {
    public String wordS;// 为字母拼音
    public int hongbaoId;
    public String title;
    public int messageType;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getWordS() {
        return wordS;
    }

    public void setWordS(String wordS) {
        this.wordS = wordS;
    }

    public int getHongbaoId() {
        return hongbaoId;
    }

    public void setHongbaoId(int hongbaoId) {
        this.hongbaoId = hongbaoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
