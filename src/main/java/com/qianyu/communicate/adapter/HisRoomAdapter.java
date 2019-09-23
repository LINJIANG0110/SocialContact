package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.SubScription;
import com.qianyu.communicate.entity.User;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class HisRoomAdapter extends MyBaseAdapter<SubScription.SubscriptionsBean, HisRoomAdapter.ViewHolder> {

    public HisRoomAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_his_room, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        //        Uri uri = Uri.parse("http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=902f9e35c5cec3fd8b6baf71e3b8f809/2e2eb9389b504fc2fc5fd3fce3dde71191ef6dc2.jpg");
        Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + R.drawable.chat_gif);
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        holder.mGifLogo.setController(draweeController);

        if (data != null && data.size() > 0) {
            final SubScription.SubscriptionsBean hostListVOListBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(hostListVOListBean.getHeadPath()) ? "" : hostListVOListBean.getHeadPath());
            holder.mNameTv.setText(TextUtils.isEmpty(hostListVOListBean.getNickName()) ? "" : hostListVOListBean.getNickName());
            holder.mContentTv.setText(TextUtils.isEmpty(hostListVOListBean.getFamilyName()) ? "" : hostListVOListBean.getFamilyName());
            if (!TextUtils.isEmpty(hostListVOListBean.getJobTitle())) {
                holder.mLevelTv.setVisibility(View.VISIBLE);
                holder.mLevelTv.setText(hostListVOListBean.getJobTitle());
            } else {
                holder.mLevelTv.setVisibility(View.GONE);
            }
            String freeType = hostListVOListBean.getFreeType();
            if (!TextUtils.isEmpty(freeType)) {
                switch (freeType) {
                    case "1":
                        holder.mMoneyTv.setText("免费");
                        break;
                    default:
                        holder.mMoneyTv.setText("￥" + hostListVOListBean.getPrice());
                        break;
                }
            } else {
                holder.mMoneyTv.setText("免费");
            }
//            String liveStatus = hostListVOListBean.getLiveStatus();
//            if (!TextUtils.isEmpty(liveStatus)) {
//                switch (liveStatus) {
//                    case "1":
//                        holder.liveStateLL.setVisibility(View.VISIBLE);
//                        holder.mGifLogo.setController(draweeController);
//                        break;
//                    case "2":
//                        holder.liveStateLL.setVisibility(View.GONE);
//                        break;
//                }
//            } else {
//                holder.liveStateLL.setVisibility(View.GONE);
//            }
//            holder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    holder.mSwipeMenuView.smoothClose();
//                    cancelConcern(position, hostListVOListBean.getId());
//                }
//            });
//            holder.contentLL.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, PersonalPageActivity.class);
//                    intent.putExtra("userId",hostListVOListBean.getUserId());
//                    context.startActivity(intent);
//                }
//            });
        }
    }


    private void cancelConcern(final int position, int id) {
//        Tools.showDialog(context);
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            context.startActivity(new Intent(context, RegistActivity.class));
            return;
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mGifLogo)
        SimpleDraweeView mGifLogo;
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
        @InjectView(R.id.mLevelTv)
        TextView mLevelTv;
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
