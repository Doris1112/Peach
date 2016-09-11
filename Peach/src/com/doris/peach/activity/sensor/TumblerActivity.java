package com.doris.peach.activity.sensor;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.TumblerView;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年4月25日
 */
public class TumblerActivity extends BaseActivity {

	private TumblerView tumblerView;
	
	private SensorManager sManager;
	private SensorEntity sensor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tumblerView = new TumblerView(this, R.drawable.ic_tumbler);
		tumblerView.setBackgroundResource(R.color.peachThemeColor);
		setContentView(tumblerView);
		
		sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = new SensorEntity(Sensor.TYPE_ACCELEROMETER, sManager);
		sensor.setListener(new SensorListener() {
			
			@Override
			public void onChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				float[] values = event.values;
				float ax = values[0];
				float ay = values[1];

				double g = Math.sqrt(ax * ax + ay * ay);
				double cos = ay / g;
				if (cos > 1) {
					cos = 1;
				} else if (cos < -1) {
					cos = -1;
				}
				double rad = Math.acos(cos);
				if (ax < 0) {
					rad = 2 * Math.PI - rad;
				}

				int uiRot = getWindowManager().getDefaultDisplay().getRotation();
				double uiRad = Math.PI / 2 * uiRot;
				rad -= uiRad;

				tumblerView.setRotation(rad);
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
