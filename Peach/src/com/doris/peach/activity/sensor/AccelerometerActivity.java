package com.doris.peach.activity.sensor;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.activity.SensorIntentActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年4月21日
 */
public class AccelerometerActivity extends BaseActivity {

	private SensorManager sManager;
	private TextView tv_accelerometer;
	
	private SensorEntity sensor;
	private Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_accelerometer);
		
		tv_accelerometer = (TextView) findViewById(R.id.tv_accelerometer);
		sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		sensor = new SensorEntity(Sensor.TYPE_ACCELEROMETER, sManager);
		sensor.setListener(new SensorListener() {
			
			@Override
			public void onChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];
				
				tv_accelerometer.setText("摇一摇试试\nx:" + x + 
						"\ny:" + y + "\nz:" + z);
				
				int value = 15;
				if (x >= value || x <= -value || y >= value ||
						y <= -value || z >= value || z <= -value) {
					// 停止 开启 停止 开启
					long [] pattern = {100,400,100,400};
					//重复两次上面的pattern 如果只想震动一次，index设为-1   
					vibrator.vibrate(pattern, -1);
				}
			}
		});
	}
	
	public void tumbler(View view){
		SensorIntentActivity.goActivity(2, this);
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
		vibrator.cancel();
	}

}
