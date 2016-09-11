package com.doris.peach.activity.sensor;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年4月23日
 */
public class LightActivity extends BaseActivity {

	private TextView tv_light;
	
	private SensorManager sManager;
	private SensorEntity sensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_light);
		
		tv_light = (TextView) findViewById(R.id.tv_light);
		
		sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = new SensorEntity(Sensor.TYPE_LIGHT, sManager);
		sensor.setListener(new SensorListener() {
			
			@Override
			public void onChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				float value = event.values[0];
				tv_light.setText("遮住手机上方试试\n光线值：" + value
						+ "\n设置屏幕亮度：" + (value/255f));
				LayoutParams params = LightActivity.this.getWindow().getAttributes();
				params.screenBrightness = value/255f;
				LightActivity.this.getWindow().setAttributes(params);
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
