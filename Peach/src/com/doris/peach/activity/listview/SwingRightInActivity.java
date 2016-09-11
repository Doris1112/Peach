package com.doris.peach.activity.listview;

import com.doris.peachlibrary.adapter.SwingRightInAnimationAdapter;

import android.os.Bundle;
import android.widget.BaseAdapter;

/**
 * 
 * @author Doris
 *
 * 2016年5月11日
 */
public class SwingRightInActivity extends MyListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		BaseAdapter mAdapter = createListAdapter();
		
		SwingRightInAnimationAdapter animationAdapter = new SwingRightInAnimationAdapter(mAdapter);
		animationAdapter.setListView(getListView());

		getListView().setAdapter(animationAdapter);
	}
}
