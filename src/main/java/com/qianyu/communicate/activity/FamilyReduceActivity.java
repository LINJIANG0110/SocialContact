package com.qianyu.communicate.activity;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.qianyu.communicate.adapter.FamilyReduceAdapter;
import com.qianyu.communicate.adapter.FriendListAdapter;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.BukaHelper_;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JavaDog on 2019-4-17.
 */

public class FamilyReduceActivity extends BaseListActivity {
    private FamilyList.ContentBean familyInfo;
    private UserBean userBean;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("群成员");
        setNextTv("移出群");
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userIds="";
                List<FamilyDetail.MembersBean> membersBeans=new ArrayList<>();
                for (int i = 0; i < adapter.data.size(); i++) {
                    final FamilyDetail.MembersBean membersBean = (FamilyDetail.MembersBean) adapter.data.get(i);
                    if (membersBean.isSelected()) {
                        membersBeans.add(membersBean);
                        final UserBean userBean = new UserBean();
                        userBean.setUserId(membersBean.getUserId());
                        userBean.setNickName(membersBean.getNickName());
                        userBean.setHeadUrl(membersBean.getHeadUrl());
                        userBean.setUserType(membersBean.getUserType());
                        BukaHelper_.getInstance(FamilyReduceActivity.this).rpc(null, Constant.RPC_TICK_SB, JSON.toJSONString(userBean));
                        EventBuss event = new EventBuss(EventBuss.FAMILY_REDUCE);
                        event.setMessage(membersBean.getUserId());
                        EventBus.getDefault().post(event);
                    }
                }
                if (membersBeans != null && membersBeans.size() > 0) {
                    for (int i = 0; i < membersBeans.size(); i++) {
                        if (i == membersBeans.size() - 1) {
                            userIds += membersBeans.get(i).getUserId();
                        } else {
                            userIds += membersBeans.get(i).getUserId() + ",";
                        }
                    }
                }
                if(TextUtils.isEmpty(userIds)){
                    ToastUtil.shortShowToast("请先选择要踢出的人...");
                    return;
                }
                NetUtils.getInstance().reduceGroup(familyInfo.getGroupId(), userIds, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        ToastUtil.shortShowToast(msg);
                        loadDatas();
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                }, null);
            }
        });
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new FamilyReduceAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 1;
    }

    @Override
    public void initData() {
        mRecyclerview.setPullRefreshEnabled(false);
        if (getIntent() != null) {
            familyInfo = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
            userBean = ((UserBean) getIntent().getSerializableExtra("userBean"));
        }
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                FamilyDetail.MembersBean membersBean = (FamilyDetail.MembersBean) data.get(position);
                if (userBean.getUserType() >= membersBean.getUserType()) {
                    ToastUtil.shortShowToast("权限不足...");
                    return;
                }
                membersBean.setSelected(!membersBean.isSelected());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void loadDatas() {
        NetUtils.getInstance().userList(familyInfo.getGroupId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                List<FamilyDetail.MembersBean> membersBeanList = (List<FamilyDetail.MembersBean>) model.getModelList();
                if (membersBeanList != null && membersBeanList.size() > 0) {
                    adapter.appendAll(membersBeanList);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, FamilyDetail.MembersBean.class);
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
