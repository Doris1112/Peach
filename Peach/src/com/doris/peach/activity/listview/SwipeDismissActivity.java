package com.doris.peach.activity.listview;

import java.util.Arrays;

import com.doris.peach.R;
import com.doris.peachlibrary.adapter.SwipeDismissAdapter;
import com.doris.peachlibrary.adapter.Abstract.ArrayAdapter;
import com.doris.peachlibrary.adapter.Interface.OnDismissCallback;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年5月11日
 */
public class SwipeDismissActivity extends MyListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ArrayAdapter<String> mAdapter = createListAdapter();

		SwipeDismissAdapter swipeDismissAdapter = new SwipeDismissAdapter(mAdapter, new MyOnDismissCallback(mAdapter));
		swipeDismissAdapter.setListView(getListView());

		getListView().setAdapter(swipeDismissAdapter);
	}

	private class MyOnDismissCallback implements OnDismissCallback {

		private ArrayAdapter<String> mAdapter;

		public MyOnDismissCallback(ArrayAdapter<String> adapter) {
			mAdapter = adapter;
		}

		@Override
		public void onDismiss(ListView listView, int[] reverseSortedPositions) {
			for (int position : reverseSortedPositions) {
				mAdapter.remove(position);
			}
			ToastView.showWhiteContentToast(SwipeDismissActivity.this,
					R.drawable.ic_toast_flag_ok,
					"Removed positions: " + Arrays.toString(
							reverseSortedPositions));
		}
	}
}
