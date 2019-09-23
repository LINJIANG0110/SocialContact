package com.qianyu.communicate.test;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class MyAdapter extends MyBaseAdapter {

    public MyAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.activity_splash_,null));
    }

    @Override
    protected void onBindViewHolder_(RecyclerView.ViewHolder holder, int position) {

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
