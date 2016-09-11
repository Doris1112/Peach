package com.doris.peach.activity.scrawl;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ColorPickerView;
import com.doris.peachlibrary.view.ColorPickerView.OnColorChangedListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年6月23日
 */
public class SelectScrawlBgActivity extends BaseActivity {

	private GridView gv_scrawl_bg;
	private LinearLayout ll_scrawl_bg;

	private int[] scrawlbgs = { R.drawable.scrawl_bg1, R.drawable.scrawl_bg2, R.drawable.scrawl_bg3,
			R.drawable.scrawl_bg4, 0 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrawlbg_select);

		ll_scrawl_bg = (LinearLayout) findViewById(R.id.ll_scrawl_bg);
		ll_scrawl_bg.addView(new ColorPickerView(this, new OnColorChangedListener() {

			@Override
			public void colorChanged(int color) {
				// TODO Auto-generated method stub
				goScrawl(false, color, 1);
			}
		}, Color.WHITE));
		gv_scrawl_bg = (GridView) findViewById(R.id.gv_scrawl_bg);
		ScrawlBgAdapter adapter = new ScrawlBgAdapter();
		gv_scrawl_bg.setAdapter(adapter);
		gv_scrawl_bg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				goScrawl(true, scrawlbgs[position], 0);
			}
		});

	}

	private void goScrawl(boolean flag, int... data) {
		Intent intent = new Intent(SelectScrawlBgActivity.this, Scrawl1Activity.class);
		if (intent != null) {
			if (flag) {
				intent.putExtra("scrawlBg", data[0]);
			} else {
				intent.putExtra("scrawlBgColor", data[0]);
			}
			intent.putExtra("scrawlBgFlag", data[1]);
			startActivity(intent);
		}
	}

	class ScrawlBgAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return scrawlbgs.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return scrawlbgs[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Tag tag = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(SelectScrawlBgActivity.this).inflate(R.layout.item_scrawlbg, null);
				tag = new Tag();
				tag.scrawlbg = (ImageView) convertView.findViewById(R.id.iv_item_scrawlbg);
				tag.text = (TextView) convertView.findViewById(R.id.tv_item_scrawlbg);
				convertView.setTag(tag);
			} else {
				tag = (Tag) convertView.getTag();
			}

			if (scrawlbgs[position] != 0) {
				tag.text.setVisibility(View.INVISIBLE);
				tag.scrawlbg.setVisibility(View.VISIBLE);
				tag.scrawlbg.setImageResource(scrawlbgs[position]);
			} else {
				tag.scrawlbg.setVisibility(View.INVISIBLE);
				tag.text.setVisibility(View.VISIBLE);
				tag.text.setText("无");
			}
			return convertView;
		}

		class Tag {
			ImageView scrawlbg;
			TextView text;
		}

	}

}