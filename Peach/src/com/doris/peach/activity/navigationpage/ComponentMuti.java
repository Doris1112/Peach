package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peachlibrary.view.navigationpage.util.Component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年8月15日
 */
public class ComponentMuti implements Component{

	@Override
	public View getView(LayoutInflater inflater) {
		LinearLayout ll = new LinearLayout(inflater.getContext());
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setLayoutParams(param);
		TextView textView = new TextView(inflater.getContext());
		textView.setText(R.string.nearbyBody);
		textView.setTextColor(inflater.getContext().getResources().getColor(android.R.color.white));
		textView.setTextSize(20);
		ImageView imageView = new ImageView(inflater.getContext());
		imageView.setImageResource(R.drawable.arrow);
		ll.removeAllViews();
		ll.addView(textView);
		ll.addView(imageView);
		ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// 引导层被点击了
			}
		});
		return ll;
	}

	@Override
	public int getAnchor() {
		return Component.ANCHOR_BOTTOM;
	}

	@Override
	public int getFitPosition() {
		return Component.FIT_START;
	}

	@Override
	public int getXOffset() {
		return 0;
	}

	@Override
	public int getYOffset() {
		return 30;
	}
	
}
