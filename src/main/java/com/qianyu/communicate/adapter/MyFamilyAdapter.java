package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class MyFamilyAdapter extends MyBaseAdapter<FamilyList.ContentBean, MyFamilyAdapter.ViewHolder> {


    public MyFamilyAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_my_family, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final FamilyList.ContentBean hostListVOListBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(hostListVOListBean.getHeadUrl()) ? "" : hostListVOListBean.getHeadUrl());
            holder.mNameTv.setText(TextUtils.isEmpty(hostListVOListBean.getGroupName()) ? "" : hostListVOListBean.getGroupName());
            holder.mContentTv.setText(TextUtils.isEmpty(hostListVOListBean.getDetails()) ? "" : hostListVOListBean.getDetails());
            holder.mTimeTv.setText(TimeUtils.getTime(hostListVOListBean.getUpdateTime()));
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mNameTv)
        TextView mNameTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
