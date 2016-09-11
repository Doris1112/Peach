package com.doris.peach.activity.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.Log;
import com.doris.peachlibrary.view.calendar.CalendarView;
import com.doris.peachlibrary.view.calendar.CalendarView.OnItemClickListener;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年7月17日
 */
public class CalendarSelectActivity extends BaseActivity {

	private CalendarView calendar;
	private TextView calendarCenter;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private CheckBox cb_selectMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_select);

		setContentUnderStatus(findViewById(R.id.ll_calendarselect_content));
		
		calendar = (CalendarView) findViewById(R.id.cv_calendar);
		calendarCenter = (TextView) findViewById(R.id.tv_calendarCenter);
		cb_selectMore = (CheckBox) findViewById(R.id.cb_selectMore);
		
		calendar.setSelectMore(false); // 单选
		try {
			// 设置日历日期
			Date date = format.parse("2015-01-01");
			calendar.setCalendarData(date);
		} catch (ParseException e) {
			Log.getInstance().writeLog("获取日期出错：", e);
		}
		// 获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
		String[] ya = calendar.getYearAndmonth().split("-");
		calendarCenter.setText(ya[0] + "年" + ya[1] + "月");

		// 设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
		calendar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void OnItemClick(Date selectedStartDate, Date selectedEndDate, Date downDate) {
				if (calendar.isSelectMore()) {
					ToastView.showWhiteContentToast(CalendarSelectActivity.this, 
							R.drawable.ic_toast_flag_verbose,
							format.format(selectedStartDate) + "到" 
							+ format.format(selectedEndDate));
				} else {
					ToastView.showWhiteContentToast(CalendarSelectActivity.this, 
							R.drawable.ic_toast_flag_verbose,
							format.format(downDate));
				}
			}
		});
		//设置日历单选和多选
		cb_selectMore.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				calendar.setSelectMore(isChecked); 
			}
		});
	}

	public void doCalendar(View view) {
		switch (view.getId()) {
		case R.id.ib_calendarLeft:
			// 点击上一月 同样返回年月
			String leftYearAndmonth = calendar.clickLeftMonth();
			String[] ya = leftYearAndmonth.split("-");
			calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
			break;
		case R.id.ib_calendarRight:
			// 点击下一月
			String rightYearAndmonth = calendar.clickRightMonth();
			String[] yb = rightYearAndmonth.split("-");
			calendarCenter.setText(yb[0] + "年" + yb[1] + "月");
			break;
		}
	}
}
