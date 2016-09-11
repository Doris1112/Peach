package com.doris.peach;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.adapter.ListButtonAdapter;
import com.doris.peachlibrary.view.ToastView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author Doris
 *
 * 2016年4月14日
 */
public class ToastActivity extends BaseActivity {
	
	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;
	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);
		
		setTitle();
		initList();
		
		ListView lv_listButton = (ListView) findViewById(R.id.lv_listButton);
		lv_listButton.setAdapter(new ListButtonAdapter(list, listener, context));
	}
	
	private void initList(){
		context = this;
		activity = this;
		
		list.add(getResources().getString(R.string.defaultToast));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, R.string.defaultToast,
						Toast.LENGTH_SHORT).show();
			}
		});
		list.add(getResources().getString(R.string.customDisplayPosition));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastView.showToastPosition(activity, R.drawable.ic_listselector,
						R.drawable.ic_toast_flag_verbose,
						getString(R.string.customDisplayPosition), 
						Gravity.CENTER);
			}
		});
		list.add(getResources().getString(R.string.customDisplayTime));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastView.MakeText(activity, getString(R.string.customDisplayTime)
						,R.drawable.ic_listselector,R.drawable.ic_toast_flag_verbose,
						5000,false).show();
			}
		});
		list.add(getResources().getString(R.string.customDisplayAnimations));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastView.MakeText(activity, getString(R.string.customDisplayAnimations)
						,R.drawable.ic_listselector,R.drawable.ic_toast_flag_verbose,
						2500,true).show();
			}
		});
		list.add(getResources().getString(R.string.customDisplayLayout));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastView.showToast(activity, R.drawable.ic_listselector, 
						R.drawable.ic_toast_flag_verbose,
						getString(R.string.customDisplayLayout), Toast.LENGTH_SHORT);
			}
		});
	}
}