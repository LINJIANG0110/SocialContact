package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.SubScription;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class RoomSearchAdapter extends MyBaseAdapter<FamilyList.ContentBean, RoomSearchAdapter.ViewHolder> {


    public RoomSearchAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_room_search, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final FamilyList.ContentBean hostListVOListBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(hostListVOListBean.getHeadUrl()) ? "" : hostListVOListBean.getHeadUrl());
            holder.mNameTv.setText(TextUtils.isEmpty(hostListVOListBean.getGroupName()) ? "" : hostListVOListBean.getGroupName());
            holder.mContentTv.setText(TextUtils.isEmpty(hostListVOListBean.getDetails()) ? "" : hostListVOListBean.getDetails());
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mGifLogo)
        SimpleDraweeView mGifLogo;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mNameTv)
        TextView mNameTv;
        @InjectView(R.id.mLevelTv)
        TextView mLevelTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.liveStateLL)
        LinearLayout liveStateLL;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
