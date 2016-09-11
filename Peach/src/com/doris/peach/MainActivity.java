package com.doris.peach;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.activity.ListIntentActivity;
import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;
import com.doris.peachlibrary.util.DeviceUtil;
import com.doris.peachlibrary.view.ToastView;

import android.R.color;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年4月9日
 */
public class MainActivity extends Activity {

	private EditText et_search_content;
	private ListView lv_search;

	private SearchAdapter adapter;
	private long exitTime = 0;
	private List<String[]> searchList = new ArrayList<String[]>();
	private DBUtil dbUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_search_content = (EditText) findViewById(R.id.et_search_content);
		lv_search = (ListView) findViewById(R.id.lv_search);

		dbUtil = new DBUtil(this);
		et_search_content.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String searchText = et_search_content.getText().toString();
				if (searchText.length() > 0) {
					searchList = dbUtil.fuzzyQuery(BaseData.TB_LIST,
							new String[] { BaseData.LIST_ID, BaseData.LIST_ITEM }, new String[] { BaseData.LIST_ITEM },
							new String[] { searchText });
					if (searchList.size() > 0) {
						spinner();
					} else {
						lv_search.setVisibility(View.GONE);
					}
				} else {
					lv_search.setVisibility(View.GONE);
				}
			}
		});

		adapter = new SearchAdapter();
		lv_search.setAdapter(adapter);
	}

	public void spinner() {
		adapter.notifyDataSetChanged();
		lv_search.setVisibility(View.VISIBLE);
		lv_search.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				BaseData.item = (String[]) adapter.getItem(position);
				et_search_content.setText(BaseData.item[1]);
				et_search_content.setSelection(BaseData.item[1].length());
				lv_search.setVisibility(View.GONE);
			}
		});
	}

	class SearchAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return searchList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return searchList.get(position);
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
				convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_search, null);
			}

			TextView text = (TextView) convertView.findViewById(R.id.tv_item_search);
			text.setText(searchList.get(position)[1]);

			return convertView;
		}

	}

	/**
	 * 搜索
	 * 
	 * @param view
	 */
	public void search(View view) {
		String content = et_search_content.getText().toString();
		if (content != null && content.length() > 0) {
			if (content.equals(BaseData.item[1])) {
				ListIntentActivity.goActivity(this);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				et_search_content.setText("");
			} else {
				List<String[]> list = dbUtil.query(BaseData.TB_LIST,
						new String[] { BaseData.LIST_ID, BaseData.LIST_ITEM }, new String[] { BaseData.LIST_ITEM },
						new String[] { content });
				if (list.size() == 1) {
					BaseData.item = list.get(0);
					ListIntentActivity.goActivity(this);
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					et_search_content.setText("");
				} else {
					ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "没有找到搜索内容");
				}
			}
		}
	}

	/**
	 * 列表
	 * 
	 * @param view
	 */
	public void list(View view) {
		Intent intent = new Intent(MainActivity.this, ListActivity.class);
		startActivity(intent);

		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (System.currentTimeMillis() - exitTime > 2000) {
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "再按一次退出");
			exitTime = System.currentTimeMillis();
		} else {
			this.finish();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (lv_search.getVisibility() == View.VISIBLE) {
				lv_search.setVisibility(View.GONE);
			}
		}
		return super.onTouchEvent(event);
	}

}
