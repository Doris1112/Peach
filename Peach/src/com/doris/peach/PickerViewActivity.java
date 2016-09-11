package com.doris.peach;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;
import com.doris.peachlibrary.domain.District;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.view.pickerview.OptionsPickerView;
import com.doris.peachlibrary.view.pickerview.TimePickerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年7月16日
 */
public class PickerViewActivity extends BaseActivity {

	private ArrayList<District> options1Items = new ArrayList<District>();
	private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
	private TextView tvTime, tvOptions;
	TimePickerView pvTime;
	OptionsPickerView pvOptions;
	View vMasker;
	
	private DialogUtil dialogUtil;
	private DBUtil dbUtil;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				setListener();
				dialogUtil.hideDialogLoading();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pickerview);
		vMasker = findViewById(R.id.vMasker);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvOptions = (TextView) findViewById(R.id.tvOptions);
		// 时间选择器
		pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
		// 控制时间范围
		// Calendar calendar = Calendar.getInstance();
		// pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
		// calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
		pvTime.setTime(new Date());
		pvTime.setCyclic(false);
		pvTime.setCancelable(true);
		// 时间选择后回调
		pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				tvTime.setText(getTime(date));
			}
		});
		// 弹出时间选择器
		tvTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pvTime.show();
			}
		});

		// 选项选择器
		pvOptions = new OptionsPickerView(this);

		dialogUtil = new DialogUtil(this);
		dbUtil = new DBUtil(this);
		dialogUtil.dialogLoading1(false);
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getData();
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	private void setListener() {
		// 二级联动效果
		pvOptions.setPicker(options1Items, options2Items, true);
		// 设置选择的二级单位
//		pvOptions.setLabels("省", "市", "区");
		pvOptions.setTitle("选择城市");
		// 设置默认选中的三级项目
		pvOptions.setCyclic(false, true, true);
		// 监听确定选择按钮
		pvOptions.setSelectOptions(0, 0, 0);
		pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				// 返回的分别是三个级别的选中位置
				String tx = options1Items.get(options1).getDistrict() 
						+ options2Items.get(options1).get(option2);
				tvOptions.setText(tx);
				vMasker.setVisibility(View.GONE);
			}
		});
		// 点击弹出选项选择器
		tvOptions.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pvOptions.show();
			}
		});
	}

	private void getData() {
		// 选项1
		if (BaseData.provinces.size() <= 0) {
			BaseData.initProvinces(dbUtil);
		}
		options1Items.addAll(BaseData.provinces);

		// 选项2
		List<District> districts = new ArrayList<District>();
		for (District option1 : options1Items) {
			ArrayList<String> options2 = new ArrayList<String>();
			List<String[]> listOptions2 = dbUtil.query(BaseData.TB_DISTRICT,
					new String[]{BaseData.DISTRICT_ID, 
							BaseData.DISTRICT_ITEM}, 
					new String[]{BaseData.DISTRICT_FLAG}, 
					new String[]{option1.getIndex()});
			for(int i = 0; i < listOptions2.size(); i ++){
				options2.add(listOptions2.get(i)[1]);
				districts.add(new District(listOptions2.get(i)[1], listOptions2.get(i)[0]));
			}
			options2Items.add(options2);
		}
	}

	public static String getTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
}
