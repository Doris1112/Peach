package com.doris.peach.activity.listview;

import com.doris.peachlibrary.adapter.SwingLeftInAnimationAdapter;

import android.os.Bundle;
import android.widget.BaseAdapter;

/**
 * 
 * @author Doris
 *
 * 2016年5月11日
 */
public class SwingLeftInActivity extends MyListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BaseAdapter mAdapter = createListAdapter();

		SwingLeftInAnimationAdapter swingLeftInAnimationAdapter = new SwingLeftInAnimationAdapter(mAdapter);
		swingLeftInAnimationAdapter.setListView(getListView());

		getListView().setAdapter(swingLeftInAnimationAdapter);
	}
}
