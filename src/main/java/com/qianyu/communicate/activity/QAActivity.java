package com.qianyu.communicate.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.QAListAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyInfo;
import com.qianyu.communicate.entity.QAList;
import com.qianyu.communicate.entity.User;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.neiquan.applibrary.wight.RefreshLayout;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2018-5-29.
 */

public class QAActivity extends BaseActivity implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {
    @InjectView(R.id.recyclerview)
    XRecyclerView mRecyclerview;
    @InjectView(R.id.refesh_ly)
    RefreshLayout mRefeshLy;
    @InjectView(R.id.mHeadLL_)
    LinearLayout mHeadLL_;
    @InjectView(R.id.mHeadImg_)
    SimpleDraweeView mHeadImg_;
    @InjectView(R.id.mNickName_)
    TextView mNickName_;
    @InjectView(R.id.mAskQuesTv_)
    TextView mAskQuesTv_;
    private TextView mHisQATv;
    private TextView mAskQuesTv;
    private TextView mAnsweredTv;
    private LinearLayout mHeadLL;
    private SimpleDraweeView mHeadImg;
    private TextView mNickName;
    private TextView mGoodATTv;
    private int mDistanceY = 0;
    private QAListAdapter adapter;
    private FamilyInfo familyInfo;
    private int pageNo = 0;
    private int pageSize = 10;

    @Override
    public int getRootViewId() {
        return R.layout.activity_qa;
    }

    @Override
    public void setViews() {
        setTitleTv("问答");
        setAdapter();
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动的距离
                mDistanceY += dy;
                //toolbar的高度
                int toolbarHeight = mHeadLL.getBottom();

                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= toolbarHeight) {
                    mHeadLL_.setVisibility(View.GONE);
                } else {
                    //上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色
                    //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
                    //将标题栏的颜色设置为完全不透明状态
                    mHeadLL_.setVisibility(View.VISIBLE);
                }
            }
        });
        if (getIntent() != null) {
            familyInfo = (FamilyInfo) getIntent().getSerializableExtra("family");
            View headView = LayoutInflater.from(this).inflate(R.layout.layout_head_qa, null);
            mRecyclerview.addHeaderView(headView);
            mHeadImg = (SimpleDraweeView) headView.findViewById(R.id.mHeadImg);
            mNickName = (TextView) headView.findViewById(R.id.mNickName);
            mGoodATTv = (TextView) headView.findViewById(R.id.mGoodATTv);
            mAskQuesTv = (TextView) headView.findViewById(R.id.mAskQuesTv);
            mHisQATv = (TextView) headView.findViewById(R.id.mHisQATv);
            mAnsweredTv = (TextView) headView.findViewById(R.id.mAnsweredTv);
            mHeadLL = (LinearLayout) headView.findViewById(R.id.mHeadLL);
            mHeadImg.setImageURI(TextUtils.isEmpty(familyInfo.getHeadUrl()) ? "" : familyInfo.getHeadUrl());
            mNickName.setText(TextUtils.isEmpty(familyInfo.getNickName()) ? "" : familyInfo.getNickName());
//            mGoodATTv.setText("擅长：" + (TextUtils.isEmpty(familyInfo.getSpecialty()) ? "" : familyInfo.getSpecialty()));
//            mGoodATTv.setVisibility(TextUtils.isEmpty(familyInfo.getSpecialty()) ? View.GONE : View.VISIBLE);
            mHeadImg_.setImageURI(TextUtils.isEmpty(familyInfo.getHeadUrl()) ? "" : familyInfo.getHeadUrl());
            mNickName_.setText(TextUtils.isEmpty(familyInfo.getNickName()) ? "" : familyInfo.getNickName());
            mAskQuesTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = MyApplication.getInstance().user;
                    if (user == null) {
                        ToastUtil.shortShowToast("请先登录...");
                        startActivity(new Intent(QAActivity.this, RegistActivity.class));
                        return;
                    }
                    if (familyInfo.getUserId() == user.getUserId()) {
                        ToastUtil.shortShowToast("主播不能给自己提问哦...");
                        return;
                    }
                    Intent intent2 = new Intent(QAActivity.this, AskQuestionActivity.class);
                    intent2.putExtra("doctorId", familyInfo.getUserId());
                    startActivity(intent2);
                }
            });
        }
    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                QAList qaList = (QAList) data.get(position);
                Intent intent = new Intent(QAActivity.this, QADetailActivity.class);
                intent.putExtra("iId", qaList.getiId());
                startActivity(intent);
            }
        });
        loadDatas();
    }

    private void loadDatas() {
    }


    @OnClick({R.id.mAskQuesTv_})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mAskQuesTv_:
                User user = MyApplication.getInstance().user;
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(QAActivity.this, RegistActivity.class));
                    return;
                }
                if (familyInfo.getUserId() == user.getUserId()) {
                    ToastUtil.shortShowToast("主播不能给自己提问哦...");
                    return;
                }
                Intent intent2 = new Intent(QAActivity.this, AskQuestionActivity.class);
                intent2.putExtra("doctorId", familyInfo.getUserId());
                startActivity(intent2);
                break;
        }
    }


    @Override
    public void onRetryClick() {
        pageNo = 0;
        loadDatas();
    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        loadDatas();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        loadDatas();
    }

    private void setAdapter() {
        adapter = new QAListAdapter(this, null);
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
        mRecyclerview.setPullRefreshEnabled(true);
    }

}
