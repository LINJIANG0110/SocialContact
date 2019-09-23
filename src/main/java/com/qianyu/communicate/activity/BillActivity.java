package com.qianyu.communicate.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.fragment.FuBowFragment;

import com.qianyu.communicate.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class BillActivity extends BaseActivity {

    @InjectView(R.id.backFL)
    FrameLayout backFL;
    @InjectView(R.id.msgTabTv)
    TextView msgTabTv;
    @InjectView(R.id.friendTabTv)
    TextView friendTabTv;
    @InjectView(R.id.line)
    View line;
    @InjectView(R.id.homeFrameLayout)
    FrameLayout homeFrameLayout;
    private FragmentTransaction fragmentTransaction;
    private FuBowFragment fuBowFragment = new FuBowFragment();

    @Override
    public int getRootViewId() {
        return R.layout.activity_bill;
    }

    @Override
    public void setViews() {
        setDefaultFragment();
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.msgTabTv, R.id.friendTabTv, R.id.backFL})
    public void onViewClicked(View view) {
        EventBuss event = new EventBuss(EventBuss.INCOME_LIST);
        switch (view.getId()) {
            case R.id.msgTabTv:
                changeTabCorlor(true);
                event.setMessage(1);
                EventBus.getDefault().post(event);
                break;
            case R.id.friendTabTv:
                changeTabCorlor(false);
                event.setMessage(2);
                EventBus.getDefault().post(event);
                break;
            case R.id.backFL:
                finish();
                break;
        }
    }

    private void setDefaultFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.homeFrameLayout, fuBowFragment);
        fragmentTransaction.show(fuBowFragment);
        fragmentTransaction.commit();
    }


    private void changeTabCorlor(boolean tab1) {
        if (tab1) {
            msgTabTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_left2));
            msgTabTv.setTextColor(getResources().getColor(R.color.white));
            friendTabTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_right1));
            friendTabTv.setTextColor(getResources().getColor(R.color.app_color));
        } else {
            msgTabTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_left1));
            msgTabTv.setTextColor(getResources().getColor(R.color.app_color));
            friendTabTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_right2));
            friendTabTv.setTextColor(getResources().getColor(R.color.white));
        }
    }


}
