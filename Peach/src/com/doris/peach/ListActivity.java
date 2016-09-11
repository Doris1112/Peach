package com.doris.peach;

import java.util.Collections;

import com.doris.peach.activity.BaseActivity;
import com.doris.peach.activity.ListIntentActivity;
import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;
import com.doris.peachlibrary.view.IndexableListView;
import com.doris.peachlibrary.domain.StringMatcher;
import com.doris.peachlibrary.util.ChineseSpell;
import com.doris.peachlibrary.util.ComparatorStrings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年4月9日
 */
public class ListActivity extends BaseActivity {

	private IndexableListView ilv_list;
	private ListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indexable_list);

		ComparatorStrings comStr = new ComparatorStrings(1, 1);
		if(BaseData.list.size() < 0){
			DBUtil dbUtil = new DBUtil(this);
			BaseData.getLists(dbUtil);
		}
		Collections.sort(BaseData.list, comStr);

		ilv_list = (IndexableListView) findViewById(R.id.ilv_indexable_list);
		adapter = new ListAdapter();
		ilv_list.setAdapter(adapter);
		ilv_list.setFastScrollEnabled(true);

		ilv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				BaseData.item = (String[]) adapter.getItem(arg2);
				ListIntentActivity.goActivity(ListActivity.this);
			}
		});

	}

	class ListAdapter extends BaseAdapter implements SectionIndexer {

		private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return BaseData.list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return BaseData.list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = LayoutInflater.from(ListActivity.this).inflate(R.layout.item_list, null);
			}
			final TextView item = (TextView) convertView.findViewById(R.id.tv_item_list);
			item.setText(BaseData.list.get(position)[1]);
			item.startAnimation(AnimationUtils.loadAnimation(ListActivity.this, R.anim.push_left_in));

			return convertView;
		}

		@Override
		public int getPositionForSection(int section) {
			// 如果没有项目目前部分,前一部分将被选中
			for (int i = section; i >= 0; i--) {
				for (int j = 0; j < getCount(); j++) {
					if (i == 0) {
						// 数字部分
						for (int k = 0; k <= 9; k++) {
							if (StringMatcher.match(getValue(j), String.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher.match(getValue(j), String.valueOf(mSections.charAt(i))))
							return j;
					}
				}
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

		@Override
		public Object[] getSections() {
			String[] sections = new String[mSections.length()];
			for (int i = 0; i < mSections.length(); i++)
				sections[i] = String.valueOf(mSections.charAt(i));
			return sections;
		}

		private String getValue(int i) {
			String value = ChineseSpell.converterToFirstSpell(BaseData.list.get(i)[1]).toUpperCase().substring(0, 1);
			return value;
		}

	}

}
