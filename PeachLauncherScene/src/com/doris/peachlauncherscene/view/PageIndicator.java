package com.doris.peachlauncherscene.view;

import android.support.v4.view.ViewPager;

/**
 * 代码来自开源项目ViewPagerIndicator
 * @author Doris
 *
 * 2016年8月11日
 */
public interface PageIndicator extends ViewPager.OnPageChangeListener {
	/**
	 * Bind the indicator to a ViewPager.
	 * 
	 * @param view
	 */
	void setViewPager(ViewPager view);

	/**
	 * Bind the indicator to a ViewPager.
	 * 
	 * @param view
	 * @param initialPosition
	 */
	void setViewPager(ViewPager view, int initialPosition);

	/**
	 * Set the current page of both the ViewPager and indicator.<br/>
	 * 
	 * This <strong>must</strong> be used if you need to set the page before the
	 * views are drawn on screen (e.g., default start page).<br/>
	 * 
	 * @param item
	 */
	void setCurrentItem(int item);

	/**
	 * Set a page change listener which will receive forwarded events.
	 * 
	 * @param listener
	 */
	void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

	/**
	 * Notify the indicator that the fragment list has changed.
	 */
	void notifyDataSetChanged();

}
