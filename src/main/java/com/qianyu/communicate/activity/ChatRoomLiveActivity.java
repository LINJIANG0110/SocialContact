package com.qianyu.communicate.activity;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.qianyu.communicate.adapter.InfoCircleAdapter;
import com.qianyu.communicate.adapter.JoinSpeakAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.BukaHelper;
import com.qianyu.communicate.bukaSdk.MediaManager;
import com.qianyu.communicate.bukaSdk.adapter.ContentAdapter;
import com.qianyu.communicate.bukaSdk.adapter.UserAdapter;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil;
import com.qianyu.communicate.bukaSdk.entity.DocPagerEntity;
import com.qianyu.communicate.bukaSdk.entity.DocRpcEntity;
import com.qianyu.communicate.bukaSdk.entity.DocumentInfo;
import com.qianyu.communicate.bukaSdk.entity.StreamInfoEntity;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.bukaSdk.popwindows.PhotoChoicePop;
import com.qianyu.communicate.emotions.fragments.EmotionComplateFragment;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.GlobalOnItemClickManagerUtils;
import com.qianyu.communicate.emotions.widget.EmotionKeyboard;
import com.qianyu.communicate.entity.FamilyInfo;
import com.qianyu.communicate.entity.GiftModel;
import com.qianyu.communicate.entity.LiveRoomShare;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.User;
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

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.views.webView.MyWebChromeClient;
import com.qianyu.communicate.views.webView.MyWebView;
import com.google.gson.GsonBuilder;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.CommonPopupWindow;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.BitmapUtis;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.PixelUtil;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;
import tv.buka.sdk.BukaSDKManager;
import tv.buka.sdk.entity.Rpc;
import tv.buka.sdk.entity.Status;
import tv.buka.sdk.listener.ReceiptListener;
import tv.buka.sdk.utils.JsonUtils;

import static com.bkrtc_sdk.bkrtc_impl.GetInstance;
import static com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil.RPC_HTML_PPT_ACTION;
import static com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil.RPC_HTML_PPT_URL;
import static com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil.WHITE_BOARD;
import static com.qianyu.communicate.utils.Tools.isAppInstalled;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class ChatRoomLiveActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mNameTv)
    TextView mNameTv;
    @InjectView(R.id.mJobTitle)
    TextView mJobTitle;
    @InjectView(R.id.mTopicTv)
    TextView mTopicTv;
    @InjectView(R.id.mHeadImg1)
    SimpleDraweeView mHeadImg1;
    @InjectView(R.id.mHeadImg2)
    SimpleDraweeView mHeadImg2;
    @InjectView(R.id.mHeadImg3)
    SimpleDraweeView mHeadImg3;
    @InjectView(R.id.mFrameLL1)
    FrameLayout mFrameLL1;
    @InjectView(R.id.mFrameLL2)
    FrameLayout mFrameLL2;
    @InjectView(R.id.mFrameLL3)
    FrameLayout mFrameLL3;
    @InjectView(R.id.mRecylerView)
    ListView contentListView;
    @InjectView(R.id.mHeadImgVoice)
    SimpleDraweeView mHeadImgVoice;
    @InjectView(R.id.mPlayImg)
    SimpleDraweeView mPlayImg;
    @InjectView(R.id.et_sendmessage)
    EditText etSendmessage;
    @InjectView(R.id.iv_face_normal)
    ImageView ivFaceNormal;
    @InjectView(R.id.iv_face_checked)
    ImageView ivFaceChecked;
    @InjectView(R.id.rl_face)
    RelativeLayout rlFace;
    @InjectView(R.id.btn_more)
    Button btnMore;
    @InjectView(R.id.btn_send)
    TextView btnSend;
    @InjectView(R.id.mLessonLL)
    LinearLayout mLessonLL;
    @InjectView(R.id.lessonImg)
    ImageView lessonImg;
    @InjectView(R.id.liveFuncLL)
    LinearLayout liveFuncLL;
    @InjectView(R.id.lessonsLL)
    LinearLayout lessonsLL;
    @InjectView(R.id.mFrameLL)
    FrameLayout mFrameLL;
    @InjectView(R.id.mLineRecylerView)
    ListView userListView;
    @InjectView(R.id.id_menu)
    SlidingMenu slidingMenu;
    @InjectView(R.id.editLogo)
    ImageView editLogo;
    @InjectView(R.id.shareLogo)
    ImageView shareLogo;
    @InjectView(R.id.endLiveLogo)
    ImageView endLiveLogo;
    @InjectView(R.id.mTopicLL)
    LinearLayout mTopicLL;
    @InjectView(R.id.mOnLineLL)
    LinearLayout mOnLineLL;
    @InjectView(R.id.mVideoLL)
    LinearLayout mVideoLL;
    @InjectView(R.id.videoLogo)
    ImageView videoLogo;
    @InjectView(R.id.mJoinSpeakLL)
    LinearLayout mJoinSpeakLL;
    @InjectView(R.id.mTakePhotoLL)
    LinearLayout mTakePhotoLL;
    @InjectView(R.id.joinSpeakLogo)
    ImageView joinSpeakLogo;
    @InjectView(R.id.mCoseVideo)
    ImageView mCoseVideo;
    @InjectView(R.id.mFullVideo)
    ImageView mFullVideo;
    @InjectView(R.id.mVideoFL)
    DragViewGroup mVideoFL;
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
    @InjectView(R.id.iv_close)
    ImageView mIvClose;
    @InjectView(R.id.mWendaLogo)
    ImageView mWendaLogo;
    @InjectView(R.id.iv_previous)
    ImageView mIvPrevious;
    @InjectView(R.id.iv_next)
    ImageView mIvNext;
    @InjectView(R.id.iv_file)
    ImageView mIvFile;
    @InjectView(R.id.iv_photo)
    ImageView mIvPhoto;
    @InjectView(R.id.mBootomLL)
    LinearLayout mBootomLL;
    @InjectView(R.id.jz_video)
    JZVideoPlayerStandard jzVideo;
    private ContentAdapter contentAdapter; //聊天室消息adapter
    private UserAdapter userAdapter; //聊天室在线人adapter
    private UserBean userBean;//进入聊天室的人及其相关信息 注意属性不是实时的
    private EmotionKeyboard mEmotionKeyboard; //表情相关
    private GlobalOnItemClickManagerUtils globalOnItemClickManager; //表情相关
    private String signal;                //当前用户是否关注头像所点击出来的用户
    private GiftControl giftControl;    //展示礼物连击效果控制器
    private BukaHelper bukaHelper;      //布卡sdk工具类
    private boolean joinSpeak = false;  //是否已经连麦
    private int mVideoWidth;
    private int mVideoHeight;
    private static final int ROOM_VIDEO_NUM = 7;
    private static final int DEFAULT_VIDEO_NUM = ROOM_VIDEO_NUM - 3;
    private DocPagerEntity mDocPagerEntity;  //文档类
    private MediaManager mMediaManager;
    private String uuid;//文档的uuid
    private PhotoChoicePop mPhotoChoicePop;  //图片选择弹窗
    private boolean streamWait = false;     //是否有人在等待连麦
    private boolean isPPt; //是否是ppt
    private String mDocUrl;//全局webviewurl
    private int mCreateErrorNum = 0;
    private int MAX_CREATE_COUNT = 3;
    private boolean drag;
    private boolean giftFlag = false;
    private FamilyInfo familyInfo;
    private boolean closeFlag = false;
    private boolean pcPlay = false;
    private boolean atFlag = true;
    private String typeShare = "0";

    @Override
    public int getRootViewId() {
        return R.layout.activity_chat_room_live;
    }

    @Override
    public void setViews() {
        mHeadImgVoice.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
        initRecylerView();
        Animation rotate = AnimationUtils.loadAnimation(ChatRoomLiveActivity.this, R.anim.anim_rotate);
        mPlayImg.startAnimation(rotate);
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
                ChatRoomLiveActivity.this.drag = drag;
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
//        params.width = PixelUtil.getScreenWidth(this) ;
//        params.height = params.width * 9 / 16;
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
                        bukaHelper.exit(mStreamLayout_, mStreamLayout, mCoseVideo, mFullVideo);
                        finish();
                        break;
                    case 3:
                        ToastUtil.shortShowToast(str);
                        bukaHelper.exit(mStreamLayout_, mStreamLayout, mCoseVideo, mFullVideo);
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
                        if (!closeFlag) {
                            if (mDocPagerEntity != null) {
                                AppLog.e("==========mDocPagerEntity============" + mDocPagerEntity.toString());
                                sendDocRpc(false, 1, mDocPagerEntity.getSlide(), mDocPagerEntity.getLastSlideIndex());
                            } else {
                                //=======dyj===========
                                if (mDocUrl != null && !mDocUrl.contains("Whiteboard")) {
                                    sendDocRpc(true, 1, 1, 0);
                                }
                                //=======dyj===========
                            }
//                        bukaHelper.rpc(null, Constant.RPC_VIDEO_PLAY, videoUrlList[0]);
                        }
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
                switch ((int) rpc.getType()) {
                    case Constant.RPC_GIFT:
                        if (!TextUtils.isEmpty(rpc.getMessage())) {
                            giftFlag = true;
                            loadDatas();
                        }
                        break;
                    case Constant.RPC_STREAM_WAIT:
                        if (!streamWait) {
                            streamWait = true;
                            showJoinSpeakPopWindow(rpc);
                        }
                        break;
                }
            }
        });
        bukaHelper.setOnStatusListener(new BukaHelper.OnStatusListener() {
            @Override
            public void onStatusListener(int type, Status status) {
                if (type == 1) {
                    bukaHelper.stopPlay(mStreamLayout_, mCoseVideo, mFullVideo);
                    //删除视频二路流
//                    delStream(status);
                    if (bukaHelper.getSessionUser(status.getSession_id()).getUserId()== bukaHelper.getCurrentUser().getUserId()) {
                        bukaHelper.stopPublish(mStreamLayout, mCoseVideo, mFullVideo);
                        bukaHelper.stopPlay(mStreamLayout_, mCoseVideo, mFullVideo);
                        bukaHelper.rpc(null, Constant.RPC_STREAM_LIVE_END, ""+bukaHelper.getCurrentUser().getUserId());
                    }
                } else {
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
                    mIvClose.setVisibility(View.GONE);
                }
                JZVideoPlayer.releaseAllVideos();
                jzVideo.setVisibility(View.GONE);
            }
        });

        bukaHelper.setOnVideoPublishListener(new BukaHelper.OnVideoPublishListener() {
            @Override
            public void onVideoPublish() {
                JZVideoPlayer.releaseAllVideos();
                jzVideo.setVisibility(View.GONE);
            }
        });
    }

    private boolean btnMoreFlag = false;
    private boolean fullFlag = false;

    @OnClick({R.id.mWendaLogo, R.id.mFullVideo, R.id.lessonsLL, R.id.mVideoLL, R.id.mJoinSpeakLL, R.id.mTakePhotoLL, R.id.mOnLineLL, R.id.mTopicLL, R.id.btn_more, R.id.btn_send, R.id.mLessonLL, R.id.et_sendmessage, R.id.mHeadImg, R.id.editLogo, R.id.shareLogo, R.id.endLiveLogo, R.id.stream_layout, R.id.iv_previous, R.id.iv_next, R.id.iv_file, R.id.iv_photo, R.id.iv_close, R.id.mCoseVideo})
    public void onViewClicked(View view) {
        List<Status> statusList = BukaSDKManager.getStatusManager().getStatusArr(Constant.STATUS_TAG);
        Intent intent3 = new Intent(ChatRoomLiveActivity.this, LessonChosenActivity_.class);
        intent3.putExtra("userBean", userBean);
        switch (view.getId()) {
            case R.id.mWendaLogo:
                SpringUtils.springAnim(mWendaLogo);
//                if (user == null) {
//                    ToastUtil.shortShowToast("请先登录...");
//                    startActivity(new Intent(ChatRoomUserActivity.this, RegistActivity.class));
//                    return;
//                }
                if (familyInfo != null) {
                    Intent intent2 = new Intent(ChatRoomLiveActivity.this, QAActivity.class);
                    intent2.putExtra("family", familyInfo);
                    startActivity(intent2);
                }
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
            case R.id.lessonsLL:
//                startActivity(new Intent(ChatRoomLiveActivity.this, LessonChosenActivity.class));
                break;
            case R.id.mVideoLL:
                SpringUtils.springAnim(mVideoLL);
                if (statusList != null && statusList.size() > 0) {
                    ToastUtil.shortShowToast("您已经开过视频啦...");
                } else {
                    bukaHelper.publish(mStreamLayout, false, mCoseVideo, mFullVideo);
                }
                break;
            case R.id.mJoinSpeakLL:
                SpringUtils.springAnim(mJoinSpeakLL);
//                showJoinSpeakPopWindow();
                showPhotoChoicePop();
                break;
            case R.id.mTakePhotoLL:
                SpringUtils.springAnim(mTakePhotoLL);
                if (statusList != null && statusList.size() > 0) {
                    ToastUtil.shortShowToast("开视频的时候不能拍照哦...");
                } else {
                    requestCameraStorage();
                }
                break;
            case R.id.mOnLineLL:
                slidingMenu.toggle();
                break;
            case R.id.mTopicLL:
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(ChatRoomLiveActivity.this, RegistActivity.class));
                    return;
                }
                Intent intent = new Intent(ChatRoomLiveActivity.this, FansRankListActivity.class);
                intent.putExtra("userId", userBean.getUserId() + "");
                intent.putExtra("fansId", "");
                startActivity(intent);
                break;
            case R.id.btn_more:
                SpringUtils.springAnim(btnMore);
//                btnMoreFlag = !btnMoreFlag;
                KeyBoardUtils.hideSoftInput(etSendmessage);
                emotionContainer.setVisibility(View.GONE);
//                if (btnMoreFlag) {
                liveFuncLL.setVisibility(View.VISIBLE);
//                    lessonsLL.setVisibility(View.GONE);
//                } else {
//                    liveFuncLL.setVisibility(View.GONE);
//                    lessonsLL.setVisibility(View.GONE);
//                }
                break;
            case R.id.btn_send:
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
            case R.id.mLessonLL:
                //==========dyj=============
//                mWebView.loadUrl(WHITE_BOARD);
                //==========dyj=============
                SpringUtils.springAnim(mLessonLL);
                liveFuncLL.setVisibility(View.GONE);
//                lessonsLL.setVisibility(View.VISIBLE);
                closeFlag = false;
                startActivityForResult(intent3, Constant.DOCUMENT_CODE);
                break;
            case R.id.et_sendmessage:
                slidingMenu.closeMenu();
                btnMoreFlag = false;
                liveFuncLL.setVisibility(View.GONE);
                lessonsLL.setVisibility(View.GONE);
                break;
            case R.id.mHeadImg:
//                if (infoFlag) {
//                    infoFlag = false;
//                    showPersonalInfoPopWindow();
//                }
                Intent intent2 = new Intent(ChatRoomLiveActivity.this, PersonalPageActivity.class);
                intent2.putExtra("userId", userBean.getUserId());
                startActivity(intent2);
                break;
            case R.id.editLogo:
                SpringUtils.springAnim(editLogo);
                Intent intent1 = new Intent(ChatRoomLiveActivity.this, ChatRoomCretaeActivity.class);
                FamilyInfo familyInfo1 = new FamilyInfo();
                familyInfo1.setFid(Integer.parseInt(userBean.getGroupId()));
                intent1.putExtra("family", this.familyInfo == null ? familyInfo1 : this.familyInfo);
                startActivity(intent1);
                break;
            case R.id.shareLogo:
                SpringUtils.springAnim(shareLogo);
//                showSharePopWindow();
                if (this.familyInfo != null && userBean != null) {
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
                new AlertDialog.Builder(ChatRoomLiveActivity.this).setTitle("退出聊天室?")
                        .setMessage("您是否确定退出聊天室？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                if (mWebView == null) {
                                    finish();
                                    return;
                                }
                                mWebView.loadUrl(WHITE_BOARD);
                                mDocUrl = WHITE_BOARD;
                                mTvPage.setVisibility(View.GONE);
                                isPPt = false;
                                setWebviewSize(19);
                                if (!pcPlay) {
                                    sendDocRpc(false, 2, 1, 0);
                                }
                                mDocPagerEntity = null;
                                if (!pcPlay) {
                                    bukaHelper.rpc(null, Constant.RPC_STREAM_LIVE_END, ""+bukaHelper.getCurrentUser().getUserId());
                                }
                                bukaHelper.exit(mStreamLayout_, mStreamLayout, mCoseVideo, mFullVideo);
                                finish();
                            }
                        }).create().show();
                break;
            case R.id.stream_layout:
                AppLog.e("==========stream_layout=================");
                if (!drag) {
                    bukaHelper.switchCamera();
                }
                break;
            case R.id.iv_previous:
                AppLog.e("==============iv_previous================");
                if (mWebView == null || mDocPagerEntity == null) {
                    return;
                }
                mWebView.loadUrl("javascript:bukaPrevious()");
                mWebView.scrollTo(0, 0);
                break;
            case R.id.iv_next:
                AppLog.e("==============iv_next================");
                if (mWebView == null || mDocPagerEntity == null) {
                    return;
                }
                mWebView.loadUrl("javascript:bukaNext()");
                mWebView.scrollTo(0, 0);
                break;
            case R.id.iv_file:
                closeFlag = false;
                startActivityForResult(intent3, Constant.DOCUMENT_CODE);
                break;
            case R.id.iv_photo:
                showPhotoChoicePop();
                break;
            case R.id.iv_close:
                closeFlag = true;
                AppLog.e("==============iv_close================");
                if (mWebView == null) {
                    return;
                }
//                mWebView.loadUrl(WHITE_BOARD);
//                mDocUrl = WHITE_BOARD;
//                mTvPage.setVisibility(View.GONE);
                mFlContext.setVisibility(View.GONE);
//                isPPt = false;
//                setWebviewSize(19);
                sendDocRpc(false, 2, 1, 0);
//                mDocPagerEntity = null;
                break;
            case R.id.mCoseVideo:
                bukaHelper.stopPublish(mStreamLayout, mCoseVideo, mFullVideo);
                bukaHelper.stopPlay(mStreamLayout_, mCoseVideo, mFullVideo);
                bukaHelper.rpc(null, Constant.RPC_STREAM_LIVE_END, ""+bukaHelper.getCurrentUser().getUserId());
                break;
        }
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
    }

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
                        List<LinearLayout> imgViews = new ArrayList<>();
                        imgViews.add(mImg1);
                        imgViews.add(mImg2);
                        imgViews.add(mImg3);
                        imgViews.add(mImg4);
                        imgViews.add(mImg5);
                        imgViews.add(mImg6);
                        showOpenAnim(PixelUtil.dp2px(ChatRoomLiveActivity.this, 100), imgViews);
//                        if (!TextUtils.isEmpty(userBean.getUserType())) {
//                            switch (userBean.getUserType()) {
//                                case "1":
//                                    toBeManagerTv.setText("取消管理员");
//                                    forbidSpeakTv.setText(TextUtils.equals("0", userBean.getUserstate()) ? "禁言" : "解禁");
//                                    break;
//                                case "0":
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
                                bukaHelper.rpc(null, Constant.RPC_MANAGER, ""+userBean.getUserId());
                            }
                        });
                        mImg3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg3);
                                popupWindow.dismiss();
                                ToastUtil.shortShowToast("主播不用送礼啦...");
                            }
                        });
                        mImg4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg4);
                                popupWindow.dismiss();
                                bukaHelper.rpc(null, Constant.RPC_SPEAK, ""+userBean.getUserId());
                            }
                        });
                        mImg5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg5);
                                popupWindow.dismiss();
                                bukaHelper.rpc(null, Constant.RPC_TICKOUT, ""+userBean.getUserId());
                            }
                        });
                        mImg6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(mImg6);
                                User user = MyApplication.getInstance().user;
                                if (user == null) {
                                    ToastUtil.shortShowToast("请先登录...");
                                    startActivity(new Intent(ChatRoomLiveActivity.this, RegistActivity.class));
                                    return;
                                }
                                if (userBean.getUserId() == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能和自己聊天哦...");
                                    return;
                                }
                                Intent intent3 = new Intent(ChatRoomLiveActivity.this, ChatActivity.class);
                                intent3.putExtra(com.qianyu.chatuidemo.Constant.EXTRA_USER_ID, userBean.getPhone());
                                startActivity(intent3);
                            }
                        });
                        mHeadImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SpringUtils.springAnim(mHeadImg);
                                Intent intent = new Intent(ChatRoomLiveActivity.this, PersonalPageActivity.class);
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

    private void showJoinSpeakPopWindow(final Rpc rpc) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.layout_join_speak)
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
                        RecyclerView mJoinSpeakRecylerView = view.findViewById(R.id.mJoinSpeakRecylerView);
                        TextView speakingNow = view.findViewById(R.id.speakingNow);
                        TextView waitSpeak = view.findViewById(R.id.waitSpeak);
                        LinearLayout allowSpeakLL = view.findViewById(R.id.allowSpeakLL);
                        final ImageView allowSpeakLogo = view.findViewById(R.id.allowSpeakLogo);
                        TextView mAcceptTv = view.findViewById(R.id.mAcceptTv);
                        TextView mRejectTv = view.findViewById(R.id.mRejectTv);
                        if (joinSpeak) {
                            speakingNow.setText("1/1");
                            waitSpeak.setText("1");
                        } else {
                            speakingNow.setText("0/1");
                            waitSpeak.setText("0");
                        }
                        allowSpeakLogo.setImageResource(joinSpeak ? R.mipmap.shezhi_dakai : R.mipmap.shezhi_guanbi);
                        allowSpeakLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (streamWait) {
                                    joinSpeak = !joinSpeak;
                                    allowSpeakLogo.setImageResource(joinSpeak ? R.mipmap.shezhi_dakai : R.mipmap.shezhi_guanbi);
                                    bukaHelper.rpc(null, joinSpeak ? Constant.RPC_STREAM_CONNECT : Constant.RPC_STREAM_END, ""+bukaHelper.getCurrentUser().getUserId());
                                    streamWait = false;
                                }
                            }
                        });
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                                StaggeredGridLayoutManager.VERTICAL);
                        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
                        mJoinSpeakRecylerView.setHasFixedSize(true);
                        mJoinSpeakRecylerView.setLayoutManager(layoutManager);
                        JoinSpeakAdapter adapter = new JoinSpeakAdapter(ChatRoomLiveActivity.this, null);
                        mJoinSpeakRecylerView.setAdapter(adapter);

                        mAcceptTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (streamWait) {
                                    joinSpeak = true;
                                    bukaHelper.rpc(null, Constant.RPC_STREAM_CONNECT, rpc.getMessage());
                                    streamWait = false;
                                    popupWindow.dismiss();
                                    ToastUtil.shortShowToast("同意连麦...");
                                }
                            }
                        });
                        mRejectTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (streamWait) {
                                    joinSpeak = false;
                                    bukaHelper.rpc(null, Constant.RPC_STREAM_END, rpc.getMessage());
                                    streamWait = false;
                                    popupWindow.dismiss();
                                    ToastUtil.shortShowToast("拒绝连麦...");
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
        popupWindow.showAtLocation(contentListView, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                streamWait = false;
            }
        });
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
                        final LinearLayout mFansLL = view.findViewById(R.id.mFansLL);
                        final LinearLayout mFirendDetailLL = view.findViewById(R.id.mFirendDetailLL);
                        final LinearLayout mOperateLL = view.findViewById(R.id.mOperateLL);
                        mOperateLL.setVisibility(View.GONE);
                        mFansLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChatRoomLiveActivity.this, MyConcernActivity.class);
                                intent.putExtra("sign", "2");
                                //userId 某个人的id
                                intent.putExtra("userId", userBean.getUserId());
                                startActivity(intent);
                            }
                        });
                        mFirendDetailLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ChatRoomLiveActivity.this, MyCircleActivity.class);
                                intent.putExtra("userId", userBean.getUserId());
                                startActivity(intent);
                            }
                        });
                        mHeadImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ChatRoomLiveActivity.this, PersonalPageActivity.class);
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
        popupWindow.showAtLocation(contentListView, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                infoFlag = true;
            }
        });
    }

    private void initCircleRecylerView(MyRecylerView mCircleRecylerView, final UserBean userBean) {
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
                Intent intent = new Intent(ChatRoomLiveActivity.this, MyCircleActivity.class);
                intent.putExtra("userId", userBean.getUserId());
                startActivity(intent);
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
        mEmotionKeyboard.setOnEmotionClickListener(new EmotionKeyboard.OnEmotionClickListener() {
            @Override
            public void onEmotionClick() {
                liveFuncLL.setVisibility(View.GONE);
                lessonsLL.setVisibility(View.GONE);
            }
        });
    }

    private void initRecylerView() {
        contentAdapter = new ContentAdapter(this);
        contentListView.setAdapter(contentAdapter);
        contentListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                btnMoreFlag = false;
                KeyBoardUtils.hideSoftInput(etSendmessage);
                liveFuncLL.setVisibility(View.GONE);
                lessonsLL.setVisibility(View.GONE);
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
                if (currentUser.getUserId()== bukaHelper.getSessionUser(user.getSession_id()).getUserId()) {
                    ToastUtil.shortShowToast("不能对自己操作哦...");
                    return;
                }
                UserBean userBean = new UserBean(user.getUser_extend());
                showOnLinePopWindow(userBean);
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
        if (!pcPlay) {
            bukaHelper.rpc(null, Constant.RPC_STREAM_LIVE_END, bukaHelper.getCurrentUser().getUserId()+"");
        }
        bukaHelper.exit(mStreamLayout_, mStreamLayout, mCoseVideo, mFullVideo);
        globalOnItemClickManager.unAttachToEditText();
        if (mWebView == null) {
            return;
        }
        mWebView.loadUrl(WHITE_BOARD);
        mDocUrl = WHITE_BOARD;
        mTvPage.setVisibility(View.GONE);
        isPPt = false;
        setWebviewSize(19);
        if (!pcPlay) {
            sendDocRpc(false, 2, 1, 0);
        }
        mDocPagerEntity = null;
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
        mWebView.setClick(true);
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
            BukaSDKManager.getRpcManager().sendBroadcastRpcWithSocket(str, RPC_HTML_PPT_ACTION, 0, null);
        }

        @JavascriptInterface
        public void pptChanged(final String str) {
            ChatRoomLiveActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if ("loadingEnd".equals(str)) {
                        //貌似不需要处理。。。
                    } else if (str.startsWith("{") && str.endsWith("}")) {
                        AppLog.e("==========pptChanged=========" + str);
                        mDocPagerEntity = JsonUtils.getBean(str, DocPagerEntity.class);
                        Spannable sp = new SpannableString(mDocPagerEntity.getSlide() + "/" + mDocPagerEntity.getLastSlideIndex());
                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#D63F41"));
                        sp.setSpan(colorSpan, 0, ("" + mDocPagerEntity.getSlide()).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        sp.setSpan(new AbsoluteSizeSpan(14, true), 0, ("" + mDocPagerEntity.getSlide()).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mTvPage.setText(sp);
                        if (isPPt) {
                            mTvPage.setVisibility(View.VISIBLE);
                            //===========dyj=========
                            mIvPrevious.setVisibility(View.VISIBLE);
                            mIvNext.setVisibility(View.VISIBLE);
                        } else {
                            mTvPage.setVisibility(View.GONE);
                            mIvPrevious.setVisibility(View.GONE);
                            mIvNext.setVisibility(View.GONE);
                        }
                        //===========dyj=========
                        //画笔翻页，这两句都要有
                        mWebView.loadUrl("javascript:bukaPage(" + mDocPagerEntity.getSlide() + ")");
                        mWebView.loadUrl("javascript:bukaPageCount(" + mDocPagerEntity.getLastSlideIndex() + ")");

                        sendDocRpc(false, 1, mDocPagerEntity.getSlide(), mDocPagerEntity.getLastSlideIndex());
                    }
                }
            });
        }
    }

    private void sendDocRpc(boolean isNewDoc, int view, int slide, int step) {
        if (isNewDoc || TextUtils.isEmpty(uuid)) {//打开了新文档，关闭文档，都算
            uuid = UUID.randomUUID().toString();
        }
        DocRpcEntity rpcBean = new DocRpcEntity();
        if (view == 1) {
            rpcBean.setId(uuid);
            rpcBean.setView(view);
            rpcBean.setPage(slide);
            rpcBean.setStep(step);
            rpcBean.setUrl(mDocUrl);
            rpcBean.setPpt(isPPt);
            rpcBean.setWhite(!isPPt);
            rpcBean.setMode(1);
            rpcBean.setCount(step);

            GsonBuilder gb = new GsonBuilder();
            gb.disableHtmlEscaping();
            String str1 = gb.create().toJson(rpcBean);
            AppLog.e("==========str1============" + str1.toString());
//            BukaSDKManager.getRpcManager().sendBroadcastRpcWithSocket(str1, RPC_HTML_PPT_URL, 0, new ReceiptListener<Object>() {
//                @Override
//                public void onSuccess(Object o) {
//                    AppLog.e("==========onSuccess============");
//                }
//
//                @Override
//                public void onError() {
//                    AppLog.e("==========onError============");
//                }
//            });
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("view", 2);
                jsonObject.put("id", uuid);
                BukaSDKManager.getRpcManager().sendBroadcastRpcWithSocket(jsonObject.toString(), RPC_HTML_PPT_URL, 0, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.DOCUMENT_CODE && resultCode == Constant.DOCUMENT_CODE_) {
            if (data != null) {
                DocumentInfo.DataBean docImageEntity = (DocumentInfo.DataBean) data.getSerializableExtra(Constant.DOCUMENT_DATA);
                AppLog.e("=============docImageEntity111=============" + docImageEntity.toString());
                if (docImageEntity != null) {
                    String docType = docImageEntity.getFileName()
                            .substring(docImageEntity.getFileName().lastIndexOf(".") + 1).toLowerCase();
                    if (TextUtils.equals(ConstantUtil.FILE_PNG, docType) || TextUtils.equals(ConstantUtil.FILE_JPG, docType) || TextUtils.equals(ConstantUtil.FILE_JPEG, docType)) {
                        playImage(docImageEntity.getFileUrl());
                    } else if (TextUtils.equals(ConstantUtil.FILE_MP4, docType) || TextUtils.equals(ConstantUtil.FILE_FLV, docType) || TextUtils.equals(ConstantUtil.FILE_WMV, docType) || TextUtils.equals(ConstantUtil.FILE_M3U8, docType)) {
                        jzVideo.setVisibility(View.VISIBLE);
                        JZVideoPlayer.releaseAllVideos();
                        jzVideo.setUp(docImageEntity.getFileUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
                        jzVideo.startVideo();
//                        Glide.with(ChatRoomLiveActivity.this).load(R.mipmap.morenzuopingzhaopian).into(jzVideo.thumbImageView);
                        bukaHelper.rpc(null, Constant.RPC_VIDEO_PLAY, docImageEntity.getFileUrl());
                }
            }
            //===========dyj==========
            else {
//                mWebView.loadUrl(mDocUrl);
            }
            //===========dyj==========
        }
    }}

    private void playImage(final String url) {
        AppLog.e("=============docImageEntity222=============");
        //======dyj=========
        mWebView.loadUrl(WHITE_BOARD);
        //======dyj======
        mFlContext.setVisibility(View.VISIBLE);
        //======dyj=========
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:bukaAddImg('" + url + "')");
            }
        }, 1000);
        //======dyj=========
//                        mWebView.loadUrl("javascript:bukaAddImg('" + url + "')");
        mDocUrl = "javascript:bukaAddImg('" + url + "')";
        //===========dyj==========
        mTvPage.setVisibility(View.GONE);
        mIvPrevious.setVisibility(View.GONE);
        mIvNext.setVisibility(View.GONE);
        isPPt = false;
//                        setWebviewSize(16);
//                        sendDocRpc(false, 2, 1, 0);
        sendDocRpc(true, 1, 1, 0);
        //===========dyj==========
    }

    /**
     * 文档转换
     *
     * @param url
     */
    private void changeDoc(final String url) {
//        Tools.showWaitDialog(ChatRoomLiveActivity.this, "文档转换中...");
        Tools.showWaitDialog(ChatRoomLiveActivity.this, "文件正在转换中...");
        final Timer timer = new Timer();
    }

    private void showPhotoChoicePop() {
        if (mPhotoChoicePop == null) {
            mPhotoChoicePop = new PhotoChoicePop(this, photoCallBack);
        }
        mPhotoChoicePop.showPop(mTopicLL);
    }

    /**
     * 图片选择器
     */
    private PhotoChoicePop.CallBackPop photoCallBack = new PhotoChoicePop.CallBackPop() {
        @Override
        public void close(final String path) {
            uploadImage(path);
        }
    };

    /**
     * 上传图片
     *
     * @param path
     */
    private void uploadImage(String path) {
        BitmapUtis.compress(path, 480, 800, new BitmapUtis.CompressCallback() {
            @Override
            public void onsucces(String s) {
                File file = new File(s);
                if (file == null) {
                    Toast.makeText(ChatRoomLiveActivity.this, "图片格式无效", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = MyApplication.getInstance().user;
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(ChatRoomLiveActivity.this, RegistActivity.class));
                    return;
                }
            }

            @Override
            public void onfail() {
                ToastUtil.shortShowToast("图片压缩失败!");
            }
        });
    }

    /**
     * url拼接
     *
     * @param url
     */
    private void addWebView(String url) {
        if (url.lastIndexOf("?") > 0) {
            url += "&ptv=5";
        } else {
            url += "?ptv=5";
        }
        mWebView.loadUrl(url);
        mDocUrl = url;
    }

    private SurfaceViewRenderer mTeacherSecondRenderer;


    private void delTeacherSecondVideo(final StreamInfoEntity stream) {
        mMediaManager.stopPlay(stream, mTeacherSecondRenderer);
        mFlContentVideo.removeAllViews();
        mFlContentVideo.setVisibility(View.INVISIBLE);
        mRlContentWeb.setVisibility(View.VISIBLE);
    }

    private SurfaceViewRenderer createSurfaceViewRenderer() {
        SurfaceViewRenderer renderer = null;
        try {
            renderer = new SurfaceViewRenderer(ChatRoomLiveActivity.this);
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
        UMImage image = new UMImage(ChatRoomLiveActivity.this, TextUtils.isEmpty(liveRoomShare.getFamilyPath()) ? "" : liveRoomShare.getFamilyPath());
        UMWeb web = new UMWeb(TextUtils.isEmpty(liveRoomShare.getUrl()) ? "" : liveRoomShare.getUrl());
        web.setTitle(TextUtils.isEmpty(liveRoomShare.getFamilyName()) ? "" : liveRoomShare.getFamilyName());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(TextUtils.isEmpty(liveRoomShare.getFamilyDescribe()) ? "" : liveRoomShare.getFamilyDescribe());//描述
        new ShareAction(ChatRoomLiveActivity.this)
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
                if (!isAppInstalled(ChatRoomLiveActivity.this, "com.tencent.mobileqq") && !isAppInstalled(ChatRoomLiveActivity.this, "com.tencent.tim")) {
                    ToastUtil.shortShowToast("请先安装QQ或者TIM客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                if (!isAppInstalled(ChatRoomLiveActivity.this, "com.tencent.mm")) {
                    ToastUtil.shortShowToast("请先安装微信客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.SINA) {
                if (!isAppInstalled(ChatRoomLiveActivity.this, "com.sina.weibo")) {
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
            Toast.makeText(ChatRoomLiveActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ChatRoomLiveActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ChatRoomLiveActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    public static final int CAMERA_PERMISSION = 10002;
    private final int REQUEST_CODE_CAMERA = 1000;

    @AfterPermissionGranted(CAMERA_PERMISSION)
    public void requestCameraStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
        } else {
//            EasyPermissions.requestPermissions(this, "", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.CAMERA);
            EasyPermissions.requestPermissions(this, "", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private GalleryFinal.OnHandlerResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHandlerResultCallback() {

        @Override
        public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                final PhotoInfo photoInfo = resultList.get(0);
                if (photoInfo != null) {
                    AppLog.e("=============imgUrl==========" + photoInfo.getPhotoPath());
//                    Tools.showDialog(InfoCompleteActivity.this);
                    uploadImage(photoInfo.getPhotoPath());
                }
            }
        }

        @Override
        public void onHandlerFailure(int requestCode, String errorMsg) {
            AppLog.e("=============onHandlerFailure====================");
        }
    };

    @Override
    public void onPermissionsGranted(List<String> perms) {
        AppLog.e("=============onPermissionsGranted111====================");
    }

    @Override
    public void onPermissionsDenied(List<String> perms) {
        AppLog.e("=============onPermissionsDenied111====================");

    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
