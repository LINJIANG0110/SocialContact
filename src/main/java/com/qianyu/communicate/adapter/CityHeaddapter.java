package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.WorkTag;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class CityHeaddapter extends MyBaseAdapter<WorkTag, CityHeaddapter.ViewHolder> {

    public CityHeaddapter(Activity context, List data) {
        super(context, data);
    }


    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_city_head, null));
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
