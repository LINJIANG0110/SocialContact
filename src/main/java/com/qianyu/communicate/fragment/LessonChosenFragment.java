package com.qianyu.communicate.fragment;

import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.LessonChosenAdapter;

import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class LessonChosenFragment extends BaseListFragment {
    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {
        setmFloatActionBtn(R.mipmap.dongtai_dingduan);
        setFloatActionBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerview.scrollToPosition(0);
            }
        });
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new LessonChosenAdapter(getActivity(),null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {

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
}
