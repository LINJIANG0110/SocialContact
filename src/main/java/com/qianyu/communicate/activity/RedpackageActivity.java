package com.qianyu.communicate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.HbResultBean;

import java.math.BigDecimal;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tv.buka.other.Gson;

public class RedpackageActivity extends BaseActivity {

    @InjectView(R.id.iv_close)
    ImageView ivClose;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_detaile)
    TextView tvDetaile;
    @InjectView(R.id.iv_fengmian)
    SimpleDraweeView ivHeadurl;

    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        setFinishOnTouchOutside(false);//点击空白处，窗体不关闭
        return R.layout.activity_redpackage;
    }

    @Override
    public void setViews() {
        String mJson = getIntent().getStringExtra("resJson");
        final HbResultBean hbBean = new Gson().fromJson(mJson, HbResultBean.class);
        String money = hbBean.diamond;
        if (money.length() >= 7) {
            tvMoney.setTextSize(50);
        }
        tvMoney.setText(money);
        tvUsername.setText(hbBean.getNickName() + "的红包");
        ivHeadurl.setImageURI(TextUtils.isEmpty(hbBean.headUrl) ? "" : hbBean.headUrl);
        tvDetaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RedpackageActivity.this, RedpackageResActivity.class).
                        putExtra("groupId", hbBean.groupId).putExtra("redPackageId", hbBean.getHongbaoId()));
                finish();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String toNumber(float number) {
        String str = "";
        if (number <= 0) {
            str = "";
        } else if (number < 10000) {
            str = number + "";
        } else {
            double d = (double) number;
            //将数字转换成万
            double num = d / 10000;
            BigDecimal b = new BigDecimal(num);
            //四舍五入保留小数点后一位
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            str = f1 + "";
        }
        return str;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
