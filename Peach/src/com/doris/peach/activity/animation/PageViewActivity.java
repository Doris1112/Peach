package com.doris.peach.activity.animation;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peachlibrary.util.animation.SwitchAnimationUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年7月26日
 */
public class PageViewActivity extends BaseActivity {
	
	private ListView lv_animation;
	private GridView gv_animation;
	private SwitchAnimationUtil mSwitchAnimationUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_pageview);
		
		lv_animation = (ListView) findViewById(R.id.lv_animation);
		gv_animation = (GridView) findViewById(R.id.gv_animation);
		
		lv_animation.setAdapter(new ListAdapter());
		gv_animation.setAdapter(new GridAdapter());
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (mSwitchAnimationUtil == null) {
			mSwitchAnimationUtil = new SwitchAnimationUtil();
			mSwitchAnimationUtil.startAnimation(getWindow().getDecorView(),
					BaseData.PAGE_VIEW_ANIM);
		}
	}
	
	private class ListAdapter extends BaseAdapter {
		private int[] res = new int[] { R.drawable.ic_launcher, R.drawable.ic_picture07 };

		@Override
		public int getCount() {
			return 6;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			if (convertView == null) {
				convertView = LayoutInflater.from(PageViewActivity.this).inflate(
						R.layout.item_loadnetworkimage, null);
			}
			ImageView avatar = (ImageView) convertView
					.findViewById(R.id.iv_item_loadnetworkimage);
			avatar.setImageResource(res[position % res.length]);
			return convertView;
		}
	}
	
	private class GridAdapter extends BaseAdapter {
		private int[] res = new int[] { R.drawable.ic_picture01, R.drawable.ic_picture02,
				R.drawable.ic_picture03, R.drawable.ic_picture04 };

		@Override
		public int getCount() {
			return 24;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			if (convertView == null) {
				ImageView mImage = new ImageView(PageViewActivity.this);
				AbsListView.LayoutParams params = new AbsListView.LayoutParams(
						440 / 4, 440 / 4);
				mImage.setLayoutParams(params);
				mImage.setScaleType(ScaleType.CENTER_CROP);
				mImage.setPadding(5, 5, 5, 5);
				convertView = mImage;
				convertView.setAlpha(0);
			}

			((ImageView) convertView).setImageResource(res[position
					% res.length]);
			return convertView;
		}
	}
}
