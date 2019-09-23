package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.BaseActivity;

import org.haitao.common.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class LiveWorkPlaceActivity extends BaseActivity {
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mNameTv)
    TextView mNameTv;
    @InjectView(R.id.mJoinTime)
    TextView mJoinTime;
    @InjectView(R.id.myFansRv)
    RelativeLayout myFansRv;
    @InjectView(R.id.myProductionsRv)
    RelativeLayout myProductionsRv;
    @InjectView(R.id.mDaynmicRv)
    RelativeLayout mDaynmicRv;
    @InjectView(R.id.lastPlayCount)
    TextView lastPlayCount;
    @InjectView(R.id.newFansCount)
    TextView newFansCount;
    @InjectView(R.id.monthIncome)
    TextView monthIncome;
    @InjectView(R.id.totalPlayCount)
    TextView totalPlayCount;
    @InjectView(R.id.totalFansCount)
    TextView totalFansCount;
    @InjectView(R.id.totalIncome)
    TextView totalIncome;
    @InjectView(R.id.mMyRoomRv)
    RelativeLayout mMyRoomRv;

    @Override
    public int getRootViewId() {
        return R.layout.activity_live_work_place;
    }

    @Override
    public void setViews() {
        setTitleTv("名医工作台");
        mHeadImg.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
    }

    @Override
    public void initData() {
        loadDatas();
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            return;
        }
    }

    @OnClick({R.id.myFansRv, R.id.myProductionsRv, R.id.mDaynmicRv,R.id.mMyRoomRv})
    public void onViewClicked(View view) {
        User user = MyApplication.getInstance().user;
        if (!MyApplication.getInstance().isLogin()) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(LiveWorkPlaceActivity.this, RegistActivity.class));
            return;
        }
        switch (view.getId()) {
            case R.id.myFansRv:
                Intent intent = new Intent(LiveWorkPlaceActivity.this, MyConcernActivity.class);
                intent.putExtra("sign", "2");
                intent.putExtra("userId", user.getUserId());
                startActivity(intent);
                break;
            case R.id.myProductionsRv:
                startActivity(new Intent(LiveWorkPlaceActivity.this, MyProductionsActivity.class));
                break;
            case R.id.mDaynmicRv:
                startActivity(new Intent(LiveWorkPlaceActivity.this, MyIncomeActivity.class));
                break;
            case R.id.mMyRoomRv:
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(LiveWorkPlaceActivity.this, RegistActivity.class));
                    return;
                }
                Intent intent1 = new Intent(LiveWorkPlaceActivity.this, MyRoomActivity.class);
                intent1.putExtra("userId",user.getUserId());
                startActivity(intent1);
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
