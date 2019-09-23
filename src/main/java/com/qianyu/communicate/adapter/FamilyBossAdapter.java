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
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.User;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FamilyBossAdapter extends MyBaseAdapter<FamilyDetail.GroupBossStateBean, FamilyBossAdapter.ViewHolder> {

    public FamilyBossAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_family_boss, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            FamilyDetail.GroupBossStateBean groupBossStateBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(groupBossStateBean.getBossUrl()) ? "" : groupBossStateBean.getBossUrl());
            holder.userName.setText(TextUtils.isEmpty(groupBossStateBean.getBossName()) ? "" : groupBossStateBean.getBossName());
            holder.mStateTv.setText(groupBossStateBean.getIsallowcall()==0?"不可召唤":"可召唤");
            holder.mNumTv.setText("("+groupBossStateBean.getDonum()+"/"+groupBossStateBean.getAllownum()+")");
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.userName)
        TextView userName;
        @InjectView(R.id.mStateTv)
        TextView mStateTv;
        @InjectView(R.id.mNumTv)
        TextView mNumTv;
        @InjectView(R.id.mRootLL)
        LinearLayout mRootLL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
