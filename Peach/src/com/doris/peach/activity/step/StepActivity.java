package com.doris.peach.activity.step;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.adapter.ListButtonAdapter;
import com.doris.peachlibrary.dialog.Interface.OnClickDialogListViewItemListener;
import com.doris.peachlibrary.util.DialogUtil;

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
 * 2016年8月12日
 */
public class StepActivity extends BaseActivity {

	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;
	private DialogUtil dialogUtil;
	private String[] verList = { "最新动态在前", "最新动态在后" };
	
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
		dialogUtil = new DialogUtil(context);
		
		list.add(getResources().getString(R.string.horizontal));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, FragmentStepActivity.class);
				intent.putExtra("isHorizontal", true);
				startActivity(intent);
			}
		});
		list.add(getString(R.string.vertical));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(verList, getString(R.string.vertical), 
						new OnClickDialogListViewItemListener() {
					
					@Override
					public void onClickClickDialogListViewItem(int position) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context, FragmentStepActivity.class);
						intent.putExtra("isHorizontal", false);
						intent.putExtra("order", position);
						startActivity(intent);
					}
				}, true);
			}
		});
		list.add(getString(R.string.horizontalSlide));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, HorizontalSlideActivity.class));
			}
		});
	}
}