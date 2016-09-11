package com.doris.peach.activity.textdrawable;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.textdrawable.DataItem;
import com.doris.peachlibrary.util.textdrawable.DataSource;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年7月17日
 */
public class TextDrawableActivity extends BaseActivity{
	
	private DataSource mDataSource;
	private ListView lv_textdrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);

		setTitle();
		
		mDataSource = new DataSource(this);
		lv_textdrawable = (ListView) findViewById(R.id.lv_listButton);
		lv_textdrawable.setAdapter(new TextDrawableAdapter());
		lv_textdrawable.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				DataItem item = (DataItem) lv_textdrawable.getItemAtPosition(position);
		        // if navigation is supported, open the next activity
		        if (item.getNavigationInfo() != DataSource.NO_NAVIGATION) {
		            Intent intent = new Intent(TextDrawableActivity.this,
		            		ListTextDrawableActivity.class);
		            intent.putExtra("TYPE", item.getNavigationInfo());
		            startActivity(intent);
		        }

			}
		});
	}
	
	private class TextDrawableAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDataSource.getCount();
		}

		@Override
		public DataItem getItem(int position) {
			// TODO Auto-generated method stub
			return mDataSource.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if(convertView == null){
				convertView = View.inflate(TextDrawableActivity.this,
						R.layout.item_textdrawable, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			DataItem item = getItem(position);
			final Drawable drawable = item.getDrawable();
            holder.imageView.setImageDrawable(drawable);
            holder.textView.setText(item.getLabel());
            if (item.getNavigationInfo() != DataSource.NO_NAVIGATION) {
                holder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.ic_action_next_item), null);
            } else {
                holder.textView.setCompoundDrawablesWithIntrinsicBounds(null,
                        null,  null, null);
            }
            // fix for animation not playing for some below 4.4 devices
            if (drawable instanceof AnimationDrawable) {
                holder.imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((AnimationDrawable) drawable).stop();
                        ((AnimationDrawable) drawable).start();
                    }
                });
            }
            return convertView;
		}

	    private  class ViewHolder {
	        private ImageView imageView;
	        private TextView textView;
	        private ViewHolder(View view) {
	            imageView = (ImageView) view.findViewById(R.id.iv_image);
	            textView = (TextView) view.findViewById(R.id.tv_text);
	        }
	    }
	}
}
