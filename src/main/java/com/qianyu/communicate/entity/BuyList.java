package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class BuyList {

    /**
     * countBuyHistory : 12
     * historyList : [{"createTime":1513764492000,"mediaId":20,"mediaName":"李雷小视频","mediaPath":"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=650aa0545b0fd9f9b41a5d2a4d44be5f/d62a6059252dd42aa6c68e83093b5bb5c8eab8cf.jpg","mediaType":1,"payTime":123,"totalPerson":0,"totalTime":-25648000,"user":{"userId":48}},{"createTime":1513764492000,"mediaId":21,"mediaName":"李雷小视频","mediaPath":"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=650aa0545b0fd9f9b41a5d2a4d44be5f/d62a6059252dd42aa6c68e83093b5bb5c8eab8cf.jpg","mediaType":1,"payTime":123,"totalPerson":0,"totalTime":-25648000,"user":{"userId":48}},{"createTime":1513764492000,"mediaId":22,"mediaName":"李雷小视频","mediaPath":"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=650aa0545b0fd9f9b41a5d2a4d44be5f/d62a6059252dd42aa6c68e83093b5bb5c8eab8cf.jpg","mediaType":1,"payTime":123,"totalPerson":0,"totalTime":-25648000,"user":{"userId":48}},{"createTime":1513764492000,"mediaId":23,"mediaName":"李雷小视频","mediaPath":"http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=650aa0545b0fd9f9b41a5d2a4d44be5f/d62a6059252dd42aa6c68e83093b5bb5c8eab8cf.jpg","mediaType":1,"payTime":123,"totalPerson":0,"totalTime":-25648000,"user":{"userId":48}}]
     */

    private int countBuyHistory;
    private List<MediaList> historyList;

    public int getCountBuyHistory() {
        return countBuyHistory;
    }

    public void setCountBuyHistory(int countBuyHistory) {
        this.countBuyHistory = countBuyHistory;
    }

    public List<MediaList> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<MediaList> historyList) {
        this.historyList = historyList;
    }

}
