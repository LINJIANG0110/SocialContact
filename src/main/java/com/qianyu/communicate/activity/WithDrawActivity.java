package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.utils.NumberUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class WithDrawActivity extends BaseActivity {
    @InjectView(R.id.canWithDrawMoney)
    TextView canWithDrawMoney;
    @InjectView(R.id.aliPayLogo)
    ImageView aliPayLogo;
    @InjectView(R.id.aliPayRv)
    RelativeLayout aliPayRv;
    @InjectView(R.id.wxPayLogo)
    ImageView wxPayLogo;
    @InjectView(R.id.wxPayRv)
    RelativeLayout wxPayRv;
    @InjectView(R.id.withDraw)
    TextView withDraw;
    private long remains;
    private boolean aliPay=true;

    @Override
    public int getRootViewId() {
        return R.layout.activity_with_draw;
    }

    @Override
    public void setViews() {
        setTitleTv("账户提现");
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            remains = getIntent().getLongExtra("remains", 0);
            canWithDrawMoney.setText(NumberUtils.rounLong(remains));
        }
    }

    @OnClick({R.id.aliPayRv, R.id.wxPayRv, R.id.withDraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aliPayRv:
                aliPay=true;
                aliPayLogo.setImageResource(R.mipmap.wxdl_xuanze);
                wxPayLogo.setImageResource(R.mipmap.wxdl_xuanzehui);
                break;
            case R.id.wxPayRv:
                aliPay=false;
                aliPayLogo.setImageResource(R.mipmap.wxdl_xuanzehui);
                wxPayLogo.setImageResource(R.mipmap.wxdl_xuanze);
                break;
            case R.id.withDraw:
                Intent intent = new Intent(WithDrawActivity.this, AccountBandActivity.class);
                intent.putExtra("aliPay",aliPay);
                intent.putExtra("remains",remains);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
