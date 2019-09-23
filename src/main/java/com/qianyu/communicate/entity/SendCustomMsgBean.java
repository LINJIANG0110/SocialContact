package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-8-22.
 */

public class SendCustomMsgBean {
    public int redPacketId;
    public String redPacketWord;
    public String nextWordPinyin;
    public int messageType;
    public String text;

    public String getRedPacketWord() {
        return redPacketWord;
    }

    public void setRedPacketWord(String redPacketWord) {
        this.redPacketWord = redPacketWord;
    }

    public String getNextWordPinyin() {
        return nextWordPinyin;
    }

    public void setNextWordPinyin(String nextWordPinyin) {
        this.nextWordPinyin = nextWordPinyin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(int redPacketId) {
        this.redPacketId = redPacketId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
//{"hongbaoId":1,"redPacketWord":"一心一意","nextWordPinyin":"yi"," messageType":1,"text":""}