package com.qianyu.communicate.utils;

import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.qianyu.communicate.appliction.MyApplication;

import org.haitao.common.utils.AppLog;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class LocationHelper {
    private LocationCallBack callBack;
    private static LocationHelper helper;
    private LocationClient locationClient;
    private BDLocationListener locationListener = new MyBDLocationListener();

    private LocationHelper() {
        //第一步实例化定位核心类
        locationClient = new LocationClient(MyApplication.getInstance(), getLocOption());
        //第二步设置位置变化回调监听
        locationClient.registerLocationListener(locationListener);
    }

    public static LocationHelper getInstance() {
        if (helper == null) {
            helper = new LocationHelper();
        }
        return helper;
    }


    public void start() {
        //        第三步开始定位
        if (locationClient != null) {
            locationClient.start();
        }
    }

    //一般会在Activity的OnDestroy方法调用
    public void stop() {
        if (locationClient != null) {
            locationClient.unRegisterLocationListener(locationListener);
            locationClient.stop();
            locationClient = null;
        }
    }

    private LocationClientOption getLocOption() {
        LocationClientOption option = new LocationClientOption();
        option.setAddrType("all");
        //设置定位坐标系
        option.setCoorType("bd09ll");
        //重新定位时间间隔
        //option.setScanSpan(60*1000);
        //设置是否打开gps
        option.setOpenGps(true);
        //设置定位模式
        option.setLocationNotify(true);
        //是否需要poi结果
//        option.setPoiDistance(1000);
//        option.setPoiExtraInfo(true);
        return option;
    }

    private class MyBDLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            AppLog.e("=================onReceiveLocation111=============");
            if (callBack != null && bdLocation != null&&!TextUtils.isEmpty(bdLocation.getCity())) {
                String city = bdLocation.getCity().replace("市","");
                callBack.callBack(bdLocation.getAddrStr(), bdLocation.getStreetNumber(), bdLocation.getLatitude(), bdLocation.getLongitude(), bdLocation.getDistrict(), bdLocation.getStreet(), city, bdLocation.getProvince());
                AppLog.e("=================onReceiveLocation222=============");
            }
            AppLog.e("=================onReceiveLocation333=============");
            //多次定位必须要调用stop方法
            if (locationClient != null) {
                locationClient.stop();
            }
        }
    }


    public interface LocationCallBack {
        void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province);
    }

    public LocationCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(LocationCallBack callBack) {
        this.callBack = callBack;

    }

}