package com.qianyu.communicate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.CommentAdapter;
import com.qianyu.communicate.adapter.PraiseHeadAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.emotions.fragments.EmotionComplateFragment;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.GlobalOnItemClickManagerUtils;
import com.qianyu.communicate.emotions.widget.EmotionKeyboard;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.entity.CommentList;
import com.qianyu.communicate.entity.PraiseList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.MyRecylerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.qianyu.communicate.image.ImagePreviewActivity;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.MyNineGridLayout;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class FriendDetailActivity extends BaseActivity {
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mUserName)
    TextView mUserName;
    @InjectView(R.id.mUserAge)
    TextView mUserAge;
    @InjectView(R.id.mUserLevel)
    TextView mUserLevel;
    @InjectView(R.id.mFamilyTv)
    TextView mFamilyTv;
    @InjectView(R.id.mContentTv)
    TextView mContentTv;
    @InjectView(R.id.layout_nine_grid)
    MyNineGridLayout layoutNineGrid;
    @InjectView(R.id.mTimeTv)
    TextView mTimeTv;
    @InjectView(R.id.mPraiseLogo)
    ImageView mPraiseLogo;
    @InjectView(R.id.mPraiseTv)
    TextView mPraiseTv;
    @InjectView(R.id.mPraiseLL)
    LinearLayout mPraiseLL;
    @InjectView(R.id.mCommentTv)
    TextView mCommentTv;
    @InjectView(R.id.mCommentLL)
    LinearLayout mCommentLL;
    @InjectView(R.id.mPraiseRecylerView)
    RecyclerView mPraiseRecylerView;
    @InjectView(R.id.mCommentRecylerView)
    MyRecylerView mCommentRecylerView;
    @InjectView(R.id.mCommentET)
    EditText mCommentET;
    @InjectView(R.id.mEmotionLogo)
    ImageView mEmotionLogo;
    @InjectView(R.id.btn_send)
    TextView btn_send;
    @InjectView(R.id.friendDetailLL)
    LinearLayout friendDetailLL;
    @InjectView(R.id.contentLL)
    LinearLayout contentLL;
    @InjectView(R.id.sexLL)
    LinearLayout sexLL;
    @InjectView(R.id.sexLogo)
    ImageView sexLogo;
    @InjectView(R.id.mCommentLogo)
    ImageView mCommentLogo;
    @InjectView(R.id.commentPraiseLL)
    LinearLayout commentPraiseLL;
    @InjectView(R.id.commentLL)
    LinearLayout commentLL;
    @InjectView(R.id.praiseLL)
    LinearLayout praiseLL;
    @InjectView(R.id.mBounceScrollView)
    ScrollView mBounceScrollView;
    @InjectView(R.id.emotionContainer)
    FrameLayout emotionContainer;
    private CommentAdapter commentAdapter;
    private CircleList.ListBean.ContentBean circleList;  //动态详情
    private int commentType = 1;//自定义评论类型：1评论，2回复
    private PraiseHeadAdapter praiseHeadAdapter;
    private EmotionKeyboard mEmotionKeyboard;
    private GlobalOnItemClickManagerUtils globalOnItemClickManager;
    private CommentList.ContentBean commentList;//你要回复的人的信息
    private int pageNum = 0;
    private boolean commentFlag = true;
    private int position;//小于0是通过未读消息来得详情，其余情况皆为正常来详情的

    @Override
    public int getRootViewId() {
        return R.layout.activity_friend_detail;
    }

    @Override
    public void back(View view) {
        super.back(view);
        KeyBoardUtils.hideSoftInput(mCommentET);
    }

    @Override
    public void setViews() {
        setTitleTv("动态详情");
        initEmotions();
        contentLL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                commentType = 1;
                mCommentET.setHint("说点什么吧...");
                mCommentET.setText("");
                emotionContainer.setVisibility(View.GONE);
                KeyBoardUtils.hideSoftInput(mCommentET);
                return true;
            }
        });
        mBounceScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                commentType = 1;
                mCommentET.setHint("说点什么吧...");
                mCommentET.setText("");
                emotionContainer.setVisibility(View.GONE);
                KeyBoardUtils.hideSoftInput(mCommentET);
                return true;
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
                .bindToContent(mBounceScrollView)//绑定内容view
                .bindToEditText(((EditText) findViewById(R.id.mCommentET)))//判断绑定那种EditView
                .bindToEmotionButton(findViewById(R.id.mEmotionLogo))//绑定表情按钮
                .build();
//        点击表情的全局监听管理类
        globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance();
        //绑定EditText
        globalOnItemClickManager.attachToEditText(mCommentET);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            circleList = ((CircleList.ListBean.ContentBean) getIntent().getSerializableExtra("circleList"));
            position = getIntent().getIntExtra("position", 0);
            if (circleList != null) {
                initTopContent();
                initPraiseRecylerView();
                initCommentRecylerView();
                praiseList();
                commentList();
                showHidePraiseComment();
            }
        }
        mBounceScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 判断scrollview 滑动到底部
                // scrollY 的值和子view的高度一样，这人物滑动到了底部
                AppLog.e(mBounceScrollView.getScrollY() + "===========mBounceScrollView111==============" + mBounceScrollView.getHeight());
                AppLog.e(mBounceScrollView.getChildAt(0).getHeight() + "===========mBounceScrollView222==============");
                if (mBounceScrollView.getChildAt(0).getHeight() - mBounceScrollView.getHeight()
                        == mBounceScrollView.getScrollY()) {
                    if (commentFlag) {
                        pageNum++;
                        commentList();
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.mFamilyTv, R.id.btn_send, R.id.contentLL, R.id.mCommentLL, R.id.mPraiseLL, R.id.mHeadImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mFamilyTv:
                if (circleList != null) {
                    Tools.enterFamily(FriendDetailActivity.this,circleList.getGroupId(), false);
                }
                break;
            case R.id.btn_send:
                publishComment();
                break;
            case R.id.contentLL:
                emotionContainer.setVisibility(View.GONE);
                KeyBoardUtils.hideSoftInput(mCommentET);
                break;
            case R.id.mCommentLL:
                SpringUtils.springAnim(mCommentLL);
                commentType = 1;
                mCommentET.setHint("说点什么吧...");
                mCommentET.setText("");
                mCommentET.requestFocus();
                emotionContainer.setVisibility(View.GONE);
                KeyBoardUtils.openKeybord(mCommentET, FriendDetailActivity.this);
                break;
            case R.id.mPraiseLL:
                SpringUtils.springAnim(mPraiseLL);
                praise();
                break;
            case R.id.mHeadImg:
                Intent intent = new Intent(FriendDetailActivity.this, PersonalPageActivity.class);
                intent.putExtra("userId", circleList.getUserId());
                startActivity(intent);
                break;
        }
    }

    private void initTopContent() {
        mHeadImg.setImageURI(TextUtils.isEmpty(circleList.getHeadUrl()) ? "" : circleList.getHeadUrl());
        mUserName.setText(TextUtils.isEmpty(circleList.getNickName()) ? "" : circleList.getNickName());
        mPraiseLogo.setImageResource(circleList.getIsClick() == 0 ? R.mipmap.dongtai_dianzan : R.mipmap.dongtai_dianzan_se);
        Spanned spanned = Html.fromHtml("来自家族 <font color='#58e4df'><u>" + circleList.getGroupName() + "</u></font>");
        mFamilyTv.setText(spanned);
        mFamilyTv.setVisibility(TextUtils.isEmpty(circleList.getGroupName()) ? View.GONE : View.VISIBLE);
        int sex = circleList.getSex();
        switch (sex) {
            case 1:
                sexLL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_blue_));
                sexLogo.setImageResource(R.mipmap.xiangqing_nan1);
//                    mUserName.setTextColor(getResources().getColor(R.color.btn_blue_));
                break;
            case 2:
                sexLL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_pink));
                sexLogo.setImageResource(R.mipmap.xiangqing_nv1);
//                    mUserName.setTextColor(getResources().getColor(R.color.colorRed_));
                break;
        }
        mUserAge.setText("" + circleList.getAge());
        mUserLevel.setText("Lv  " + circleList.getLevel());
        mContentTv.setText(TextUtils.isEmpty(circleList.getTitle()) ? "" : circleList.getTitle());
        mContentTv.setVisibility(TextUtils.isEmpty(circleList.getTitle()) ? View.GONE : View.VISIBLE);
        mTimeTv.setText(TimeUtils.getTime(circleList.getCreateTime()));
        mPraiseTv.setText(NumberUtils.roundInt(circleList.getFabulous()));
        mCommentTv.setText(NumberUtils.roundInt(circleList.getComment()));
        initNineGridLayout();
    }

    private void showHidePraiseComment() {
        int p = Integer.parseInt(mPraiseTv.getText().toString());
        int c = Integer.parseInt(mCommentTv.getText().toString());
        if ((p > 0 || c > 0)) {
            praiseLL.setVisibility(p > 0 ? View.VISIBLE : View.GONE);
            commentLL.setVisibility(c > 0 ? View.VISIBLE : View.GONE);
            mCommentLogo.setVisibility(c > 0 ? View.VISIBLE : View.GONE);
            commentPraiseLL.setBackground(getResources().getDrawable(R.drawable.circle_detail));
        } else {
            mCommentLogo.setVisibility(View.GONE);
            commentPraiseLL.setBackground(null);
        }
    }

    private void commentList() {
        NetUtils.getInstance().commentList(circleList.getCircleId(), pageNum, 10, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                CommentList commentList = (CommentList) model.getModel();
                if (commentList != null) {
                    List<CommentList.ContentBean> list = commentList.getContent();
                    if (pageNum == 0) {
//                        commentAdapter.clear();aa
                    }
                    commentAdapter.append(list);
                    if (commentAdapter.data.size() >= commentList.getTotalElements()) {
                        commentFlag = false;
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, CommentList.class);
    }

    private void praiseList() {
        NetUtils.getInstance().praiseList(circleList.getCircleId(), -1, -1, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                PraiseList praiseList = (PraiseList) model.getModel();
                if (praiseList != null) {
                    List<PraiseList.ContentBean> list = praiseList.getContent();
                    praiseHeadAdapter.appendAll(list);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                praiseLL.setVisibility(View.GONE);
            }
        }, PraiseList.class);
    }

    private void praise() {
        final User user = MyApplication.getInstance().user;
        if (user != null) {
            NetUtils.getInstance().commentPraise(circleList.getCircleId(), user.getUserId(), circleList.getUserId(), 1, "", new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    if (praiseHeadAdapter.data.size() > 0) {
                        for (int i = 0; i < praiseHeadAdapter.data.size(); i++) {
                            final PraiseList.ContentBean contentBean = praiseHeadAdapter.data.get(i);
                            AppLog.e("=============000=============" + contentBean.toString());
                            if (contentBean.getUserId() == user.getUserId()) {
                                AppLog.e("=============111=============");
                                praiseHeadAdapter.removeSingle(contentBean);
                                mPraiseLogo.setImageResource(R.mipmap.dongtai_dianzan);
                                mPraiseTv.setText("" + (Integer.parseInt(mPraiseTv.getText().toString().trim()) - 1));
                                showHidePraiseComment();
                                if (position < 0) {
                                    EventBus.getDefault().post(new EventBuss(EventBuss.FRIEND_CIRCLE));
                                } else {
                                    EventBuss event = new EventBuss(EventBuss.CANCEL_PRAISE);
                                    event.setMessage(position);
                                    EventBus.getDefault().post(event);
                                }
                                return;
                            }
                        }
                    }
                    final PraiseList.ContentBean praiseSetBean = new PraiseList.ContentBean();
                    praiseSetBean.setHeadUrl(user.getHeadUrl());
                    praiseSetBean.setUserId(user.getUserId());
                    praiseSetBean.setNickName(user.getNickName());
                    AppLog.e("=============333=============");
                    praiseHeadAdapter.appendSingle(praiseSetBean);
                    mPraiseLogo.setImageResource(R.mipmap.dongtai_dianzan_se);
                    mPraiseTv.setText("" + (Integer.parseInt(mPraiseTv.getText().toString().trim()) + 1));
                    showHidePraiseComment();
                    if (position < 0) {
                        EventBus.getDefault().post(new EventBuss(EventBuss.FRIEND_CIRCLE));
                    } else {
                        EventBuss event = new EventBuss(EventBuss.ADD_PRAISE);
                        event.setMessage(position);
                        EventBus.getDefault().post(event);
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {
                    ToastUtil.shortShowToast(msg);
                }
            }, null);
        } else {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(FriendDetailActivity.this, RegistActivity.class));
        }
    }

    private void publishComment() {
        final String content = mCommentET.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.shortShowToast("请先输入文字...");
            return;
        }
        final User user = MyApplication.getInstance().user;
        if (user != null) {
            final String commentCotent = commentType == 1 ? content : ("回复" + commentList.getNickName() + ":" + content);
            long toUserId;
            if (commentType == 1) {
                toUserId = circleList.getUserId();
            } else {
                toUserId = commentList.getUserId();
            }
            //清空评论回复数据
            commentType = 1;
            mCommentET.setHint("说点什么吧...");
            mCommentET.setText("");
            emotionContainer.setVisibility(View.GONE);
            KeyBoardUtils.hideSoftInput(mCommentET);
            NetUtils.getInstance().commentPraise(circleList.getCircleId(), user.getUserId(), toUserId, 2, commentCotent, new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
//                    ToastUtil.shortShowToast(msg);
                    //评论回复数量+1
                    mCommentTv.setText("" + (1 + Integer.parseInt(mCommentTv.getText().toString().trim())));
                    showHidePraiseComment();
                    if (position < 0) {
                        EventBus.getDefault().post(new EventBuss(EventBuss.FRIEND_CIRCLE));
                    } else {
                        EventBuss event = new EventBuss(EventBuss.COMMENT);
                        event.setMessage(position);
                        EventBus.getDefault().post(event);
                    }
                    pageNum = 0;
                    commentList();
                }

                @Override
                public void onFail(String code, String msg, String result) {
                    ToastUtil.shortShowToast(msg);
                }
            }, null);
        } else {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(FriendDetailActivity.this, RegistActivity.class));
        }
    }

    private void initNineGridLayout() {
        List<String> mUrls = new ArrayList<>();
        String picPath = circleList.getFileItemUrl();
        if (!TextUtils.isEmpty(picPath)) {
            String[] split = picPath.split(",");
            for (int i = 0; i < split.length; i++) {
                mUrls.add(split[i]);
            }
        }
        layoutNineGrid.setUrlList(mUrls);
        layoutNineGrid.setOnImgClickListener(new MyNineGridLayout.OnImgClickListener() {
            @Override
            public void onClick(int p, String url, List<String> urlList) {
                final ArrayList<ImageItem> mImageList = new ArrayList<>();
                mImageList.clear();
                if (urlList != null && urlList.size() > 0) {
                    for (int i = 0; i < urlList.size(); i++) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.setPath(urlList.get(i));
                        mImageList.add(imageItem);
                    }
                    Intent intent = new Intent();
                    intent.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, p);
                    intent.setClass(FriendDetailActivity.this, ImagePreviewActivity.class);
                    intent.putParcelableArrayListExtra(ImagePreviewActivity.IMAGEURL, mImageList);
                    startActivity(intent);
                }
            }
        });
    }

    private void initPraiseRecylerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(8,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mPraiseRecylerView.setLayoutManager(layoutManager);
        praiseHeadAdapter = new PraiseHeadAdapter(this, null);
        mPraiseRecylerView.setAdapter(praiseHeadAdapter);
    }

    private void initCommentRecylerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mCommentRecylerView.setLayoutManager(layoutManager);
        commentAdapter = new CommentAdapter(this, null, circleList.getCircleId());
        mCommentRecylerView.setAdapter(commentAdapter);
        mCommentRecylerView.setNestedScrollingEnabled(false);

        commentAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                commentList = (CommentList.ContentBean) data.get(position);
                User user = MyApplication.getInstance().user;
                if (user != null && commentList.getUserId() == user.getUserId()) {
                    ToastUtil.shortShowToast("不能回复自己...");
                    return;
                }
                commentType = 2;
                mCommentET.setHint("回复 " + commentList.getNickName() + ":");
                mCommentET.setText("");
                mCommentET.requestFocus();
                emotionContainer.setVisibility(View.GONE);
                KeyBoardUtils.openKeybord(mCommentET, FriendDetailActivity.this);
            }
        });
        commentAdapter.setOnItemLongClickListener(new MyBaseAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, List data, int position) {
                final CommentList.ContentBean commentList = (CommentList.ContentBean) data.get(position);
                new AlertDialog.Builder(FriendDetailActivity.this).setTitle("删除该评论?")
                        .setMessage("您是否确定删除该评论？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                User user = MyApplication.getInstance().user;
                                if (user != null) {
                                    NetUtils.getInstance().deleteComment(circleList.getCircleId(), commentList.getCommentId(), user.getUserId(), new NetCallBack() {
                                        @Override
                                        public void onSuccess(String result, String msg, ResultModel model) {
                                            ToastUtil.shortShowToast(msg);
                                            commentAdapter.removeSingle(commentList);
                                            mCommentTv.setText("" + (Integer.parseInt(mCommentTv.getText().toString().trim()) - 1));
                                            if (FriendDetailActivity.this.position < 0) {
                                                EventBus.getDefault().post(new EventBuss(EventBuss.FRIEND_CIRCLE));
                                            } else {
                                                EventBuss event = new EventBuss(EventBuss.COMMENT_DELETE);
                                                event.setMessage(FriendDetailActivity.this.position);
                                                EventBus.getDefault().post(event);
                                            }
                                            showHidePraiseComment();
                                        }

                                        @Override
                                        public void onFail(String code, String msg, String result) {
                                            ToastUtil.shortShowToast(msg);
                                        }
                                    }, null);
                                }
                            }
                        }).create().show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
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
        /**
         * 按下返回键，如果表情显示，则隐藏，没有显示则回退页面
         */
        AppLog.e("=========onBackPressed==========" + mEmotionKeyboard.interceptBackPress());
        if (!mEmotionKeyboard.interceptBackPress()) {
            super.onBackPressed();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        globalOnItemClickManager.unAttachToEditText();
    }
}
