package com.doris.peach.activity;

import com.doris.peach.activity.sensor.AccelerometerActivity;
import com.doris.peach.activity.sensor.LightActivity;
import com.doris.peach.activity.sensor.OrientationActivity;
import com.doris.peach.activity.sensor.TumblerActivity;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author Doris
 *
 * 2016年4月21日
 */
public class SensorIntentActivity extends BaseIntentActivity{

	public static void goActivity(int flag, Context packageContext) {
		Intent intent = new Intent();
		switch (flag) {
		case 1:
			//加速度传感器
			goActivity(intent, packageContext, AccelerometerActivity.class);
			break;
		case 2:
			//加速度传感器 不倒翁
			goActivity(intent, packageContext, TumblerActivity.class);
			break;
		case 3:
			//方向传感器
			goActivity(intent, packageContext, OrientationActivity.class);
			break;
		case 5:
			//光线感应传感器
			goActivity(intent, packageContext, LightActivity.class);
			break;
		case 6:
			break;
		}
	}
}
