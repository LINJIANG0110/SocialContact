package com.qianyu.communicate.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.GiftNumAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.FragmentAdapter;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.GiftList;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.fragment.GiftFragment;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpringUtils;

import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-3-12.
 */

public class GiftListActivity extends BaseActivity {

    @InjectView(R.id.head_view)
    RelativeLayout headView;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    @InjectView(R.id.remainLogo)
    ImageView remainLogo;
    @InjectView(R.id.remainMoney)
    TextView remainMoney;
    @InjectView(R.id.addNum)
    ImageView addNum;
    @InjectView(R.id.mRechargeLL)
    LinearLayout mRechargeLL;
    @InjectView(R.id.sendNum)
    TextView sendNum;
    @InjectView(R.id.mGiftLL)
    LinearLayout mGiftLL;
    @InjectView(R.id.sendGift)
    TextView sendGift;
    private String[] titles = new String[]{"", "", ""};
    private long noId = 1;
    private long userId;
    private GiftNumAdapter oderPopAdapter;
    private GiftList giftList;
    private String phone;
    private PersonalInfo personalInfo;

    public ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        GiftFragment giftFragment1 = new GiftFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("gift", 1);
        bundle1.putLong("userId", userId);
        bundle1.putString("phone", phone);
        giftFragment1.setArguments(bundle1);
        fragments.add(giftFragment1);

        GiftFragment giftFragment2 = new GiftFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("gift", 2);
        bundle2.putLong("userId", userId);
        bundle2.putString("phone", phone);
        giftFragment2.setArguments(bundle2);
        fragments.add(giftFragment2);

        GiftFragment giftFragment3 = new GiftFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("gift", 3);
        bundle3.putLong("userId", userId);
        bundle3.putString("phone", phone);
        giftFragment3.setArguments(bundle3);
        fragments.add(giftFragment3);
        return fragments;
    }

    private int[] tabIcons = {
            R.mipmap.wallet,
            R.mipmap.coin,
            R.mipmap.diamond,
    };

    private int[] tabIconsPressed = {
            R.mipmap.wallet1,
            R.mipmap.coin1,
            R.mipmap.diamond1,
    };

    @Override
    public int getRootViewId() {
        return R.layout.activity_gift_list;
    }

    @Override
    public void setViews() {
        if (getIntent() != null) {
            userId = getIntent().getLongExtra("userId", 0);
            phone = getIntent().getStringExtra("phone");
        }
        EventBus.getDefault().register(this);
        setSystemBarTint_();
        setTitleTv("礼物");
        setNextTv("礼物说明");
        headView.setBackground(getResources().getDrawable(R.drawable.toolbar_bg_bmp));
        setupViewPager();
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getNextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGiftStatePop();
            }
        });
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.GIFT_DATA) {
//            giftList = (GiftList) event.getMessage();
//            if (remainMoney != null) {
//                remainMoney.setText(giftList.getUsersPice() + "");
//            }
            initMoney();// 刷新
            if (giftList != null) {
                List<GiftList.SouvenirNosBean> souvenirNos = giftList.getSouvenirNos();
                for (int i = 0; i < souvenirNos.size(); i++) {
                    if (1 == souvenirNos.get(i).getQuantity()) {
                        noId = souvenirNos.get(i).getId();
                    }
                }
            }
        }
    }

    public void initMoney(){
        if (MyApplication.getInstance().isLogin()) {
            User user = MyApplication.getInstance().user;
            NetUtils.getInstance().mineInfo(user.getUserId(), new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    personalInfo = (PersonalInfo) model.getModel();
//                    remainMoney.setText(NumberUtils.rounLong(personalInfo.getFubao()));
                    int position = mViewPager.getCurrentItem();
                    if (personalInfo != null) {
                        remainLogo.setImageResource(position == 0 ? R.mipmap.wallet1 : (position == 1 ? R.mipmap.coin1 : R.mipmap.diamond1));
                        remainMoney.setText((position == 0 ? NumberUtils.rounLong(personalInfo.getFubao()) : (position == 1 ? NumberUtils.rounLong(personalInfo.getGold()) : NumberUtils.rounLong(personalInfo.getDiamond()))) + "");
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, PersonalInfo.class);
        }
    }
    @Override
    public void initData() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (personalInfo != null) {
                    remainLogo.setImageResource(position == 0 ? R.mipmap.wallet1 : (position == 1 ? R.mipmap.coin1 : R.mipmap.diamond1));
                    remainMoney.setText((position == 0 ? NumberUtils.rounLong(personalInfo.getFubao()) : (position == 1 ? NumberUtils.rounLong(personalInfo.getGold()) : NumberUtils.rounLong(personalInfo.getDiamond()))) + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initMoney();
    }

    @OnClick({R.id.mRechargeLL, R.id.mGiftLL, R.id.sendGift})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mGiftLL:
                showGiftNumPopWindow();
                break;
            case R.id.sendGift:
                sendGift();
                break;
            case R.id.mRechargeLL:
                SpringUtils.springAnim(addNum);
                startActivity(new Intent(GiftListActivity.this, WalletActivity.class));
                break;
        }
    }

    private void showGiftStatePop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.gift_state_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        TextView mGiftState1 = (TextView) view.findViewById(R.id.mGiftState1);
                        TextView mGiftSureTv = (TextView) view.findViewById(R.id.mGiftSureTv);
                        TextView mGiftState3 = (TextView) view.findViewById(R.id.mGiftState3);
                        Spanned spanned1 = Html.fromHtml("1、赠送礼物会增加对方的 <font color='#699AFA'>魅力值</font>，自己获得<font color='#FDD96A'>富豪值</font>" +
                                ";积累<font color='#699AFA'>魅力值</font>可以参与魅力榜排名，积累<font color='#FDD96A'>富豪值</font>可以参与富豪榜的排名。");
                        mGiftState1.setText(spanned1);
                        Spanned spanned2 = Html.fromHtml("3、使用 <font color='#699AFA'>钻石</font>赠送礼物会增加对方的<font color='#699AFA'>魅力值</font>" +
                                "，自己获得<font color='#73F0EB'>经验值</font>跟<font color='#FDD96A'>富豪值</font>，并且有几率获得最高500倍等量的金币中奖返还（批量赠送时按每个道具来计算）。");
                        mGiftState3.setText(spanned2);
                        mGiftSureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mTabLayout, Gravity.CENTER, 0, 0);
    }

    private void sendGift() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(GiftListActivity.this, RegistActivity.class));
            return;
        }
        List<GiftList.ContentBean> data = giftList.getContent();
        long gid = 0;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                GiftList.ContentBean goodSBean = data.get(i);
                if (goodSBean.isSelected()) {
                    gid = goodSBean.getGiftId();
                }
            }
            if (gid <= 0) {
                ToastUtil.shortShowToast("请先选择礼物...");
                return;
            }
            //passivityUserId  被送礼物人的id
//            Tools.showDialog(GiftActivity.this);
        } else {
            ToastUtil.shortShowToast("暂无礼物...");
        }
    }

    private void showGiftNumPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.gift_num_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1.0f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RecyclerView mRecylerView = view.findViewById(R.id.mRecylerView);
                        initRecylerView(mRecylerView);
                        oderPopAdapter = new GiftNumAdapter(GiftListActivity.this, null);
                        mRecylerView.setAdapter(oderPopAdapter);
                        oderPopAdapter.appendAll(giftList.getSouvenirNos());
                        oderPopAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, List data, int position) {
                                GiftList.SouvenirNosBean souvenirNosBean = (GiftList.SouvenirNosBean) data.get(position);
                                noId = souvenirNosBean.getId();
                                sendNum.setText("" + souvenirNosBean.getQuantity());
                                popupWindow.dismiss();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
//        popupWindow.showAsDropDown(mGiftLL,0, 10);
        int[] location = new int[2];
        mGiftLL.getLocationOnScreen(location);
        popupWindow.showAtLocation(mGiftLL, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight() - 40);
    }

    private void initRecylerView(RecyclerView mRecylerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(layoutManager);
    }

    private void setupViewPager() {
        ArrayList<Fragment> fragments = getFragments();
        if (titles != null && titles.length > 0 && fragments != null && fragments.size() > 0 && titles.length == fragments.size()) {
//            for (int i = 0; i < title.length; i++) {
//                mTabLayout.addTab(mTabLayout.newTab().setText(title[i]));
//            }
            FragmentAdapter adapter =
                    new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
            mViewPager.setAdapter(adapter);
            mTabLayout.setupWithViewPager(mViewPager);
            mTabLayout.setTabsFromPagerAdapter(adapter);
            for (int i = 0; i < titles.length; i++) {
                mTabLayout.getTabAt(i).setCustomView(getTabView(i, titles));
            }
        }
    }

    public View getTabView(int position, String[] titles) {
        //这是头部导航的布局
        View view = LayoutInflater.from(this).inflate(net.neiquan.applibrary.R.layout.item_tab, null);
        TextView txt_title = view.findViewById(net.neiquan.applibrary.R.id.txt_title);
        txt_title.setText(titles[position]);
        ImageView img_title = view.findViewById(net.neiquan.applibrary.R.id.img_title);
        img_title.setImageResource(tabIcons[position]);
        if (position == 0) {
            //这个地方设置初始字体选中颜色
            txt_title.setTextColor(Color.RED);
            img_title.setImageResource(tabIconsPressed[position]);
        } else {
            //这个地方设置初始字体未选中颜色
            txt_title.setTextColor(Color.BLACK);
            img_title.setImageResource(tabIcons[position]);
        }
        return view;
    }

    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = view.findViewById(net.neiquan.applibrary.R.id.img_title);
        TextView txt_title = view.findViewById(net.neiquan.applibrary.R.id.txt_title);
        //这个地方设置字体选中颜色
        txt_title.setTextColor(Color.RED);
        if (titles != null && titles.length > 0 && tabIconsPressed != null && tabIconsPressed.length > 0 && titles.length == tabIconsPressed.length) {
            img_title.setImageResource(tabIconsPressed[tab.getPosition()]);
        }
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = view.findViewById(net.neiquan.applibrary.R.id.img_title);
        TextView txt_title = view.findViewById(net.neiquan.applibrary.R.id.txt_title);
        //这个地方设置字体未选中颜色
        txt_title.setTextColor(Color.BLACK);
        if (titles != null && titles.length > 0 && tabIcons != null && tabIcons.length > 0 && titles.length == tabIcons.length) {
            img_title.setImageResource(tabIcons[tab.getPosition()]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
