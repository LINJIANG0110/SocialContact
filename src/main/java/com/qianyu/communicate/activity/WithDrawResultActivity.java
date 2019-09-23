package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class WithDrawResultActivity extends BaseActivity {
    @InjectView(R.id.withDraw)
    TextView withDraw;
    @InjectView(R.id.mTimeTv)
    TextView mTimeTv;

    @Override
    public int getRootViewId() {
        return R.layout.activity_with_draw_result;
    }

    @Override
    public void setViews() {
        setTitleTv("提现详情");
    }

    @Override
    public void initData() {
        withDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
