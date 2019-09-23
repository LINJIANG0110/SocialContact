package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.WorkTag;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class CityChooseadapter extends MyBaseAdapter<WorkTag, CityChooseadapter.ViewHolder> {


    public CityChooseadapter(Activity context, List data) {
        super(context, data);
    }


    @Override
    public int getItemCount() {
        return 11;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_city_choose, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        holder.mCityRecylerView.setLayoutManager(layoutManager);
        holder.mCityRecylerView.setAdapter(new CityItemdapter(context,null));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.mCityRecylerView)
        RecyclerView mCityRecylerView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
