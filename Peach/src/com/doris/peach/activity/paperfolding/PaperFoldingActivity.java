package com.doris.peach.activity.paperfolding;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.activity.paperfolding.util.View3D;

import android.content.Intent;
import android.os.Bundle;
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
 *         2016年8月3日
 */
public class PaperFoldingActivity extends BaseActivity {

	private Integer[] mThumbIds = { R.drawable.cocotte72x72, R.drawable.duck72x72, 
			R.drawable.boat72x72, R.drawable.austria72x72, R.drawable.blueyellow72x72 };
	private String[] mModels = { "cocotte.txt", "duck.txt", "boat.txt",
			"austria.txt", "cocotte.txt" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview_title);

		setTitle();
		GridView grid = (GridView) findViewById(R.id.gv);
		grid.setAdapter(new PapaerFoldingAdapter());
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				// TODO Auto-generated method stub
				if (View3D.front == 0) {
		    		View3D.texturesON = true;
		    		View3D.front = R.drawable.gally400x572; 
//		    		View3D.back = R.drawable.blue32x32; 
		    		View3D.back = R.drawable.sunako400x572;
		    		View3D.background = R.drawable.background256x256;
		    	}
		    	// Change Colors Textures
		    	if (id == mThumbIds.length-1) {
		    		View3D.texturesON = false;
		    	}else{
		    		View3D.texturesON = true;
		    	}
		    	startActivity(new Intent(PaperFoldingActivity.this,
		    			ModelView.class).putExtra("model", mModels[position]));
		    	return;
			}
		});
	}

	class PapaerFoldingAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mThumbIds.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mThumbIds[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView i = new ImageView(PaperFoldingActivity.this);
            i.setImageResource(mThumbIds[position]);
            i.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // 85 85 ou des icones 48x48
            i.setLayoutParams(new GridView.LayoutParams(170, 170));
            return i;
		}

	}

}
