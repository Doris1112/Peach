package com.doris.peach.activity.pulltorefresh;

import java.util.Arrays;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年5月13日
 */
public class PullToRefreshListInViewPagerActivity extends BaseActivity implements OnRefreshListener<ListView>{

	private static final String[] STRINGS = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance",
			"Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };

	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_list_in_vp);

		mViewPager = (ViewPager) findViewById(R.id.vp_list);
		mViewPager.setAdapter(new ListViewPagerAdapter());
	}
	
	class ListViewPagerAdapter extends PagerAdapter {

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			Context context = container.getContext();

			PullToRefreshListView plv = (PullToRefreshListView) LayoutInflater.from(context).inflate(
					R.layout.item_listview_in_viewpager, container, false);

			ListAdapter adapter = new ArrayAdapter<String>(context, R.layout.item_text,
					Arrays.asList(STRINGS));
			plv.setAdapter(adapter);

			plv.setOnRefreshListener(PullToRefreshListInViewPagerActivity.this);

			container.addView(plv, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return plv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public int getCount() {
			return 3;
		}

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		new GetDataTask(refreshView).execute();
	}

	static class GetDataTask extends AsyncTask<Void, Void, Void> {

		PullToRefreshBase<?> mRefreshedView;

		public GetDataTask(PullToRefreshBase<?> refreshedView) {
			mRefreshedView = refreshedView;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mRefreshedView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
}