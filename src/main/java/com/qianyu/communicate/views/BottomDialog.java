package com.qianyu.communicate.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.FrameLayout;

import com.qianyu.communicate.R;

/**
 * Created by JavaDog on 2019-9-9.
 */

public class BottomDialog extends BottomSheetDialog {
    int peekHeight = 0;

    private BottomSheetBehavior<View> mBottomSheetBehavior;

    public BottomDialog(@NonNull Context context) {
        super(context);
    }

    public BottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    public BottomDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void show() {
        super.show();
        if (peekHeight == 0) {
            View container = findViewById(R.id.content_layout);// content_layout
            container.measure(0, 0);
            peekHeight = container.getMeasuredHeight();
            FrameLayout bottomSheet = (FrameLayout) findViewById(android.support.design.R.id.design_bottom_sheet);
            BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setPeekHeight(peekHeight);
        }
    }
}