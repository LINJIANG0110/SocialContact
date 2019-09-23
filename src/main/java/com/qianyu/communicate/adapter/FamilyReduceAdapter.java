package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.FamilyReduceActivity;
import com.qianyu.communicate.activity.FriendInviteActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.FamilyManage;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.TimeUtils;

import net.neiquan.applibrary.wight.CommonPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FamilyReduceAdapter extends MyBaseAdapter<FamilyDetail.MembersBean, FamilyReduceAdapter.ViewHolder> {

    public FamilyReduceAdapter(Activity context, List data) {
        super(context, data);
    }


    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_family_reudce, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            FamilyDetail.MembersBean membersBean = data.get(position);
            holder.mSelectLogo.setImageResource(membersBean.isSelected() ? R.mipmap.wxdl_xuanze : R.mipmap.wxdl_xuanzehui);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(membersBean.getHeadUrl()) ? "" : membersBean.getHeadUrl());
            holder.mUserNameTv.setText(TextUtils.isEmpty(membersBean.getNickName()) ? "" : membersBean.getNickName());
            holder.mTimeTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(membersBean.getLastLoginTime()));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mSelectLogo)
        ImageView mSelectLogo;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mUserNameTv)
        TextView mUserNameTv;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
