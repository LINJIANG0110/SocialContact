package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.adapter.RankListAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class RichListFragment extends BaseListFragment {

    private LinearLayout mTimeLL;
    private TextView mTimeTv;
    private LinearLayout mLL1;
    private LinearLayout mLL2;
    private LinearLayout mLL3;
    private SimpleDraweeView mHeadImg1;
    private SimpleDraweeView mHeadImg2;
    private SimpleDraweeView mHeadImg3;

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {
        View headView = mInflater.inflate(R.layout.rank_list_head, null);
        mTimeLL = headView.findViewById(R.id.mTimeLL);
        mTimeTv = headView.findViewById(R.id.mTimeTv);
        mLL1 = headView.findViewById(R.id.mLL1);
        mLL2 = headView.findViewById(R.id.mLL2);
        mLL3 = headView.findViewById(R.id.mLL3);
        mHeadImg1 = headView.findViewById(R.id.mHeadImg1);
        mHeadImg2 = headView.findViewById(R.id.mHeadImg2);
        mHeadImg3 = headView.findViewById(R.id.mHeadImg3);
        mHeadImg1.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
        mHeadImg2.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
        mHeadImg3.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
        mRecyclerview.addHeaderView(headView);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PersonalPageActivity.class));
            }
        });
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new RankListAdapter(getActivity(), null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                startActivity(new Intent(getActivity(), PersonalPageActivity.class));
            }
        });
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
