package com.qianyu.communicate.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qianyu.communicate.utils.Contants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(Contants.WX_APP_ID);
	}
}
