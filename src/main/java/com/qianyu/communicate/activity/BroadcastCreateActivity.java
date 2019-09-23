package com.qianyu.communicate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.BroadcastTagAdapter;
import com.qianyu.communicate.adapter.TagAdapter;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.ImpressLabel;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.applibrary.flowtag.FlowTagLayout;
import net.neiquan.applibrary.flowtag.OnTagSelectListener;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class BroadcastCreateActivity extends BaseActivity {
    @InjectView(R.id.tagFlowLayout)
    FlowTagLayout tagFlowLayout;
    private BroadcastTagAdapter<ImpressLabel> adapter;
    @Override
    public int getRootViewId() {
        return R.layout.activity_broadcast_create;
    }

    @Override
    public void setViews() {

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
        adapter = new BroadcastTagAdapter<ImpressLabel>(this);
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
}
