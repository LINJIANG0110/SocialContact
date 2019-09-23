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

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.base.BaseActivity;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.ui.AvatarCropFragment;


import net.neiquan.applibrary.R;

import org.haitao.common.utils.AppLog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 截取头像
 */
public class ImageCropActivity extends BaseActivity implements View.OnClickListener {

    private TextView btnReChoose;
    private TextView btnOk;
    private ImageView ivShow;

    AvatarCropFragment mFragment;

    String imagePath;

    @Override
    public int getRootViewId() {
        return R.layout.activity_crop;
    }

    @Override
    public void setViews() {

        btnOk = findViewById(R.id.btn_pic_ok);
        btnReChoose = findViewById(R.id.btn_pic_rechoose);
        ivShow = findViewById(R.id.iv_show);
        btnOk.setOnClickListener(this);
        btnReChoose.setOnClickListener(this);

        imagePath = getIntent().getStringExtra(AndroidImagePicker.KEY_PIC_PATH);
        mFragment = new AvatarCropFragment();
        Bundle data = new Bundle();
        data.putString(AndroidImagePicker.KEY_PIC_PATH, imagePath);
        mFragment.setArguments(data);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mFragment)
                .commit();

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_pic_rechoose) {


        } else if (i == R.id.btn_pic_ok) {//                //
            //
            Bitmap bmp = mFragment.getCropBitmap(120 * 2);
//                finish();
            String s = saveBitmapFile(bmp);
            AndroidImagePicker.getInstance().notifyImageCropComplete(bmp, 0);
            ivShow.setVisibility(View.VISIBLE);
            ivShow.setImageBitmap(bmp);
            Intent data = new Intent();
            data.putExtra("bitmap", bmp);
            data.putExtra("bitmappath", s);
            setResult(RESULT_OK, data);
            finish();

        } else {
        }
    }

    public String saveBitmapFile(Bitmap bitmap) {
        String pictureName = getRandomName() + ".png";
        File file = new File("/mnt/sdcard/", pictureName); //将要保存图片的路径
        String path = file.getPath();
        if (file.exists()) {
            file.delete();
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            AppLog.e("----=======");
            bos.flush();
            bos.close();
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRandomName() {
        return dateToString(new Date(), "yyyy_MM_dd_HH_mm_ss");
    }

    public static String dateToString(Date data, String formatType) {
        return (new SimpleDateFormat(formatType)).format(data);
    }

}
