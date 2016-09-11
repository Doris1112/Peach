package com.doris.peach.activity.dialog;

import com.doris.peach.R;
import com.doris.peachlibrary.view.ToastView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年4月28日
 */
public class DialogSpinnerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_spinner);
		
		ListView lv_spinner = (ListView) 
				findViewById(R.id.lv_dialog_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				R.layout.item_text_black, 
				new String[] { "选项1", "选项2", "选项3", "选项4",
						"选项5", "选项6" });
		lv_spinner.setAdapter(adapter);
		lv_spinner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				ToastView.showWhiteContentToast(DialogSpinnerActivity.this,
						R.drawable.ic_toast_flag_ok, 
						((TextView)view).getText().toString());
				finish();
			}
		});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}
}
