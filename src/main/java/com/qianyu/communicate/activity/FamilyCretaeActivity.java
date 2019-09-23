package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FamilySuggestAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.LocationHelper;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class FamilyCretaeActivity extends BaseActivity {

    @InjectView(R.id.mFamilyName)
    EditText mFamilyName;
    @InjectView(R.id.mFamilyCreated)
    CardView mFamilyCreated;
    @InjectView(R.id.mChangeTv)
    TextView mChangeTv;
    @InjectView(R.id.mRecylerView)
    RecyclerView mRecyclerview;
    private FamilySuggestAdapter adapter;
    private LocationHelper locationHelper;
    private String address;
    private int pageNum=0;

    @Override
    public int getRootViewId() {
        return R.layout.activity_family_create;
    }

    @Override
    public void setViews() {
        setTitleTv("创建家族");
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(layoutManager);
        adapter = new FamilySuggestAdapter(this, null);
        mRecyclerview.setAdapter(adapter);

        locationHelper = LocationHelper.getInstance();
        if (locationHelper != null) {
            locationHelper.setCallBack(new LocationHelper.LocationCallBack() {
                @Override
                public void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province) {
                    address = city;
                }
            });
            locationHelper.start();
        }
    }

    @Override
    public void initData() {
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, final List data, final int position) {
                final FamilyList.ContentBean contentBean = (FamilyList.ContentBean) data.get(position);
                Tools.enterFamily(FamilyCretaeActivity.this,contentBean.getGroupId(), false);
            }
        });
    }

    @OnClick({R.id.mFamilyCreated, R.id.mChangeTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mFamilyCreated:
                String familyName = mFamilyName.getText().toString().trim();
                User user = MyApplication.getInstance().user;
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(this, RegistActivity.class));
                    finish();
                    return;
                }
                if (TextUtils.isEmpty(familyName)) {
                    ToastUtil.shortShowToast("请先输入群名字...");
                    return;
                }
                if (familyName.contains("代充") || familyName.contains("官方")) {
                    ToastUtil.longShowToast("家族名字中不可出现“代充”、“官方”等关键字...");
                    return;
                }
                Tools.showDialog(FamilyCretaeActivity.this);
                NetUtils.getInstance().familyCreate(familyName, address, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        ToastUtil.shortShowToast(msg);
                        final FamilyList.ContentBean contentBean = (FamilyList.ContentBean) model.getModel();
                        Tools.enterFamily(FamilyCretaeActivity.this,contentBean.getGroupId(), false);
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        Tools.dismissWaitDialog();
                        ToastUtil.shortShowToast(msg);
                    }
                }, FamilyList.ContentBean.class);
                break;
            case R.id.mChangeTv:
                pageNum++;
                loadDatas();
                break;
        }
    }

    private void loadDatas() {
        NetUtils.getInstance().activeGroup(pageNum, 6, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                FamilyList familyList = (FamilyList) model.getModel();
                if (familyList != null) {
                    List<FamilyList.ContentBean> list = familyList.getContent();
                    if(list!=null&&list.size()>0) {
                        adapter.appendAll(list);
                    }else {
                        pageNum=-1;
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, FamilyList.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationHelper != null) {
            locationHelper.stop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
