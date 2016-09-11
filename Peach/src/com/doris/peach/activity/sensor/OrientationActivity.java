package com.doris.peach.activity.sensor;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年4月22日
 */
public class OrientationActivity extends BaseActivity {

	private TextView tv_orientation;
	private ImageView iv_orientation_compass;
	
	private SensorManager sManager;
	private SensorEntity sensor;
	
	private float fromDegrees = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_orientation);
		
		tv_orientation = (TextView) findViewById(R.id.tv_orientation);
		iv_orientation_compass = (ImageView) findViewById(R.id.iv_orientation_compass);
		
		sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = new SensorEntity(Sensor.TYPE_ORIENTATION, sManager);
		sensor.setListener(new SensorListener() {
			
			@Override
			public void onChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				tv_orientation.setText("转动手机试试\nx=" + event.values[0] 
						+ "\ny=" + event.values[1] 
						+ "\nz=" + event.values[2]);
				
				RotateAnimation animation = new RotateAnimation(fromDegrees,
						-event.values[0],
						RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
						RotateAnimation.RELATIVE_TO_SELF, 0.5f);
				animation.setDuration(200);
				iv_orientation_compass.startAnimation(animation);
				fromDegrees = -event.values[0];
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sensor.register();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensor.unregister();
	}

	
}
