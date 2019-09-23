package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-7-4.
 */

public class ServiceDialogActivity extends BaseActivity {

    @InjectView(R.id.mEnsureTv)
    TextView mEnsureTv;
    @InjectView(R.id.mContentTv)
    TextView mContentTv;

    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        setFinishOnTouchOutside(false);//点击空白处，窗体不关闭
        return R.layout.activity_service_dialog;
    }

    @Override
    public void setViews() {

    }

    @Override
    public void initData() {
        mContentTv.setText(getFromAssets("userservice2.txt"));
    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName),"utf-8");
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                line+="\n";
                Result += line;
            }
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick(R.id.mEnsureTv)
    public void onViewClicked() {
        finish();
    }
}
