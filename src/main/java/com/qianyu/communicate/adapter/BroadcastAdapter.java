package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.BroadcastBean;
import com.qianyu.communicate.entity.FirendNear;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by JavaDog on 2019-8-20.
 */

public class BroadcastAdapter extends MyBaseAdapter<BroadcastBean, BroadcastAdapter.ViewHolder> {

    @Override
    protected BroadcastAdapter.ViewHolder getViewHolder() {
        return new BroadcastAdapter.ViewHolder(mInflater.inflate(R.layout.item_broadcast, null));
    }

    public BroadcastAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected void onBindViewHolder_(BroadcastAdapter.ViewHolder holder, int position) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
