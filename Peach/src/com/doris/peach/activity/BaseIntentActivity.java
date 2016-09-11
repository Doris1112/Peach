package com.doris.peach.activity;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author Doris
 *
 * 2016年4月13日
 */
public class BaseIntentActivity {

	/**
	 * 
	 * @param intent
	 * @param packageContext
	 * @param cls
	 */
	protected static void goActivity(Intent intent, Context packageContext, Class cls) {
		intent.setClass(packageContext, cls);
		packageContext.startActivity(intent);
	}
}
