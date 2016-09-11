package com.doris.peach.activity.scrawl;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 * 2016年6月27日
 */
public class ShowScrawlActivity extends BaseActivity {

	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrawl_show);
		
		ImageView iv_scrawlpic = (ImageView) findViewById(R.id.iv_scrawlpic);
		String path = getIntent().getStringExtra("path");
		boolean save = getIntent().getBooleanExtra("save", false);
		if(save){
			ToastView.showWhiteContentToast(this,
					R.drawable.ic_toast_flag_verbose, 
					"保存成功：" + path );
		}
		bitmap = BitmapFactory.decodeFile(path);
		iv_scrawlpic.setImageBitmap(bitmap);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(bitmap != null){
			bitmap.recycle();
			bitmap = null;
		}
		super.onDestroy();
	}
	
}