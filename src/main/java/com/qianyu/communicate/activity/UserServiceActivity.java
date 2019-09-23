package com.qianyu.communicate.activity;

import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.InjectView;

/**
 * Created by JavaDog on 2018-4-13.
 */

public class UserServiceActivity extends BaseActivity {
    @InjectView(R.id.mContentTv)
    TextView mContentTv;

    @Override
    public int getRootViewId() {
        return R.layout.activity_user_service;
    }

    @Override
    public void setViews() {
        setTitleTv("服务条款及隐私政策");
    }

    @Override
    public void initData() {
        mContentTv.setText(getFromAssets("userservice.txt"));
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
}
