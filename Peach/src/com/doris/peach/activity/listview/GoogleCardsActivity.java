package com.doris.peach.activity.listview;

import java.util.ArrayList;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.adapter.Interface.OnDismissCallback;
import com.doris.peachlibrary.adapter.SwingBottomInAnimationAdapter;
import com.doris.peachlibrary.adapter.SwipeDismissAdapter;
import com.doris.peachlibrary.adapter.Abstract.ArrayAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年5月11日
 */
public class GoogleCardsActivity extends BaseActivity implements OnDismissCallback{

	private ListView lv_googlecards_listview;
	private GoogleCardsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		
		lv_googlecards_listview = (ListView) findViewById(
				R.id.lv_list);
		adapter = new GoogleCardsAdapter(this);
		SwingBottomInAnimationAdapter animationAdapter = new 
				SwingBottomInAnimationAdapter(
						new SwipeDismissAdapter(adapter, this));
		animationAdapter.setListView(lv_googlecards_listview);
		lv_googlecards_listview.setAdapter(animationAdapter);
		adapter.addAll(getItems());
	}
	
	private ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			items.add(i);
		}
		return items;
	}
	
	class GoogleCardsAdapter extends ArrayAdapter<Integer>{
		
		private Context mContext;
		private LruCache<Integer, Bitmap> mMemoryCache;

		public GoogleCardsAdapter(Context context) {
			mContext = context;
			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
			final int cacheSize = maxMemory;
			mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(Integer key, Bitmap bitmap) {
					return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
				}
			};
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(mContext).inflate(
						R.layout.item_googlecards_card, parent, false);

				viewHolder = new ViewHolder();
				viewHolder.textView = (TextView) view.findViewById(
						R.id.tv_googlecards_card_textview);
				viewHolder.imageView = (ImageView) view.findViewById(
						R.id.iv_googlecards_card_imageview);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			viewHolder.textView.setText("This is card " + 
					(getItem(position) + 1));
			setImageView(viewHolder, position);
			
			return view;
		}

		private void setImageView(ViewHolder viewHolder, int position) {
			int imageResId;
			switch (getItem(position) % 5) {
				case 0:
					imageResId = R.drawable.p10;
					break;
				case 1:
					imageResId = R.drawable.p11;
					break;
				case 2:
					imageResId = R.drawable.p12;
					break;
				case 3:
					imageResId = R.drawable.p13;
					break;
				default:
					imageResId = R.drawable.p14;
			}

			Bitmap bitmap = getBitmapFromMemCache(imageResId);
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(mContext.getResources(), imageResId);
				addBitmapToMemoryCache(imageResId, bitmap);
			}
			viewHolder.imageView.setImageBitmap(bitmap);
		}

		private void addBitmapToMemoryCache(int key, Bitmap bitmap) {
			if (getBitmapFromMemCache(key) == null) {
				mMemoryCache.put(key, bitmap);
			}
		}

		private Bitmap getBitmapFromMemCache(int key) {
			return mMemoryCache.get(key);
		}

		 class ViewHolder {
			TextView textView;
			ImageView imageView;
		}
	}

	@Override
	public void onDismiss(ListView listView, int[] reverseSortedPositions) {
		// TODO Auto-generated method stub
		for (int position : reverseSortedPositions) {
			adapter.remove(position);
		}
	}
}
