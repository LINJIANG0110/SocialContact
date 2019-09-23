package com.qianyu.communicate.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JavaDog on 2019-6-6.
 */

public class MallBillList{

    /**
     * sum : {"gold":1150,"diamond":1050,"fubao":1272}
     * pageList : [{"accHeadUrl":"http://qianyubk.oss-cn-shanghai.aliyuncs.com/5957482037574901.jpg","createTime":1559787497000,"accNickName":"沐紫忆","diamond":10,"gold":10,"accUserId":83,"fubao":10},{"accHeadUrl":"http://qianyubk.oss-cn-shanghai.aliyuncs.com/5957482037574901.jpg","createTime":1559787481000,"accNickName":"沐紫忆","diamond":10,"gold":10,"accUserId":83,"fubao":10},{"accUserId":76,"gold":0,"diamond":0,"accHeadUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559797518319&di=4ad6b3f4ab0e0426936abeb81d072468&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn06%2F726%2Fw1381h945%2F20180430%2F1c53-fzvpatr3070762.jpg","accNickName":"Ethan","createTime":1559719310000,"fubao":122},{"accNickName":"沐紫忆","accHeadUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559797518319&di=4ad6b3f4ab0e0426936abeb81d072468&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn06%2F726%2Fw1381h945%2F20180430%2F1c53-fzvpatr3070762.jpg","diamond":10,"gold":10,"accUserId":83,"fubao":10,"createTime":1559634228000},{"accNickName":"沐紫忆","accHeadUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559797518319&di=4ad6b3f4ab0e0426936abeb81d072468&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn06%2F726%2Fw1381h945%2F20180430%2F1c53-fzvpatr3070762.jpg","createTime":1559634183000,"diamond":10,"gold":10,"accUserId":83,"fubao":10},{"accNickName":"沐紫忆","accHeadUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559797518319&di=4ad6b3f4ab0e0426936abeb81d072468&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn06%2F726%2Fw1381h945%2F20180430%2F1c53-fzvpatr3070762.jpg","diamond":10,"gold":10,"createTime":1559633977000,"accUserId":83,"fubao":10},{"diamond":0,"fubao":0,"createTime":1559627364000,"accHeadUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559797518319&di=4ad6b3f4ab0e0426936abeb81d072468&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn06%2F726%2Fw1381h945%2F20180430%2F1c53-fzvpatr3070762.jpg","gold":1000,"accNickName":"玉公子","accUserId":83},{"gold":0,"diamond":0,"accHeadUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559797518319&di=4ad6b3f4ab0e0426936abeb81d072468&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn06%2F726%2Fw1381h945%2F20180430%2F1c53-fzvpatr3070762.jpg","fubao":1000,"accNickName":"玉公子","createTime":1559627349000,"accUserId":83},{"accUserId":76,"gold":0,"fubao":0,"createTime":1559627330000,"accHeadUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559797518319&di=4ad6b3f4ab0e0426936abeb81d072468&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn06%2F726%2Fw1381h945%2F20180430%2F1c53-fzvpatr3070762.jpg","accNickName":"娃哈哈","diamond":1000},{"accUserId":76,"fubao":100,"diamond":0,"gold":100,"accHeadUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559797518319&di=4ad6b3f4ab0e0426936abeb81d072468&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn06%2F726%2Fw1381h945%2F20180430%2F1c53-fzvpatr3070762.jpg","accNickName":"娃哈哈","createTime":1559627313000}]
     */

    private SumBean sum;
    private List<PageListBean> pageList;

    public SumBean getSum() {
        return sum;
    }

    public void setSum(SumBean sum) {
        this.sum = sum;
    }

    public List<PageListBean> getPageList() {
        return pageList;
    }

    public void setPageList(List<PageListBean> pageList) {
        this.pageList = pageList;
    }

    public static class SumBean {
        /**
         * gold : 1150
         * diamond : 1050
         * fubao : 1272
         */

        private long gold;
        private long diamond;
        private long fubao;

        public long getGold() {
            return gold;
        }

        public void setGold(long gold) {
            this.gold = gold;
        }

        public long getDiamond() {
            return diamond;
        }

        public void setDiamond(long diamond) {
            this.diamond = diamond;
        }

        public long getFubao() {
            return fubao;
        }

        public void setFubao(long fubao) {
            this.fubao = fubao;
        }
    }

    public static class PageListBean {
        /**
         * accHeadUrl : http://qianyubk.oss-cn-shanghai.aliyuncs.com/5957482037574901.jpg
         * createTime : 1559787497000
         * accNickName : 沐紫忆
         * diamond : 10
         * gold : 10
         * accUserId : 83
         * fubao : 10
         */

        private String accHeadUrl;
        private long createTime;
        private String accNickName;
        private long diamond;
        private long gold;
        private int accUserId;
        private long fubao;

        public String getAccHeadUrl() {
            return accHeadUrl;
        }

        public void setAccHeadUrl(String accHeadUrl) {
            this.accHeadUrl = accHeadUrl;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getAccNickName() {
            return accNickName;
        }

        public void setAccNickName(String accNickName) {
            this.accNickName = accNickName;
        }

        public long getDiamond() {
            return diamond;
        }

        public void setDiamond(long diamond) {
            this.diamond = diamond;
        }

        public long getGold() {
            return gold;
        }

        public void setGold(long gold) {
            this.gold = gold;
        }

        public int getAccUserId() {
            return accUserId;
        }

        public void setAccUserId(int accUserId) {
            this.accUserId = accUserId;
        }

        public long getFubao() {
            return fubao;
        }

        public void setFubao(long fubao) {
            this.fubao = fubao;
        }
    }
}
