package com.qianyu.communicate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.widget.SkillLevelView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.SkillDetailAdapter;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.SkillList;
import com.qianyu.communicate.entity.SkillUpInfo;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class SkillDetailActivity extends BaseActivity {
    @InjectView(R.id.mRecylerView)
    RecyclerView mRecylerView;
    @InjectView(R.id.skillLogo)
    SimpleDraweeView skillLogo;
    @InjectView(R.id.skillName)
    TextView skillName;
    @InjectView(R.id.skillLevel)
    TextView skillLevel;
    @InjectView(R.id.skillStars)
    SkillLevelView skillStars;
    @InjectView(R.id.currentEffect)
    TextView currentEffect;
    @InjectView(R.id.upEffect)
    TextView upEffect;
    @InjectView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.practiseHint)
    TextView practiseHint;
    @InjectView(R.id.expendLogo1)
    ImageView expendLogo1;
    @InjectView(R.id.expendChosen1)
    FrameLayout expendChosen1;
    @InjectView(R.id.expendNum1)
    TextView expendNum1;
    @InjectView(R.id.successRate1)
    TextView successRate1;
    @InjectView(R.id.expendLogo2)
    ImageView expendLogo2;
    @InjectView(R.id.expendChosen2)
    FrameLayout expendChosen2;
    @InjectView(R.id.expendNum2)
    TextView expendNum2;
    @InjectView(R.id.successRate2)
    TextView successRate2;
    @InjectView(R.id.expendLogo3)
    ImageView expendLogo3;
    @InjectView(R.id.expendChosen3)
    FrameLayout expendChosen3;
    @InjectView(R.id.expendNum3)
    TextView expendNum3;
    @InjectView(R.id.successRate3)
    TextView successRate3;
    @InjectView(R.id.costLogo1)
    ImageView costLogo1;
    @InjectView(R.id.costChosen1)
    FrameLayout costChosen1;
    @InjectView(R.id.costNum1)
    TextView costNum1;
    @InjectView(R.id.costRate1)
    TextView costRate1;
    @InjectView(R.id.costLogo2)
    ImageView costLogo2;
    @InjectView(R.id.costChosen2)
    FrameLayout costChosen2;
    @InjectView(R.id.costNum2)
    TextView costNum2;
    @InjectView(R.id.costRate2)
    TextView costRate2;
    @InjectView(R.id.costLogo3)
    ImageView costLogo3;
    @InjectView(R.id.costChosen3)
    FrameLayout costChosen3;
    @InjectView(R.id.costNum3)
    TextView costNum3;
    @InjectView(R.id.costRate3)
    TextView costRate3;
    @InjectView(R.id.currentSuccessRate)
    TextView currentSuccessRate;
    @InjectView(R.id.basicSuccessRate)
    TextView basicSuccessRate;
    @InjectView(R.id.successRateHint)
    TextView successRateHint;
    @InjectView(R.id.levelUpNow)
    CardView levelUpNow;
    private SkillList skillSelectEd;
    private SkillDetailAdapter adapter;
    private String conSumeType1 = "SUN";
    private String conSumeType2 = "DIAMOND";

    @Override
    public int getRootViewId() {
        return R.layout.activity_skill_detail;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        setTitleTv("技能详情");
        initRecylerView();
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.MINE_TAB) {
            skillUpInfo();
        }
    }

    @Override
    public void initData() {
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                int lastIndex = 0;
                for (int i = 0; i < data.size(); i++) {
                    if (((SkillList) data.get(i)).isSelected()) {
                        lastIndex = i;
                    }
                    ((SkillList) data.get(i)).setSelected(false);
                }
                skillSelectEd = (SkillList) data.get(position);
                skillSelectEd.setSelected(true);
                adapter.notifyItemRangeChanged(lastIndex, 1);
                adapter.notifyItemRangeChanged(position, 1);
                Uri uri = Uri.parse(TextUtils.isEmpty(skillSelectEd.getSkillPicPath()) ? "" : skillSelectEd.getSkillPicPath());
                final DraweeController draweeController =
                        Fresco.newDraweeControllerBuilder()
                                .setUri(uri)
                                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                                .build();
                skillLogo.setController(draweeController);
                skillName.setText(TextUtils.isEmpty(skillSelectEd.getSkillName()) ? "" : skillSelectEd.getSkillName());
                currentEffect.setText(TextUtils.isEmpty(skillSelectEd.getSkillDescribe()) ? "" : skillSelectEd.getSkillDescribe());
                skillUpInfo();
            }
        });
    }

    @OnClick({R.id.practiseHint, R.id.expendChosen1, R.id.expendChosen2, R.id.expendChosen3, R.id.costChosen1, R.id.costChosen2, R.id.costChosen3, R.id.successRateHint, R.id.levelUpNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.practiseHint:
                startActivity(new Intent(SkillDetailActivity.this, PractiseRateActivity.class));
                break;
            case R.id.expendChosen1:
                conSumeType1 = "SUN";
                changeChosenBg(expendChosen1);
                break;
            case R.id.expendChosen2:
                conSumeType1 = "MOON";
                changeChosenBg(expendChosen2);
                break;
            case R.id.expendChosen3:
                changeChosenBg(expendChosen3);
                break;
            case R.id.costChosen1:
                conSumeType2 = "FUBAO";
                changeCostBg(costChosen1);
                break;
            case R.id.costChosen2:
                conSumeType2 = "JINBI";
                changeCostBg(costChosen2);
                break;
            case R.id.costChosen3:
                conSumeType2 = "DIAMOND";
                changeCostBg(costChosen3);
                break;
            case R.id.successRateHint:
                startActivity(new Intent(SkillDetailActivity.this, SuccessRateActivity.class));
                break;
            case R.id.levelUpNow:
                if (skillSelectEd == null) {
                    ToastUtil.shortShowToast("请先选择需要升级的技能...");
                    return;
                }
                Tools.showDialog(SkillDetailActivity.this);
                NetUtils.getInstance().skillUp(skillSelectEd.getSkillType(), conSumeType1 + "," + conSumeType2, DeviceUtils.getDeviceId(SkillDetailActivity.this),new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        Tools.dismissWaitDialog();
                        ToastUtil.shortShowToast(msg);
                        skillUpInfo();
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        Tools.dismissWaitDialog();
                        if (TextUtils.equals("1008",code)) {
                            new AlertDialog.Builder(SkillDetailActivity.this).setTitle(msg)
                                    .setMessage(msg + ",是否购买?")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            startActivity(new Intent(SkillDetailActivity.this, HotMallActivity.class));
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).create().show();
                        }else {
                            ToastUtil.shortShowToast(msg);
                        }
                    }
                }, null);
                break;
        }
    }

    private void skillUpInfo() {
        NetUtils.getInstance().skillUpInfo(skillSelectEd.getSkillType(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                SkillUpInfo skillUpInfo = (SkillUpInfo) model.getModel();
                if (skillUpInfo != null) {
                    SkillUpInfo.SkillMapBean skillMap = skillUpInfo.getSkillMap();
                    if (skillMap != null) {
                        skillLevel.setText("Lv." + skillMap.getLevel());
                        skillStars.setLevelView(skillMap.getLevel());
                        if (skillMap.getNeedExp() != 0) {
                            double value = new BigDecimal((float) skillMap.getSkillEnergy() / skillMap.getNeedExp()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            mProgressBar.setProgress((int) (value * 100));
                        } else {
                            mProgressBar.setProgress(100);
                        }
                        basicSuccessRate.setText("基础成功概率：" + skillMap.getBaseClientRate() / 10000 * 100 + "%");
                        String holdColor = "<font color='#59e5df'>";
                        String holdColor_ = "<font color='#FF0000'>";
                        expendNum1.setText(Html.fromHtml((skillUpInfo.getSuns() < skillMap.getSunNum() ? holdColor_ : holdColor) + NumberUtils.rounLong(skillUpInfo.getSuns()) + "</font>/" + NumberUtils.rounLong(skillMap.getSunNum())));
                        expendNum2.setText(Html.fromHtml((skillUpInfo.getMoons() < skillMap.getMoonNum() ? holdColor_ : holdColor) + NumberUtils.rounLong(skillUpInfo.getMoons()) + "</font>/" + NumberUtils.rounLong(skillMap.getMoonNum())));
                        costNum1.setText(Html.fromHtml((skillUpInfo.getFubao() <skillMap.getFubaoNum() ? holdColor_ : holdColor) + NumberUtils.rounLong(skillUpInfo.getFubao()) + "</font>/" + NumberUtils.rounLong(skillMap.getFubaoNum())));
                        costNum2.setText(Html.fromHtml((skillUpInfo.getGold() <skillMap.getJinbiNum() ? holdColor_ : holdColor) + NumberUtils.rounLong(skillUpInfo.getGold()) + "</font>/" + NumberUtils.rounLong(skillMap.getJinbiNum())));
                        costNum3.setText(Html.fromHtml((skillUpInfo.getDiamond() <skillMap.getDiamondNum() ? holdColor_ : holdColor) + NumberUtils.rounLong(skillUpInfo.getDiamond()) + "</font>/" + NumberUtils.rounLong(skillMap.getDiamondNum())));
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, SkillUpInfo.class);
    }

    private void loadDatas() {
        NetUtils.getInstance().skillList(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                List<SkillList> skillList = (List<SkillList>) model.getModelList();
                if (skillList != null && skillList.size() > 0) {
                    skillSelectEd = skillList.get(0);
                    skillSelectEd.setSelected(true);
                    Uri uri = Uri.parse(TextUtils.isEmpty(skillSelectEd.getSkillPicPath()) ? "" : skillSelectEd.getSkillPicPath());
                    final DraweeController draweeController =
                            Fresco.newDraweeControllerBuilder()
                                    .setUri(uri)
                                    .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                                    .build();
                    skillLogo.setController(draweeController);
                    skillName.setText(TextUtils.isEmpty(skillSelectEd.getSkillName()) ? "" : skillSelectEd.getSkillName());
                    currentEffect.setText(TextUtils.isEmpty(skillSelectEd.getSkillDescribe()) ? "" : skillSelectEd.getSkillDescribe());
                    skillUpInfo();
                    adapter.appendAll(skillList);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, SkillList.class);
    }

    private void initRecylerView() {
        LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecylerView.setLayoutManager(mLayoutManager);
        adapter = new SkillDetailAdapter(this, null);
        mRecylerView.setAdapter(adapter);
    }

    private void changeChosenBg(FrameLayout mChosenFL) {
        expendChosen1.setBackground(null);
        expendChosen2.setBackground(null);
        expendChosen3.setBackground(null);
        mChosenFL.setBackground(getResources().getDrawable(R.drawable.shape_blue_dashed));
    }

    private void changeCostBg(FrameLayout mChosenFL) {
        costChosen1.setBackground(null);
        costChosen2.setBackground(null);
        costChosen3.setBackground(null);
        mChosenFL.setBackground(getResources().getDrawable(R.drawable.shape_blue_dashed));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
