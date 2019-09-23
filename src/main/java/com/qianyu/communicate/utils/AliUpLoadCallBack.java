package com.qianyu.communicate.utils;


import com.alibaba.sdk.android.oss.model.OSSException;

public abstract class AliUpLoadCallBack {


    public abstract void onSuccess(String RootPath, String bucket, String fileName);

    public void onProgress(String objectKey, int byteCount, int totalSize) {

    }

    public abstract void onFailure(String objectKey, OSSException ossException);
}
