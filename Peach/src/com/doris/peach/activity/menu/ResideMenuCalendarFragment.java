package com.doris.peach.activity.menu;

import java.util.ArrayList;

import com.doris.peach.R;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 *         2016年7月26日
 */
public class ResideMenuCalendarFragment extends Fragment {

	private View parentView;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		parentView = inflater.inflate(R.layout.fragment_automenu_residecalendar, container, false);
		listView = (ListView) parentView.findViewById(R.id.listView);
		initView();
		return parentView;
	}

	private void initView() {
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
				getCalendarData());
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				ToastView.showWhiteContentToast(getActivity(), R.drawable.ic_toast_flag_verbose, "Clicked item!");
			}
		});
	}

	private ArrayList<String> getCalendarData() {
		ArrayList<String> calendarList = new ArrayList<String>();
		calendarList.add("New Year's Day");
		calendarList.add("St. Valentine's Day");
		calendarList.add("Easter Day");
		calendarList.add("April Fool's Day");
		calendarList.add("Mother's Day");
		calendarList.add("Memorial Day");
		calendarList.add("National Flag Day");
		calendarList.add("Father's Day");
		calendarList.add("Independence Day");
		calendarList.add("Labor Day");
		calendarList.add("Columbus Day");
		calendarList.add("Halloween");
		calendarList.add("All Soul's Day");
		calendarList.add("Veterans Day");
		calendarList.add("Thanksgiving Day");
		calendarList.add("Election Day");
		calendarList.add("Forefather's Day");
		calendarList.add("Christmas Day");
		return calendarList;
	}

}
