package com.qianyu.communicate.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.MyConcernFamilyAdapter;
import com.qianyu.communicate.adapter.MyFamilyAdapter;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.UserGroups;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import java.util.List;

/**
 * Created by JavaDog on 2019-5-29.
 */

public class MyFamilyActivity extends BaseListActivity {

    private MyConcernFamilyAdapter familyAdapter;
    private SimpleDraweeView mHeadImg;
    private TextView mFamilyName;
    private TextView mFamilyDetail;
    private TextView mConcernView;
    private LinearLayout mMyFamilyLL;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("我的家族");
        View view = LayoutInflater.from(this).inflate(R.layout.layout_head_my_family, null);
        RecyclerView mConcernRecylerView = (RecyclerView) view.findViewById(R.id.mConcernRecylerView);
        mHeadImg = (SimpleDraweeView) view.findViewById(R.id.mHeadImg);
        mMyFamilyLL = (LinearLayout) view.findViewById(R.id.mMyFamilyLL);
        mConcernView = (TextView) view.findViewById(R.id.mConcernView);
        mFamilyName = (TextView) view.findViewById(R.id.mFamilyName);
        mFamilyDetail = (TextView) view.findViewById(R.id.mFamilyDetail);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mConcernRecylerView.setLayoutManager(layoutManager);
        familyAdapter = new MyConcernFamilyAdapter(this, null);
        mConcernRecylerView.setAdapter(familyAdapter);
        mRecyclerview.addHeaderView(view);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new MyFamilyAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }


    @Override
    public void initData() {
        loadDatas();
        loadFamily();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                FamilyList.ContentBean contentBean = (FamilyList.ContentBean) data.get(position);
                Tools.enterFamily(MyFamilyActivity.this,contentBean.getGroupId(), false);
            }
        });
        familyAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                FamilyList.ContentBean contentBean = (FamilyList.ContentBean) data.get(position);
                Tools.enterFamily(MyFamilyActivity.this,contentBean.getGroupId(), false);
            }
        });
    }

    private void loadFamily() {
        NetUtils.getInstance().userGroupList(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                UserGroups userGroups = (UserGroups) model.getModel();
                if (userGroups != null) {
                    final FamilyList.ContentBean contentBean = userGroups.getGroupInfo();
                    if (contentBean != null) {
                        mHeadImg.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl())?"":contentBean.getHeadUrl());
                        mFamilyName.setText(TextUtils.isEmpty(contentBean.getGroupName())?"":contentBean.getGroupName());
                        mFamilyDetail.setText(TextUtils.isEmpty(contentBean.getDetails())?"":contentBean.getDetails());
                        mMyFamilyLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Tools.enterFamily(MyFamilyActivity.this,contentBean.getGroupId(), false);
                            }
                        });
                    }
                    List<FamilyList.ContentBean> gzlist = userGroups.getGzlist();
                    if (gzlist != null&&gzlist.size()>0) {
                        familyAdapter.appendAll(gzlist);
                        mConcernView.setVisibility(View.VISIBLE);
                    }else {
                        mConcernView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
            }
        }, UserGroups.class);
    }

    private void loadDatas() {
        NetUtils.getInstance().activeGroup(pageNum, PAEG_SIZE, new NetCallBack() {
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
                        if (list == null || list.size() < PAEG_SIZE) {
                            if (pageNum == 0 && (list == null || list.size() == 0)) {
//                                mRefeshLy.showEmptyView();
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
        pageNum = 0;
        loadDatas();
        loadFamily();
    }


    @Override
    public void onRefresh() {
        pageNum = 0;
        loadDatas();
        loadFamily();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        loadDatas();
    }
}
