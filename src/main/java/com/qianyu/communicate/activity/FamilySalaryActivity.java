package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FamilySalaryAdapter;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.WageInfo;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;

import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-7-8.
 */

public class FamilySalaryActivity extends BaseActivity {
    @InjectView(R.id.myFubaoTv)
    TextView myFubaoTv;
    @InjectView(R.id.myCoinTv)
    TextView myCoinTv;
    @InjectView(R.id.manageFubao)
    TextView manageFubao;
    @InjectView(R.id.manageCoin)
    TextView manageCoin;
    @InjectView(R.id.jifenFubao)
    TextView jifenFubao;
    @InjectView(R.id.jifenCoin)
    TextView jifenCoin;
    @InjectView(R.id.mOwnerSalaryTv)
    TextView mOwnerSalaryTv;
    @InjectView(R.id.mManagerSalaryTv)
    TextView mManagerSalaryTv;
    @InjectView(R.id.mOwnerRequest)
    TextView mOwnerRequest;
    @InjectView(R.id.mMangerRequest)
    TextView mMangerRequest;
    @InjectView(R.id.jifen1Logo)
    ImageView jifen1Logo;
    @InjectView(R.id.jifen2Logo)
    ImageView jifen2Logo;
    @InjectView(R.id.jifen3Logo)
    ImageView jifen3Logo;
    @InjectView(R.id.jifen1State)
    TextView jifen1State;
    @InjectView(R.id.jifen2State)
    TextView jifen2State;
    @InjectView(R.id.jifen3State)
    TextView jifen3State;
    @InjectView(R.id.jifen1Num)
    TextView jifen1Num;
    @InjectView(R.id.jifen2Num)
    TextView jifen2Num;
    @InjectView(R.id.jifen3Num)
    TextView jifen3Num;
    @InjectView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.mRecylerView)
    RecyclerView mRecylerView;
    @InjectView(R.id.mSubmitCardView)
    CardView mSubmitCardView;
    private long groupId;
    private FamilySalaryAdapter adapter;
    private WageInfo.GroupScoreStageBean stageBean1;
    private WageInfo.GroupScoreStageBean stageBean2;
    private WageInfo.GroupScoreStageBean stageBean3;

    @Override
    public int getRootViewId() {
        return R.layout.activity_family_salary;
    }

    @Override
    public void setViews() {
        setTitleTv("家族工资");
        setNextTv("收入规则");
        getNextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamilySalaryActivity.this, IncomeRuleActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            groupId = getIntent().getLongExtra("groupId", 0);
        }
        loadDatas();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        adapter = new FamilySalaryAdapter(this, null);
        mRecylerView.setAdapter(adapter);
    }

    private void loadDatas() {
        NetUtils.getInstance().wageInfo(groupId, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                WageInfo wageInfo = (WageInfo) model.getModel();
                if (wageInfo != null) {
                    WageInfo.WagesBean wages = wageInfo.getWages();
                    List<WageInfo.WagelistBean> wagelist = wageInfo.getWagelist();
                    List<WageInfo.GroupScoreStageBean> groupScoreStage = wageInfo.getGroupScoreStage();
                    adapter.appendAll(wageInfo.getUserScoreInfo());
                    if (wages != null) {
                        myCoinTv.setText("x" + NumberUtils.rounLong(wages.getGold()));
                        myFubaoTv.setText("x" + NumberUtils.rounLong(wages.getFubao()));
                        manageCoin.setText("x" + NumberUtils.rounLong(wages.getBasicgold()));
                        manageFubao.setText("x" + NumberUtils.rounLong(wages.getBasicfubao()));
                        jifenCoin.setText("x" + NumberUtils.rounLong(wages.getAddgold()));
                        jifenFubao.setText("x" + NumberUtils.rounLong(wages.getAddfubao()));
                    }
                    if (wagelist != null && wagelist.size() > 0) {
                        for (int i = 0; i < wagelist.size(); i++) {
                            WageInfo.WagelistBean wagelistBean = wagelist.get(i);
                            if (wagelistBean.getUserType() == 1) {
                                mOwnerSalaryTv.setText(wagelistBean.getRewardName());
                            }
                            if (wagelistBean.getUserType() == 2) {
                                mManagerSalaryTv.setText(wagelistBean.getRewardName());
                            }
                        }
                    }
                    long groupScore = wageInfo.getGroupScore();
                    mProgressBar.setProgress((int) groupScore);
                    if (groupScoreStage != null && groupScoreStage.size() == 3) {
                        stageBean1 = groupScoreStage.get(0);
                        stageBean2 = groupScoreStage.get(1);
                        stageBean3 = groupScoreStage.get(2);
                        jifen1Num.setText(stageBean1.getConfigValue());
                        jifen2Num.setText(stageBean2.getConfigValue());
                        jifen3Num.setText(stageBean3.getConfigValue());
                        if (groupScore < Long.parseLong(stageBean1.getConfigValue())) {
                            jifen1Logo.setImageResource(R.mipmap.box2);
                            jifen2Logo.setImageResource(R.mipmap.box2);
                            jifen3Logo.setImageResource(R.mipmap.box2);
                            jifen1State.setText("查看奖励");
                            jifen2State.setText("查看奖励");
                            jifen3State.setText("查看奖励");
                        } else if (groupScore == Long.parseLong(stageBean1.getConfigValue())) {
                            jifen1Logo.setImageResource(R.mipmap.box1);
                            jifen2Logo.setImageResource(R.mipmap.box2);
                            jifen3Logo.setImageResource(R.mipmap.box2);
                            jifen1State.setText("查看奖励");
                            jifen2State.setText("查看奖励");
                            jifen3State.setText("查看奖励");
                        } else if (Long.parseLong(stageBean1.getConfigValue()) < groupScore && groupScore < Long.parseLong(stageBean2.getConfigValue())) {
                            jifen1Logo.setImageResource(R.mipmap.box3);
                            jifen2Logo.setImageResource(R.mipmap.box2);
                            jifen3Logo.setImageResource(R.mipmap.box2);
                            jifen1State.setText("已领取");
                            jifen2State.setText("查看奖励");
                            jifen3State.setText("查看奖励");
                        } else if (groupScore == Long.parseLong(stageBean2.getConfigValue())) {
                            jifen1Logo.setImageResource(R.mipmap.box3);
                            jifen2Logo.setImageResource(R.mipmap.box1);
                            jifen3Logo.setImageResource(R.mipmap.box2);
                            jifen1State.setText("已领取");
                            jifen2State.setText("查看奖励");
                            jifen3State.setText("查看奖励");
                        } else if (Long.parseLong(stageBean2.getConfigValue()) < groupScore && groupScore < Long.parseLong(stageBean3.getConfigValue())) {
                            jifen1Logo.setImageResource(R.mipmap.box3);
                            jifen2Logo.setImageResource(R.mipmap.box3);
                            jifen3Logo.setImageResource(R.mipmap.box2);
                            jifen1State.setText("已领取");
                            jifen2State.setText("已领取");
                            jifen3State.setText("查看奖励");
                        } else if (groupScore == Long.parseLong(stageBean3.getConfigValue())) {
                            jifen1Logo.setImageResource(R.mipmap.box3);
                            jifen2Logo.setImageResource(R.mipmap.box3);
                            jifen3Logo.setImageResource(R.mipmap.box1);
                            jifen1State.setText("已领取");
                            jifen2State.setText("已领取");
                            jifen3State.setText("查看奖励");
                        }
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, WageInfo.class);
    }

    @OnClick({R.id.mOwnerRequest, R.id.mMangerRequest, R.id.jifen1Logo, R.id.jifen2Logo, R.id.jifen3Logo, R.id.mSubmitCardView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mOwnerRequest:
                break;
            case R.id.mMangerRequest:
                break;
            case R.id.jifen1Logo:
                if (stageBean1 != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(stageBean1.getConfigDetail());
                        long jinbi = jsonObject.getLong("JINBI");
                        long fubao = jsonObject.getLong("FUBAO");
                        showRewardPopWindow(jinbi, fubao);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.jifen2Logo:
                if (stageBean2 != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(stageBean2.getConfigDetail());
                        long jinbi = jsonObject.getLong("JINBI");
                        long fubao = jsonObject.getLong("FUBAO");
                        showRewardPopWindow(jinbi, fubao);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.jifen3Logo:
                if (stageBean3 != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(stageBean3.getConfigDetail());
                        long jinbi = jsonObject.getLong("JINBI");
                        long fubao = jsonObject.getLong("FUBAO");
                        showRewardPopWindow(jinbi, fubao);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.mSubmitCardView:
                finish();
                break;
        }
    }

    private void showRewardPopWindow(final long jinbi, final long fubao) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.layout_reward_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.9f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        TextView mCoinTv = (TextView) view.findViewById(R.id.mCoinTv);
                        TextView mFuBaoTv = (TextView) view.findViewById(R.id.mFuBaoTv);
                        mCoinTv.setText(NumberUtils.rounLong(jinbi));
                        mFuBaoTv.setText(NumberUtils.rounLong(fubao));
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        int offsetX = (jifen2Logo.getWidth() - popupWindow.getContentView().getMeasuredWidth()) / 2;
        int offsetY = -(popupWindow.getContentView().getMeasuredHeight() + jifen2Logo.getHeight());
        popupWindow.showAsDropDown(jifen2Logo, offsetX, offsetY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
