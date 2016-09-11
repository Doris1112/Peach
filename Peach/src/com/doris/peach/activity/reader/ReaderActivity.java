package com.doris.peach.activity.reader;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.adapter.ListButtonAdapter;
import com.doris.peachlibrary.dialog.Interface.OnClickDialogListViewItemListener;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.view.reader.util.CodeViewTheme;

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
 *         2016年8月8日
 */
public class ReaderActivity extends BaseActivity {

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

	private void initList() {
		context = this;
		dialogUtil = new DialogUtil(context);
		
		list.add(getString(R.string.readTxt));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ReadTextActivity.class);
				intent.putExtra("filePath", "text/readChinese.txt");
				startActivity(intent);
			}
		});
		list.add(getString(R.string.readCode));
		listener.add(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(new String[]{"Themes", "HtmlCode"},
						getString(R.string.readCode) , new OnClickDialogListViewItemListener() {
							
							@Override
							public void onClickClickDialogListViewItem(int position) {
								// TODO Auto-generated method stub
								switch (position) {
								case 0:
									getReadCodeTheme();
									break;
								case 1:
									Intent intent = new Intent(context, ReadCodeActivity.class);
									intent.putExtra("isHtml",true);
									startActivity(intent);
									break;
								}
							}
						}, true);
			}
		});
		list.add(getResources().getString(R.string.readPDF));
		listener.add(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, ReadPDFActivity.class));
			}
		});
	}
	
	private void getReadCodeTheme(){
		CodeViewTheme[] themes = CodeViewTheme.listThemes();
		String[] list = new String[themes.length];
		for (int i = 0; i < themes.length; i ++) {
			list[i] = themes[i].getName();
		}
		dialogUtil.listViewDialog(list, "Themes",
				new OnClickDialogListViewItemListener() {
					
					@Override
					public void onClickClickDialogListViewItem(int position) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context, ReadCodeActivity.class);
						intent.putExtra("theme",position);
						intent.putExtra("isHtml",false);
						startActivity(intent);
					}
				}, true);
	}

}
