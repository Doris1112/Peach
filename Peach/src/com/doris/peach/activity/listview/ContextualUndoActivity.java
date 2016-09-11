package com.doris.peach.activity.listview;

import com.doris.peach.R;
import com.doris.peachlibrary.adapter.ContextualUndoAdapter;
import com.doris.peachlibrary.adapter.ContextualUndoAdapter.DeleteItemCallback;
import com.doris.peachlibrary.adapter.Abstract.ArrayAdapter;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年5月11日
 */
public class ContextualUndoActivity extends MyListActivity{

	private final ArrayAdapter<String> mAdapter = createListAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ContextualUndoAdapter contextualUndoAdapter = new ContextualUndoAdapter(mAdapter, R.layout.item_undo_row, R.id.b_undo_row);
		contextualUndoAdapter.setListView(getListView());
		getListView().setAdapter(contextualUndoAdapter);
		contextualUndoAdapter.setDeleteItemCallback(new MyDeleteItemCallback());
	}

	private class MyDeleteItemCallback implements DeleteItemCallback {

		@Override
		public void deleteItem(int position) {
			mAdapter.remove(position);
			mAdapter.notifyDataSetChanged();
		}
	}
}
