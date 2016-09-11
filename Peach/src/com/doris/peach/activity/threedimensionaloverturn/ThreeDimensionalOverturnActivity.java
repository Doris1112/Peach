package com.doris.peach.activity.threedimensionaloverturn;

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
 *         2016年7月26日
 */
public class ThreeDimensionalOverturnActivity extends BaseActivity{

	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);
		
		setTitle();
		initList();
		
		ListView lv_listButton = (ListView) findViewById(R.id.lv_listButton);
		lv_listButton.setAdapter(new ListButtonAdapter(list, listener, context));
	}

	private void initList(){
		context = this;
		
		list.add(getResources().getString(R.string.login));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, LoginActivity.class));
			}
		});
		list.add(getResources().getString(R.string.setting));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, SettingsActivity.class));
			}
		});
		list.add(getResources().getString(R.string.picture));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, ImageActivity.class));
			}
		});
	}
}
