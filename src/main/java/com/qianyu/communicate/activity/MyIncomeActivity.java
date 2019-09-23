package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.views.MarqueeView;

import com.qianyu.communicate.base.BaseActivity;

import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class MyIncomeActivity extends BaseActivity {

    @InjectView(R.id.exChangeSleeve)
    TextView exChangeSleeve;
    @InjectView(R.id.goWithDraw)
    TextView goWithDraw;
    @InjectView(R.id.exChangeMoney)
    RelativeLayout exChangeMoney;
    @InjectView(R.id.withDrawMoney)
    RelativeLayout withDrawMoney;
    @InjectView(R.id.incomeDetail)
    RelativeLayout incomeDetail;
    @InjectView(R.id.mv_bar)
    MarqueeView mvBar;
    @InjectView(R.id.currentMonth)
    TextView currentMonth;
    @InjectView(R.id.lastMonth)
    TextView lastMonth;
    @InjectView(R.id.totalMoney)
    TextView totalMoney;
    @InjectView(R.id.canWithDraw)
    TextView canWithDraw;
    private long remains;

    @Override
    public int getRootViewId() {
        return R.layout.activity_my_income;
    }

    @Override
    public void setViews() {
        setTitleTv("我的收益");
    }

    @Override
    public void initData() {
        //设置集合
        List<String> items = new ArrayList<>();
        items.add("@梦逍遥   送给   美人鱼的嫁衣   三个丘比特之箭");
        items.add("@孙思邈   送给   华佗   三个大宝剑");
        items.add("@老中医   送给   女神经 四头皮皮虾、三条皮皮膳、二个皮皮蟹和一根超级巨无霸的大黄瓜，哈哈哈哈哈哈哈哈哈！");
//        mvBar.startWithText("@老中医   送给   女神经 四头皮皮虾、三条皮皮膳、二个皮皮蟹和一根超级巨无霸的大黄瓜，哈哈哈哈哈哈哈哈哈！");
        mvBar.startWithList(items);
        loadDatas();
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            return;
        }
//        Tools.showDialog(this);
    }

    @OnClick({R.id.exChangeSleeve, R.id.goWithDraw, R.id.exChangeMoney, R.id.withDrawMoney, R.id.incomeDetail})
    public void onViewClicked(View view) {
        Intent intent = new Intent(MyIncomeActivity.this, WithDrawActivity.class);
        intent.putExtra("remains",remains);
        switch (view.getId()) {
            case R.id.exChangeSleeve:
                startActivity(new Intent(MyIncomeActivity.this, GetSleeveActivity.class));
                break;
            case R.id.goWithDraw:
                if(remains<=0){
                    ToastUtil.shortShowToast("暂无可提现余额...");
                    return;
                }
                startActivity(intent);
                finish();
                break;
            case R.id.exChangeMoney:
                startActivity(new Intent(MyIncomeActivity.this, ExChangeSleeveActivity.class));
                break;
            case R.id.withDrawMoney:
                if(remains<=0){
                    ToastUtil.shortShowToast("暂无可提现余额...");
                    return;
                }
                startActivity(intent);
                finish();
                break;
            case R.id.incomeDetail:
                startActivity(new Intent(MyIncomeActivity.this, BillActivity.class));
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
