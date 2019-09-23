package com.qianyu.communicate.bukaSdk.adapter;

/**
 * @说明: //TODO
 * @作者: hwk
 * @创建日期: 2017/8/30 16:44
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.qianyu.communicate.R;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.PixelUtil;

import java.util.HashMap;
import java.util.List;


public class PhotoChoiceAdapter extends BaseAdapter {
    private Context context;

    private List<String> pictures;
    private HashMap<Integer, Boolean> isChecked;
    private CallBack callBack;

    public PhotoChoiceAdapter(Context context, CallBack callBack, List<String> pictures, HashMap<Integer, Boolean> isChecked) {
        this.pictures = pictures;
        this.context = context;
        this.callBack = callBack;
        this.isChecked = isChecked;
    }

    @Override
    public int getCount() {
        if (null != pictures) {
            return pictures.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {

        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    private int tempPositions = 0;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_pop_choice_photo, null);
            int screenWidth = PixelUtil.getScreenWidth(context) - PixelUtil.dp2px(context, 3) * 2;
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(screenWidth / 3, screenWidth / 3);
            convertView.setLayoutParams(layoutParams);
            viewHolder.iv_photo = convertView.findViewById(R.id.iv_photo);
            viewHolder.checkbox = convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        } else {
            // 取得converHolder附加的对象
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 给组件设置资源
        Glide.with(context.getApplicationContext())
                .load(pictures.get(position))
                .priority(Priority.HIGH)
                .centerCrop()//裁剪正方形
                .placeholder(R.mipmap.wxdl_chushi)
                .error(R.mipmap.wxdl_chushi)
                .into(viewHolder.iv_photo);

//        viewHolder.checkbox.setChecked(isChecked.get(position));
        viewHolder.checkbox.setBackground(context.getResources().getDrawable(isChecked.get(position)?R.drawable.check_true:R.drawable.check_false));
//        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean checked = viewHolder.checkbox.isChecked();
//                AppLog.e("===============checkbox==============="+checked);
////                isChecked.put(position, checked);
////                if (checked) {
////                    for (int i = 0; i < pictures.size(); i++) {
////                        if (i != position) {
////                            isChecked.put(i, false);
////                        }
////                    }
////                }
////                PhotoChoiceAdapter.this.notifyDataSetChanged();
////                callBack.check(checked, pictures.get(position));
//            }
//        });
        viewHolder.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewHolder.checkbox.setChecked(!viewHolder.checkbox.isChecked());
//                boolean checked = !viewHolder.checkbox.isChecked();
                boolean checked=!isChecked.get(position);
                AppLog.e("===============iv_photo==============="+checked);
                isChecked.put(position, checked);
                if (checked) {
                    for (int i = 0; i < pictures.size(); i++) {
                        if (i != position) {
                            isChecked.put(i, false);
                        }
                    }
                }
                PhotoChoiceAdapter.this.notifyDataSetChanged();
                callBack.check(checked, pictures.get(position));
            }
        });

        return convertView;
    }

    class ViewHolder {
        public RelativeLayout mPhotoRootRV;
        public ImageView checkbox;
        public ImageView iv_photo;
    }


    public interface CallBack {
        void check(boolean isChecked, String nativePath);
    }

}