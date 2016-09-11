package com.doris.peach.activity.win8;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.activity.animation.Rotate3dAnimation;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.Win8Button1;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 * 2016年5月9日
 */
public class Win8ButtonActivity2 extends BaseActivity {

	private ViewGroup ll_win8btn_container;
	private Win8Button1 win8_01, win8_btn, win8_03;
	
	private final String Y = "Y";
	private int Statar_number = 2;
	
	Handler handler = new Handler();
	final Handler myHandler = new Handler();
	final Runnable myFrontResults = new Runnable() {
		@Override
		public void run() {
			showFront();
		}
	};
	final Runnable myBackResults = new Runnable() {
		@Override
		public void run() {
			showBack();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win8_button2);
		
		ll_win8btn_container = (ViewGroup) findViewById(
				R.id.ll_win8btn_container);
		win8_01 = (Win8Button1) findViewById(R.id.win8_01);
		win8_btn = (Win8Button1) findViewById(R.id.win8_btn);
		win8_03 = (Win8Button1) findViewById(R.id.win8_03);
		
		ll_win8btn_container.setPersistentDrawingCache(
				ViewGroup.PERSISTENT_ANIMATION_CACHE);
		win8_01.setImageBitmap(createReflectedImage(((BitmapDrawable)
				getResources().getDrawable(R.drawable.ic_win8_5))
				.getBitmap()));
		win8_01.setOnClickListener(new ImageTouchListener(
				R.drawable.ic_win8_5));
		networkOperation();
		win8_btn.setImageBitmap(createReflectedImage(((BitmapDrawable) 
				getResources().getDrawable(R.drawable.ic_win8_5))
				.getBitmap()));
		win8_btn.setOnClickListener(new ImageTouchListener(
				R.drawable.ic_win8_5));
		networkOperation();
		Setimage();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				handler.postDelayed(this, 1000);
				Statar_number++;
				if (Statar_number == 1) {
					ToastView.showWhiteContentToast(Win8ButtonActivity2.this,
							R.drawable.ic_toast_flag_verbose, 
							"点击Win8按钮触发事件");
				}
			}
		};

		// 启动计时器：
		handler.postDelayed(runnable, 1000); 
	}
	
	public void Setimage() {
		win8_01.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Statar_number = 0;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
				
				}
				return false;
			}
		});
		win8_03.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Statar_number = 0;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					
				}
				return false;
			}
		});
	}
	
	private final class ImageTouchListener implements OnClickListener {
		public int drawableId;
		public ImageTouchListener() {

		}
		public ImageTouchListener(int drawableId) {
			this.drawableId = drawableId;
		}
		@Override
		public void onClick(View v) {

		}
	}
	
	private Bitmap createReflectedImage(Bitmap originalBitmap) {
		final int reflectionGap = 4;

		int width = originalBitmap.getWidth();
		int height = originalBitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionBitmap = Bitmap.createBitmap(originalBitmap, 0, height / 2, width, height / 2, matrix, false);
		Bitmap withReflectionBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		Canvas canvas = new Canvas(withReflectionBitmap);
		canvas.drawBitmap(originalBitmap, 0, 0, null);

		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);

		canvas.drawBitmap(reflectionBitmap, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, originalBitmap.getHeight(), 0, withReflectionBitmap.getHeight(),
				0x70ffffff, 0x00ffffff, TileMode.MIRROR);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		canvas.drawRect(0, height, width, withReflectionBitmap.getHeight(), paint);

		return withReflectionBitmap;
	}
	
	public void showBack() {
		applyRotation(1, 0, 90, win8_01, win8_btn, ll_win8btn_container, Y);
	}

	public void showFront() {
		applyRotation(-1, 0, -90, win8_01, win8_btn, ll_win8btn_container, Y);
	}
	
	protected void networkOperation() {
		Thread t = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(3000);
						myHandler.post(myBackResults);
						Thread.sleep(2000);
						Thread.sleep(3000);
						myHandler.post(myFrontResults);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
	}
	
	private void applyRotation(int position, float start, float end, 
			ImageView imageView1, ImageView imageView2,
			ViewGroup container, String rotate) {
		final float centerX = container.getWidth() / 2.0f;
		final float centerY = container.getHeight() / 2.0f;

		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true, rotate);
		rotation.setDuration(500);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(position, imageView1, imageView2, container, rotate));

		container.startAnimation(rotation);
	}
	
	private final class DisplayNextView implements Animation.AnimationListener {
		private final int mPosition;
		private ViewGroup container;
		private ImageView imageView1;
		private ImageView imageView2;
		private String rotate;

		private DisplayNextView(int position, ImageView imageView1, ImageView imageView2, ViewGroup mcontainer,
				String rotate) {
			this.mPosition = position;
			this.imageView1 = imageView1;
			this.imageView2 = imageView2;
			this.container = mcontainer;
			this.rotate = rotate;
		}

		@Override
		public void onAnimationStart(Animation arg0) {
		}

		@Override
		public void onAnimationEnd(Animation arg0) {
			container.post(new SwapViews(mPosition, imageView1, imageView2, container, rotate));
		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
		}
	}
	
	private final class SwapViews implements Runnable {

		private final int mPosition;
		private ImageView imageView1;
		private ImageView imageView2;
		private ViewGroup container;
		private String rotate;

		public SwapViews(int position, ImageView imageView1, ImageView imageView2, ViewGroup mcGroup, String rotate) {
			this.mPosition = position;
			this.imageView1 = imageView1;
			this.imageView2 = imageView2;
			this.container = mcGroup;
			this.rotate = rotate;
		}

		@Override
		public void run() {
			final float centerX = ll_win8btn_container.getWidth() / 2.0f;
			final float centerY = ll_win8btn_container.getHeight() / 2.0f;
			Rotate3dAnimation rotation = null;
			if (mPosition > -1) {
				imageView1.setVisibility(View.GONE);
				imageView2.setVisibility(View.VISIBLE);
				imageView2.requestFocus();

				rotation = new Rotate3dAnimation(-90, 0, centerX, centerY, 310.0f, false, rotate);
			} else {
				imageView1.setVisibility(View.VISIBLE);
				imageView2.setVisibility(View.GONE);
				imageView1.requestFocus();

				rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false, rotate);
			}

			rotation.setDuration(500);
			rotation.setFillAfter(true);

			rotation.setInterpolator(new DecelerateInterpolator());

			container.startAnimation(rotation);
		}
	}
}
