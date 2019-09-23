package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.SuccessRateAdapter;

import com.qianyu.communicate.adapter.SuccessRateAdapter_;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.AddData;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.TimeUtils;

import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class SuccessRateActivity extends BaseActivity {
    @InjectView(R.id.addTable)
    TextView addTable;
    @InjectView(R.id.goCharge)
    TextView goCharge;
    @InjectView(R.id.rankTable)
    TextView rankTable;
    @InjectView(R.id.toolUser)
    TextView toolUser;
    private CountDownTimer mTimer1, mTimer2;

    @Override
    public int getRootViewId() {
        return R.layout.activity_success_rate;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        setTitleTv("品阶成功率的提升");
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.MINE_TAB) {
            loadDatas();
        }
    }

    @Override
    public void initData() {
        loadDatas();
    }

    @OnClick({R.id.addTable, R.id.goCharge, R.id.rankTable, R.id.toolUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addTable:
                showTablePopWindow();
                break;
            case R.id.goCharge:
                startActivity(new Intent(SuccessRateActivity.this, HotMallActivity.class));
                break;
            case R.id.rankTable:
                showTablePopWindow_();
                break;
            case R.id.toolUser:
                startActivity(new Intent(SuccessRateActivity.this, HotMallActivity.class));
                break;
        }
    }

    private void loadDatas() {
        NetUtils.getInstance().addData(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                AddData addData = (AddData) model.getModel();
                if (addData != null) {
                    if (addData.getOrderTime() > 0) {
                        startTimmer(addData);
                    } else {
                        goCharge.setText("去充值");
                    }
                    if (addData.getPopTime() > 0) {
                        startTimmer_(addData);
                    } else {
                        toolUser.setText("去使用");
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, AddData.class);
    }

    //倒计时
    private void startTimmer(final AddData addData) {
        mTimer1 = new CountDownTimer(addData.getOrderTime(), 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                goCharge.setText("+" + addData.getOrderBuf() / 10000 * 100 + "%  " + TimeUtils.getHMSTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                goCharge.setText("去充值");
            }
        };
        mTimer1.start();
    }

    private void startTimmer_(final AddData addData) {
        mTimer2 = new CountDownTimer(addData.getPopTime(), 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                toolUser.setText("+" + addData.getPopBuf() / 10000 * 100 + "%  " + TimeUtils.getHMSTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                toolUser.setText("去使用");
            }
        };
        mTimer2.start();
    }

    private void showTablePopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.add_table_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mRecylerView);
                        initRecylerView(recyclerView);
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(addTable, Gravity.BOTTOM, 0, 0);
    }

    private void showTablePopWindow_() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.add_table_pop_)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mRecylerView);
                        initRecylerView_(recyclerView);
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(addTable, Gravity.BOTTOM, 0, 0);
    }

    private void initRecylerView(RecyclerView recyclerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SuccessRateAdapter(this, null));
    }

    private void initRecylerView_(RecyclerView recyclerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SuccessRateAdapter_(this, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mTimer1 != null) {
            mTimer1.cancel();
            mTimer1.onFinish();
        }
        if (mTimer2 != null) {
            mTimer2.cancel();
            mTimer2.onFinish();
        }
    }
}
