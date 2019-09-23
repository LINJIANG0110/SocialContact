package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.EventRecordAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.emotions.fragments.EmotionComplateFragment;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.GlobalOnItemClickManagerUtils;
import com.qianyu.communicate.emotions.widget.EmotionKeyboard;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JavaDog on 2019-5-28.
 */

public class MyEventRecordActivity extends BaseActivity {

    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @InjectView(R.id.et_sendmessage)
    EditText etSendmessage;
    @InjectView(R.id.countryTv)
    TextView countryTv;
    @InjectView(R.id.iv_face_normal)
    ImageView ivFaceNormal;
    @InjectView(R.id.iv_face_checked)
    ImageView ivFaceChecked;
    @InjectView(R.id.rl_face)
    RelativeLayout rlFace;
    @InjectView(R.id.btn_send)
    TextView btnSend;
    @InjectView(R.id.emotionContainer1)
    FrameLayout emotionContainer;
    private EmotionKeyboard mEmotionKeyboard;
    private GlobalOnItemClickManagerUtils globalOnItemClickManager;
    private EventRecordAdapter adapter;

    @Override
    public int getRootViewId() {
        return R.layout.activity_my_event;
    }

    @Override
    public void setViews() {
        setTitleTv("我的事件记录");
        initEmotions();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
//        mRecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(layoutManager);
        adapter = new EventRecordAdapter(this, null);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void initData() {
        loadDatas();
        etSendmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int lengthBefore, int lengthAfter) {

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
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = etSendmessage.getText().toString().trim();
                final User user = MyApplication.getInstance().user;
                NetUtils.getInstance().worldSpeak(user.getUserId(), content, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        ToastUtil.shortShowToast(msg);
                        EventRecord.ContentBean eventRecord = new EventRecord.ContentBean();
                        eventRecord.setSendNickName(user.getNickName());
                        eventRecord.setHeadUrl(user.getHeadUrl());
                        eventRecord.setEventMsg(content);
                        adapter.appendSingle(eventRecord);
                        KeyBoardUtils.hideSoftInput(etSendmessage);
                        emotionContainer.setVisibility(View.GONE);
                        btnSend.setVisibility(View.GONE);
                        etSendmessage.setText("");
                        mRecyclerview.smoothScrollToPosition(adapter.getItemCount() - 1);
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                },null);
            }
        });
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
            NetUtils.getInstance().myEvent(user.getUserId(), -1, -1, new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    EventRecord circleList = (EventRecord) model.getModel();
                    if (circleList != null) {
                        List<EventRecord.ContentBean> list = circleList.getContent();
                        Collections.reverse(list);
                        adapter.appendAll(list);
                        if (list != null&&list.size()>0) {
                            mRecyclerview.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }
                    }
                    EventBus.getDefault().post(new EventBuss(EventBuss.MY_EVENT_READ));
                }

                @Override
                public void onFail(String code, String msg, String result) {
                }
            }, EventRecord.class);
        }
    }


    private void initEmotions() {
        EmotionComplateFragment emotionComplateFragment = new EmotionComplateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EmotionComplateFragment.EMOTION_MAP_TYPE, EmotionUtils.EMOTION_CLASSIC_TYPE);
        emotionComplateFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.emotionContainer1, emotionComplateFragment);
        transaction.commit();
        //初始化EmotionKeyboard
        mEmotionKeyboard = EmotionKeyboard.with(this)
                .setEmotionView(findViewById(R.id.emotionContainer1))//绑定表情面板
                .bindToContent(mRecyclerview)//绑定内容view
                .bindToEditText(((EditText) findViewById(R.id.et_sendmessage)))//判断绑定那种EditView
                .bindToEmotionButton(findViewById(R.id.rl_face))//绑定表情按钮
                .build();
//        点击表情的全局监听管理类
        globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance();
        //绑定EditText
        globalOnItemClickManager.attachToEditText(etSendmessage);
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
    protected void onDestroy() {
        globalOnItemClickManager.unAttachToEditText();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
