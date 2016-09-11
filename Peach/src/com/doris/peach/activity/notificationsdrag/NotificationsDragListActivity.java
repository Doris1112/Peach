package com.doris.peach.activity.notificationsdrag;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.notificationsdrag.WaterDrop;
import com.doris.peachlibrary.view.notificationsdrag.util.CoverManager;
import com.doris.peachlibrary.view.notificationsdrag.util.CoverManager.OnDragCompeteListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年8月10日
 */
public class NotificationsDragListActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		
		CoverManager.getInstance().init(NotificationsDragListActivity.this);
		
		ListView list = (ListView) findViewById(R.id.lv_list);
		list.setAdapter(new NotificationsDragAdapter());
		
		CoverManager.getInstance().setMaxDragDistance(250);
		CoverManager.getInstance().setEffectDuration(150);
	}
	
	private class NotificationsDragAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 11;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(NotificationsDragListActivity.this).inflate(
						R.layout.item_notificationsdrag, null);
			}
			WaterDrop drop = (WaterDrop) convertView.findViewById(R.id.drop);
			drop.setText(String.valueOf(position));

			drop.setEffectResource(R.drawable.explosion_heart);
			drop.setOnDragCompeteListener(new OnDragCompeteListener() {

				@Override
				public void onDragComplete() {
					ToastView.showWhiteContentToast(NotificationsDragListActivity.this,
							R.drawable.ic_toast_flag_verbose,
							"remove:" + position);
				}
			});

			return convertView;
		}
		
	}
}
