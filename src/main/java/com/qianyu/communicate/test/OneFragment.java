package com.qianyu.communicate.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qianyu.communicate.R;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.qianyu.communicate.image.ImagePreviewActivity;

import com.qianyu.communicate.base.BaseFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */
public class OneFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.standard)
    Button standard;

    @Override
    public int getRootViewId() {
        return R.layout.home_layout;
    }

    @Override
    public void setViews() {
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.standard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.standard:
                ArrayList<String> urls = new ArrayList<>();
                urls.add("http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201204/20120412123914329.jpg");
                urls.add("http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201204/20120412123929822.jpg");
                urls.add("http://pic1.16pic.com/00/10/80/16pic_1080912_b.jpg");
                final ArrayList<ImageItem> mImageList = new ArrayList<>();
                mImageList.clear();
                if (urls != null && urls.size() > 0) {
                    for (int i = 0; i < urls.size(); i++) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.setPath(urls.get(i));
                        mImageList.add(imageItem);
                    }
                }
                Intent intent = new Intent();
                intent.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, 0);
                intent.setClass(getActivity(), ImagePreviewActivity.class);
                intent.putParcelableArrayListExtra(ImagePreviewActivity.IMAGEURL, mImageList);
                startActivity(intent);
                break;
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

