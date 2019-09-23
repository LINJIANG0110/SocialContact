package com.qianyu.communicate.views;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/11/8 0008.
 * 用于recylerview 的item懒加载
 */

public class MyBaseQuickAdapter<User> extends BaseQuickAdapter<User, BaseViewHolder> {

    public MyBaseQuickAdapter() {
        super(0);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

    }
}
