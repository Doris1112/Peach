package com.doris.peach.activity.pulltorefresh;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年5月12日
 */
public class PullToRefreshActivity extends BaseActivity {
	
	private ListView lv_pull_to_refresh;
	private List<Object[]> pullTos = new ArrayList<Object[]>();
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);
		
		setTitle();
		
		lv_pull_to_refresh = (ListView) findViewById(
				R.id.lv_listButton);
		
		initPullTos();
		lv_pull_to_refresh.setAdapter(new PullToRefreshAdapter());
	}
	
	class PullToRefreshAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pullTos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return pullTos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = LayoutInflater.from(
						PullToRefreshActivity.this)
						.inflate(R.layout.item_button, null);
			}
			
			Button btn = (Button) convertView.findViewById(R.id.b_button);
			btn.setText(pullTos.get(position)[0].toString());
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(
							PullToRefreshActivity.this,
							(Class)pullTos.get(position)[1]));
				}
			});
			return convertView;
		}
	}
	
	private void initPullTos(){
		pullTos.add(new Object[]{"ListView", PullToRefreshListActivity.class});
		pullTos.add(new Object[]{"ExpandableListView", PullToRefreshExpandableListActivity.class});
		pullTos.add(new Object[]{"GridView", PullToRefreshGridActivity.class});
		pullTos.add(new Object[]{"WebView", PullToRefreshWebViewActivity.class});
		pullTos.add(new Object[]{"ScrollView", PullToRefreshScrollViewActivity.class});
		pullTos.add(new Object[]{"HorizontalScrollView", PullToRefreshHorizontalScrollViewActivity.class});
		pullTos.add(new Object[]{"ViewPager", PullToRefreshViewPagerActivity.class});
		pullTos.add(new Object[]{"ListView Fragment", PullToRefreshListFragmentActivity.class});
		pullTos.add(new Object[]{"WebView Advanced", PullToRefreshWebView2Activity.class});
		pullTos.add(new Object[]{"ListView in ViewPager", PullToRefreshListInViewPagerActivity.class});
	}
}
