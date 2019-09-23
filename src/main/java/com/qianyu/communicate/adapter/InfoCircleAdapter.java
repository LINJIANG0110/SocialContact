package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.UserInfo;

import net.neiquan.applibrary.utils.PixelUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class InfoCircleAdapter extends MyBaseAdapter<String, InfoCircleAdapter.ViewHolder> {


    public InfoCircleAdapter(Activity context, List data) {
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = (PixelUtil.getScreenWidth(context)-PixelUtil.dp2px(context, 150))/4;
        params.height = params.width;
        params.topMargin=params.leftMargin=params.rightMargin=params.bottomMargin= PixelUtil.dp2px(context, 2);
        holder.mHeadImg.setLayoutParams(params);
        if (data != null&&data.size()>0) {
            String userCircleBean = data.get(position);
            holder.mHeadImg.setVisibility(TextUtils.isEmpty(userCircleBean)?View.GONE:View.VISIBLE);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(userCircleBean)?"":userCircleBean);
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
