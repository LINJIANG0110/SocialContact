package com.qianyu.communicate.bukaSdk;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.bukaSdk.adapter.ContentAdapter;
import com.qianyu.communicate.bukaSdk.adapter.UserAdapter;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.EnterGroup;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.GiftModel;
import com.qianyu.communicate.entity.MsgRecord;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.SpUtil;
import com.qianyu.communicate.utils.TimeUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.TimeUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.SurfaceViewRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tv.buka.sdk.BukaSDKManager;
import tv.buka.sdk.entity.Chat;
import tv.buka.sdk.entity.Num;
import tv.buka.sdk.entity.Room;
import tv.buka.sdk.entity.Rpc;
import tv.buka.sdk.entity.Status;
import tv.buka.sdk.entity.Stream;
import tv.buka.sdk.entity.User;
import tv.buka.sdk.listener.ChatListener;
import tv.buka.sdk.listener.ConnectListener;
import tv.buka.sdk.listener.ReceiptListener;
import tv.buka.sdk.listener.RpcListener;
import tv.buka.sdk.listener.StatusListener;
import tv.buka.sdk.listener.UserListener;
import tv.buka.sdk.utils.DeviceUtils;
import tv.buka.sdk.utils.JsonUtils;

import static com.qianyu.communicate.activity.FamilyRoomActivity.firstMikeUser;
import static com.qianyu.communicate.activity.FamilyRoomActivity.secondMikeUser;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class BukaHelper_ {

    private static BukaHelper_ sInstance;
    private Activity mContext;
    private ContentAdapter contentAdapter;
    private UserAdapter userAdapter;
    private ListView contentListView;
    public UserBean userBean;
    private List<Long> mMediaIds;
    private int mMediaIdIndex = 0;
    private int mCameraId = 1;
    public Stream mPublishStreamStatus;
    private long aid = 0;
    private long vid = 0;

    private BukaHelper_(Activity context) {
        this.mContext = context;
    }

    public static synchronized BukaHelper_ getInstance(Activity context) {
        if (sInstance == null) {
            sInstance = new BukaHelper_(context);
        }
        return sInstance;
    }

    //初始化buksSdk及需要的views
    public void initBukaViews(final UserBean userBean, final EnterGroup enterGroup, ListView contentListView, ContentAdapter contentAdapter, final UserAdapter userAdapter) {
        this.userBean = userBean;
        this.contentListView = contentListView;
        this.contentAdapter = contentAdapter;
        this.userAdapter = userAdapter;
        initBukaSdk();
        NetUtils.getInstance().msgRecord(Long.parseLong(userBean.getGroupId()), System.currentTimeMillis(), -1, -1, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                MsgRecord msgRecord = (MsgRecord) model.getModel();
                if (msgRecord != null) {
                    List<UserBean> list = msgRecord.getContent();
                    if (list != null && list.size() > 0) {
                        addMsg(list);
                    }
                }
                if (enterGroup != null && !TextUtils.isEmpty(enterGroup.getIntroduce()) && userBean.getUserType() > 1 && userBean.getUserType() < 4) {
                    String introduce = SpUtil.getStringSP(mContext, "welcome" + userBean.getGroupId(), "");
                    String time = SpUtil.getStringSP(mContext, "times" + userBean.getGroupId(), "");
                    int between = 0;
                    if (!TextUtils.isEmpty(time)) {
                        between = TimeUtils.daysBetween(Long.parseLong(time), System.currentTimeMillis());
                    }
                    if (TextUtils.isEmpty(introduce) || !TextUtils.equals(introduce, enterGroup.getIntroduce()) || between >= 1) {
                        SpUtil.saveStringToSP(mContext, "welcome" + userBean.getGroupId(), enterGroup.getIntroduce());
                        SpUtil.saveStringToSP(mContext, "times" + userBean.getGroupId(), System.currentTimeMillis() + "");
                        UserBean ownerInfo = enterGroup.getGroupOwnerInfo();
                        ownerInfo.setText("@" + userBean.getNickName() + " " + enterGroup.getIntroduce());
                        addLog(ownerInfo);
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                if (enterGroup != null && !TextUtils.isEmpty(enterGroup.getIntroduce()) && userBean.getUserType() > 1 && userBean.getUserType() < 4) {
                    String introduce = SpUtil.getStringSP(mContext, "welcome" + userBean.getGroupId(), "");
                    String time = SpUtil.getStringSP(mContext, "times" + userBean.getGroupId(), "");
                    int between = 0;
                    if (!TextUtils.isEmpty(time)) {
                        between = TimeUtils.daysBetween(Long.parseLong(time), System.currentTimeMillis());
                    }
                    if (TextUtils.isEmpty(introduce) || !TextUtils.equals(introduce, enterGroup.getIntroduce()) || between >= 1) {
                        SpUtil.saveStringToSP(mContext, "welcome" + userBean.getGroupId(), enterGroup.getIntroduce());
                        SpUtil.saveStringToSP(mContext, "times" + userBean.getGroupId(), System.currentTimeMillis() + "");
                        UserBean ownerInfo = enterGroup.getGroupOwnerInfo();
                        ownerInfo.setText("@" + userBean.getNickName() + " " + enterGroup.getIntroduce());
                        addLog(ownerInfo);
                    }
                }
            }
        }, MsgRecord.class);
        BukaSDKManager.getMediaManager().loudSpeaker(mContext);
        try {
            registerHeadsetPlugReceiver();
        } catch (Exception e) {
            AppLog.e("============registerHeadsetPlugReceiver============" + e.getMessage());
        }
    }

    private void initBukaSdk() {
        this.initBuka();
//        this.initAve();
        BukaSDKManager.getConnectManager().connect(userBean.getUserId() == 0 ? DeviceUtils.getDeviceId(mContext) : userBean.getUserId() + "", "",
                new ReceiptListener() {
                    @Override
                    public void onSuccess(Object o) {
                        // TODO Auto-generated method stub
                        login();
                        AppLog.e("==========getDeviceId==========" + DeviceUtils.getDeviceId(mContext));
                    }

                    @Override
                    public void onError(Object o) {
                        // TODO Auto-generated method stub
                        onLoginListener.onStatusListener(0, "连接聊天室失败,请重新进入");
                    }
                });
    }

    private void initBuka() {
//        BukaSDK.init("f6f4645d5d08d19498c54142771e8260",
//                mContext, BukaSDKVersion.BukaSDKVersion3);
        BukaSDKManager.getConnectManager().addListener(connectListener);
        BukaSDKManager.getUserManager().addListener(userListener);
        BukaSDKManager.getChatManager().addListener(chatListener);
        BukaSDKManager.getRpcManager().addListener(rpcListener);
        BukaSDKManager.getStatusManager().addListener(statusListener);
//        BukaSDK.start();
    }


    //登录bukaSdk
    private void login() {
        BukaSDKManager.getUserManager().login(
                userBean.getGroupId(),
                JsonUtils.toJson(userBean),
                new ReceiptListener() {

                    @Override
                    public void onSuccess(Object o) {
                        onLoginListener.onStatusListener(2, "登录聊天室成功...");
                        // TODO Auto-generated method
                        // stub
                        AppLog.e("当前房间人数"
                                + BukaSDKManager
                                .getUserManager()
                                .getUserArr()
                                .size());
//                        initTest();
                        play();
                        rpc(null, Constant.RPC_RIDE_IN, JSON.toJSONString(userBean));
                    }

                    @Override
                    public void onError(Object o) {
                        onLoginListener.onStatusListener(1, "登录聊天室失败,请重新进入");
                    }
                });
    }

    //获取当前用户的信息（用户属性实时更新的）
    public UserBean getCurrentUser() {
        try {
            BukaSDKManager.getUserManager().getSelfUser(new ReceiptListener<User>() {
                @Override
                public void onSuccess(User user) {
                    userBean = new UserBean(user.getUser_extend());
                }

                @Override
                public void onError(Object o) {
                    userBean = null;
                }
            });
            return userBean;
        } catch (Exception e) {
            AppLog.e("============getCurrentUser==========" + e.getMessage());
        }
        return userBean;
    }

    private UserBean sessionUser;

    public UserBean getSessionUser(String seesion_id) {
        BukaSDKManager.getUserManager().getUser(seesion_id, new ReceiptListener<User>() {
            @Override
            public void onSuccess(User user) {
                sessionUser = new UserBean(user.getUser_extend());
            }

            @Override
            public void onError(Object o) {
                sessionUser = null;
            }
        });
        return sessionUser;
    }

    //推流
    public void publish() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("room_id", userBean.getGroupId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BukaSDKManager.getMediaManager().startPulish(2, mCameraId, 0, false, true, "", 640, 360, 1000, 30, new ReceiptListener<Stream>() {
            //        BukaSDKManager.getMediaManager().startPulish(2, mCameraId, mCameraId, false, true, jsonObject.toString(), 180, 320, 500, 15, new ReceiptListener<Stream>() {
            @Override
            public void onSuccess(Stream status) {
                AppLog.e("推流成功");
                mPublishStreamStatus = status;
                aid = status.getAid();
                vid = status.getVid();
                BukaSDKManager.getMediaManager().loudSpeaker(mContext);
                NetUtils.getInstance().saveVoice(Long.parseLong(userBean.getGroupId()), userBean.getUserId(), new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {

                    }

                    @Override
                    public void onFail(String code, String msg, String result) {

                    }
                }, null);
            }

            @Override
            public void onError(Object o) {
                AppLog.e("推流失败");
            }
        });
    }

    public void stopPublish() {
        BukaSDKManager.getMediaManager().stopPublish(new ReceiptListener<SurfaceViewRenderer>() {
            @Override
            public void onSuccess(SurfaceViewRenderer renderer) {
                AppLog.e("停止推流");
                mPublishStreamStatus = null;
            }

            @Override
            public void onError(Object o) {
                AppLog.e("停止推流失败");
            }
        });
    }

    public void play() {
        final List<Status> statusList = BukaSDKManager.getStatusManager().getStatusArr(Constant.STATUS_TAG);
        AppLog.e("=========play000=========");
        if (statusList != null && statusList.size() > 0) {
            for (int i = 0; i < statusList.size(); i++) {
                final Status status = statusList.get(i);
                try {
                    final JSONObject extend = new JSONObject(status.getStatus_extend());
                    JSONObject stream = new JSONObject(extend.getString(Constant.KEY));
                    final long aid = stream.getLong(Constant.AID);
                    final long vid = stream.getLong(Constant.VID);
                    AppLog.e("=========play111=========" + status.getStatus_extend());
                    onStatusListener.onStatusListener(1, status);
                    if (getSessionUser(status.getSession_id()).getUserId() != getCurrentUser().getUserId()) {
                        AppLog.e("=========play2222=========");
                        BukaSDKManager.getMediaManager().startPlay(aid, vid, new ReceiptListener<SurfaceViewRenderer>() {
                            @Override
                            public void onSuccess(SurfaceViewRenderer renderer) {
                                AppLog.e("=========play333=========");
                            }

                            @Override
                            public void onError(Object o) {
                                AppLog.e("拉流失败");
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void play_(final Status status, boolean hasAudio) {
        try {
            JSONObject extend = new JSONObject(status.getStatus_extend());
            JSONObject stream = new JSONObject(extend.getString(Constant.KEY));
            long aid = stream.getLong(Constant.AID);
            long vid = stream.getLong(Constant.VID);
            AppLog.e(aid + "=========stopPlay_=========" + vid);
            onStatusListener.onStatusListener(1, status);
            if (getSessionUser(status.getSession_id()).getUserId() != getCurrentUser().getUserId()) {
                BukaSDKManager.getMediaManager().startPlay(aid, vid, false, hasAudio, new ReceiptListener<SurfaceViewRenderer>() {
                    @Override
                    public void onSuccess(SurfaceViewRenderer renderer) {
                        AppLog.e("拉流成功");
                    }

                    @Override
                    public void onError(Object o) {
                        AppLog.e("拉流失败");
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        final List<Status> statusList = BukaSDKManager.getStatusManager().getStatusArr(Constant.STATUS_TAG);
        AppLog.e("=========stopPlay000=========" + (statusList != null ? statusList.size() : 0));
        if (statusList != null && statusList.size() > 0) {
            for (int i = 0; i < statusList.size(); i++) {
                Status status = statusList.get(i);
                try {
                    JSONObject extend = new JSONObject(status.getStatus_extend());
                    JSONObject stream = new JSONObject(extend.getString(Constant.KEY));
                    long aid = stream.getLong(Constant.AID);
                    long vid = stream.getLong(Constant.VID);
                    AppLog.e("=========stopPlay111=========" + statusList.size());
                    AppLog.e(aid + "=========stopPlay222=========" + vid);
                    onStatusListener.onStatusListener(2, status);
                    BukaSDKManager.getMediaManager().stopPlay(aid, vid, new ReceiptListener<SurfaceViewRenderer>() {
                        @Override
                        public void onSuccess(SurfaceViewRenderer renderer) {
                            AppLog.e("停止拉流");
                        }

                        @Override
                        public void onError(Object o) {
                            AppLog.e("停止拉流失败");
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopPlay_(final Status status) {
        try {
            JSONObject extend = new JSONObject(status.getStatus_extend());
            JSONObject stream = new JSONObject(extend.getString(Constant.KEY));
            long aid = stream.getLong(Constant.AID);
            long vid = stream.getLong(Constant.VID);
            AppLog.e(aid + "=========stopPlay_=========" + vid);
            onStatusListener.onStatusListener(2, status);
            BukaSDKManager.getMediaManager().stopPlay(aid, vid, new ReceiptListener<SurfaceViewRenderer>() {
                @Override
                public void onSuccess(SurfaceViewRenderer renderer) {
                    AppLog.e("停止拉流");
                }

                @Override
                public void onError(Object o) {
                    AppLog.e("停止拉流失败");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //退出登录
    public void logout() {
        BukaSDKManager.getUserManager().logout(new ReceiptListener() {
            @Override
            public void onSuccess(Object o) {
                clearLog();
                AppLog.e("退出登录");
                BukaSDKManager.getRpcManager().removeListener(rpcListener);
                BukaSDKManager.getStatusManager().removeListener(statusListener);
                BukaSDKManager.getUserManager().removeListener(userListener);
                BukaSDKManager.getChatManager().removeListener(chatListener);
                BukaSDKManager.getConnectManager().removeListener(connectListener);
                BukaSDKManager.getConnectManager().disconnect();
            }

            @Override
            public void onError(Object o) {
                // TODO Auto-generated method stub
                AppLog.e("退出登录失败");
            }
        });
    }

    //聊天sessionId传null则为群聊，否则单聊
    public void chat(final String sessionId, String content) {
        ReceiptListener listener = new ReceiptListener() {
            @Override
            public void onSuccess(Object o) {
                // TODO Auto-generated
                // method
                // stub
            }

            @Override
            public void onError(Object o) {
                // TODO Auto-generated
                // method
                // stub
            }
        };
        if (sessionId == null) {
            BukaSDKManager.getChatManager()
                    .sendBroadcastChat(
                            content,
                            listener);
        } else {
            BukaSDKManager.getChatManager()
                    .sendUnicastChat(
                            content,
                            sessionId, listener);
        }
    }

    //聊天sessionId传null则为群聊，否则单聊
    public void rpc(final String sessionId, long type, String content) {
        ReceiptListener listener = new ReceiptListener() {

            @Override
            public void onSuccess(Object o) {
                // TODO Auto-generated
                // method
                // stub
            }

            @Override
            public void onError(Object o) {
                // TODO Auto-generated
                // method
                // stub
//                ToastUtil.shortShowToast("操作失败");
            }
        };
        if (sessionId == null) {
            BukaSDKManager
                    .getRpcManager()
                    .sendBroadcastRpc(
                            content,
                            type,
                            0,
                            listener);
        } else {
            BukaSDKManager.getRpcManager()
                    .sendUnicastRpc(
                            content,
                            type, 0, sessionId, listener);
        }
    }

    //修改用户信息
    public void updateUser(UserBean userBean) {
        BukaSDKManager.getUserManager().update(
                JsonUtils.toJson(userBean),
                new ReceiptListener() {

                    @Override
                    public void onSuccess(Object o) {
                        // TODO Auto-generated method
                        // stub
                    }

                    @Override
                    public void onError(Object o) {
                        // TODO Auto-generated method
                        // stub
                    }
                });
    }

    public void exit() {
        stopPlay();
        stopPublish();
        BukaSDKManager.getRpcManager().sendBroadcastRpc("", Constant.RPC_USER_OUT, 0);
//        if (firstMikeUser != null && firstMikeUser.getUserId() == userBean.getUserId()) {
//            BukaSDKManager.getRpcManager().sendBroadcastRpc(JSON.toJSONString(userBean), Constant.RPC_END_FIRST_MIKE, 0);
//        }
//        if (secondMikeUser != null && secondMikeUser.getUserId() == userBean.getUserId()) {
//            BukaSDKManager.getRpcManager().sendBroadcastRpc(JSON.toJSONString(userBean), Constant.RPC_END_SECOND_MIKE, 0);
//        }
        logout();
//        BukaSDKManager.getRpcManager().removeListener(rpcListener);
//        BukaSDKManager.getStatusManager().removeListener(statusListener);
//        BukaSDKManager.getUserManager().removeListener(userListener);
//        BukaSDKManager.getChatManager().removeListener(chatListener);
//        BukaSDKManager.getConnectManager().removeListener(connectListener);
        if (headsetPlugReceiver != null) {
            try {
                mContext.unregisterReceiver(headsetPlugReceiver);
            } catch (Exception e) {
                AppLog.e("=========headsetPlugReceiver==========" + e.getMessage());
            }
        }
    }

    private void addLog(UserBean userBean) {
        contentAdapter.getmList().push(userBean);
        if (contentAdapter.getmList().size() > 1000) {
            contentAdapter.getmList().poll();
        }
        contentAdapter.notifyDataSetChanged();
        contentListView.setSelection(contentAdapter.getmList().size() - 1);
    }

    private void addMsg(List<UserBean> list) {
        contentAdapter.getmList().addAll(list);
        if (contentAdapter.getmList().size() > 1000) {
            contentAdapter.getmList().poll();
        }
        contentAdapter.notifyDataSetChanged();
        contentListView.setSelection(contentAdapter.getmList().size() - 1);
    }

    public void clearLog() {
        contentAdapter.getmList().clear();
        contentAdapter.notifyDataSetChanged();
    }

    ConnectListener connectListener = new ConnectListener() {

        @Override
        public void onSessionPackageLost() {
            // TODO Auto-generated method stub
            AppLog.e("丢包，正在离线补包（暂未实现）");
        }

        @Override
        public void onSessionOff() {
            // TODO Auto-generated method stub
            AppLog.e("onSessionOff");
            if (onLoginListener != null) {
                onLoginListener.onStatusListener(3, "您已断开连接,请重新进入");
            }
        }

        @Override
        public void onSessionDisconnected() {
            // TODO Auto-generated method stub
            AppLog.e("onSessionDisconnected");
//            if (onLoginListener != null) {
//                onLoginListener.onStatusListener(3, "您已断开连接,请重新进入");
//            }
        }

        @Override
        public void onSessionConnected() {
            // TODO Auto-generated method stub
            AppLog.e("onSessionConnected");
            if (onLoginListener != null) {
                onLoginListener.onStatusListener(4, "重连中...");
            }
        }

        @Override
        public void onServerArrChanged() {
            // TODO Auto-generated method stub
            if (!BukaSDKManager.getConnectManager().isConnect()
                    && !BukaSDKManager.getConnectManager().isConnecting()) {
                AppLog.e("未连接成功222");
            }
        }
    };


    UserListener userListener = new UserListener() {

        @Override
        public void onUserIn(User user) {
            // TODO Auto-generated method stub
            AppLog.e("用户" + new UserBean(user.getUser_extend()).getNickName()
                    + "进入房间");
            onNumChangeListener.onNumListener(1, 1);
        }

        @Override
        public void onUserChanged(User oldUser, User user) {
            // TODO Auto-generated method stub
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    userAdapter.setmList();
                }
            });
            AppLog.e("用户"
                    + new UserBean(oldUser.getUser_extend()).getNickName()
                    + "将昵称修改成"
                    + new UserBean(user.getUser_extend()).getNickName());
        }

        @Override
        public void onUserNumChanged(Num num) {
            // TODO Auto-generated method stub
            //房间人数
            //==========dyj=========
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    userAdapter.setmList();
                }
            });
            //==========dyj=========
            AppLog.e("房间人数变成" + num.getValue());
            onNumChangeListener.onNumListener(3, num.getValue());
        }

        @Override
        public void onUserOut(User user) {
            // TODO Auto-generated method stub
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    userAdapter.setmList();
                }
            });
            AppLog.e("用户" + new UserBean(user.getUser_extend()).getNickName()
                    + "退出房间");
        }

        @Override
        public void onUserDisconnect(User user) {
            // TODO Auto-generated method stub
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    userAdapter.setmList();
                }
            });
            AppLog.e("用户" + new UserBean(user.getUser_extend()).getNickName()
                    + "意外断线，暂离房间");
        }

        @Override
        public void onSelfDisconnect() {
            // TODO Auto-generated method stub
            AppLog.e("您已断线");
        }

        @Override
        public void onSelfConnect(Room room) {
            AppLog.e("您已重连成功");
        }

    };


    private List<UserBean> noSeeIds = new ArrayList<>();

    public List<UserBean> getNoSeeIds() {
        return noSeeIds;
    }

    public void setNoSeeIds(List<UserBean> noSeeIds) {
        this.noSeeIds = noSeeIds;
    }

    public static boolean isGoodJson(String json) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(json);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
//        try {
//            new Gson().fromJson(json, Object.class);
//            return true;
//        } catch(JsonSyntaxException ex) {
//            return false;
//        }
    }

    ChatListener chatListener = new ChatListener() {

        @Override
        public void onChatReceive(final Chat chat) {
            // TODO Auto-generated method stub
            BukaSDKManager.getUserManager().getUser(
                    chat.getSend_session_id(), new ReceiptListener<User>() {
                        @Override
                        public void onSuccess(User user) {
                            if (user != null) {
                                UserBean userBean = new UserBean(user.getUser_extend());
                                for (int i = 0; i < noSeeIds.size(); i++) {
                                    if (userBean.getUserId() == noSeeIds.get(i).getUserId()) {
                                        return;
                                    }
                                }
                                Log.e("布卡sdk监听消息", chat.getMessage());
                                userBean.setText(chat.getMessage());
                                userBean.setMstype(0);
                                if (isGoodJson(chat.getMessage()) == true) {
                                    userBean.setMessageExt(chat.getMessage());
                                }
                                if (chat.getReceive_session_id().length() > 0) {
                                    //单发
                                } else {
                                    //群发
                                }
                                AppLog.e("========chatListener========" + userBean.toString());
                                addLog(userBean);
                            } else {
                                AppLog.e("收到消息，但未找到此人信息");
                            }
                        }

                        @Override
                        public void onError(Object o) {

                        }
                    });

        }
    };

    RpcListener rpcListener = new RpcListener() {

        @Override
        public void onRpcReceive(final Rpc rpc) {
            // TODO Auto-generated method stub
            switch ((int) rpc.getType()) {
                case Constant.RPC_GIFT://礼物
                    //rpc.getSend_session_id()  送礼物的人的seesion_id
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getSend_session_id(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (user != null) {
                                        UserBean userBean = new UserBean(user.getUser_extend());
                                        if (!TextUtils.isEmpty(rpc.getMessage())) {
                                            GiftModel giftModel = JsonUtils.getBean(rpc.getMessage(), GiftModel.class);
                                            userBean.setMstype(0);
                                            userBean.setText("送给主播" + giftModel.getGiftCount() + "个" + giftModel.getGiftName());
                                            addLog(userBean);
                                        }
                                        AppLog.e("========rpcListener111========" + userBean.toString());
                                        onRpcListener.onRpcListener(user, rpc);
                                    } else {
                                        AppLog.e("收到PRC，但未找到此人信息");
                                    }
                                }

                                @Override
                                public void onError(Object o) {

                                }
                            });
                    break;
                case Constant.RPC_BEGIN_FIRST_MIKE:
                    if (!TextUtils.isEmpty(rpc.getMessage())) {
                        BukaSDKManager.getUserManager().getUser(
                                rpc.getSend_session_id(), new ReceiptListener<User>() {
                                    @Override
                                    public void onSuccess(User user) {
                                        UserBean manager = new UserBean(user.getUser_extend());
                                        UserBean userBean = JsonUtils.getBean(rpc.getMessage(), UserBean.class);
                                        userBean.setMike(1);
                                        onRpcListener.onRpcListener_(manager, userBean, rpc);
                                    }

                                    @Override
                                    public void onError(Object o) {

                                    }
                                });
                    }
                    break;
                case Constant.RPC_RIDE_IN:
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getSend_session_id(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    UserBean manager = new UserBean(user.getUser_extend());
                                    onRpcListener.onRpcListener_(manager, null, rpc);
                                }

                                @Override
                                public void onError(Object o) {

                                }
                            });
                    break;
                case Constant.RPC_BEGIN_SECOND_MIKE:
                    if (!TextUtils.isEmpty(rpc.getMessage())) {
                        BukaSDKManager.getUserManager().getUser(
                                rpc.getSend_session_id(), new ReceiptListener<User>() {
                                    @Override
                                    public void onSuccess(User user) {
                                        UserBean manager = new UserBean(user.getUser_extend());
                                        if (!TextUtils.isEmpty(rpc.getMessage())) {
                                            UserBean userBean = JsonUtils.getBean(rpc.getMessage(), UserBean.class);
                                            userBean.setMike(2);
                                            onRpcListener.onRpcListener_(manager, userBean, rpc);
                                        }
                                    }

                                    @Override
                                    public void onError(Object o) {

                                    }
                                });
                    }
                    break;
                case Constant.RPC_END_FIRST_MIKE:
                    if (!TextUtils.isEmpty(rpc.getMessage())) {
                        UserBean userBean = JsonUtils.getBean(rpc.getMessage(), UserBean.class);
                        userBean.setMike(3);
                        com.qianyu.communicate.entity.User currentUser = MyApplication.getInstance().user;
                        if (currentUser != null) {
                            if (currentUser.getUserId() == userBean.getUserId()) {
                                updateUser(userBean);
                                stopPublish();
                            }
                        }
                    }
                    break;
                case Constant.RPC_END_SECOND_MIKE:
                    if (!TextUtils.isEmpty(rpc.getMessage())) {
                        UserBean userBean = JsonUtils.getBean(rpc.getMessage(), UserBean.class);
                        userBean.setMike(4);
                        com.qianyu.communicate.entity.User currentUser = MyApplication.getInstance().user;
                        if (currentUser != null) {
                            if (currentUser.getUserId() == userBean.getUserId()) {
                                updateUser(userBean);
                                stopPublish();
                            }
                        }
                    }
                    break;
                case Constant.RPC_REJECT_MIKE:
                    if (!TextUtils.isEmpty(rpc.getMessage())) {
                        BukaSDKManager.getUserManager().getUser(
                                rpc.getSend_session_id(), new ReceiptListener<User>() {
                                    @Override
                                    public void onSuccess(User user) {
                                        UserBean manager = new UserBean(user.getUser_extend());
                                        UserBean userBean = JsonUtils.getBean(rpc.getMessage(), UserBean.class);
                                        onRpcListener.onRpcListener_(manager, userBean, rpc);
                                    }

                                    @Override
                                    public void onError(Object o) {

                                    }
                                });
                    }
                    break;
                default://禁言、踢人、设管理员
                    AppLog.e("========rpc=======" + rpc.getMessage());
                    //rpc.getMessage()  被（解）禁言等的人的信息
                    if (!TextUtils.isEmpty(rpc.getMessage())) {
                        BukaSDKManager.getUserManager().getUser(
                                rpc.getSend_session_id(), new ReceiptListener<User>() {
                                    @Override
                                    public void onSuccess(User user) {
                                        if (user != null) {
                                            UserBean manager = new UserBean(user.getUser_extend());
                                            UserBean userBean = JsonUtils.getBean(rpc.getMessage(), UserBean.class);
                                            switch ((int) rpc.getType()) {
                                                case Constant.RPC_FORBID_SPEAK:
                                                    userBean.setMstype(1);
                                                    break;
                                                case Constant.RPC_ALLOW_SPEAK:
                                                    userBean.setMstype(2);
                                                    break;
                                                case Constant.RPC_FORBID_IN:
                                                    userBean.setMstype(3);
                                                    break;
                                                case Constant.RPC_ALLOW_IN:
                                                    userBean.setMstype(4);
                                                    break;
                                                case Constant.RPC_SET_MANAGER:
                                                    userBean.setUserType(2);
                                                    userBean.setMstype(5);
                                                    break;
                                                case Constant.RPC_TICK_SB:
                                                    userBean.setUserType(5);
                                                    userBean.setMstype(6);
                                                    break;
                                            }
                                            com.qianyu.communicate.entity.User currentUser = MyApplication.getInstance().user;
                                            if (currentUser != null) {
                                                if (currentUser.getUserId() == userBean.getUserId()) {
                                                    updateUser(userBean);
                                                }
                                            }
                                            addLog(userBean);
                                            onRpcListener.onRpcListener_(manager, userBean, rpc);
                                        }
                                    }

                                    @Override
                                    public void onError(Object o) {

                                    }
                                });
                    }
                    break;
            }
        }
    };

    StatusListener statusListener = new StatusListener() {

        @Override
        public void onStatusDelete(Status status) {
            // TODO Auto-generated method stub
            AppLog.e(status.getSession_id() + "==========状态删除========" + status.getStatus_extend());
            stopPlay_(status);
        }

        @Override
        public void onStatusAdd(Status status) {
            // TODO Auto-generated method stub
            AppLog.e(status.getSession_id() + "==========状态添加111========" + status.getStatus_extend());
            AppLog.e("==========状态添加222========" + status.getTag());
            play_(status, true);
        }

        @Override
        public void onStatusChanged(Status status, Status status1) {

        }
    };

    private OnNumChangeListener onNumChangeListener;

    public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
    }

    public interface OnNumChangeListener {
        void onNumListener(int type, long num);
    }

    private OnRpcListener onRpcListener;

    public void setOnRpcListener(OnRpcListener onRpcListener) {
        this.onRpcListener = onRpcListener;
    }

    public interface OnRpcListener {
        void onRpcListener(User user, Rpc rpc);

        void onRpcListener_(UserBean manager, UserBean userBean, Rpc rpc);
    }

    private OnStatusListener onStatusListener;

    public void setOnStatusListener(OnStatusListener onStatusListener) {
        this.onStatusListener = onStatusListener;
    }

    public interface OnStatusListener {
        void onStatusListener(int type, Status status);
    }

    private OnLoginListener onLoginListener;

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    public interface OnLoginListener {
        void onStatusListener(int type, String str);
    }


    private void registerHeadsetPlugReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        mContext.registerReceiver(headsetPlugReceiver, intentFilter);

        // for bluetooth headset connection receiver
        IntentFilter bluetoothFilter = new IntentFilter(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
        mContext.registerReceiver(headsetPlugReceiver, bluetoothFilter);
    }

    private BroadcastReceiver headsetPlugReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (BluetoothProfile.STATE_DISCONNECTED == adapter.getProfileConnectionState(BluetoothProfile.HEADSET)) {
                    //Bluetooth headset is now disconnected
                    AppLog.e("============loudSpeaker111=============");
                    BukaSDKManager.getMediaManager().loudSpeaker(mContext);
                } else {
                    AppLog.e("============microSpeaker111=============");
                    BukaSDKManager.getMediaManager().microSpeaker(mContext);
                }
            } else if ("android.intent.action.HEADSET_PLUG".equals(action)) {
                if (intent.hasExtra("state")) {
                    if (intent.getIntExtra("state", 0) == 0) {
                        AppLog.e("============loudSpeaker222=============");
                        BukaSDKManager.getMediaManager().loudSpeaker(mContext);
                    } else {
                        AppLog.e("============microSpeaker222=============");
                        BukaSDKManager.getMediaManager().microSpeaker(mContext);
                    }
                }
            }
        }
    };

}
