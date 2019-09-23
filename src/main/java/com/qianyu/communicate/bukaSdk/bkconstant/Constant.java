package com.qianyu.communicate.bukaSdk.bkconstant;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class Constant {
    public static final long RPC_USER_OUT = 1020;
    //    public static final String KEY = "BukaSDK_Media_Info";
    public static final String KEY = "stream_info";
    public static final String STATUS_TAG = "stream";
    public static final String AID = "aid";
    public static final String VID = "vid";
    public static final String APPLICATIONKEY = "Go2mIz1H7vWAo1G7S8AD5p0r1frt769d";
    public static final int DOCUMENT_CODE = 0x001;
    public static final int DOCUMENT_CODE_ = 0x002;
    public static final String DOCUMENT_DATA = "docement_data";

    public static final int RPC_GIFT = 111;    //送礼物
    public static final int RPC_SPEAK = 2222;   //禁言
    public static final int RPC_MANAGER = 3333;  //设管理员
    public static final int RPC_TICKOUT = 433;  //踢人
    public static final int RPC_STREAM_WAIT = 31235;  //推流等待
    public static final int RPC_STREAM_CONNECT = 123126;  //推流接通
    public static final int RPC_STREAM_END = 12317;  //推流结束
    public static final int RPC_STREAM_LIVE_END = 123128;  //主播结束推流和播放
    public static final int RPC_STREAM_SPEAKER_END = 1321239;  //连麦的人结束推流和播放
    public static final int RPC_VIDEO_PLAY = 1123120;  //主播点播视频
    public static final int PRAISE_SUCCESS = 12312311;  //打赏

    public static final int RPC_SEND_GIFT = 1;    //送礼物
    public static final int RPC_FORBID_SPEAK = 2;   //禁言
    public static final int RPC_ALLOW_SPEAK = 3;   //取消禁言
    public static final int RPC_FORBID_IN = 4;   //禁入
    public static final int RPC_ALLOW_IN = 5;   //取消禁入
    public static final int RPC_SET_MANAGER = 6;  //设管理员
    public static final int RPC_TICK_SB = 7;  //踢人
    public static final int RPC_BEGIN_FIRST_MIKE = 8;  //上一麦
    public static final int RPC_BEGIN_SECOND_MIKE = 9;  //上二麦
    public static final int RPC_END_FIRST_MIKE = 10;  //下一麦
    public static final int RPC_END_SECOND_MIKE = 11;  //下二麦
    public static final int RPC_REJECT_MIKE = 12;  //拒绝上麦
    public static final int RPC_RIDE_IN = 13;  //坐骑进入
}
