package com.qianyu.communicate.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.neiquan.applibrary.R;
import net.neiquan.applibrary.wight.RefreshLayout;

/**
 * 作者: dyj
 * 时间：2016/3/4.
 */
public abstract class BaseListFragment extends BaseFragment implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {

    protected int pageNum = 0;
    protected static int PAEG_SIZE = 10;
    protected View view;
    protected MyBaseAdapter adapter;
    protected XRecyclerView mRecyclerview;
    RelativeLayout mHeadView;
    protected RefreshLayout mRefeshLy;
    protected ImageView mFloatActionBtn;

    @Override
    public int getRootViewId() {
        return R.layout.base_common_xlistview;
    }

    private void setAdapter() {
        setHeadView(isHaveHead());
        setHeadViews();
        adapter = getAdapter();
        mRecyclerview.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getLineNum() == 0 ? 1 : getLineNum(),
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(layoutManager);
        mRefeshLy.setRetryListener(this);

        mRecyclerview.setLoadingListener(this);
        mRecyclerview.setLoadingMoreEnabled(false);
        mRecyclerview.setPullRefreshEnabled(true);
    }

    protected abstract boolean isHaveHead();

    /**
     * 当返回为true时候设置
     */
    protected abstract void setHeadViews();

    private void setHeadView(boolean isHaveHead) {
        if (!isHaveHead) {
            mHeadView.setVisibility(View.GONE);
        } else {
            mHeadView.setVisibility(View.VISIBLE);
        }
    }


    protected abstract MyBaseAdapter getAdapter();

    protected abstract int getLineNum();


    protected void setTitleTv(int id) {
        TextView tv = rootView.findViewById(R.id.title_tv);
        tv.setText(id);
    }

    protected void setTitleTv(String str) {
        TextView tv = rootView.findViewById(R.id.title_tv);
        tv.setText(str);
    }

    protected void setBackTv(String str) {
        TextView tv = rootView.findViewById(R.id.back_tv);
        tv.setText(str);
    }

    protected TextView getBackTv() {
        return (TextView) rootView.findViewById(R.id.back_tv);
    }

    protected ImageView getBackImg() {
        return (ImageView) rootView.findViewById(R.id.back_img);
    }


    protected void setNextTv(String str) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.VISIBLE);
        tv.setText(str);
        View iv = rootView.findViewById(R.id.next_img);
        iv.setVisibility(View.GONE);
    }

    protected TextView getNextTv() {

        return (TextView) rootView.findViewById(R.id.next_tv);
    }

    protected void setBackGone() {
        View tv = rootView.findViewById(R.id.ly_back);
        tv.setVisibility(View.GONE);
    }


    protected void setBackOnClick(View.OnClickListener onClick) {
        LinearLayout ly_back = rootView.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(onClick);
        View iv = rootView.findViewById(R.id.back_img);
        iv.setOnClickListener(onClick);
    }


    @SuppressLint("NewApi")
    protected void setNextTvBG(Drawable drawable) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setBackground(drawable);
    }

    protected void setNextImage(int id) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.GONE);
        ImageView iv = rootView.findViewById(R.id.next_img);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(id);
    }

    protected ImageView getNextImgage() {
        return (ImageView) rootView.findViewById(R.id.next_img);
    }

    protected void setNextOnClick(View.OnClickListener onClick) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setOnClickListener(onClick);
        View iv = rootView.findViewById(R.id.next_img);
        iv.setOnClickListener(onClick);
    }

    protected void setNextGone() {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.GONE);
        ImageView iv = rootView.findViewById(R.id.next_img);
        iv.setVisibility(View.GONE);
    }

    public void setmFloatActionBtn(int id) {
        mFloatActionBtn.setVisibility(View.VISIBLE);
        mFloatActionBtn.setImageResource(id);
    }

    public void setFloatActionBtnOnClick(View.OnClickListener onClick) {
        mFloatActionBtn.setOnClickListener(onClick);
    }


    @Override
    public void setViews() {
        mRecyclerview = rootView.findViewById(R.id.recyclerview);
        mHeadView = rootView.findViewById(R.id.head_view);
        mRefeshLy = rootView.findViewById(R.id.refesh_ly);
        mFloatActionBtn = rootView.findViewById(R.id.mFloatActionBtn);
        setAdapter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerview = null;
        adapter = null;
    }
}
