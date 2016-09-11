package com.doris.peach.activity.menu;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.adapter.ListButtonAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年7月25日
 */
public class AutoMenuActivity extends BaseActivity {

	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;
	
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
		
		list.add(getResources().getString(R.string.FloatingActionButton));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, FloatingActionButtonActivity.class));
			}
		});
		list.add(getResources().getString(R.string.FloatingActionButton_));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, FloatingActionButtonActivity1.class));
			}
		});
		list.add(getResources().getString(R.string.FragmentButton));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, FragmentButtonActivity.class));
			}
		});
		list.add(getResources().getString(R.string.customAnimation));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, CustomAnimationActivity.class));
			}
		});
		list.add(getResources().getString(R.string.ScrollView));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, ScrollViewMenuActivity.class));
			}
		});
		list.add(getResources().getString(R.string.ResideMenu));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, ResideMenuActivity.class));
			}
		});
		list.add(getResources().getString(R.string.SatelliteMenu));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, SatelliteMenuActivity.class));
			}
		});
	}
	
}
