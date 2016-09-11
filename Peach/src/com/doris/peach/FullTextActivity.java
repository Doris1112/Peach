package com.doris.peach;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.fulltext.MoreTextView;
import com.doris.peachlibrary.view.fulltext.CollapsedTextView;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年8月3日
 */
public class FullTextActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fulltext);
		
		CollapsedTextView ctvView = (CollapsedTextView) findViewById(R.id.ctv_view);
        ctvView.setShowText(getString(R.string.sayingGoodbyeToCambridgeAgain));
        
        MoreTextView mtvText = (MoreTextView) findViewById(R.id.mtv_text);
        mtvText.setText(getString(R.string.sayingGoodbyeToCambridgeAgain));
	}
}
