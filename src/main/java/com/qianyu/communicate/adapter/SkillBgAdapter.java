package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.UserInfo;
import com.facebook.drawee.view.SimpleDraweeView;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.hyphenate.easeui.widget.SkillLevelView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class SkillBgAdapter extends MyBaseAdapter<UserInfo.SkillMapBean, SkillBgAdapter.ViewHolder> {


    public SkillBgAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_skill_bg, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.width = PixelUtil.dp2px(context, 60);
//        params.height = params.width;
//        params.topMargin=params.leftMargin=params.rightMargin=params.bottomMargin= PixelUtil.dp2px(context, 2);
//        holder.mHeadImg.setLayoutParams(params);
        if (data != null&&data.size()>0) {
            UserInfo.SkillMapBean skillMapBean = data.get(position);
//            Uri uri = Uri.parse(TextUtils.isEmpty(skillMapBean.getSkillPicPath())?"":skillMapBean.getSkillPicPath());
//            DraweeController draweeController =
//                    Fresco.newDraweeControllerBuilder()
//                            .setUri(uri)
//                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
//                            .build();
//            holder.mHeadImg.setController(draweeController);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(skillMapBean.getStaticPath())?"":skillMapBean.getStaticPath());
            holder.mSkillView.setLevelView(skillMapBean.getLevel());
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mSkillView)
        SkillLevelView mSkillView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
