package com.qianyu.communicate.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FuBowAdapter;
import com.qianyu.communicate.adapter.MallFuBowAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.InComeList;
import com.qianyu.communicate.entity.MallBill;
import com.qianyu.communicate.entity.MallBillList;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class MallFuBaoFragment extends BaseListFragment {

    private MallBill mallBill = new MallBill();
    private MallFuBowAdapter mallFuBowAdapter;
    private TextView mContentTv;
    private TextView mCoinTv;
    private TextView mFuBaoTv;
    private TextView mDiamondTv;

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {
        EventBus.getDefault().register(this);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.MALL_BILL) {
            mallBill = ((MallBill) event.getMessage());
            onRefresh();
        }
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        if (getArguments() != null) {
            mallBill = (MallBill) getArguments().getSerializable("mallBill");
        }
        mallFuBowAdapter = new MallFuBowAdapter(getActivity(), null);
        mallFuBowAdapter.setType(mallBill.getType());
        return mallFuBowAdapter;
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mall_record, null);
        mContentTv = ((TextView) view.findViewById(R.id.mContentTv));
        mContentTv.setText(mallBill.getType() == 1 ? "总收入" : "总支出");
        mCoinTv = ((TextView) view.findViewById(R.id.mCoinTv));
        mFuBaoTv = ((TextView) view.findViewById(R.id.mFuBaoTv));
        mDiamondTv = ((TextView) view.findViewById(R.id.mDiamondTv));
        mRecyclerview.addHeaderView(view);
        loadDatas();
    }

    private void loadDatas() {
        NetUtils.getInstance().findOrder(mallBill.getType(), mallBill.getUserId(), mallBill.getStartTime(), mallBill.getEndTime(), pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                MallBillList mallBillList = model.getModel();
                if (mallBillList != null && mRecyclerview != null && mRefeshLy != null) {
                    MallBillList.SumBean sum = mallBillList.getSum();
                    if (sum != null) {
                        mCoinTv.setText(NumberUtils.rounLong(sum.getGold()));
                        mFuBaoTv.setText(NumberUtils.rounLong(sum.getFubao()));
                        mDiamondTv.setText(NumberUtils.rounLong(sum.getDiamond()));
                    }
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    List<MallBillList.PageListBean> list = mallBillList.getPageList();
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
                ToastUtil.shortShowToast(msg);
                if (mRecyclerview != null && mRefeshLy != null) {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    mRefeshLy.showFailView();
                }
            }
        }, MallBillList.class);
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
