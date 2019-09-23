package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.FamilyList;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.utils.NumberUtils;

import net.neiquan.applibrary.utils.PixelUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class ChatRoomSuggestAdapter extends MyBaseAdapter<FamilyList.ContentBean, ChatRoomSuggestAdapter.ViewHolder> {

    public ChatRoomSuggestAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_chat_room_suggest, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
//        Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + R.drawable.room_gif);
//        DraweeController draweeController =
//                Fresco.newDraweeControllerBuilder()
//                        .setUri(uri)
//                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
//                        .build();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width =( PixelUtil.getScreenWidth(context)  - PixelUtil.dp2px(context, 40))/ 2;
        params.height = params.width;
        holder.mHeadImg.setLayoutParams(params);
        if (data != null && data.size() > 0) {
            FamilyList.ContentBean familyListBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(familyListBean.getHeadUrl()) ? "" : familyListBean.getHeadUrl());
            holder.mNameTv.setText(TextUtils.isEmpty(familyListBean.getGroupName()) ? "" : familyListBean.getGroupName());
            holder.mContentTv.setText(TextUtils.isEmpty(familyListBean.getGroupName()) ? "" : familyListBean.getGroupName());
            holder.mNumTv.setText(NumberUtils.rounLong(familyListBean.getScore()));
//            holder.mGifLogo.setController(draweeController);
            Glide.with(context).load(R.drawable.room_gif).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.mGifLogo);
//            String familyStatus = familyListBean.getState()+"";
//            if (!TextUtils.isEmpty(familyStatus)) {
//                switch (familyStatus) {
//                    case "1":
//                        holder.mGifLogo.setController(draweeController);
//                        break;
//                    default:
//                        holder.mGifLogo.setImageURI( Uri.parse("res://" + context.getPackageName() + "/" + R.mipmap.yishengbuzai));
//                        break;
//                }
//            }else {
//                holder.mGifLogo.setImageURI( Uri.parse("res://" + context.getPackageName() + "/" + R.mipmap.yishengbuzai));
//            }
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mGifLogo)
        SimpleDraweeView mGifLogo;
        @InjectView(R.id.mNumTv)
        TextView mNumTv;
        @InjectView(R.id.mNameTv)
        TextView mNameTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
