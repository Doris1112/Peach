package com.doris.peach.activity.navigationpage;

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
 * 2016年7月22日
 */
public class NavigationPageActivity extends BaseActivity {
	
	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;
	private DialogUtil dialogUtil;
	
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
		
		list.add(getResources().getString(R.string.copyGanji));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, CopyGanjiActivity.class));
			}
		});
		list.add(getResources().getString(R.string.viewPage));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, ViewPagerActivity.class));
			}
		});
		list.add(getResources().getString(R.string.guidanceFragment));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, GuidanceFragmentActivity.class));
			}
		});
		list.add(getResources().getString(R.string.copymoji));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, CopyMojiActivity.class));
			}
		});
		list.add(getResources().getString(R.string.imitationWeChat));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, CopyWeChatActivity.class));
			}
		});
		list.add(getResources().getString(R.string.copyRen));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, CopyRenActivity.class));
			}
		});
		list.add(getResources().getString(R.string.autoViewFlow));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, AutoViewFlowActivity.class));
			}
		});
		list.add(getResources().getString(R.string.viewFlipper));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, ViewFlipperActivity.class));
			}
		});
		list.add(getResources().getString(R.string.SpringIndicator));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, SpringIndicatorActivity.class));
			}
		});
		list.add(getResources().getString(R.string.ColorAnimationView));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, ColorAnimationActivity.class));
			}
		});
		list.add(getResources().getString(R.string.Titanic));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, TitanicActivity.class));
			}
		});
		list.add(getResources().getString(R.string.copyQQ));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, CopyQQActivity.class));
			}
		});
		list.add(getResources().getString(R.string.circleSign));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(new String[] { "Fragments", "Events", 
						"Single shot", "Animations", "Custom text", "Custom showcase", 
						"Memory management" }, getString(R.string.circleSign), 
						new OnClickDialogListViewItemListener() {
							
							@Override
							public void onClickClickDialogListViewItem(int position) {
								// TODO Auto-generated method stub
								switch (position) {
								case 0:
									startActivity(new Intent(context, CircleSignFragmentActivity.class));
									break;
								case 1:
									startActivity(new Intent(context, CircleSignEventsActivity.class));
									break;
								case 2:
									startActivity(new Intent(context, CircleSignSingleShotActivity.class));
									break;
								case 3:
									startActivity(new Intent(context, CircleSignAnimationsActivity.class));
									break;
								case 4:
									startActivity(new Intent(context, CircleSignCustomTextActivity.class));
									break;
								case 5:
									startActivity(new Intent(context, CircleSignCustomShowcaseActivity.class));
									break;
								case 6:
									startActivity(new Intent(context, CircleSignMemoryManagementActivity.class));
									break;
								}
							}
						}, true);
			}
		});
	}
	
}
