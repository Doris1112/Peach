package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.CircleFlowIndicator;
import com.doris.peachlibrary.view.ViewFlow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 *         2016年7月25日
 */
public class AutoViewFlowActivity extends BaseActivity {

	private ViewFlow viewFlow;

	public int[] ids = { R.drawable.start01, R.drawable.start02, R.drawable.start03 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_autoviewflow);

		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		viewFlow.setAdapter(new DemoPic());
		viewFlow.setmSideBuffer(ids.length); // 实际图片张数
		
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
		viewFlow.setSelection(0); // 设置初始位置
	}

	private class DemoPic extends BaseAdapter {
		private LayoutInflater mInflater;

		public DemoPic() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return ids.length;
		}

		@Override
		public Object getItem(int position) {
			return ids[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_autoviewflow_image, null);
			}

			((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(ids[position % ids.length]);
			if (position == getCount() - 1) {
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
			}
			return convertView;
		}
	}
}
