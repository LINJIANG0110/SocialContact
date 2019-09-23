package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.MediaList;
import com.qianyu.communicate.jzvd.JZVideoPlayerStandard;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.TimeUtils;

import com.qianyu.communicate.base.MyBaseAdapter;
import net.neiquan.applibrary.utils.PixelUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class BuyRecordAdapter extends MyBaseAdapter<MediaList, BuyRecordAdapter.ViewHolder> {


    public BuyRecordAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_buy_record, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        holder.mHeadImg.setImageURI("http://www.qqzhi.com/uploadpic/2015-01-16/013806507.jpg");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = PixelUtil.getScreenWidth(context) - PixelUtil.dp2px(context, 20);
        layoutParams.height = layoutParams.width * 9 / 16;
        holder.mHeadImg.setLayoutParams(layoutParams);
        if (data != null && data.size() > 0) {
            final MediaList mediaList = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(mediaList.getMediaPic()) ? "" : mediaList.getMediaPic());
            holder.mTitleName.setText(TextUtils.isEmpty(mediaList.getMediaName()) ? "" : mediaList.getMediaName());
            holder.mPlayCount.setText(NumberUtils.roundInt(mediaList.getTotalPerson()));
            holder.mTimeTv.setText(TimeUtils.getTime(mediaList.getCreateTime()));
            holder.mTotalTime.setText("时长" + TimeUtils.getHourAndMin(mediaList.getTotalTime()));
//            holder.mPlayedTv.setText("已播：" + NumberUtils.roundPercent(mediaList.getPayTime(), mediaList.getTotalTime()));
            holder.playLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpringUtils.springAnim(holder.playLogo);
//                    Intent intent = new Intent(context, JzFullActivity.class);
//                    intent.putExtra("url",mediaList.getMediaPath());
//                    intent.putExtra("name",mediaList.getMediaName());
//                    context.startActivity(intent);
                    JZVideoPlayerStandard.startFullscreen(context, JZVideoPlayerStandard.class,  mediaList.getMediaPath(), mediaList.getMediaName());
                }
            });
            holder.mDeleteLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpringUtils.springAnim(holder.deleteLogo);
                    delWatchHistory(position, mediaList.getMediaId());
                }
            });
        }
    }

    private void delWatchHistory(final int p, final int historyId) {
        new AlertDialog.Builder(context).setTitle("确定删除该视频？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //        Tools.showDialog(context);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).create().show();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mDeleteLL)
        LinearLayout mDeleteLL;
        @InjectView(R.id.deleteLogo)
        ImageView deleteLogo;
        @InjectView(R.id.playLogo)
        ImageView playLogo;
        @InjectView(R.id.mTitleName)
        TextView mTitleName;
        @InjectView(R.id.mPlayCount)
        TextView mPlayCount;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mTotalTime)
        TextView mTotalTime;
        @InjectView(R.id.mPlayedTv)
        TextView mPlayedTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
