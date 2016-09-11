package com.doris.peach.activity.dialog;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 
 * @author Doris
 *
 * 2016年4月28日
 */
public class ImitationWeChatActivity2 extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imitation_wechat);
		setContentUnderStatus(findViewById(R.id.ll_imitationwechat_content));
	}
	
	public void showPopupwindow(View view){
		startActivity(new Intent(this,
				DialogImitationWeChatActivity2.class));
	}
}
