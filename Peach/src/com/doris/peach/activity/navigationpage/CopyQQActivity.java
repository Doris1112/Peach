package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.navigationpage.util.Component;
import com.doris.peachlibrary.view.navigationpage.util.GuideBuilder;
import com.doris.peachlibrary.view.navigationpage.util.Guide;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 
 * @author Doris
 *
 * 2016年8月15日
 */
public class CopyQQActivity extends BaseActivity {

	private Button header_imgbtn;
	private LinearLayout ll_nearby;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_copyqq);
		
		header_imgbtn = (Button) findViewById(R.id.header_imgbtn);
		ll_nearby = (LinearLayout) findViewById(R.id.ll_nearby);
		header_imgbtn.post(new Runnable() {
			@Override
			public void run() {
				showGuideView();
			}
		});
	}
	
	private void showGuideView() {
		GuideBuilder builder = new GuideBuilder();
		builder.setTargetView(header_imgbtn).setAlpha(150).setHighTargetCorner(20).setHighTargetPadding(10)
				.setOverlayTarget(false).setOutsideTouchable(false);
		builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
			@Override
			public void onShown() {

			}

			@Override
			public void onDismiss() {
				showGuideView2();
			}
		});

		builder.addComponent(new ComponentSimple());
		Guide guide = builder.createGuide();
		guide.setShouldCheckLocInWindow(false);
		guide.show(this);
	}

	private void showGuideView2() {
		final GuideBuilder builder1 = new GuideBuilder();
		builder1.setTargetView(ll_nearby).setAlpha(150).setHighTargetGraphStyle(Component.CIRCLE)
				.setOverlayTarget(false).setExitAnimationId(android.R.anim.fade_out).setOutsideTouchable(false);
		builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
			@Override
			public void onShown() {

			}

			@Override
			public void onDismiss() {
				
			}
		});

		builder1.addComponent(new ComponentMuti());
		Guide guide = builder1.createGuide();
		guide.setShouldCheckLocInWindow(false);
		guide.show(this);
	}
	
}
