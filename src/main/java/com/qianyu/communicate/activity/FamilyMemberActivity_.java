package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FamilyMemberAdapter;
import com.qianyu.communicate.adapter.FamilyMemberAdapter_;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.FamilyMember;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class FamilyMemberActivity_ extends BaseActivity {

    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private FamilyList.ContentBean familyInfo;
    private FamilyMemberAdapter_ familyMemberAdapter;

    @Override
    public int getRootViewId() {
        return R.layout.activity_family_member;
    }

    @Override
    public void setViews() {
        setTitleTv("群成员");
        setAdapter();
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            familyInfo = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
            loadDatas();
        }
    }

    private void loadDatas() {
        NetUtils.getInstance().userList(familyInfo.getGroupId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                List<FamilyDetail.MembersBean> membersBeanList = ( List<FamilyDetail.MembersBean>) model.getModelList();
                if (membersBeanList != null&&membersBeanList.size()>0) {
                    familyMemberAdapter.appendAll(membersBeanList);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, FamilyDetail.MembersBean.class);
    }

    private void setAdapter() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(6,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(layoutManager);
        familyMemberAdapter = new FamilyMemberAdapter_(this, null);
        mRecyclerview.setAdapter(familyMemberAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

}
