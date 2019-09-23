package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.fragment.BillFragment;

import com.qianyu.communicate.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class BillActivity_ extends BaseActivity {

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
    private Fragment fragments[] = {new BillFragment(), new BillFragment()};

    @Override
    public int getRootViewId() {
        return R.layout.activity_bill;
    }

    @Override
    public void setViews() {
        Bundle args1 = new Bundle();
        args1.putInt("type", 1);
        fragments[0].setArguments(args1);
        Bundle args2 = new Bundle();
        args2.putInt("type", 2);
        fragments[1].setArguments(args2);
        setDefaultFragment(true, 0);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.msgTabTv, R.id.friendTabTv, R.id.backFL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.msgTabTv:
                changeTabCorlor(true);
                setDefaultFragment(false, 0);
                break;
            case R.id.friendTabTv:
                changeTabCorlor(false);
                setDefaultFragment(false, 1);
                break;
            case R.id.backFL:
                finish();
                break;
        }
    }

    private void setDefaultFragment(boolean first, int index) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (first) {
            fragmentTransaction.add(R.id.homeFrameLayout, fragments[0]);
            fragmentTransaction.add(R.id.homeFrameLayout, fragments[1]);
        }
        fragmentTransaction.hide(fragments[0]);
        fragmentTransaction.hide(fragments[1]);
        fragmentTransaction.show(fragments[index]);
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
