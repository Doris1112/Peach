package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peachlibrary.view.navigationpage.util.Component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 
 * @author Doris
 *
 *         2016年8月15日
 */
public class ComponentSimple implements Component {

	@Override
	public View getView(LayoutInflater inflater) {

		LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layout_layer_frends, null);
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
		return Component.ANCHOR_LEFT;
	}

	@Override
	public int getFitPosition() {
		return Component.FIT_START;
	}

	@Override
	public int getXOffset() {
		return 50;
	}

	@Override
	public int getYOffset() {
		return 60;
	}

}
