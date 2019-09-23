package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qianyu.communicate.R;
import com.facebook.drawee.view.SimpleDraweeView;

import com.qianyu.communicate.base.MyBaseAdapter;
import net.neiquan.applibrary.utils.PixelUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class PublishImgAdapter extends MyBaseAdapter<String, PublishImgAdapter.ViewHolder> {


    public PublishImgAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_img_publish, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = (PixelUtil.getScreenWidth(context) - PixelUtil.dp2px(context, 40)) / 3;
        params.height = params.width;
        params.topMargin = params.leftMargin = params.rightMargin = params.bottomMargin = PixelUtil.dp2px(context, 5);
        holder.mHeadImg.setLayoutParams(params);
        if (data != null && data.size() > 0) {
            String imgPath = data.get(position);
            holder.mHeadImg.setImageURI(imgPath);
            if (position == data.size()-1) {
                Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + R.mipmap.renzheng_zhaopiandi);
                holder.mHeadImg.setImageURI(uri);
            }
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
