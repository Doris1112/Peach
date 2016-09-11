package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.navigationpage.ShowcaseView;
import com.doris.peachlibrary.view.navigationpage.util.Target;
import com.doris.peachlibrary.view.navigationpage.util.ViewTarget;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年8月16日
 */
public class CircleSignSingleShotActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_circlesign_singleshot);
		
		Target viewTarget = new ViewTarget(R.id.button, this);
        new ShowcaseView.Builder(this)
                .setTarget(viewTarget)
                .setContentTitle(R.string.title_single_shot)
                .setContentText(R.string.desc_single_shot)
                .singleShot(13)
                .build();
	}
	
}
