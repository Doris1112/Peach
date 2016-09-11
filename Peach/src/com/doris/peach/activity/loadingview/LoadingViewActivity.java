package com.doris.peach.activity.loadingview;

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
 *         2016年6月30日
 */
public class LoadingViewActivity extends BaseActivity {

	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;
	private DialogUtil dialogUtil;
	private String[] bezier = { "边框模式", "填充模式" };
	private String[] effect4 = { "ViewPager", "GridView", "SystemView" };
	
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
		dialogUtil = new DialogUtil(context);
		
		list.add(getResources().getString(R.string.effect1));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, LoadingViewActivity1.class));
			}
		});
		list.add(getString(R.string.effect2));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, LoadingDrawableActivity.class));
			}
		});
		list.add(getString(R.string.effect3));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, LoadingViewActivity2.class));
			}
		});
		list.add(getString(R.string.effect4));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(effect4, getString(R.string.effect4),
						new OnClickDialogListViewItemListener() {
					
					@Override
					public void onClickClickDialogListViewItem(int position) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0:
							startActivity(new Intent(context, LoadingViewPagerActivity.class));
							break;
						case 1:
							startActivity(new Intent(context, LoadinViewGridActivity.class));
							break;
						case 2:
							startActivity(new Intent(context, LoadingViewSystemActivity.class));
							break;
						}
					}
				}, true);
			}
		});
		list.add(getString(R.string.Bezier));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(bezier, getString(R.string.Bezier),
						new OnClickDialogListViewItemListener() {
							
							@Override
							public void onClickClickDialogListViewItem(int position) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(context, BezierActivity.class);
								intent.putExtra("mode", position);
								startActivity(intent);
							}
						}, true);
			}
		});
	}
}