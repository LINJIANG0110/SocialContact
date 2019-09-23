package com.qianyu.communicate.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FuBowAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.InComeList;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;

import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;
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

public class FuBowFragment extends BaseListFragment {

    private int type;
    private ImageView mTotalLogo;
    private TextView totalNum;
    private int type1;

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
        if (event.getState() == EventBuss.INCOME_LIST) {
//            type = ((int) event.getMessage());
//            switch (type) {
//                case 1:
//                    totlaTv.setText("总收入");
//                    break;
//                case 2:
//                    totlaTv.setText("总支出");
//                    break;
//            }
//            onRefresh();
        }
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
            type1 = getArguments().getInt("type1");
        }
        return new FuBowAdapter(getActivity(), null, type);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_bill_head, null);
        mRecyclerview.addHeaderView(headView);
        mTotalLogo = headView.findViewById(R.id.mTotalLogo);
        totalNum = headView.findViewById(R.id.totalIncomeNum);
        mTotalLogo.setImageResource(type1 == 1 ? R.mipmap.wallet1 : (type1 == 2 ? R.mipmap.diamond1 : R.mipmap.coin1));
        loadDatas();
        initRemainMoney();
    }

    private void initRemainMoney() {
        if (MyApplication.getInstance().isLogin()) {
            User user = MyApplication.getInstance().user;
            NetUtils.getInstance().mineInfo(user.getUserId(), new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    PersonalInfo personalInfo = (PersonalInfo) model.getModel();
                    if (personalInfo != null) {
                        switch (type1){
                            case 1:
                                totalNum.setText(NumberUtils.rounLong(personalInfo.getFubao()));
                                break;
                            case 2:
                                totalNum.setText(NumberUtils.rounLong(personalInfo.getDiamond()));
                                break;
                            case 3:
                                totalNum.setText(NumberUtils.rounLong(personalInfo.getGold()));
                                break;
                        }
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, PersonalInfo.class);
        }
    }

    private void loadDatas() {
        AppLog.e(type + "===========type=========" + type1);
        Tools.showDialog(getActivity());
        User user = MyApplication.getInstance().user;
        NetUtils.getInstance().billList(user.getUserId(), type, type1, pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                InComeList inComeList = model.getModel();
//                if (totalNum != null) {
//                    totalNum.setText("" + ((TextUtils.equals("1", type)) ? inComeList.getTotal() : ("-" + inComeList.getTotal())));
//                }
                Tools.dismissWaitDialog();
                if (mRecyclerview != null && mRefeshLy != null) {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    List<InComeList.ContentBean> list = inComeList.getContent();
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
                AppLog.e(result + "============onFail==============" + type);
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
                if (mRecyclerview != null && mRefeshLy != null) {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    mRefeshLy.showFailView();
                }
            }
        }, InComeList.class);
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
