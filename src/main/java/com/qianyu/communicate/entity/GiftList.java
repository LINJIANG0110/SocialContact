package com.qianyu.communicate.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16 0016.
 */

public class GiftList {


    private double usersPice;
    private List<SouvenirNosBean> souvenirNos;
    /**
     * size : 20
     * totalPages : 1
     * content : [{"acceptCharm":2,"acceptCharmSpecial":1.0E-4,"acceptExperience":2,"acceptPlute":2,"createTime":1553668582000,"giftId":3,"giftName":"玫瑰花","giftPrice":1,"giftType":3,"sendCharm":2,"sendCharmSpecial":2,"sendExperience":2,"sendPlute":2,"state":1,"updateTime":1553679407000},{"acceptCharm":2,"acceptCharmSpecial":1.0E-4,"acceptExperience":2,"acceptPlute":2,"createTime":1553668556000,"giftId":2,"giftName":"玫瑰花","giftPrice":1,"giftType":2,"sendCharm":2,"sendCharmSpecial":1.0E-4,"sendExperience":2,"sendPlute":2,"state":1,"updateTime":1553679404000},{"acceptCharm":2,"acceptCharmSpecial":1.0E-4,"acceptExperience":2,"acceptPlute":2,"createTime":1553668377000,"giftId":1,"giftName":"玫瑰花","giftPrice":10,"giftType":1,"sendCharm":2,"sendCharmSpecial":1.0E-4,"sendExperience":2,"sendPlute":2,"state":1,"updateTime":1553679513000}]
     * totalElements : 3
     */

    private int size;
    private int totalPages;
    private int totalElements;
    private List<ContentBean> content;


    public double getUsersPice() {
        return usersPice;
    }

    public void setUsersPice(double usersPice) {
        this.usersPice = usersPice;
    }

    public List<SouvenirNosBean> getSouvenirNos() {
        return souvenirNos;
    }

    public void setSouvenirNos(List<SouvenirNosBean> souvenirNos) {
        this.souvenirNos = souvenirNos;
    }

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

    public static class SouvenirNosBean {
        /**
         * id : 1
         * meaning : 一生一世
         * quantity : 1314
         */

        private long id;
        private String meaning;
        private double quantity;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getMeaning() {
            return meaning;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }
    }

    public static class ContentBean implements Serializable{
        /**
         * acceptCharm : 2
         * acceptCharmSpecial : 1.0E-4
         * acceptExperience : 2
         * acceptPlute : 2
         * createTime : 1553668582000
         * giftId : 3
         * giftName : 玫瑰花
         * giftPrice : 1
         * giftType : 3
         * sendCharm : 2
         * sendCharmSpecial : 2
         * sendExperience : 2
         * sendPlute : 2
         * state : 1
         * updateTime : 1553679407000
         */

        private long acceptCharm;
        private double acceptCharmSpecial;
        private long acceptExperience;
        private long acceptPlute;
        private long createTime;
        private long giftId;
        private String giftName;
        private String giftUrl;
        private long giftPrice;
        private int giftType;
        private long sendCharm;
        private long sendCharmSpecial;
        private long sendExperience;
        private long sendPlute;
        private int state;
        private long giftNumber;
        private long updateTime;
        private boolean selected=false;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public long getAcceptCharm() {
            return acceptCharm;
        }

        public void setAcceptCharm(long acceptCharm) {
            this.acceptCharm = acceptCharm;
        }

        public double getAcceptCharmSpecial() {
            return acceptCharmSpecial;
        }

        public void setAcceptCharmSpecial(double acceptCharmSpecial) {
            this.acceptCharmSpecial = acceptCharmSpecial;
        }

        public long getAcceptExperience() {
            return acceptExperience;
        }

        public void setAcceptExperience(long acceptExperience) {
            this.acceptExperience = acceptExperience;
        }

        public long getAcceptPlute() {
            return acceptPlute;
        }

        public void setAcceptPlute(long acceptPlute) {
            this.acceptPlute = acceptPlute;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getGiftId() {
            return giftId;
        }

        public void setGiftId(long giftId) {
            this.giftId = giftId;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getGiftUrl() {
            return giftUrl;
        }

        public void setGiftUrl(String giftUrl) {
            this.giftUrl = giftUrl;
        }

        public long getGiftPrice() {
            return giftPrice;
        }

        public void setGiftPrice(long giftPrice) {
            this.giftPrice = giftPrice;
        }

        public int getGiftType() {
            return giftType;
        }

        public void setGiftType(int giftType) {
            this.giftType = giftType;
        }

        public long getSendCharm() {
            return sendCharm;
        }

        public void setSendCharm(long sendCharm) {
            this.sendCharm = sendCharm;
        }

        public long getSendCharmSpecial() {
            return sendCharmSpecial;
        }

        public void setSendCharmSpecial(long sendCharmSpecial) {
            this.sendCharmSpecial = sendCharmSpecial;
        }

        public long getSendExperience() {
            return sendExperience;
        }

        public void setSendExperience(long sendExperience) {
            this.sendExperience = sendExperience;
        }

        public long getSendPlute() {
            return sendPlute;
        }

        public void setSendPlute(long sendPlute) {
            this.sendPlute = sendPlute;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getGiftNumber() {
            return giftNumber;
        }

        public void setGiftNumber(long giftNumber) {
            this.giftNumber = giftNumber;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
