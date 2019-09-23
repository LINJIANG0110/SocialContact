package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseViewPagerActivity_Smart_Normal_;
import com.qianyu.communicate.emotions.fragments.EmotionComplateFragment;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.GlobalOnItemClickManagerUtils;
import com.qianyu.communicate.emotions.widget.EmotionKeyboard;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.fragment.EventRecordFragment;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.applibrary.utils.SmartItem;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JavaDog on 2019-3-22.
 */

public class EventRecordActivity extends BaseViewPagerActivity_Smart_Normal_ {
    private EmotionKeyboard mEmotionKeyboard;
    private GlobalOnItemClickManagerUtils globalOnItemClickManager;
    private long groupId;
    private int position=0;

    @Override
    public boolean isHaveHead() {
        return true;
    }

    @Override
    public List<SmartItem> getSmartItems() {
        if (getIntent() != null) {
            groupId = getIntent().getLongExtra("groupId", 0);
            position = getIntent().getIntExtra("position", 0);
        }
        List<SmartItem> smartItems = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("event", 1);
        bundle.putLong("groupId", groupId);
        smartItems.add(new SmartItem("群", EventRecordFragment.class, bundle));
        Bundle bundle1 = new Bundle();
        bundle1.putInt("event", 2);
        bundle1.putLong("groupId", groupId);
        smartItems.add(new SmartItem("全国", EventRecordFragment.class, bundle1));
        return smartItems;
    }

    @Override
    public void initData() {
        setTitleTv("事件记录");
        initEmotions();
        mViewPager.setCurrentItem(position);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Spanned group = Html.fromHtml("<u>全国</u>");
                Spanned country = Html.fromHtml("<u>全国</u>");
                countryTv.setText(position == 0 ? group : country);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
                        EventBuss event = new EventBuss(EventBuss.EVENT_RECORD);
                        event.setMessage(eventRecord);
                        EventBus.getDefault().post(event);
                        KeyBoardUtils.hideSoftInput(etSendmessage);
                        emotionContainer.setVisibility(View.GONE);
                        btnSend.setVisibility(View.GONE);
                        etSendmessage.setText("");
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                },null);
            }
        });
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
                .bindToContent(mViewPager)//绑定内容view
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
}
