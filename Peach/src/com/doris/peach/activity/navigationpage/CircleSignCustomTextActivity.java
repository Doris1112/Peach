package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.navigationpage.ShowcaseView;
import com.doris.peachlibrary.view.navigationpage.util.ViewTarget;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;

/**
 * 
 * @author Doris
 *
 *         2016年8月16日
 */
public class CircleSignCustomTextActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_circlesign_customtext);

		TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(getResources().getDimension(R.dimen.abc_text_size_body_1_material));
		paint.setStrikeThruText(true);
		paint.setColor(Color.RED);
		paint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/RobotoSlab-Regular.ttf"));

		TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		title.setTextSize(getResources().getDimension(R.dimen.abc_text_size_headline_material));
		title.setUnderlineText(true);
		title.setColor(Color.YELLOW);
		title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/RobotoSlab-Regular.ttf"));

		ShowcaseView showcaseView = new ShowcaseView.Builder(this).withNewStyleShowcase()
				.setTarget(new ViewTarget(R.id.imageView, this)).setContentTextPaint(paint)
				.setContentTitle(R.string.custom_text_painting_title).setContentText(R.string.custom_text_painting_text)
				.setContentTitlePaint(title).build();

		showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
		showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
		showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
	}

}
