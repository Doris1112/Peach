package com.doris.peach.activity.loadingview;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.loadingview.util.Style;
import com.doris.peachlibrary.view.loadingview.SpinKitView;
import com.doris.peachlibrary.view.loadingview.Sprite;
import com.doris.peachlibrary.view.loadingview.util.Colors;
import com.doris.peachlibrary.view.loadingview.util.SpriteFactory;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * 
 * @author Doris
 *
 * 2016年8月16日
 */
public class LoadinViewGridActivity extends BaseActivity implements Colors{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview);
		
		GridView list = (GridView) findViewById(R.id.gv);
		list.setAdapter(new GridAdapter());
	}
	
	class GridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Style.values().length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = View.inflate(LoadinViewGridActivity.this, R.layout.item_loadingview_grid, null);
			
			SpinKitView spinKitView = (SpinKitView) convertView.findViewById(R.id.spin_kit);
			convertView.setBackgroundColor(colors[position % colors.length]);
            position = position % 15;
            Style style = Style.values()[position];
            Sprite drawable = SpriteFactory.create(style);
            spinKitView.setIndeterminateDrawable(drawable);
			
			return convertView;
		}
		
	}
}
