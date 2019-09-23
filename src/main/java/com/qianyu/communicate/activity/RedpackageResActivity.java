package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.RPUserRecordApdater;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.RedPackageDelBean;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 红包详情
 */
public class RedpackageResActivity extends BaseActivity {
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_record)
    TextView tvRecord;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.iv_userhead)
    SimpleDraweeView ivUserhead;
    @InjectView(R.id.tv_idiom)
    TextView tvIdiom;
    @InjectView(R.id.tv_surplus)
    TextView tvSurplus;// 共多少个剩余多少个
    @InjectView(R.id.mListview)
    ListView mListview;
    private String groupId;
    private String redPackageId;
    RPUserRecordApdater recordApdater;
    private List<RedPackageDelBean.UserRecordBean> recordData;

    @Override
    public int getRootViewId() {
        return R.layout.activity_redpackage_res;
    }

    @Override
    public void setViews() {
        tvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RedpackageResActivity.this, RedPackageJlActivity.class));
            }
        });
        groupId = getIntent().getStringExtra("groupId");
        redPackageId = getIntent().getStringExtra("redPackageId");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        getRedPackageDel();
    }

    // 获取红包详情
    private void getRedPackageDel() {
        NetUtils.getInstance().getRedpackageDel(groupId, redPackageId, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("红包详情", result);
                try {
                    RedPackageDelBean delBean = (RedPackageDelBean) model.getModel();
                    if (delBean.isOverTime == 0) {
                        tvMoney.setText(delBean.totalDiamond);
                        ivUserhead.setImageURI(TextUtils.isEmpty(delBean.headUrl) ? "" : delBean.headUrl);
                        tvUsername.setText(delBean.nickName + "的红包");
                        tvIdiom.setText("接龙成语：" + delBean.title);
//                        领取2/5，剩余0.13元
                        tvSurplus.setText("领取" + delBean.nowNum + "/" + delBean.totalNum + ",剩余" + delBean.balance + "钻石");
                        recordData = delBean.logList;
                        recordApdater = new RPUserRecordApdater(RedpackageResActivity.this, recordData);
                        mListview.setAdapter(recordApdater);
                    } else if (delBean.isOverTime == 1) {
                        ToastUtil.shortShowToast("红包已过时");
                    } else {
                        ToastUtil.shortShowToast(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
            }
        }, RedPackageDelBean.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }

}
