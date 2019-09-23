package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class InComeList {


    /**
     * size : 20
     * totalPages : 2
     * content : [{"consume":20,"content":"释放技能:六脉神剑","createTime":1558525331000,"id":30,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558518721000,"id":27,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558518540000,"id":26,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558518391000,"id":25,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558517987000,"id":24,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558517900000,"id":23,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558517879000,"id":22,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558517721000,"id":21,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558517701000,"id":20,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558517673000,"id":19,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558517500000,"id":18,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558517498000,"id":17,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558514331000,"id":16,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558514157000,"id":15,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558514097000,"id":14,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558513926000,"id":13,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558513823000,"id":12,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558076698000,"id":9,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558076696000,"id":8,"isOk":2,"userId":80},{"consume":68800,"content":"赠送梦幻浮岛x1","createTime":1558076696000,"id":7,"isOk":2,"userId":80}]
     * totalElements : 24
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
         * consume : 20
         * content : 释放技能:六脉神剑
         * createTime : 1558525331000
         * id : 30
         * isOk : 2
         * userId : 80
         */

        private double consume;
        private String content;
        private long createTime;
        private long id;
        private int isOk;
        private long userId;

        public double getConsume() {
            return consume;
        }

        public void setConsume(double consume) {
            this.consume = consume;
        }

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

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getIsOk() {
            return isOk;
        }

        public void setIsOk(int isOk) {
            this.isOk = isOk;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }
}
