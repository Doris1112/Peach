package com.doris.peach.activity.listview;

import com.doris.peachlibrary.adapter.SwingBottomInAnimationAdapter;
import com.doris.peachlibrary.adapter.SwingRightInAnimationAdapter;

import android.os.Bundle;
import android.widget.BaseAdapter;

/**
 * 
 * @author Doris
 *
 * 2016年5月11日
 */
public class SwingBottomRightInActivity extends MyListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BaseAdapter mAdapter = createListAdapter();
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(mAdapter);
		SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(swingBottomInAnimationAdapter);

		swingRightInAnimationAdapter.setListView(getListView());
		getListView().setAdapter(swingRightInAnimationAdapter);
	}
}
