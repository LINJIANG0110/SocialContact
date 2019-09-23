package com.qianyu.communicate.test;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseSiderBarActivity;
import net.neiquan.applibrary.bean.SortModel;
import net.neiquan.applibrary.utils.CharacterParser;

import org.haitao.common.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wl_user on 2017/7/25.
 */

public class SiderBarActivity extends BaseSiderBarActivity {

    @Override
    public boolean isHaveHead() {
        return true;
    }

    @Override
    public void init() {
        setTitleTv("选择城市");
    }

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
        return mSortList;
    }

    @Override
    public void onItemClickListener(int position, SortModel model) {
        AppLog.e("=====position=====" + position + "======model======" + model);
    }

}
