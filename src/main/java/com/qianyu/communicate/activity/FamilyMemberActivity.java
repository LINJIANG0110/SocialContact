package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FamilyDetailAdapter;
import com.qianyu.communicate.adapter.FamilyMemberAdapter;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.FamilyMember;
import com.qianyu.communicate.http.NetUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class FamilyMemberActivity extends BaseActivity {

    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private FamilyList.ContentBean familyInfo;
    private FamilyMemberAdapter familyMemberAdapter;
    private UserBean userBean;

    @Override
    public int getRootViewId() {
        return R.layout.activity_family_member;
    }

    @Override
    public void setViews() {
        setTitleTv("群成员");
        getNextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextTv(familyMemberAdapter.isManage()?"管理":"取消");
                familyMemberAdapter.setManage(!familyMemberAdapter.isManage());
            }
        });
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            familyInfo = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
            userBean = ((UserBean) getIntent().getSerializableExtra("userBean"));
            if(userBean.getUserType()<=2) {
                setNextTv("管理");
            }
            loadDatas();
        }
        setAdapter();
    }

    private void loadDatas() {
        NetUtils.getInstance().userList(familyInfo.getGroupId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                List<FamilyDetail.MembersBean> membersBeanList = ( List<FamilyDetail.MembersBean>) model.getModelList();
                if (membersBeanList != null&&membersBeanList.size()>0) {
                    membersBeanList.add(new FamilyDetail.MembersBean());
                    membersBeanList.add(new FamilyDetail.MembersBean());
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
        familyMemberAdapter = new FamilyMemberAdapter(this, null,familyInfo,userBean);
        mRecyclerview.setAdapter(familyMemberAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

}
