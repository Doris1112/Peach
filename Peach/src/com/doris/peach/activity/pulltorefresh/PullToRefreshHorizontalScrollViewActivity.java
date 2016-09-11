package com.doris.peach.activity.pulltorefresh;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshHorizontalScrollView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.HorizontalScrollView;

/**
 * 
 * @author Doris
 *
 * 2016年5月13日
 */
public class PullToRefreshHorizontalScrollViewActivity extends BaseActivity {

	PullToRefreshHorizontalScrollView mPullRefreshScrollView;
	HorizontalScrollView mScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_horizontalscrollview);
		
		mPullRefreshScrollView = (PullToRefreshHorizontalScrollView) findViewById(R.id.pull_refresh_horizontalscrollview);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<HorizontalScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<HorizontalScrollView> refreshView) {
				new GetDataTask().execute();
			}
		});

		mScrollView = mPullRefreshScrollView.getRefreshableView();
	}
	
	class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mPullRefreshScrollView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
}
