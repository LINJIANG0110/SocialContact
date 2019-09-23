package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.facebook.drawee.view.SimpleDraweeView;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.SubScription;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class CircleBgAdapter extends MyBaseAdapter<SubScription.SubscriptionsBean, CircleBgAdapter.ViewHolder> {


    public CircleBgAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_personal_concerns, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
//        Uri uri =Uri.parse("res://"+context.getPackageName()+"/" + R.drawable.room_gif);
//        DraweeController draweeController =
//                Fresco.newDraweeControllerBuilder()
//                        .setUri(uri)
//                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
//                        .build();
//        holder.mGifLogo.setController(draweeController);
        holder.mGifLogo.setImageURI( Uri.parse("res://" + context.getPackageName() + "/" + R.mipmap.yishengbuzai));
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.width= PixelUtil.getScreenWidth(context)/2-PixelUtil.dp2px(context,10);
//        params.height= PixelUtil.getScreenWidth(context)/2;
//        holder.mHeadImg.setLayoutParams(params);
        if (data != null && data.size() > 0) {
            SubScription.SubscriptionsBean recentlyListBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(recentlyListBean.getHeadPath()) ? "" : recentlyListBean.getHeadPath());
            holder.mNameTv.setText(TextUtils.isEmpty(recentlyListBean.getNickName())?"":recentlyListBean.getNickName());
            holder.mContentTv.setText(TextUtils.isEmpty(recentlyListBean.getFamilyName()) ? "" : recentlyListBean.getFamilyName());
            String freeType = recentlyListBean.getFreeType();
            if (!TextUtils.isEmpty(freeType)) {
                switch (freeType) {
                    case "1":
                        holder.mMoneyFL.setVisibility(View.GONE);
                        break;
                    default:
//                        holder.mMoneyFL.setVisibility(View.VISIBLE);
//                        holder.mMoneyTv.setText("￥" + recentlyListBean.getPrice());
                        break;
                }
            } else {
                holder.mMoneyFL.setVisibility(View.GONE);
            }
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mGifLogo)
        SimpleDraweeView mGifLogo;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mNameTv)
        TextView mNameTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mMoneyFL)
        FrameLayout mMoneyFL;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
