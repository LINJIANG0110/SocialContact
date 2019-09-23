package com.qianyu.communicate.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.qianyu.chatuidemo.ui.ChatActivity;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.GridViewAdapter;
import com.qianyu.communicate.adapter.InfoCircleAdapter;
import com.qianyu.communicate.adapter.SkillBgAdapter;
import com.qianyu.communicate.adapter.ViewPagerAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.BaseWebActivity;
import com.qianyu.communicate.base.BaseWebActivity_;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.BukaHelper_;
import com.qianyu.communicate.bukaSdk.adapter.ContentAdapter;
import com.qianyu.communicate.bukaSdk.adapter.UserAdapter;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.emotions.fragments.EmotionComplateFragment;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.GlobalOnItemClickManagerUtils;
import com.qianyu.communicate.emotions.widget.EmotionKeyboard;
import com.qianyu.communicate.entity.EnterGroup;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.GiftList;
import com.qianyu.communicate.entity.HbResultBean;
import com.qianyu.communicate.entity.LiveRoomShare;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.RedPackageBean;
import com.qianyu.communicate.entity.SendCustomMsgBean;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserInfo;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpUtil;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.MarqueHorientalView;
import com.qianyu.communicate.views.MyRecylerView;
import com.qianyu.communicate.views.RoundImageView;
import com.qianyu.communicate.views.SlidingMenu;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.applibrary.wight.ProgressWebView;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.APPURL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.PixelUtil;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.BukaSDKManager;
import tv.buka.sdk.entity.Rpc;
import tv.buka.sdk.entity.Status;
import tv.buka.sdk.utils.DeviceUtils;

import static com.qianyu.communicate.utils.Tools.isAppInstalled;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class FamilyRoomActivity extends BaseActivity {
    @InjectView(R.id.iv_redpackage)
    RoundImageView ivRedpackage;
    @InjectView(R.id.llay_redpackage)
    LinearLayout llayRedpacgage;
    @InjectView(R.id.mXXL)
    LinearLayout llayXXL;
    @InjectView(R.id.mHeadFL1)
    FrameLayout mHeadFL1;
    @InjectView(R.id.mHeadImg1)
    SimpleDraweeView mHeadImg1;
    @InjectView(R.id.mMike1Logo)
    ImageView mMike1Logo;
    @InjectView(R.id.mHeadFL2)
    FrameLayout mHeadFL2;
    @InjectView(R.id.mHeadImg2)
    SimpleDraweeView mHeadImg2;
    @InjectView(R.id.mMike2Logo)
    ImageView mMike2Logo;
    @InjectView(R.id.mNameTv)
    TextView mNameTv;
    @InjectView(R.id.mRecylerView)
    ListView contentListView;
    @InjectView(R.id.et_sendmessage)
    EditText etSendmessage;
    @InjectView(R.id.rl_face)
    RelativeLayout rlFace;
    @InjectView(R.id.btn_change)
    TextView btnChange;
    @InjectView(R.id.btn_send)
    TextView btnSend;
    @InjectView(R.id.mFrameLL)
    FrameLayout mFrameLL;
    @InjectView(R.id.mLineRecylerView)
    ListView userListView;
    @InjectView(R.id.id_menu)
    SlidingMenu slidingMenu;
    @InjectView(R.id.shareLogo)
    ImageView shareLogo;
    @InjectView(R.id.endLiveLogo)
    ImageView endLiveLogo;
    @InjectView(R.id.mOnLineList)
    TextView mOnLineList;
    @InjectView(R.id.mOnLineTv)
    TextView mOnLineTv;
    @InjectView(R.id.mHitBossLL)
    LinearLayout mHitBossLL;
    @InjectView(R.id.mGuradLL)
    LinearLayout mGuradLL;
    @InjectView(R.id.mLuckPanLL)
    LinearLayout mLuckPanLL;
    @InjectView(R.id.mEventLL)
    LinearLayout mEventLL;
    @InjectView(R.id.emotionContainer)
    FrameLayout emotionContainer;
    @InjectView(R.id.mRideViewLL)
    LinearLayout mRideViewLL;
    @InjectView(R.id.mRideName)
    TextView mRideName;
    @InjectView(R.id.mRideTv)
    TextView mRideTv;
    @InjectView(R.id.mRideImageView)
    SimpleDraweeView mRideImageView;
    @InjectView(R.id.mMarQueeView)
    MarqueHorientalView mMarQueeView;
    @InjectView(R.id.mBootomLL)
    LinearLayout mBootomLL;
    @InjectView(R.id.mForBidSpeakImg)
    ImageView mForBidSpeakImg;
    @InjectView(R.id.mForBidSpeakName)
    TextView mForBidSpeakName;
    @InjectView(R.id.mForBidSpeakTime)
    TextView mForBidSpeakTime;
    @InjectView(R.id.mRevengeLL)
    LinearLayout mRevengeLL;
    @InjectView(R.id.mForBidSpeakLL)
    LinearLayout mForBidSpeakLL;
    @InjectView(R.id.mForbidInImg)
    ImageView mForbidInImg;
    @InjectView(R.id.mForbidInName)
    TextView mForbidInName;
    @InjectView(R.id.mForbidInTime)
    TextView mForbidInTime;
    @InjectView(R.id.mGoOtherGroup)
    TextView mGoOtherGroup;
    @InjectView(R.id.mForbidInLL)
    LinearLayout mForbidInLL;
    @InjectView(R.id.mWorldHead)
    SimpleDraweeView mWorldHead;
    @InjectView(R.id.mWorldMsg)
    TextView mWorldMsg;
    @InjectView(R.id.mWorldLogo)
    ImageView mWorldLogo;
    @InjectView(R.id.mWorldEventLL)
    LinearLayout mWorldEventLL;
    @InjectView(R.id.mGroupEventLl)
    LinearLayout mGroupEventLl;
    @InjectView(R.id.backFL)
    FrameLayout backFL;
    @InjectView(R.id.mApplyTv)
    TextView mApplyTv;
    private ContentAdapter contentAdapter;  //聊天室消息adapter
    private UserAdapter userAdapter;        //聊天室在线人adapter
    private UserBean userBean;              //进入聊天室的人及其相关信息
    private EmotionKeyboard mEmotionKeyboard;   //表情相关
    private GlobalOnItemClickManagerUtils globalOnItemClickManager; //表情相关
    private BukaHelper_ bukaHelper;      //布卡sdk工具类
    private FamilyList.ContentBean familyInfo;//聊天室相关信息
    private CountDownTimer mTimer1, mTimer2, mTimer3;//禁言、禁入倒计时
    public static UserBean firstMikeUser, secondMikeUser;//上一麦、二麦的人的信息
    private List<GiftList.ContentBean> mDataList = new ArrayList<>();//礼物数据集合；
    private GiftList.ContentBean giftModel;
    private boolean silence = false;
    private PersonalInfo personalInfo;//个人信息
    private EnterGroup enterGroup;//进群的信息（主要是为了取群主信息和群欢迎词）
    private boolean change = true;//默认家族
    private ProgressWebView luckPanWebView, bossWebView, xxlWebview;
    private boolean boss = false;//是否开始打boss
    private String atPhone;//被@人的手机号
    private boolean ride = false;//是否有坐骑在显示
    private int index = 0;//礼物选中index
    private boolean atFlag = false;//@功能

    @Override
    public int getRootViewId() {
        return R.layout.activity_family_room;
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.PUSH_GIFT) {
            initEventMsg();
        } else if (event.getState() == EventBuss.FAMILY_RECRUIT) {
            initEventMsg();
        } else if (event.getState() == EventBuss.FAMILY_BOSS) {
            AppLog.e("===========FAMILY_BOSS222============");
            initEventMsg();
        } else if (event.getState() == EventBuss.WORLD_RECRUIT) {
            initEventMsg();
        } else if (event.getState() == EventBuss.WORLD_MSG) {
            initEventMsg();
        } else if (event.getState() == EventBuss.PUSH_SKILL) {
            initEventMsg();
            EventRecord.ContentBean contentBean = (EventRecord.ContentBean) event.getMessage();
            User user = MyApplication.getInstance().user;
            if (contentBean != null && contentBean.getAcceptUserId() == user.getUserId()) {
                switch (contentBean.getSkillType()) {
                    case "BAN"://禁止说话
                        if (contentBean.getEffectValue() > 0) {//进直播间被禁言
                            ImageUtil.loadHeadImgNetCorner(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl(), mForBidSpeakImg);
                            mForBidSpeakName.setText(TextUtils.isEmpty(contentBean.getSendNickName()) ? "" : contentBean.getSendNickName());
                            startTimmer(contentBean.getEffectValue() * 1000, mForBidSpeakTime);
                        }
                        if (userBean != null) {
                            NetUtils.getInstance().updateSkill(userBean.getUserId(), Long.parseLong(userBean.getGroupId()), 1, new NetCallBack() {
                                @Override
                                public void onSuccess(String result, String msg, ResultModel model) {

                                }

                                @Override
                                public void onFail(String code, String msg, String result) {

                                }
                            }, null);
                        }
                        break;
                    case "KICK"://踢出群聊
                        if (contentBean.getEffectValue() > 0) {//进直播间被禁入
                            ImageUtil.loadHeadImgNetCorner(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl(), mForbidInImg);
                            mForbidInName.setText(TextUtils.isEmpty(contentBean.getSendNickName()) ? "" : contentBean.getSendNickName());
                            startTimmer_(contentBean.getEffectValue() * 1000, mForbidInTime);
                        }
                        if (userBean != null) {
                            NetUtils.getInstance().updateSkill(userBean.getUserId(), Long.parseLong(userBean.getGroupId()), 2, new NetCallBack() {
                                @Override
                                public void onSuccess(String result, String msg, ResultModel model) {

                                }

                                @Override
                                public void onFail(String code, String msg, String result) {

                                }
                            }, null);
                        }
                        break;
                    case "CONFUSION"://胡说八道
                        if (userBean != null) {
                            userBean.setConfusionNum((int) contentBean.getEffectValue());
                        }
                        break;
                }
            }
        } else if (event.getState() == EventBuss.FAMILY_EXIT) {
            AppLog.e("===========FAMILY_BOSS333============");
            bukaHelper.exit();
            finish();
        } else if (event.getState() == EventBuss.FAMILY_EXIT_ENTER) {
            bukaHelper.exit();
            finish();
        } else if (event.getState() == EventBuss.FAMILY_ENTER) {
            mApplyTv.setVisibility(View.VISIBLE);
        } else if (event.getState() == EventBuss.FAMILY_NAME) {
            String message = (String) event.getMessage();
            mNameTv.setText(message);
        } else if (event.getState() == EventBuss.CREATE_REDPACKE) {
            // 创建红包成功回调
            RedPackageBean mBean = (RedPackageBean) event.getMessage();
            SendCustomMsgBean baseBean = new SendCustomMsgBean();
            baseBean.setMessageType(mBean.messageType);
            baseBean.setNextWordPinyin(mBean.wordS);
            baseBean.setRedPacketId(mBean.hongbaoId);
            baseBean.setRedPacketWord(mBean.title);
            baseBean.setText(mBean.title);
            String mJson = new Gson().toJson(baseBean);
            Log.e("发送的红包json", mJson);
            saveMsg(mJson, mBean.title);
            // 第二步刷新钻石
            initRemainMoney();
        }
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        RedPackageBean mBean = new RedPackageBean();
        mBean.setHongbaoId(1);
        mBean.setTitle("一心一意");
        mBean.setWordS("yi");
        String mJson = new Gson().toJson(mBean);
        Log.e("test", mJson);
        initUserAndChatRecylerView();
        initEmotions();
        etSendmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int lengthBefore, int lengthAfter) {
                String content = etSendmessage.getText().toString().trim();
                if (content.startsWith("@") && start == 0 && lengthBefore == 0 && lengthAfter == 1) {
                    atFlag = true;
                    KeyBoardUtils.hideSoftInput(etSendmessage);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            slidingMenu.openMenu();
                        }
                    }, 500);
                } else {
                    atFlag = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = etSendmessage.getText().toString().trim();
                if (content.length() <= 0) {
                    btnSend.setVisibility(View.GONE);
                } else {
                    btnSend.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void initData() {
        setResult(101);
        if (getIntent() != null) {
            userBean = ((UserBean) getIntent().getSerializableExtra("userBean"));
            enterGroup = ((EnterGroup) getIntent().getSerializableExtra("enterGroup"));
            familyInfo = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
            boss = getIntent().getBooleanExtra("boss", false);
            mNameTv.setText(TextUtils.isEmpty(familyInfo.getGroupName()) ? "" : familyInfo.getGroupName());
            mApplyTv.setVisibility(enterGroup.getApplyNum() > 0 ? View.VISIBLE : View.GONE);
            mHitBossLL.setVisibility(enterGroup.getBossStatus() > 0 ? View.VISIBLE : View.GONE);
            if (enterGroup.getBossStatus() > 0 && boss) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showBossPopWindow();
                    }
                }, 2000);
            }
            if (userBean != null) {
                initBukaSdk();
            }
            if (userBean.getJinyantime() > 0) {//进直播间被禁言
                EnterGroup.ManagerUser operationInfo = userBean.getJinYanInfo();
                ImageUtil.loadHeadImgNetCorner(TextUtils.isEmpty(operationInfo.getHeadUrl()) ? "" : operationInfo.getHeadUrl(), mForBidSpeakImg);
                mForBidSpeakName.setText(TextUtils.isEmpty(operationInfo.getNickName()) ? "" : operationInfo.getNickName());
                startTimmer(userBean.getJinyantime() * 1000, mForBidSpeakTime);
            }
            if (userBean.getTichutime() > 0) {//进直播间被禁入
                EnterGroup.ManagerUser operationInfo = userBean.getTichunInfo();
                ImageUtil.loadHeadImgNetCorner(TextUtils.isEmpty(operationInfo.getHeadUrl()) ? "" : operationInfo.getHeadUrl(), mForbidInImg);
                mForbidInName.setText(TextUtils.isEmpty(operationInfo.getNickName()) ? "" : operationInfo.getNickName());
                startTimmer_(userBean.getTichutime() * 1000, mForbidInTime);
            }
            try {
                if (null != userBean.getUserflag() && userBean.getUserflag().equals("0")) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //execute the task
                            Log.e("传入id",userBean.getGroupId());
                            startActivity(new Intent(FamilyRoomActivity.this, SystemRedpackageDialog.class)
                                    .putExtra("groupId", userBean.getGroupId()));
                        }
                    }, 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        initRemainMoney();
        initEventMsg();
    }

    //初始化布卡sdk
    private void initBukaSdk() {
        requestPermisson();
        bukaHelper = BukaHelper_.getInstance(this);
        bukaHelper.initBukaViews(userBean, enterGroup, contentListView, contentAdapter, userAdapter);
        Tools.showWaitDialog(this, "进入房间中...", true);
        //用户登录布卡sdk监听
        bukaHelper.setOnLoginListener(new BukaHelper_.OnLoginListener() {
            @Override
            public void onStatusListener(int type, String str) {
                Tools.dismissWaitDialog();
                switch (type) {
                    case 0:
                    case 1:
                        ToastUtil.shortShowToast(str);
                        bukaHelper.exit();
                        finish();
                        break;
                    case 3:
                        ToastUtil.shortShowToast(str);
                        bukaHelper.exit();
                        finish();
                        break;
//                    case 4:
//                        bukaHelper.play();
//                        break;
                }
            }
        });
        //用户在线人数监听
        bukaHelper.setOnNumChangeListener(new BukaHelper_.OnNumChangeListener() {
            @Override
            public void onNumListener(int type, long num) {
                switch (type) {
                    case 1:
                        break;
                    case 3:
                        mOnLineList.setText("在线列表（" + NumberUtils.roundInt((int) num) + "）");
                        mOnLineTv.setText(NumberUtils.roundInt((int) num));
                        break;

                }
            }
        });
        //用户信令监听
        bukaHelper.setOnRpcListener(new BukaHelper_.OnRpcListener() {
            @Override
            public void onRpcListener(tv.buka.sdk.entity.User user, Rpc rpc) {
//                switch ((int) rpc.getType()) {
//                    case Constant.RPC_GIFT:
//                        if (!TextUtils.isEmpty(rpc.getMessage())) {
//                            GiftModel giftModel = JsonUtils.getBean(rpc.getMessage(), GiftModel.class);
//                            giftModel.setJumpCombo(giftModel.getGiftCount());
//                            giftControl.loadGift(giftModel, false);
//                        }
//                        break;
//                }
            }

            @Override
            public void onRpcListener_(UserBean manager, UserBean userBean, Rpc rpc) {
                AppLog.e(userBean != null ? userBean.toString() : "" + "===========setOnRpcListener===============" + manager != null ? manager.toString() : "");
                switch ((int) rpc.getType()) {
                    //禁言
                    case Constant.RPC_FORBID_SPEAK:
                        if (FamilyRoomActivity.this.userBean.getUserId() == userBean.getUserId()) {
                            ImageUtil.loadHeadImgNetCorner(TextUtils.isEmpty(manager.getHeadUrl()) ? "" : manager.getHeadUrl(), mForBidSpeakImg);
                            mForBidSpeakName.setText(TextUtils.isEmpty(manager.getNickName()) ? "" : manager.getNickName());
                            startTimmer(72 * 60 * 60 * 1000, mForBidSpeakTime);
                        }
                        break;
                    //取消禁言
                    case Constant.RPC_ALLOW_SPEAK:
                        if (FamilyRoomActivity.this.userBean.getUserId() == userBean.getUserId()) {
                            mBootomLL.setVisibility(View.VISIBLE);
                            mForBidSpeakLL.setVisibility(View.GONE);
                            if (mTimer1 != null) {
                                mTimer1.cancel();
                                mTimer1.onFinish();
                            }
                        }
                        break;
                    //禁入
                    case Constant.RPC_FORBID_IN:
                        if (FamilyRoomActivity.this.userBean.getUserId() == userBean.getUserId()) {
                            ImageUtil.loadHeadImgNetCorner(TextUtils.isEmpty(manager.getHeadUrl()) ? "" : manager.getHeadUrl(), mForbidInImg);
                            mForbidInName.setText(TextUtils.isEmpty(manager.getNickName()) ? "" : manager.getNickName());
                            startTimmer_(2 * 60 * 60 * 1000, mForbidInTime);
                        }
                        break;
                    // 踢出群
                    case Constant.RPC_ALLOW_IN:
                        if (FamilyRoomActivity.this.userBean.getUserId() == userBean.getUserId()) {
                            mForbidInLL.setVisibility(View.GONE);
                            if (mTimer2 != null) {
                                mTimer2.cancel();
                                mTimer2.onFinish();
                            }
                        }
                        break;
                    //上一麦
                    case Constant.RPC_BEGIN_FIRST_MIKE:
                        if (manager.getUserId() == FamilyRoomActivity.this.userBean.getUserId() && manager.getUserId() == userBean.getUserId()) {//权限够，自己上一麦
                            bukaHelper.updateUser(userBean);
                            bukaHelper.publish();
                        } else if (FamilyRoomActivity.this.userBean.getUserId() == userBean.getUserId()) {//被人抱上麦，需要自己同意
                            showUpMikePopWindow(manager, 1, userBean);
                        }
                        break;
                    //上二麦
                    case Constant.RPC_BEGIN_SECOND_MIKE:
                        if (manager.getUserId() == FamilyRoomActivity.this.userBean.getUserId() && manager.getUserId() == userBean.getUserId()) {
                            bukaHelper.updateUser(userBean);
                            bukaHelper.publish();
                        } else if (FamilyRoomActivity.this.userBean.getUserId() == userBean.getUserId()) {//被人抱上二麦，需要自己同意
                            showUpMikePopWindow(manager, 2, userBean);
                        }
                        break;
                    //拒绝上麦
                    case Constant.RPC_REJECT_MIKE:
                        if (FamilyRoomActivity.this.userBean.getUserId() == userBean.getUserId()) {
                            ToastUtil.shortShowToast(manager.getNickName() + "拒绝上麦！");
                        }
                        break;
                    //坐骑进入
                    case Constant.RPC_RIDE_IN:
                        if (!ride && !TextUtils.isEmpty(manager.getMountName()) && !TextUtils.isEmpty(manager.getMountPicPath()))
                            rideTranslateAnim(manager);
                        break;
                }
            }
        });
        //用户在直播间监听流
        bukaHelper.setOnStatusListener(new BukaHelper_.OnStatusListener() {
            @Override
            public void onStatusListener(int type, Status status) {
                UserBean sessionUser = bukaHelper.getSessionUser(status.getSession_id());
                if (type == 1) {
                    switch (sessionUser.getMike()) {
                        case 1:
                            firstMikeUser = sessionUser;
                            mHeadFL1.setVisibility(View.VISIBLE);
                            mMike1Logo.setVisibility(View.GONE);
                            mHeadImg1.setImageURI(TextUtils.isEmpty(sessionUser.getHeadUrl()) ? "" : sessionUser.getHeadUrl());
                            break;
                        case 2:
                            secondMikeUser = sessionUser;
                            mHeadFL2.setVisibility(View.VISIBLE);
                            mMike2Logo.setVisibility(View.GONE);
                            mHeadImg2.setImageURI(TextUtils.isEmpty(sessionUser.getHeadUrl()) ? "" : sessionUser.getHeadUrl());
                            break;
                    }
                } else {
                    if (firstMikeUser != null && sessionUser.getUserId() == firstMikeUser.getUserId()) {
                        firstMikeUser = null;
                        mHeadFL1.setVisibility(View.GONE);
                        mMike1Logo.setVisibility(View.VISIBLE);
                    }
                    if (secondMikeUser != null && sessionUser.getUserId() == secondMikeUser.getUserId()) {
                        secondMikeUser = null;
                        mHeadFL2.setVisibility(View.GONE);
                        mMike2Logo.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @OnClick({R.id.backFL, R.id.mOnLineLL, R.id.btn_change, R.id.btn_send, R.id.et_sendmessage, R.id.mHeadFL1, R.id.mHeadFL2, R.id.mMike1Logo, R.id.mMike2Logo,
            R.id.shareLogo, R.id.endLiveLogo, R.id.mHitBossLL, R.id.mGuradLL, R.id.mLuckPanLL, R.id.mEventLL, R.id.mRevengeLL, R.id.mGoOtherGroup, R.id.mWorldEventLL,
            R.id.mGroupEventLl, R.id.iv_redpackage, R.id.mXXL, R.id.llay_redpackage})
    public void onViewClicked(View view) {
        User user = MyApplication.getInstance().user;
        List<Status> statusList = BukaSDKManager.getStatusManager().getStatusArr(Constant.STATUS_TAG);
        Intent intent3 = new Intent(FamilyRoomActivity.this, EventRecordActivity.class);
        switch (view.getId()) {
            case R.id.backFL:
                SpringUtils.springAnim(endLiveLogo);
                new AlertDialog.Builder(FamilyRoomActivity.this).setTitle("退出聊天室?")
                        .setMessage("您是否确定退出聊天室？")
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                bukaHelper.exit();
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.mOnLineLL:
                slidingMenu.toggle();
                break;
            case R.id.btn_change:
                change = !change;
                btnChange.setText(change ? "家族" : "世界");
                etSendmessage.setHint(change ? "说点什么吧..." : "世界喊话消耗50钻石");
                break;
            case R.id.btn_send:
                SpringUtils.springAnim(btnSend);
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(FamilyRoomActivity.this, RegistActivity.class));
                    return;
                }
                KeyBoardUtils.hideSoftInput(etSendmessage);
                emotionContainer.setVisibility(View.GONE);
                String content = etSendmessage.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    if (change) {
                        SendCustomMsgBean mBean = new SendCustomMsgBean();
                        if (userBean != null && userBean.getConfusionNum() > 0) {
                            List<String> confuseList = SpUtil.getStrListValue(this, "confuse");
                            if (confuseList != null && confuseList.size() > 0) {
                                mBean.setText(confuseList.get((int) (Math.random() * confuseList.size())) + "【胡言乱语中】");
                                mBean.setMessageType(0);// 普通消息
                                String mJson = new Gson().toJson(mBean);
                                saveMsg(mJson, confuseList.get((int) (Math.random() * confuseList.size())) + "【胡言乱语中】");
                                updateConfusionNum();
                            }
                        } else {
                            mBean.setText(content);
                            mBean.setMessageType(0);// 普通消息
                            String mJson = new Gson().toJson(mBean);
                            saveMsg(mJson, content);
                        }
                    } else {
                        worldSpeak(user, content);
                    }
                }
                etSendmessage.setText("");
                btnSend.setVisibility(View.GONE);
                break;
            case R.id.et_sendmessage:
                slidingMenu.closeMenu();
                break;
            case R.id.mHeadFL1:
                showMikePopWindow(mHeadFL1, 1);
                break;
            case R.id.mHeadFL2:
                showMikePopWindow(mHeadFL2, 2);
                break;
            case R.id.mMike1Logo:
                showMikePopWindow(mMike1Logo, 1);
                break;
            case R.id.mMike2Logo:
                showMikePopWindow(mMike2Logo, 2);
                break;
            case R.id.shareLogo:
                SpringUtils.springAnim(shareLogo);
                silence = !silence;
                ToastUtil.shortShowToast(silence ? "打开静音" : "关闭静音");
                shareLogo.setImageResource(silence ? R.mipmap.mute : R.mipmap.voice);
                if (statusList == null || statusList.size() <= 0) {
                    return;
                }
                bukaHelper.stopPlay();
                for (int i = 0; i < statusList.size(); i++) {
                    final Status status = statusList.get(i);
                    if (silence) {
                        bukaHelper.play_(status, false);
                    } else {
                        bukaHelper.play_(status, true);
                    }
                }
                break;
            case R.id.endLiveLogo:
                SpringUtils.springAnim(endLiveLogo);
                NetUtils.getInstance().enterGroup(familyInfo.getGroupId(), new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        mApplyTv.setVisibility(View.GONE);
                        EnterGroup enterGroup = (EnterGroup) model.getModel();
//                        Intent intent = new Intent(FamilyRoomActivity.this, familyInfo.getGroupId() == 1 ? FamilyCustomerDetailActivity.class : enterGroup.getUserType() >= 4 ? FamilyCustomerDetailActivity.class : FamilyMemberDetailActivity.class);
                        Intent intent = new Intent(FamilyRoomActivity.this, enterGroup.getUserType() >= 4 ? FamilyCustomerDetailActivity.class : FamilyMemberDetailActivity.class);
                        intent.putExtra("family", familyInfo);
                        intent.putExtra("userBean", userBean);
                        intent.putExtra("enterGroup", enterGroup);
                        startActivity(intent);
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                    }
                }, EnterGroup.class);
                break;
            case R.id.mHitBossLL:
                showBossPopWindow();
                break;
            case R.id.mGuradLL:
                Intent intent = new Intent(FamilyRoomActivity.this, BaseWebActivity_.class);
                intent.putExtra("url", APPURL.BASE_H5_URL + "gameType=10004&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(FamilyRoomActivity.this));
                startActivity(intent);
                break;
            case R.id.mLuckPanLL:
                showLuckPanPopWindow();
                break;
            case R.id.mEventLL:
                Intent intent1 = new Intent(this, EventRecordActivity.class);
                intent1.putExtra("groupId", familyInfo.getGroupId());
                startActivity(intent1);
                break;
            case R.id.mRevengeLL:
                EnterGroup.ManagerUser operationInfo = userBean.getJinYanInfo();
                if (operationInfo != null) {
                    Intent intent2 = new Intent(this, SkillActivity.class);
                    intent2.putExtra("userId", operationInfo.getUserId());
                    startActivity(intent2);
                }
                break;
            case R.id.mGoOtherGroup:
                bukaHelper.exit();
                finish();
                break;
            case R.id.mWorldEventLL:
                intent3.putExtra("position", 1);
                intent3.putExtra("groupId", familyInfo.getGroupId());
                startActivity(intent3);
                break;
            case R.id.mGroupEventLl:
                intent3.putExtra("position", 0);
                intent3.putExtra("groupId", familyInfo.getGroupId());
                startActivity(intent3);
                break;
            // 红包创建入口
            case R.id.iv_redpackage:
                startActivity(new Intent(FamilyRoomActivity.this, CRedPackageActivity.class).putExtra("groupId", userBean.getGroupId()));
                break;
            // 消消乐入口
            case R.id.mXXL:
                showXxlPopWindow();
                break;

            // 红包
            case R.id.llay_redpackage:
                startActivity(new Intent(FamilyRoomActivity.this, CRedPackageActivity.class).putExtra("groupId", userBean.getGroupId()));
                break;

        }
    }

    //坐骑进入动画
    public void rideTranslateAnim(UserBean userBean) {
        int sex = userBean.getSex();
        switch (sex) {
            case 1:
                mRideName.setTextColor(getResources().getColor(R.color.chat_color_nan));
                break;
            case 2:
                mRideName.setTextColor(getResources().getColor(R.color.chat_color_nv));
                break;
        }
        mRideName.setText(TextUtils.isEmpty(userBean.getNickName()) ? "" : userBean.getNickName());
        mRideTv.setText(TextUtils.isEmpty(userBean.getMountName()) ? "" : userBean.getMountName());
        mRideImageView.setImageURI(TextUtils.isEmpty(userBean.getMountPicPath()) ? "" : userBean.getMountPicPath());
        mRideImageView.setVisibility(TextUtils.isEmpty(userBean.getMountPicPath()) ? View.GONE : View.VISIBLE);
        mRideViewLL.setVisibility(View.VISIBLE);
        ride = true;
        mRideViewLL.post(new Runnable() {
            @Override
            public void run() {
                AppLog.e("==========onAnimationStart=========" + mRideViewLL.getMeasuredWidth());
                final ObjectAnimator objectAnimator01 = ObjectAnimator.ofFloat(mRideViewLL, "TranslationX", mRideViewLL.getMeasuredWidth(), 0);
                objectAnimator01.setDuration(1000);
                final ObjectAnimator objectAnimator02 = ObjectAnimator.ofFloat(mRideViewLL, "alpha", 1, 0);
                objectAnimator02.setDuration(1000);
                final AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator02).after(objectAnimator01);
                animatorSet.start();
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRideViewLL.setAlpha(1.0f);
                        mRideViewLL.setTranslationX(mRideViewLL.getMeasuredWidth());
                        mRideViewLL.clearAnimation();
                        mRideViewLL.setVisibility(View.GONE);
                        ride = false;
                        AppLog.e("==========onAnimationEnd=========");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });
    }

    //世界发言
    private void worldSpeak(User user, String content) {
        NetUtils.getInstance().worldSpeak(user.getUserId(), content, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                ToastUtil.shortShowToast(msg);
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
            }
        }, null);
    }

    //更新胡言乱语次数
    private void updateConfusionNum() {
        userBean.setConfusionNum(userBean.getConfusionNum() - 1);
        NetUtils.getInstance().updateConfuse(userBean.getUserId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {

            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, null);
    }

    //保存聊天消息
    private void saveMsg(final String contentJson, String text) {
        final User user = MyApplication.getInstance().user;
        NetUtils.getInstance().msgSave(text, Long.parseLong(TextUtils.isEmpty(userBean.getGroupId()) ? "" : userBean.getGroupId()), contentJson, userBean.getUserType(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("saveMsg", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
//                    String data = jsonObject.getString("data");// 老版本直接取data
                    String data = jsonObject.getJSONObject("data").getString("returnMessage");// 通过服务端检验敏感字后返回的文本 需要替换contentJson中的text文本
                    SendCustomMsgBean mBean = new Gson().fromJson(contentJson, SendCustomMsgBean.class);
                    mBean.setText(data);
                    String dataJson = new Gson().toJson(mBean);
                    Log.e("传入布卡dataJson", dataJson);
                    bukaHelper.chat(null, dataJson);
                    if (data.startsWith("@") && !TextUtils.isEmpty(atPhone) && !TextUtils.equals(atPhone, user.getPhone())) {
                        Tools.groupAtMe("在" + familyInfo.getGroupName() + data, atPhone, familyInfo.getGroupId());
                    }
                    // 这里验证红包接龙红包结果
                    int isFlag = jsonObject.getJSONObject("data").getInt("isflag");
                    if (isFlag == 1) {
                        // 这里接龙红包成功-1.发条领取消息2.展示红包页面
                        String word = jsonObject.getJSONObject("data").getString("word");
                        String hongbaoId = jsonObject.getJSONObject("data").getString("hongbaoId");
                        String nowNum = jsonObject.getJSONObject("data").getString("nowNum");
                        String wordS = jsonObject.getJSONObject("data").getString("wordS");
                        String headUrl = jsonObject.getJSONObject("data").getString("headUrl");
                        String nickName = jsonObject.getJSONObject("data").getString("nickName");
                        String totalNum = jsonObject.getJSONObject("data").getString("totalNum");
                        String createTime = jsonObject.getJSONObject("data").getString("createTime");
                        String diamond = jsonObject.getJSONObject("data").getString("diamond");
                        //第一步
                        String sendMes = nickName + "领取了接龙红包，下一个拼音:" + wordS;
                        if (nowNum.equals(totalNum)) {
                            sendMes = nickName + "领取了接龙红包，红包已领完！";
                        }
                        SendCustomMsgBean tempBean = new SendCustomMsgBean();
                        tempBean.setMessageType(8);// 红包获取自义定消息
                        tempBean.setText(sendMes);
                        String sendJson = new Gson().toJson(tempBean);
                        saveMsg(sendJson, sendMes);
                        // 第二步刷新钻石
                        initRemainMoney();
                        //第三步
                        HbResultBean resBean = new HbResultBean();
                        resBean.setWord(word);
                        resBean.setHongbaoId(hongbaoId);
                        resBean.setNowNum(nowNum);
                        resBean.setWordS(wordS);
                        resBean.setHeadUrl(headUrl);
                        resBean.setNickName(nickName);
                        resBean.setTotalNum(totalNum);
                        resBean.setCreateTime(createTime);
                        resBean.setDiamond(diamond);
                        resBean.setGroupId(userBean.getGroupId());
                        String resJson = new Gson().toJson(resBean);
                        startActivity(new Intent(FamilyRoomActivity.this, RedpackageActivity.class).putExtra("resJson", resJson));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, null);
    }

    //世界事件及群事件
    private void initEventMsg() {
        NetUtils.getInstance().worldEvent(-1, -1, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                EventRecord circleList = (EventRecord) model.getModel();
                if (circleList != null) {
                    List<EventRecord.ContentBean> list = circleList.getContent();
                    if (list != null && list.size() > 0) {
                        mWorldEventLL.setVisibility(View.VISIBLE);
                        final EventRecord.ContentBean contentBean = list.get(0);
                        showWorldEvent(contentBean);
                    } else {
                        mWorldEventLL.setVisibility(View.GONE);
                    }
                } else {
                    mWorldEventLL.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, EventRecord.class);

        NetUtils.getInstance().groupEvent(familyInfo.getGroupId(), -1, -1, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                EventRecord circleList = (EventRecord) model.getModel();
                if (circleList != null) {
                    List<EventRecord.ContentBean> list = circleList.getContent();
                    if (list != null && list.size() > 0) {
                        mGroupEventLl.setVisibility(View.VISIBLE);
                        final EventRecord.ContentBean contentBean = list.get(0);
//                        int sendSex = contentBean.getSendSex();
//                        int acceptSex = contentBean.getAcceptSex();
//                        if (!TextUtils.isEmpty(contentBean.getEventMsg())) {
//                            try {
//                                SpannableString content = Tools.matcherSearchTitle(getResources().getColor(sendSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
//                                        getResources().getColor(acceptSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
//                                        contentBean.getEventMsg(), contentBean.getSendNickName(), contentBean.getAcceptNickName());
//                                mMarQueeView.setText(SpanStringUtils.getEmotionContent_(EmotionUtils.EMOTION_CLASSIC_TYPE, FamilyRoomActivity.this, mMarQueeView, content));
//                            } catch (Exception e) {
//                                mMarQueeView.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, FamilyRoomActivity.this, mMarQueeView, contentBean.getEventMsg()));
//                            }
//                        } else {
//                            mMarQueeView.setText("");
//                        }
                        mMarQueeView.setText(TextUtils.isEmpty(contentBean.getEventMsg()) ? "" : Html.fromHtml(contentBean.getEventMsg()));
                    } else {
                        mGroupEventLl.setVisibility(View.GONE);
                    }
                } else {
                    mGroupEventLl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, EventRecord.class);
    }

    private void showWorldEvent(final EventRecord.ContentBean contentBean) {
        mWorldHead.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl());
        mWorldMsg.setText(TextUtils.isEmpty(contentBean.getEventMsg()) ? "" : contentBean.getEventMsg());
//        int sendSex = contentBean.getSendSex();
//        int acceptSex = contentBean.getAcceptSex();
//        if (!TextUtils.isEmpty(contentBean.getEventMsg())) {
//            try {
//                SpannableString content = Tools.matcherSearchTitle(getResources().getColor(sendSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
//                        getResources().getColor(acceptSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
//                        contentBean.getEventMsg(), contentBean.getSendNickName(), contentBean.getAcceptNickName());
//                mWorldMsg.setText(SpanStringUtils.getEmotionContent_(EmotionUtils.EMOTION_CLASSIC_TYPE, this, mWorldMsg, content));
//            } catch (Exception e) {
//                mWorldMsg.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, this, mWorldMsg, contentBean.getEventMsg()));
//            }
//        } else {
//            mWorldMsg.setText("");
//        }
        mWorldMsg.setText(TextUtils.isEmpty(contentBean.getEventMsg()) ? "" : Html.fromHtml(contentBean.getEventMsg()));
        mWorldLogo.setVisibility(TextUtils.isEmpty(contentBean.getPicPath()) ? View.INVISIBLE : View.VISIBLE);
        ImageUtil.loadPicNet(TextUtils.isEmpty(contentBean.getPicPath()) ? "" : contentBean.getPicPath(), mWorldLogo);
        if (!TextUtils.isEmpty(contentBean.getEventType())) {
            switch (contentBean.getEventType()) {
                case "FAMILYBOSS"://家族Boss
                case "FAMILYCALL"://家族召唤
                case "FAMILYRECRUIT"://世界招募
                    mWorldLogo.setVisibility(View.VISIBLE);
                    mWorldLogo.setImageResource(R.mipmap.enter);
                    break;
            }
        }
        mWorldLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(contentBean.getEventType())) {
                    switch (contentBean.getEventType()) {
                        case "SKILL":// 技能相关
                            Intent intent = new Intent(FamilyRoomActivity.this, SkillActivity.class);
                            intent.putExtra("userId", contentBean.getSendUserId());
                            startActivity(intent);
                            break;
                        case "FAMILYBOSS"://家族Boss
                        case "FAMILYCALL"://家族召唤
                        case "FAMILYRECRUIT"://世界招募
                            EventBuss event = new EventBuss(EventBuss.FAMILY_EXIT_ENTER);
                            event.setMessage(contentBean);
                            EventBus.getDefault().post(event);
                            bukaHelper.exit();
                            finish();
                            break;
                    }
                }
            }
        });
    }

    //当前用户个人信息
    private void initRemainMoney() {
        if (MyApplication.getInstance().isLogin()) {
            User user = MyApplication.getInstance().user;
            NetUtils.getInstance().mineInfo(user.getUserId(), new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    personalInfo = (PersonalInfo) model.getModel();
                    if (null != remainMoney) {
                        if (gifType == 1) {
                            remainMoney.setText(NumberUtils.rounLong(personalInfo.getFubao()));
                        } else if (gifType == 2) {
                            remainMoney.setText(NumberUtils.rounLong(personalInfo.getGold()));
                        } else {
                            remainMoney.setText(NumberUtils.rounLong(personalInfo.getDiamond()));
                        }
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, PersonalInfo.class);
        }
    }

    //禁言、进入倒计时
    private void startTimmer(long time, final TextView mTimeTv) {
        mTimer1 = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mBootomLL.setVisibility(View.GONE);
                mForBidSpeakLL.setVisibility(View.VISIBLE);
                mTimeTv.setText(TimeUtils.getHMSTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                mBootomLL.setVisibility(View.VISIBLE);
                mForBidSpeakLL.setVisibility(View.GONE);
            }
        };
        mTimer1.start();
    }

    private void startTimmer_(long time, final TextView mTimeTv) {
        mTimer2 = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mForbidInLL.setVisibility(View.VISIBLE);
                mTimeTv.setText(TimeUtils.getHMSTime(millisUntilFinished) + "后可再次进入");
            }

            @Override
            public void onFinish() {
                mForbidInLL.setVisibility(View.GONE);
            }
        };
        mTimer2.start();
    }

    //被邀请上麦弹窗
    private void showUpMikePopWindow(final UserBean manager, final int mike, final UserBean userBean) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.up_mike_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        TextView mUpMikeTv = (TextView) view.findViewById(R.id.mUpMikeTv);
                        final TextView mCountDownTv = (TextView) view.findViewById(R.id.mCountDownTv);
                        TextView mContentTv = (TextView) view.findViewById(R.id.mContentTv);
                        TextView mCancelMikeTv = (TextView) view.findViewById(R.id.mCancelMikeTv);
                        mContentTv.setText(manager.getNickName() + "邀请你上麦，是否接受？");
                        mUpMikeTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                bukaHelper.updateUser(userBean);
                                bukaHelper.publish();
                            }
                        });
                        mCancelMikeTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                if (mTimer3 != null) {
                                    mTimer3.cancel();
                                    mTimer3.onFinish();
                                }
                                bukaHelper.rpc(null, Constant.RPC_REJECT_MIKE, JSON.toJSONString(manager));
                            }
                        });
                        mTimer3 = new CountDownTimer(5 * 1000, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                mCountDownTv.setText(millisUntilFinished / 1000 + "");
                            }

                            @Override
                            public void onFinish() {
                                popupWindow.dismiss();
                                bukaHelper.rpc(null, Constant.RPC_REJECT_MIKE, JSON.toJSONString(manager));
                            }
                        };
                        mTimer3.start();
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(slidingMenu, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mTimer3 != null) {
                    mTimer3.cancel();
                    mTimer3.onFinish();
                }
            }
        });
    }

    //上下麦弹窗
    private void showMikePopWindow(View mMikeLogo, final int mike) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.family_mike_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1.0f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        TextView mMikeOneTv = (TextView) view.findViewById(R.id.mMikeOneTv);
                        TextView mMikeTwoTv = (TextView) view.findViewById(R.id.mMikeTwoTv);
                        TextView mMikeDownTv = (TextView) view.findViewById(R.id.mMikeDownTv);
                        mMikeOneTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                if (bukaHelper.mPublishStreamStatus != null) {
                                    ToastUtil.shortShowToast("您已上麦，请勿重复操作...");
                                    return;
                                }
                                if (firstMikeUser != null) {
                                    if (userBean.getUserType() < firstMikeUser.getUserType()) {
                                        bukaHelper.rpc(null, Constant.RPC_END_FIRST_MIKE, JSON.toJSONString(firstMikeUser));
                                    } else {
                                        ToastUtil.shortShowToast("一麦已经有人上了...");
                                        return;
                                    }
                                }
                                if (userBean.getUserType() > 2) {
                                    ToastUtil.shortShowToast("请联系管理员上麦...");
                                    return;
                                }
                                bukaHelper.rpc(null, Constant.RPC_BEGIN_FIRST_MIKE, JSON.toJSONString(userBean));
                            }
                        });
                        mMikeTwoTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                if (bukaHelper.mPublishStreamStatus != null) {
                                    ToastUtil.shortShowToast("您已上麦，请勿重复操作...");
                                    return;
                                }
                                if (secondMikeUser != null) {
                                    if (userBean.getUserType() < secondMikeUser.getUserType()) {
                                        bukaHelper.rpc(null, Constant.RPC_END_SECOND_MIKE, JSON.toJSONString(secondMikeUser));
                                    } else {
                                        ToastUtil.shortShowToast("二麦已经有人上了...");
                                        return;
                                    }
                                }
                                if (userBean.getUserType() > 2) {
                                    ToastUtil.shortShowToast("请联系管理员上麦...");
                                    return;
                                }
                                bukaHelper.rpc(null, Constant.RPC_BEGIN_SECOND_MIKE, JSON.toJSONString(userBean));
                            }
                        });
                        mMikeDownTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                if (mike == 1 && firstMikeUser == null) {
                                    ToastUtil.shortShowToast("当前麦没人上...");
                                    return;
                                }
                                if (mike == 2 && secondMikeUser == null) {
                                    ToastUtil.shortShowToast("当前麦没人上...");
                                    return;
                                }
                                bukaHelper.rpc(null, mike == 1 ? Constant.RPC_END_FIRST_MIKE : Constant.RPC_END_SECOND_MIKE, JSON.toJSONString(mike == 1 ? firstMikeUser : secondMikeUser));
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAsDropDown(mMikeLogo, -popupWindow.getWidth() / 4, 20);
    }

    TextView remainMoney;
    int gifType = 2;

    //礼物弹窗
    private void showGiftPopWindow(final UserBean userBean) {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(FamilyRoomActivity.this, RegistActivity.class));
            return;
        }
        CommonPopupWindow giftPopupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.gift_pop_window)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        remainMoney = view.findViewById(R.id.remainMoney);
                        final ImageView remainLogo = view.findViewById(R.id.remainLogo);
                        final TextView mWalletTv = view.findViewById(R.id.mWalletTv);
                        final TextView mCoinTv = view.findViewById(R.id.mCoinTv);
                        final TextView mDiamondTv = view.findViewById(R.id.mDiamondTv);
                        SimpleDraweeView mHeadImg = view.findViewById(R.id.mHeadImg);
                        TextView mNameTv = view.findViewById(R.id.mNameTv);
                        LinearLayout ageSexLL = view.findViewById(R.id.ageSexLL);
                        ImageView sexLogo = view.findViewById(R.id.sexLogo);
                        TextView mAgeTv = view.findViewById(R.id.mAgeTv);
                        TextView mIdTv = view.findViewById(R.id.mIdTv);
                        final ViewPager viewPager = view.findViewById(R.id.viewpager);
                        final LinearLayout idotLayout = view.findViewById(R.id.ll_dot);
                        LinearLayout chargeLL = view.findViewById(R.id.chargeLL);
                        final LinearLayout mGiftLL = view.findViewById(R.id.mGiftLL);
                        final ImageView addNum = view.findViewById(R.id.addNum);
                        final LinearLayout mNormalGiftLL = view.findViewById(R.id.mNormalGiftLL);
                        final LinearLayout mDiamondGiftLL = view.findViewById(R.id.mDiamondGiftLL);
                        final ImageView yijianzhongqing = view.findViewById(R.id.yijianzhongqing);
                        final ImageView tianchangdijiu = view.findViewById(R.id.tianchangdijiu);
                        final ImageView woaini = view.findViewById(R.id.woaini);
                        final ImageView yishengyishi = view.findViewById(R.id.yishengyishi);
                        final ImageView shiquanshimei = view.findViewById(R.id.shiquanshimei);
                        final ImageView qinqin = view.findViewById(R.id.qinqin);
                        final ImageView xiangni = view.findViewById(R.id.xiangni);
                        final ImageView yaobaobao = view.findViewById(R.id.yaobaobao);
                        if (userBean != null) {
                            mHeadImg.setImageURI(TextUtils.isEmpty(userBean.getHeadUrl()) ? "" : userBean.getHeadUrl());
                            mNameTv.setText(TextUtils.isEmpty(userBean.getNickName()) ? "" : userBean.getNickName());
                            mAgeTv.setText("" + userBean.getAge());
                            mIdTv.setText("ID:" + userBean.getUserId());
                            int sex = userBean.getSex();
                            switch (sex) {
                                case 1:
                                    ageSexLL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_blue_));
                                    sexLogo.setImageResource(R.mipmap.xiangqing_nan1);
                                    break;
                                case 2:
                                    ageSexLL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_pink));
                                    sexLogo.setImageResource(R.mipmap.xiangqing_nv1);
                                    break;
                            }
                        }
                        loadGiftDatas(userBean, 2, viewPager, idotLayout, mNormalGiftLL, mDiamondGiftLL);
                        if (personalInfo != null) {
                            remainMoney.setText(NumberUtils.rounLong(personalInfo.getGold()));
                        }
                        chargeLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(addNum);
                                popupWindow.dismiss();
                                startActivity(new Intent(FamilyRoomActivity.this, WalletActivity.class));
                            }
                        });
                        mWalletTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gifType = 1;
                                remainLogo.setImageResource(R.mipmap.wallet1);
                                loadGiftDatas(userBean, 1, viewPager, idotLayout, mNormalGiftLL, mDiamondGiftLL);
                                giftTypeBg(mWalletTv, mCoinTv, mDiamondTv, mWalletTv);
                                if (personalInfo != null) {
                                    remainMoney.setText(NumberUtils.rounLong(personalInfo.getFubao()));
                                }
                            }
                        });
                        mCoinTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gifType = 2;
                                remainLogo.setImageResource(R.mipmap.coin1);
                                loadGiftDatas(userBean, 2, viewPager, idotLayout, mNormalGiftLL, mDiamondGiftLL);
                                giftTypeBg(mWalletTv, mCoinTv, mDiamondTv, mCoinTv);
                                if (personalInfo != null) {
                                    remainMoney.setText(NumberUtils.rounLong(personalInfo.getGold()));
                                }
                            }
                        });
                        mDiamondTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gifType = 3;
                                remainLogo.setImageResource(R.mipmap.diamond1);
                                loadGiftDatas(userBean, 3, viewPager, idotLayout, mNormalGiftLL, mDiamondGiftLL);
                                giftTypeBg(mWalletTv, mCoinTv, mDiamondTv, mDiamondTv);
                                if (personalInfo != null) {
                                    remainMoney.setText(NumberUtils.rounLong(personalInfo.getDiamond()));
                                }
                            }
                        });
                        yijianzhongqing.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendGift(userBean, 1);
                            }
                        });
                        tianchangdijiu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendGift(userBean, 999);
                            }
                        });
                        woaini.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendGift(userBean, 520);
                            }
                        });
                        yishengyishi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendGift(userBean, 1314);
                            }
                        });
                        shiquanshimei.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendGift(userBean, 10);
                            }
                        });
                        qinqin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendGift(userBean, 77);
                            }
                        });
                        xiangni.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendGift(userBean, 365);
                            }
                        });
                        yaobaobao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendGift(userBean, 188);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        giftPopupWindow.showAtLocation(contentListView, Gravity.BOTTOM, 0, 0);
    }

    private void sendGift(final UserBean userBean, final int giftNum) {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(this, RegistActivity.class));
            return;
        }
        NetUtils.getInstance().sendGift(userBean.getUserId(), giftModel.getGiftId(), giftNum, user.getUserId(), DeviceUtils.getDeviceId(this), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                ToastUtil.shortShowToast(msg);
                Tools.dismissWaitDialog();
                try {
                    initRemainMoney();// 刷新
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    long sendExp = data.getLong("sendExp");
                    long sendPlut = data.getLong("sendPlut");
                    long accCharm = data.getLong("accCharm");
                    Tools.sendGift(userBean.getPhone(), giftModel.getGiftType(), giftModel.getGiftPrice() + "", giftModel.getGiftName(), giftModel.getGiftUrl(), giftNum + "", sendExp + "", sendPlut + "", accCharm + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
                Tools.dismissWaitDialog();
            }
        }, null);
    }

    //礼物数据加载
    private void loadGiftDatas(final UserBean userBean, int giftType, final ViewPager viewPager, final LinearLayout idotLayout, final LinearLayout mNormalGiftLL, final LinearLayout mDiamondGiftLL) {
        NetUtils.getInstance().giftList(giftType, -1, -1, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                GiftList giftList = (GiftList) model.getModel();
                if (giftList != null) {
                    mDataList.clear();
                    mDataList.addAll(giftList.getContent());
                    initValues(userBean, viewPager, idotLayout, mNormalGiftLL, mDiamondGiftLL);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, GiftList.class);
    }

    //礼物视图加载
    private void initValues(final UserBean userBean, ViewPager viewPager, final LinearLayout idotLayout, final LinearLayout mNormalGiftLL, final LinearLayout mDiamondGiftLL) {
        final int pageSize = 9;//每一页显示的个数
        int pageCount = (int) Math.ceil(mDataList.size() * 1.0 / pageSize);//总的页数=总数/每页数量，并取整
        final GridViewAdapter[] arr = new GridViewAdapter[pageCount];
        final List<View> mPagerList = new ArrayList<View>();
        for (int j = 0; j < pageCount; j++) {
            final GridView gridview = (GridView) LayoutInflater.from(this).inflate(R.layout.bottom_vp_gridview, viewPager, false);
            final GridViewAdapter gridAdapter = new GridViewAdapter(this, mDataList, j);
            gridview.setAdapter(gridAdapter);
            arr[j] = gridAdapter;
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        giftModel = mDataList.get(i);
                        int giftType = giftModel.getGiftType();
                        if (giftType == 3) {
                            mNormalGiftLL.setVisibility(View.GONE);
                            mDiamondGiftLL.setVisibility(View.VISIBLE);
                        } else {
                            if (i == id) {
                                if (giftModel.isSelected()) {
                                    giftModel.setSelected(false);
                                } else {
                                    giftModel.setSelected(true);
                                    sendGift(userBean, 1);
                                    gridAdapter.notifyDataSetChanged();
                                }
                            } else {
                                giftModel.setSelected(false);
                            }
                        }
                    }
                }
            });
            mPagerList.add(gridview);
        }
        viewPager.setAdapter(new ViewPagerAdapter(mPagerList, FamilyRoomActivity.this));
        idotLayout.removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            idotLayout.addView(LayoutInflater.from(this).inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        idotLayout.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                arr[position].notifyDataSetChanged();
                // 取消圆点选中
                idotLayout.getChildAt(index)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                idotLayout.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                index = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    //礼物福宝、钻石、金币按钮点击
    private void giftTypeBg(TextView mWalletTv, TextView mCoinTv, TextView mDiamondTv, TextView mSelectedTv) {
        mWalletTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_line));
        mWalletTv.setTextColor(getResources().getColor(R.color.app_color));
        mCoinTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_line));
        mCoinTv.setTextColor(getResources().getColor(R.color.app_color));
        mDiamondTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_line));
        mDiamondTv.setTextColor(getResources().getColor(R.color.app_color));
        mSelectedTv.setBackground(getResources().getDrawable(R.drawable.shape_solid_app_corlor_));
        mSelectedTv.setTextColor(getResources().getColor(R.color.white));
    }

    //在线列表弹窗
    private void showOnLinePopWindow(final UserBean userBean) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.layout_on_line)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        final SimpleDraweeView mHeadImg = view.findViewById(R.id.mHeadImg);
                        mHeadImg.setImageURI(TextUtils.isEmpty(userBean.getHeadUrl()) ? "" : userBean.getHeadUrl());
                        final LinearLayout mImg1 = view.findViewById(R.id.mImg1);
                        final LinearLayout mImg2 = view.findViewById(R.id.mImg2);
                        final LinearLayout mImg3 = view.findViewById(R.id.mImg3);
                        final LinearLayout mImg4 = view.findViewById(R.id.mImg4);
                        final LinearLayout mImg5 = view.findViewById(R.id.mImg5);
                        final LinearLayout mImg6 = view.findViewById(R.id.mImg6);
                        final TextView toBeManagerTv = view.findViewById(R.id.toBeManagerTv);
                        final TextView forbidSpeakTv = view.findViewById(R.id.forbidSpeakTv);
                        final User user = MyApplication.getInstance().user;
                        List<LinearLayout> imgViews = new ArrayList<>();
                        imgViews.add(mImg1);
                        imgViews.add(mImg2);
                        imgViews.add(mImg3);
                        imgViews.add(mImg4);
                        imgViews.add(mImg5);
                        imgViews.add(mImg6);
                        showOpenAnim(PixelUtil.dp2px(FamilyRoomActivity.this, 100), imgViews);
                        mImg1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg1);
                                popupWindow.dismiss();
                                if (FamilyRoomActivity.this.userBean.getUserType() > userBean.getUserType() || FamilyRoomActivity.this.userBean.getUserType() > 2) {
                                    ToastUtil.shortShowToast("权限不足...");
                                    return;
                                }
                                if (userBean.getMike() == 1 || userBean.getMike() == 2) {
                                    ToastUtil.shortShowToast("该用户已上麦，请勿重复操作...");
                                    return;
                                }
                                if (firstMikeUser != null) {
                                    if (FamilyRoomActivity.this.userBean.getUserType() >= firstMikeUser.getUserType()) {
                                        ToastUtil.shortShowToast("一麦已经有人上了...");
                                        return;
                                    }
                                    bukaHelper.rpc(null, Constant.RPC_END_FIRST_MIKE, JSON.toJSONString(firstMikeUser));
                                }
                                bukaHelper.rpc(null, Constant.RPC_BEGIN_FIRST_MIKE, JSON.toJSONString(userBean));
                            }
                        });
                        mImg2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg2);
                                popupWindow.dismiss();
                                if (FamilyRoomActivity.this.userBean.getUserType() > userBean.getUserType() || FamilyRoomActivity.this.userBean.getUserType() > 2) {
                                    ToastUtil.shortShowToast("权限不足...");
                                    return;
                                }
                                if (userBean.getMike() == 1 || userBean.getMike() == 2) {
                                    ToastUtil.shortShowToast("该用户已上麦，请勿重复操作...");
                                    return;
                                }
                                if (secondMikeUser != null) {
                                    if (FamilyRoomActivity.this.userBean.getUserType() >= secondMikeUser.getUserType()) {
                                        ToastUtil.shortShowToast("二麦已经有人上了...");
                                        return;
                                    }
                                    bukaHelper.rpc(null, Constant.RPC_END_SECOND_MIKE, JSON.toJSONString(secondMikeUser));
                                }
                                bukaHelper.rpc(null, Constant.RPC_BEGIN_SECOND_MIKE, JSON.toJSONString(userBean));
                            }
                        });
                        mImg3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg3);
                                if (user == null) {
                                    ToastUtil.shortShowToast("请先登录...");
                                    startActivity(new Intent(FamilyRoomActivity.this, RegistActivity.class));
                                    return;
                                }
                                if (userBean.getUserId() == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能和自己聊天哦...");
                                    return;
                                }
                                Intent intent3 = new Intent(FamilyRoomActivity.this, ChatActivity.class);
                                intent3.putExtra(com.qianyu.chatuidemo.Constant.EXTRA_USER_ID, userBean.getPhone());
                                startActivity(intent3);
                            }
                        });
                        mImg4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg4);
                                if (userBean.getUserId() == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能给自己送礼哦...");
                                    return;
                                }
                                popupWindow.dismiss();
                                showGiftPopWindow(userBean);
                            }
                        });
                        mImg5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg5);
                                if (userBean.getUserId() == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能对自己使用技能哦...");
                                    return;
                                }
                                popupWindow.dismiss();
                                Intent intent = new Intent(FamilyRoomActivity.this, SkillActivity.class);
                                intent.putExtra("userId", userBean.getUserId());
                                startActivity(intent);
                            }
                        });
                        mImg6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg6);
                                if (userBean.getUserId() == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能屏蔽自己哦！");
                                    return;
                                }
                                popupWindow.dismiss();
                                bukaHelper.getNoSeeIds().add(userBean);
                            }
                        });
                        mHeadImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SpringUtils.springAnim(mHeadImg);
                                Intent intent = new Intent(FamilyRoomActivity.this, PersonalPageActivity.class);
                                intent.putExtra("userId", userBean.getUserId());
                                startActivity(intent);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(contentListView, Gravity.CENTER, 0, 0);
    }

    //在线列表动画效果
    private void showOpenAnim(int px, List<LinearLayout> imgViews) {
        for (int i = 0; i < imgViews.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            double a = (i < 3 ? -1 : 1) * Math.cos(45 * Math.PI / 180 * (i < 3 ? i : (i - 3)));
            double b = (i < 3 ? -1 : 1) * Math.sin(45 * Math.PI / 180 * (i < 3 ? i : (i - 3)));
            double x = a * px;
            double y = b * px;
            set.playTogether(
                    ObjectAnimator.ofFloat(imgViews.get(i), "translationX", 0, (float) x),
                    ObjectAnimator.ofFloat(imgViews.get(i), "translationY", 0, (float) y)
                    , ObjectAnimator.ofFloat(imgViews.get(i), "alpha", 0, 1).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100);
            set.start();
        }
    }

    // 消消乐弹窗
    private void showXxlPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.activity_web)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1.0f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RelativeLayout mHeadView = (RelativeLayout) view.findViewById(R.id.head_view);
                        ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
                        iv_close.setVisibility(View.VISIBLE);
                        mHeadView.setVisibility(View.GONE);
                        LinearLayout llayDiss = (LinearLayout) view.findViewById(R.id.ly_back);
                        xxlWebview = (ProgressWebView) view.findViewById(R.id.baseweb_webview);
                        xxlWebview.setBackgroundColor(0);
                        xxlWebview.getBackground().setAlpha(0);
                        User user = MyApplication.getInstance().user;
//                        http://localhost:8888/sanxiao/index.html?userId=80&headUrl=&nickName=;
                        xxlWebview.loadUrl(APPURL.BASE_H5_URL_XXL + "gameType=10000&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&headUrl=" + user.getHeadUrl()
                                + "&nickName=" + user.getNickName() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(FamilyRoomActivity.this));
                        Tools.showDialog(FamilyRoomActivity.this);
                        iv_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        llayDiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Tools.dismissWaitDialog();
                                popupWindow.dismiss();
                            }
                        });
                        xxlWebview.setOnH5Listener(new ProgressWebView.OnH5Listener() {
                            @Override
                            public void onH5Listener(String message) {
                                try {
                                    JSONObject jsonObject = new JSONObject(message);
                                    String type = jsonObject.getString("type");
                                    switch (type) {
                                        case "loading":
                                            Tools.dismissWaitDialog();
                                            break;
                                        case "shareBySanxiao":
                                            // 拉起分享
                                            share();
                                            break;
//                                        case "ShoppingMall":
//                                            Intent intent = new Intent(FamilyRoomActivity.this, HotMallActivity.class);
//                                            startActivityForResult(intent, 100);
//                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(contentListView, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (xxlWebview != null) {
                    // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再 // destory()
                    ViewParent parent = xxlWebview.getParent();
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(xxlWebview);
                    }
                    xxlWebview.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                    xxlWebview.getSettings().setJavaScriptEnabled(false);
                    xxlWebview.clearHistory();
                    xxlWebview.clearView();
                    xxlWebview.removeAllViews();
                    xxlWebview.destroy();
                }
            }
        });
    }

    //大转盘弹窗
    private void showLuckPanPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.activity_web)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1.0f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RelativeLayout mHeadView = (RelativeLayout) view.findViewById(R.id.head_view);
                        mHeadView.setVisibility(View.GONE);
                        luckPanWebView = (ProgressWebView) view.findViewById(R.id.baseweb_webview);
                        luckPanWebView.setBackgroundColor(0);
                        luckPanWebView.getBackground().setAlpha(0);
                        User user = MyApplication.getInstance().user;
                        luckPanWebView.loadUrl(APPURL.BASE_H5_URL + "gameType=10000&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(FamilyRoomActivity.this));
                        Tools.showDialog(FamilyRoomActivity.this);
                        luckPanWebView.setOnH5Listener(new ProgressWebView.OnH5Listener() {
                            @Override
                            public void onH5Listener(String message) {
                                try {
                                    JSONObject jsonObject = new JSONObject(message);
                                    String type = jsonObject.getString("type");
                                    switch (type) {
                                        case "loading":
                                            Tools.dismissWaitDialog();
                                            break;
                                        case "closeLuckDraw":
                                            popupWindow.dismiss();
                                            break;
                                        case "ShoppingMall":
                                            Intent intent = new Intent(FamilyRoomActivity.this, HotMallActivity.class);
                                            startActivityForResult(intent, 100);
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(contentListView, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (luckPanWebView != null) {
                    // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再 // destory()
                    ViewParent parent = luckPanWebView.getParent();
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(luckPanWebView);
                    }
                    luckPanWebView.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                    luckPanWebView.getSettings().setJavaScriptEnabled(false);
                    luckPanWebView.clearHistory();
                    luckPanWebView.clearView();
                    luckPanWebView.removeAllViews();
                    luckPanWebView.destroy();
                }
            }
        });
    }

    //boss弹窗
    private void showBossPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.activity_web)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1.0f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RelativeLayout mHeadView = (RelativeLayout) view.findViewById(R.id.head_view);
                        mHeadView.setVisibility(View.GONE);
                        bossWebView = (ProgressWebView) view.findViewById(R.id.baseweb_webview);
                        bossWebView.setBackgroundColor(0);
                        bossWebView.getBackground().setAlpha(0);
                        User user = MyApplication.getInstance().user;
                        bossWebView.loadUrl(APPURL.BASE_H5_URL + "gameType=10005&userId=" + user.getUserId() + "&groupId=" + userBean.getGroupId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(FamilyRoomActivity.this));
                        Tools.showDialog(FamilyRoomActivity.this);
                        bossWebView.setOnH5Listener(new ProgressWebView.OnH5Listener() {
                            @Override
                            public void onH5Listener(String message) {
                                try {
                                    JSONObject jsonObject = new JSONObject(message);
                                    String type = jsonObject.getString("type");
                                    switch (type) {
                                        case "loading":
                                            Tools.dismissWaitDialog();
                                            break;
                                        case "closeGroupBoss":
                                            popupWindow.dismiss();
                                            initRemainMoney();// 刷新
                                            break;
                                        case "updateSkill":
//                                            JSONObject value = jsonObject.getJSONObject("value");
//                                            String skillType = value.getString("skillType");
                                            startActivity(new Intent(FamilyRoomActivity.this, SkillDetailActivity.class));
                                            break;
                                        case "GetMoreFubao"://获取福宝
                                            JSONObject value = jsonObject.getJSONObject("value");
                                            long fubao = value.getLong("fubao");
                                            Intent intent = new Intent(FamilyRoomActivity.this, FubowGetActivity.class);
                                            intent.putExtra("fubao", fubao);
                                            startActivityForResult(intent, 100);
                                            break;
                                        case "GetMoreCoins"://获取金币
                                            JSONObject value1 = jsonObject.getJSONObject("value");
                                            long gold = value1.getLong("gold");
                                            Intent intent1 = new Intent(FamilyRoomActivity.this, CoinGetActivity.class);
                                            intent1.putExtra("coin", gold);
                                            startActivityForResult(intent1, 100);
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(contentListView, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (bossWebView != null) {
                    // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再 // destory()
                    ViewParent parent = bossWebView.getParent();
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(bossWebView);
                    }
                    bossWebView.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                    bossWebView.getSettings().setJavaScriptEnabled(false);
                    bossWebView.clearHistory();
                    bossWebView.clearView();
                    bossWebView.removeAllViews();
                    bossWebView.destroy();
                    initRemainMoney();// 刷新
                }
            }
        });
    }

    private void showPersonalInfoPopWindow(final UserBean userBean) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.personal_info_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        final SimpleDraweeView mHeadImg = view.findViewById(R.id.mHeadImg);
                        final TextView mNameTv = view.findViewById(R.id.mNameTv);
                        final TextView mAgeTv = view.findViewById(R.id.mAgeTv);
                        final TextView mLevelTv = view.findViewById(R.id.mLevelTv);
                        final TextView mLocationTv = view.findViewById(R.id.mLocationTv);
                        final TextView mIdNumTv = view.findViewById(R.id.mIdNumTv);
                        final TextView mRickRankTv = view.findViewById(R.id.mRickRankTv);
                        final TextView mCharmRankTv = view.findViewById(R.id.mCharmRankTv);
                        final LinearLayout mFirendDetailLL = view.findViewById(R.id.mFirendDetailLL);
                        final MyRecylerView mSkillRecylerView = view.findViewById(R.id.skillRecylerView);
                        final MyRecylerView mCircleRecylerView = view.findViewById(R.id.circleRecylerView);
                        final LinearLayout atTaLL = view.findViewById(R.id.atTaLL);
                        final LinearLayout giftLL = view.findViewById(R.id.giftLL);
                        final LinearLayout skillLL = view.findViewById(R.id.skillLL);
                        final ImageView userOfficial = view.findViewById(R.id.userOfficial);
                        final User user = MyApplication.getInstance().user;
                        giftLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(giftLL);
                                if (userBean.getUserId() == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能给自己送礼哦...");
                                    return;
                                }
                                popupWindow.dismiss();
                                showGiftPopWindow(userBean);
                            }
                        });
                        skillLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(skillLL);
                                if (userBean.getUserId() == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能对自己使用技能哦...");
                                    return;
                                }
                                popupWindow.dismiss();
                                Intent intent = new Intent(FamilyRoomActivity.this, SkillActivity.class);
                                intent.putExtra("userId", userBean.getUserId());
                                startActivity(intent);
                            }
                        });
                        mFirendDetailLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(FamilyRoomActivity.this, MyCircleActivity.class);
                                intent.putExtra("userId", userBean.getUserId());
                                startActivity(intent);
                            }
                        });
                        mHeadImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(FamilyRoomActivity.this, PersonalPageActivity.class);
                                intent.putExtra("userId", userBean.getUserId());
                                startActivity(intent);
                            }
                        });
                        atTaLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(atTaLL);
                                if (userBean.getUserId() == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能私聊自己哦...");
                                    return;
                                }
                                if (TextUtils.isEmpty(userBean.getPhone())) {
                                    return;
                                }
                                popupWindow.dismiss();
                                slidingMenu.closeMenu();
                                Intent intent = new Intent(FamilyRoomActivity.this, ChatActivity.class);
                                intent.putExtra(com.qianyu.chatuidemo.Constant.EXTRA_USER_ID, userBean.getPhone());
                                startActivity(intent);
                            }
                        });
                        NetUtils.getInstance().familyUserInfo(userBean.getUserId(), new NetCallBack() {
                            @Override
                            public void onSuccess(String result, String msg, ResultModel model) {
                                Log.e("FAMILYrOOMaCI",result);
                                UserInfo familyUserInfo = model.getModel();
                                if (familyUserInfo != null) {
                                    UserInfo.UserInfoBean userInfo = familyUserInfo.getUserInfo();
                                    if (userInfo != null) {
                                        mHeadImg.setImageURI(TextUtils.isEmpty(userInfo.getHeadUrl()) ? "" : userInfo.getHeadUrl());
                                        mNameTv.setText(TextUtils.isEmpty(userInfo.getNickName()) ? "" : userInfo.getNickName());
                                        mAgeTv.setText(userInfo.getAge() + "岁");
                                        mLevelTv.setText(userInfo.getLevel() + "级");
                                        mLocationTv.setText(TextUtils.isEmpty(userInfo.getCurrentAddress()) ? "" : userInfo.getCurrentAddress());
                                        mIdNumTv.setText(userInfo.getUserId() + "");
                                        mRickRankTv.setText(userInfo.getRichNum() + "");
                                        mCharmRankTv.setText(userInfo.getCharmNum() + "");
                                        userOfficial.setVisibility(userInfo.getExpand() > 0 ? View.VISIBLE : View.GONE);
                                    }
                                    initCircleRecylerView(familyUserInfo, mCircleRecylerView, mSkillRecylerView);
                                }
                            }

                            @Override
                            public void onFail(String code, String msg, String result) {

                            }
                        }, UserInfo.class);
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(contentListView, Gravity.BOTTOM, 0, 0);
    }

    //个人信息的动态和技能列表
    private void initCircleRecylerView(final UserInfo userInfo, MyRecylerView mCircleRecylerView, MyRecylerView mSkillRecylerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.HORIZONTAL);
        mCircleRecylerView.setHasFixedSize(true);
        mCircleRecylerView.setLayoutManager(layoutManager);
        if (userInfo.getUserCircle() != null) {
            String fileItemUrl = userInfo.getUserCircle().getFileItemUrl();
            if (!TextUtils.isEmpty(fileItemUrl)) {
                try {
                    List<String> urls = new ArrayList<>();
                    String[] strings = fileItemUrl.split(",");
                    for (int i = 0; i < strings.length; i++) {
                        urls.add(strings[i]);
                    }
                    InfoCircleAdapter adapter = new InfoCircleAdapter(this, urls);
                    mCircleRecylerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, List data, int position) {
                            if (userInfo != null && userInfo.getUserInfo() != null) {
                                Intent intent = new Intent(FamilyRoomActivity.this, MyCircleActivity.class);
                                intent.putExtra("userId", userInfo.getUserInfo().getUserId());
                                startActivity(intent);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager2.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mSkillRecylerView.setHasFixedSize(true);
        mSkillRecylerView.setLayoutManager(layoutManager2);
        if (userInfo.getSkillMap() != null && userInfo.getSkillMap().size() > 0) {
            mSkillRecylerView.setAdapter(new SkillBgAdapter(this, userInfo.getSkillMap()));
        }
    }

    //聊天表情
    private void initEmotions() {
        EmotionComplateFragment emotionComplateFragment = new EmotionComplateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EmotionComplateFragment.EMOTION_MAP_TYPE, EmotionUtils.EMOTION_CLASSIC_TYPE);
        emotionComplateFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.emotionContainer, emotionComplateFragment);
        transaction.commit();
        //初始化EmotionKeyboard
        mEmotionKeyboard = EmotionKeyboard.with(this)
                .setEmotionView(findViewById(R.id.emotionContainer))//绑定表情面板
                .bindToContent(mFrameLL)//绑定内容view
                .bindToEditText(((EditText) findViewById(R.id.et_sendmessage)))//判断绑定那种EditView
                .bindToEmotionButton(findViewById(R.id.rl_face))//绑定表情按钮
                .build();
//        点击表情的全局监听管理类
        globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance();
        //绑定EditText
        globalOnItemClickManager.attachToEditText(etSendmessage);
    }

    //用户在线列表和聊天列表
    private void initUserAndChatRecylerView() {
        contentAdapter = new ContentAdapter(this);
        contentListView.setAdapter(contentAdapter);
        contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                KeyBoardUtils.hideSoftInput(etSendmessage);
            }
        });
        contentListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                KeyBoardUtils.hideSoftInput(etSendmessage);
                emotionContainer.setVisibility(View.GONE);
                slidingMenu.closeMenu();
                return false;
            }
        });

        userAdapter = new UserAdapter(this);
        userListView.setAdapter(userAdapter);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv.buka.sdk.entity.User user = userAdapter.getmList().get(i);
                UserBean userBean = new UserBean(user.getUser_extend());
                if (atFlag) {
                    slidingMenu.closeMenu();
                    etSendmessage.setText("@" + userBean.getNickName() + " ");
                    etSendmessage.setSelection(userBean.getNickName().length() + 2);
                    atPhone = userBean.getPhone();
                } else {
                    showOnLinePopWindow(userBean);
                }
            }
        });
        contentAdapter.setOnPersonalInfoListener(new ContentAdapter.OnPersonalInfoListener() {
            @Override
            public void onPersonalInfo(UserBean userBean) {
                showPersonalInfoPopWindow(userBean);
            }
        });
    }

    //申请权限
    private void requestPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionList = new ArrayList<>();
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (this.checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CALL_PHONE);
            }
            if (this.checkSelfPermission(Manifest.permission.READ_LOGS)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_LOGS);
            }
            if (this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (this.checkSelfPermission(Manifest.permission.SET_DEBUG_APP)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.SET_DEBUG_APP);
            }
            if (this.checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
            }
            if (this.checkSelfPermission(Manifest.permission.GET_ACCOUNTS)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.GET_ACCOUNTS);
            }
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CAMERA);
            }
            if (this.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.RECORD_AUDIO);
            }
            if (this.checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
            }
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 100);
        }
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, permissions[i] + "，权限别用户拒绝了。", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (bukaHelper != null) {
            bukaHelper.exit();
        }
        globalOnItemClickManager.unAttachToEditText();
        if (mTimer1 != null) {
            mTimer1.cancel();
            mTimer1.onFinish();
        }
        if (mTimer2 != null) {
            mTimer2.cancel();
            mTimer2.onFinish();
        }
        if (mTimer3 != null) {
            mTimer3.cancel();
            mTimer3.onFinish();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (emotionContainer.getVisibility() == View.VISIBLE) {
                emotionContainer.setVisibility(View.GONE);
                return true;
            } else {
                new AlertDialog.Builder(FamilyRoomActivity.this).setTitle("退出聊天室?")
                        .setMessage("您是否确定退出聊天室？")
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                bukaHelper.exit();
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        /**
         * 按下返回键，如果表情显示，则隐藏，没有显示则回退页面
         */
        AppLog.e("=========onBackPressed==========" + mEmotionKeyboard.interceptBackPress());
        if (!mEmotionKeyboard.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    //分享
    private void share() {
        User user = MyApplication.getInstance().user;
        UMImage image = new UMImage(FamilyRoomActivity.this, com.qianyu.communicate.R.drawable.sharelogo);
        UMWeb web = new UMWeb(APPURL.BASE_SHARE_URL + "gameType=10003&headUrl=" + user.getHeadUrl() + "&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" +
                user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(FamilyRoomActivity.this));
        web.setTitle("千语分享");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("据说有实力的人，可以让任何人闭嘴。");//描述
        new ShareAction(FamilyRoomActivity.this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .open();
    }

    //分享
    private void share(LiveRoomShare liveRoomShare) {
//        UMImage image = new UMImage(ChatRoomUserActivity.this, imgUrl);
        UMImage image = new UMImage(FamilyRoomActivity.this, TextUtils.isEmpty(liveRoomShare.getFamilyPath()) ? "" : liveRoomShare.getFamilyPath());
        UMWeb web = new UMWeb(TextUtils.isEmpty(liveRoomShare.getUrl()) ? "" : liveRoomShare.getUrl());
        web.setTitle(TextUtils.isEmpty(liveRoomShare.getFamilyName()) ? "" : liveRoomShare.getFamilyName());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(TextUtils.isEmpty(liveRoomShare.getFamilyDescribe()) ? "" : liveRoomShare.getFamilyDescribe());//描述
        new ShareAction(FamilyRoomActivity.this)
//                .withText("千语")
                .withMedia(web)
//                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                .setCallback(shareListener)
                .open();
    }

    //分享回调
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            if (platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE) {
                if (!isAppInstalled(FamilyRoomActivity.this, "com.tencent.mobileqq") && !isAppInstalled(FamilyRoomActivity.this, "com.tencent.tim")) {
                    ToastUtil.shortShowToast("请先安装QQ或者TIM客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                if (!isAppInstalled(FamilyRoomActivity.this, "com.tencent.mm")) {
                    ToastUtil.shortShowToast("请先安装微信客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.SINA) {
                if (!isAppInstalled(FamilyRoomActivity.this, "com.sina.weibo")) {
                    ToastUtil.shortShowToast("请先安装新浪微博客户端");
                    return;
                }
            }
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(FamilyRoomActivity.this, "成功了", Toast.LENGTH_LONG).show();
            NetUtils.getInstance().appShare(new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("eventType", "shareBySanxiao");
                        jsonObject.put("value", "success");
                        if (xxlWebview != null) {
                            Log.e("分享成功", "分享成功");
                            xxlWebview.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, null);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(FamilyRoomActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(FamilyRoomActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("eventType", "backupEvent");
                if (luckPanWebView != null) {
                    jsonObject.put("value", "GROTARY");
                    luckPanWebView.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                }
                if (bossWebView != null) {
                    jsonObject.put("value", "GROUPBOSS");
                    bossWebView.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
