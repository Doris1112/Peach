package com.doris.peach.activity.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.adapter.CalendarAdapter;
import com.doris.peachlibrary.view.ToastView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author Doris
 *
 * 2016年7月17日
 */
public class LunarCalendarActivity extends BaseActivity {
	
	private ViewFlipper vf_calendar;
	private TextView tv_year;
	private GridView gv_month;
	
	private CalendarAdapter adapter;
	// 每次滑动，增加或减去一个月,默认为0（即显示当前月）
	private static int jumpMonth = 0;
	// 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
	private static int jumpYear = 0;
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	private List<String[]> data = new ArrayList<String[]>();

	public LunarCalendarActivity() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date); // 当期日期
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_lunar);
		
		vf_calendar = (ViewFlipper) findViewById(R.id.vf_calendar);
		tv_year = (TextView) findViewById(R.id.tv_year);
		
		addGridView();
		
		adapter = new CalendarAdapter(this, jumpMonth, jumpYear, year_c,
				month_c, day_c, data);
		gv_month.setAdapter(adapter);
		addTextToTopTextView(tv_year);
		vf_calendar.addView(gv_month,0);
	}
	
	/**
	 * 上一个月
	 * 
	 * @param view
	 */
	public void previous(View view) {
		jumpMonth--;
		addGridView();
		adapter = new CalendarAdapter(this, jumpMonth, jumpYear, year_c,
				month_c, day_c, data);
		gv_month.setAdapter(adapter);
		addTextToTopTextView(tv_year);
		vf_calendar.addView(gv_month,1);
		vf_calendar.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_right_in));
		vf_calendar.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_right_out));
		vf_calendar.showNext();
		vf_calendar.removeViewAt(0);
	}

	/**
	 * 下一个月
	 * 
	 * @param view
	 */
	public void next(View view) {
		jumpMonth++;
		addGridView();
		adapter = new CalendarAdapter(this, jumpMonth, jumpYear, year_c,
				month_c, day_c, data);
		gv_month.setAdapter(adapter);
		addTextToTopTextView(tv_year);
		vf_calendar.addView(gv_month,1);
		vf_calendar.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_left_in));
		vf_calendar.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_left_out));
		vf_calendar.showNext();
		vf_calendar.removeViewAt(0);
		
	}
	
	/**
	 * 添加头部的年份 闰哪月等信息
	 * 
	 * @param view
	 */
	private void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		textDate.append(adapter.getShowYear()).append("年")
				.append(adapter.getShowMonth()).append("月").append("\t");
		if (!adapter.getLeapMonth().equals("")
				&& adapter.getLeapMonth() != null) {
			textDate.append("闰").append(adapter.getLeapMonth()).append("月")
					.append("\t");
		}
		textDate.append(adapter.getAnimalsYear()).append("年").append("(")
				.append(adapter.getCyclical()).append("年)");
		view.setText(textDate);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	/**
	 * 添加显示月份信息
	 */
	private void addGridView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		// 取得屏幕的宽度和高度
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int Width = display.getWidth();
		int Height = display.getHeight();

		gv_month = new GridView(this);
		gv_month.setNumColumns(7);
		gv_month.setColumnWidth(46);
		if (Width == 480 && Height == 800) {
			gv_month.setColumnWidth(69);
		}
		gv_month.setGravity(Gravity.CENTER_VERTICAL);
		// 去除gridView边框
		gv_month.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gv_month.setVerticalSpacing(1);
		gv_month.setHorizontalSpacing(1);
		gv_month.setBackgroundResource(R.drawable.ic_bg_white);
		gv_month.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				//点击任何一个item，得到这个item的日期
				int startPosition = adapter.getStartPositon();
				int endPosition = adapter.getEndPosition();
				if(startPosition <= position  && position <= endPosition){
					String scheduleYear = adapter.getShowYear();
					String scheduleMonth = adapter.getShowMonth();
					String scheduleDay = adapter.getDateByClickItem(position)
							.split("\\.")[0]; // 这一天的阳历
					
					ToastView.showWhiteContentToast(LunarCalendarActivity.this,
							R.drawable.ic_toast_flag_verbose,
							scheduleYear + "年" + scheduleMonth + "月" + scheduleDay + "日");
					
				}
			}
		});
		
		gv_month.setLayoutParams(params);
	}
}
