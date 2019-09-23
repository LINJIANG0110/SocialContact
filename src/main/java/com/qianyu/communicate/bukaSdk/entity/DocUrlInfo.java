package com.qianyu.communicate.bukaSdk.entity;

/**
 * Created by tangxiaowei on 2017/8/27.
 */

public class DocUrlInfo {
    public DocInfo data;
    public int code;
    public String msg;

    @Override
    public String toString() {
        return "DocUrlInfo{" +
                "data=" + data.toString() +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
