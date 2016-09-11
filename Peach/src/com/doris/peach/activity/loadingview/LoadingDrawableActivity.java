package com.doris.peach.activity.loadingview;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年8月13日
 */
public class LoadingDrawableActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loadingview_drawable);
		
		setContentUnderStatus(findViewById(R.id.sv_loadingvewdrawable));
	}
}
