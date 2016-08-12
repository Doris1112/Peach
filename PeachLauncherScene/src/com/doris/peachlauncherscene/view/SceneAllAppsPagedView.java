package com.doris.peachlauncherscene.view;

import java.util.ArrayList;

import com.doris.peachlauncherscene.LauncherScene;
import com.doris.peachlauncherscene.R;
import com.doris.peachlauncherscene.bean.ApplicationInfo;
import com.doris.peachlauncherscene.util.LauncherXmlParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 主菜单中所有应用的view，继承PagedView 代码来自4.0的Launcher
 * @author Doris
 *
 * 2016年8月11日
 */
public class SceneAllAppsPagedView extends PagedView {
	private final static String TAG = "way";

	private static final int ROW_NUM = 5;
	private static final int COLUMN_NUM = 4;

	private ArrayList<ApplicationInfo> mApps;
	private int mNumAppsPages;
	private LauncherScene mSceneLauncher;

	private final LayoutInflater mLayoutInflater;

	public SceneAllAppsPagedView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SceneAllAppsPagedView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mFadeInAdjacentScreens = false;
		mContentIsRefreshable = false;
		mCellCountX = LauncherXmlParser.getSettingInt("scene_menu_column_num",
				COLUMN_NUM);
		mCellCountY = LauncherXmlParser.getSettingInt("scene_menu_row_num",
				ROW_NUM);
		mLayoutInflater = LayoutInflater.from(context);
		mSceneLauncher = (LauncherScene) context;
		Bitmap bmp = BitmapFactory.decodeFile(LauncherXmlParser
				.getMainmenuBgPath());
		if (bmp != null) {
			setBackgroundDrawable(new BitmapDrawable(context.getResources(),
					bmp));
		}
	}

	@Override
	public void syncPages() {
		removeAllViews();
		for (int i = 0; i < mNumAppsPages; ++i) {
			PagedViewCellLayout layout = new PagedViewCellLayout(getContext());
			setupPage(layout);
			addView(layout);
			syncPageItems(i, false);
		}
	}

	@Override
	public void syncPageItems(int page, boolean immediate) {
		// ensure that we have the right number of items on the pages
		int numCells = mCellCountX * mCellCountY;
		int startIndex = page * numCells;
		int endIndex = Math.min(startIndex + numCells, mApps.size());
		PagedViewCellLayout layout = null;
		try {
			layout = (PagedViewCellLayout) getPageAt(page);
		} catch (ClassCastException e) {
			Log.e(TAG, "syncAppsPageItems() error", e);
			layout = null;
			// layout = (PagedViewCellLayout) getChildAt(0);
		}

		if (layout == null) {
			Log.e(TAG, "syncAppsPageItems(), layout == null");
			return;
		}

		layout.removeAllViewsOnPage();
		ArrayList<Object> items = new ArrayList<Object>();
		ArrayList<Bitmap> images = new ArrayList<Bitmap>();
		for (int i = startIndex; i < endIndex; ++i) {
			ApplicationInfo info = mApps.get(i);
			PagedViewIcon icon = (PagedViewIcon) mLayoutInflater.inflate(
					R.layout.apps_customize_application, layout, false);
			icon.applyFromApplicationInfo(info);
			icon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mSceneLauncher.onAppsItemClick(v);
				}
			});

			int index = i - startIndex;
			int x = index % mCellCountX;
			int y = index / mCellCountX;
			layout.addViewToCellLayout(icon, -1, i,
					new PagedViewCellLayout.LayoutParams(x, y, 1, 1));

			items.add(info);
			images.add(info.iconBitmap);
		}
	}

	private void setVisibilityOnChildren(ViewGroup layout, int visibility) {
		int childCount = layout.getChildCount();
		for (int i = 0; i < childCount; ++i) {
			layout.getChildAt(i).setVisibility(visibility);
		}
	}

	private void setupPage(PagedViewCellLayout layout) {
		layout.setCellCount(mCellCountX, mCellCountY);
		layout.setGap(mPageLayoutWidthGap, mPageLayoutHeightGap);
		layout.setPadding(mPageLayoutPaddingLeft, mPageLayoutPaddingTop,
				mPageLayoutPaddingRight, mPageLayoutPaddingBottom);

		// Note: We force a measure here to get around the fact that when we do
		// layout calculations
		// immediately after syncing, we don't have a proper width. That said,
		// we already know the
		// expected page width, so we can actually optimize by hiding all the
		// TextView-based
		// children that are expensive to measure, and let that happen naturally
		// later.
		setVisibilityOnChildren(layout, View.GONE);
		int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
				MeasureSpec.AT_MOST);
		int heightSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
				MeasureSpec.AT_MOST);
		layout.measure(widthSpec, heightSpec);
		setVisibilityOnChildren(layout, View.VISIBLE);
	}

	private boolean testDataReady() {
		return !mApps.isEmpty();
	}

	private void updatePageCounts() {
		mNumAppsPages = (int) Math.ceil((float) mApps.size()
				/ (mCellCountX * mCellCountY));
	}

	public void setApps(ArrayList<ApplicationInfo> list) {
		mApps = list;
		updatePageCounts();
		if (testDataReady()) {
			setDataIsReady();
			syncPages();
			requestLayout();
		}
	}

}
