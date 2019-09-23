/*
 *
 *  * Copyright (C) 2015 Eason.Lai (easonline7@gmail.com)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.qianyu.communicate.image;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyu.communicate.base.BaseActivity;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.ui.ImagePreviewFragment;


import net.neiquan.applibrary.R;

import java.util.ArrayList;

//public class ImagePreviewActivity extends BaseActivity implements View.OnClickListener, ImagePreviewFragment.OnImageSingleTapClickListener, ImagePreviewFragment.OnImagePageSelectedListener, AndroidImagePicker.OnImageSelectedListener {
public class ImagePreviewActivity extends BaseActivity implements View.OnClickListener, ImagePreviewFragment.OnImagePageSelectedListener, AndroidImagePicker.OnImageSelectedListener {
    private static final String TAG = ImagePreviewActivity.class.getSimpleName();

    ImagePreviewFragment mFragment;
    TextView mTitleCount;
    CheckBox mCbSelected;
    TextView mBtnOk;

    ArrayList<ImageItem> mImageList;
    int mShowItemPosition = 0;
    AndroidImagePicker androidImagePicker;
    public static String IMAGEURL = "image_urls";//网络图片

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_pic_rechoose) {
            finish();

        } else if (i == R.id.btn_ok) {
            setResult(RESULT_OK);// select complete
            finish();
        } else {
        }
    }


//    @Override
//    public void onImageSingleTap(MotionEvent e) {
//        finish();
//        View topBar = findViewById(R.id.top_bar);
//        View bottomBar = findViewById(R.id.bottom_bar);
//        if (topBar.getVisibility() == View.VISIBLE) {
//            topBar.setAnimation(AnimationUtils.loadAnimation(ImagePreviewActivity.this, R.anim.top_out));
//            bottomBar.setAnimation(AnimationUtils.loadAnimation(ImagePreviewActivity.this, R.anim.fade_out));
//            topBar.setVisibility(View.GONE);
//            bottomBar.setVisibility(View.GONE);
//        } else {
////            topBar.setAnimation(AnimationUtils.loadAnimation(ImagePreviewActivity.this, R.anim.top_in));
////            bottomBar.setAnimation(AnimationUtils.loadAnimation(ImagePreviewActivity.this, R.anim.fade_in));
////            topBar.setVisibility(View.VISIBLE);
////            bottomBar.setVisibility(View.VISIBLE);
//        }
//
//    }

    @Override
    public void onImagePageSelected(int position, ImageItem item, boolean isSelected) {
        mTitleCount.setText(position + 1 + "/" + mImageList.size());
        mCbSelected.setChecked(isSelected);
    }

    @Override
    public void onImageSelected(int position, ImageItem item, int selectedItemsCount, int maxSelectLimit) {
        if (selectedItemsCount > 0) {
            mBtnOk.setEnabled(true);
            mBtnOk.setText(getResources().getString(R.string.select_complete, selectedItemsCount + "", maxSelectLimit + ""));
        } else {
            mBtnOk.setText(getResources().getString(R.string.complete));
            mBtnOk.setEnabled(false);
        }
        Log.i(TAG, "=====EVENT:onImageSelected");
    }

    @Override
    protected void onDestroy() {
        androidImagePicker.removeOnImageItemSelectedListener(this);
        Log.i(TAG, "=====removeOnImageItemSelectedListener");
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        return R.layout.activity_image_pre;
    }

    @Override
    public void setViews() {

        androidImagePicker = AndroidImagePicker.getInstance();
        androidImagePicker.addOnImageSelectedListener(this);

        mShowItemPosition = getIntent().getIntExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, 0);

        mBtnOk = findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);
        mCbSelected = findViewById(R.id.btn_check);
        mImageList = getIntent().getParcelableArrayListExtra(IMAGEURL);
        if (mImageList == null) {
            mImageList = AndroidImagePicker.getInstance().getImageItemsOfCurrentImageSet();
        } else {
            mBtnOk.setVisibility(View.GONE);
            mCbSelected.setVisibility(View.GONE);
        }
        mTitleCount = findViewById(R.id.tv_title_count);
        mTitleCount.setText("1/" + mImageList.size());
        int selectedCount = AndroidImagePicker.getInstance().getSelectImageCount();

        onImageSelected(0, null, selectedCount, androidImagePicker.getSelectLimit());

        //back press
        findViewById(R.id.btn_backpress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (androidImagePicker.getSelectImageCount() > androidImagePicker.getSelectLimit()) {
                    if (mCbSelected.isChecked()) {
                        //holder.cbSelected.setCanChecked(false);
                        mCbSelected.toggle();
                        String toast = getResources().getString(R.string.you_have_a_select_limit, androidImagePicker.getSelectLimit() + "");
                        Toast.makeText(ImagePreviewActivity.this, toast, Toast.LENGTH_SHORT).show();
                    } else {
                        //
                    }
                } else {
                    //
                }
            }

        });

        mCbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFragment.selectCurrent(isChecked);
            }
        });


        mFragment = new ImagePreviewFragment();
        Bundle data = new Bundle();
        data.putParcelableArrayList(ImagePreviewActivity.IMAGEURL, mImageList);
        data.putInt(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, mShowItemPosition);
        mFragment.setArguments(data);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mFragment)
                .commit();
    }

    @Override
    public void initData() {
        mFragment.setOnImageSingleTapListener(new ImagePreviewFragment.OnImageSingleTapClickListener() {
            @Override
            public void onImageSingleTap(MotionEvent e) {
                finish();
            }
        });
    }
}
