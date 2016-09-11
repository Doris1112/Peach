package com.doris.peach.activity.pulltorefresh;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.PullToRefreshWebView2;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * @author Doris
 *
 * 2016年5月13日
 */
public class PullToRefreshWebView2Activity extends BaseActivity implements OnRefreshListener<WebView> {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_webview2);
		
		PullToRefreshWebView2 pullRefreshWebView = (PullToRefreshWebView2) findViewById(R.id.pull_refresh_webview2);
		pullRefreshWebView.setOnRefreshListener(this);

		WebView webView = pullRefreshWebView.getRefreshableView();
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new SampleWebViewClient());
		webView.loadUrl("file:///android_asset/html/ptr_webview2_sample.html");
	}
	
	static class SampleWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public void onRefresh(final PullToRefreshBase<WebView> refreshView) {
		refreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				refreshView.onRefreshComplete();
			}
		}, 2 * 1000);
	}
}
