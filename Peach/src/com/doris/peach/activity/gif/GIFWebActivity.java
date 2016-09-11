package com.doris.peach.activity.gif;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.os.Bundle;
import android.webkit.WebView;

/**
 * GIF
 * 
 * @author Doris
 *
 *         2016年4月29日
 */
public class GIFWebActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gif_web);

		WebView wv_gif = (WebView) findViewById(R.id.wv_gif);
		wv_gif.loadUrl("file:///android_asset/html/gif.html");
	}
}
