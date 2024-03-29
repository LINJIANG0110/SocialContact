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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.qianyu.communicate.base.BaseActivity;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.ui.ImagesGridFragment;

import net.neiquan.applibrary.R;


public class ImagesGridActivity extends BaseActivity implements View.OnClickListener,AndroidImagePicker.OnImageSelectedListener {
    private static final String TAG = ImagesGridActivity.class.getSimpleName();

    private TextView mBtnOk;

    ImagesGridFragment mFragment;
    AndroidImagePicker androidImagePicker;
    String imagePath;

    /**
     * 预览页面
     * @param position
     */
    private void go2Preview(int position) {
        Intent intent = new Intent();
        intent.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, position);
        intent.setClass(ImagesGridActivity.this, ImagePreviewActivity.class);
        startActivityForResult(intent, AndroidImagePicker.REQ_PREVIEW);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_pic_rechoose) {
            finish();

        } else if (i == R.id.btn_ok) {
            finish();
            androidImagePicker.notifyOnImagePickComplete(androidImagePicker.getSelectedImages());
            //setResult(RESULT_OK);

        } else {
        }

    }


    @Override
    public void onImageSelected(int position, ImageItem item, int selectedItemsCount, int maxSelectLimit) {
        if(selectedItemsCount > 0){
            mBtnOk.setEnabled(true);
            //mBtnOk.setText("完成("+selectedItemsCount+"/"+maxSelectLimit+")");
            mBtnOk.setText(getResources().getString(R.string.select_complete,selectedItemsCount,maxSelectLimit));
        }else{
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == Activity.RESULT_OK){

            if(requestCode == AndroidImagePicker.REQ_CAMERA){
                Bitmap bmp = (Bitmap)data.getExtras().get("bitmap");
                Log.i(TAG,"=====get Bitmap:"+bmp.hashCode());
            }else if(requestCode == AndroidImagePicker.REQ_PREVIEW){
                setResult(RESULT_OK);
                finish();
            }

        }

    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_images_grid;
    }

    @Override
    public void setViews() {

        androidImagePicker = AndroidImagePicker.getInstance();
        androidImagePicker.clearSelectedImages();//most of the time you need to clear the last selected images or you can comment out this line

        mBtnOk = findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);

        if(androidImagePicker.getSelectMode() == AndroidImagePicker.Select_Mode.MODE_SINGLE){
            mBtnOk.setVisibility(View.GONE);
        }else{
            mBtnOk.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.btn_backpress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final boolean isCrop = getIntent().getBooleanExtra("isCrop",false);
        imagePath = getIntent().getStringExtra(AndroidImagePicker.KEY_PIC_PATH);
        mFragment = new ImagesGridFragment();
        /*Bundle data = new Bundle();
        data.putString(AndroidImagePicker.KEY_PIC_PATH,imagePath);
        mFragment.setArguments(data);*/

        mFragment.setOnImageItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position = androidImagePicker.isShouldShowCamera() ? position-1 : position;

                if(androidImagePicker.getSelectMode() == AndroidImagePicker.Select_Mode.MODE_MULTI){
                    go2Preview(position);
                }else if(androidImagePicker.getSelectMode() == AndroidImagePicker.Select_Mode.MODE_SINGLE){
                    if(isCrop){
                       /* Intent intent = new Intent();
                        intent.setClass(ImagesGridActivity.this,ImageCropActivity.class);
                        intent.putExtra(AndroidImagePicker.KEY_PIC_PATH,androidImagePicker.getImageItemsOfCurrentImageSet().get(position).path);
                        startActivityForResult(intent, AndroidImagePicker.REQ_CAMERA);*/
                    }else{
                        androidImagePicker.clearSelectedImages();
                        androidImagePicker.addSelectedImageItem(position, androidImagePicker.getImageItemsOfCurrentImageSet().get(position));
                        setResult(RESULT_OK);
                        finish();
                    }

                }

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();

        androidImagePicker.addOnImageSelectedListener(this);

        int selectedCount = androidImagePicker.getSelectImageCount();
        onImageSelected(0, null, selectedCount, androidImagePicker.getSelectLimit());
    }

    @Override
    public void initData() {

    }


}
