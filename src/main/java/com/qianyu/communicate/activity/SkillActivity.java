package com.qianyu.communicate.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.widget.SkillLevelView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.SkillAdapter;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.SkillList;
import com.qianyu.communicate.entity.SkillRelease;
import com.qianyu.communicate.entity.SkillReleaseInfo;
import com.qianyu.communicate.entity.UserInfo;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.RoundProgressBar;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class SkillActivity extends BaseActivity {
    @InjectView(R.id.mRecylerView)
    RecyclerView mRecylerView;
    @InjectView(R.id.otherAbility)
    SkillLevelView otherAbility;
    @InjectView(R.id.otherEffect)
    TextView otherEffect;
    @InjectView(R.id.mRoundProgress)
    RoundProgressBar mRoundProgress;
    @InjectView(R.id.mHitRate)
    TextView mHitRate;
    @InjectView(R.id.myAbility)
    SkillLevelView myAbility;
    @InjectView(R.id.myEffect)
    TextView myEffect;
    @InjectView(R.id.hitRateHint)
    TextView hitRateHint;
    @InjectView(R.id.mEffect1)
    TextView mEffect1;
    @InjectView(R.id.mEffect2)
    TextView mEffect2;
    @InjectView(R.id.expendLogo0)
    SimpleDraweeView expendLogo0;
    @InjectView(R.id.expendChosen0)
    FrameLayout expendChosen0;
    @InjectView(R.id.expendNum0)
    TextView expendNum0;
    @InjectView(R.id.successRate0)
    TextView successRate0;
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
    @InjectView(R.id.singleAttack)
    ImageView singleAttack;
    @InjectView(R.id.groupAttack)
    ImageView groupAttack;
    @InjectView(R.id.punishTa)
    CardView punishTa;
    @InjectView(R.id.singleAttackLL)
    LinearLayout singleAttackLL;
    @InjectView(R.id.groupAttackLL)
    LinearLayout groupAttackLL;
    private long userId;
    private SkillAdapter adapter;
    private SkillList skillSelectEd;
    private String consumePop = "SKILLNUMBER";

    @Override
    public int getRootViewId() {
        return R.layout.activity_skill;
    }

    @Override
    public void setViews() {
//        Spanned spanned = Html.fromHtml("命中 <font color='#00ffff'>+90.00</font>  闪避 <font color='#00ffff'>+63.00</font>");
        setTitleTv("你想对Ta做什么?");
        initRecylerView();
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
                mEffect1.setText(TextUtils.isEmpty(skillSelectEd.getSkillDescribe()) ? "" : skillSelectEd.getSkillDescribe());
                expendLogo0.setImageURI(TextUtils.isEmpty(skillSelectEd.getStaticPath()) ? "" : skillSelectEd.getStaticPath());
                skillReleaseInfo();
            }
        });
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            userId = getIntent().getLongExtra("userId", 0);
            loadDatas();
        }
    }

    @OnClick({R.id.hitRateHint, R.id.expendChosen0, R.id.expendChosen1, R.id.expendChosen2, R.id.expendChosen3, R.id.singleAttackLL, R.id.groupAttackLL, R.id.punishTa})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hitRateHint:
                startActivity(new Intent(SkillActivity.this, SkillDetailActivity.class));
                break;
            case R.id.expendChosen0:
                consumePop = "SKILLNUMBER";
                changeChosenBg(expendChosen0);
                break;
            case R.id.expendChosen1:
                consumePop = "JINBI";
                changeChosenBg(expendChosen1);
                break;
            case R.id.expendChosen2:
                consumePop = "DIAMOND";
                changeChosenBg(expendChosen2);
                break;
            case R.id.expendChosen3:
                consumePop = "FUBAO";
                changeChosenBg(expendChosen3);
                break;
            case R.id.singleAttackLL:
                singleAttack.setImageResource(R.mipmap.wxdl_xuanze);
                groupAttack.setImageResource(R.mipmap.wxdl_xuanzehui);
                break;
            case R.id.groupAttackLL:
                groupAttack.setImageResource(R.mipmap.wxdl_xuanze);
                singleAttack.setImageResource(R.mipmap.wxdl_xuanzehui);
                break;
            case R.id.punishTa:
                if (skillSelectEd == null) {
                    ToastUtil.shortShowToast("请先选择要使用的技能...");
                    return;
                }
                Tools.showDialog(SkillActivity.this);
                NetUtils.getInstance().skillRelease(userId, skillSelectEd.getSkillType(), consumePop, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        Tools.dismissWaitDialog();
                        SkillRelease skillRelease = (SkillRelease) model.getModel();
                        if (skillRelease != null) {
                            if (skillRelease.getIsOk() == 1) {//1成功 0失败
                                Tools.skillSuccess(skillRelease.getAccPhone(), skillRelease.getSendNickName(),skillRelease.getAccNickName(),skillRelease.getSKIllName(), skillRelease.getMsg(),
                                        skillRelease.getSendUserId(), skillRelease.getSendLevel(), skillRelease.getAccUserId(), skillRelease.getAccLevel());
                            } else {
                                ToastUtil.shortShowToast((TextUtils.isEmpty(msg)?"":replaceBlank((Html.fromHtml(msg))+"")));
                                Tools.skillFail(skillRelease.getAccPhone(),  skillRelease.getSendNickName(),skillRelease.getAccNickName(),skillRelease.getSKIllName(), skillRelease.getMsg(),
                                        skillRelease.getSendUserId(), skillRelease.getSendLevel(), skillRelease.getAccUserId(), skillRelease.getAccLevel());
                            }
                        }
                        skillReleaseInfo();
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        Tools.dismissWaitDialog();
                        ToastUtil.shortShowToast(msg);
                    }
                }, SkillRelease.class);
                break;
        }
    }

    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }

    private void skillReleaseInfo() {
        NetUtils.getInstance().skillReleaseInfo(userId, skillSelectEd.getSkillType(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                SkillReleaseInfo skillReleaseInfo = (SkillReleaseInfo) model.getModel();
                if (skillReleaseInfo != null) {
                    SkillReleaseInfo.ConsumePopBean consumePop = skillReleaseInfo.getConsumePop();
                    SkillReleaseInfo.ConsumePopBean holdPop = skillReleaseInfo.getHoldPop();
                    SkillReleaseInfo.ToUserSkillInfoBean toUserSkillInfo = skillReleaseInfo.getToUserSkillInfo();
                    SkillReleaseInfo.UserSkillInfoBean userSkillInfo = skillReleaseInfo.getUserSkillInfo();
                    if (toUserSkillInfo != null) {
                        otherAbility.setLevelView(toUserSkillInfo.getLevel());
                        otherEffect.setText(TextUtils.isEmpty(toUserSkillInfo.getSkillDescribe()) ? "" : toUserSkillInfo.getSkillDescribe());
                    }
                    if (userSkillInfo != null) {
                        myAbility.setLevelView(userSkillInfo.getLevel());
                        myEffect.setText(TextUtils.isEmpty(userSkillInfo.getSkillDescribe()) ? "" : userSkillInfo.getSkillDescribe());
                        mRoundProgress.setProgress(Integer.parseInt(userSkillInfo.getRate()));
                        mHitRate.setText(userSkillInfo.getRate() + "%");
                    }
                    if (consumePop != null && holdPop != null) {
                        String holdColor = "<font color='#59e5df'>";
                        String holdColor_ = "<font color='#FF0000'>";
                        expendNum0.setText(Html.fromHtml((holdPop.getSKILLNUMBER() < consumePop.getSKILLNUMBER() ? holdColor_ : holdColor) + NumberUtils.rounLong(holdPop.getSKILLNUMBER()) + "</font>/" + NumberUtils.rounLong(consumePop.getSKILLNUMBER())));
                        expendNum1.setText(Html.fromHtml((holdPop.getJINBI() < consumePop.getJINBI() ? holdColor_ : holdColor) + NumberUtils.rounLong(holdPop.getJINBI()) + "</font>/" + NumberUtils.rounLong(consumePop.getJINBI())));
                        expendNum2.setText(Html.fromHtml((holdPop.getDIAMOND() < consumePop.getDIAMOND() ? holdColor_ : holdColor) + NumberUtils.rounLong(holdPop.getDIAMOND()) + "</font>/" + NumberUtils.rounLong(consumePop.getDIAMOND())));
                        expendNum3.setText(Html.fromHtml((holdPop.getFUBAO() < consumePop.getFUBAO() ? holdColor_ : holdColor) + NumberUtils.rounLong(holdPop.getFUBAO()) + "</font>/" + NumberUtils.rounLong(consumePop.getFUBAO())));
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, SkillReleaseInfo.class);
    }

    private void loadDatas() {
        NetUtils.getInstance().skillList(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                List<SkillList> skillList = (List<SkillList>) model.getModelList();
                if (skillList != null && skillList.size() > 0) {
                    skillSelectEd = skillList.get(0);
                    skillSelectEd.setSelected(true);
                    mEffect1.setText(TextUtils.isEmpty(skillSelectEd.getSkillDescribe()) ? "" : skillSelectEd.getSkillDescribe());
                    expendLogo0.setImageURI(TextUtils.isEmpty(skillSelectEd.getStaticPath()) ? "" : skillSelectEd.getStaticPath());
                    skillReleaseInfo();
                    adapter.appendAll(skillList);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, SkillList.class);
    }

    private void initRecylerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        adapter = new SkillAdapter(this, null);
        mRecylerView.setAdapter(adapter);
//        mRecylerView.addItemDecoration(new SpacesItemDecoration(2));
//        ((SimpleItemAnimator)mRecylerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void changeChosenBg(FrameLayout mChosenFL) {
        expendChosen0.setBackground(null);
        expendChosen1.setBackground(null);
        expendChosen2.setBackground(null);
        expendChosen3.setBackground(null);
        mChosenFL.setBackground(getResources().getDrawable(R.drawable.shape_blue_dashed));
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
//            outRect.right = space;
//            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
//            if (parent.getChildPosition(view) == 0)
            outRect.top = space;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
