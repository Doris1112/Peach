package com.doris.peachlauncherscene.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 
 * @author Doris
 *
 * 2016年8月11日
 */
public class SceneWorkspace extends PagedView {
	public SceneWorkspace(Context context) {
		this(context, null);
	}

	public SceneWorkspace(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SceneWorkspace(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContentIsRefreshable = false;
		setDataIsReady();
		mFadeInAdjacentScreens = false;
	}

	@Override
	public void syncPages() {

	}

	@Override
	public void syncPageItems(int page, boolean immediate) {

	}

}
