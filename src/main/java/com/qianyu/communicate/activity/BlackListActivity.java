package com.qianyu.communicate.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.BlackListAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.BlackList;
import com.qianyu.communicate.entity.InComeList;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class BlackListActivity extends BaseListActivity {
    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("拉黑列表");
//        View headView = LayoutInflater.from(this).inflate(R.layout.layout_head_buy_record, null);
//        TextView contentTv = headView.findViewById(R.id.mContentTv);
//        contentTv.setText("被加入黑名单的用户将无法给你发私信、评论、关注你，你将无法收到Ta@你的消息提示");
//        mRecyclerview.addHeaderView(headView);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new BlackListAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        loadDatas();
    }

    private void loadDatas() {
        Tools.showDialog(this);
        NetUtils.getInstance().blackList( pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                BlackList blackList = model.getModel();
                Tools.dismissWaitDialog();
                if (mRecyclerview != null && mRefeshLy != null) {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    List<BlackList.ContentBean> list = blackList.getContent();
                    if (pageNum == 0) {
                        adapter.clear();
                    }
                    adapter.append(list);
                    if (list == null || list.size() < 10) {
                        if (pageNum == 0 && (list == null || list.size() == 0)) {
                            mRefeshLy.showEmptyView();
                        }
                        mRecyclerview.setLoadingMoreEnabled(false);
                    } else {
                        mRecyclerview.setLoadingMoreEnabled(true);
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
        }, BlackList.class);
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
}
