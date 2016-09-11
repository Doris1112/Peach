package com.doris.peach.activity.calendar;

import org.joda.time.LocalDate;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.calendar.CollapseCalendarView;
import com.doris.peachlibrary.view.calendar.util.CalendarManager;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年7月27日
 */
public class CollapseCalendarActivity extends BaseActivity {
	
	private CollapseCalendarView mCalendarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_collapse);
		
		CalendarManager manager = new CalendarManager(LocalDate.now(), CalendarManager.State.MONTH, LocalDate.now(), LocalDate.now().plusYears(1));

        mCalendarView = (CollapseCalendarView) findViewById(R.id.calendar);
        mCalendarView.init(manager);
	}
}
