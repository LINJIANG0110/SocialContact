package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15 0015.
 */

public class ConcernList {

    /**
     * size : 20
     * totalPages : 1
     * content : [{"sendUserId":26,"phone":"17611038900","nickName":"刘诗诗","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=93fa906c27e80289e248afd4ada6e6bc&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F0%2Fw1200h1200%2F20180519%2F9966-haturfs7204243.jpg"},{"sendUserId":54,"phone":"13689022141","nickName":"AAA","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196600314&di=d1bfc453bb297ec20a2981610ed1139f&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F94o3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2F3c6d55fbb2fb4316ed8affc727a4462308f7d3f2.jpg"}]
     * totalElements : 2
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

    public static class ContentBean {
        /**
         * sendUserId : 26
         * phone : 17611038900
         * nickName : 刘诗诗
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=93fa906c27e80289e248afd4ada6e6bc&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F0%2Fw1200h1200%2F20180519%2F9966-haturfs7204243.jpg
         */

        private long userId;
        private long sendUserId;
        private String phone;
        private String nickName;
        private String headUrl;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(long sendUserId) {
            this.sendUserId = sendUserId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }
    }
}
