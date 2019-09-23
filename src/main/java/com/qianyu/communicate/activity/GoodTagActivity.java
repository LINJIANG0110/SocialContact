package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.AdeptAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.AdeptLabel;
import com.qianyu.communicate.entity.User;

import net.neiquan.applibrary.flowtag.FlowTagLayout;
import net.neiquan.applibrary.flowtag.OnTagSelectListener;

import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class GoodTagActivity extends BaseActivity {
    @InjectView(R.id.tagFlowLayout)
    FlowTagLayout tagFlowLayout;
    @InjectView(R.id.diyTagTv)
    TextView diyTagTv;
    @InjectView(R.id.tagHintTv)
    TextView tagHintTv;
    private AdeptAdapter<AdeptLabel> adapter;

    @Override
    public int getRootViewId() {
        return R.layout.activity_impress_tag;
    }

    @Override
    public void setViews() {
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        setNextTv("保存");
        setTitleTv("添加擅长");
        tagHintTv.setText("添加符合你的标签（最多三个）");
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLabels();
            }
        });
    }

    private String labelDetailsId = "";

    private void saveLabels() {
        labelDetailsId="";
        List<AdeptLabel> mDataList = adapter.mDataList;
        final List<AdeptLabel> mDataList1 = new ArrayList<>();
        if (mDataList != null && mDataList.size() > 0) {
            for (int i = 0; i < mDataList.size(); i++) {
                AdeptLabel impressLabel = mDataList.get(i);
                if (impressLabel.isSelected()) {
                    mDataList1.add(impressLabel);
                }
            }
            for (int i = 0; i < mDataList1.size(); i++) {
                AdeptLabel impressLabel = mDataList1.get(i);
                if (i == mDataList1.size() - 1) {
                    labelDetailsId += impressLabel.getaId();
                } else {
                    labelDetailsId += impressLabel.getaId() + ",";
                }
            }
            if (mDataList1.size() > 3) {
                ToastUtil.shortShowToast("最多添加三个标签...");
                return;
            }
            if (TextUtils.isEmpty(labelDetailsId)) {
                ToastUtil.shortShowToast("请先选择标签...");
                return;
            }
            User user = MyApplication.getInstance().user;
            if (user != null) {
//                Tools.showDialog(this);
            } else {
                ToastUtil.shortShowToast("请先登录...");
                startActivity(new Intent(GoodTagActivity.this, RegistActivity.class));
            }
        } else {
            ToastUtil.shortShowToast("暂无标签...");
        }
    }

    @Override
    public void initData() {
        initTagLayout();
        loadDatas();
    }

    private void loadDatas() {
//        Tools.showDialog(this);
    }

    private void initTagLayout() {
        tagFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        adapter = new AdeptAdapter<AdeptLabel>(this);
        tagFlowLayout.setAdapter(adapter);
//        adapter.clearAndAddAll(strings);
        tagFlowLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//                for (int i = 0; i < selectedList.size(); i++) {
//                    AppLog.e("==========setOnTagSelectListener===========" + selectedList.get(i));
//                    Search data = ((Search) parent.getAdapter().getItem(selectedList.get(i)));
//                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
//                    intent.putExtra("search", data.getTitle());
//                    startActivity(intent);
//                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick(R.id.diyTagTv)
    public void onViewClicked() {

    }
}
