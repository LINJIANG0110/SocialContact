package com.qianyu.communicate.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JavaDog on 2019-5-13.
 */

public class EventRecord implements Serializable{

    /**
     * size : 20
     * totalPages : 1
     * content : [{"acceptNickName":"AngleBaby","acceptUserId":23,"createTime":1557479579342,"eventMsg":"送给AngleBaby1个梦幻浮岛","eventType":"GIFT","groupId":13,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg","id":8,"picPath":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554960942827&di=abb3cfea25404e0dc5991bc81b9da685&imgtype=0&src=http%3A%2F%2Fimg10.360buyimg.com%2Fn0%2Fg8%2FM01%2F10%2F0D%2FrBEHaFDFfToIAAAAAAHBV9lFeDYAADNGQMNLAMAAcFv315.jpg","pushType":"group","sendNickName":"马尔扎哈","sendUserId":29},{"acceptNickName":"AngleBaby","acceptUserId":23,"createTime":1557479575684,"eventMsg":"送给AngleBaby1个梦幻浮岛","eventType":"GIFT","groupId":13,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg","id":7,"picPath":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554960942827&di=abb3cfea25404e0dc5991bc81b9da685&imgtype=0&src=http%3A%2F%2Fimg10.360buyimg.com%2Fn0%2Fg8%2FM01%2F10%2F0D%2FrBEHaFDFfToIAAAAAAHBV9lFeDYAADNGQMNLAMAAcFv315.jpg","pushType":"group","sendNickName":"马尔扎哈","sendUserId":29},{"acceptNickName":"AngleBaby","acceptUserId":23,"createTime":1557479572578,"eventMsg":"送给AngleBaby1个梦幻浮岛","eventType":"GIFT","groupId":13,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg","id":6,"picPath":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554960942827&di=abb3cfea25404e0dc5991bc81b9da685&imgtype=0&src=http%3A%2F%2Fimg10.360buyimg.com%2Fn0%2Fg8%2FM01%2F10%2F0D%2FrBEHaFDFfToIAAAAAAHBV9lFeDYAADNGQMNLAMAAcFv315.jpg","pushType":"group","sendNickName":"马尔扎哈","sendUserId":29}]
     * totalElements : 3
     */

    private int size;
    private int totalPages;
    private int totalElements;
    private List<ContentBean> content;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable{
        /**
         * acceptNickName : AngleBaby
         * acceptUserId : 23
         * createTime : 1557479579342
         * eventMsg : 送给AngleBaby1个梦幻浮岛
         * eventType : GIFT
         * groupId : 13
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg
         * id : 8
         * picPath : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554960942827&di=abb3cfea25404e0dc5991bc81b9da685&imgtype=0&src=http%3A%2F%2Fimg10.360buyimg.com%2Fn0%2Fg8%2FM01%2F10%2F0D%2FrBEHaFDFfToIAAAAAAHBV9lFeDYAADNGQMNLAMAAcFv315.jpg
         * pushType : group
         * sendNickName : 马尔扎哈
         * sendUserId : 29
         */

        private String acceptNickName;
        private long acceptUserId;
        private long createTime;
        private String msg;
        private String eventMsg;
        private String eventType;
        private long groupId;
        private String headUrl;
        private long id;
        private String picPath;
        private String pushType;
        private String sendNickName;
        private long sendUserId;
        private int isOk;
        private String groupName;
        private String skillType;
        private long effectValue;
        private int sendSex;
        private int acceptSex;
        private long pdartId;
        private long sendPhone;
        private long accPhone;

        public int getSendSex() {
            return sendSex;
        }

        public void setSendSex(int sendSex) {
            this.sendSex = sendSex;
        }

        public int getAcceptSex() {
            return acceptSex;
        }

        public void setAcceptSex(int acceptSex) {
            this.acceptSex = acceptSex;
        }

        public String getSkillType() {
            return skillType;
        }

        public void setSkillType(String skillType) {
            this.skillType = skillType;
        }

        public long getEffectValue() {
            return effectValue;
        }

        public void setEffectValue(long effectValue) {
            this.effectValue = effectValue;
        }

        public String getAcceptNickName() {
            return acceptNickName;
        }

        public void setAcceptNickName(String acceptNickName) {
            this.acceptNickName = acceptNickName;
        }

        public long getAcceptUserId() {
            return acceptUserId;
        }

        public void setAcceptUserId(long acceptUserId) {
            this.acceptUserId = acceptUserId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getEventMsg() {
            return eventMsg;
        }

        public void setEventMsg(String eventMsg) {
            this.eventMsg = eventMsg;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public long getGroupId() {
            return groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }

        public String getPushType() {
            return pushType;
        }

        public void setPushType(String pushType) {
            this.pushType = pushType;
        }

        public String getSendNickName() {
            return sendNickName;
        }

        public void setSendNickName(String sendNickName) {
            this.sendNickName = sendNickName;
        }

        public long getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(long sendUserId) {
            this.sendUserId = sendUserId;
        }

        public int getIsOk() {
            return isOk;
        }

        public void setIsOk(int isOk) {
            this.isOk = isOk;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public long getPdartId() {
            return pdartId;
        }

        public void setPdartId(long pdartId) {
            this.pdartId = pdartId;
        }

        public long getSendPhone() {
            return sendPhone;
        }

        public void setSendPhone(long sendPhone) {
            this.sendPhone = sendPhone;
        }

        public long getAccPhone() {
            return accPhone;
        }

        public void setAccPhone(long accPhone) {
            this.accPhone = accPhone;
        }
    }
}
