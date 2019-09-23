package com.qianyu.communicate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.hys.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.CommentTopicAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.emotions.fragments.EmotionComplateFragment;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.GlobalOnItemClickManagerUtils;
import com.qianyu.communicate.emotions.widget.EmotionKeyboard;
import com.qianyu.communicate.entity.AnswerDelBean;
import com.qianyu.communicate.entity.TopicDelBean;
import com.qianyu.communicate.entity.TopicEvalueBean;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.BottomDialog;
import com.qianyu.communicate.views.webView.NoScrollWebView;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.RefreshLayout;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.CustomToast;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.other.Gson;

import static com.qianyu.communicate.activity.BaseMListActivity.PAEG_SIZE;

/**
 * 话题详情
 */
public class TopicDelActivity extends BaseActivity implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {

    @InjectView(R.id.tv_all)
    TextView tvAll;
    @InjectView(R.id.mWebview)
    NoScrollWebView mWebview;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.rlly_evalue_1)
    RelativeLayout rllyEvalue;
    @InjectView(R.id.mHeadImg_1)
    SimpleDraweeView mHeadImg_1;
    @InjectView(R.id.tvName_1)
    TextView tvName_1;
    @InjectView(R.id.tvTitle_1)
    TextView tvTitle_1;
    @InjectView(R.id.mTimeTv1)
    TextView mTimeTv1;
    @InjectView(R.id.rlly_evalue_2)
    RelativeLayout rllyEvalue2;
    @InjectView(R.id.mHeadImg_2)
    SimpleDraweeView mHeadImg_2;
    @InjectView(R.id.tvName_2)
    TextView tvName_2;
    @InjectView(R.id.tvTitle_2)
    TextView tvTitle_2;
    @InjectView(R.id.mTimeTv2)
    TextView mTimeTv2;
    @InjectView(R.id.tv_allevalue)
    TextView tv_allevalue;
    @InjectView(R.id.tv_pl)
    TextView tvPlHint;
    @InjectView(R.id.view_line)
    View viewLine;
    @InjectView(R.id.llaySetcontent)
    LinearLayout llaySetcontent;
    @InjectView(R.id.llaySetevalue)
    LinearLayout llaySetevalue;
    @InjectView(R.id.llayZanMain)
    LinearLayout llayZanMain;
    @InjectView(R.id.ivZanMain)
    ImageView ivZanMain;
    @InjectView(R.id.tvZanMain)
    TextView tvZanMain;
    @InjectView(R.id.llay_root)
    LinearLayout llayRoot;
    @InjectView(R.id.tv_nickname)
    TextView tvNickname;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImag;
    @InjectView(R.id.tv_datetime)
    TextView tvDatetime;
    @InjectView(R.id.relyMainUser)
    RelativeLayout relyMainUser;
    private int PAGE_NUM = 0;
    List<TopicEvalueBean> evalueData = new ArrayList<>();// 评论列表
    AnswerDelBean answerBean;// 该页面数据对象
    private String topicTitle;
    private String topicId;
    private String commentId = "0";

    @Override
    public int getRootViewId() {
        return R.layout.activity_topic_del;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        topicId = getIntent().getStringExtra("topicId");
        commentId = getIntent().getStringExtra("commentId");
        topicTitle = getIntent().getStringExtra("topicTitle");
        setTitleTv("话题详情");
        getTopicDel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.TOPIC_ANSWER) {
            // 刷新页面
            answerBean.setCountNum((Integer.valueOf(answerBean.countNum) + 1) + "");
            tvAll.setText("查看全部回答（" + answerBean.countNum + "）");
        }
    }

    // 回答详情
    private void getTopicDel() {
        Tools.showDialog(this);
        NetUtils.getInstance().getTpicAnswerDel(topicId, commentId, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("回答详情", result);
                Tools.dismissWaitDialog();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    answerBean = new Gson().fromJson(dataObject + "", AnswerDelBean.class);
                    if (null != answerBean) {
                        llayRoot.setVisibility(View.VISIBLE);
                        tvAll.setText("查看全部回答（" + answerBean.countNum + "）");
                        tv_allevalue.setText("查看全部" + answerBean.topicComment.commentNum + "条评论");
                        evalueData = answerBean.commentList;
                        initEvalue();
                        initUser();
                        boolean isZanStatus = answerBean.fabulousFlag == 1 ? true : false;
                        initZan(isZanStatus, answerBean.topicComment.fabulousNum);
//                        mWebview.loadData(answerBean.topicComment.content, "text/html", "UTF-8");
                        mWebview.loadData(answerBean.topicComment.content, "text/html;charset=UTF-8", "UTF-8");
                    } else {
                        llayRoot.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Log.e("话题详情fail", result);
                Tools.dismissWaitDialog();
            }
        }, AnswerDelBean.class);
    }

    @Override
    public void initData() {

    }

    private void initUser() {
        try {
            tvTitle.setText(topicTitle);
            tvNickname.setText(answerBean.createUserInfo.nickName);
            mHeadImag.setImageURI(TextUtils.isEmpty(answerBean.createUserInfo.headUrl) ? "" : answerBean.createUserInfo.headUrl);
            tvDatetime.setText(TimeUtils.getDescriptionTimeFromTimestamp(answerBean.topicComment.createTime));
        } catch (Exception e) {

        }
    }

    // 查看个人详情
    private void intentPersonal(long userId) {
        Intent intent = new Intent(this, PersonalPageActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @OnClick({R.id.tv_all, R.id.tv_allevalue, R.id.llaySetcontent, R.id.llaySetevalue, R.id.llayZanMain, R.id.rlly_evalue_2, R.id.rlly_evalue_1, R.id.relyMainUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 查看贴住详情
            case R.id.relyMainUser:
                Log.e("传入的userId", answerBean.topicComment.userId);
                intentPersonal(Long.valueOf(answerBean.topicComment.userId));
                // this.startActivity(new Intent(this,PersonalPageActivity.class).putExtra("userId",delBean.userId));
                break;
            // 查看个人详情
            case R.id.rlly_evalue_1:
                if (evalueData.size() > 0) {
                    intentPersonal(evalueData.get(0).userId);
                }
                break;
            case R.id.rlly_evalue_2:
                if (evalueData.size() > 1) {
                    intentPersonal(evalueData.get(1).userId);
                }
                break;
            // 赞主贴
            case R.id.llayZanMain:
                submitTopicEvalue(-2, "", answerBean.topicComment.userId + "", 1);
                break;
            case R.id.tv_all:
                startActivity(new Intent(TopicDelActivity.this, TopicAllActivity.class).putExtra("topicId", topicId).putExtra("topicTitle", topicTitle));
                finish();
                break;
            case R.id.llaySetevalue:
            case R.id.tv_allevalue:
                initPop();
                break;
            case R.id.llaySetcontent:
                startActivity(new Intent(TopicDelActivity.this, TopicAnswerActivity.class).putExtra("topicId", topicId).putExtra("topicTitle", topicTitle));
                break;
        }
    }

    FrameLayout emotionContainerView;
    ImageView mEmotionLogo;
    EditText etMenuContent;
    XRecyclerView mContentRecylerView;
    TextView tvMenuAll;
    RefreshLayout mRefeshLy;
    private CommentTopicAdapter commentAdapter;
    private int commentType = 1;//自定义评论类型：1评论，2回复
    private String commentType2Name = "";// 回复昵称
    private String commentType2Uid = "";// 回复用户id

    private void initPop() {
        commentType = 1;
        commentType2Name = "";
        commentType2Uid = "";
        final BottomDialog dialog = new BottomDialog(TopicDelActivity.this, R.style.BottomSheetEdit);//, R.style.BottomSheetEdit dialog_soft_input
        View v = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog_layout, null);
        ImageView ivClose = (ImageView) v.findViewById(R.id.iv_close);
        tvMenuAll = (TextView) v.findViewById(R.id.tvMenuAll);
        mContentRecylerView = (XRecyclerView) v.findViewById(R.id.mRecycleviewEvalue);
        emotionContainerView = (FrameLayout) v.findViewById(R.id.emotionContainer);
        etMenuContent = (EditText) v.findViewById(R.id.et_content);
        mEmotionLogo = (ImageView) v.findViewById(R.id.mEmotionLogo);
        mRefeshLy = (RefreshLayout) v.findViewById(R.id.refesh_ly);
        TextView tvSubmit = (TextView) v.findViewById(R.id.tv_submit);
        tvMenuAll.setText("查看全部" + answerBean.topicComment.commentNum + "条评论");
        initCommentRecylerView();
        initEmotions();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMenuContent.getText().toString().equals("")) {
                    ToastUtils.getInstance().show(TopicDelActivity.this, "请填写评论内容");
                    return;
                }
                submitTopicEvalue(-1, etMenuContent.getText().toString(), answerBean.topicComment.userId, 2);
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.setContentView(v);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    // 获取评论
    private void getCommentData() {
        NetUtils.getInstance().topicDetaileEvalue(commentId, PAGE_NUM, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("话题详情评论", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data.length() > 0) {
                        List<TopicEvalueBean> tempData = new Gson().fromJson(data + "", new TypeToken<List<TopicEvalueBean>>() {
                        }.getType());
                        if (mContentRecylerView != null && mRefeshLy != null) {
                            mContentRecylerView.loadMoreComplete();
                            mContentRecylerView.refreshComplete();
                            mRefeshLy.hideAll();
                            if (PAGE_NUM == 0) {
                                commentAdapter.clear();
                                evalueData.clear();
                            }
                            commentAdapter.append(tempData);
                            evalueData = commentAdapter.data;
                            if (tempData == null || tempData.size() < 10) {
                                if (PAGE_NUM == 0 && (tempData == null || tempData.size() == 0)) {
                                    mRefeshLy.showEmptyView();
                                }
                                mContentRecylerView.setLoadingMoreEnabled(false);
                            } else {
                                mContentRecylerView.setLoadingMoreEnabled(true);
                            }
                        }
                        initEvalue();
                    } else {
                        mContentRecylerView.loadMoreComplete();
                        mContentRecylerView.refreshComplete();
                        mContentRecylerView.setLoadingMoreEnabled(false);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Log.e("话题详情评论fail", result);
                if (mContentRecylerView != null && mRefeshLy != null) {
                    mContentRecylerView.loadMoreComplete();
                    mContentRecylerView.refreshComplete();
                    mRefeshLy.hideAll();
                    mRefeshLy.showFailView();
                }
            }
        }, TopicDelBean.class);
    }

    // 刷新帖子赞
    public void initZan(boolean isCurrentStatus, String num) {
        if (isCurrentStatus) {
            tvZanMain.setText("已赞同 " + num);
            llayZanMain.setBackgroundResource(R.drawable.llay_yuanjiaobeijing_sl);
            tvZanMain.setTextColor(getResources().getColor(R.color.white));
            ivZanMain.setBackgroundResource(R.mipmap.topic_zan_true);
        } else {
            tvZanMain.setText("赞同 " + num);
            llayZanMain.setBackgroundResource(R.drawable.llay_yuanjiaobeijing_ql);
            tvZanMain.setTextColor(getResources().getColor(R.color.btn_blue));
            ivZanMain.setBackgroundResource(R.mipmap.topic_zan_true_a);
        }
    }

    // 刷新评论
    private void initEvalue() {
        // 默认设置两个
        if (null != evalueData && evalueData.size() > 0) {
            rllyEvalue.setVisibility(View.VISIBLE);
            tvName_1.setText(evalueData.get(0).nickName);
            tvTitle_1.setText(evalueData.get(0).content);
            mHeadImg_1.setImageURI(TextUtils.isEmpty(evalueData.get(0).headUrl) ? "" : evalueData.get(0).headUrl);
            long createTime = evalueData.get(0).getCreateTime();
            if (createTime == 0) {
                mTimeTv1.setVisibility(View.GONE);
            } else {
                mTimeTv1.setVisibility(View.VISIBLE);
                mTimeTv1.setText(TimeUtils.getDescriptionTimeFromTimestamp(createTime));
            }
        } else {
            rllyEvalue.setVisibility(View.GONE);
        }
        if (null != evalueData && evalueData.size() > 1) {
            rllyEvalue2.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            tvName_2.setText(evalueData.get(1).nickName);
            tvTitle_2.setText(evalueData.get(1).content);
            mHeadImg_2.setImageURI(TextUtils.isEmpty(evalueData.get(1).headUrl) ? "" : evalueData.get(1).headUrl);
            long createTime = evalueData.get(0).getCreateTime();
            if (createTime == 0) {
                mTimeTv2.setVisibility(View.GONE);
            } else {
                mTimeTv2.setVisibility(View.VISIBLE);
                mTimeTv2.setText(TimeUtils.getDescriptionTimeFromTimestamp(createTime));
            }
        } else {
            rllyEvalue2.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
        }
    }

    private void initCommentRecylerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mContentRecylerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mContentRecylerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mContentRecylerView.setHasFixedSize(true);
        mContentRecylerView.setLayoutManager(layoutManager);
        mRefeshLy.setRetryListener(this);
        commentAdapter = new CommentTopicAdapter(this, evalueData);
        mContentRecylerView.setAdapter(commentAdapter);
        mContentRecylerView.setNestedScrollingEnabled(false);
        mContentRecylerView.setLoadingListener(this);
        if (null != evalueData && evalueData.size() >= 10) {
            mContentRecylerView.setLoadingMoreEnabled(true);
        } else {
            mContentRecylerView.setLoadingMoreEnabled(false);
        }
        mContentRecylerView.setPullRefreshEnabled(true);
        commentAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                TopicEvalueBean evalueBean = (TopicEvalueBean) data.get(position);
                User user = MyApplication.getInstance().user;
                if (user != null && evalueBean.userId == user.getUserId()) {
                    ToastUtil.shortShowToast("不能回复自己...");
                    return;
                }
                commentType = 2;
                commentType2Name = evalueBean.nickName;
                commentType2Uid = evalueBean.userId + "";
                etMenuContent.setHint("回复 " + evalueBean.nickName + ":");
                etMenuContent.setText("");
                etMenuContent.requestFocus();
                emotionContainerView.setVisibility(View.GONE);
                KeyBoardUtils.openKeybord(etMenuContent, TopicDelActivity.this);
            }
        });
        commentAdapter.setOnItemLongClickListener(new MyBaseAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, List data, final int position) {
                new AlertDialog.Builder(TopicDelActivity.this).setTitle("删除该评论?")
                        .setMessage("您是否确定删除该评论？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, final int i) {
                                dialogInterface.dismiss();
                                User user = MyApplication.getInstance().user;
                                if (user != null) {
                                    NetUtils.getInstance().delTopicComment(evalueData.get(position).id, evalueData.get(position).userId, new NetCallBack() {
                                        @Override
                                        public void onSuccess(String result, String msg, ResultModel model) {
                                            ToastUtil.shortShowToast(msg);
                                            commentAdapter.removeSingle(evalueData.get(position));
                                            evalueData = commentAdapter.data;
                                            answerBean.topicComment.setCommentNum((Integer.valueOf(answerBean.topicComment.commentNum) - 1) + "");
                                            tv_allevalue.setText("查看全部" + answerBean.topicComment.commentNum + "条评论");
                                            if (null != tvMenuAll)
                                                tvMenuAll.setText("查看全部" + answerBean.topicComment.commentNum + "条评论");
                                            initEvalue();
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
        mContentRecylerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mContentRecylerView.canScrollVertically(-1)) {      //canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                    mContentRecylerView.requestDisallowInterceptTouchEvent(false);
                } else {
                    mContentRecylerView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
    }

    @Override
    public void onRetryClick() {
        PAGE_NUM = 0;
        getCommentData();
    }

    @Override
    public void onRefresh() {
        PAGE_NUM = 0;
        getCommentData();
    }

    @Override
    public void onLoadMore() {
        PAGE_NUM++;
        getCommentData();
    }

    // 话题详情评论mType=1点赞 2评论
    private void submitTopicEvalue(final int pos, String content, String toUserId, final int mType) {
        final String commentCotent = commentType == 1 ? content : ("回复" + commentType2Name + ":" + content);
        toUserId = commentType == 1 ? toUserId : commentType2Uid;
        NetUtils.getInstance().sbumitTopicEvalue(commentId, toUserId, commentCotent, mType, topicId, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("话题详情发布评论或点赞", result);
                try {
                    // 发布成功刷新页面
                    JSONObject job = new JSONObject(result);
                    int code = job.getInt("code");
                    if (code == 200) {
                        if (mType == 2) {
                            ToastUtils.getInstance().show(TopicDelActivity.this, "发布成功");
                            // 这里通知需要刷新的页面刷新
                            EventBuss event = new EventBuss(EventBuss.TOPIC_PL);
                            event.setMessage("");
                            EventBus.getDefault().post(event);
                        }
                        if (null != etMenuContent) {
                            etMenuContent.setText("");
                        }
                        if (mType == 1) {
                            // 点赞时使用pos
                            if (pos == -2) {
                                // 主贴刷新
                                try {
                                    int fabulousNum = answerBean.fabulousFlag == 1 ? Integer.valueOf(answerBean.topicComment.fabulousNum) - 1 : Integer.valueOf(answerBean.topicComment.fabulousNum) + 1;
                                    answerBean.topicComment.setFabulousNum(fabulousNum + "");
                                    answerBean.setFabulousFlag(answerBean.fabulousFlag == 1 ? 0 : 1);
                                    boolean isStatus = answerBean.fabulousFlag == 1 ? true : false;
                                    initZan(isStatus, answerBean.topicComment.fabulousNum);
                                    if (isStatus == true) {
                                        // 这里通知需要刷新的页面刷新
                                        EventBuss event = new EventBuss(EventBuss.TOPIC_ZAN);
                                        event.setMessage("");
                                        EventBus.getDefault().post(event);
                                    }
                                } catch (Exception e) {

                                }
                            } else {
                                // 列表点赞个人刷新
                            }
                        } else {
                            // 评论刷新列表
                            getCommentData();
                            // 刷新条数
                            answerBean.topicComment.setCommentNum((Integer.valueOf(answerBean.topicComment.commentNum) + 1) + "");
                            tv_allevalue.setText("查看全部" + answerBean.topicComment.commentNum + "条评论");
                            if (null != tvMenuAll) {
                                tvMenuAll.setText("查看全部" + answerBean.topicComment.commentNum + "条评论");
                            }
                        }
                    } else {
                        ToastUtils.getInstance().show(TopicDelActivity.this, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Log.e("话题详情发布评论或点赞fail", result);
            }
        }, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (null != emotionContainerView && emotionContainerView.getVisibility() == View.VISIBLE) {
                emotionContainerView.setVisibility(View.GONE);
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private EmotionKeyboard mEmotionKeyboard;
    private GlobalOnItemClickManagerUtils globalOnItemClickManager;
    EmotionComplateFragment emotionComplateFragment;

    // 初始化表情
    private void initEmotions() {
        // 初始化一次即可
        if (null == emotionComplateFragment) {
            emotionComplateFragment = new EmotionComplateFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(EmotionComplateFragment.EMOTION_MAP_TYPE, EmotionUtils.EMOTION_CLASSIC_TYPE);
            emotionComplateFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.emotionContainer, emotionComplateFragment);
            transaction.commit();
            //初始化EmotionKeyboard
            mEmotionKeyboard = EmotionKeyboard.with(this)
                    .setEmotionView(emotionContainerView)//绑定表情面板
                    .bindToContent(mContentRecylerView)//绑定内容view
                    .bindToEditText(etMenuContent)//判断绑定那种EditView
                    .bindToEmotionButton(mEmotionLogo)//绑定表情按钮
                    .build();
            // 点击表情的全局监听管理类
            globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance();
            //绑定EditText
            globalOnItemClickManager.attachToEditText(etMenuContent);
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }

}
