package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.MyMBaseAdapter;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.entity.TopicBean;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.image.ImagePreviewActivity;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JavaDog on 2019-9-5.
 */

public class TopicAdapter extends MyMBaseAdapter<TopicBean.TopicDataBean, TopicAdapter.ViewHolder> {

    public TopicAdapter(Activity context, List data) {
        super(context, data);
    }

    private enum ItemType {
        Typezero;
    }

    @Override
    public int getItemViewType(int position) {
        int mType = 1;
        if (mType == 1) {
            return TopicAdapter.ItemType.Typezero.ordinal();
        }
        return -1;
    }

    @Override
    protected TopicAdapter.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        //根据不同的viewType，创建并返回影响的ViewHolder
        if (viewType == TopicAdapter.ItemType.Typezero.ordinal()) {
            return new TopicAdapter.ViewHolder(mInflater.inflate(R.layout.item_topic, null));// 图片数量0的itemtype
        } else {
            return null;
        }
    }

    @Override
    protected void onBindViewHolder_(final TopicAdapter.ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final TopicBean.TopicDataBean topicBean = data.get(position);
            if (TextUtils.isEmpty(topicBean.getPicPath())) {
                holder.ivFengmian.setVisibility(View.GONE);
                holder.tvTitleNoimg.setVisibility(View.VISIBLE);
                holder.tvHotnum.setVisibility(View.GONE);
            } else {
                holder.ivFengmian.setVisibility(View.VISIBLE);
                holder.tvTitleNoimg.setVisibility(View.GONE);
                holder.tvHotnum.setVisibility(View.VISIBLE);
            }
            holder.ivFengmian.setImageURI(TextUtils.isEmpty(topicBean.getPicPath()) ? "" : topicBean.getPicPath());
            holder.tvTitle.setText(topicBean.getTitle());
            holder.tvTitleNoimg.setText(topicBean.getHotNum() + "热度");
            holder.tvHotnum.setText(topicBean.getHotNum() + "热度");
//            holder.mCommentLL.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    SpringUtils.springAnim(holder.praiseLogo);
//                    praise(circleList, holder.mShareTv, holder.praiseLogo);
//                }
//            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_hotnum)
        TextView tvHotnum;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.iv_fengmian)
        SimpleDraweeView ivFengmian;
        @InjectView(R.id.tv_hotnum_noimg)
        TextView tvTitleNoimg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
