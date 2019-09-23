package com.qianyu.communicate.test;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.emotions.fragments.EmotionComplateFragment;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.GlobalOnItemClickManagerUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.emotions.widget.EmotionKeyboard;

import org.haitao.common.utils.AppLog;

/**
 * Created by SiberiaDante
 * Describe: 表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 * Time: 2017/6/26
 * Email: 994537867@qq.com
 * GitHub: https://github.com/SiberiaDante
 * 博客园： http://www.cnblogs.com/shen-hua/
 */

public class TestEmotionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvContent;
    private EditText mEdtContent;
    private Button mBtnSend;
    private EmotionKeyboard mEmotionKeyboard;
    private GlobalOnItemClickManagerUtils globalOnItemClickManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_emotion_test);
        initView();
        initData();
        //初始化EmotionKeyboard
        mEmotionKeyboard = EmotionKeyboard.with(this)
                .setEmotionView(findViewById(R.id.emotionContainer))//绑定表情面板
                .bindToContent(mTvContent)//绑定内容view
                .bindToEditText(((EditText) findViewById(R.id.bar_edit_text)))//判断绑定那种EditView
                .bindToEmotionButton(findViewById(R.id.emotion_button))//绑定表情按钮
                .build();
//        点击表情的全局监听管理类
        globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance();
        //绑定EditText
        globalOnItemClickManager.attachToEditText(mEdtContent);
    }

    private void initView() {
        mTvContent = (TextView) findViewById(R.id.tv_input_content);
        mEdtContent = ((EditText) findViewById(R.id.bar_edit_text));
        mBtnSend = ((Button) findViewById(R.id.bar_btn_send));
        mBtnSend.setOnClickListener(this);
    }

    private void initData() {
        EmotionComplateFragment emotionComplateFragment = new EmotionComplateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EmotionComplateFragment.EMOTION_MAP_TYPE, EmotionUtils.EMOTION_CLASSIC_TYPE);
        emotionComplateFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.emotionContainer, emotionComplateFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_btn_send:
                mTvContent.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, this, mTvContent, mEdtContent.getText().toString()));
                String s = mEdtContent.getText().toString();
                Log.e("info","==========111========="+s);
                mEdtContent.setText("");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        /**
         * 按下返回键，如果表情显示，则隐藏，没有显示则回退页面
         */
        AppLog.e("=========onBackPressed=========="+mEmotionKeyboard.interceptBackPress());
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
