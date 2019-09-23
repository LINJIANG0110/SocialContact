package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.qianyu.communicate.image.ImagePreviewActivity;

import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.MyNineGridLayout;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class MyCircleAdapter extends MyBaseAdapter<CircleList.ListBean.ContentBean, MyCircleAdapter.ViewHolder> {


    public MyCircleAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_my_circle, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final CircleList.ListBean.ContentBean myCircle = data.get(position);
            holder.mContentTv.setText(TextUtils.isEmpty(myCircle.getTitle()) ? "" : myCircle.getTitle());
            List<String> mUrls = new ArrayList<>();
            if (!TextUtils.isEmpty(myCircle.getFileItemUrl())) {
                String[] split = myCircle.getFileItemUrl().split(",");
                for (int i = 0; i < split.length; i++) {
                    mUrls.add(split[i]);
                }
            }
            holder.layoutNineGrid.setUrlList(mUrls);
            holder.layoutNineGrid.setOnImgClickListener(new MyNineGridLayout.OnImgClickListener() {
                @Override
                public void onClick(int p, String url, List<String> urlList) {
                    final ArrayList<ImageItem> mImageList = new ArrayList<>();
                    mImageList.clear();
                    if (urlList != null && urlList.size() > 0) {
                        for (int i = 0; i < urlList.size(); i++) {
                            ImageItem imageItem = new ImageItem();
                            imageItem.setPath(urlList.get(i));
                            mImageList.add(imageItem);
                        }
                        Intent intent = new Intent();
                        intent.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, p);
                        intent.setClass(context, ImagePreviewActivity.class);
                        intent.putParcelableArrayListExtra(ImagePreviewActivity.IMAGEURL, mImageList);
                        context.startActivity(intent);
                    }
                }
            });
            String day = TimeUtils.getDayByTimeStamp(myCircle.getCreateTime()) + "";
            String month = TimeUtils.getMonthByTimeStamp(myCircle.getCreateTime()) + "";
            Spannable sp = new SpannableString(day + "" + month + "月");
            sp.setSpan(new AbsoluteSizeSpan(20, true), 0, day.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sp.setSpan(new AbsoluteSizeSpan(12, true), day.length(), day.length() + month.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.mTimeTv.setText(sp);
            holder.mPraiseTv.setText(NumberUtils.roundInt(myCircle.getFabulous()));
            holder.mCommentTv.setText(NumberUtils.roundInt(myCircle.getComment()));
            holder.praiseLogo.setImageResource(1 == myCircle.getIsClick() ? R.mipmap.dongtai_dianzan_se : R.mipmap.dongtai_dianzan);
//            holder.mCommentLL.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    SpringUtils.springAnim(holder.commentLogo);
//                }
//            });
            holder.mPraiseLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpringUtils.springAnim(holder.praiseLogo);
                    praise(myCircle, holder.mPraiseTv, holder.praiseLogo);
                }
            });
        }
    }

    private void praise(final CircleList.ListBean.ContentBean circleList, final TextView mShareTv, final ImageView praiseLogo) {
        final User user = MyApplication.getInstance().user;
        if (user != null) {
            NetUtils.getInstance().commentPraise(circleList.getCircleId(), user.getUserId(), circleList.getUserId(), 1, "", new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    ToastUtil.shortShowToast(msg);
                    circleList.setIsClick(circleList.getIsClick() == 0 ? 1 : 0);
                    if (circleList.getIsClick() == 0) {
                        circleList.setFabulous(circleList.getFabulous() - 1);
                    } else {
                        circleList.setFabulous(circleList.getFabulous() + 1);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onFail(String code, String msg, String result) {
                    ToastUtil.shortShowToast(msg);
                }
            }, null);
        } else {
            ToastUtil.shortShowToast("请先登录...");
            context.startActivity(new Intent(context, RegistActivity.class));
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.layout_nine_grid)
        MyNineGridLayout layoutNineGrid;
        @InjectView(R.id.mPraiseTv)
        TextView mPraiseTv;
        @InjectView(R.id.mPraiseLL)
        LinearLayout mPraiseLL;
        @InjectView(R.id.mCommentTv)
        TextView mCommentTv;
        @InjectView(R.id.mCommentLL)
        LinearLayout mCommentLL;
        @InjectView(R.id.mRootView)
        LinearLayout mRootView;
        @InjectView(R.id.praiseLogo)
        ImageView praiseLogo;
        @InjectView(R.id.commentLogo)
        ImageView commentLogo;
        @InjectView(R.id.mContentLL)
        LinearLayout mContentLL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
