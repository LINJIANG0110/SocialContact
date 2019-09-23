package com.qianyu.communicate.fragment;

import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.ArticleListAdapter;
import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class ArticleListFragment extends BaseListFragment {

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {

    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new ArticleListAdapter(getActivity(), null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        setmFloatActionBtn(R.mipmap.dongtai_dingduan);
        setFloatActionBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerview.scrollToPosition(0);
            }
        });
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
//                QAList qaList = (QAList) data.get(position);
//                Intent intent = new Intent(getActivity(), QADetailActivity.class);
//                intent.putExtra("iId",qaList.getiId());
//                startActivity(intent);
            }
        });
    }

    private void loadDatas() {
//        NetUtils.getInstance().qaList(0, 0, 1, 0, 0, pageNum, PAEG_SIZE, new NetCallBack() {
//            @Override
//            public void onSuccess(String result, String msg, ResultModel model) {
//                List<QAList> list = ((List<QAList>) model.getModelList());
//                if (mRecyclerview != null && mRefeshLy != null && adapter != null) {
//                    mRecyclerview.loadMoreComplete();
//                    mRecyclerview.refreshComplete();
//                    mRefeshLy.hideAll();
//                    if (pageNum == 0) {
//                        adapter.clear();
//                    }
//                    adapter.append(list);
//                    if (list == null || list.size() < PAEG_SIZE) {
//                        if (pageNum == 0 && (list == null || list.size() == 0)) {
//                            mRefeshLy.showEmptyView();
//                        }
//                        mRecyclerview.setLoadingMoreEnabled(false);
//                    } else {
//                        mRecyclerview.setLoadingMoreEnabled(true);
//                    }
//                }
//            }
//
//            @Override
//            public void onFail(String code, String msg, String result) {
//                ToastUtil.shortShowToast(msg);
//                if (mRecyclerview != null && mRefeshLy != null) {
//                    mRecyclerview.loadMoreComplete();
//                    mRecyclerview.refreshComplete();
//                    mRefeshLy.hideAll();
//                    mRefeshLy.showFailView();
//                }
//            }
//        }, QAList.class);
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
