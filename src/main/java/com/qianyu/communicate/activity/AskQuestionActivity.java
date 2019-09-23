package com.qianyu.communicate.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.User;

import org.haitao.common.utils.ToastUtil;

import butterknife.InjectView;

/**
 * Created by JavaDog on 2018-5-29.
 */

public class AskQuestionActivity extends BaseActivity {
    @InjectView(R.id.mQuestTitle)
    EditText mQuestTitle;
    @InjectView(R.id.mQuestContent)
    EditText mQuestContent;
    @InjectView(R.id.mQuestNum)
    TextView mQuestNum;
    private int doctorId;

    @Override
    public int getRootViewId() {
        return R.layout.activity_ask_question;
    }

    @Override
    public void setViews() {
        if (getIntent() != null) {
            doctorId = getIntent().getIntExtra("doctorId", 0);
        }
        setTitleTv("我要提问");
        setNextTv("提交问题");
        getNextTv().setTextColor(getResources().getColor(R.color.work_yellow));
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mQuestTitle.getText().toString().trim();
                String content = mQuestContent.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    ToastUtil.shortShowToast("请先输入您的问题...");
                } else if (TextUtils.isEmpty(content)) {
                    ToastUtil.shortShowToast("请先输入您的问题详细描述...");
                }else {
                    User user = MyApplication.getInstance().user;
                }
            }
        });
    }


    @Override
    public void initData() {
        mQuestContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = mQuestContent.getText().toString().trim();
                mQuestNum.setText(content.length() + "/200");
            }
        });
    }

}
