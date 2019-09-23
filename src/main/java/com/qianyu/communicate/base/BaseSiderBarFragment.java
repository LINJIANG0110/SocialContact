package com.qianyu.communicate.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import net.neiquan.applibrary.R;
import net.neiquan.applibrary.adpter.SortAdapter;
import net.neiquan.applibrary.bean.SortModel;
import net.neiquan.applibrary.utils.CharacterParser;
import net.neiquan.applibrary.utils.PinyinComparator;
import net.neiquan.applibrary.wight.MySideBar;

import java.util.Collections;
import java.util.List;

/**
 * Created by wl_user on 2017/7/25.
 */

public abstract class BaseSiderBarFragment extends BaseFragment {
    private ListView mListView;
    private TextView mTvDialog;
    private MySideBar mSiderBar;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private List<SortModel> SourceDateList;
    public SortAdapter adapter;
    private RelativeLayout headView;

    @Override
    public int getRootViewId() {
        return R.layout.base_siderbar_layout;
    }

    @Override
    public void setViews() {
        mListView = rootView.findViewById(R.id.mListView);
        mTvDialog = rootView.findViewById(R.id.mTvDialog);
        mSiderBar = rootView.findViewById(R.id.mSiderBar);
        headView = rootView.findViewById(R.id.head_view);
        headView.setVisibility(isHaveHead() == true ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initData() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        mSiderBar.setTextView(mTvDialog);
        // 设置右侧触摸监听
        mSiderBar.setOnTouchingLetterChangedListener(new MySideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }

            }
        });
        //item点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                onItemClickListener(position, ((SortModel) adapter.getItem(position)));
            }
        });
        //数据填充
        SourceDateList = filledData(characterParser);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(getActivity().getApplication(), SourceDateList);
        mListView.setAdapter(adapter);
        init();
    }

    /**
     * 为ListView填充数据
     * @param characterParser
     * @return
     */
    public abstract List<SortModel> filledData(CharacterParser characterParser);
    //        private List<SortModel> filledData(CharacterParser characterParser){
    //    List<SortModel> mSortList = new ArrayList<SortModel>();
//        for (int i = 0; i < date.length; i++) {
//        SortModel sortModel = new SortModel();
//        sortModel.setName(date[i]);
//        // 汉字转换成拼音
//        String pinyin = characterParser.getSelling(date[i]);
//        String sortString = pinyin.substring(0, 1).toUpperCase();
//        // 正则表达式，判断首字母是否是英文字母
//        if (sortString.matches("[A-Z]")) {
//            sortModel.setSortLetters(sortString.toUpperCase());
//        } else {
//            sortModel.setSortLetters("#");
//        }
//        mSortList.add(sortModel);
//    }
//        return mSortList;

    public abstract void onItemClickListener(int position, SortModel model);

    public abstract boolean isHaveHead();

    public abstract void init();
}
