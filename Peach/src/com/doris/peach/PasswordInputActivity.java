package com.doris.peach;

import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.view.PasswordInputView;
import com.doris.peachlibrary.view.PasswordInputView.onMaxlengthListener;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;

/**
 * 
 * @author Doris
 *
 *         2016年8月2日
 */
public class PasswordInputActivity extends BaseActivity {

	private onMaxlengthListener listener = new onMaxlengthListener() {

		@Override
		public void onMaxLlengthListner(String text) {
			// TODO Auto-generated method stub
			ToastView.showWhiteContentToast(PasswordInputActivity.this, R.drawable.ic_toast_flag_ok, text);
		}
	};
	
	private DialogUtil dialogUtil;
	private Handler handler = new Handler(){
		public void handleMessage (Message msg) {
			switch (msg.what) {
			case 0:
				dialogUtil.hideDialogLoading();
				String text = msg.getData().getString("text");
				ToastView.showWhiteContentToast(PasswordInputActivity.this, R.drawable.ic_toast_flag_ok, text);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_passwordinput);

		PasswordInputView passwordInputView = (PasswordInputView) findViewById(R.id.passwordInputView);
		passwordInputView.setListener(listener);
		PasswordInputView passwordInputView1 = (PasswordInputView) findViewById(R.id.passwordInputView1);
		passwordInputView1.setListener(listener);
		
		dialogUtil = new DialogUtil(this);
	}
	
	public void showDialog(View view){
		dialogUtil.showPassworInput(new onMaxlengthListener() {
			
			@Override
			public void onMaxLlengthListner(final String text) {
				// TODO Auto-generated method stub
				dialogUtil.dialogLoading("正在验证密码……");
				new Thread(){
					public void run() {
						SystemClock.sleep(4000);
						Message msg = new Message();
						msg.what = 0;
						Bundle bundle = msg.getData();
						bundle.putString("text", text);
						handler.sendMessage(msg);
					};
				}.start();
			}
		});
	}

}
