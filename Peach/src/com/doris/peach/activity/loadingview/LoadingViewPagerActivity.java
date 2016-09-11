package com.doris.peach.activity.loadingview;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.loadingview.util.Style;
import com.doris.peachlibrary.view.loadingview.util.Colors;
import com.doris.peachlibrary.view.loadingview.util.ArgbEvaluator;
import com.doris.peachlibrary.view.loadingview.util.SpriteFactory;
import com.doris.peachlibrary.view.loadingview.Sprite;
import com.doris.peachlibrary.view.loadingview.SpinKitView;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年8月16日
 */
public class LoadingViewPagerActivity extends BaseActivity implements Colors {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loadingview_viewpager);

		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(new PagerAdapter() {
			@Override
			public int getCount() {
				return Style.values().length;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_loadingview_pager, null);

				SpinKitView spinKitView = (SpinKitView) view.findViewById(R.id.spin_kit);
				TextView name = (TextView) view.findViewById(R.id.name);
				Style style = Style.values()[position];
				name.setText(style.name());
				Sprite drawable = SpriteFactory.create(style);
				spinKitView.setIndeterminateDrawable(drawable);
				container.addView(view);

				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}
		});

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				int color = (int) ArgbEvaluator.getInstance().evaluate(positionOffset, colors[position % colors.length],
						colors[(position + 1) % colors.length]);
				getWindow().getDecorView().setBackgroundColor(color);
			}

			@Override
			public void onPageSelected(int position) {
				getWindow().getDecorView().setBackgroundColor(colors[position % colors.length]);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

}
