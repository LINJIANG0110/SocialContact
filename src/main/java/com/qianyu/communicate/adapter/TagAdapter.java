package com.qianyu.communicate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.ImpressLabel;

import net.neiquan.applibrary.flowtag.OnInitSelectedPosition;

import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private Context mContext;
    public List<T> mDataList;
    private int count = 0;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_impress_tag, null);
        final TextView textView = view.findViewById(R.id.mTagTv);
        if (mDataList != null && mDataList.size() > 0) {
            final ImpressLabel impressLabel = (ImpressLabel) mDataList.get(position);
            textView.setText(TextUtils.isEmpty(impressLabel.getLabelName()) ? "" : impressLabel.getLabelName());
            textView.setTextColor(mContext.getResources().getColor(impressLabel.isSelected() ? R.color.app_color : R.color.text_black));
//            textView.setBackground(mContext.getResources().getDrawable(impressLabel.isSelected() ? R.drawable.shape_cornor_impress_corlor : R.drawable.shape_cornor_gray_label));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = 0;
                    for (int i = 0; i < mDataList.size(); i++) {
                        ImpressLabel adeptLabel = (ImpressLabel) mDataList.get(i);
                        if (adeptLabel.isSelected()) {
                            count++;
                        }
                    }
                    if (count >= 10) {
                        if (impressLabel.isSelected()) {
                            impressLabel.setSelected(!impressLabel.isSelected());
                            textView.setTextColor(mContext.getResources().getColor(impressLabel.isSelected() ? R.color.app_color : R.color.text_black));
//                            textView.setBackground(mContext.getResources().getDrawable(impressLabel.isSelected() ? R.drawable.shape_cornor_impress_corlor : R.drawable.shape_cornor_gray_label));
                        } else {
                            ToastUtil.shortShowToast("最多只能添加十个标签哦...");
                        }
                        return;
                    }
                    impressLabel.setSelected(!impressLabel.isSelected());
                    textView.setTextColor(mContext.getResources().getColor(impressLabel.isSelected() ? R.color.app_color : R.color.text_black));
//                    textView.setBackground(mContext.getResources().getDrawable(impressLabel.isSelected() ? R.drawable.shape_cornor_impress_corlor : R.drawable.shape_cornor_gray_label));
                }
            });
        }
        return view;
    }


    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        return position % 2 == 0;
    }
}
