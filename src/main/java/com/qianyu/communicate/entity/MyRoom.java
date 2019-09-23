package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2018-2-7.
 */

public class MyRoom {

    /**
     * families : [{"createTime":1517914765000,"familyName":"路途他啊","familyPath":"http://word-file.oss-cn-beijing.aliyuncs.com/a900dfb282a70906aa79c66dcbcd2d98.png","fid":102,"freeType":"1","liveType":"1","price":0},{"createTime":1517811576000,"familyName":"啦啦啦阿里","familyPath":"http://word-file.oss-cn-beijing.aliyuncs.com/f724091ca1433f93a52155cfe19a7331.png","fid":92,"freeType":"1","liveType":"1","price":0}]
     * totalFamily : 2
     */

    private int totalFamily;
    private List<SubScription.SubscriptionsBean> families;

    public int getTotalFamily() {
        return totalFamily;
    }

    public void setTotalFamily(int totalFamily) {
        this.totalFamily = totalFamily;
    }

    public List<SubScription.SubscriptionsBean> getFamilies() {
        return families;
    }

    public void setFamilies(List<SubScription.SubscriptionsBean> families) {
        this.families = families;
    }

}
