package com.doris.peach.application;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.doris.peachlibrary.util.CrashHandler;

import android.app.Application;

/**
 * 
 * @author Doris
 *
 * 2016年4月19日
 */
public class PeachApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		ApiStoreSDK.init(this, "a654435075d19d1d8a342ffbad713e42");
		
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		
	}
	
}
