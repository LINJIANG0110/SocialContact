package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil;
import com.qianyu.communicate.bukaSdk.entity.DocumentInfo;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.views.SwipeMenuView;

import org.haitao.common.utils.AppLog;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class LessonChosenAdapter extends MyBaseAdapter<DocumentInfo.DataBean, LessonChosenAdapter.ViewHolder> {


    public LessonChosenAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_lesson_chosen, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final DocumentInfo.DataBean docImageEntity = data.get(position);
            String docType = docImageEntity.getFileName()
                    .substring(docImageEntity.getFileName().lastIndexOf(".") + 1).toLowerCase();
            int docIcon;
            switch (docType) {
                case ConstantUtil.FILE_PPT:
                case ConstantUtil.FILE_PPTX:
                    docIcon = R.mipmap.wenjian_ppt;
                    break;
                case ConstantUtil.FILE_DOC:
                case ConstantUtil.FILE_DOCX:
                    docIcon = R.mipmap.wenjian_word;
                    break;
                case ConstantUtil.FILE_XLS:
                case ConstantUtil.FILE_XLSX:
                    docIcon = R.mipmap.wenjian_exel;
                    break;
                case ConstantUtil.FILE_PDF:
                case ConstantUtil.FILE_PDFX:
                    docIcon = R.mipmap.wenjian_pdf;
                    break;
                case ConstantUtil.FILE_PNG:
                case ConstantUtil.FILE_JPG:
                case ConstantUtil.FILE_JPEG:
                    docIcon = 0;
                    break;
                default:
                    docIcon = R.mipmap.yunduan_wendang;
                    break;
            }
            if (docIcon != 0) {
                Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + docIcon);
                holder.mHeadImg.setImageURI(uri);
            } else {
                holder.mHeadImg.setImageURI(docImageEntity.getFileUrl());
            }
            holder.mDocumentName.setText(docImageEntity.getFileName());
            holder.mSelectLogo.setVisibility(docImageEntity.isSelected() ? View.VISIBLE : View.GONE);
            holder.mDocumentSize.setText((NumberUtils.roundInt_(docImageEntity.getFileSize())));
            holder.mDocumentTime.setText(TimeUtils.getTime(docImageEntity.getFileCreateTime()));
            holder.mContentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppLog.e("=============setOnItemClickListener===============");
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setSelected(false);
                    }
                    docImageEntity.setSelected(true);
                    notifyDataSetChanged();
                    onItemSelectDeletListener.onItemSeleDele(docImageEntity);
                }
            });
            holder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fileDelete(position, holder, docImageEntity);
                }
            });
        }
    }

    private void fileDelete(final int position, final ViewHolder holder, DocumentInfo.DataBean docImageEntity) {
    }

    private OnItemSelectDeletListener onItemSelectDeletListener;

    public void setOnItemSelectDeletListener(OnItemSelectDeletListener onItemSelectDeletListener) {
        this.onItemSelectDeletListener = onItemSelectDeletListener;
    }

    public interface OnItemSelectDeletListener {
        void onItemSeleDele(DocumentInfo.DataBean dataBean);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mSwipeMenuView)
        SwipeMenuView mSwipeMenuView;
        @InjectView(R.id.mContentLL)
        LinearLayout mContentLL;
        @InjectView(R.id.mDeleteTv)
        TextView mDeleteTv;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mDocumentName)
        TextView mDocumentName;
        @InjectView(R.id.mDocumentSize)
        TextView mDocumentSize;
        @InjectView(R.id.mDocumentTime)
        TextView mDocumentTime;
        @InjectView(R.id.mSelectLogo)
        ImageView mSelectLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
