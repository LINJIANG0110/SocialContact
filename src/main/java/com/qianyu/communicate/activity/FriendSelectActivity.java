package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guyj.BidirectionalSeekBar;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.Constellation;
import com.qianyu.communicate.entity.FriendSelect;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.applibrary.numberpicker.view.AlertDatePicker;
import net.neiquan.applibrary.numberpicker.view.AlertPickerSingle;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-3-8.
 */

public class FriendSelectActivity extends BaseActivity {
    @InjectView(R.id.manChoseLogo)
    ImageView manChoseLogo;
    @InjectView(R.id.manSexFL)
    FrameLayout manSexFL;
    @InjectView(R.id.womanChoseLogo)
    ImageView womanChoseLogo;
    @InjectView(R.id.womanSexFL)
    FrameLayout womanSexFL;
    @InjectView(R.id.unKnowChoseLogo)
    ImageView unKnowChoseLogo;
    @InjectView(R.id.unKnowSexFL)
    FrameLayout unKnowSexFL;
    @InjectView(R.id.m15MinutesTv)
    TextView m15MinutesTv;
    @InjectView(R.id.mOneHourTv)
    TextView mOneHourTv;
    @InjectView(R.id.mOneDayTv)
    TextView mOneDayTv;
    @InjectView(R.id.mThreeDayTv)
    TextView mThreeDayTv;
    @InjectView(R.id.mAgeTv)
    TextView mAgeTv;
    @InjectView(R.id.mSeekBar)
    BidirectionalSeekBar mSeekBar;
    @InjectView(R.id.mAgeRv)
    RelativeLayout mAgeRv;
    @InjectView(R.id.mStarTv)
    TextView mStarTv;
    @InjectView(R.id.mStarRv)
    RelativeLayout mStarRv;
    private int sex = 1;
    private String birthDay;
    private ArrayList<String> startList = new ArrayList<>();
    private int activeTime = 3 * 24 * 60;
    private int minAge = 18;
    private int maxAge = 41;
    private List<Constellation> list;
    private int position = -1;

    @Override
    public int getRootViewId() {
        return R.layout.activity_friend_select;
    }

    @Override
    public void setViews() {
        setTitleTv("筛选附近人");
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        setNextTv("保存");
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendSelect friendSelect = new FriendSelect();
                friendSelect.setSex(sex);
                friendSelect.setActivetime(activeTime);
                friendSelect.setMinage(minAge);
                friendSelect.setMaxage((maxAge < 40 ? maxAge : 41));
                if (list != null && list.size() > 0 && position != -1) {
                    Constellation constellation = list.get(position);
                    friendSelect.setConstellationId(constellation.getConstellationId());
                }
                EventBuss event = new EventBuss(EventBuss.FRIEND_SELECT);
                event.setMessage(friendSelect);
                EventBus.getDefault().post(event);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        loadConstellation();
        mSeekBar.setOnSeekBarChangeListener(new BidirectionalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(int leftProgress, int rightProgress) {
                minAge = leftProgress;
                maxAge = rightProgress;
                mAgeTv.setText(minAge + "-" + (maxAge < 40 ? rightProgress : "40+"));
            }
        });
    }

    private void loadConstellation() {
        NetUtils.getInstance().getConstellation(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                list = (List<Constellation>) model.getModelList();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        startList.add(list.get(i).getConstellation());
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, Constellation.class);
    }

    @OnClick({R.id.manSexFL, R.id.womanSexFL, R.id.unKnowSexFL, R.id.m15MinutesTv, R.id.mOneHourTv, R.id.mOneDayTv, R.id.mThreeDayTv, R.id.mAgeRv, R.id.mStarRv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.manSexFL:
                if(sex==1){
                    manChoseLogo.setVisibility(View.GONE);
                    womanChoseLogo.setVisibility(View.GONE);
                    unKnowChoseLogo.setVisibility(View.GONE);
                    sex = 0;
                }else {
                    manChoseLogo.setVisibility(View.VISIBLE);
                    womanChoseLogo.setVisibility(View.GONE);
                    unKnowChoseLogo.setVisibility(View.GONE);
                    sex = 1;
                }
                break;
            case R.id.womanSexFL:
                if(sex==2){
                    manChoseLogo.setVisibility(View.GONE);
                    womanChoseLogo.setVisibility(View.GONE);
                    unKnowChoseLogo.setVisibility(View.GONE);
                    sex = 0;
                }else {
                    manChoseLogo.setVisibility(View.GONE);
                    unKnowChoseLogo.setVisibility(View.GONE);
                    womanChoseLogo.setVisibility(View.VISIBLE);
                    sex = 2;
                }
                break;
            case R.id.unKnowSexFL:
                manChoseLogo.setVisibility(View.GONE);
                womanChoseLogo.setVisibility(View.GONE);
                unKnowChoseLogo.setVisibility(View.VISIBLE);
                sex = 0;
                break;
            case R.id.m15MinutesTv:
                activeTime = 15;
                setTimeSelectedBg(m15MinutesTv);
                break;
            case R.id.mOneHourTv:
                activeTime = 60;
                setTimeSelectedBg(mOneHourTv);
                break;
            case R.id.mOneDayTv:
                activeTime = 24 * 60;
                setTimeSelectedBg(mOneDayTv);
                break;
            case R.id.mThreeDayTv:
                activeTime = 3 * 24 * 60;
                setTimeSelectedBg(mThreeDayTv);
                break;
            case R.id.mAgeRv:
//                new AlertDatePicker.Builder(FriendSelectActivity.this)
//                        .setTitle("请选择您的出生日期")
//                        .setCancelable(true)
//                        .setDateSelectListener(new AlertDatePicker.OnDateSelectListener() {
//                            @Override
//                            public void endSelect(Date selectDate, String selectDateStr, boolean isCancel) {
//                                birthDay = selectDateStr;
//                                mAgeTv.setText(selectDateStr);
//                            }
//                        }).show();
                break;
            case R.id.mStarRv:
                if (startList == null || startList.size() <= 0) {
                    ToastUtil.shortShowToast("暂无星座数据...");
                    return;
                }
                new AlertPickerSingle.Builder(FriendSelectActivity.this)
                        .setTitle("选择星座")
                        .setCancelable(true)
                        .setData(startList)
                        .setDefaultIndex(2)
                        .setOnItemSelectListener(new AlertPickerSingle.OnItemSelectListener() {
                            @Override
                            public void endSelect(AlertPickerSingle chooser, boolean isCancel, int id, String text) {
                                position = id+1;
                                mStarTv.setText(text);
                            }
                        }).show();
                break;
        }
    }

    private void setTimeSelectedBg(TextView mSelectedTv) {
        m15MinutesTv.setBackground(getResources().getDrawable(R.drawable.shape_little_cornor_gray));
        m15MinutesTv.setTextColor(getResources().getColor(R.color.text_gray));
        mOneHourTv.setBackground(getResources().getDrawable(R.drawable.shape_little_cornor_gray));
        mOneHourTv.setTextColor(getResources().getColor(R.color.text_gray));
        mOneDayTv.setBackground(getResources().getDrawable(R.drawable.shape_little_cornor_gray));
        mOneDayTv.setTextColor(getResources().getColor(R.color.text_gray));
        mThreeDayTv.setBackground(getResources().getDrawable(R.drawable.shape_little_cornor_gray));
        mThreeDayTv.setTextColor(getResources().getColor(R.color.text_gray));
        mSelectedTv.setBackground(getResources().getDrawable(R.drawable.shape_solid_app_corlor_));
        mSelectedTv.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
