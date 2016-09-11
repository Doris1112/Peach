package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.navigationpage.ShowcaseView;
import com.doris.peachlibrary.view.navigationpage.util.Target;
import com.doris.peachlibrary.view.navigationpage.util.ViewTarget;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年8月16日
 */
public class CircleSignAnimationsActivity extends BaseActivity implements View.OnClickListener {

	private int counter = 0;
	private ShowcaseView showcaseView;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_circlesign_animations);

		textView1 = (TextView) findViewById(R.id.textView);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);

		showcaseView = new ShowcaseView.Builder(this).setTarget(new ViewTarget(findViewById(R.id.textView)))
				.setOnClickListener(this).build();
		showcaseView.setButtonText(getString(R.string.next));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (counter) {
		case 0:
			showcaseView.setShowcase(new ViewTarget(textView2), true);
			break;
		case 1:
			showcaseView.setShowcase(new ViewTarget(textView3), true);
			break;
		case 2:
			showcaseView.setTarget(Target.NONE);
			showcaseView.setContentTitle("Check it out");
			showcaseView.setContentText("You don't always need a target to showcase");
			showcaseView.setButtonText(getString(R.string.close));
			setAlpha(0.4f, textView1, textView2, textView3);
			break;
		case 3:
			showcaseView.hide();
			setAlpha(1.0f, textView1, textView2, textView3);
			break;
		}
		counter++;
	}

	private void setAlpha(float alpha, View... views) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			for (View view : views) {
				view.setAlpha(alpha);
			}
		}
	}

}
