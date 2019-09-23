package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.SkillList;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.applibrary.utils.PixelUtil;

import org.haitao.common.utils.AppLog;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class SkillAdapter extends MyBaseAdapter<SkillList, SkillAdapter.ViewHolder> {


    public SkillAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_skill, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.width = PixelUtil.getScreenWidth(context) / 3 - PixelUtil.dp2px(context, 30);
//        params.height = PixelUtil.getScreenWidth(context) / 3 - holder.mSkillContent.getHeight() - PixelUtil.dp2px(context, 30);
//        params.gravity = Gravity.CENTER;
//        holder.mSkillLogo.setLayoutParams(params);
        if (data != null && data.size() > 0) {
            SkillList skillList = data.get(position);
            if (skillList.isSelected()) {
                Uri uri = Uri.parse(TextUtils.isEmpty(skillList.getSkillPicPath()) ? "" : skillList.getSkillPicPath());
                final DraweeController draweeController =
                        Fresco.newDraweeControllerBuilder()
                                .setUri(uri)
                                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                                .build();
                holder.mSkillLogo.setController(draweeController);
            } else {
                holder.mSkillLogo.setImageURI(TextUtils.isEmpty(skillList.getStaticPath()) ? "" : skillList.getStaticPath());
            }
            holder.mSkillContent.setText(TextUtils.isEmpty(skillList.getSkillName()) ? "" : skillList.getSkillName());
            holder.mRootLL.setBackground(context.getResources().getDrawable(skillList.isSelected() ? R.drawable.shape_cornor_line : R.drawable.shape_cornor_white));
            holder.mRootFL.setBackgroundColor(context.getResources().getColor(skillList.isSelected() ? R.color.colorRed : R.color.little_gray));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.mSkillLogo)
        SimpleDraweeView mSkillLogo;
        @InjectView(R.id.mRootFL)
        FrameLayout mRootFL;
        @InjectView(R.id.mSkillContent)
        TextView mSkillContent;
        @InjectView(R.id.mRootLL)
        LinearLayout mRootLL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
