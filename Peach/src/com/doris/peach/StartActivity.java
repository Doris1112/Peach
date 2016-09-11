package com.doris.peach;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;
import com.doris.peachlibrary.util.SharedPreferencesUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

/**
 * 
 * @author Doris
 *
 * 2016年4月9日
 */
public class StartActivity extends Activity {

	private LinearLayout ll_start;
	private Animation start_aplaha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		ll_start = (LinearLayout) findViewById(R.id.ll_start);

		// 得到动画
		start_aplaha = AnimationUtils.loadAnimation(this, R.anim.start_aplaha);
		// 给动画设置监听
		start_aplaha.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				// 动画结束跳转主界面
				Intent intent = new Intent(StartActivity.this, MainActivity.class);
				startActivity(intent);
				// 把该页面finish
				StartActivity.this.finish();
				
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});
	
		//给布局设置动画
		ll_start.startAnimation(start_aplaha);
		
		boolean isFirst = (Boolean) SharedPreferencesUtil.getValue(
				this, "isFirst", true);
		DBUtil dbUtil = new DBUtil(this);
		if(isFirst){
			copyDb();
			SharedPreferencesUtil.setValues(this, 
					new String[] { "isFirst" }, new Object[] { false });
		}
		BaseData.getLists(dbUtil);
	}
	
	/**
	 * 复制数据库
	 */
	public void copyDb(){
		File file=getDatabasePath("peach.db");
		//如果不存在数据库
		if(!file.exists()){
			File file_parent=file.getParentFile();
			//如果不存在文件夹
			if(!file_parent.exists()){
				file_parent.mkdir();//创建文件夹
			}
			try {
				//复制数据库
				InputStream is=this.getAssets().open("database/peach.db");
				OutputStream os=new FileOutputStream(file);
				int len=0;
				byte[] buffer=new byte[1024];
				while((len=is.read(buffer))!=-1){
					os.write(buffer,0,len);
					os.flush();
				}
				os.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
