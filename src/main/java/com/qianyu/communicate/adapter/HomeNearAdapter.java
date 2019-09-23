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
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.FirendNear;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.SwipeMenuView;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class HomeNearAdapter extends MyBaseAdapter<FirendNear.ContentBean, HomeNearAdapter.ViewHolder> {
    private double lat;
    private double lng;
    private boolean friend;
    private FamilyList.ContentBean familyInfo;
    private String topicId;
    private String topicTitle;
    private String mType;

    public void setLatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        notifyDataSetChanged();
    }

    // 新增话题-话题类型则将familyInfo传空-邀请入群则将话题参数传空
    public void setFriendFamily(boolean friend, FamilyList.ContentBean familyInfo, String topicId, String topicTitle, String mType) {
        this.friend = friend;
        this.familyInfo = familyInfo;
        this.mType = mType;
        this.topicId = topicId;
        this.topicTitle = topicTitle;
    }

    public HomeNearAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_home_near, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            holder.mSwipeMenuView.setSwipeEnable(false);
            final FirendNear.ContentBean contentBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl());
            holder.mContentTv.setText(TextUtils.isEmpty(contentBean.getNickName()) ? "" : contentBean.getNickName());
            holder.mDetailTv.setVisibility(TextUtils.isEmpty(contentBean.getDetails()) ? View.GONE : View.VISIBLE);
            holder.mDetailTv.setText(TextUtils.isEmpty(contentBean.getDetails()) ? "" : contentBean.getDetails());
            holder.mLevelTv.setText("Lv  " + contentBean.getLevel());
            holder.mAgeTv.setText("" + contentBean.getAge());
            holder.userOfficial.setVisibility(contentBean.getExpand() > 0 ? View.VISIBLE : View.GONE);
            if (lat <= 0 || lng <= 0 || contentBean.getLastLoginLatitude() <= 0 || contentBean.getLastLoginLongitude() <= 0) {
                holder.mDistanceTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(contentBean.getLastLoginTime()));
            } else {
                holder.mDistanceTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(contentBean.getLastLoginTime()) + "·" + NumberUtils.getDistance(lng, lat, contentBean.getLastLoginLongitude(), contentBean.getLastLoginLatitude()));
            }
//            holder.mStateLogo.setVisibility(contentBean.getState() == 0 ? View.VISIBLE : View.INVISIBLE);
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
            holder.contentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mType.equals("topic")) {
                        ToastUtil.shortShowToast("问答邀请成功!");
                        data.remove(position);
                        notifyDataSetChanged();
                        // 邀请话题回答
                        Tools.setTopic(contentBean.getPhone(), "", topicTitle, topicId);
                    } else {
                        if (friend) {
                            Intent intent = new Intent(context, PersonalPageActivity.class);
                            intent.putExtra("userId", contentBean.getUserId());
                            context.startActivity(intent);
                        } else {
                            ToastUtil.shortShowToast("邀请入群成功!");
                            data.remove(position);
                            notifyDataSetChanged();
                            Tools.groupInvite(contentBean.getPhone(), familyInfo.getHeadUrl(), familyInfo.getGroupName(), familyInfo.getGroupId());
                        }
                    }
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
        @InjectView(R.id.mDetailTv)
        TextView mDetailTv;
        @InjectView(R.id.liveStateLL)
        LinearLayout liveStateLL;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;
        @InjectView(R.id.mStateLogo)
        ImageView mStateLogo;
        @InjectView(R.id.userOfficial)
        ImageView userOfficial;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
