package com.doris.peach;

import java.io.File;

import com.doris.peach.activity.BaseActivity;
import com.doris.peach.aidl.AIDLUser;
import com.doris.peach.aidl.Login;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.util.FileUtil;
import com.doris.peachlibrary.util.Log;
import com.doris.peachlibrary.view.ToastView;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

/**
 * AIDL
 * @author Doris
 *
 * 2016年4月20日
 */
public class AIDLActivity extends BaseActivity {

	private EditText et_aidl_username;
	private EditText et_aidl_password;
	
	private Intent service;
	private Login login;
	private DialogUtil dialogUtil;
	// 文件路径
	public static final String FILEPATH = "/sdcard/peach/resource/apk/PeachLogin.apk";
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				dialogUtil.hideDialogLoading();
				installApk();
				break;
			}
		};
	};
	
	ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			login = Login.Stub.asInterface(service);
			ToastView.showWhiteContentToast(AIDLActivity.this, R.drawable.ic_toast_flag_ok, "连接成功");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aidl);
		
		setTitle();
		et_aidl_username = (EditText) findViewById(R.id.et_aidl_username);
		et_aidl_password = (EditText) findViewById(R.id.et_aidl_password);
		
		service = new Intent();
		service.setAction("com.doris.peach.aidl.Login");
		service.setPackage(getPackageName());
		
		dialogUtil = new DialogUtil(this);
	}
	
	public void installPeachLoginApk(View view){
		File file = new File(FILEPATH);
		if (!FileUtil.isFileExist(FILEPATH.substring(0, FILEPATH.lastIndexOf("/")), file)) {
			// 复制
			dialogUtil.dialogLoading1(false);
			new Thread(){
				public void run() {
					FileUtil.assetsCopyToSD(FILEPATH.substring(0, FILEPATH.lastIndexOf("/")),
							"apk/PeachLogin.apk", FILEPATH.substring(FILEPATH.lastIndexOf("/")), 
							AIDLActivity.this);
					handler.sendEmptyMessage(0);
				};
			}.start();
		}else{
			installApk();
		}
	}
	
	public void login(View view){
		try{
			String userName = et_aidl_username.getText().toString();
			String password = et_aidl_password.getText().toString();
			if(userName.length() > 0){
				if(password.length() > 0){
					AIDLUser user = login.login(userName, password);
					if(user != null){
						ToastView.showWhiteContentToast(AIDLActivity.this, R.drawable.ic_toast_flag_ok, user.toString());
					}else{
						ToastView.showWhiteContentToast(this, 
								R.drawable.ic_toast_flag_verbose, "用户名或密码错误");
					}
				}else{
					ToastView.showWhiteContentToast(this, 
							R.drawable.ic_toast_flag_verbose, "请输入密码");
				}
			}else{
				ToastView.showWhiteContentToast(this, 
						R.drawable.ic_toast_flag_verbose, "请输入用户名");
			}
		}catch(Exception e){
			ToastView.showWhiteContentToast(this, 
					R.drawable.ic_toast_flag_verbose, "AIDL登陆出错");
			Log.getInstance().writeLog("AIDL登陆出错：", e);
		}
	}
	
	private void installApk() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + FILEPATH), "application/vnd.android.package-archive");
		startActivity(intent);
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
