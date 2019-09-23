package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.ConcernList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.views.SwipeMenuView;

import com.qianyu.communicate.base.MyBaseAdapter;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class MyConcernAdapter extends MyBaseAdapter<ConcernList.ContentBean, MyConcernAdapter.ViewHolder> {


    public MyConcernAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_my_concern, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final ConcernList.ContentBean hostListVOListBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(hostListVOListBean.getHeadUrl()) ? "" : hostListVOListBean.getHeadUrl());
            holder.mNameTv.setText(TextUtils.isEmpty(hostListVOListBean.getNickName()) ? "" : hostListVOListBean.getNickName());
            holder.mContentTv.setText("ID:"+hostListVOListBean.getUserId());
            holder.mSwipeMenuView.setSwipeEnable(false);
            holder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mSwipeMenuView.smoothClose();
                }
            });
            holder.contentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonalPageActivity.class);
                    intent.putExtra("userId", hostListVOListBean.getUserId());
                    context.startActivity(intent);
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mSwipeMenuView)
        SwipeMenuView mSwipeMenuView;
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
        @InjectView(R.id.contentLL)
        LinearLayout contentLL;
        @InjectView(R.id.mDeleteTv)
        TextView mDeleteTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
