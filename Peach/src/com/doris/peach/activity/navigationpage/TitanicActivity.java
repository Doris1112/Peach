package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.navigationpage.Titanic;
import com.doris.peachlibrary.view.navigationpage.TitanicTextView;

import android.graphics.Typeface;
import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年8月5日
 */
public class TitanicActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_titanic);
		
		TitanicTextView tv = (TitanicTextView) findViewById(R.id.ttv);
		tv.setTypeface(Typeface.createFromAsset(getAssets(),
				"fonts/Satisfy-Regular.ttf"));
		Titanic titanic = new Titanic();
		titanic.start(tv);
	}
	
}
