package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.QADetailAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.emotions.fragments.EmotionComplateFragment;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.GlobalOnItemClickManagerUtils;
import com.qianyu.communicate.emotions.widget.EmotionKeyboard;
import com.qianyu.communicate.entity.QAList;
import com.qianyu.communicate.entity.User;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.neiquan.applibrary.wight.RefreshLayout;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class QADetailActivity extends BaseActivity implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {
    @InjectView(R.id.refesh_ly)
    RefreshLayout mRefeshLy;
    @InjectView(R.id.recyclerview)
    XRecyclerView mRecyclerview;
    @InjectView(R.id.mCommentET)
    EditText mCommentET;
    @InjectView(R.id.mEmotionLogo)
    ImageView mEmotionLogo;
    @InjectView(R.id.btn_send)
    TextView btn_send;
    @InjectView(R.id.friendDetailLL)
    LinearLayout friendDetailLL;
    @InjectView(R.id.emotionContainer)
    FrameLayout emotionContainer;
    private EmotionKeyboard mEmotionKeyboard;
    private GlobalOnItemClickManagerUtils globalOnItemClickManager;
    private SimpleDraweeView mHeadImg;
    private QADetailAdapter adapter;
    protected int pageNum = 0;
    protected int PAEG_SIZE = 10;
    private TextView mTitleTv;
    private TextView mUserName;
    private TextView mTimeTv;
    private TextView mContentTv;
    private TextView mReplyTv;
    private int iId;
    private QAList qaList;
    private QAList.ReplyListBean replyListBean_;//一级回复的model
    private int type_ = 1;//1：一级回复，2：二级回复，3：三级回复
    private QAList.ReplyListBean replyListBean2;//二级回复的model

    @Override
    public int getRootViewId() {
        return R.layout.activity_qa_detail;
    }

    @Override
    public void back(View view) {
        super.back(view);
        KeyBoardUtils.hideSoftInput(mCommentET);
    }

    @Override
    public void setViews() {
        setTitleTv("问答详情");
        initEmotions();
        View mHeadView = LayoutInflater.from(this).inflate(R.layout.activity_qa_detail_head, null);
        mTitleTv = ((TextView) mHeadView.findViewById(R.id.mTitleTv));
        mHeadImg = ((SimpleDraweeView) mHeadView.findViewById(R.id.mHeadImg));
        mUserName = ((TextView) mHeadView.findViewById(R.id.mUserName));
        mTimeTv = ((TextView) mHeadView.findViewById(R.id.mTimeTv));
        mContentTv = ((TextView) mHeadView.findViewById(R.id.mContentTv));
        mReplyTv = ((TextView) mHeadView.findViewById(R.id.mReplyTv));
        mRecyclerview.addHeaderView(mHeadView);
        setAdapter();
        mHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_ = 1;
                iId = getIntent().getIntExtra("iId", 0);
                emotionContainer.setVisibility(View.GONE);
                KeyBoardUtils.hideSoftInput(mCommentET);
                mCommentET.setHint("我要回答...");
                mCommentET.setText("");
            }
        });
    }

    @Override
    public void initData() {
        adapter.setOnReplyListener(new QADetailAdapter.OnReplyListener() {
            @Override
            public void onReply(int type, QAList.ReplyListBean replyListBean, int position) {
                type_ = type;
                replyListBean_ = replyListBean;
                if (type == 2) {//2级回复
                    iId = replyListBean.getiId();
                    int doctorId = replyListBean.getDoctorId();
                    mCommentET.setHint("回复" + (doctorId == 0 ? replyListBean.getUserNickName() : replyListBean.getDoctoeNickName()) + "...");
                } else {//3级回复
                    replyListBean2 = replyListBean.getReplyList().get(position);
                    iId = replyListBean.getiId();
                    int doctorId = replyListBean2.getDoctorId();
                    mCommentET.setHint("回复" + (doctorId == 0 ? replyListBean2.getUserNickName() : replyListBean2.getDoctoeNickName()) + "...");
                }
                mCommentET.setText("");
                mCommentET.requestFocus();
                emotionContainer.setVisibility(View.GONE);
                KeyBoardUtils.openKeybord(mCommentET, QADetailActivity.this);
            }
        });
        if (getIntent() != null) {
            iId = getIntent().getIntExtra("iId", 0);
        }
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                type_ = 1;
                iId = getIntent().getIntExtra("iId", 0);
                emotionContainer.setVisibility(View.GONE);
                KeyBoardUtils.hideSoftInput(mCommentET);
                mCommentET.setHint("我要回答...");
                mCommentET.setText("");
            }
        });
    }

    private void loadDatas() {
    }


    @OnClick({R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                final User user = MyApplication.getInstance().user;
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(QADetailActivity.this, RegistActivity.class));
                    return;
                }
                final String content = mCommentET.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.shortShowToast("请先输入文字...");
                    return;
                }
                if (type_ == 1) { //1级回复
                    if (qaList.getUserId() == user.getUserId()) {
                        ToastUtil.shortShowToast("不能回答自己...");
                        return;
                    }
                } else if (type_ == 2) { //2级回复
                    if ((replyListBean_.getDoctorId() == 0 ? replyListBean_.getUserId() : replyListBean_.getDoctorId()) == user.getUserId()) {
                        ToastUtil.shortShowToast("不能回复自己...");
                        return;
                    }
                } else {//3级回复
                    int type = replyListBean2.getType(); //2：对2级回复的普通回复，3：对回复的他人的回复再回复。。
                    int doctorId = replyListBean2.getDoctorId();
                    if ((type == 2 ? (doctorId==0?replyListBean2.getUserId():replyListBean2.getDoctorId()) : replyListBean2.getDoctorId()) == user.getUserId()) {
                        ToastUtil.shortShowToast("不能回复自己...");
                        return;
                    }
                }
                break;
        }
    }

    @Override
    public void onRetryClick() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        loadDatas();
    }

    private void setAdapter() {
        adapter = new QADetailAdapter(this, null);
        mRecyclerview.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(layoutManager);
        mRefeshLy.setRetryListener(this);

        mRecyclerview.setLoadingListener(this);
        mRecyclerview.setLoadingMoreEnabled(false);
        mRecyclerview.setPullRefreshEnabled(true);
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
                .bindToContent(mRefeshLy)//绑定内容view
                .bindToEditText(((EditText) findViewById(R.id.mCommentET)))//判断绑定那种EditView
                .bindToEmotionButton(findViewById(R.id.mEmotionLogo))//绑定表情按钮
                .build();
//        点击表情的全局监听管理类
        globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance();
        //绑定EditText
        globalOnItemClickManager.attachToEditText(mCommentET);
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
