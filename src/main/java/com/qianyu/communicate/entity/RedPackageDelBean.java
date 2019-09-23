package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-8-22.
 */

public class RedPackageDelBean {
    public int isOverTime;//0位过时1已过时
    public String totalNum;
    public String totalDiamond;
    public String type;
    public List<UserRecordBean> logList;
    public String title;
    public String userId;
    public String id;
    public String nowNum;
    public String groupId;
    public String balance;
    public String headUrl;
    public String nickName;

    public class UserRecordBean {
        public String diamond;
        public Long createTime;
        public String nickName;
        public String headUrl;
        public String word;
    }
}
