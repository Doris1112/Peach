package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.navigationpage.ShowcaseView;
import com.doris.peachlibrary.view.navigationpage.util.SimpleShowcaseEventListener;
import com.doris.peachlibrary.view.navigationpage.util.ViewTarget;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 *         2016年8月16日
 */
public class CircleSignMemoryManagementActivity extends BaseActivity {

	int currentShowcase = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_circlesign_singleshot);

		showcase();
	}

	private void showcase() {
		new ShowcaseView.Builder(this).withMaterialShowcase()
				.setContentText(String.format("Showing %1$d", currentShowcase))
				.setTarget(new ViewTarget(R.id.button, this))
				.setShowcaseEventListener(new SimpleShowcaseEventListener() {
					@Override
					public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
						currentShowcase++;
						showcase();
					}
				}).build();
	}

}
