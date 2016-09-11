package com.doris.peach.activity.navigationpage;

import java.util.ArrayList;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.adapter.BasePagerAdapter;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 
 * @author Doris
 *
 *         2016年7月24日
 */
public class ViewPagerActivity extends BaseActivity implements OnPageChangeListener {

	private ViewPager vp_viewpager;
	private LinearLayout ll_indicator;
	private Button b_inApp;

	private PagerAdapter adapter;
	private int[] images = { R.drawable.start01, R.drawable.start02, R.drawable.start03 };
	private ArrayList<View> views = new ArrayList<View>();
	private ImageView[] indicators = new ImageView[images.length];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_viewpager);

		vp_viewpager = (ViewPager) findViewById(R.id.vp_viewpager);
		ll_indicator = (LinearLayout) findViewById(R.id.ll_indicator);
		b_inApp = (Button) findViewById(R.id.b_inApp);

		for (int i = 0; i < images.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(images[i]);
			views.add(imageView);
			indicators[i] = new ImageView(this);
			indicators[i].setBackgroundResource(R.drawable.indicators_default);
			if (i == 0) {
				indicators[i].setBackgroundResource(R.drawable.indicators_now);
			}
			ll_indicator.addView(indicators[i]);
		}

		adapter = new BasePagerAdapter(views);
		vp_viewpager.setAdapter(adapter);
		vp_viewpager.setOnPageChangeListener(this);
	}

	public void startBtn(View view) {
		ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_ok, "进入软件按钮被点击！");
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 == indicators.length - 1) {
			b_inApp.setVisibility(View.VISIBLE);
		} else {
			b_inApp.setVisibility(View.INVISIBLE);
		}
		// 更改指示器图片
		for (int i = 0; i < indicators.length; i++) {
			indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
			if (arg0 != i) {
				indicators[i].setBackgroundResource(R.drawable.indicators_default);
			}
		}
	}
}
