package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.GiftFuBowAdapter;

import com.qianyu.communicate.adapter.MyGiftFuBowAdapter;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.GiftList;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class MyGiftActivity extends BaseListActivity {
    protected int PAEG_SIZE = 15;
    private long userId;
    private TextView mContentTv;

    @Override
    protected boolean isHaveHead() {
        setSystemBarTint_();
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("礼物");
        mHeadView.setBackground(getResources().getDrawable(R.drawable.toolbar_bg_bmp));
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_head_buy_record, null);
        mContentTv = headView.findViewById(R.id.mContentTv);
        mRecyclerview.addHeaderView(headView);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new MyGiftFuBowAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 3;
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            userId = getIntent().getLongExtra("userId", 0);
            loadDatas();
        }
    }

    private void loadDatas() {
        Tools.showDialog(this);
        NetUtils.getInstance().myGift(userId, pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Tools.dismissWaitDialog();
                GiftList giftList = (GiftList) model.getModel();
                if (giftList != null) {
                    mContentTv.setText("共"+giftList.getTotalElements()+"个礼物");
                    List<GiftList.ContentBean> list = giftList.getContent();
                    if (mRecyclerview != null && mRefeshLy != null && adapter != null) {
                        mRecyclerview.loadMoreComplete();
                        mRecyclerview.refreshComplete();
                        mRefeshLy.hideAll();
                        if (pageNum == 0) {
                            adapter.clear();
                        }
                        adapter.append(list);
                        if (list == null || list.size() < PAEG_SIZE) {
                            if (pageNum == 0 && (list == null || list.size() == 0)) {
                                mRefeshLy.showEmptyView();
                            }
                            mRecyclerview.setLoadingMoreEnabled(false);
                        } else {
                            mRecyclerview.setLoadingMoreEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
                if (mRecyclerview != null && mRefeshLy != null) {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    mRefeshLy.showFailView();
                }
            }
        }, GiftList.class);
    }


    @Override
    public void onRetryClick() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        loadDatas();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
