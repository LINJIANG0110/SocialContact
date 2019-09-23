package com.qianyu.communicate.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */
public class ThreeFragment_ extends BaseFragment {

    @InjectView(R.id.threeTv)
    TextView threeTv;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_three;
    }

    @Override
    public void setViews() {
    }

    @Override
    public void initData() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.threeTv)
    public void onClick() {

    }
}

