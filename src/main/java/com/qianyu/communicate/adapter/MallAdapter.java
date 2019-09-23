package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class MallAdapter extends MyBaseAdapter<User, MallAdapter.ViewHolder> {

    public MallAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_mall, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.goodName)
        TextView goodName;
        @InjectView(R.id.goodImg)
        ImageView goodImg;
        @InjectView(R.id.goodDiscount)
        TextView goodDiscount;
        @InjectView(R.id.goodDesc)
        TextView goodDesc;
        @InjectView(R.id.costLogo)
        ImageView costLogo;
        @InjectView(R.id.costMoney)
        TextView costMoney;
        @InjectView(R.id.cheapLogo)
        ImageView cheapLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}