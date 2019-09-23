package com.qianyu.communicate.fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.MallAdapter;

import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;
import net.neiquan.applibrary.wight.CommonPopupWindow;

import java.util.List;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class HotToolFragment extends BaseListFragment {
    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {
        mRecyclerview.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_head_mall, null));
        mRecyclerview.setBackgroundColor(getResources().getColor(R.color.app_color));
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new MallAdapter(getActivity(), null);
    }

    @Override
    protected int getLineNum() {
        return 3;
    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                showGoodPopWindow();
            }
        });
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    private void showGoodPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
                //设置PopupWindow布局
                .setView(R.layout.good_num_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        TextView cancelTv = view.findViewById(R.id.cancelTv);
                        TextView sureTv = view.findViewById(R.id.sureTv);
                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        sureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mRecyclerview, Gravity.CENTER, 0, 0);
    }
}
