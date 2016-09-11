package com.doris.peach.activity.dialog;

import com.doris.peach.R;
import com.doris.peachlibrary.view.ToastView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * @author Doris
 *
 * 2016年4月28日
 */
public class DialogImitationWeChatActivity2 extends Activity implements OnClickListener{
	
	private LinearLayout layout1, layout2, layout3, layout4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_imitation_wechat);
		
		layout1 = (LinearLayout) findViewById(R.id.ll_dialog_wechat_layout1);
		layout2 = (LinearLayout) findViewById(R.id.ll_dialog_wechat_layout2);
		layout3 = (LinearLayout) findViewById(R.id.ll_dialog_wechat_layout3);
		layout4 = (LinearLayout) findViewById(R.id.ll_dialog_wechat_layout4);
		
		layout1.setOnClickListener(this);
		layout2.setOnClickListener(this);
		layout3.setOnClickListener(this);
		layout4.setOnClickListener(this);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_dialog_wechat_layout1:
			ToastView.showToast(this, 
					R.drawable.ic_listselector, 
					R.drawable.ic_toast_flag_ok, 
					getString(R.string.aChat), 
					Toast.LENGTH_SHORT);
			break;
		case R.id.ll_dialog_wechat_layout2:
			ToastView.showToast(this, 
					R.drawable.ic_listselector, 
					R.drawable.ic_toast_flag_ok, 
					getString(R.string.theReceiverModel), 
					Toast.LENGTH_SHORT);
			break;
		case R.id.ll_dialog_wechat_layout3:
			ToastView.showToast(this, 
					R.drawable.ic_listselector, 
					R.drawable.ic_toast_flag_ok, 
					getString(R.string.theLoginPage), 
					Toast.LENGTH_SHORT);
			break;
		case R.id.ll_dialog_wechat_layout4:
			ToastView.showToast(this, 
					R.drawable.ic_listselector, 
					R.drawable.ic_toast_flag_ok, 
					getString(R.string.scan), 
					Toast.LENGTH_SHORT);
			break;
		}
		finish();
	}
}
