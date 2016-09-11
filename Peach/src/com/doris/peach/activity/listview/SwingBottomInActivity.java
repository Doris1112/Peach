package com.doris.peach.activity.listview;

import com.doris.peachlibrary.adapter.SwingBottomInAnimationAdapter;

import android.os.Bundle;
import android.widget.BaseAdapter;

/**
 * 
 * @author Doris
 *
 * 2016年5月11日
 */
public class SwingBottomInActivity extends MyListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		BaseAdapter adapter = createListAdapter();
		SwingBottomInAnimationAdapter animationAdapter = 
				new SwingBottomInAnimationAdapter(adapter);
		animationAdapter.setListView(getListView());
		getListView().setAdapter(animationAdapter);
	}
	
}
