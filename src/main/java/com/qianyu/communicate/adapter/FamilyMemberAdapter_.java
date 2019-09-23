package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FamilyMemberAdapter_ extends MyBaseAdapter<FamilyDetail.MembersBean, FamilyMemberAdapter_.ViewHolder> {

    public FamilyMemberAdapter_(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_family_member, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        holder.mManageTv.setVisibility(View.GONE);
        if (data != null&&data.size()>0) {
            FamilyDetail.MembersBean membersBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(membersBean.getHeadUrl())?"":membersBean.getHeadUrl());
            holder.userName.setText(TextUtils.isEmpty(membersBean.getNickName())?"":membersBean.getNickName());
            holder.mTimeTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(membersBean.getLastLoginTime()));
            holder.headLogo.setVisibility(View.VISIBLE);
            switch (membersBean.getUserType()){
                case 1:
                    holder.headLogo.setImageResource(R.mipmap.patriarch);
                    break;
                case 2:
                    holder.headLogo.setImageResource(R.mipmap.manager);
                    break;
                case 3:
                case 4:
                case 5:
                    holder.headLogo.setVisibility(View.GONE);
                    break;
            }
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.headLogo)
        ImageView headLogo;
        @InjectView(R.id.userName)
        TextView userName;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mManageTv)
        TextView mManageTv;
        @InjectView(R.id.mRootLL)
        LinearLayout mRootLL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
