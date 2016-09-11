package com.doris.peach.activity;

import java.util.ArrayList;

import com.doris.peach.R;
import com.doris.peachlibrary.adapter.Abstract.ArrayAdapter;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年4月11日
 */
public class ListViewActivity extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setDivider(null);
	}
	
	protected ArrayAdapter<String> createListAdapter(ArrayList<String> list) {
		return new MyListAdapter(this, list);
	}

	private static class MyListAdapter extends ArrayAdapter<String> {

		private Context mContext;
		private ArrayList<String> items;

		public MyListAdapter(Context context, ArrayList<String> items) {
			super(items);
			this.items = items;
			mContext = context;
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).hashCode();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
			}
			tv.setText(items.get(position));
			return tv;
		}
	}
}
