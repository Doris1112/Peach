package com.doris.peach.activity.turn;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.adapter.ListButtonAdapter;
import com.doris.peachlibrary.util.FileUtil;
import com.doris.peachlibrary.view.ToastView;

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
 *         2016年6月29日
 */
public class TurnActivity extends BaseActivity {

	// 文件路径
	public static final String TEXTPATH = "/sdcard/peach/resource/text/turn.txt";
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
		
		list.add(getResources().getString(R.string.picTurn));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, TurnPicActivity.class));
			}
		});
		list.add(getResources().getString(R.string.picTurnV));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, TurnPicVActivity.class));
			}
		});
		list.add(getResources().getString(R.string.textTurn));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (downloadTxt())
					startActivity(new Intent(context, TurnTxtActivity.class));
			}
		});
	}

	private boolean downloadTxt() {
		File file = new File(TEXTPATH);
		if (!FileUtil.isFileExist(TEXTPATH.substring(0, TEXTPATH.lastIndexOf("/")), file)) {
			// 复制
			FileUtil.assetsCopyToSD(TEXTPATH.substring(0, TEXTPATH.lastIndexOf("/")),
					"text/turn.txt", TEXTPATH.substring(TEXTPATH.lastIndexOf("/")), this);
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, 
					"正在将文本复制到手机，请稍后！");
			return false;
		}
		return true;
	}
}
