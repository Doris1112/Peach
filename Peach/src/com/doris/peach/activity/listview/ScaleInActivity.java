package com.doris.peach.activity.listview;

import com.doris.peachlibrary.adapter.ScaleInAnimationAdapter;

import android.os.Bundle;
import android.widget.BaseAdapter;

/**
 * 
 * @author Doris
 *
 * 2016年5月11日
 */
public class ScaleInActivity extends MyListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BaseAdapter mAdapter = createListAdapter();

		ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(mAdapter, 0f);
		scaleInAnimationAdapter.setListView(getListView());

		getListView().setAdapter(scaleInAnimationAdapter);
	}
}
