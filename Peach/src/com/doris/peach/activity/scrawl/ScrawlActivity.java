package com.doris.peach.activity.scrawl;

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
 * 涂鸦
 * @author Doris
 *
 * 2016年6月23日
 */
public class ScrawlActivity extends BaseActivity {
	
	public static final String path = "/sdcard/peach/scrawl/";
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
		
		list.add(getResources().getString(R.string.gesture_scrawl));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, SelectScrawlBgActivity.class));
			}
		});
		list.add(getResources().getString(R.string.inpicscrawl));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, Scrawl2Activity.class));
			}
		});
		list.add(getResources().getString(R.string.scrawlpic));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, ShowScrawlsActivity.class));
			}
		});
	}
}