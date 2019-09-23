package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class PractiseRateActivity extends BaseActivity {
    @InjectView(R.id.mDoubleLevel)
    TextView mDoubleLevel;
    @InjectView(R.id.mGoBuyCardView)
    CardView mGoBuyCardView;

    @Override
    public int getRootViewId() {
        return R.layout.activity_practise_rate;
    }

    @Override
    public void setViews() {
        setTitleTv("品阶熟练度的提升");
        Spanned spanned = Html.fromHtml("购买使用道具“<font color='#59e5df'>双倍升级</font> ” ");
        mDoubleLevel.setText(spanned);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.mGoBuyCardView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mGoBuyCardView:
                startActivity(new Intent(PractiseRateActivity.this,HotMallActivity.class));
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
