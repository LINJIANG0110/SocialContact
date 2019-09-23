package com.qianyu.communicate.bukaSdk;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bkrtc_sdk.bkrtc_impl;
import com.qianyu.communicate.bukaSdk.adapter.ContentAdapter;
import com.qianyu.communicate.bukaSdk.adapter.UserAdapter;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.GiftModel;
import com.qianyu.communicate.entity.PraiseModel;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.PixelUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.SurfaceViewRenderer;

import java.util.List;

import tv.buka.sdk.BukaSDK;
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
import tv.buka.sdk.listener.NetWorkListener;
import tv.buka.sdk.listener.ReceiptListener;
import tv.buka.sdk.listener.RpcListener;
import tv.buka.sdk.listener.StatusListener;
import tv.buka.sdk.listener.UserListener;
import tv.buka.sdk.utils.DeviceUtils;
import tv.buka.sdk.utils.HttpUtils;
import tv.buka.sdk.utils.JsonUtils;
import tv.buka.sdk.utils.ResponseUtils;
import tv.buka.sdk.v3.config.Url;

import static android.R.attr.id;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class BukaHelper {

    private static BukaHelper sInstance;
    private Activity mContext;
    private ContentAdapter contentAdapter;
    private UserAdapter userAdapter;
    private ListView contentListView;
    public UserBean userBean;
    private List<Long> mMediaIds;
    private int mMediaIdIndex = 0;
    private int mCameraId = 1;
    private SurfaceViewRenderer mPublishStreamStatus;
    private long aid = 0;
    private long vid = 0;

    private BukaHelper(Activity context) {
        this.mContext = context;
    }

    public static synchronized BukaHelper getInstance(Activity context) {
        if (sInstance == null) {
            sInstance = new BukaHelper(context);
        }
        return sInstance;
    }

    //初始化buksSdk及需要的views
    public void initBukaViews(UserBean userBean, ListView contentListView, ContentAdapter contentAdapter, final UserAdapter userAdapter
            , LinearLayout mStreamLayout, LinearLayout mStreamLayout_, ImageView closeVideo, ImageView mFullVideo) {
        this.userBean = userBean;
        this.contentListView = contentListView;
        this.contentAdapter = contentAdapter;
        this.userAdapter = userAdapter;
        initBukaSdk(mStreamLayout, mStreamLayout_, closeVideo, mFullVideo);
//        NetUtils.getInstance().getMsg(userBean.getGroupId(), new NetCallBack() {
//            @Override
//            public void onSuccess(String result, String msg, ResultModel model) {
//                List<UserBean> list = (List<UserBean>) model.getModelList();
//                if (list != null && list.size() > 0) {
//                    Collections.reverse(list);
//                    addMsg(list);
//                }
//            }
//
//            @Override
//            public void onFail(String code, String msg, String result) {
//
//            }
//        }, UserBean.class);
        BukaSDKManager.getMediaManager().loudSpeaker(mContext);
        try {
            registerHeadsetPlugReceiver();
        } catch (Exception e) {
            AppLog.e("============registerHeadsetPlugReceiver============" + e.getMessage());
        }
    }

    private void initBukaSdk(final LinearLayout mStreamLayout, final LinearLayout mStreamLayout_, final ImageView closeVideo, final ImageView mFullVideo) {
        this.initBuka();
//        this.initAve();
        BukaSDKManager.getConnectManager().connect(userBean.getUserId() == 0 ? DeviceUtils.getDeviceId(mContext) : userBean.getUserId() + "", "",
                new ReceiptListener() {
                    @Override
                    public void onSuccess(Object o) {
                        // TODO Auto-generated method stub
                        login(mStreamLayout, mStreamLayout_, closeVideo, mFullVideo);
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
    private void login(final LinearLayout mStreamLayout, final LinearLayout mStreamLayout_, final ImageView closeVideo, final ImageView mFullVideo) {
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
                        play(mStreamLayout, mStreamLayout_, closeVideo, mFullVideo);
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
    public void publish(final LinearLayout mStreamLayout, final boolean isSecond, final ImageView closeVideo, final ImageView mFullVideo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("room_id", userBean.getGroupId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        BukaSDKManager.getMediaManager().startPulish(2, mCameraId, mCameraId, true, true, jsonObject.toString(), 960, 540, 500, 15, new ReceiptListener<Stream>() {
        BukaSDKManager.getMediaManager().startPulish(2, mCameraId, mCameraId, true, true, jsonObject.toString(), 180, 320, 500, 15, new ReceiptListener<Stream>() {
            //            BukaSDKManager.getMediaManager().startPulish(2,mCameraId, mCameraId, true, true, "stream_info", PixelUtil.getScreenWidth(mContext) * 141 / 400, PixelUtil.getScreenWidth(mContext) * 141 / 400 * 16 / 9, 500, 15, new ReceiptListener<Stream>() {
            @Override
            public void onSuccess(Stream status) {
                AppLog.e("推流成功");
                if (isSecond) {
                    status.getSvr().setZOrderOnTop(true);
                }
                mStreamLayout.setVisibility(View.VISIBLE);
                mStreamLayout.addView(status.getSvr());
                mPublishStreamStatus = status.getSvr();
                closeVideo.setVisibility(View.VISIBLE);
                mFullVideo.setVisibility(View.VISIBLE);
                aid = status.getAid();
                vid = status.getVid();
                BukaSDKManager.getMediaManager().loudSpeaker(mContext);
                if (onVideoPublishListener != null) {
                    onVideoPublishListener.onVideoPublish();
                }
                //录制
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("aid", aid);
//                    jsonObject.put("vid", vid);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                BukaSDKManager.getStatusManager().pushStatus("rtmp", jsonObject.toString(), new ReceiptListener<String>() {
//                    public void onSuccess(String statusId) {
//                        AppLog.e(aid+","+vid+"==========pushStatus111==============="+statusId);
//                    }
//
//                    public void onError() {
//                        AppLog.e("==========pushStatus222===============");
//                    }
//                });
            }

            @Override
            public void onError(Object o) {
                AppLog.e("推流失败");
            }
        });
    }

    public void stopPublish(final LinearLayout mStreamLayout, final ImageView mCloseVideo, final ImageView mFullVideo) {
        BukaSDKManager.getMediaManager().stopPublish(new ReceiptListener<SurfaceViewRenderer>() {
            @Override
            public void onSuccess(SurfaceViewRenderer renderer) {
                AppLog.e("停止推流");
                mStreamLayout.removeAllViews();
                if (mCloseVideo != null) {
                    mCloseVideo.setVisibility(View.GONE);
                }
                if (mFullVideo != null) {
                    mFullVideo.setVisibility(View.GONE);
                }
                mStreamLayout.setVisibility(View.GONE);
            }

            @Override
            public void onError(Object o) {
                AppLog.e("停止推流失败");
            }
        });
    }

    public void play(final LinearLayout mStreamLayout, final LinearLayout mStreamLayout_, final ImageView closeVideo, final ImageView mFullVideo) {
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
                    if (getSessionUser(status.getSession_id()).getUserId()== getCurrentUser().getUserId()) {
                        final int finalI = i;
                        AppLog.e("=========play2223=========");
                        BukaSDKManager.getMediaManager().startPlay(aid, vid, new ReceiptListener<SurfaceViewRenderer>() {
                            @Override
                            public void onSuccess(SurfaceViewRenderer renderer) {
                                AppLog.e("=========play333=========");
                                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                renderer.setLayoutParams(layoutParams);
                                if (statusList.size() >= 2) {
                                    if (finalI == 0) {
                                        mStreamLayout.removeAllViews();
                                        AppLog.e("拉流成功");
                                        mStreamLayout.setVisibility(View.VISIBLE);
                                        mStreamLayout.addView(renderer);
                                    } else {
                                        renderer.setZOrderOnTop(true);
                                        mStreamLayout_.removeAllViews();
                                        AppLog.e("拉流成功");
                                        mStreamLayout_.setVisibility(View.VISIBLE);
                                        mStreamLayout_.addView(renderer);
                                    }
                                } else {
                                    mStreamLayout.removeAllViews();
                                    AppLog.e("拉流成功");
                                    mStreamLayout.setVisibility(View.VISIBLE);
                                    mStreamLayout.addView(renderer);
                                }
                                closeVideo.setVisibility(View.VISIBLE);
                                mFullVideo.setVisibility(View.VISIBLE);
//                                BukaSDKManager.getMediaManager().loudSpeaker(mContext);
                                if (onPcPlayListener != null) {
                                    onPcPlayListener.onPcPlay(false);
                                }
                                try {
                                    JSONObject user_extend = new JSONObject(extend.getString("user_extend"));
                                    String sdk = user_extend.getString("sdk");
                                    if (TextUtils.equals("2", sdk) || TextUtils.equals("5", sdk)) {
                                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        params.width = PixelUtil.getScreenWidth(mContext);
                                        params.height = params.width * 9 / 16;
                                        mStreamLayout.setLayoutParams(params);
                                        mFullVideo.setVisibility(View.GONE);
                                        if (onPcPlayListener != null) {
                                            onPcPlayListener.onPcPlay(true);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Object o) {
                                AppLog.e("拉流失败");
//                                mStreamLayout.removeAllViews();
//                                mStreamLayout_.removeAllViews();
//                                closeVideo.setVisibility(View.GONE);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopPlay(final LinearLayout mStreamLayout, final ImageView mCoseVideo, final ImageView mFullVideo) {
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
                    BukaSDKManager.getMediaManager().stopPlay(aid, vid, new ReceiptListener<SurfaceViewRenderer>() {
                        @Override
                        public void onSuccess(SurfaceViewRenderer renderer) {
                            AppLog.e("停止拉流");
                            mStreamLayout.removeAllViews();
                            mStreamLayout.setVisibility(View.GONE);
//                            mCoseVideo.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Object o) {
                            AppLog.e("停止拉流失败");
                            mStreamLayout.removeAllViews();
                            mStreamLayout.setVisibility(View.GONE);
//                            mCoseVideo.setVisibility(View.GONE);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            mStreamLayout.removeAllViews();
            mStreamLayout.setVisibility(View.GONE);
//            mCoseVideo.setVisibility(View.GONE);
        }
    }

    public void stopPlay_(Status status, final LinearLayout mStreamLayout_, final ImageView mCoseVideo) {
        try {
            JSONObject extend = new JSONObject(status.getStatus_extend());
            JSONObject stream = new JSONObject(extend.getString(Constant.KEY));
            long aid = stream.getLong(Constant.AID);
            long vid = stream.getLong(Constant.VID);
            AppLog.e(aid + "=========stopPlay_=========" + vid);
            BukaSDKManager.getMediaManager().stopPlay(aid, vid, new ReceiptListener<SurfaceViewRenderer>() {
                @Override
                public void onSuccess(SurfaceViewRenderer renderer) {
                    AppLog.e("停止拉流");
                    mStreamLayout_.removeAllViews();
                    mStreamLayout_.setVisibility(View.GONE);
//                    mCoseVideo.setVisibility(View.GONE);
                }

                @Override
                public void onError(Object o) {
                    AppLog.e("停止拉流失败");
                    mStreamLayout_.removeAllViews();
                    mStreamLayout_.setVisibility(View.GONE);
//                    mCoseVideo.setVisibility(View.GONE);
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

    //摄像头转换
    public void switchCamera() {
        if (mPublishStreamStatus != null) {
            mCameraId = mCameraId == 0 ? 1 : 0;
//            BukaSDKManager.getMediaManager().switchCamera(mContext, aid, vid, mCameraId, mCameraId, PixelUtil.getScreenWidth(mContext) * 141 / 400, PixelUtil.getScreenWidth(mContext) * 141 / 400 * 16 / 9, 10, mPublishStreamStatus);
//            BukaSDKManager.getMediaManager().switchCamera(vid,mCameraId);
            tv.buka.sdk.v3.manager.MediaManager mediaManager = (tv.buka.sdk.v3.manager.MediaManager) BukaSDKManager.getMediaManager();
            mediaManager.switchCamera(mCameraId, aid, vid, mCameraId, 180, 320, 10, mPublishStreamStatus);
        }
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

    public void exit(final LinearLayout mStreamLayout, final LinearLayout mStreamLayout_, final ImageView mCoseVideo, final ImageView mFullVideo) {
        stopPlay(mStreamLayout, mCoseVideo, mFullVideo);
        stopPublish(mStreamLayout_, mCoseVideo, mFullVideo);
        BukaSDKManager.getRpcManager().sendBroadcastRpc("", Constant.RPC_USER_OUT, 0);
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
        Log.e("布卡sdk接受消息",userBean.getText());
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
                                userBean.setText(chat.getMessage());
                                userBean.setMstype(0);
                                if (chat.getReceive_session_id().length() > 0) {
                                    //单发
                                } else {
                                    //群发
                                }
                                addLog(userBean);
                                AppLog.e("========chatListener========" + userBean.toString());
                                saveMsg(user, userBean);
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

    private void saveMsg(User user, UserBean userBean) {
        if (getSessionUser(user.getSession_id()).getUserId()==getCurrentUser().getUserId()) {
//            NetUtils.getInstance().saveMsg(JsonUtils.toJson(userBean), new NetCallBack() {
//                @Override
//                public void onSuccess(String result, String msg, ResultModel model) {
//
//                }
//
//                @Override
//                public void onFail(String code, String msg, String result) {
//
//                }
//            }, null);
        }
    }

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
                                            saveMsg(user, userBean);
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
                case Constant.RPC_STREAM_WAIT://推流等待
                    AppLog.e(rpc.getMessage() + "========RPC_STREAM_WAIT========" + rpc.getSend_session_id());
                    //rpc.getSend_session_id()  推流的人的seesion_id
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getSend_session_id(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (user != null) {
                                        UserBean userBean = new UserBean(user.getUser_extend());
                                        AppLog.e("========rpcListener222========" + userBean.toString());
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
                case Constant.RPC_STREAM_CONNECT:
                    AppLog.e(rpc.getMessage() + "========RPC_STREAM_CONNECT========" + rpc.getSend_session_id());
                    //rpc.getSend_session_id()  推流的人的seesion_id
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getSend_session_id(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (user != null) {
                                        UserBean userBean = new UserBean(user.getUser_extend());
                                        AppLog.e("========rpcListener333=======" + userBean.toString());
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
                case Constant.RPC_STREAM_END:
                    AppLog.e(rpc.getMessage() + "========RPC_STREAM_END========" + rpc.getSend_session_id());
                    //rpc.getSend_session_id()  推流的人的seesion_id
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getSend_session_id(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (user != null) {
                                        UserBean userBean = new UserBean(user.getUser_extend());
                                        AppLog.e("========rpcListener444========" + userBean.toString());
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
                case Constant.RPC_STREAM_LIVE_END:
                    AppLog.e(rpc.getMessage() + "========RPC_STREAM_LIVE_END========" + rpc.getSend_session_id());
                    //rpc.getSend_session_id()  推流的人的seesion_id
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getSend_session_id(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (user != null) {
                                        UserBean userBean = new UserBean(user.getUser_extend());
                                        AppLog.e("========rpcListener555========" + userBean.toString());
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
                case Constant.RPC_STREAM_SPEAKER_END:
                    AppLog.e(rpc.getMessage() + "========RPC_STREAM_SPEAKER_END========" + rpc.getSend_session_id());
                    //rpc.getSend_session_id()  推流的人的seesion_id
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getSend_session_id(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (user != null) {
                                        UserBean userBean = new UserBean(user.getUser_extend());
                                        AppLog.e("========rpcListener666========" + userBean.toString());
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
                case Constant.RPC_VIDEO_PLAY:
                    AppLog.e(rpc.getMessage() + "========RPC_VIDEO_PLAY========" + rpc.getSend_session_id());
                    //rpc.getSend_session_id()  推流的人的seesion_id
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getSend_session_id(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (user != null) {
                                        UserBean userBean = new UserBean(user.getUser_extend());
                                        AppLog.e("========rpcListener777========" + userBean.toString());
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
                case ConstantUtil.RPC_HTML_PPT_URL:
                case ConstantUtil.RPC_HTML_PPT_ACTION:
                    onRpcListener.onRpcListener(null, rpc);
                    break;
                case Constant.PRAISE_SUCCESS:
                    if (TextUtils.isEmpty(rpc.getSend_session_id())) {
                        return;
                    }
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getSend_session_id(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (user != null) {
                                        UserBean userBean = new UserBean(user.getUser_extend());
                                        userBean.setMstype(4);
                                        AppLog.e("========rpcListener171717========" + userBean.toString());
                                        if (!TextUtils.isEmpty(rpc.getMessage())) {
                                            PraiseModel praiseModel = JsonUtils.getBean(rpc.getMessage(), PraiseModel.class);
                                            userBean.setText(praiseModel.getUserName() + "赞赏了" + praiseModel.getLiveName() + " " + praiseModel.getPrise() + "元现金");
                                            addLog(userBean);
                                            saveMsg(user, userBean);
                                        }
                                    } else {
                                        AppLog.e("收到PRC，但未找到此人信息");
                                    }
                                }

                                @Override
                                public void onError(Object o) {

                                }
                            });
                    break;
                default://禁言、踢人、设管理员
                    final long userId = getCurrentUser().getUserId();//当前用户session_id
                    AppLog.e(rpc.getSend_session_id() + "========session_id1111=======" + rpc.getReceive_session_id());
                    //rpc.getMessage()  被（解）禁言等的人的seesion_id
                    if (TextUtils.isEmpty(rpc.getMessage())) {
                        return;
                    }
                    BukaSDKManager.getUserManager().getUser(
                            rpc.getMessage(), new ReceiptListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (user != null) {
                                        UserBean userBean = new UserBean(user.getUser_extend());
                                        AppLog.e(rpc.getMessage() + "========session_id222========" + user.getSession_id());
//                                        switch ((int) rpc.getType()) {
//                                            case Constant.RPC_SPEAK:
//                                                userBean.setUserstate(TextUtils.equals("0", userBean.getUserstate()) ? "1" : "0");
//                                                userBean.setMstype(1);
//                                                familyUserManage(userBean.getUserId(), "", userBean.getUserstate(), "");
//                                                break;
//                                            case Constant.RPC_TICKOUT:
//                                                userBean.setKick(TextUtils.equals("0", userBean.getKick()) ? "1" : "0");
//                                                userBean.setMstype(2);
//                                                familyUserManage(userBean.getUserId(), "", "", userBean.getKick());
//                                                break;
//                                            case Constant.RPC_MANAGER:
//                                                userBean.setUserType(TextUtils.equals("1", userBean.getUserType()) ? "0" : "1");
//                                                userBean.setMstype(3);
//                                                familyUserManage(userBean.getUserId(), userBean.getUserType(), "", "");
//                                                break;
//                                        }
                                        AppLog.e("========rpcListener888========" + userBean.toString());
                                        if (userId== userBean.getUserId()) {
                                            updateUser(userBean);
                                        }
                                        addLog(userBean);
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
            }
        }
    };

    private void familyUserManage(long fUId, String manage, String silence, String kick) {

    }

    StatusListener statusListener = new StatusListener() {

        @Override
        public void onStatusDelete(Status status) {
            // TODO Auto-generated method stub
            AppLog.e(status.getSession_id() + "==========状态删除========" + status.getStatus_extend());
            onStatusListener.onStatusListener(1, status);
        }

        @Override
        public void onStatusAdd(Status status) {
            // TODO Auto-generated method stub
            AppLog.e(status.getSession_id() + "==========状态添加111========" + status.getStatus_extend());
            AppLog.e("==========状态添加222========" + status.getTag());
            onStatusListener.onStatusListener(2, status);
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

    private OnVideoPublishListener onVideoPublishListener;

    public void setOnVideoPublishListener(OnVideoPublishListener onVideoPublishListener) {
        this.onVideoPublishListener = onVideoPublishListener;
    }

    public interface OnVideoPublishListener {
        void onVideoPublish();
    }

    private OnPcPlayListener onPcPlayListener;

    public void setOnPcPlayListener(OnPcPlayListener onPcPlayListener) {
        this.onPcPlayListener = onPcPlayListener;
    }

    public interface OnPcPlayListener {
        void onPcPlay(boolean pc);
    }

    private void initAve() {
        bkrtc_impl.GetInstance().register(mContext);
        bkrtc_impl.GetInstance().AveCreate(false, true, 3);
        bkrtc_impl.GetInstance().AveSetUserId("2356");
    }

    private void initTest() {
        markId(20, "test", new ReceiptListener<List<Long>>() {
            @Override
            public void onSuccess(List<Long> longs) {
                do {
                    long id;
                    id = getMediaId();
                    Log.e("MarkId", "Id = " + id);
                } while (id <= 0L);
            }

            @Override
            public void onError(Object o) {

            }
        });
    }

    private void markId(final int count, final String idExtend, final ReceiptListener<List<Long>> receiptListener) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", "media");
            obj.put("num", count);
            obj.put("index_extend", idExtend);
            obj.put("session_token", BukaSDKManager.getConnectManager().getSession().getSession_token());
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

        HttpUtils.startPostAsyncRequest(Url.getInstance().onlyIndex(), obj.toString(), 1, new NetWorkListener() {

            public void requestDidSuccess(String res, int tag) {
                if (!ResponseUtils.isSuccessStr(res)) {
                    BukaSDK.errorLog("MediaManager:获取ID失败");
                    markId(count, idExtend, receiptListener);
                    if (receiptListener != null) {
                        receiptListener.onError(res);
                    }

                } else {
                    mMediaIds = JsonUtils.getBeanList(res, Long.class);
                    mMediaIdIndex = 0;
                    if (receiptListener != null) {
                        receiptListener.onSuccess(JsonUtils.getBeanList(res, Long.class));
                    }

                }
            }

            public void requestDidFailed(String s, int i) {
                BukaSDK.errorLog("MediaManager:获取ID失败");
                markId(count, idExtend, receiptListener);
                if (receiptListener != null) {
                    receiptListener.onError(s);
                }

            }
        });
    }

    private long getMediaId() {
        final long[] mediaId = new long[1];
        if (mMediaIds == null || mMediaIds.size() == 0 || mMediaIdIndex >= mMediaIds.size()) {
            markId(20, "test", new ReceiptListener<List<Long>>() {
                @Override
                public void onSuccess(List<Long> longs) {
                    mediaId[0] = mMediaIds.get(mMediaIdIndex++);
                }

                @Override
                public void onError(Object o) {
                }
            });
        } else {
            mediaId[0] = mMediaIds.get(mMediaIdIndex++);
        }


        return mediaId[0];
    }

    private void setString(String key, String str) {
        SharedPreferences sPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        sPrefs.edit().putString(key, str).commit();
    }

    private String getStringForKey(String key, String value) {
        return PreferenceManager.getDefaultSharedPreferences(
                mContext).getString(key, value);
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
