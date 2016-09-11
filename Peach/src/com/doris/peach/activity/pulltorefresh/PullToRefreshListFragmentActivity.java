package com.doris.peach.activity.pulltorefresh;

import java.util.Arrays;
import java.util.LinkedList;

import com.doris.peach.R;
import com.doris.peach.activity.BaseFragmentActivity;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年5月13日
 */
public class PullToRefreshListFragmentActivity extends BaseFragmentActivity implements OnRefreshListener<ListView> {

	private LinkedList<String> mListItems;
	private ArrayAdapter<String> mAdapter;

	private PullToRefreshListFragment mPullRefreshListFragment;
	private PullToRefreshListView mPullRefreshListView;
	
	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_list_fragment);

		mPullRefreshListFragment = (PullToRefreshListFragment) getSupportFragmentManager().findFragmentById(
				R.id.frag_ptr_list);

		mPullRefreshListView = mPullRefreshListFragment.getPullToRefreshListView();
		mPullRefreshListView.setOnRefreshListener(this);

		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		
		mAdapter = new ArrayAdapter<String>(this, R.layout.item_text, mListItems);
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setDivider(null);
		actualListView.setAdapter(mAdapter);

		mPullRefreshListFragment.setListShown(true);
	}
	
	class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addFirst("Added after refresh...");
			mAdapter.notifyDataSetChanged();

			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		new GetDataTask().execute();
	}
	
}
