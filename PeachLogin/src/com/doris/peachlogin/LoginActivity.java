package com.doris.peachlogin;

import com.doris.peach.aidl.AIDLUser;
import com.doris.peach.aidl.Login;
import com.doris.peachlogin.util.Log;
import com.doris.peachlogin.util.ToastView;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * @author Doris
 *
 *         2016年6月27日
 */
public class LoginActivity extends Activity {

	private EditText userName, password;

	private Login login;
	private Intent service;

	ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			login = Login.Stub.asInterface(service);
			ToastView.showToast(LoginActivity.this, R.drawable.ic_toast_ok, 
					R.drawable.ic_toast_flag_ok, "连接成功");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		userName = (EditText) findViewById(R.id.et_username);
		password = (EditText) findViewById(R.id.et_password);
		
		service = new Intent();
		service.setAction("com.doris.peach.aidl.Login");
	}

	public void login(View view) {
		try{
			String name = userName.getText().toString();
			String pwd = password.getText().toString();
			if(name.length() > 0){
				if(pwd.length() > 0){
					AIDLUser user = login.login(name, pwd);
					if(user != null){
						ToastView.showToast(this, R.drawable.ic_toast_ok, 
								R.drawable.ic_toast_flag_ok, user.toString());
					}else{
						ToastView.showToast(this, R.drawable.ic_toast_verbose, 
								R.drawable.ic_toast_flag_verbose, "用户名或密码错误");
					}
				}else{
					ToastView.showToast(this, R.drawable.ic_toast_verbose, 
							R.drawable.ic_toast_flag_verbose, "请输入密码");
				}
			}else{
				ToastView.showToast(this, R.drawable.ic_toast_verbose, 
						R.drawable.ic_toast_flag_verbose, "请输入用户名");
			}
		}catch(Exception e){
			ToastView.showToast(this, R.drawable.ic_toast_error,
					R.drawable.ic_toast_flag_error, "AIDL登陆出错");
			Log.getInstance().writeLog("AIDL登陆出错：" + e.getStackTrace().toString());
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//绑定服务
		bindService(service, connection, Service.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//解绑服务
		unbindService(connection);
	}
}
