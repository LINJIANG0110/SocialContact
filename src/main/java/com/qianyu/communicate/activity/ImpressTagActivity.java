package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.TagAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.ImpressLabel;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.applibrary.flowtag.FlowTagLayout;
import net.neiquan.applibrary.flowtag.OnTagSelectListener;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class ImpressTagActivity extends BaseActivity {
    @InjectView(R.id.tagFlowLayout)
    FlowTagLayout tagFlowLayout;
    @InjectView(R.id.diyTagTv)
    TextView diyTagTv;
    @InjectView(R.id.tagHintTv)
    TextView tagHintTv;
    private TagAdapter<ImpressLabel> adapter;

    @Override
    public int getRootViewId() {
        return R.layout.activity_impress_tag;
    }

    @Override
    public void setViews() {
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        setNextTv("保存");
        setTitleTv("添加标签");
        tagHintTv.setText("添加符合你的标签（最多十个）");
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLabels();
            }
        });
    }

    private String labelDetailsId = "";

    private void saveLabels() {
        labelDetailsId = "";
        List<ImpressLabel> mDataList = adapter.mDataList;
        final List<ImpressLabel> mDataList1 = new ArrayList<>();
        if (mDataList != null && mDataList.size() > 0) {
            for (int i = 0; i < mDataList.size(); i++) {
                ImpressLabel impressLabel = mDataList.get(i);
                if (impressLabel.isSelected()) {
                    mDataList1.add(impressLabel);
                }
            }
            for (int i = 0; i < mDataList1.size(); i++) {
                ImpressLabel impressLabel = mDataList1.get(i);
                if (i == mDataList1.size() - 1) {
                    labelDetailsId += impressLabel.getLabelName();
                } else {
                    labelDetailsId += impressLabel.getLabelName() + ",";
                }
            }
            if (mDataList1.size() > 10) {
                ToastUtil.shortShowToast("最多添加十个标签...");
                return;
            }
            if (TextUtils.isEmpty(labelDetailsId)) {
                ToastUtil.shortShowToast("请先选择标签...");
                return;
            }
            User user = MyApplication.getInstance().user;
            if (user == null) {
                ToastUtil.shortShowToast("请先登录...");
                startActivity(new Intent(ImpressTagActivity.this, RegistActivity.class));
                return;
            }
            EventBuss event = new EventBuss(EventBuss.IMPRESS_TAG);
            event.setMessage(labelDetailsId);
            EventBus.getDefault().post(event);
            finish();
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
        NetUtils.getInstance().userLabel(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                List<ImpressLabel> labels = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        labels.add(new ImpressLabel((String) data.get(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.clearAndAddAll(labels);
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        },null);
    }

    private void initTagLayout() {
        tagFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        adapter = new TagAdapter<ImpressLabel>(this);
        tagFlowLayout.setAdapter(adapter);
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
