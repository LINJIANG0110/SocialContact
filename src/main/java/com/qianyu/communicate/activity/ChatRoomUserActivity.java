package com.qianyu.communicate.activity;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.BounceInterpolator;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.Button;
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

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.chatuidemo.ui.ChatActivity;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.GiftNumAdapter;
import com.qianyu.communicate.adapter.GridViewAdapter;
import com.qianyu.communicate.adapter.InfoCircleAdapter;
import com.qianyu.communicate.adapter.PraiseListAdapter;
import com.qianyu.communicate.adapter.ViewPagerAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.BukaHelper;
import com.qianyu.communicate.bukaSdk.MediaManager;
import com.qianyu.communicate.bukaSdk.adapter.ContentAdapter;
import com.qianyu.communicate.bukaSdk.adapter.UserAdapter;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil;
import com.qianyu.communicate.bukaSdk.entity.DocPagerEntity;
import com.qianyu.communicate.bukaSdk.entity.DocRpcBean;
import com.qianyu.communicate.bukaSdk.entity.StreamInfoEntity;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.emotions.fragments.EmotionComplateFragment;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.GlobalOnItemClickManagerUtils;
import com.qianyu.communicate.emotions.widget.EmotionKeyboard;
import com.qianyu.communicate.entity.FamilyInfo;
import com.qianyu.communicate.entity.GiftList;
import com.qianyu.communicate.entity.GiftModel;
import com.qianyu.communicate.entity.LiveRoomShare;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.PraiseModel;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.giftlibrary.CustormAnim;
import com.qianyu.communicate.giftlibrary.GiftControl;
import com.qianyu.communicate.jzvd.JZVideoPlayer;
import com.qianyu.communicate.jzvd.JZVideoPlayerStandard;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.DragViewGroup;
import com.qianyu.communicate.views.MyRecylerView;
import com.qianyu.communicate.views.SlidingMenu;
import com.qianyu.communicate.views.webView.MyWebChromeClient;
import com.qianyu.communicate.views.webView.MyWebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.PixelUtil;
import org.haitao.common.utils.ToastUtil;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.BukaSDKManager;
import tv.buka.sdk.entity.Rpc;
import tv.buka.sdk.entity.Status;
import tv.buka.sdk.listener.ReceiptListener;
import tv.buka.sdk.utils.JsonUtils;

import static com.bkrtc_sdk.bkrtc_impl.GetInstance;
import static com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil.WHITE_BOARD;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class ChatRoomUserActivity extends BaseActivity {
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mNameTv)
    TextView mNameTv;
    @InjectView(R.id.mJobTitle)
    TextView mJobTitle;
    @InjectView(R.id.mTopicTv)
    TextView mTopicTv;
    @InjectView(R.id.mStartLL)
    LinearLayout mStartLL;
    @InjectView(R.id.mExitTopicFl)
    FrameLayout mExitTopicFl;
    @InjectView(R.id.mTopicTv_)
    TextView mTopicTv_;
    @InjectView(R.id.mStartTimeTv)
    TextView mStartTimeTv;
    @InjectView(R.id.mFrameLL1)
    FrameLayout mFrameLL1;
    @InjectView(R.id.mFrameLL2)
    FrameLayout mFrameLL2;
    @InjectView(R.id.mFrameLL3)
    FrameLayout mFrameLL3;
    @InjectView(R.id.mHeadImg1)
    SimpleDraweeView mHeadImg1;
    @InjectView(R.id.mHeadImg2)
    SimpleDraweeView mHeadImg2;
    @InjectView(R.id.mHeadImg3)
    SimpleDraweeView mHeadImg3;
    @InjectView(R.id.mRecylerView)
    ListView contentListView;
    @InjectView(R.id.mHeadImgVoice)
    SimpleDraweeView mHeadImgVoice;
    @InjectView(R.id.et_sendmessage)
    EditText etSendmessage;
    @InjectView(R.id.iv_face_normal)
    ImageView ivFaceNormal;
    @InjectView(R.id.iv_face_checked)
    ImageView ivFaceChecked;
    @InjectView(R.id.joinSpeakLogo)
    ImageView joinSpeakLogo;
    @InjectView(R.id.rl_face)
    RelativeLayout rlFace;
    @InjectView(R.id.btn_more)
    Button btnMore;
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
    @InjectView(R.id.mCoseVideo)
    ImageView mCoseVideo;
    @InjectView(R.id.mFullVideo)
    ImageView mFullVideo;
    @InjectView(R.id.mVideoFL)
    DragViewGroup mVideoFL;
    @InjectView(R.id.mTopicLL)
    LinearLayout mTopicLL;
    @InjectView(R.id.mOnLineLL)
    LinearLayout mOnLineLL;
    @InjectView(R.id.stream_layout)
    LinearLayout mStreamLayout;
    @InjectView(R.id.stream_layout_)
    LinearLayout mStreamLayout_;
    @InjectView(R.id.mContentTv)
    TextView mContentTv;
    @InjectView(R.id.mTotalPersonTv)
    TextView mTotalPersonTv;
    @InjectView(R.id.mOnLineList)
    TextView mOnLineList;
    @InjectView(R.id.mOnLineTv)
    TextView mOnLineTv;
    @InjectView(R.id.concernLogo)
    ImageView concernLogo;
    @InjectView(R.id.emotionContainer)
    FrameLayout emotionContainer;
    @InjectView(R.id.ll_gift_parent)
    LinearLayout giftParent;
    @InjectView(R.id.fl_content)
    FrameLayout mFlContext;
    @InjectView(R.id.fl_content_video)
    RelativeLayout mFlContentVideo;
    @InjectView(R.id.rl_content_web)
    RelativeLayout mRlContentWeb;
    @InjectView(R.id.webview)
    MyWebView mWebView;
    @InjectView(R.id.tv_page)
    TextView mTvPage;
    @InjectView(R.id.iv_previous)
    ImageView mIvPrevious;
    @InjectView(R.id.iv_next)
    ImageView mIvNext;
    @InjectView(R.id.iv_close)
    ImageView mIvClose;
    @InjectView(R.id.mWendaLogo)
    ImageView mWendaLogo;
    @InjectView(R.id.mPraiseLogo)
    ImageView mPraiseLogo;
    @InjectView(R.id.jz_video)
    JZVideoPlayerStandard jzVideo;
    private ContentAdapter contentAdapter;  //聊天室消息adapter
    private UserAdapter userAdapter;        //聊天室在线人adapter
    private UserBean userBean;              //进入聊天室的人及其相关信息
    private String whether;                 //当前用户是否关注该主播
    private EmotionKeyboard mEmotionKeyboard;   //表情相关
    private GlobalOnItemClickManagerUtils globalOnItemClickManager; //表情相关
    private int usersPice;//当前用户余额
    private int userId; //主播id
    private FamilyInfo familyInfo;//主播信息
    private List<GiftList.SouvenirNosBean> souvenirNos;  //礼物数量集合，1314等
    private GiftModel giftModel;        //礼物model
    private GiftControl giftControl;    //展示礼物连击效果控制器
    private String signal;              //当前用户是否关注头像所点击出来的用户
    private BukaHelper bukaHelper;      //布卡sdk工具类
    private CommonPopupWindow joinSpeakPopupWindow; //连麦弹窗
    private int mVideoWidth;
    private int mVideoHeight;
    private static final int ROOM_VIDEO_NUM = 7;
    private static final int DEFAULT_VIDEO_NUM = ROOM_VIDEO_NUM - 3;
    private DocPagerEntity mDocPagerEntity; //文档类
    private MediaManager mMediaManager;
    private boolean docFlag = true;     //关闭文档后不再接受文档信令
    private boolean publishFlag = false;    //是否已经推流
    private boolean isLoad = false;//是否正在加载文档
    private DocRpcBean docRpcBean;//rpc信令接收到的文档类
    private boolean isPPt;//是否是ppt
    private String mDocUrl;//全局webviewurl
    private int mCreateErrorNum = 0;
    private int MAX_CREATE_COUNT = 3;
    private boolean drag;
    private boolean giftFlag = false;
    private boolean closePublishFlag = false;
    private boolean pcPlay = false;
    double prise = 0;
    private boolean atFlag = true;
    private String typeShare = "0";

    @Override
    public int getRootViewId() {
        return R.layout.activity_chat_room_user;
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.MINE_TAB) {
            if (prise > 0) {
                PraiseModel praiseModel = new PraiseModel();
                praiseModel.setLiveName(familyInfo.getNickName());
                User user = MyApplication.getInstance().user;
                praiseModel.setUserName(user.getNickName());
                praiseModel.setPrise(prise);
                bukaHelper.rpc(null, Constant.PRAISE_SUCCESS, JsonUtils.toJson(praiseModel));
                prise = 0;
            }
            loadGifts();
        }
//        else if(event.getState() == EventBuss.PRAISE_SUCCES){
//            PraiseModel praiseModel = new PraiseModel();
//            praiseModel.setLiveName(familyInfo.getNickName());
//            User user = MyApplication.getInstance().user;
//            praiseModel.setUserName(user.getNickName());
//            praiseModel.setPrise(prise);
//            bukaHelper.rpc(null, Constant.PRAISE_SUCCESS, JsonUtils.toJson(praiseModel));
//        }
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        mIvPrevious.setVisibility(View.GONE);
        mIvNext.setVisibility(View.GONE);
        mHeadImgVoice.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
        initRecylerView();
        initEmotions();
        etSendmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int lengthBefore, int lengthAfter) {
                String content = etSendmessage.getText().toString().trim();
                AppLog.e("=======start=======" + start + "=====lengthBefore======" + lengthBefore + "=======lengthAfter=====" + lengthAfter);
                if (content.startsWith("@") && start == 0 && lengthBefore == 0 && lengthAfter == 1) {
                    KeyBoardUtils.hideSoftInput(etSendmessage);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            slidingMenu.openMenu();
                        }
                    }, 500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = etSendmessage.getText().toString().trim();
                if (content.length() <= 0) {
                    btnMore.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.GONE);
                } else {
                    btnMore.setVisibility(View.GONE);
                    btnSend.setVisibility(View.VISIBLE);
                }
            }
        });

        mMediaManager = new MediaManager();
        mVideoWidth = PixelUtil.getScreenWidth(this) / DEFAULT_VIDEO_NUM;
        mVideoHeight = mVideoWidth / 16 * 9;
        initWebView();

        mVideoFL.setOnDragViewListener(new DragViewGroup.onDragViewListener() {
            @Override
            public void onDrag(boolean drag) {
                ChatRoomUserActivity.this.drag = drag;
            }
        });

        jzVideo.setOnBackListener(new JZVideoPlayerStandard.OnBackListener() {
            @Override
            public void onBack() {
                JZVideoPlayer.releaseAllVideos();
                jzVideo.setVisibility(View.GONE);
            }
        });

        jzVideo.setOnFullScreenListener(new JZVideoPlayer.OnFullScreenListener() {
            @Override
            public void onFullScreen() {
                AppLog.e("========fullscreen111=========");
                slidingMenu.openMenu();
                slidingMenu.closeMenu();
            }
        });
    }

    @Override
    public void initData() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = PixelUtil.getScreenWidth(this) * 270 / 750;
        params.height = params.width * 16 / 9;
        mStreamLayout.setLayoutParams(params);
        FrameLayout.LayoutParams params_ = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params_.width = PixelUtil.getScreenWidth(this) * 112 / 750;
        params_.height = params_.width * 16 / 9;
        params_.gravity = Gravity.BOTTOM;
        mStreamLayout_.setLayoutParams(params_);
        if (getIntent() != null) {
            userBean = ((UserBean) getIntent().getSerializableExtra("userBean"));
            initBukaSdk();
            loadDatas();
        }
        loadGifts();
    }

    private void initBukaSdk() {
        requestPermisson();
        bukaHelper = BukaHelper.getInstance(this);
        bukaHelper.initBukaViews(userBean, contentListView, contentAdapter, userAdapter, mStreamLayout, mStreamLayout_, mCoseVideo, mFullVideo);
        Tools.showWaitDialog(this, "进入房间中...", true);
        bukaHelper.setOnLoginListener(new BukaHelper.OnLoginListener() {
            @Override
            public void onStatusListener(int type, String str) {
                Tools.dismissWaitDialog();
                switch (type) {
                    case 0:
                    case 1:
                        ToastUtil.shortShowToast(str);
                        bukaHelper.exit(mStreamLayout, mStreamLayout_, mCoseVideo, mFullVideo);
                        finish();
                        break;
                    case 3:
                        ToastUtil.shortShowToast(str);
                        bukaHelper.exit(mStreamLayout, mStreamLayout_, mCoseVideo, mFullVideo);
                        finish();
                        break;
                    case 4:
                        bukaHelper.play(mStreamLayout, mStreamLayout_, mCoseVideo, mFullVideo);
                        break;
                }
            }
        });
        bukaHelper.setOnNumChangeListener(new BukaHelper.OnNumChangeListener() {
            @Override
            public void onNumListener(int type, long num) {
                switch (type) {
                    case 1:
                        break;
                    case 3:
                        mOnLineList.setText("在线列表（" + NumberUtils.roundInt((int) num) + "）");
                        mContentTv.setText("在线：" + NumberUtils.roundInt((int) num));
                        mOnLineTv.setText(NumberUtils.roundInt((int) num));
                        break;

                }
            }
        });
        bukaHelper.setOnRpcListener(new BukaHelper.OnRpcListener() {
            @Override
            public void onRpcListener(tv.buka.sdk.entity.User user, Rpc rpc) {
                AppLog.e("===========setOnRpcListener===============");
                UserBean userBean1 = null;
                if (user != null) {
                    userBean1 = new UserBean(user.getUser_extend());
                }
                switch ((int) rpc.getType()) {
                    case Constant.RPC_GIFT:
                        if (!TextUtils.isEmpty(rpc.getMessage())) {
                            giftFlag = true;
                            loadDatas();
                        }
                        break;
                    case Constant.RPC_TICKOUT:
                        if (userBean1.getUserId() == userBean.getUserId()) {
                            ToastUtil.shortShowToast("您已被踢出房间...");
                            finish();
                        }
                        break;
                    case Constant.RPC_STREAM_CONNECT:
                        publishFlag = true;
                        if (joinSpeakPopupWindow != null && TextUtils.equals(bukaHelper.getCurrentUser().getUserId()+"", rpc.getMessage())) {
                            joinSpeakPopupWindow.dismiss();
                            bukaHelper.publish(mStreamLayout_, true, mCoseVideo, mFullVideo);
                        }
                        break;
                    case Constant.RPC_STREAM_END:
                        if (joinSpeakPopupWindow != null && TextUtils.equals(bukaHelper.getCurrentUser().getUserId()+"", rpc.getMessage())) {
                            joinSpeakPopupWindow.dismiss();
                            ToastUtil.shortShowToast("请求拒绝...");
                        }
                        break;
                    case Constant.RPC_STREAM_LIVE_END:
                        mCoseVideo.setVisibility(View.GONE);
                        mFullVideo.setVisibility(View.GONE);
                        mStreamLayout.setVisibility(View.GONE);
                        mStreamLayout_.setVisibility(View.GONE);
                        bukaHelper.stopPublish(mStreamLayout_, mCoseVideo, mFullVideo);
                        bukaHelper.stopPlay(mStreamLayout, mCoseVideo, mFullVideo);
                        bukaHelper.stopPlay(mStreamLayout_, mCoseVideo, mFullVideo);
                        break;
                    case Constant.RPC_STREAM_SPEAKER_END:
                        AppLog.e("===========stopPlay131313===============");
                        publishFlag = false;
                        break;
                    case ConstantUtil.RPC_HTML_PPT_URL:
                        if (docFlag) {
                            pptUrl(rpc);
                        }
                        break;
                    case ConstantUtil.RPC_HTML_PPT_ACTION:
                        pptAction(rpc);
                        break;
                    case Constant.RPC_VIDEO_PLAY:
                        String videoUrl = rpc.getMessage();
                        AppLog.e("==========videoUrl=========" + videoUrl);
                        if (!TextUtils.isEmpty(videoUrl)) {
                            jzVideo.setVisibility(View.VISIBLE);
                            JZVideoPlayer.releaseAllVideos();
                            jzVideo.setUp(videoUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
                            jzVideo.startVideo();
//                            Glide.with(ChatRoomUserActivity.this).load(R.mipmap.morenzuopingzhaopian).into(jzVideo.thumbImageView);
                        }
                        break;
                }
            }
        });
        bukaHelper.setOnStatusListener(new BukaHelper.OnStatusListener() {
            @Override
            public void onStatusListener(int type, Status status) {
                if (type == 1) {
                    if (!publishFlag && !closePublishFlag) {
                        AppLog.e("=========stopPlay333=========");
                        mFullVideo.setVisibility(View.GONE);
                        mCoseVideo.setVisibility(View.GONE);
                        bukaHelper.stopPlay(mStreamLayout, mCoseVideo, mFullVideo);
                    } else {
                        AppLog.e("=========stopPlay444=========");
                        bukaHelper.stopPlay_(status, mStreamLayout_, mCoseVideo);
                    }
                    //删除视频二路流
//                    delStream(status);
                } else {
                    mStartLL.setVisibility(View.GONE);
                    bukaHelper.play(mStreamLayout, mStreamLayout_, mCoseVideo, mFullVideo);
                    //播放视频二路流
//                    if (status == null || TextUtils.isEmpty(status.getStatus_extend())) {
//                        return;
//                    }
//                    addStream(status);
                }
            }
        });

        bukaHelper.setOnPcPlayListener(new BukaHelper.OnPcPlayListener() {
            @Override
            public void onPcPlay(boolean pc) {
                if (pc) {
                    pcPlay = true;
                }
                JZVideoPlayer.releaseAllVideos();
                jzVideo.setVisibility(View.GONE);
                mStartLL.setVisibility(View.GONE);
            }
        });
    }

    private boolean fullFlag = false;

    @OnClick({R.id.mExitTopicFl, R.id.mWendaLogo, R.id.mPraiseLogo, R.id.mFullVideo, R.id.mOnLineLL, R.id.mTopicLL, R.id.btn_more, R.id.btn_send, R.id.joinSpeakLogo, R.id.et_sendmessage, R.id.mHeadImg, R.id.shareLogo, R.id.endLiveLogo, R.id.stream_layout, R.id.concernLogo, R.id.mCoseVideo, R.id.iv_close})
    public void onViewClicked(View view) {
        User user = MyApplication.getInstance().user;
        List<Status> statusList = BukaSDKManager.getStatusManager().getStatusArr(Constant.STATUS_TAG);
        switch (view.getId()) {
            case R.id.mExitTopicFl:
                mStartLL.setVisibility(View.GONE);
                break;
            case R.id.mWendaLogo:
                SpringUtils.springAnim(mWendaLogo);
//                if (user == null) {
//                    ToastUtil.shortShowToast("请先登录...");
//                    startActivity(new Intent(ChatRoomUserActivity.this, RegistActivity.class));
//                    return;
//                }
                if (familyInfo != null) {
                    Intent intent2 = new Intent(ChatRoomUserActivity.this, QAActivity.class);
                    intent2.putExtra("family", familyInfo);
                    startActivity(intent2);
                }
                break;
            case R.id.mPraiseLogo:
                SpringUtils.springAnim(mPraiseLogo);
                showPraisePopWindow();
                break;
            case R.id.mFullVideo:
                fullFlag = !fullFlag;
                if (fullFlag) {
                    mFullVideo.setBackground(getResources().getDrawable(R.mipmap.liaotianshi_shipingsuoxiao));
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    params.topMargin = 0;
                    params.width = PixelUtil.getScreenWidth(this);
                    params.height = PixelUtil.getScreenHeight(this);
                    mVideoFL.setLayoutParams(params);

                    FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params1.width = PixelUtil.getScreenWidth(this);
                    params1.height = PixelUtil.getScreenHeight(this);
                    mStreamLayout.setLayoutParams(params1);

                    FrameLayout.LayoutParams params_ = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params_.width = PixelUtil.getScreenWidth(this) * 224 / 750;
                    params_.height = params_.width * 16 / 9;
                    params_.gravity = Gravity.BOTTOM;
                    mStreamLayout_.setLayoutParams(params_);
                } else {
                    mFullVideo.setBackground(getResources().getDrawable(R.mipmap.liaotianshi_shipingfangda));
                    if (pcPlay) {
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.width = PixelUtil.getScreenWidth(this) * 592 / 750;
                        params.height = params.width * 9 / 16;
                        mStreamLayout.setLayoutParams(params);
                    } else {
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.topMargin = PixelUtil.dp2px(this, 80);
                        params.gravity = Gravity.RIGHT;
                        params.width = PixelUtil.getScreenWidth(this) * 270 / 750;
                        params.height = params.width * 16 / 9;
                        mVideoFL.setLayoutParams(params);

                        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params1.width = PixelUtil.getScreenWidth(this) * 270 / 750;
                        params1.height = params1.width * 16 / 9;
                        mStreamLayout.setLayoutParams(params1);

                        FrameLayout.LayoutParams params_ = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params_.width = PixelUtil.getScreenWidth(this) * 112 / 750;
                        params_.height = params_.width * 16 / 9;
                        params_.gravity = Gravity.BOTTOM;
                        mStreamLayout_.setLayoutParams(params_);
                    }
                }
                break;
            case R.id.mOnLineLL:
                slidingMenu.toggle();
                break;
            case R.id.mTopicLL:
                Intent intent1 = new Intent(ChatRoomUserActivity.this, FansRankListActivity.class);
                //userId 某个人的id
                intent1.putExtra("userId", userId + "");
                intent1.putExtra("fansId", user == null ? "" : user.getUserId() + "");
                startActivity(intent1);
                break;
            case R.id.btn_more://送礼物
                SpringUtils.springAnim(btnMore);
                KeyBoardUtils.hideSoftInput(etSendmessage);
                emotionContainer.setVisibility(View.GONE);
                noId = 0;
                gNum = 1;
                showGiftPopWindow();
                break;
            case R.id.btn_send:
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(ChatRoomUserActivity.this, RegistActivity.class));
                    return;
                }
                SpringUtils.springAnim(btnSend);
                KeyBoardUtils.hideSoftInput(etSendmessage);
                emotionContainer.setVisibility(View.GONE);
                String content = etSendmessage.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    bukaHelper.chat(null, content);
                }
                etSendmessage.setText("");
                btnMore.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.GONE);
                break;
            case R.id.joinSpeakLogo://用户连麦
                SpringUtils.springAnim(joinSpeakLogo);
                KeyBoardUtils.hideSoftInput(etSendmessage);
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(ChatRoomUserActivity.this, RegistActivity.class));
                    return;
                }
                if (statusList != null && statusList.size() > 0) {
                    AppLog.e("==========publish111=============" + statusList.size());
                    if (statusList.size() < 2) {
                        bukaHelper.rpc(null, Constant.RPC_STREAM_WAIT, bukaHelper.getCurrentUser().getUserId()+"");
                        showJoinSpeakPopWindow();
                    } else {
                        ToastUtil.shortShowToast("当前已有人在连麦...");
                    }
                } else {
                    AppLog.e("==========publish222=============");
                    ToastUtil.shortShowToast("请先等待医生开通连线...");
                }
                break;
            case R.id.et_sendmessage:
                slidingMenu.closeMenu();
                break;
            case R.id.mHeadImg:
//                if (infoFlag) {
//                    infoFlag = false;
//                    showPersonalInfoPopWindow();
//                }
                Intent intent = new Intent(ChatRoomUserActivity.this, PersonalPageActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                break;
            case R.id.shareLogo:
                SpringUtils.springAnim(shareLogo);
//                showSharePopWindow();
                if (familyInfo != null && userBean != null) {
                    if (statusList != null && statusList.size() > 0) {
                        typeShare = "1";
                    } else {
                        if (TextUtils.isEmpty(familyInfo.getMedia_url())) {
                            typeShare = "0";
                        } else {
                            typeShare = "2";
                        }
                    }
                }
                break;
            case R.id.endLiveLogo:
                SpringUtils.springAnim(endLiveLogo);
                new AlertDialog.Builder(ChatRoomUserActivity.this).setTitle("退出聊天室?")
                        .setMessage("您是否确定退出聊天室？")
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                bukaHelper.exit(mStreamLayout, mStreamLayout_, mCoseVideo, mFullVideo);
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
            case R.id.stream_layout:
                if (!drag) {
                    bukaHelper.switchCamera();
                }
                break;
            case R.id.concernLogo://详情页面
                SpringUtils.springAnim(concernLogo);
                Intent intent2 = new Intent(ChatRoomUserActivity.this, ChatDetailActivity.class);
                intent2.putExtra("userBean", userBean);
                intent2.putExtra("live", true);
                startActivity(intent2);
//                addRead();
                break;
            case R.id.mCoseVideo:
                AppLog.e("==========publishFlag=========" + publishFlag);
                if (publishFlag) {
                    closePublishFlag = true;
                    bukaHelper.rpc(null, Constant.RPC_STREAM_SPEAKER_END, bukaHelper.getCurrentUser().getUserId()+"");
                    mStreamLayout_.setVisibility(View.GONE);
                    bukaHelper.stopPublish(mStreamLayout_, null, null);
                } else {
                    closePublishFlag = false;
                    mCoseVideo.setVisibility(View.GONE);
                    mFullVideo.setVisibility(View.GONE);
                    mStreamLayout.setVisibility(View.GONE);
                    mStreamLayout_.setVisibility(View.GONE);
                    bukaHelper.stopPlay(mStreamLayout, mCoseVideo, mFullVideo);
                    bukaHelper.stopPublish(mStreamLayout_, mCoseVideo, mFullVideo);
                }
                break;
            case R.id.iv_close:
                mFlContext.setVisibility(View.GONE);
                docFlag = false;
                break;
        }
    }

    private void showPraisePopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.praise_pop_window)
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
                        final SimpleDraweeView mHeadImg = (SimpleDraweeView) view.findViewById(R.id.mHeadImg);
                        TextView mPraiseName = (TextView) view.findViewById(R.id.mPraiseName);
                        RecyclerView mPraiseRecylerView = (RecyclerView) view.findViewById(R.id.mPraiseRecylerView);
                        if (familyInfo != null) {
                            mHeadImg.setImageURI(TextUtils.isEmpty(familyInfo.getHeadUrl()) ? "" : familyInfo.getHeadUrl());
                            mPraiseName.setText("赞赏给  " + (TextUtils.isEmpty(familyInfo.getNickName()) ? "" : familyInfo.getNickName()));
                        }
                        initRecylerView_(mPraiseRecylerView);
                        final PraiseListAdapter praiseListAdapter = new PraiseListAdapter(ChatRoomUserActivity.this, null);
                        mPraiseRecylerView.setAdapter(praiseListAdapter);
                        mHeadImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SpringUtils.springAnim(mHeadImg);
                                if (familyInfo != null) {
                                    Intent intent = new Intent(ChatRoomUserActivity.this, PersonalPageActivity.class);
                                    intent.putExtra("userId", familyInfo.getUserId());
                                    startActivity(intent);
                                }
                            }
                        });
                        praiseListAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, List data, int position) {
                                switch (position) {
                                    case 0:
                                        prise = 2;
                                        break;
                                    case 1:
                                        prise = 6;
                                        break;
                                    case 2:
                                        prise = 9;
                                        break;
                                    case 3:
                                        prise = 66;
                                        break;
                                    case 4:
                                        prise = 88;
                                        break;
                                    case 5:
                                        prise = 520;
                                        break;
                                }
                                wxPay();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mPraiseLogo, Gravity.CENTER, 0, 0);
    }

    private void wxPay() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(this, RegistActivity.class));
            return;
        }
//        NetUtils.getInstance().wxPay(0, Tools.getIPAddress(this), userId, prise, 4, new NetCallBack() {
//            @Override
//            public void onSuccess(String result, String msg, ResultModel model) {
//                WxPay wxPay = (WxPay) model.getModel();
//                if (wxPay != null) {
//                    WXPayHelper.getInstance(ChatRoomUserActivity.this, new WXPayHelper.WXPayCallBack() {
//                        @Override
//                        public void success() {
//                            //没走回调
//                        }
//
//                        @Override
//                        public void falure(String message) {
//
//                        }
//                    }).directPay(wxPay);
//                }
//            }
//
//            @Override
//            public void onFail(String code, String msg, String result) {
//                ToastUtil.shortShowToast(msg);
//            }
//        }, WxPay.class);
    }

    private void initRecylerView_(RecyclerView mRecylerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(layoutManager);
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
    }

    private void loadGifts() {
//        Tools.showDialog(this);
        User user = MyApplication.getInstance().user;
        if (user == null) {
            return;
        }
    }

    private void addRead() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
//            Tools.showDialog(this);
        } else {
            ToastUtil.shortShowToast("请先登录....");
            startActivity(new Intent(ChatRoomUserActivity.this, RegistActivity.class));
        }
    }

    private ViewPager viewPager;
    private LinearLayout idotLayout;//知识圆点
    private List<View> mPagerList;//页面集合
    private List<GiftList.ContentBean> mDataList = new ArrayList<>();//数据集合；
    private LayoutInflater mInflater;
    private int currPage;
    /*总的页数*/
    private int pageCount;
    /*每一页显示的个数*/
    private int pageSize = 8;
    /*当前显示的是第几页*/
    private int curIndex = 0;
    private GridViewAdapter[] arr;

    private void showGiftPopWindow() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(ChatRoomUserActivity.this, RegistActivity.class));
            return;
        }
        if (mDataList == null || mDataList.size() <= 0) {
            ToastUtil.shortShowToast("暂无礼物...");
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
                        final TextView remainMoney = view.findViewById(R.id.remainMoney);
                        final TextView sendGift = view.findViewById(R.id.sendGift);
                        final TextView sendNum = view.findViewById(R.id.sendNum);
                        remainMoney.setText("" + usersPice);
                        SimpleDraweeView mHeadImg = view.findViewById(R.id.mHeadImg);
                        TextView mNameTv = view.findViewById(R.id.mNameTv);
                        LinearLayout ageSexLL = view.findViewById(R.id.ageSexLL);
                        ImageView sexLogo = view.findViewById(R.id.sexLogo);
                        TextView mAgeTv = view.findViewById(R.id.mAgeTv);
                        TextView mIdTv = view.findViewById(R.id.mIdTv);
                        if (familyInfo != null) {
                            mHeadImg.setImageURI(TextUtils.isEmpty(familyInfo.getHeadUrl()) ? "" : familyInfo.getHeadUrl());
                            mNameTv.setText(TextUtils.isEmpty(familyInfo.getNickName()) ? "用户" : familyInfo.getNickName());
                            mAgeTv.setText("" + familyInfo.getAge());
                            mIdTv.setText("ID:" + (TextUtils.isEmpty(familyInfo.getUserNum()) ? "暂无" : familyInfo.getUserNum()));
                            String sex = familyInfo.getSex()==0?"男":"女";
                            if (!TextUtils.isEmpty(sex)) {
                                switch (sex) {
                                    case "男":
                                        ageSexLL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_blue_));
                                        sexLogo.setImageResource(R.mipmap.xiangqing_nan1);
                                        break;
                                    case "女":
                                        ageSexLL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_pink));
                                        sexLogo.setImageResource(R.mipmap.xiangqing_nv1);
                                        break;
                                }
                            }
                        }
                        viewPager = view.findViewById(R.id.viewpager);
                        idotLayout = view.findViewById(R.id.ll_dot);
                        initValues();
                        LinearLayout chargeLL = view.findViewById(R.id.chargeLL);
                        final LinearLayout mGiftLL = view.findViewById(R.id.mGiftLL);
                        final ImageView addNum = view.findViewById(R.id.addNum);
                        chargeLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(addNum);
                                popupWindow.dismiss();
                                startActivity(new Intent(ChatRoomUserActivity.this, WalletActivity.class));
                            }
                        });
                        mGiftLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showGiftNumPopWindow(sendNum);
                            }
                        });
                        sendGift.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendGifts(remainMoney, popupWindow);
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

    private String giftName;
    private String giftUrl;

    private void sendGifts(final TextView remainMoney, final PopupWindow popupWindow) {
        final User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(ChatRoomUserActivity.this, RegistActivity.class));
            return;
        }
        long gid = 0;
        if (mDataList != null && mDataList.size() > 0) {
            for (int i = 0; i < mDataList.size(); i++) {
                GiftList.ContentBean goodSBean = mDataList.get(i);
                if (goodSBean.isSelected()) {
                    gid = goodSBean.getGiftId();
                    gPrice = goodSBean.getGiftPrice();
                    giftName = goodSBean.getGiftName();
                    giftUrl = goodSBean.getGiftUrl();
                }
            }
            if (gid <= 0) {
                ToastUtil.shortShowToast("请先选择礼物...");
                return;
            }
            //passivityUserId  被送礼物人的id
//            Tools.showDialog(ChatRoomUserActivity.this);
        } else {
            ToastUtil.shortShowToast("暂无礼物...");
        }
    }

    private long noId = 0;
    private double gNum = 1;
    private double gPrice = 0;

    private void showGiftNumPopWindow(final TextView sendNum) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.gift_num_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RecyclerView mRecylerView = view.findViewById(R.id.mRecylerView);
                        initRecylerView(mRecylerView);
                        final GiftNumAdapter oderPopAdapter = new GiftNumAdapter(ChatRoomUserActivity.this, null);
                        mRecylerView.setAdapter(oderPopAdapter);
                        oderPopAdapter.appendAll(souvenirNos);
                        oderPopAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, List data, int position) {
                                GiftList.SouvenirNosBean souvenirNosBean = (GiftList.SouvenirNosBean) data.get(position);
                                noId = souvenirNosBean.getId();
                                gNum = souvenirNosBean.getQuantity();
                                sendNum.setText("" + souvenirNosBean.getQuantity());
                                popupWindow.dismiss();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
//        popupWindow.showAsDropDown(mGiftLL,0, 10);
        int[] location = new int[2];
        rlFace.getLocationOnScreen(location);
        popupWindow.showAtLocation(rlFace, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight() - 40);
    }

    private void initRecylerView(RecyclerView mRecylerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(layoutManager);
    }

    private void showJoinSpeakPopWindow() {
        //设置PopupWindow布局
        //设置宽高
        //设置动画
        //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
        //设置PopupWindow里的子View及点击事件
        //设置外部是否可点击 默认是true
        //开始构建
        joinSpeakPopupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.user_join_speak)
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
                        TextView mNameTv = view.findViewById(R.id.mNameTv);
                        mNameTv.setText(userBean.getNickName());
                        SimpleDraweeView mHeadImg = view.findViewById(R.id.mHeadImg);
                        mHeadImg.setImageURI(TextUtils.isEmpty(userBean.getHeadUrl()) ? "" : userBean.getHeadUrl());
                        final ImageView shouqiLogo = view.findViewById(R.id.shouqiLogo);
                        final ImageView guaduanLogo = view.findViewById(R.id.guaduanLogo);
                        final ImageView gongfangLogo = view.findViewById(R.id.gongfangLogo);
                        shouqiLogo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(shouqiLogo);
                                joinSpeakPopupWindow.dismiss();
                            }
                        });
                        guaduanLogo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(guaduanLogo);
//                                bukaHelper.stopPublish(mStreamLayout_, mCoseVideo);
                                joinSpeakPopupWindow.dismiss();
                            }
                        });
                        gongfangLogo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(gongfangLogo);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        joinSpeakPopupWindow.showAtLocation(contentListView, Gravity.BOTTOM, 0, 0);
    }

    private void showOnLinePopWindow(final UserBean currentUser, final UserBean userBean) {
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
                        List<LinearLayout> imgViews = new ArrayList<>();
                        imgViews.add(mImg1);
                        imgViews.add(mImg2);
                        imgViews.add(mImg3);
                        imgViews.add(mImg4);
                        imgViews.add(mImg5);
                        imgViews.add(mImg6);
                        showOpenAnim(PixelUtil.dp2px(ChatRoomUserActivity.this, 100), imgViews);
//                        if (!TextUtils.isEmpty(userBean.getUserType())) {
//                            switch (userBean.getUserType()) {
//                                case "2":
//                                    mImg2.setVisibility(View.GONE);  //设管理员
//                                    mImg4.setVisibility(View.GONE);  //禁言
//                                    mImg5.setVisibility(View.GONE);  //踢人
//                                    break;
//                                case "1":
//                                    mImg2.setVisibility(TextUtils.equals("2", currentUser.getUserType()) ? View.VISIBLE : View.GONE);
//                                    mImg4.setVisibility(TextUtils.equals("2", currentUser.getUserType()) ? View.VISIBLE : View.GONE);
//                                    mImg5.setVisibility(TextUtils.equals("2", currentUser.getUserType()) ? View.VISIBLE : View.GONE);
//                                    toBeManagerTv.setText("取消管理员");
//                                    forbidSpeakTv.setText(TextUtils.equals("0", userBean.getUserstate()) ? "禁言" : "解禁");
//                                    break;
//                                case "0":
//                                    mImg2.setVisibility(TextUtils.equals("2", currentUser.getUserType()) ? View.VISIBLE : View.GONE);
//                                    mImg4.setVisibility(TextUtils.equals("0", currentUser.getUserType()) ? View.GONE : View.VISIBLE);
//                                    mImg5.setVisibility(TextUtils.equals("0", currentUser.getUserType()) ? View.GONE : View.VISIBLE);
//                                    toBeManagerTv.setText("设为管理员");
//                                    forbidSpeakTv.setText(TextUtils.equals("0", userBean.getUserstate()) ? "禁言" : "解禁");
//                                    break;
//                            }
//                        }
                        mImg1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg1);
                                popupWindow.dismiss();
                                slidingMenu.closeMenu();
                                etSendmessage.setText("@" + userBean.getNickName() + " ");
                                etSendmessage.setSelection(userBean.getNickName().length() + 2);
                            }
                        });
                        mImg2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg2);
                                popupWindow.dismiss();
                                bukaHelper.rpc(null, Constant.RPC_MANAGER, userBean.getUserId()+"");
                            }
                        });
                        mImg3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg3);
                                popupWindow.dismiss();
                                noId = 0;
                                gNum = 1;
                                showGiftPopWindow();
                            }
                        });
                        mImg4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg4);
                                popupWindow.dismiss();
                                bukaHelper.rpc(null, Constant.RPC_SPEAK, userBean.getUserId()+"");
                            }
                        });
                        mImg5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg5);
                                popupWindow.dismiss();
                                bukaHelper.rpc(null, Constant.RPC_TICKOUT,userBean.getUserId()+"");
                            }
                        });
                        mImg6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg6);
                                User user = MyApplication.getInstance().user;
                                if (user == null) {
                                    ToastUtil.shortShowToast("请先登录...");
                                    startActivity(new Intent(ChatRoomUserActivity.this, RegistActivity.class));
                                    return;
                                }
                                if (userBean.getUserId() == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能和自己聊天哦...");
                                    return;
                                }
                                Intent intent3 = new Intent(ChatRoomUserActivity.this, ChatActivity.class);
                                intent3.putExtra(com.qianyu.chatuidemo.Constant.EXTRA_USER_ID, userBean.getPhone());
                                startActivity(intent3);
                            }
                        });
                        mHeadImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SpringUtils.springAnim(mHeadImg);
                                Intent intent = new Intent(ChatRoomUserActivity.this, PersonalPageActivity.class);
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

    private void showSharePopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.layout_share_pop)
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
                        final FrameLayout qqShare = view.findViewById(R.id.qqShare);
                        final FrameLayout wxShare = view.findViewById(R.id.wxShare);
                        final FrameLayout sinaShare = view.findViewById(R.id.sinaShare);
                        final FrameLayout circleShare = view.findViewById(R.id.circleShare);
                        TextView cancelShare = view.findViewById(R.id.cancelShare);
                        cancelShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        qqShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(qqShare);
                            }
                        });
                        wxShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(wxShare);
                            }
                        });
                        sinaShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(sinaShare);
                            }
                        });
                        circleShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(circleShare);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(contentListView, Gravity.BOTTOM, 0, 0);
    }

    private boolean infoFlag = true;

    private void showPersonalInfoPopWindow() {
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
                        final ImageView concernLogo = view.findViewById(R.id.concernLogo);
                        final TextView concernTv = view.findViewById(R.id.concernTv);
                        final TextView mNameTv = view.findViewById(R.id.mNameTv);
                        final LinearLayout ageSexLL = view.findViewById(R.id.ageSexLL);
                        final ImageView sexLogo = view.findViewById(R.id.sexLogo);
                        final TextView mIdNum = view.findViewById(R.id.mIdNum);
                        final LinearLayout mIdNumLL = view.findViewById(R.id.mIdNumLL);
                        final TextView mJobTitle = view.findViewById(R.id.mJobTitle);
                        final TextView mAgeTv = view.findViewById(R.id.mAgeTv);
                        final TextView mFansTv = view.findViewById(R.id.mFansTv);
                        final TextView mLocationTv = view.findViewById(R.id.mLocationTv);
                        final MyRecylerView mCircleRecylerView = view.findViewById(R.id.circleRecylerView);
                        final LinearLayout atTaLL = view.findViewById(R.id.atTaLL);
                        final LinearLayout giftLL = view.findViewById(R.id.giftLL);
                        final LinearLayout mFansLL = view.findViewById(R.id.mFansLL);
                        final LinearLayout mFirendDetailLL = view.findViewById(R.id.mFirendDetailLL);
                        giftLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                                SpringUtils.springAnim(giftLL);
                                noId = 0;
                                gNum = 1;
                                showGiftPopWindow();
                            }
                        });
                        mFansLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChatRoomUserActivity.this, MyConcernActivity.class);
                                intent.putExtra("sign", "2");
                                //userId 某个人的id
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                            }
                        });
                        mFirendDetailLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChatRoomUserActivity.this, MyCircleActivity.class);
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                            }
                        });
                        mHeadImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ChatRoomUserActivity.this, PersonalPageActivity.class);
                                intent.putExtra("userId", userId);
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
        popupWindow.showAtLocation(contentListView, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                infoFlag = true;
            }
        });
    }

    private void concernOrNot_(final TextView concernTv, final ImageView concernLogo) {
        User user = MyApplication.getInstance().user;
        if (user != null) {
//            Tools.showDialog(this);
        } else {
            ToastUtil.shortShowToast("请先登录....");
            startActivity(new Intent(ChatRoomUserActivity.this, RegistActivity.class));
        }
    }

    private void initCircleRecylerView(MyRecylerView mCircleRecylerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.HORIZONTAL);
        mCircleRecylerView.setHasFixedSize(true);
        mCircleRecylerView.setLayoutManager(layoutManager);
        InfoCircleAdapter adapter = new InfoCircleAdapter(this, null);
        mCircleRecylerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                Intent intent = new Intent(ChatRoomUserActivity.this, MyCircleActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    private void initValues() {
        mInflater = LayoutInflater.from(this);
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDataList.size() * 1.0 / pageSize);
        arr = new GridViewAdapter[pageCount];
        //viewpager
        mPagerList = new ArrayList<View>();
        for (int j = 0; j < pageCount; j++) {
            final GridView gridview = (GridView) mInflater.inflate(R.layout.bottom_vp_gridview, viewPager, false);
            final GridViewAdapter gridAdapter = new GridViewAdapter(this, mDataList, j);
            gridview.setAdapter(gridAdapter);
            arr[j] = gridAdapter;
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        GiftList.ContentBean giftModel = mDataList.get(i);
                        if (i == id) {
                            if (giftModel.isSelected()) {
                                giftModel.setSelected(false);
                            } else {
                                giftModel.setSelected(true);
                            }
                            Log.i("tag", "==点击位置：" + i + "..id:" + id);
                        } else {
                            giftModel.setSelected(false);
//                            Log.i("tag","==位置2："+i+"..id:"+id);
                        }
                    }
                    Log.i("tag", "状态：" + mDataList.toString());
                    gridAdapter.notifyDataSetChanged();
                }
            });
            mPagerList.add(gridview);
        }

        viewPager.setAdapter(new ViewPagerAdapter(mPagerList, ChatRoomUserActivity.this));
        setOvalLayout();
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            idotLayout.addView(mInflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        idotLayout.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                arr[position].notifyDataSetChanged();
                // 取消圆点选中
                idotLayout.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                idotLayout.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

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

    private void initRecylerView() {
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
                return false;
            }
        });

        userAdapter = new UserAdapter(this);
        userListView.setAdapter(userAdapter);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv.buka.sdk.entity.User user = userAdapter.getmList().get(i);
                final UserBean currentUser = bukaHelper.getCurrentUser();
                if (currentUser.getUserId()==bukaHelper.getCurrentUser().getUserId()) {
                    ToastUtil.shortShowToast("不能对自己操作哦...");
                    return;
                }
                UserBean userBean = new UserBean(user.getUser_extend());
                showOnLinePopWindow(currentUser, userBean);
            }
        });
    }

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
        bukaHelper.exit(mStreamLayout, mStreamLayout_, mCoseVideo, mFullVideo);
        globalOnItemClickManager.unAttachToEditText();
        if (mWebView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再 // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }
            mWebView.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            mWebView.destroy();
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
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        /**
         * 按下返回键，如果表情显示，则隐藏，没有显示则回退页面
         */
        AppLog.e("=========onBackPressed==========" + mEmotionKeyboard.interceptBackPress());
        if (!mEmotionKeyboard.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    private void pptAction(Rpc rpc) {
//        if (!rpc.getSend_session_id().equals(bukaHelper.getCurrentUser().getSession_id())) {
            String message = rpc.getMessage();
            message = message.replace("terminal", "'Android'");
            AppLog.e("==========docRpcBean222============" + message);
            if (mWebView != null) {
                AppLog.e(mDocUrl + "==========isPPt888=========" + message);
                mWebView.loadUrl("javascript:bukaAction(" + "\"" + message + "\"" + ")");
            }
//        }
    }

    private void pptUrl(Rpc rpc) {
        mFlContext.setVisibility(View.VISIBLE);
        AppLog.e("RPC_HTML_PPT_URL = 4003" + rpc.getSend_session_id());
        docRpcBean = JsonUtils.getBean(rpc.getMessage(), DocRpcBean.class);
        docRpcBean.setStep(docRpcBean.getCount() == 0 ? docRpcBean.getStep() : docRpcBean.getCount());
        int view = docRpcBean.getView();//0 大视频视图 1文档视图 2删除文档
        String url = docRpcBean.getUrl();
        int page = docRpcBean.getPage();
        boolean ppt = docRpcBean.isPpt();
        isLoad = false;
        AppLog.e(mDocUrl + "==========docRpcBean111============" + docRpcBean.toString());
        if (ppt) {
            mTvPage.setVisibility(View.VISIBLE);
            //=========dyj========
            mIvPrevious.setVisibility(View.VISIBLE);
            mIvNext.setVisibility(View.VISIBLE);
            Spannable sp = new SpannableString(page + "/" + docRpcBean.getStep());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#D63F41"));
            sp.setSpan(colorSpan, 0, ("" + page).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sp.setSpan(new AbsoluteSizeSpan(14, true), 0, ("" + page).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mTvPage.setText(sp);
        } else {
            mTvPage.setVisibility(View.GONE);
            mIvPrevious.setVisibility(View.GONE);
            mIvNext.setVisibility(View.GONE);
        }
        if (view == 1 && !TextUtils.isEmpty(url)) {
            mFlContentVideo.setVisibility(View.INVISIBLE);
            mRlContentWeb.setVisibility(View.VISIBLE);
            //=========dyj========
            isPPt = ppt;
            AppLog.e(mDocUrl + "==========isPPt000=========" + url);
            //如果全局url和rpc中的url不一致
            if (!mDocUrl.equals(url)) {
                if (page == 1) {//这是一个新文档，需要loadurl
                    mDocUrl = url;
                    if (mDocUrl.contains("Whiteboard")) {
                        setWebviewSize(19);
                    } else {
                        setWebviewSize(16);
                    }
                    AppLog.e(mDocUrl + "==========isPPt111=========" + url);
//                    mWebView.loadUrl(WHITE_BOARD);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
                    mWebView.loadUrl(mDocUrl);
//                        }
//                    }, 1000);
                } else {//这是文档的翻页，需要调用js函数
                    if (mDocUrl.contains("Whiteboard")) {
//                        mTvPage.setVisibility(View.GONE);
                        mDocUrl = url;
                        isLoad = true;
                        if (mDocUrl.contains("Whiteboard")) {
                            setWebviewSize(19);
                        } else {
                            setWebviewSize(16);
                        }
                        AppLog.e(mDocUrl + "==========isPPt222=========" + url);
                        mWebView.loadUrl(mDocUrl);
                    } else {
//                        mTvPage.setVisibility(View.VISIBLE);
                        mDocUrl = url;
                        isLoad = true;
                        if (mDocUrl.contains("Whiteboard")) {
                            setWebviewSize(19);
                        } else {
                            setWebviewSize(16);
                        }
                        AppLog.e(mDocUrl + "==========isPPt333=========" + url);
                        mWebView.loadUrl(mDocUrl);
                    }
                    //===dyj==
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.loadUrl("javascript:bukaPageCount(" + docRpcBean.getStep() + ")");
                            mWebView.loadUrl("javascript:bukaJump(" + docRpcBean.getPage() + "," + docRpcBean.getStep() + ")");
                        }
                    }, 1000);
                    //===dyj==
                }
            } else {//url一致，再次打开同一个文档,  翻页到第一页
                if (mDocUrl.contains("Whiteboard")) {
                    setWebviewSize(19);
                } else {
                    setWebviewSize(16);
                }
                if (page == 1) {
//                    mWebView.loadUrl("javascript:bukaPageCount(" + docRpcBean.getCount() + ")");
                    mWebView.loadUrl("javascript:bukaPageCount(" + docRpcBean.getStep() + ")");
                    mWebView.loadUrl("javascript:bukaJump(" + docRpcBean.getPage() + "," + docRpcBean.getStep() + ")");
                    AppLog.e(docRpcBean.getCount() + "==========isPPt555=========" + docRpcBean.getPage() + "======================" + docRpcBean.getStep());
                } else {
//                    mWebView.loadUrl("javascript:bukaPageCount(" + docRpcBean.getCount() + ")");
                    mWebView.loadUrl("javascript:bukaPageCount(" + docRpcBean.getStep() + ")");
                    mWebView.loadUrl("javascript:bukaJump(" + docRpcBean.getPage() + "," + docRpcBean.getStep() + ")");
                    AppLog.e(docRpcBean.getCount() + "==========isPPt777=========" + docRpcBean.getPage() + "======================" + docRpcBean.getStep());
                }
            }
        } else if (view == 2) {
            isPPt = false;
            mTvPage.setVisibility(View.GONE);
            setWebviewSize(19);
            mWebView.loadUrl(WHITE_BOARD);
            mDocUrl = WHITE_BOARD;
            mFlContext.setVisibility(View.GONE);
        } else if (view == 0) {
            mFlContentVideo.setVisibility(View.VISIBLE);
            mRlContentWeb.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置webview的宽高比例
     * 白板是19/9，文档时16/9
     *
     * @param size
     */
    private void setWebviewSize(int size) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mWebView.getLayoutParams();
        int webHeight = PixelUtil.getScreenHeight(this) - mVideoHeight;
        params.height = webHeight;
        params.width = webHeight / 9 * size;
        AppLog.e("webview height = " + params.height + ", width = " + params.width);
        mWebView.setLayoutParams(params);
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface", "AddJavascriptInterface"})
    private void initWebView() {
        setWebviewSize(19);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.addJavascriptInterface(new JavaScriptObect(), "buka");
        //设置自适应屏幕，两者合用
        mWebView.getSettings().setUseWideViewPort(true);  //将图片调整到适合webview的大小
        mWebView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebView.getSettings().setSupportZoom(true);
        //滚动条
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //Bugly Javascript 异常捕获功能
//        boolean flag = CrashReport.setJavascriptMonitor(mWebView, true);
//        AppLog.e("WebView 注入 Bugly Javascript 异常捕获功能 = " + flag);
        mWebView.setClick(false);
        mWebView.loadUrl(WHITE_BOARD);
        mDocUrl = WHITE_BOARD;
        mWebView.setOnTouchScreenListener(new MyWebView.OnTouchScreenListener() {
            @Override
            public boolean onTouchScreen() {
                return false;
            }
        });
    }

    private class JavaScriptObect {

        public JavaScriptObect() {
        }

        @JavascriptInterface
        public void pptPaint(String str) {

        }

        @JavascriptInterface
        public void pptChanged(final String str) {
            ChatRoomUserActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppLog.e("==========pptChanged=========" + str);
                    if ("loadingEnd".equals(str)) {
                        //貌似不需要处理。。。
                    } else if (str.startsWith("{") && str.endsWith("}")) {
                        mDocPagerEntity = JsonUtils.getBean(str, DocPagerEntity.class);
//                        Spannable sp = new SpannableString(mDocPagerEntity.getSlide() + "/" + mDocPagerEntity.getLastSlideIndex());
//                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#D63F41"));
//                        sp.setSpan(colorSpan, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                        sp.setSpan(new AbsoluteSizeSpan(14, true), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                        mTvPage.setText(sp);
                        if (isPPt) {
                            mTvPage.setVisibility(View.VISIBLE);
                        }
                        if (isLoad && docRpcBean != null) {
                            AppLog.e("==========pptChanged111=========");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    mWebView.loadUrl("javascript:bukaPageCount(" + docRpcBean.getCount() + ")");
                                    mWebView.loadUrl("javascript:bukaPageCount(" + docRpcBean.getStep() + ")");
                                    mWebView.loadUrl("javascript:bukaJump(" + docRpcBean.getPage() + "," + docRpcBean.getStep() + ")");
                                }
                            }, 1000);
                        } else {
                            AppLog.e("==========pptChanged222=========");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mWebView.loadUrl("javascript:bukaPage('" + mDocPagerEntity.getSlide() + "')");
                                }
                            }, 1000);
                        }
                    }
                    if ("bukaPrepared".equals(str)) {
                        mWebView.loadUrl("javascript:bukaFilp(0)");
                        if (docRpcBean != null) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    mWebView.loadUrl("javascript:bukaPageCount(" + docRpcBean.getCount() + ")");
                                    mWebView.loadUrl("javascript:bukaPageCount(" + docRpcBean.getStep() + ")");
                                    mWebView.loadUrl("javascript:bukaJump(" + docRpcBean.getPage() + "," + docRpcBean.getStep() + ")");
                                }
                            }, 1000);
                        }
                    }
                }
            });
        }
    }

    private SurfaceViewRenderer mTeacherSecondRenderer;

    private void addTeacherSecondVideo(final StreamInfoEntity stream) {
        mMediaManager.startPlay(stream, "msg", createSurfaceViewRenderer(), new ReceiptListener<SurfaceViewRenderer>() {
            @Override
            public void onSuccess(SurfaceViewRenderer surfaceViewRenderer) {
                mTeacherSecondRenderer = surfaceViewRenderer;
                mFlContentVideo.removeAllViews();

                mFlContentVideo.setVisibility(View.VISIBLE);
                int height = mFlContentVideo.getHeight();
                int width = height / 9 * 16;
                AppLog.e("addTeacherSecondVideo height = " + height + "; widht = " + width);
                mFlContentVideo.addView(surfaceViewRenderer, width, height);
                mRlContentWeb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Object o) {
                AppLog.e("播放老师第二路流失败。");
            }
        });
    }

    private void delTeacherSecondVideo(final StreamInfoEntity stream) {
        mMediaManager.stopPlay(stream, mTeacherSecondRenderer);
        mFlContentVideo.removeAllViews();
        mFlContentVideo.setVisibility(View.INVISIBLE);
        mRlContentWeb.setVisibility(View.VISIBLE);
    }

    private SurfaceViewRenderer createSurfaceViewRenderer() {
        SurfaceViewRenderer renderer = null;
        try {
            renderer = new SurfaceViewRenderer(ChatRoomUserActivity.this);
            renderer.init(GetInstance().Egl().getEglBaseContext(), null);
            renderer.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            renderer.setMirror(false);
            renderer.requestLayout();
            mCreateErrorNum = 0;
        } catch (Exception e) {
            AppLog.e("createSurfaceViewRenderer error: " + e.getLocalizedMessage() + "create num=" + mCreateErrorNum);
            if (mCreateErrorNum <= MAX_CREATE_COUNT) {
                mCreateErrorNum++;
                createSurfaceViewRenderer();
            } else {
                ToastUtil.shortShowToast("创建SurfaceViewRenderer失败");
            }
        }
        return renderer;
    }

    private void share(LiveRoomShare liveRoomShare) {
//        UMImage image = new UMImage(ChatRoomUserActivity.this, imgUrl);
        UMImage image = new UMImage(ChatRoomUserActivity.this, TextUtils.isEmpty(liveRoomShare.getFamilyPath()) ? "" : liveRoomShare.getFamilyPath());
        UMWeb web = new UMWeb(TextUtils.isEmpty(liveRoomShare.getUrl()) ? "" : liveRoomShare.getUrl());
        web.setTitle(TextUtils.isEmpty(liveRoomShare.getFamilyName()) ? "" : liveRoomShare.getFamilyName());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(TextUtils.isEmpty(liveRoomShare.getFamilyDescribe()) ? "" : liveRoomShare.getFamilyDescribe());//描述
        new ShareAction(ChatRoomUserActivity.this)
//                .withText("千语")
                .withMedia(web)
//                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                .setCallback(shareListener)
                .open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            if (platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE) {
                if (!isAppInstalled(ChatRoomUserActivity.this, "com.tencent.mobileqq") && !isAppInstalled(ChatRoomUserActivity.this, "com.tencent.tim")) {
                    ToastUtil.shortShowToast("请先安装QQ或者TIM客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                if (!isAppInstalled(ChatRoomUserActivity.this, "com.tencent.mm")) {
                    ToastUtil.shortShowToast("请先安装微信客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.SINA) {
                if (!isAppInstalled(ChatRoomUserActivity.this, "com.sina.weibo")) {
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
            Toast.makeText(ChatRoomUserActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ChatRoomUserActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ChatRoomUserActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    private boolean isAppInstalled(Activity ac, String uri) {
        PackageManager pm = ac.getPackageManager();
        boolean installed = false;
        try {
//            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            pm.getPackageInfo(uri, PackageManager.MATCH_DEFAULT_ONLY);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

}
