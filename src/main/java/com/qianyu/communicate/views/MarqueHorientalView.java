package com.qianyu.communicate.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by JavaDog on 2018-8-27.
 */

public class MarqueHorientalView extends TextView{
    public MarqueHorientalView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarqueHorientalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueHorientalView(Context context) {
        super(context);
    }

    @Override

    public boolean isFocused() {
        return true;
    }
}
