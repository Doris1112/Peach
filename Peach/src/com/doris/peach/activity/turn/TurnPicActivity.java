package com.doris.peach.activity.turn;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.DeviceUtil;
import com.doris.peachlibrary.view.CurlView;
import com.doris.peachlibrary.view.ZoomView;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 
 * @author Doris
 *
 * 2016年6月29日
 */
public class TurnPicActivity extends BaseActivity {

	private CurlView cv_images;
	private ZoomView zv_images;
	
	private boolean drawText = false;//决定是否显示文本或图片
	
	private int[] mBitmapIds = { R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14 };
	private String[] mTexts ={ "第一页", "第二页", "第三页", "第四页", "第五页", "最后一页" }; // 必须是成双的
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_turn_pic);
		
		cv_images = (CurlView) findViewById(R.id.cv_images);
		cv_images.setBitmapProvider(new BitmapProvider());
		cv_images.setSizeChangedObserver(new SizeChangedObserver());
		cv_images.setCurrentIndex(1);
		cv_images.setBackgroundColor(0xFF202830);
		cv_images.setEnableTouchPressure(true);
		
		zv_images = (ZoomView) findViewById(R.id.zv_images);
		zv_images.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				zv_images.onToutch(v, event);
				return true;
			}
		});
	}
	
	/**
	 * 图片提供.
	 */
	private class BitmapProvider implements CurlView.BitmapProvider{
		@Override
		public Bitmap getBitmap(int width, int height, int index){
			if (drawText) {
				 Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		         Canvas canvas = new Canvas(bitmap);
		         Paint paint = new Paint();
		         paint.setColor(Color.BLACK);
		         paint.setStyle(Style.FILL);
		         canvas.drawColor(0);
		         paint.setTextSize(50f);
		         paint.setTextAlign(Align.LEFT);
		         paint.setAntiAlias(true);
		         canvas.drawText(mTexts[index], 30, 60, paint);
				 return bitmap;
			} else {
				boolean isLeftPage = false;
				Bitmap b;
				if (index % 2 == 0){// 左边
					index = index / 2;
					isLeftPage = true;
				} else {
					index = (index / 2);
					isLeftPage = false;
				}
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				// 获取资源图片
				b = BitmapFactory.decodeResource(getResources(), getResources()
						.getIdentifier("p1" + index, "drawable", getPackageName()),
						opt);
				if (isLeftPage) {
					b = Bitmap.createBitmap(b, 0, 0, width, height);
				} else {
					b = Bitmap.createBitmap(b, width, 0, width, height);
				}
				return b;
			}
		}

		@Override
		public int getBitmapCount() {
			if(drawText)
				return mTexts.length;//
			else
				return mBitmapIds.length * 2; // 一张两页
		}

		@Override
		public int[] setBitmapSize() {
			int picSize[] = new int[2];
			if(drawText){
				picSize[0] = DeviceUtil.getScreenWidth(TurnPicActivity.this);
				picSize[1] = DeviceUtil.getScreenHeight(TurnPicActivity.this);
			}else{
				// just read first pic
				Drawable pic = getResources().getDrawable(mBitmapIds[0]);
				picSize[0] = pic.getIntrinsicWidth() / 2; // after will div 2
				picSize[1] = pic.getIntrinsicHeight();
			}
			return picSize;
		}
	}
	
	/**
	 *CurlView大小改变。
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
			if (w > h) {
				cv_images.setViewMode(CurlView.SHOW_TWO_PAGES);
			} else {
				cv_images.setViewMode(CurlView.SHOW_ONE_PAGE);
			}
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		cv_images.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		cv_images.onResume();
	}
	
	@Override
	public Object onRetainNonConfigurationInstance(){
		return cv_images.getCurrentIndex();
	}
}