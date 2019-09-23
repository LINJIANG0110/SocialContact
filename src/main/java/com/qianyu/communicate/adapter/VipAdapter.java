package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class VipAdapter extends MyBaseAdapter<User, VipAdapter.ViewHolder> {

    public VipAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_vip, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {

    }


    static class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
