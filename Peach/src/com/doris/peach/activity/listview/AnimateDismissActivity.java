package com.doris.peach.activity.listview;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.adapter.Interface.OnDismissCallback;
import com.doris.peachlibrary.adapter.AnimateDismissAdapter;
import com.doris.peachlibrary.adapter.Abstract.ArrayAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年5月11日
 */
public class AnimateDismissActivity extends BaseActivity{

	private List<Integer> mSelectedPositions;
	private MyListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animate_dismiss);

		mSelectedPositions = new ArrayList<Integer>();

		ListView listView = (ListView) findViewById(R.id.b_animatedismiss_listview);
		mAdapter = new MyListAdapter(MyListActivity.getItems());
		final AnimateDismissAdapter<String> animateDismissAdapter = new AnimateDismissAdapter<String>(mAdapter, new MyOnDismissCallback());
		animateDismissAdapter.setListView(listView);
		listView.setAdapter(animateDismissAdapter);

		Button button = (Button) findViewById(R.id.b_animatedismiss_button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				animateDismissAdapter.animateDismiss(mSelectedPositions);
				mSelectedPositions.clear();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CheckedTextView tv = ((CheckedTextView) view);
				tv.toggle();
				if (tv.isChecked()) {
					mSelectedPositions.add(position);
				} else {
					mSelectedPositions.remove((Integer) position);
				}
			}
		});
	}

	private class MyOnDismissCallback implements OnDismissCallback {

		@Override
		public void onDismiss(ListView listView, int[] reverseSortedPositions) {
			for (int position : reverseSortedPositions) {
				mAdapter.remove(position);
			}
		}
	}

	private class MyListAdapter extends ArrayAdapter<String> {

		public MyListAdapter(ArrayList<String> items) {
			super(items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CheckedTextView tv = (CheckedTextView) convertView;
			if (tv == null) {
				tv = (CheckedTextView) LayoutInflater.from(AnimateDismissActivity.this).inflate(R.layout.item_animate_row, parent, false);
			}
			tv.setText(getItem(position));
			tv.setChecked(mSelectedPositions.contains(position));
			return tv;
		}
	}
}
