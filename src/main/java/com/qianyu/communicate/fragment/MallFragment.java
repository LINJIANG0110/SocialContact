package com.qianyu.communicate.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.MallBillActivity;
import com.qianyu.communicate.adapter.FamilyManageAdapter;
import com.qianyu.communicate.base.BaseFragment;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyManage;
import com.qianyu.communicate.entity.MallBill;
import com.qianyu.communicate.event.EventBuss;

import net.neiquan.applibrary.numberpicker.view.AlertDatePicker;
import net.neiquan.applibrary.numberpicker.view.DatePicker;
import net.neiquan.applibrary.utils.SmartItem;
import net.neiquan.applibrary.wight.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JavaDog on 2019-6-17.
 */

public class MallFragment extends BaseFragment {
    @InjectView(R.id.mSearchEt)
    EditText mSearchEt;
    @InjectView(R.id.smartTabLayout)
    SmartTabLayout mSmartTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    private MallBill mallBill = new MallBill();

    @Override
    public int getRootViewId() {
        if (getArguments() != null) {
            mallBill.setType(getArguments().getInt("type"));
        }
        return R.layout.fragment_mall;
    }


    @Override
    public void setViews() {
        List<SmartItem> smartItems = getSmartItems();
        FragmentPagerItems.Creator add = FragmentPagerItems.with(getActivity());
        if (smartItems != null && smartItems.size() > 0) {
            for (SmartItem smartItem : smartItems) {
                if (smartItem.bundleExtra != null) {
                    add = add.add(smartItem.title, smartItem.fragmentClass, smartItem.bundleExtra);
                } else {
                    add = add.add(smartItem.title, smartItem.fragmentClass);
                }
            }
            FragmentStatePagerItemAdapter adapter = new FragmentStatePagerItemAdapter(
                    getChildFragmentManager(), add
                    .create());
            mViewPager.setAdapter(adapter);
            mSmartTabLayout.setViewPager(mViewPager);
        }
    }

    @Override
    public void initData() {
        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = mSearchEt.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.shortShowToast("请先输入你要搜索的内容...");
                    } else {
                        KeyBoardUtils.hideSoftInput(mSearchEt);
                        EventBuss event = new EventBuss(EventBuss.MALL_BILL);
                        mallBill.setUserId(Long.parseLong(content));
                        event.setMessage(mallBill);
                        EventBus.getDefault().post(event);
                    }
                    return true;
                }
                return false;
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mallBill.setStartTime(0);
                        mallBill.setEndTime(0);
                        break;
                    case 1:
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, 0);
                        c.set(Calendar.MINUTE, 0);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);
                        mallBill.setStartTime(c.getTimeInMillis());
                        mallBill.setEndTime(System.currentTimeMillis());
                        break;
                    case 2:
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DATE, -7);
                        Date date = calendar.getTime();
                        mallBill.setStartTime(date.getTime());
                        mallBill.setEndTime(System.currentTimeMillis());
                        break;
                    case 3:
                        showMallTimePopWindow();
                        break;
                }
                EventBuss event = new EventBuss(EventBuss.MALL_BILL);
                event.setMessage(mallBill);
                EventBus.getDefault().post(event);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void showMallTimePopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
                //设置PopupWindow布局
                .setView(R.layout.layout_mall_time)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        TextView mCancelTv = (TextView) view.findViewById(R.id.mCancelTv);
                        TextView mSureTv = (TextView) view.findViewById(R.id.mSureTv);
                        final TextView mStartTimeTv = (TextView) view.findViewById(R.id.mStartTimeTv);
                        final TextView mEndTimeTv = (TextView) view.findViewById(R.id.mEndTimeTv);
                        final DatePicker mTimePicker = (DatePicker) view.findViewById(R.id.mTimePicker);
                        mCancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        mSureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        mStartTimeTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                falg=true;
                                mStartTimeTv.setTextColor(getResources().getColor(R.color.app_color));
                                mEndTimeTv.setTextColor(getResources().getColor(R.color.text_black));
                            }
                        });
                        mEndTimeTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                falg=false;
                                mEndTimeTv.setTextColor(getResources().getColor(R.color.app_color));
                                mStartTimeTv.setTextColor(getResources().getColor(R.color.text_black));
                            }
                        });
                        mTimePicker.setOnSelectingListener(new DatePicker.OnSelectingListener() {
                            @Override
                            public void selected(boolean selected) {
                                if(falg){
                                    mStartTimeTv.setText(mTimePicker.getSelectDateStr());
                                    mallBill.setStartTime( mTimePicker.getSelectDate().getTime());
                                }else {
                                    mEndTimeTv.setText(mTimePicker.getSelectDateStr());
                                    mallBill.setEndTime( mTimePicker.getSelectDate().getTime());
                                }
                                EventBuss event = new EventBuss(EventBuss.MALL_BILL);
                                event.setMessage(mallBill);
                                EventBus.getDefault().post(event);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mViewPager, Gravity.BOTTOM, 0, 0);
    }
    boolean falg=true;

    public List<SmartItem> getSmartItems() {
        List<SmartItem> smartItems = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mallBill", mallBill);
        smartItems.add(new SmartItem("全部", MallFuBaoFragment.class, bundle));
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("mallBill", mallBill);
        smartItems.add(new SmartItem("当天", MallFuBaoFragment.class, bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("mallBill", mallBill);
        smartItems.add(new SmartItem("一周", MallFuBaoFragment.class, bundle2));
        Bundle bundle3 = new Bundle();
        bundle3.putSerializable("mallBill", mallBill);
        smartItems.add(new SmartItem("更多", MallFuBaoFragment.class, bundle3));
        return smartItems;
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
