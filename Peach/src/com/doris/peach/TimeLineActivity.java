package com.doris.peach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.domain.Diary;
import com.doris.peachlibrary.util.Log;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年7月17日
 */
public class TimeLineActivity extends BaseActivity {
	
	private List<Diary> diarys = new ArrayList<Diary>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);
		
		setTitle();
		init();
		Collections.sort(diarys, new Comparator<Diary>() {

			@Override
			public int compare(Diary lhs, Diary rhs) {
				// TODO Auto-generated method stub
				Date date1;
				Date date2;
				int returnValue = -1;
				try {
					date1 = sdf.parse(lhs.getDate());
					date2 = sdf.parse(rhs.getDate());
					// 对日期字段进行升序，如果欲降序可采用after方法  
	                if (date1.before(date2)) {  
	                	returnValue = 1;  
	                }  
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.getInstance().writeLog("时间轴-日期排序出错：", e);
				}
				return returnValue; 
			}
		});
		
		ListView lv_listButton = (ListView) findViewById(R.id.lv_listButton);
		lv_listButton.setAdapter(new TimelineAdapter(diarys));
	}
	
	/**
	 * 初始化数据
	 */
	private void init(){
		Diary diary0 = new Diary(1, "日志0", "2016-07-14 14:50:49", "好好学习");
		Diary diary1 = new Diary(2, "日志1", "2015-04-13 17:25:19", "好好学习");
		Diary diary2 = new Diary(3, "日志2", "2016-06-12 09:05:29", "好好学习");
		Diary diary3 = new Diary(4, "日志3", "2014-03-11 16:11:36", "好好学习");
		Diary diary4 = new Diary(5, "日志4", "2016-06-10 11:23:54", "好好学习");
		Diary diary5 = new Diary(6, "日志5", "2016-05-15 13:09:39", "好好学习");
		diarys.add(diary0);
		diarys.add(diary1);
		diarys.add(diary2);
		diarys.add(diary3);
		diarys.add(diary4);
		diarys.add(diary5);
	}
	
	private class TimelineAdapter extends BaseAdapter {

		private List<Diary> list;

		public TimelineAdapter( List<Diary> list) {
			super();
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("SimpleDateFormat")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_line, null);
				viewHolder = new ViewHolder();
				viewHolder.year = (TextView) convertView.findViewById(R.id.text_year);
				viewHolder.month_day = (TextView) convertView.findViewById(R.id.text_month_day);
				viewHolder.time = (TextView) convertView.findViewById(R.id.show_time);
				viewHolder.title = (TextView) convertView.findViewById(R.id.text_title);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			Diary diary = list.get(position);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
			Date date = null;
			try {
				date = sdf.parse(diary.getDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			viewHolder.year.setText(date.getYear() + 1900 + "");
			viewHolder.month_day.setText(date.getMonth() + 1 + "/" + date.getDate());
			viewHolder.time.setText(sdf1.format(date));
			viewHolder.title.setText(diary.getTitle());
			
			viewHolder.title.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			return convertView;
		}

		class ViewHolder {
			public TextView year;
			public TextView month_day;
			public TextView title;
			public TextView time;
		}
	}
}
