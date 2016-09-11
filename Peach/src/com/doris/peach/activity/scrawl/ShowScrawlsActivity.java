package com.doris.peach.activity.scrawl;

import java.io.File;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 * 2016年6月27日
 */
public class ShowScrawlsActivity extends BaseActivity {
	
	private GridView gv_scrawlspic;
	
	private String[] images; 
	private ScrawlsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrawls_show);
		
		gv_scrawlspic = (GridView) findViewById(R.id.gv_scrawlspic);
		
		File file = new File(ScrawlActivity.path);
		images = file.list();
		adapter = new ScrawlsAdapter();
		gv_scrawlspic.setAdapter(adapter);
		gv_scrawlspic.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ShowScrawlsActivity.this, ShowScrawlActivity.class);
				intent.putExtra("path", adapter.getItem(position).toString());
				intent.putExtra("save", false);
				startActivity(intent);
			}
		});
	
	}
	
	class ScrawlsAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return  ScrawlActivity.path + images[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView image;
			if(convertView == null){
				convertView = LayoutInflater.from(ShowScrawlsActivity.this).inflate(R.layout.item_img, null);
				image = (ImageView) convertView.findViewById(R.id.iv_image);
				convertView.setTag(image);
			}else{
				image = (ImageView) convertView.getTag();
			}
			
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 4; //图片宽高都为原来的四分之一，即图片为原来的八分之一  
			Bitmap bitmap = BitmapFactory.decodeFile(getItem(position).toString(), opts);
			image.setImageBitmap(bitmap);
			
			return convertView;
		}
	}
}