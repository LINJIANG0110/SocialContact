package com.qianyu.communicate.activity;

import android.content.Intent;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FriendCircleAdapter;
import com.qianyu.communicate.base.MyMBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class FriendsCircleActivity extends BaseMListActivity {
    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("动态");
        setmFloatActionBtn(R.drawable.qq);
        setNextImage(R.mipmap.ic_launcher);
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FriendsCircleActivity.this, PublishCircleActivity.class));
            }
        });
        setFloatActionBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerview.scrollToPosition(0);
            }
        });
    }

    @Override
    protected MyMBaseAdapter getAdapter() {
        return new FriendCircleAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new MyMBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                startActivity(new Intent(FriendsCircleActivity.this, FriendDetailActivity.class));
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
