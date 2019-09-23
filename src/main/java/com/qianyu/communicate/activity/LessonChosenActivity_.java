package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.LessonChosenAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.entity.DocumentInfo;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.fileupload.FileUploadActivity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.neiquan.applibrary.wight.RefreshLayout;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class LessonChosenActivity_ extends BaseActivity implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {
    @InjectView(R.id.refesh_ly)
    RefreshLayout mRefeshLy;
    @InjectView(R.id.recyclerview)
    XRecyclerView mRecyclerview;
    @InjectView(R.id.mFloatActionBtn)
    ImageView mFloatActionBtn;
    @InjectView(R.id.mChosenTv)
    TextView mChosenTv;
    @InjectView(R.id.mPlayTv)
    TextView mPlayTv;
    private LessonChosenAdapter adapter;
    private boolean selectFlag=false;
    private UserBean userBean;

    @Override
    public int getRootViewId() {
        return R.layout.activity_lesson_chosen_;
    }

    @Override
    public void setViews() {
        if (getIntent() != null) {
            userBean = ((UserBean) getIntent().getSerializableExtra("userBean"));
        }
        setTitleTv("我的文档");
        setNextTv("上传");
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonChosenActivity_.this, FileUploadActivity.class);
                intent.putExtra("userBean",userBean);
                startActivityForResult(intent,1001);
            }
        });
        setAdapter();
    }

    @Override
    public void initData() {
        setResult(Constant.DOCUMENT_CODE_, null);
        loadDatas();
        adapter.setOnItemSelectDeletListener(new LessonChosenAdapter.OnItemSelectDeletListener() {
            @Override
            public void onItemSeleDele(DocumentInfo.DataBean dataBean) {
                mChosenTv.setText("已选  "+dataBean.getFileName());
            }
        });
    }

    @OnClick({R.id.mPlayTv, R.id.mFloatActionBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mFloatActionBtn:
                mRecyclerview.scrollToPosition(0);
                break;
            case R.id.mPlayTv:
                for (int i = 0; i < adapter.data.size(); i++) {
                    AppLog.e("=============mPlayTv111===============");
                    if (adapter.data.get(i).isSelected()) {
                        selectFlag=true;
                        AppLog.e("=============mPlayTv222===============" + adapter.data.get(i));
                        Intent intent = new Intent();
                        intent.putExtra(Constant.DOCUMENT_DATA, adapter.data.get(i));
                        setResult(Constant.DOCUMENT_CODE_, intent);
                        finish();
                    }
                }
                if(!selectFlag){
                    ToastUtil.shortShowToast("请先选择要播放的文档...");
                }
                break;
        }
    }

    @Override
    public void onRetryClick() {
        loadDatas();
    }

    /**
     * 获取文档
     */
    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(LessonChosenActivity_.this,RegistActivity.class));
            return;
        }
    }

    private void setAdapter() {
        adapter = new LessonChosenAdapter(this, null);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001&&resultCode==1002){
            loadDatas();
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
