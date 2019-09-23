package com.qianyu.communicate.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.WorkTagAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.WorkTag;

import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class WorkTagActivity extends BaseListActivity {

    private WorkTag workTag;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        setNextTv("保存");
        setTitleTv("行业");
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTag();
            }
        });
    }

    private void saveTag() {
        List data = adapter.data;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (((WorkTag) data.get(i)).isChosen()) {
                    workTag = (WorkTag) data.get(i);
                }
            }
            if (workTag == null) {
                ToastUtil.shortShowToast("请先选择标签...");
                return;
            }
            User user = MyApplication.getInstance().user;
            if (user == null) {
                ToastUtil.shortShowToast("请先登录...");
                startActivity(new Intent(WorkTagActivity.this, RegistActivity.class));
            } else {
                EventBuss event = new EventBuss(EventBuss.WORK_TAG);
                event.setMessage(workTag);
                EventBus.getDefault().post(event);
                finish();
            }
        } else {
            ToastUtil.shortShowToast("暂无行业标签...");
        }
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new WorkTagAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        mRecyclerview.setPullRefreshEnabled(false);
        mRecyclerview.setLoadingMoreEnabled(false);
        mRecyclerview.hideFootView();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                for (int i = 0; i < data.size(); i++) {
                    ((WorkTag) data.get(i)).setChosen(false);
                }
                ((WorkTag) data.get(position)).setChosen(true);
                adapter.notifyDataSetChanged();
            }
        });
        loadDatas();
    }

    private void loadDatas() {
        NetUtils.getInstance().workInfo(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Tools.dismissWaitDialog();
                List<WorkTag> list = (List<WorkTag>) model.getModelList();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        WorkTag workTag = list.get(i);
                        if (TextUtils.equals("IT", workTag.getHeadLine())) {
                            workTag.setHeadLine("  IT  ");
                        }
                    }
                    if (adapter != null) {
                        adapter.appendAll(list);
                    }
                } else {
                    if (mRefeshLy != null) {
                        mRefeshLy.showEmptyView();
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
                if (mRefeshLy != null) {
                    mRefeshLy.showFailView();
                }
            }
        }, WorkTag.class);
    }

    @Override
    public void onRetryClick() {
        loadDatas();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
