package com.doris.peach.activity.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 
 * @author Doris
 *
 * 2016年4月21日
 */
public class SensorEntity implements SensorEventListener {

	// 两次检测的时间间隔
	//private static final int UPTATE_INTERVAL_TIME = 200;
	
	Sensor sensor;
	SensorManager sManager;
	SensorListener listener;

	public void setListener(SensorListener listener) {
		this.listener = listener;
	}

	//private long lastUpdateTime;

	public SensorEntity(int type, SensorManager sManager) {
		this.sensor = sManager.getDefaultSensor(type);
		this.sManager = sManager;
	}

	public void register() {
		sManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
	}

	public void unregister() {
		sManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		// 现在检测时间
		//long currentUpdateTime = System.currentTimeMillis();
		// 两次检测的时间间隔
		//long timeInterval = currentUpdateTime - lastUpdateTime;
		// 判断是否达到了检测时间间隔
		//if (timeInterval < UPTATE_INTERVAL_TIME)
			//return;
		// 现在的时间变成last时间
		//lastUpdateTime = currentUpdateTime;
		
		if(listener != null){
			listener.onChanged(arg0);
		}
	}

}
