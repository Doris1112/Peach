package com.doris.peach.activity.pulltorefresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doris.peach.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import android.app.ExpandableListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

/**
 * 
 * @author Doris
 *
 * 2016年5月13日
 */
public class PullToRefreshExpandableListActivity extends ExpandableListActivity {

	private PullToRefreshExpandableListView mPullRefreshListView;
	
	private static final String KEY = "key";
	private String[] mChildStrings = { "Child One", "Child Two", "Child Three", "Child Four", "Child Five",
			"Child Six" };
	private String[] mGroupStrings = { "Group One", "Group Two", "Group Three" };
	private List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
	private SimpleExpandableListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_expandable_list);
		
		mPullRefreshListView = (PullToRefreshExpandableListView) 
				findViewById(R.id.pull_refresh_expandable_list);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
				new GetDataTask().execute();
			}
		});
		
		for (String group : mGroupStrings) {
			Map<String, String> groupMap1 = new HashMap<String, String>();
			groupData.add(groupMap1);
			groupMap1.put(KEY, group);

			List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
			for (String string : mChildStrings) {
				Map<String, String> childMap = new HashMap<String, String>();
				childList.add(childMap);
				childMap.put(KEY, string);
			}
			childData.add(childList);
		}

		mAdapter = new SimpleExpandableListAdapter(this, groupData, R.layout.simple_expandable_list_item_1,
				new String[] { KEY }, new int[] { android.R.id.text1 }, childData,
				R.layout.item_text, new String[] { KEY }, new int[] { android.R.id.text1 });
		setListAdapter(mAdapter);
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return mChildStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			Map<String, String> newMap = new HashMap<String, String>();
			newMap.put(KEY, "Added after refresh...");
			groupData.add(newMap);

			List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
			for (String string : mChildStrings) {
				Map<String, String> childMap = new HashMap<String, String>();
				childMap.put(KEY, string);
				childList.add(childMap);
			}
			childData.add(childList);

			mAdapter.notifyDataSetChanged();

			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
	}
}
