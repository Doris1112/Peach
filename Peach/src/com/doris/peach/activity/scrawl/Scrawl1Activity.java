package com.doris.peach.activity.scrawl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.util.ScreenShot;

import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 
 * @author Doris
 *
 * 2016年6月23日
 */
public class Scrawl1Activity extends BaseActivity {
	
	private RelativeLayout rl_scrawl1;
	private GestureOverlayView gov_scrawl;
	private Button b_scrawl1_clear, b_scrawl1_save;
	
	private DialogUtil dialogUtil;
	private  Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				dialogUtil.hideDialogLoading();
				b_scrawl1_clear.setVisibility(View.VISIBLE);
				b_scrawl1_save.setVisibility(View.VISIBLE);
				Bundle bundle = msg.getData();
				Intent intent = new Intent(Scrawl1Activity.this, ShowScrawlActivity.class);
				intent.putExtra("path", bundle.getString("path"));
				intent.putExtra("save", true);
				startActivity(intent);
				break;
			case 1:
				dialogUtil.hideDialogLoading();
				dialogUtil.showAlertDialog1("保存失败！", null);
				break;
			}
		};
	};
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrawl1);
		
		int scrawlBgFlag = getIntent().getIntExtra("scrawlBgFlag", 0);
		int scrawlBg = getIntent().getIntExtra("scrawlBg", 0);
		int scrawlBgColor = getIntent().getIntExtra("scrawlBgColor", 0);
		
		b_scrawl1_clear = (Button) findViewById(R.id.b_scrawl1_clear);
		b_scrawl1_save = (Button) findViewById(R.id.b_scrawl1_save);
		rl_scrawl1 = (RelativeLayout) findViewById(R.id.rl_scrawl1);
		switch (scrawlBgFlag) {
		case 0:
			// 图片
			if(scrawlBg != 0)
				rl_scrawl1.setBackgroundResource(scrawlBg);
			break;
		case 1:
			// 颜色
			if(scrawlBgColor != 0)
				rl_scrawl1.setBackgroundColor(scrawlBgColor);
			break;
		}
		
		gov_scrawl = (GestureOverlayView) findViewById(R.id.gov_scrawl);
		gov_scrawl.setGestureStrokeWidth(8);  
		gov_scrawl.setFadeOffset(3600000);
		
		dialogUtil = new DialogUtil(this);
	}
	
	public void manage(View view){
		switch (view.getId()) {
		case R.id.b_scrawl1_clear:
			gov_scrawl.setFadeOffset(100);//设置间隔时间
			gov_scrawl.clear(true);
			gov_scrawl.setFadeOffset(3600000);
			break;
		case R.id.b_scrawl1_save:
			final String imageName = sdf.format(new Date());
			b_scrawl1_clear.setVisibility(View.GONE);
			b_scrawl1_save.setVisibility(View.GONE);
			dialogUtil.dialogLoading("正在保存……");
			new Thread(){
				public void run() {
					String path = ScrawlActivity.path + imageName + ".png";
					if(ScreenShot.shoot(Scrawl1Activity.this, path)){
						Message msg = new Message();
						msg.what = 0;
						Bundle bundle = msg.getData();
						bundle.putString("path", path);
						handler.sendMessage(msg);
					}else{
						handler.sendEmptyMessage(1);
					}
				};
			}.start();
			break;
		}
	}
}