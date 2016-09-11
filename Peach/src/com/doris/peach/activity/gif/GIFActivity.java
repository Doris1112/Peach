package com.doris.peach.activity.gif;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.GIFCustomView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * GIF
 * @author Doris
 *
 * 2016年4月29日
 */
public class GIFActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gif);
		
		setTitle();
		
		LinearLayout ll_gif = (LinearLayout) findViewById(R.id.ll_gif);
		ll_gif.addView(new GIFCustomView(this, R.drawable.ic_gif_image));
	}
	
	public void openTheGIFImages(View view){
		startActivity(new Intent(this, GIFWebActivity.class));
	}
}
