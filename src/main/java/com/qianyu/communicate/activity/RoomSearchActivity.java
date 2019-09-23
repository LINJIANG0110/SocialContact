package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.RoomSearchAdapter;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.wight.RefreshLayout;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class RoomSearchActivity extends BaseActivity implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {

    protected MyBaseAdapter adapter;
    @InjectView(R.id.recyclerview)
    XRecyclerView mRecyclerview;
    @InjectView(R.id.refesh_ly)
    RefreshLayout mRefeshLy;
    @InjectView(R.id.mSearchLL)
    LinearLayout mSearchLL;
    @InjectView(R.id.mSearchEt)
    EditText mSearchEt;
    private String condition = "";
    private int pageNum=0;
    private int pageSize=10;

    @Override
    public int getRootViewId() {
        return R.layout.activity_room_search;
    }

    @Override
    public void setViews() {
        setAdapter();
        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyBoardUtils.hideSoftInput(mSearchEt);
                    condition = mSearchEt.getText().toString().trim();
                    if (TextUtils.isEmpty(condition)) {
                        ToastUtil.shortShowToast("请先输入你要搜索的内容...");
                    }else {
                        loadDatas();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setAdapter() {
        adapter = new RoomSearchAdapter(this, null);
        mRecyclerview.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
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
        mRecyclerview.setPullRefreshEnabled(false);
        mRecyclerview.hideFootView();
    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                FamilyList.ContentBean contentBean = (FamilyList.ContentBean) data.get(position);
                Tools.enterFamily(RoomSearchActivity.this,contentBean.getGroupId(), false);
            }
        });
    }

    private void loadDatas() {
        NetUtils.getInstance().familyList(condition,pageNum, pageSize, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        FamilyList familyList = (FamilyList) model.getModel();
                        if (familyList != null) {
                            List<FamilyList.ContentBean> list = familyList.getContent();
                            if (mRecyclerview != null && mRefeshLy != null && adapter != null) {
                                mRecyclerview.loadMoreComplete();
                                mRecyclerview.refreshComplete();
                                mRefeshLy.hideAll();
                                if (pageNum == 0) {
                                    adapter.clear();
                                }
                                adapter.append(list);
                                if (list == null || list.size() < pageSize) {
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
                        if (mRecyclerview != null && mRefeshLy != null) {
                            mRecyclerview.loadMoreComplete();
                            mRecyclerview.refreshComplete();
                            mRefeshLy.hideAll();
                            mRefeshLy.showFailView();
                        }
                    }
                }, FamilyList.class);
    }

    @Override
    public void onRetryClick() {
        loadDatas();
    }

    @Override
    public void onRefresh() {
        loadDatas();
    }

    @Override
    public void onLoadMore() {
        loadDatas();
    }

    @OnClick({R.id.mCancelTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mCancelTv:
                KeyBoardUtils.hideSoftInput(mSearchEt);
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
