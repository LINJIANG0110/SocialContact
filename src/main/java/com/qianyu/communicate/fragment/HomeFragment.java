package com.qianyu.communicate.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.chatuidemo.ui.ConversationListFragment;
import com.qianyu.communicate.R;
import com.qianyu.communicate.utils.SpringUtils;

import com.qianyu.communicate.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.msgTabTv)
    TextView msgTabTv;
    @InjectView(R.id.friendTabTv)
    TextView friendTabTv;
    @InjectView(R.id.addFriendLogo)
    ImageView addFriendLogo;
    @InjectView(R.id.line)
    View line;
    @InjectView(R.id.homeFrameLayout)
    FrameLayout homeFrameLayout;
    private FragmentTransaction fragmentTransaction;
//    private Fragment fragments[] = {new HomeFriendFragment(), new HomeMsgFragment()};
//private Fragment fragments[] = {new HomeFriendFragment(), new ConversationListFragment()};
    private Fragment fragments[] = {new ConversationListFragment(), new HomeFriendFragment()};

    @Override
    public int getRootViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void setViews() {
        setDefaultFragment(true, 0);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.msgTabTv, R.id.friendTabTv, R.id.addFriendLogo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.msgTabTv:
                changeTabCorlor(true);
                setDefaultFragment(false, 0);
                break;
            case R.id.friendTabTv:
                changeTabCorlor(false);
                setDefaultFragment(false, 1);
                break;
            case R.id.addFriendLogo:
                SpringUtils.springAnim(addFriendLogo);
//                Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
//                getActivity().startActivity(intent);
                break;
        }
    }

    private void setDefaultFragment(boolean first, int index) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (first) {
            fragmentTransaction.add(R.id.homeFrameLayout, fragments[0]);
            fragmentTransaction.add(R.id.homeFrameLayout, fragments[1]);
        }
        fragmentTransaction.hide(fragments[0]);
        fragmentTransaction.hide(fragments[1]);
        fragmentTransaction.show(fragments[index]);
        fragmentTransaction.commit();
    }


    private void changeTabCorlor(boolean tab1) {
        if (tab1) {
            msgTabTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_left2));
            msgTabTv.setTextColor(getResources().getColor(R.color.white));
            friendTabTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_right1));
            friendTabTv.setTextColor(getResources().getColor(R.color.app_color));
        } else {
            msgTabTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_left1));
            msgTabTv.setTextColor(getResources().getColor(R.color.app_color));
            friendTabTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_right2));
            friendTabTv.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
