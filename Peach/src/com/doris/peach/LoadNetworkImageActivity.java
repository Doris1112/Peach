package com.doris.peach;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.AsyncImageLoader;
import com.doris.peachlibrary.util.AsyncImageLoader.ImageCallback;
import com.doris.peachlibrary.util.DeviceUtil;
import com.doris.peachlibrary.view.ToastView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年5月17日
 */
public class LoadNetworkImageActivity extends BaseActivity {

	private EditText et_path;
	private Button b_search;
	private ListView lv_loadnetworkimage;
	private List<String> images = new ArrayList<String>();
	private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	private ImageView iv_image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_image_network);
		
		setTitle();
		
		if(!DeviceUtil.isNetworkAvailable(this)){
			ToastView.showWhiteContentToast(this, 
					R.drawable.ic_toast_flag_verbose,
					"没有连接到网络");
		}
		
		et_path = (EditText) findViewById(R.id.et_loadnetworkimage_path);
		b_search = (Button) findViewById(R.id.b_loadnetworkimage_search);
		iv_image = (ImageView) findViewById(R.id.iv_loadnetworkimage_image);
		lv_loadnetworkimage = (ListView) findViewById(R.id.lv_loadnetworkimage);
		
		et_path.setSelection(et_path.getText().toString().length());
		et_path.addTextChangedListener(new Watcher());
	
		initImages();
		LoadNetworkImageAdapter adapter = new LoadNetworkImageAdapter();
		lv_loadnetworkimage.setAdapter(adapter);
		lv_loadnetworkimage.setOnItemClickListener(
				new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, final int position, long id) {
				// TODO Auto-generated method stub
				et_path.setText(images.get(position));
				asyncImageLoader.loadDrawable(position, images.get(position),
						new ImageCallback(){

					@Override
					public void onImageLoad(Integer t, Drawable drawable) {
						// TODO Auto-generated method stub
						iv_image.setBackgroundDrawable(drawable);
						//iv_image.setBackground(drawable);
					}
					
					@Override
					public void onError(Integer t) {
						// TODO Auto-generated method stub
						iv_image.setBackgroundResource(R.drawable.ic_launcher);
					}
					
				});
			}
		});
	}
	
	public void searchImage(View view){
		String imageUrl = et_path.getText().toString();
		asyncImageLoader.loadDrawable(0, imageUrl, new ImageCallback(){

			@Override
			public void onImageLoad(Integer t, Drawable drawable) {
				// TODO Auto-generated method stub
				iv_image.setBackgroundDrawable(drawable);
				//iv_image.setBackground(drawable);
			}

			@Override
			public void onError(Integer t) {
				// TODO Auto-generated method stub
				iv_image.setBackgroundResource(R.drawable.ic_launcher);
			}
			
		});
	}
	
	class LoadNetworkImageAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return images.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = LayoutInflater.from(
						LoadNetworkImageActivity.this)
						.inflate(R.layout.item_loadnetworkimage, 
								null);
			}
			convertView.setTag(position);
			
			ImageView image = (ImageView) convertView.findViewById(
					R.id.iv_item_loadnetworkimage);
			TextView path = (TextView) convertView.findViewById(
					R.id.tv_item_loadnetworkimage);
			image.setBackgroundResource(R.drawable.ic_launcher);
			path.setText(images.get(position));
			
			asyncImageLoader.loadDrawable(position, 
					images.get(position), new ImageCallback() {
				
				@Override
				public void onImageLoad(Integer t, Drawable drawable) {
					// TODO Auto-generated method stub
					View view = lv_loadnetworkimage.findViewWithTag(t);
					if(view != null){
						ImageView iv = (ImageView) view.findViewById(R.id.iv_item_loadnetworkimage);
						iv.setBackgroundDrawable(drawable);
						//iv.setBackground(drawable);
					}
				}
				
				@Override
				public void onError(Integer t) {
					// TODO Auto-generated method stub
					View view = lv_loadnetworkimage.findViewWithTag(t);
					if(view != null){
						ImageView iv = (ImageView) view.findViewById(R.id.iv_item_loadnetworkimage);
						iv.setBackgroundResource(R.drawable.ic_launcher);
					}
				}
			});
			
			return convertView;
		}
		
	}
	
	class Watcher implements TextWatcher {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (et_path.getText().toString().length() <= 7) {
				b_search.setVisibility(View.GONE);
			} else {
				b_search.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private void initImages(){
		images.add("http://img2.imgtn.bdimg.com/it/u=1251211055,786293259&fm=21&gp=0.jpg");
		images.add("http://img4.duitang.com/uploads/item/201508/11/20150811194252_BjcQe.jpeg");
		images.add("http://img2.3lian.com/2014/f6/79/84.jpg");
	}
}
