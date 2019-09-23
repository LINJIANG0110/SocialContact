package com.qianyu.communicate.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.GiftList;

import net.neiquan.applibrary.utils.ImageUtil;

import java.util.List;

/**
 * Created by ZXM on 2016/9/12.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<GiftList.ContentBean> mDatas;
    private LayoutInflater inflater;
    private Context mContext;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 9;

    public GridViewAdapter(Context context, List<GiftList.ContentBean> mDatas, int curIndex) {
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.mContext = context;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);
    }

    @Override
    public GiftList.ContentBean getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = convertView.findViewById(R.id.textView);
            viewHolder.iv = convertView.findViewById(R.id.imageView);
            viewHolder.costLogo = convertView.findViewById(R.id.costLogo);
            viewHolder.cv = convertView.findViewById(R.id.mMoneyTv);
            viewHolder.giftExprience = convertView.findViewById(R.id.giftExprience);
            viewHolder.giftBeautfyLL = convertView.findViewById(R.id.giftBeautfyLL);
            viewHolder.giftBeautfyAddLL = convertView.findViewById(R.id.giftBeautfyAddLL);
            viewHolder.giftBeautfy = convertView.findViewById(R.id.giftBeautfy);
            viewHolder.giftBeautfyAdd = convertView.findViewById(R.id.giftBeautfyAdd);
            viewHolder.item_layout = convertView.findViewById(R.id.item_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        GiftList.ContentBean giftModel = getItem(position);
        viewHolder.tv.setText(giftModel.getGiftName() + "");
        viewHolder.cv.setText(giftModel.getGiftPrice() + "");
//        ImageUtil.loadPicNet(giftModel.getGiftUrl(), viewHolder.iv);
        Uri uri = Uri.parse(TextUtils.isEmpty(giftModel.getGiftUrl()) ? "" : giftModel.getGiftUrl());
        final DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        viewHolder.iv.setController(draweeController);
        viewHolder.costLogo.setImageResource(giftModel.getGiftType() == 1 ? R.mipmap.wallet1 : (giftModel.getGiftType() == 2 ? R.mipmap.coin1 : R.mipmap.diamond1));
        viewHolder.giftBeautfyLL.setVisibility((giftModel.getGiftType() == 0 || giftModel.getGiftType() == 3) ? View.VISIBLE : View.INVISIBLE);
        viewHolder.giftBeautfyAddLL.setVisibility((giftModel.getGiftType() == 0 || giftModel.getGiftType() == 3) ? View.VISIBLE : View.INVISIBLE);
        viewHolder.giftExprience.setText("+" + giftModel.getAcceptExperience());
        viewHolder.giftBeautfy.setText("+" + giftModel.getAcceptCharm());
        viewHolder.giftBeautfyAdd.setText("+" + giftModel.getAcceptCharmSpecial() * 100 + "%");
        if (giftModel.isSelected()) {//被选中
            viewHolder.item_layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_wrap_line_app_color));
        } else {
            viewHolder.item_layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_wrap_line_gray));
        }
        return convertView;
    }


    class ViewHolder {
        public FrameLayout item_layout;
        public TextView tv;
        public TextView cv;
        public SimpleDraweeView iv;
        public ImageView costLogo;
        public TextView giftExprience;
        public LinearLayout giftBeautfyLL;
        public LinearLayout giftBeautfyAddLL;
        public TextView giftBeautfy;
        public TextView giftBeautfyAdd;
    }

}