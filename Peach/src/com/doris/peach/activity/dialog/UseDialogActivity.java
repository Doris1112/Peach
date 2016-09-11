package com.doris.peach.activity.dialog;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.activity.DialogIntentActivity;
import com.doris.peachlibrary.adapter.ListButtonAdapter;
import com.doris.peachlibrary.util.DeviceUtil;
import com.doris.peachlibrary.util.DialogUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年4月22日
 */
public class UseDialogActivity extends BaseActivity {
	
	private DialogUtil dialogUtil;
	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		
		initList();
		
		ListView lv_listButton = (ListView) findViewById(R.id.lv_list);
		lv_listButton.setAdapter(new ListButtonAdapter(list, listener, context));
		
		dialogUtil = new DialogUtil(this);
	}
	
	private void initList(){
		context = this;
		
		list.add(getResources().getString(R.string.dialogPopupfrombelow));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.dialogPopupfrombelow(
						DeviceUtil.getScreenHeight(UseDialogActivity.this));
			}
		});
		list.add(getResources().getString(R.string.dialogLoading));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.dialogLoading(true);
			}
		});
		list.add(getResources().getString(R.string.dialogLoading1));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.dialogLoading1(true);
			}
		});
		list.add(getResources().getString(R.string.dialogAnim));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.dialogAnim("自定义dialog进入和退出动画", null);
			}
		});
		list.add(getResources().getString(R.string.sweetAlert));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogIntentActivity.goActivity(7, UseDialogActivity.this);
			}
		});
	}
}
