package com.doris.peach;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.adapter.ListButtonAdapter;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.util.FileUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年7月17日
 */
public class LauncherActivity extends BaseActivity {

	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;
	
	private DialogUtil dialogUtil;
	// 文件路径
	public static final String FILEPATH = "/sdcard/peach/resource/apk/PeachLauncherScene.apk";
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);
		
		setTitle();
		initList();
		
		ListView lv_listButton = (ListView) findViewById(R.id.lv_listButton);
		lv_listButton.setAdapter(new ListButtonAdapter(list, listener, context));
		
	}
	
	private void initList(){
		context = this;
		dialogUtil = new DialogUtil(context);
		
		list.add(getString(R.string.installLauncherScene));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File file = new File(FILEPATH);
				if (!FileUtil.isFileExist(FILEPATH.substring(0, FILEPATH.lastIndexOf("/")), file)) {
					// 复制
					dialogUtil.dialogLoading1(false);
					new Thread(){
						public void run() {
							FileUtil.assetsCopyToSD(FILEPATH.substring(0, FILEPATH.lastIndexOf("/")),
									"apk/PeachLauncherScene.apk", FILEPATH.substring(FILEPATH.lastIndexOf("/")), 
									LauncherActivity.this);
							handler.sendEmptyMessage(0);
						};
					}.start();
				}else{
					installApk();
				}
			}
		});
		/*list.add(getResources().getString(R.string.effect2));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, Win8ButtonActivity2.class));
			}
		});*/
	}
	
	private void installApk() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + FILEPATH), "application/vnd.android.package-archive");
		startActivity(intent);
	}
}
