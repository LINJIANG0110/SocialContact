package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.WorkTag;

import com.qianyu.communicate.base.MyBaseAdapter;
import net.neiquan.applibrary.utils.PixelUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class WorkTagAdapter extends MyBaseAdapter<WorkTag, WorkTagAdapter.ViewHolder> {

    public WorkTagAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_work_tag, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            WorkTag workTag = data.get(position);
            if (position == 0) {
                holder.mRootLL.setPadding(PixelUtil.dp2px(context, 20), PixelUtil.dp2px(context, 30), PixelUtil.dp2px(context, 20), PixelUtil.dp2px(context, 10));
            }
            holder.titleTv.setVisibility(TextUtils.equals("其他", workTag.getHeadLine()) ? View.INVISIBLE : View.VISIBLE);
            holder.titleTv.setText(workTag.getHeadLine());
            holder.contentTv.setText(workTag.getProName());
            holder.chosenLogo.setVisibility(workTag.isChosen() ? View.VISIBLE : View.GONE);
            switch (position) {
                case 0:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_green));
                    break;
                case 1:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_blue));
                    break;
                case 2:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_blue));
                    break;
                case 3:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_yellow));
                    break;
                case 4:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_yellow));
                    break;
                case 5:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_red));
                    break;
                case 6:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_red));
                    break;
                case 7:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_gray_green));
                    break;
                case 8:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_gray_green));
                    break;
                case 9:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_gray_green));
                    break;
                case 10:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_purple));
                    break;
                case 11:
                    holder.titleTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_work_pink));
                    break;
            }
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.titleTv)
        TextView titleTv;
        @InjectView(R.id.contentTv)
        TextView contentTv;
        @InjectView(R.id.chosenLogo)
        ImageView chosenLogo;
        @InjectView(R.id.mRootLL)
        LinearLayout mRootLL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
