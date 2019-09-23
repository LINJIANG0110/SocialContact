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
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.FirendNear;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.SwipeMenuView;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class LookMeAdapter extends MyBaseAdapter<FirendNear.ContentBean, LookMeAdapter.ViewHolder> {

    public LookMeAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_look_me, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final FirendNear.ContentBean contentBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl());
            holder.mContentTv.setText(TextUtils.isEmpty(contentBean.getNickName()) ? "" : contentBean.getNickName());
            holder.mNameTv.setText(TextUtils.isEmpty(contentBean.getNickName()) ? "" : contentBean.getNickName() + "：查看了你的个人信息");
            holder.mDistanceTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(contentBean.getCreateTime()));
            holder.contentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PersonalPageActivity.class);
                    intent.putExtra("userId",contentBean.getUserId());
                    context.startActivity(intent);
                }
            });
            holder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetUtils.getInstance().deleteLook(""+contentBean.getUserId(), 2, new NetCallBack() {
                        @Override
                        public void onSuccess(String result, String msg, ResultModel model) {
                            removeSingle(contentBean);
                        }

                        @Override
                        public void onFail(String code, String msg, String result) {

                        }
                    },null);
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mSwipeMenuView)
        SwipeMenuView mSwipeMenuView;
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
        @InjectView(R.id.mDeleteTv)
        TextView mDeleteTv;
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
