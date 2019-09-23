package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.chatuidemo.Constant;
import com.qianyu.chatuidemo.ui.ChatActivity;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FirendNear;
import com.qianyu.communicate.entity.SearchFriend;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.views.SwipeMenuView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FriendSearchAdapter extends MyBaseAdapter<SearchFriend.ContentBean, FriendSearchAdapter.ViewHolder> {
    private double lat;
    private double lng;

    public void setLatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public FriendSearchAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_friend_search, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            SearchFriend.ContentBean contentBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl());
            holder.mContentTv.setText(TextUtils.isEmpty(contentBean.getNickName()) ? "" : contentBean.getNickName());
            holder.mLevelTv.setText("Lv  " + contentBean.getLevel());
            holder.mDistanceTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(contentBean.getLastLoginTime()) + "·" + NumberUtils.getDistance(lng,lat,contentBean.getLastLoginLongitude(),contentBean.getLastLoginLatitude()));
            holder.mAgeTv.setText(""+contentBean.getAge());
            holder.mSignTv.setText(TextUtils.isEmpty(contentBean.getDetails())?"":contentBean.getDetails());
            int sex = contentBean.getSex();
            switch (sex) {
                case 1:
                    holder.ageSexLL.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_blue_));
                    holder.sexLogo.setImageResource(R.mipmap.xiangqing_nan1);
//                    mUserName.setTextColor(getResources().getColor(R.color.btn_blue_));
                    break;
                case 2:
                    holder.ageSexLL.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_pink));
                    holder.sexLogo.setImageResource(R.mipmap.xiangqing_nv1);
//                    mUserName.setTextColor(getResources().getColor(R.color.colorRed_));
                    break;
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mNameTv)
        TextView mNameTv;
        @InjectView(R.id.ageSexLL)
        LinearLayout ageSexLL;
        @InjectView(R.id.mAgeTv)
        TextView mAgeTv;
        @InjectView(R.id.sexLogo)
        ImageView sexLogo;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mDistanceTv)
        TextView mDistanceTv;
        @InjectView(R.id.contentLL)
        LinearLayout contentLL;
        @InjectView(R.id.mSignTv)
        TextView mSignTv;
        @InjectView(R.id.mLevelTv)
        TextView mLevelTv;
        @InjectView(R.id.liveStateLL)
        LinearLayout liveStateLL;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;
        @InjectView(R.id.mStateLogo)
        ImageView mStateLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
