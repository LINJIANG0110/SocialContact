package com.qianyu.communicate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;

import net.neiquan.applibrary.bean.SortModel;

import java.util.List;

/**
 * Created by JavaDog on 2019-4-16.
 */

public class ContactListAdapter extends BaseAdapter {
    private List<SortModel> mList = null;
    private Context mContext;

    public ContactListAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup arg2) {
        ContactListAdapter.ViewHolder viewHolder = null;
        final SortModel mContent = mList.get(position);
        if (view == null) {
            viewHolder = new ContactListAdapter.ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_contact_list, null);
            viewHolder.mTvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.mTvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.mAddTv = (TextView) view.findViewById(R.id.mAddTv);
            viewHolder.mLayout = (RelativeLayout) view.findViewById(R.id.layout);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ContactListAdapter.ViewHolder) view.getTag();
        }
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.mTvLetter.setVisibility(View.VISIBLE);
            viewHolder.mTvLetter.setText(mContent.getSortLetters());
        } else {
            viewHolder.mTvLetter.setVisibility(View.GONE);
        }
        viewHolder.mTvTitle.setText(this.mList.get(position).getName());
//        viewHolder.mLayout.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View arg0, MotionEvent arg1) {
//                // TODO Auto-generated method stub
//                return false;
//            }
//        });
        return view;
    }

    final static class ViewHolder {
        TextView mTvLetter;
        TextView mTvTitle;
        TextView mAddTv;
        RelativeLayout mLayout;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mList.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
//	@Override
//	public Object[] getSections() {
//		return null;
//	}

    /**
     * listview点击事件接口
     *
     * @author Alan
     */
    public interface ListItemClickHelp {
        void onClick(View item, View widget, int position, int which);
    }
}
