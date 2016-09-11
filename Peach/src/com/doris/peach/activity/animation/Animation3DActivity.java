package com.doris.peach.activity.animation;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.Win8Button1;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

/**
 * 
 * @author Doris
 *
 * 2016年5月9日
 */
public class Animation3DActivity extends BaseActivity {

	private Button b_car;
	private Win8Button1 win8btn;

	private int date[];
	private int date_id = 0;;
	private int screenW;
	private int mMouseDownX;
	private int mMouseDownY;

	int mMouseMoveX;
	int mMouseMoveY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation3d);

		b_car = (Button) findViewById(R.id.b_car);
		win8btn = (Win8Button1) findViewById(R.id.win8btn);

		DisplayMetrics dm = new DisplayMetrics();
		screenW = dm.widthPixels;

		date = new int[101];
		for (int j = 0; j < date.length; j++) {
			date[j] = R.drawable.aodi0000 + j;
		}

		win8btn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mMouseDownX = (int) event.getX();
					mMouseDownY = (int) event.getY();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {

				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					mMouseMoveX = (int) event.getX();
					mMouseMoveY = (int) event.getY();

					float x = mMouseDownX - mMouseMoveX;
					float y = mMouseDownY - mMouseMoveY;

					float x_limit = screenW / 3;
					float x_abs = Math.abs(x);
					float y_abs = Math.abs(y);

					if (x_abs >= y_abs) {
						if (x > x_limit || x < -x_limit) {
							if (x > 0) {
								// right
								date_id += 2;
								if (date_id >= date.length) {
									date_id = 0;
								}
								b_car.setBackgroundDrawable(getResources().getDrawable(date[date_id]));
							} else if (x <= 0) {
								date_id -= 2;
								if (date_id <= 0) {
									date_id = date.length;
									return true;
								}
								b_car.setBackgroundDrawable(getResources().getDrawable(date[date_id]));

							}
						}
					}

				}
				return false;
			}
		});
	}
}
