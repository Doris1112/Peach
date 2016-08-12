package com.doris.peachlauncherscene.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/**
 * 代码来自4.0的Launcher
 * @author Doris
 *
 * 2016年8月11日
 */
public class PagedViewCellLayoutChildren extends ViewGroup {

	private boolean mCenterContent;

	private int mCellWidth;
	private int mCellHeight;
	private int mWidthGap;
	private int mHeightGap;

	public PagedViewCellLayoutChildren(Context context) {
		super(context);
	}

	@Override
	public void cancelLongPress() {
		super.cancelLongPress();

		// Cancel long press for all children
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			child.cancelLongPress();
		}
	}

	public void setGap(int widthGap, int heightGap) {
		mWidthGap = widthGap;
		mHeightGap = heightGap;
		requestLayout();
	}

	public void setCellDimensions(int width, int height) {
		mCellWidth = width;
		mCellHeight = height;
		requestLayout();
	}

	@Override
	public void requestChildFocus(View child, View focused) {
		super.requestChildFocus(child, focused);
		if (child != null) {
			Rect r = new Rect();
			child.getDrawingRect(r);
			requestRectangleOnScreen(r);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

		// int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
		/*
		 * if (widthSpecMode == MeasureSpec.UNSPECIFIED || heightSpecMode ==
		 * MeasureSpec.UNSPECIFIED) { throw new
		 * RuntimeException("CellLayout cannot have UNSPECIFIED dimensions"); }
		 */
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			PagedViewCellLayout.LayoutParams lp = (PagedViewCellLayout.LayoutParams) child
					.getLayoutParams();
			lp.setup(mCellWidth, mCellHeight, mWidthGap, mHeightGap,
					getPaddingLeft(), getPaddingTop());

			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.width,
					MeasureSpec.EXACTLY);
			int childheightMeasureSpec = MeasureSpec.makeMeasureSpec(lp.height,
					MeasureSpec.EXACTLY);

			child.measure(childWidthMeasureSpec, childheightMeasureSpec);
		}

		setMeasuredDimension(widthSpecSize, heightSpecSize);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();

		int offsetX = 0;
		if (mCenterContent && count > 0) {
			// determine the max width of all the rows and center accordingly
			int maxRowX = 0;
			int minRowX = Integer.MAX_VALUE;
			for (int i = 0; i < count; i++) {
				View child = getChildAt(i);
				if (child.getVisibility() != GONE) {
					PagedViewCellLayout.LayoutParams lp = (PagedViewCellLayout.LayoutParams) child
							.getLayoutParams();
					minRowX = Math.min(minRowX, lp.x);
					maxRowX = Math.max(maxRowX, lp.x + lp.width);
				}
			}
			int maxRowWidth = maxRowX - minRowX;
			offsetX = (getMeasuredWidth() - maxRowWidth) / 2;
		}

		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				PagedViewCellLayout.LayoutParams lp = (PagedViewCellLayout.LayoutParams) child
						.getLayoutParams();

				int childLeft = offsetX + lp.x;
				int childTop = lp.y;
				child.layout(childLeft, childTop, childLeft + lp.width,
						childTop + lp.height);
			}
		}
	}

	public void enableCenteredContent(boolean enabled) {
		mCenterContent = enabled;
	}

	@Override
	protected void setChildrenDrawingCacheEnabled(boolean enabled) {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View view = getChildAt(i);
			view.setDrawingCacheEnabled(enabled);
		}
	}

}