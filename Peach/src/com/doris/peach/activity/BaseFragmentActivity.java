package com.doris.peach.activity;

import com.doris.peach.R;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

/**
 * 
 * @author Doris
 *
 * 2016年5月19日
 */
public class BaseFragmentActivity extends FragmentActivity {

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
	}
	
	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		super.startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
}
