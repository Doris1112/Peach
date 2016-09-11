package com.doris.peach.activity.pulltorefresh;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.dialog.Interface.DialogButtonClickListener;
import com.doris.peachlibrary.util.DeviceUtil;
import com.doris.peachlibrary.util.DialogUtil;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * @author Doris
 *
 * 2016年5月13日
 */
public class PullToRefreshWebViewActivity extends BaseActivity {

	private PullToRefreshWebView mPullRefreshWebView;
	private WebView mWebView;
	private DialogUtil dialogUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_webview);
		
		setContentUnderStatus(findViewById(R.id.ll_prwebview1_content));
		
		mPullRefreshWebView = (PullToRefreshWebView) findViewById(R.id.pull_refresh_webview);
		mWebView = mPullRefreshWebView.getRefreshableView();

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new SampleWebViewClient());
		dialogUtil = new DialogUtil(this);
		if(!DeviceUtil.isNetworkAvailable(this)){
			dialogUtil.showAlertDialog("未检测到网络连接，是否连接网络？", 
					new DialogButtonClickListener() {
				
				@Override
				public void onClickYes() {
					// TODO Auto-generated method stub
					startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
				}
				
				@Override
				public void onClickNo() {
					// TODO Auto-generated method stub
					PullToRefreshWebViewActivity.this.finish();
				}
			});
		}else{
			mWebView.loadUrl("http://www.baidu.com");
		}
	}
	
	static class SampleWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}
