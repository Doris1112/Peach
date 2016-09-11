package com.doris.peach.activity.sensor;

import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.activity.SensorIntentActivity;
import com.doris.peachlibrary.view.ToastView;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年4月21日
 */
public class SensorActivity extends BaseActivity {

	private ListView lv_sensor;
	private List<Sensor> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);
		
		setTitle();
		SensorManager sm=(SensorManager)getSystemService(SENSOR_SERVICE);
		
		lv_sensor = (ListView) findViewById(R.id.lv_listButton);
		list = sm.getSensorList(Sensor.TYPE_ALL);
		
		lv_sensor.setAdapter(new SensorAdapter());
	}
	
	class SensorAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = LayoutInflater.from(SensorActivity.this).
						inflate(R.layout.item_button, null);
			}
			
			Button btn = (Button) convertView.findViewById(R.id.b_button);
			btn.setText(list.get(position).getName()+ "");
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ToastView.showWhiteContentToast(SensorActivity.this,
							R.drawable.ic_toast_flag_verbose, "这个功能太高级，还得靠大神！");
				}
			});
			
			switch (list.get(position).getType()) {
			case 1:
				//Sensor.TYPE_ACCELEROMETER
				btn.setText("加速度传感器");
				btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SensorIntentActivity.goActivity(1, SensorActivity.this);
					}
				});
				break;
			case 2:
				//Sensor.TYPE_MAGNETIC_FIELD
				btn.setText("磁力传感器");
				break;
			case 3:
				//Sensor.TYPE_ORIENTATION
				btn.setText("方向传感器");
				btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SensorIntentActivity.goActivity(3, SensorActivity.this);
					}
				});
				break;
			case 4:
				//Sensor.TYPE_GYROSCOPE
				btn.setText("陀螺仪传感器");
				break;
			case 5:
				//Sensor.TYPE_LIGHT
				btn.setText("光线感应传感器");
				btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SensorIntentActivity.goActivity(5, SensorActivity.this);
					}
				});
				break;
			case 6:
				//Sensor.TYPE_PRESSURE
				btn.setText("压力传感器");
				break;
			case 7:
				//Sensor.TYPE_TEMPERATURE
				btn.setText("温度传感器");
				break;
			case 8:
				//Sensor.TYPE_PROXIMITY
				btn.setText("距离传感器");
				break;
			case 9:
				//Sensor.TYPE_GRAVITY
				btn.setText("重力传感器");
				break;
			case 10:
				//Sensor.TYPE_LINEAR_ACCELERATION
				btn.setText("线性加速度传感器");
				break;
			case 11:
				//Sensor.TYPE_ROTATION_VECTOR
				btn.setText("旋转矢量传感器");
				break;
			case 12:
				//Sensor.TYPE_RELATIVE_HUMIDITY
				btn.setText("湿度传感器");
				break;
			case 13:
				//Sensor.TYPE_AMBIENT_TEMPERATURE
				btn.setText("温度传感器");
				break;
			case 14:
				//Android4.4后 API19
				//Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED
				btn.setText("未校准磁力传感器");
				break;
			case 15:
				//Sensor.TYPE_GAME_ROTATION_VECTOR
				btn.setText("游戏动作传感器");
				break;
			case 16:
				//Sensor.TYPE_GYROSCOPE_UNCALIBRATED
				btn.setText("未校准陀螺仪传感器");
				break;
			case 17:
				//Sensor.TYPE_SIGNIFICANT_MOTION
				btn.setText("特殊动作触发传感器");
				break;
			case 18:
				//Sensor.TYPE_STEP_DETECTOR
				btn.setText("步行检测传感器");
				break;
			case 19:
				//Sensor.TYPE_STEP_COUNTER
				btn.setText("计步传感器");
				break;
			case 20:
				//Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR
				btn.setText("地磁旋转矢量传感器");
				break;
			case 21:
				//Sensor.TYPE_HEART_RATE
				btn.setText("心率传感器");
				break;
			case 65565:
				btn.setText("紫外线传感器");
				break;
			case 65558:
				btn.setText("屏幕方向传感器");
				break;
			}
			return convertView;
		}
		
	}
}
