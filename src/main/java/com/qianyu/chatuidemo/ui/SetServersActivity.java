package com.qianyu.chatuidemo.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qianyu.chatuidemo.DemoModel;
import com.qianyu.communicate.R;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class SetServersActivity extends FragmentActivity {

    EditText restEdit;
    EditText imEdit;
    EaseTitleBar titleBar;

    DemoModel demoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_servers);

        restEdit = findViewById(R.id.et_rest);
        imEdit = findViewById(R.id.et_im);
        titleBar = findViewById(R.id.title_bar);

        demoModel = new DemoModel(this);
        if(demoModel.getRestServer() != null)
            restEdit.setText(demoModel.getRestServer());
        if(demoModel.getIMServer() != null)
            imEdit.setText(demoModel.getIMServer());
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(!TextUtils.isEmpty(restEdit.getText()))
            demoModel.setRestServer(restEdit.getText().toString());
        if(!TextUtils.isEmpty(imEdit.getText()))
            demoModel.setIMServer(imEdit.getText().toString());
        super.onBackPressed();
    }
}
