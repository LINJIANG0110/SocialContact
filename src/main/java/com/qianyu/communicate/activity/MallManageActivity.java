package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FriendSearchAdapter;
import com.qianyu.communicate.adapter.MallSearchAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.SearchFriend;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-6-5.
 */

public class MallManageActivity extends BaseActivity {
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mUserName)
    TextView mUserName;
    @InjectView(R.id.mIdNum)
    TextView mIdNum;
    @InjectView(R.id.mWalletTv)
    TextView mWalletTv;
    @InjectView(R.id.mCoinTv)
    TextView mCoinTv;
    @InjectView(R.id.mDiamondTv)
    TextView mDiamondTv;
    @InjectView(R.id.mDetailTv)
    TextView mDetailTv;
    @InjectView(R.id.mSearchEt)
    EditText mSearchEt;
    @InjectView(R.id.mRecylerView)
    RecyclerView mRecylerView;
    private MallSearchAdapter adapter;

    @Override
    public int getRootViewId() {
        return R.layout.activity_mall_manage;
    }

    @Override
    public void setViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setTitleTv("商户账号");
        mHeadImg.bringToFront();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        adapter = new MallSearchAdapter(this, null);
        mRecylerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                SearchFriend.ContentBean contentBean = (SearchFriend.ContentBean) data.get(position);
                Intent intent = new Intent(MallManageActivity.this, PersonalPageActivity.class);
                intent.putExtra("userId",contentBean.getUserId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = mSearchEt.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.shortShowToast("请先输入你要搜索的内容...");
                    } else {
                        searchFriend(content);
                        KeyBoardUtils.hideSoftInput(mSearchEt);
                    }
                    return true;
                }
                return false;
            }
        });
        loadDatas();
    }

    private void loadDatas() {
        NetUtils.getInstance().expandInfo(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                PersonalInfo personalInfo = (PersonalInfo) model.getModel();
                if (personalInfo != null) {
                    mHeadImg.setImageURI(TextUtils.isEmpty(personalInfo.getHeadUrl())?"":personalInfo.getHeadUrl());
                    mUserName.setText(TextUtils.isEmpty(personalInfo.getNickName())?"":personalInfo.getNickName());
                    mIdNum.setText("ID:"+personalInfo.getUserId());
                    mWalletTv.setText(NumberUtils.rounLong(personalInfo.getFubao()));
                    mCoinTv.setText(NumberUtils.rounLong(personalInfo.getGold()));
                    mDiamondTv.setText(NumberUtils.rounLong(personalInfo.getDiamond()));
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, PersonalInfo.class);
    }

    @OnClick({R.id.mDetailTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mDetailTv:
                startActivity(new Intent(MallManageActivity.this, MallBillActivity.class));
                break;
        }
    }

    private void searchFriend(String content) {
        NetUtils.getInstance().searchUser(content, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                SearchFriend searchFriend = (SearchFriend) model.getModel();
                if (searchFriend != null) {
                    List<SearchFriend.ContentBean> list = searchFriend.getContent();
                    if (list != null && list.size() > 0) {
                        adapter.appendAll(list);
                    } else {
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, SearchFriend.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
