package com.qianyu.communicate.http;


import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.alibaba.fastjson.JSON;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.User;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.OkHttpUtils;
import net.neiquan.okhttp.http.APPURL;
import net.neiquan.okhttp.listener.UploadListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import tv.buka.sdk.utils.DeviceUtils;

/**
 * 作者 ： dyj
 * 时间 ： 2016/2/21.
 */

public class NetUtils {

    /**
     * pageSize
     */
    public static int PAGE_SIZE = 10;

    private static NetUtils single = null;

    // 静态工厂方法
    public static NetUtils getInstance() {
        if (single == null) {
            single = new NetUtils();
        }
        return single;
    }

    public void norPost(String url, Object object, final NetCallBack callback, final Class classType) {
        OkHttpUtils.getInstance().postJsonAnsy(url, JSON.toJSONString(object), callback, classType);
    }

    public void norPost(String url, Map<String, Object> map, final NetCallBack callback, final Class classType) {
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        User user = MyApplication.getInstance().user;
        if (user != null) {
            String token = user.getToken();
            if (!TextUtils.isEmpty(token)) {
                map.put("token", token);
            }
            map.put("uid", user.getUserId());
        }
        map.put("termType", 1);//设备类型 1安卓 2ios
        if (net.neiquan.okhttp.http.NetUtils.deviceId != null) {
            map.put("deviceId", net.neiquan.okhttp.http.NetUtils.deviceId);
        }
//        OkHttpUtils.getInstance().postJsonAnsy(url, map, callback, classType);
        OkHttpUtils.getInstance().postAnsy(url, map, callback, classType);
    }

    public void norPostFile(String url, Pair<String, File> file, Map<String, String> map, final UploadListener uploadListener) {
        if (map == null) {
            map = new HashMap<String, String>();
        }
        OkHttpUtils.getInstance().postFile(url, file, map, uploadListener);
    }

    public void norPost_(String url, Map<String, Object> map, final NetCallBack callback, final Class classType) {
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        if (MyApplication.getInstance().isLogin()) {
            map.put("token", MyApplication.getInstance().user.getToken()); // 拼接userid
        }
        //map.put("deviceType", 0); // 拼接userid
        OkHttpUtils.getInstance().postJsonAnsy_(url, map, callback, classType);
        //OkHttpUtils.getInstance().SetSessionOutCls(WelcomeActivity.class);
    }

    /**
     * 登录注册
     *
     * @param otherType  1qq 2微信 3微博 4手机号
     * @param otherToken 登录token,手机号登录传手机号
     * @param phone
     * @param code
     * @param latitude
     * @param longitude
     * @param address
     * @param callback
     * @param classType
     */
    public void login(int otherType, String otherToken, String jiguangId, String phone, String code, String latitude, String longitude, String address, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("otherType", otherType);
        params.put("otherToken", otherToken);
        params.put("jiguangId", TextUtils.isEmpty(jiguangId) ? "" : jiguangId);
        if (!TextUtils.isEmpty(code))
            params.put("code", code);
        if (!TextUtils.isEmpty(phone))
            params.put("phone", phone);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("address", TextUtils.isEmpty(address) ? "" : address);
        norPost(APPURL.LOGIN, params, callback, classType);
    }

    /**
     * 快速登录
     *
     * @param jiguangId
     * @param latitude
     * @param longitude
     * @param callback
     * @param classType
     */
    public void quickLogin(long userId, String jiguangId, String address, String latitude, String longitude, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("jiguangId", TextUtils.isEmpty(jiguangId) ? "" : jiguangId);
        params.put("address", TextUtils.isEmpty(address) ? "" : address);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        norPost(APPURL.QUICK_LOGIN, params, callback, classType);
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @param callback
     * @param classType
     */
    public void sendCode(String phone, String deviceId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone", phone);
        params.put("deviceId", deviceId);
        norPost(APPURL.SEND_CODE, params, callback, classType);
    }

    /**
     * 绑定
     *
     * @param otherType  登录方式
     * @param otherToken 秘钥串:微信UUID,QQUUID,手机号
     * @param phone
     * @param code
     * @param callback
     * @param classType
     */
    public void userBind(int otherType, String otherToken, String jiguangId, String phone, String code, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("otherType", otherType);
        params.put("otherToken", otherToken);
        params.put("jiguangId", TextUtils.isEmpty(jiguangId) ? "" : jiguangId);
        params.put("code", TextUtils.isEmpty(code) ? "" : code);
        params.put("phone", TextUtils.isEmpty(phone) ? "" : phone);
        norPost(APPURL.USER_BIND, params, callback, classType);
    }

    /**
     * 绑定三方
     *
     * @param otherType
     * @param otherToken
     * @param jiguangId
     * @param phone
     * @param code
     * @param callback
     * @param classType
     */
    public void userBindThird(int otherType, String otherToken, String jiguangId, String phone, String code, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("otherType", otherType);
        params.put("otherToken", otherToken);
        params.put("jiguangId", TextUtils.isEmpty(jiguangId) ? "" : jiguangId);
        params.put("code", TextUtils.isEmpty(code) ? "" : code);
        params.put("phone", TextUtils.isEmpty(phone) ? "" : phone);
        norPost(APPURL.USER_BIND_THIRD, params, callback, classType);
    }

    /**
     * 获取绑定状态
     *
     * @param userId
     * @param callback
     * @param classType
     */
    public void bindState(long userId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        norPost(APPURL.BIND_STATE, params, callback, classType);
    }

    /**
     * 初始化用户信息
     *
     * @param otherType
     * @param otherToken
     * @param phone
     * @param nickName
     * @param headUrl
     * @param age
     * @param sex
     * @param birthDay
     * @param address
     * @param callback
     * @param classType
     */
    public void initUserInfo(int otherType, String otherToken, String phone, String latitude, String longitude, String nickName, String headUrl, int age, String jiguangId, String birthDay, int sex, String address, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("otherType", otherType);
        params.put("otherToken", otherToken);
        params.put("phone", TextUtils.isEmpty(phone) ? "" : phone);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("nickName", TextUtils.isEmpty(nickName) ? "" : nickName);
        params.put("headUrl", TextUtils.isEmpty(headUrl) ? "" : headUrl);
        params.put("age", age);
        params.put("jiguangId", TextUtils.isEmpty(jiguangId) ? "" : jiguangId);
        params.put("birthDay", TextUtils.isEmpty(birthDay) ? "" : birthDay);
        params.put("sex", sex);
        params.put("address", TextUtils.isEmpty(address) ? "" : address);
        norPost(APPURL.INIT_USERINFO, params, callback, classType);
    }

    /**
     * 获取星座列表
     *
     * @param callback
     * @param classType
     */
    public void getConstellation(final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        norPost(APPURL.CONSTELLATION, params, callback, classType);
    }

    /**
     * 附近人列表
     *
     * @param longitude
     * @param latitude
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void nearBy(String longitude, String latitude, int minage, int maxage, long constellationId, int activetime, int sex, String page, String size, String deviceId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        if (minage != 0)
            params.put("minage", minage);
        if (maxage != 0)
            params.put("maxage", maxage);
        if (constellationId != 0)
            params.put("constellationId", constellationId);
        if (activetime != 0)
            params.put("activetime", activetime);
        if (sex != 0)
            params.put("sex", sex);
        params.put("page", page);
        params.put("size", size);
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("termType", 1);//设备类型 1安卓 2ios
        params.put("deviceId", deviceId);
        norPost(APPURL.NEAR_BY, params, callback, classType);
    }

    /**
     * 发布朋友圈
     *
     * @param userId
     * @param title
     * @param fileItemUrl
     * @param text
     * @param callback
     * @param classType
     */

    public void publishCircle(String userId, String title, String fileItemUrl, String text,
                              final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        if (!TextUtils.isEmpty(title))
            params.put("title", title);
        if (!TextUtils.isEmpty(fileItemUrl))
            params.put("fileItemUrl", fileItemUrl);
        if (!TextUtils.isEmpty(text))
            params.put("text", text);
        norPost(APPURL.PUBLISH_CIRCLE, params, callback, classType);
    }

    /**
     * 文件上传
     *
     * @param file
     * @param uploadListener
     */
    public void fileUpload(File file, final UploadListener uploadListener) {
        Map<String, String> params = new HashMap<String, String>();
        Pair<String, File> files = new Pair<String, File>("file", file);
        norPostFile(APPURL.FILE_UPLOAD, files, params, uploadListener);
    }

    /**
     * 动态详情
     *
     * @param circleId
     * @param callback
     * @param classType
     */
    public void circleDetail(long circleId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("circleId", circleId);
        norPost(APPURL.CIRCLE_DETAIL, params, callback, classType);
    }

    /**
     * 我的动态
     *
     * @param userId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void myCircle(long userId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        if (userId != 0)
            params.put("currentUserId", user == null ? 0 : user.getUserId());
        if (userId != 0)
            params.put("userId", userId);
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.MY_CIRCLE, params, callback, classType);
    }

    /**
     * 动态列表
     *
     * @param userId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void circleList(long userId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (userId != 0)
            params.put("userId", userId);
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.CIRCLE_LIST, params, callback, classType);
    }

    /**
     * 未读评论点赞
     *
     * @param userId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void unReadCircle(long userId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (userId != 0)
            params.put("userId", userId);
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.UNREAD_CIRCLE, params, callback, classType);
    }

    /**
     * 点赞列表
     *
     * @param circleId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void praiseList(long circleId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("circleId", circleId);
        if (page != -1)
            params.put("page", page);
        if (size != -1)
            params.put("size", size);
        norPost(APPURL.PRAISE_LIST, params, callback, classType);
    }

    /**
     * 评论列表
     *
     * @param circleId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void commentList(long circleId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("circleId", circleId);
        if (page != -1)
            params.put("page", page);
        if (size != -1)
            params.put("size", size);
        norPost(APPURL.COMMENT_LIST, params, callback, classType);
    }


    /**
     * 评论点赞
     *
     * @param circleId
     * @param userId
     * @param toUserId
     * @param type      1点赞,2评论回复
     * @param content
     * @param callback
     * @param classType
     */
    public void commentPraise(long circleId, long userId, long toUserId, int type, String content,
                              final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("circleId", circleId);
        params.put("userId", userId);
        params.put("toUserId", toUserId);
        params.put("type", type);
        params.put("content", content);
        norPost(APPURL.COMMENT_PRAISE, params, callback, classType);
    }

    /**
     * 删除评论
     *
     * @param circleId
     * @param commentId
     * @param userId
     * @param callback
     * @param classType
     */
    public void deleteComment(int circleId, long commentId, long userId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("circleId", circleId);
        params.put("commentId", commentId);
        params.put("userId", userId);
        norPost(APPURL.DELETE_COMMENT, params, callback, classType);
    }

    /**
     * 删除动态
     *
     * @param circleId
     * @param userId
     * @param callback
     * @param classType
     */
    public void deleteCircle(long circleId, long userId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("circleId", circleId);
        params.put("userId", userId);
        norPost(APPURL.DELETE_CIRCLE, params, callback, classType);
    }

    /**
     * 创建群
     *
     * @param groupName
     * @param callback
     * @param classType
     */
    public void familyCreate(String groupName, String address, final NetCallBack callback,
                             final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        params.put("groupName", groupName);
        if (!TextUtils.isEmpty(address))
            params.put("address", address);
        norPost(APPURL.FAMILY_CREATE, params, callback, classType);
    }

    /**
     * 加载群详情（新 包括成员、工资、群信息）
     *
     * @param groupId
     * @param callback
     * @param classType
     */
    public void familyDetail(long groupId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        norPost(APPURL.FAMILY_DETAIL, params, callback, classType);
    }

    /**
     * 修改群信息
     *
     * @param groupId   必填
     * @param groupName 必填
     * @param details
     * @param headUrl
     * @param joinType  加入群状态 1允许任何人加入，2只能通过群二维码加入，3任何人不能加入
     * @param qrcodeUrl
     * @param sort
     * @param callback
     * @param classType
     */
    public void familyUpdate(long groupId, String groupName, String introduce, String details, String
            headUrl, String joinType, String qrcodeUrl, int sort, final NetCallBack callback,
                             final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        if (!TextUtils.isEmpty(groupName))
            params.put("groupName", groupName);
        if (!TextUtils.isEmpty(introduce))
            params.put("introduce", introduce);
        if (!TextUtils.isEmpty(details))
            params.put("details", details);
        if (!TextUtils.isEmpty(headUrl))
            params.put("headUrl", headUrl);
        if (!TextUtils.isEmpty(joinType))
            params.put("joinType", joinType);
        if (!TextUtils.isEmpty(qrcodeUrl))
            params.put("qrcodeUrl", qrcodeUrl);
        if (sort != -1)
            params.put("sort", sort);
        norPost(APPURL.FAMILY_UPDATE, params, callback, classType);
    }

    /**
     * 群列表
     *
     * @param groupName
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void familyList(String groupName, int page, int size, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupName", groupName);
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.FAMILY_LIST, params, callback, classType);
    }

    /**
     * banner
     *
     * @param callback
     * @param classType
     */
    public void banner(final NetCallBack callback,
                       final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        norPost(APPURL.BANNER, params, callback, classType);
    }

    /**
     * 保存群聊天记录
     *
     * @param groupId
     * @param text
     * @param userType                 （1群主 2管理员 3成员 4游客）
     * @param callback
     * @param classType
     * @param textJson(拓展字段新版本都是用该字段传)
     */
    public void msgSave(String text, long groupId, String textJson, int userType, final NetCallBack callback,
                        final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        params.put("groupId", groupId);
        params.put("text", text);
        params.put("userType", userType);
        params.put("messageExt", textJson);// 新增拓展字段
        norPost(APPURL.MSG_SAVE, params, callback, classType);
    }

    /**
     * 获取群聊天记录
     *
     * @param groupId
     * @param callback
     * @param classType
     */
    public void msgRecord(long groupId, long createTime, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("createTime", createTime);
        if (page != -1)
            params.put("page", page);
        if (size != -1)
            params.put("size", size);
        norPost(APPURL.MSG_RECORD, params, callback, classType);
    }

    /**
     * 群内用户列表加载
     *
     * @param groupId
     * @param callback
     * @param classType
     */
    public void userList(long groupId, final NetCallBack callback,
                         final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        norPost(APPURL.USER_LIST, params, callback, classType);
    }

    /**
     * 关注群
     *
     * @param groupId
     * @param callback
     * @param classType
     */
    public void concernGroup(long groupId, final NetCallBack callback,
                             final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        norPost(APPURL.CONCERN_GROUP, params, callback, classType);
    }


    /**
     * 是否通过申请成为群成员
     *
     * @param groupId
     * @param userId
     * @param issuccess 1同意 0拒绝
     * @param callback
     * @param classType
     */
    public void agreeGroup(long groupId, long userId, int issuccess, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("userId", userId);
        params.put("issuccess", issuccess);
        norPost(APPURL.AGREE_GROUP, params, callback, classType);
    }

    /**
     * 保存上麦记录
     *
     * @param groupId
     * @param userId
     * @param callback
     * @param classType
     */
    public void saveVoice(long groupId, long userId, final NetCallBack callback,
                          final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("userId", userId);
        norPost(APPURL.SAVE_VOICE, params, callback, classType);
    }

    /**
     * 申请成为群成员
     *
     * @param groupId
     * @param callback
     * @param classType
     */
    public void applyGroup(long groupId, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        norPost(APPURL.APPLY_GROUP, params, callback, classType);
    }

    /**
     * 活跃群
     *
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void activeGroup(int page, int size, final NetCallBack callback,
                            final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (page != -1)
            params.put("page", page);
        if (size != -1)
            params.put("size", size);
        norPost(APPURL.ACTIVE_GROUP, params, callback, classType);
    }

    /**
     * 点击进群
     * 返回信息   userstate 0正常 1表示禁言 2踢出    userType 1群主 2管理 3成员 4游客    time 禁言和踢出时剩余的时间
     *
     * @param groupId
     * @param callback
     * @param classType
     */
    public void enterGroup(long groupId, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        params.put("groupId", groupId);
        norPost(APPURL.ENTER_GROUP, params, callback, classType);
    }

    /**
     * 取消关注群
     *
     * @param groupId
     * @param callback
     * @param classType
     */
    public void unConcernGroup(long groupId, final NetCallBack callback,
                               final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        norPost(APPURL.UNCONCERN_GROUP, params, callback, classType);
    }

    /**
     * 用户群列表
     *
     * @param callback
     * @param classType
     */
    public void userGroupList(final NetCallBack callback,
                              final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        norPost(APPURL.USER_GROUP_LIST, params, callback, classType);
    }

    /**
     * 查看家族消息
     *
     * @param callback
     * @param classType
     */
    public void lookGroup(final NetCallBack callback,
                          final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        norPost(APPURL.LOOK_GROUP, params, callback, classType);
    }

    /**
     * 用户浏览历史
     *
     * @param callback
     * @param classType
     */
    public void groupHistory(final NetCallBack callback,
                             final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        if (user != null) {
            params.put("userId", user.getUserId());
        }
        norPost(APPURL.GROUP_HISTORY, params, callback, classType);
    }

    /**
     * 清空历史
     *
     * @param groupIds
     * @param callback
     * @param classType
     */
    public void clearHistory(String groupIds, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("groupIds", groupIds);
        norPost(APPURL.CLEAR_HISTORY, params, callback, classType);
    }

    /**
     * 设置管理员
     *
     * @param userId
     * @param groupId
     * @param userType  （1 群主 2管理员 3成员 4游客）
     * @param callback
     * @param classType
     */
    public void setManager(long userId, long groupId, int userType, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("groupId", groupId);
        params.put("userType", userType);
        norPost(APPURL.SET_MANAGER, params, callback, classType);
    }

    /**
     * 改变用户状态（禁言，踢出）
     *
     * @param userId
     * @param groupId
     * @param userState 用户状态0正常 1禁言 2踢出
     * @param isenable  0解除动作 1进行相应的禁言、踢出动作
     * @param time      秒 表示禁言、踢出多长时间
     * @param callback
     * @param classType
     */
    public void changeUserState(long currentUserId, long userId, long groupId, int userState, int isenable, int time, final NetCallBack callback,
                                final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currentUserId", currentUserId);
        params.put("userId", userId);
        params.put("groupId", groupId);
        params.put("userState", userState);
        params.put("isenable", isenable);
        params.put("time", time);
        norPost(APPURL.CHANGE_USER_STATE, params, callback, classType);
    }

    /**
     * 踢人(将成员降为游客 游客不可踢)
     *
     * @param groupId
     * @param userIds
     * @param callback
     * @param classType
     */
    public void reduceGroup(long groupId, String userIds, final NetCallBack callback,
                            final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("userIds", userIds);
        norPost(APPURL.REDUCE_GROUP, params, callback, classType);
    }

    /**
     * 退出家族
     *
     * @param groupId
     * @param userId
     * @param callback
     * @param classType
     */
    public void exitGroup(long groupId, long userId, final NetCallBack callback,
                          final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("userId", userId);
        norPost(APPURL.EIXT_GROUP, params, callback, classType);
    }

    /**
     * 更新胡言乱语次数
     *
     * @param userId
     * @param callback
     * @param classType
     */
    public void updateConfuse(long userId, final NetCallBack callback,
                              final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        norPost(APPURL.UPDATE_CONFUSE, params, callback, classType);
    }

    /**
     * 在群内，受到禁言或者踢出技能时，请求一次后台
     *
     * @param userId
     * @param groupId
     * @param type      1禁言 2踢出
     * @param callback
     * @param classType
     */
    public void updateSkill(long userId, long groupId, int type, final NetCallBack callback,
                            final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("groupId", groupId);
        params.put("type", type);
        norPost(APPURL.UPDATE_SKILL, params, callback, classType);
    }

    /**
     * 拉取用户技能列表
     *
     * @param callback
     * @param classType
     */
    public void skillList(final NetCallBack callback,
                          final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        norPost(APPURL.SKILL_LIST, params, callback, classType);
    }

    /**
     * 释放技能时实力对比
     *
     * @param toUserId
     * @param skillType
     * @param callback
     * @param classType
     */
    public void skillReleaseInfo(long toUserId, String skillType, final NetCallBack callback,
                                 final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("toUserId", toUserId);
        params.put("skillType", skillType);
        norPost(APPURL.SKILL_RELEASE_INFO, params, callback, classType);
    }

    /**
     * 技能释放
     *
     * @param toUserId
     * @param skillType
     * @param consumePop
     * @param callback
     * @param classType
     */
    public void skillRelease(long toUserId, String skillType, String consumePop, final NetCallBack callback,
                             final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("toUserId", toUserId);
        params.put("skillType", skillType);
        params.put("consumePop", consumePop);
        norPost(APPURL.SKILL_RELEASE, params, callback, classType);
    }

    /**
     * 获取一个要升级技能的信息
     *
     * @param skillType
     * @param callback
     * @param classType
     */
    public void skillUpInfo(String skillType, final NetCallBack callback,
                            final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("skillType", skillType);
        norPost(APPURL.SKILL_UP_INFO, params, callback, classType);
    }

    /**
     * 技能升级
     *
     * @param skillType
     * @param conSumeType
     * @param callback
     * @param classType
     */
    public void skillUp(String skillType, String conSumeType, String deviceId, final NetCallBack callback,
                        final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("skillType", skillType);
        params.put("conSumeType", conSumeType);
        params.put("termType", 1);//设备类型 1安卓 2ios
        params.put("deviceId", deviceId);
        norPost(APPURL.SKILL_UP, params, callback, classType);
    }

    /**
     * 群事件记录
     *
     * @param groupId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void groupEvent(long groupId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (groupId != 0)
            params.put("groupId", groupId);
        if (page != -1)
            params.put("page", page);
        if (size != -1)
            params.put("size", size);
        norPost(APPURL.GROUP_EVENT, params, callback, classType);
    }

    /**
     * 世界事件记录
     *
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void worldEvent(int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (page != -1)
            params.put("page", page);
        if (size != -1)
            params.put("size", size);
        norPost(APPURL.WORLD_EVENT, params, callback, classType);
    }

    /**
     * 个人事件记录
     *
     * @param userId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void myEvent(long userId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        if (page != -1)
            params.put("page", page);
        if (size != -1)
            params.put("size", size);
        norPost(APPURL.MY_EVENT, params, callback, classType);
    }

    /**
     * 家族召唤
     *
     * @param groupId
     * @param userId
     * @param msg
     * @param callback
     * @param classType
     */
    public void familyCall(long groupId, long userId, String msg, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("userId", userId);
        params.put("msg", msg);
        norPost(APPURL.FAMILY_CALL, params, callback, classType);
    }

    /**
     * 召唤boss
     *
     * @param groupId
     * @param userId
     * @param msg
     * @param callback
     * @param classType
     */
    public void familyBoss(long groupId, long userId, String msg, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("userId", userId);
        params.put("msg", msg);
        norPost(APPURL.FAMILY_BOSS, params, callback, classType);
    }

    /**
     * 世界招募
     *
     * @param groupId
     * @param userId
     * @param msg
     * @param callback
     * @param classType
     */
    public void worldCall(long groupId, long userId, String msg, final NetCallBack callback,
                          final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("userId", userId);
        params.put("msg", msg);
        norPost(APPURL.WORLD_CALL, params, callback, classType);
    }

    /**
     * 世界发言
     *
     * @param userId
     * @param msg
     * @param callback
     * @param classType
     */
    public void worldSpeak(long userId, String msg, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("msg", msg);
        norPost(APPURL.WORLD_SPEAK, params, callback, classType);
    }

    /**
     * 送礼
     *
     * @param acceptUserId 接受用户id
     * @param giftId
     * @param giftNumber
     * @param sendUserId   发送用户id
     * @param callback
     * @param classType
     */
    public void sendGift(long acceptUserId, long giftId, int giftNumber, long sendUserId, String deviceId,
                         final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acceptUserId", acceptUserId);
        params.put("giftId", giftId);
        params.put("giftNumber", giftNumber);
        params.put("sendUserId", sendUserId);
        params.put("termType", 1);//设备类型 1安卓 2ios
        params.put("deviceId", deviceId);
        norPost(APPURL.SEND_GIFT, params, callback, classType);
    }

    /**
     * 礼物列表
     *
     * @param giftType  礼物类型 1元宝,2金币,3钻石,默认全查
     * @param page      非必传
     * @param size      非必传
     * @param callback
     * @param classType
     */
    public void giftList(int giftType, int page, int size, final NetCallBack callback,
                         final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        if (giftType != 0)
            params.put("giftType", giftType);
        if (page != -1)
            params.put("page", page);
        if (size != -1)
            params.put("size", size);
        norPost(APPURL.GIFT_LIST, params, callback, classType);
    }

    /**
     * 收到的礼物列表
     *
     * @param acceptUserId
     * @param callback
     * @param classType
     */
    public void myGift(long acceptUserId, int page, int size, final NetCallBack callback,
                       final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acceptUserId", acceptUserId);
        if (page != -1)
            params.put("page", page);
        if (size != -1)
            params.put("size", size);
        norPost(APPURL.MY_GIFT, params, callback, classType);
    }

    /**
     * 申请成为好友
     *
     * @param toUserId  被申请人
     * @param userId    申请人
     * @param callback
     * @param classType
     */
    public void applyUser(long toUserId, long userId, final NetCallBack callback,
                          final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("toUserId", toUserId);
        params.put("userId", userId);
        norPost(APPURL.APPLY_USER, params, callback, classType);
    }

    /**
     * 删除好友
     *
     * @param toUserId
     * @param userId
     * @param callback
     * @param classType
     */
    public void deleteFriend(long toUserId, long userId, final NetCallBack callback,
                             final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("toUserId", toUserId);
        params.put("userId", userId);
        norPost(APPURL.DELETE_FRIEND, params, callback, classType);
    }

    /**
     * 是否通过好友申请
     *
     * @param toUserId
     * @param userId
     * @param isAgree
     * @param callback
     * @param classType
     */
    public void agreeApply(long toUserId, long userId, int isAgree, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("toUserId", toUserId);
        params.put("userId", userId);
        params.put("isAgree", isAgree);
        norPost(APPURL.AGREE_APPLY, params, callback, classType);
    }

    /**
     * 好友列表
     *
     * @param userId
     * @param callback
     * @param classType
     */
    public void friendList(long userId, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        norPost(APPURL.FRIEND_LIST, params, callback, classType);
    }

    /**
     * 查询好友
     *
     * @param searchParam
     * @param callback
     * @param classType
     */
    public void searchUser(String searchParam, final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("searchParam", searchParam);
        norPost(APPURL.SEARCH_USER, params, callback, classType);
    }


    /**
     * 聊天室个人信息
     *
     * @param userId
     * @param callback
     * @param classType
     */
    public void familyUserInfo(long userId, final NetCallBack callback,
                               final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        norPost(APPURL.FAMILY_USER_INFO, params, callback, classType);
    }

    /**
     * 用户中心个人信息
     *
     * @param toUserId
     * @param callback
     * @param classType
     */
    public void userInfo(long toUserId, final NetCallBack callback,
                         final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("toUserId", toUserId);
        norPost(APPURL.USER_INFO, params, callback, classType);
    }

    /**
     * 用户中心粉丝榜
     *
     * @param userId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void userFans(long userId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.USER_FANS, params, callback, classType);
    }

    /**
     * 我的个人信息
     *
     * @param userId
     * @param callback
     * @param classType
     */
    public void mineInfo(long userId, final NetCallBack callback,
                         final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        norPost(APPURL.MINE_INFO, params, callback, classType);
    }

    /**
     * 用户升级
     *
     * @param callback
     * @param classType
     */
    public void userUpdate(final NetCallBack callback,
                           final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        norPost(APPURL.USER_UPDATE, params, callback, classType);
    }

    /**
     * 获取升级信息
     *
     * @param leven
     * @param callback
     * @param classType
     */
    public void upGradeInfo(int leven, final NetCallBack callback,
                            final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("leven", leven);
        norPost(APPURL.UPGRADE_INFO, params, callback, classType);
    }

    /**
     * 修改个人信息
     *
     * @param userId
     * @param nickName
     * @param headUrl
     * @param age
     * @param sex          性别 1男 2女
     * @param height
     * @param weight
     * @param userState    恋爱状态 0未设置1单身2热恋中
     * @param professionId
     * @param label        标签（逗号隔开）
     * @param details      个人签名
     * @param callback
     * @param classType
     */
    public void updateInfo(long userId, String nickName, String headUrl, int age, int sex, double height, double weight, int userState,
                           long professionId, String label, String details, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        if (!TextUtils.isEmpty(nickName))
            params.put("nickName", nickName);
        if (!TextUtils.isEmpty(headUrl))
            params.put("headUrl", headUrl);
        if (age > 0)
            params.put("age", age);
        if (sex > 0)
            params.put("sex", sex);
        if (height > 0)
            params.put("height", height);
        if (weight > 0)
            params.put("weight", weight);
        params.put("userState", userState);
        if (professionId > 0)
            params.put("professionId", professionId);
        if (!TextUtils.isEmpty(label))
            params.put("label", label);
        if (!TextUtils.isEmpty(details))
            params.put("details", details);
        norPost(APPURL.UPDATE_INFO, params, callback, classType);
    }


    /**
     * 修改个人信息
     *
     * @param userId
     * @param examinePic
     * @param unExaminePic
     */
    public void updatePhotos(long userId, String examinePic, String unExaminePic, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("examinePic", examinePic);
        params.put("unExaminePic", unExaminePic);
        norPost(APPURL.UPDATE_PHOTOS, params, callback, classType);
    }

    /**
     * 行业信息
     *
     * @param callback
     * @param classType
     */
    public void workInfo(final NetCallBack callback,
                         final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        norPost(APPURL.WORK_INFO, params, callback, classType);
    }

    /**
     * 用户标签
     *
     * @param callback
     * @param classType
     */
    public void userLabel(final NetCallBack callback,
                          final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        norPost(APPURL.USER_LABEL, params, callback, classType);
    }

    /**
     * 微信下单
     *
     * @param productId
     * @param productNum 商品购买数量 （充值默认就是1）
     * @param clientIp
     * @param userId
     * @param callback
     * @param classType
     */
    public void wxPay(long productId, int productNum, String clientIp, long userId, String deviceId, final NetCallBack callback,
                      final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);
        params.put("productNum", productNum);
        params.put("termType", 1);//设备类型 1安卓 2ios
        params.put("clientIp", clientIp);
        params.put("userId", userId);
        params.put("deviceId", deviceId);
        norPost(APPURL.WX_PAY, params, callback, classType);
    }

    /**
     * 支付宝支付
     *
     * @param productId
     * @param productNum
     * @param userId
     * @param callback
     * @param classType
     */
    public void aliPay(long productId, int productNum, String clientIp, long userId, String deviceId, final NetCallBack callback,
                       final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);
        params.put("productNum", productNum);
        params.put("termType", 1);//设备类型 1安卓 2ios
        params.put("clientIp", clientIp);
        params.put("userId", userId);
        params.put("deviceId", deviceId);
        norPost(APPURL.ALI_PAY, params, callback, classType);
    }

    /**
     * 购买钻石类道具
     *
     * @param productId
     * @param number
     * @param userId
     * @param callback
     * @param classType
     */
    public void buyShop(long productId, int number, long userId, String deviceId, final NetCallBack callback,
                        final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);
        params.put("number", number);
        params.put("userId", userId);
        params.put("termType", 1);//设备类型 1安卓 2ios
        params.put("deviceId", deviceId);
        norPost(APPURL.BUY_SHOP, params, callback, classType);
    }

    /**
     * 充值列表
     *
     * @param callback
     * @param classType
     */
    public void productList(int productType, final NetCallBack callback,
                            final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productType", productType);
        norPost(APPURL.PRODUCT_LIST, params, callback, classType);
    }

    /**
     * 账单
     *
     * @param userId
     * @param type        1收入 2支出
     * @param consumeType 1金币 2钻石 3福宝
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void billList(long userId, int type, int consumeType, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("type", type);
        params.put("consumeType", consumeType);
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.BILL_LIST, params, callback, classType);
    }

    /**
     * 拉黑
     *
     * @param toUserId
     * @param callback
     * @param classType
     */
    public void pullBack(long toUserId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("toUserId", toUserId);
        norPost(APPURL.PULL_BACK, params, callback, classType);
    }

    /**
     * 拉黑列表
     *
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void blackList(int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.BLACK_LIST, params, callback, classType);
    }

    /**
     * 解除拉黑
     *
     * @param toUserId
     * @param callback
     * @param classType
     */
    public void relieveBlack(long toUserId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("toUserId", toUserId);
        norPost(APPURL.RELIEVE_BLACK, params, callback, classType);
    }

    /**
     * 举报
     *
     * @param userId
     * @param toUserId
     * @param title
     * @param content
     * @param files
     * @param callback
     * @param classType
     */
    public void saveReport(long userId, long toUserId, String title, String content, String files, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("toUserId", toUserId);
        params.put("title", title);
        params.put("content", content);
        params.put("files", files);
        norPost(APPURL.SAVE_REPORT, params, callback, classType);
    }

    /**
     * 意见反馈
     *
     * @param userId
     * @param content
     * @param files     图片地址
     * @param callback
     * @param classType
     */
    public void suggestBack(long userId, String content, String files, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("content", content);
        params.put("files", files);
        norPost(APPURL.SUGGEST_BACK, params, callback, classType);
    }

    /**
     * 初始化代理页面信息
     *
     * @param callback
     * @param classType
     */
    public void expandInfo(final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
//        params.put("userId", 80);
        norPost(APPURL.EXPAND_INFO, params, callback, classType);
    }

    /**
     * 给其它用户充值
     *
     * @param toUserId
     * @param fubao
     * @param gold
     * @param diamond
     * @param callback
     * @param classType
     */
    public void rechargeOther(long toUserId, long fubao, long gold, long diamond, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
//        params.put("userId", 80);
        params.put("toUserId", toUserId);
        params.put("fubao", fubao);
        params.put("gold", gold);
        params.put("diamond", diamond);
        norPost(APPURL.RECHARGE_OTHER, params, callback, classType);
    }

    /**
     * 查询支出或收入
     *
     * @param type
     * @param toUserId
     * @param startTime
     * @param endTime
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void findOrder(int type, long toUserId, long startTime, long endTime, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
//        params.put("userId", 80);
        params.put("type", type);
        if (toUserId != 0)
            params.put("toUserId", toUserId);
        if (startTime != 0)
            params.put("startTime", startTime);
        if (endTime != 0)
            params.put("endTime", endTime);
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.FIND_ORDER, params, callback, classType);
    }

    /**
     * 谁查看我
     *
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void lookMe(int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.LOOK_ME, params, callback, classType);
    }

    /**
     * 清空查看我
     *
     * @param ids
     * @param type      1 清空全部 2 清空部分
     * @param callback
     * @param classType
     */
    public void deleteLook(String ids, int type, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("ids", ids);
        params.put("type", type);
        norPost(APPURL.DELETE_LOOK, params, callback, classType);
    }

    /**
     * 加成相关数据
     *
     * @param callback
     * @param classType
     */
    public void addData(final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        norPost(APPURL.ADD_DATA, params, callback, classType);
    }

    /**
     * 分享
     *
     * @param callback
     * @param classType
     */
    public void appShare(final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        norPost(APPURL.APP_SHARE, params, callback, classType);
    }

    /**
     * 个人工资说明
     *
     * @param groupId
     * @param callback
     * @param classType
     */
    public void wageInfo(long groupId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("groupId", groupId);
        norPost(APPURL.WAGE_INFO, params, callback, classType);
    }

    /**
     * 红包获取随机成语
     *
     * @param callback
     * @param classType
     */
    public void getIdioms(final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        norPost(APPURL.RANDOM_CY, params, callback, classType);
    }

    /**
     * 接龙红包验证是否是成语
     *
     * @param callback
     * @param classType
     */
    public void vertifyIdioms(String word, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("word", word);
        norPost(APPURL.CHECK_CY, params, callback, classType);
    }

    /**
     * 接龙红包创建
     *
     * @param callback
     * @param classType
     */
    public void createRedpackage(String groupId, String title, String diamond, String number, String type, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("type", type);
        params.put("number", number);
        params.put("diamond", diamond);
        params.put("title", title);
        params.put("groupId", groupId);
        norPost(APPURL.REDPACKAGE_CREATE, params, callback, classType);
    }

    /**
     * 系统创建接龙红包
     *
     * @param callback
     * @param classType
     */
    public void createSysRedpackage(String groupId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("groupId", groupId);
        norPost(APPURL.SYSREDPACK_CREATE, params, callback, classType);
    }

    /**
     * 红包详情
     *
     * @param callback
     * @param classType
     */
    public void getRedpackageDel(String groupId, String hongbaoId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("hongbaoId", hongbaoId);
        params.put("groupId", groupId);
        norPost(APPURL.GETREDPACKAGE_DEL, params, callback, classType);
    }

    /**
     * 版本升级
     *
     * @param callback
     * @param classType
     */
    public void versionUpdate(final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", 1);//  1安卓 2ios
        norPost(APPURL.VERSION_UPDATE, params, callback, classType);
    }

    /**
     * 话题列表
     *
     * @param userId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void topicList(long userId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (userId != 0)
            params.put("userId", userId);
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.TOPIC_LIST, params, callback, classType);
    }

    /**
     * 话题详情
     *
     * @param topicId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void topicDetaile(String topicId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("topicId", topicId);
        params.put("page", page);
        params.put("size", size);
        Log.e("话题详情入参", params + "");
        norPost(APPURL.TOPIC_DETAILE, params, callback, classType);
    }

    /**
     * 回答详情
     *
     * @param topicId//必须 话题Id
     * @param commentId// 必须 答案Id =0时 表示查询最热的话题 >0时 按正常的Id查询
     * @param callback
     * @param classType
     */
    public void getTpicAnswerDel(String topicId, String commentId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("topicId", topicId);
        params.put("commentId", commentId);
        Log.e("回答详情入参", params + "");
        norPost(APPURL.TOPIC_ANSWER, params, callback, classType);
    }

    /**
     * 话题详情评论列表
     *
     * @param commentId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void topicDetaileEvalue(String commentId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("commentId", commentId);
        params.put("page", page);
        params.put("size", size);
        Log.e("话题评论列表入参", params + "*");
        norPost(APPURL.TOPIC_DETAILE_EVALUE, params, callback, classType);
    }

    /**
     * 话题详情评论
     *
     * @param commentId
     * @param toUserId
     * @param content
     * @param type
     * @param callback
     * @param classType
     */
    public void sbumitTopicEvalue(String commentId, String toUserId, String content, int type, String topicId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("commentId", commentId);
        params.put("toUserId", toUserId);
        params.put("content", content);
        params.put("type", type);
        params.put("topicId", topicId);
        Log.e("提交评论点赞入参", params + "");
        norPost(APPURL.SUBMIT_TOPIC_EVALUE, params, callback, classType);
    }

    /**
     * 创建话题
     *
     * @userId long 必须
     * @topicId long 必须 文章id
     * @commentTitle long 必须 贴主 或者 回复人的id
     * @content String 必须 内容
     */
    public void saveContent(String topicId, String content, String commentTitle, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("topicId", topicId);
        params.put("commentTitle", commentTitle);
        params.put("content", content);
        Log.e("创建话题入参", params + "");
        norPost(APPURL.SAVE_CONTENT, params, callback, classType);
    }

    /**
     * 话题发表评论
     *
     * @userId long 必须
     * @commentId long 必须 文章id
     * @toUserId long 必须 贴主 或者 回复人的id
     * @content String 必须 内容
     * @type int 必须 1点赞 2评论
     */
    public void saveComment(String commentId, String toUserId, String content, String type, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        User user = MyApplication.getInstance().user;
        params.put("userId", user == null ? 0 : user.getUserId());
        params.put("commentId", commentId);
        params.put("toUserId", toUserId);
        params.put("content", content);
        params.put("type", type);
        norPost(APPURL.SAVE_COMMENT, params, callback, classType);
    }

    /**
     * 话题未读评论点赞
     *
     * @param userId
     * @param page
     * @param size
     * @param callback
     * @param classType
     */
    public void unReadCircleTopic(long userId, int page, int size, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (userId != 0)
            params.put("userId", userId);
        params.put("page", page);
        params.put("size", size);
        norPost(APPURL.UNREAD_CIRCLE_TOPIC, params, callback, classType);
    }

    /**
     * 删除话题评论
     *
     * @param commentId
     * @param userId
     * @param callback
     * @param classType
     */
    public void delTopicComment(String commentId, long userId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("id", commentId);
        norPost(APPURL.DELETE_TOPIC_COMMENT, params, callback, classType);
    }

    /**
     * 删除话题评论
     *
     * @param userId
     * @param callback
     * @param classType
     */
    public void addKefu(long userId, final NetCallBack callback, final Class classType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        norPost(APPURL.ADD_KEFU, params, callback, classType);
    }


}
