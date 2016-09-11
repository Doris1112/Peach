package com.doris.peach.activity.listview;

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
 * 2016年5月11日
 */
public class ListViewActivity extends BaseActivity{
	
	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;
	
	private DialogUtil dialogUtil;

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
		
		list.add(getResources().getString(R.string.googleCardsExample));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, GoogleCardsActivity.class));
			}
		});
		list.add(getResources().getString(R.string.animationInAdapters));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(getResources().getStringArray(R.array.listviewInAnimation),
						getString(R.string.animationInAdapters),
						new OnClickDialogListViewItemListener() {

							@Override
							public void onClickClickDialogListViewItem(int position) {
								// TODO Auto-generated method stub
								goNext(position);
							}
						}, true);
			}
		});
		list.add(getResources().getString(R.string.itemManipulation));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(getResources().getStringArray(R.array.listviewDelete),
						getString(R.string.itemManipulation),
						new OnClickDialogListViewItemListener() {

							@Override
							public void onClickClickDialogListViewItem(int position) {
								// TODO Auto-generated method stub
								goNextDismiss(position);
							}
						}, true);
			}
		});
	}
	
	private void goNextDismiss(int index){
		Intent intent = null;
		switch (index) {
		case 0:
			intent = new Intent(this, SwipeDismissActivity.class);
			break;
		case 1:
			intent = new Intent(this, AnimateDismissActivity.class);
			break;
		case 2:
			intent = new Intent(this, ContextualUndoActivity.class);
			break;
		}
		if(intent != null)
			startActivity(intent);
	}
	
	private void goNext(int index){
		Intent intent = null;
		switch (index) {
		case 0:
			intent = new Intent(this, SwingBottomInActivity.class);
			break;
		case 1:
			intent = new Intent(this, SwingRightInActivity.class);
			break;
		case 2:
			intent = new Intent(this, SwingLeftInActivity.class);
			break;
		case 3:
			intent = new Intent(this, SwingBottomRightInActivity.class);
			break;
		case 4:
			intent = new Intent(this, ScaleInActivity.class);
			break;
		}
		if(intent != null)
			startActivity(intent);
	}
}
