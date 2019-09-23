package com.qianyu.communicate.bukaSdk.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.bukaSdk.bkconstant.ConstantUtil;
import com.qianyu.communicate.bukaSdk.entity.DocImageEntity;

import java.util.List;


/**
 * @说明: //文件夹列表
 * @作者: hwk
 * @创建日期: 2017/8/29 19:01
 */

public class FolderAdapter extends BaseAdapter {

    private List<DocImageEntity> mDocumentInfos;
    private Context context;

    public FolderAdapter(Context context, List<DocImageEntity> documentInfos) {
        this.context = context;
        this.mDocumentInfos = documentInfos;
    }

    public void setDocumentInfos(List<DocImageEntity> documentInfos) {
        this.mDocumentInfos = documentInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mDocumentInfos == null || mDocumentInfos.size() == 0) {
            return 0;
        } else {
            return mDocumentInfos.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return mDocumentInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_pop_folder, null);
            holder = new ViewHolder();
            holder.iv_type = convertView.findViewById(R.id.iv_type);
            holder.tv_title = convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        String docType = mDocumentInfos.get(position).getDocument_library_file_name()
//                .substring(mDocumentInfos.get(position).getDocument_library_file_name().lastIndexOf(".") + 1).toLowerCase();
        int docIcon;
        switch (mDocumentInfos.get(position).getType()) {
            case ConstantUtil.FILE_PPT:
                docIcon = R.mipmap.yunduan_ppt;
                break;
            case ConstantUtil.FILE_DOC:
                docIcon = R.mipmap.yunduan_wendang;
                break;
            case ConstantUtil.FILE_XLS:
                docIcon = R.mipmap.yunduan_wendang;
                break;
            case ConstantUtil.FILE_PDF:
                docIcon = R.mipmap.yunduan_wendang;
                break;
            case ConstantUtil.FILE_PNG:
                docIcon = R.mipmap.liaotianshi_tupian;
                break;
            default:
                docIcon = R.mipmap.liaotianshi_tupian;
                break;
        }
        holder.iv_type.setBackgroundResource(docIcon);
        holder.tv_title.setText(mDocumentInfos.get(position).getFileName());
        return convertView;
    }


    public class ViewHolder {
        ImageView iv_type;
        TextView tv_title;
    }
}
