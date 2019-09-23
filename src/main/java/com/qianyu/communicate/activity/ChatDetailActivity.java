package com.qianyu.communicate.activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.FamilyInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.SpringUtils;

import org.haitao.common.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2018-7-4.
 */

public class ChatDetailActivity extends BaseActivity {

    @InjectView(R.id.mBackFL)
    FrameLayout mBackFL;
    @InjectView(R.id.mShareFL)
    FrameLayout mShareFL;
    @InjectView(R.id.mMoreFL)
    FrameLayout mMoreFL;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mNumTv)
    TextView mNumTv;
    @InjectView(R.id.mTitleTv)
    TextView mTitleTv;
    @InjectView(R.id.mTimeTv)
    TextView mTimeTv;
    @InjectView(R.id.mNameTv)
    TextView mNameTv;
    @InjectView(R.id.mGoodAtTv)
    TextView mGoodAtTv;
    @InjectView(R.id.mSubjectTv)
    TextView mSubjectTv;
    @InjectView(R.id.mCollectLL)
    LinearLayout mCollectLL;
    @InjectView(R.id.mStartLiveLL)
    LinearLayout mStartLiveLL;
    @InjectView(R.id.mCollectImg)
    ImageView mCollectImg;
    @InjectView(R.id.mCollectTv)
    TextView mCollectTv;
    @InjectView(R.id.mEnterTv)
    TextView mEnterTv;
    private UserBean userBean;
    private boolean live = false;
    private boolean collectFlag = true;
    private FamilyInfo familyInfo;
    private String whether;
    private Integer userId;//主播id

    @Override
    public int getRootViewId() {
        return R.layout.activity_chat_detail;
    }

    @Override
    public void setViews() {

    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            userBean = ((UserBean) getIntent().getSerializableExtra("userBean"));
            live = getIntent().getBooleanExtra("live", false);
            loadDatas();
        }
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
    }

    @OnClick({R.id.mBackFL, R.id.mShareFL, R.id.mMoreFL, R.id.mCollectLL, R.id.mEnterTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBackFL:
                finish();
                break;
            case R.id.mShareFL:
                SpringUtils.springAnim(mShareFL);
                break;
            case R.id.mMoreFL:
                SpringUtils.springAnim(mMoreFL);
                break;
            case R.id.mCollectLL:
//                collectFlag = !collectFlag;
//                mCollectImg.setImageResource(collectFlag ? R.mipmap.collect_yes : R.mipmap.collect_no);
                addRead();
                break;
            case R.id.mEnterTv:
                if (!live) {
                    Intent intent = new Intent(ChatDetailActivity.this, ChatRoomUserActivity.class);
//                    Intent intent = new Intent(ChatDetailActivity.this, FamilyRoomActivity.class);
                    intent.putExtra("userBean", userBean);
                    startActivity(intent);
                }
                finish();
                break;
        }
    }

    private void addRead() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
//            Tools.showDialog(this);
        } else {
            ToastUtil.shortShowToast("请先登录....");
            startActivity(new Intent(ChatDetailActivity.this, RegistActivity.class));
        }
    }

}
