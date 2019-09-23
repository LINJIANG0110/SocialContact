package com.qianyu.communicate.activity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.VipAdapter;

import com.qianyu.communicate.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class VipActivity_ extends BaseActivity {
    @InjectView(R.id.backFL)
    FrameLayout backFL;
    @InjectView(R.id.silverPrice)
    TextView silverPrice;
    @InjectView(R.id.goldPrice)
    TextView goldPrice;
    @InjectView(R.id.superPrice)
    TextView superPrice;
    @InjectView(R.id.silverTv)
    TextView silverTv;
    @InjectView(R.id.silverFL)
    FrameLayout silverFL;
    @InjectView(R.id.goldTv)
    TextView goldTv;
    @InjectView(R.id.goldFL)
    FrameLayout goldFL;
    @InjectView(R.id.superTv)
    TextView superTv;
    @InjectView(R.id.superFL)
    FrameLayout superFL;
    @InjectView(R.id.mRecylerView)
    RecyclerView mRecylerView;
    @InjectView(R.id.openMemberShip)
    CardView openMemberShip;

    @Override
    public int getRootViewId() {
        return R.layout.activity_vip_;
    }

    @Override
    public void setViews() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.setAdapter(new VipAdapter(this,null));
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.backFL, R.id.silverFL, R.id.goldFL, R.id.superFL, R.id.openMemberShip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backFL:
                finish();
                break;
            case R.id.silverFL:
                changeBgAndColor(silverTv);
                break;
            case R.id.goldFL:
                changeBgAndColor(goldTv);
                break;
            case R.id.superFL:
                changeBgAndColor(superTv);
                break;
            case R.id.openMemberShip:
                break;
        }
    }

    private void changeBgAndColor(TextView chosseTv) {
        silverTv.setTextColor(getResources().getColor(R.color.text_gray));
        silverTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_line));
        goldTv.setTextColor(getResources().getColor(R.color.text_gray));
        goldTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_line));
        superTv.setTextColor(getResources().getColor(R.color.text_gray));
        superTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_line));
        chosseTv.setTextColor(getResources().getColor(R.color.white));
        chosseTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_app_corlor));
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.inject(this);
//    }
}
