package com.doris.peach.activity.win8;

import com.doris.peach.R;
import com.doris.peach.R.drawable;
import com.doris.peach.R.id;
import com.doris.peach.R.layout;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.Win8Button;
import com.doris.peachlibrary.view.Win8Button.OnViewClick;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年4月15日
 */
public class Win8ButtonActivity1 extends BaseActivity {
	
	private OnViewClick onclick = new OnViewClick() {
		
		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			ToastView.showWhiteContentToast(Win8ButtonActivity1.this, R.drawable.ic_toast_flag_verbose, "Win8Button");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win8_button1);
		
		Win8Button w8b_btn = (Win8Button) findViewById(R.id.w8b_btn);
		w8b_btn.setOnClickIntent(onclick);
		Win8Button w8b_btn1 = (Win8Button) findViewById(R.id.w8b_btn1);
		w8b_btn1.setOnClickIntent(onclick);
		Win8Button w8b_btn2 = (Win8Button) findViewById(R.id.w8b_btn2);
		w8b_btn2.setOnClickIntent(onclick);
		Win8Button w8b_btn3 = (Win8Button) findViewById(R.id.w8b_btn3);
		w8b_btn3.setOnClickIntent(onclick);
	}

}
