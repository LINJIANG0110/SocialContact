package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.MallBill;
import com.qianyu.communicate.entity.MallBillList;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.fragment.BillFragment;
import com.qianyu.communicate.fragment.FuBowFragment;
import com.qianyu.communicate.fragment.MallFragment;
import com.qianyu.communicate.fragment.MallFuBaoFragment;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.TimeUtils;

import net.neiquan.applibrary.numberpicker.view.AlertDatePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-6-5.
 */

public class MallBillActivity extends BaseActivity {
    @InjectView(R.id.msgTabTv)
    TextView msgTabTv;
    @InjectView(R.id.friendTabTv)
    TextView friendTabTv;
    @InjectView(R.id.line)
    View line;
    @InjectView(R.id.homeFrameLayout)
    FrameLayout homeFrameLayout;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragments[] = {new MallFragment(), new MallFragment()};

    @Override
    public int getRootViewId() {
        return R.layout.activity_mall_bill;
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

    @OnClick({ R.id.msgTabTv, R.id.friendTabTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.msgTabTv:
                changeTabCorlor(true);
                setDefaultFragment(false,0);
                break;
            case R.id.friendTabTv:
                changeTabCorlor(false);
                setDefaultFragment(false,1);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

}
