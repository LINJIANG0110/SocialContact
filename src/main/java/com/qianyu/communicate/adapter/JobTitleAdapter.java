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
 * Created by wl_user on 2017/8/24.
 */

public class JobTitleAdapter extends MyBaseAdapter<User, JobTitleAdapter.ViewHolder> {

    public JobTitleAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_pop_job_title, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
