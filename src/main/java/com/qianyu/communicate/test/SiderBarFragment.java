package com.qianyu.communicate.test;

import android.content.Intent;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseSiderBarFragment;
import net.neiquan.applibrary.bean.SortModel;
import net.neiquan.applibrary.utils.CharacterParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wl_user on 2017/7/25.
 */

public class SiderBarFragment extends BaseSiderBarFragment {
    @Override
    public List<SortModel> filledData(CharacterParser characterParser) {
        String[] datas = getResources().getStringArray(R.array.date);
        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < datas.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(datas[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(datas[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;    }

    @Override
    public void onItemClickListener(int position, SortModel model) {
        startActivity(new Intent(getActivity(),TwoActivity.class));
    }

    @Override
    public boolean isHaveHead() {
        return true;
    }

    @Override
    public void init() {
        setTitleTv("选择城市");
    }
}
