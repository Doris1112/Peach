package com.doris.peach.activity.listview;

import java.util.ArrayList;

import com.doris.peach.R;
import com.doris.peachlibrary.adapter.Abstract.ArrayAdapter;
import com.doris.peachlibrary.util.DeviceUtil;

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
 * 2016年5月11日
 */
public class MyListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setDivider(null);
		getListView().setBackgroundResource(R.color.peachThemeColor);
		setContentUnderStatus(getListView());
	}
	
	protected ArrayAdapter<String> createListAdapter() {
		return new MyListAdapter(this, getItems());
	}

	public static ArrayList<String> getItems() {
		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) {
			items.add(String.valueOf(i));
		}
		return items;
	}

	private static class MyListAdapter extends ArrayAdapter<String> {

		private Context mContext;

		public MyListAdapter(Context context, ArrayList<String> items) {
			super(items);
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
				tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_list_row, parent, false);
			}
			tv.setText("This is row number " + getItem(position));
			return tv;
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
	}
	
	/**
	 * 设置内容在状态栏之下
	 */
	private void setContentUnderStatus(View view){
		view.setPadding(0, DeviceUtil.getStatusBarHeight(this), 0, 0);
	}
}
