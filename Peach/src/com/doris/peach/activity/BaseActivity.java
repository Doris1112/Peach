package com.doris.peach.activity;

import com.doris.peach.R;
import com.doris.peach.data.BaseData;
import com.doris.peachlibrary.util.DeviceUtil;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年4月12日
 */
public class BaseActivity extends Activity {

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

	/**
	 * 设置标题
	 */
	public void setTitle() {
		TextView title = (TextView) findViewById(R.id.tv_activity_title);
		title.setText(BaseData.item[1]);
	}

	/**
	 * 设置内容在状态栏之下
	 */
	public void setContentUnderStatus(View view){
		view.setPadding(0, DeviceUtil.getStatusBarHeight(this), 0, 0);
	}
}
