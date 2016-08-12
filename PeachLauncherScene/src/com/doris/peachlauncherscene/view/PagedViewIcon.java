package com.doris.peachlauncherscene.view;

import com.doris.peachlauncherscene.bean.ApplicationInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 单个应用的图标和文字，通过TextView实现， 增加点击图标和文字变暗的效果
 * @author Doris
 *
 * 2016年8月11日
 */
public class PagedViewIcon extends TextView{
	
	public PagedViewIcon(Context context) {
		this(context, null);
		setFocusable(true);
	}

	public PagedViewIcon(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		setFocusable(true);
	}

	public PagedViewIcon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFocusable(true);
	}

	public void applyFromApplicationInfo(ApplicationInfo info) {
		Drawable d = createStateDrawable(getContext(), new BitmapDrawable(
				getContext().getResources(), info.iconBitmap));
		setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
		setText(info.title);
		setTag(info);
	}

	@Override
	protected boolean onSetAlpha(int alpha) {
		return false;
	}

	private StateListDrawable createStateDrawable(Context context,
			Drawable normal) {
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(View.PRESSED_ENABLED_STATE_SET,
				createPressDrawable(normal));
		drawable.addState(View.ENABLED_STATE_SET, normal);
		drawable.addState(View.EMPTY_STATE_SET, normal);
		return drawable;
	}

	private Drawable createPressDrawable(Drawable d) {
		Bitmap bitmap = ((BitmapDrawable) d).getBitmap().copy(
				Bitmap.Config.ARGB_8888, true);
		Paint paint = new Paint();
		paint.setColor(0x60000000);
		RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		new Canvas(bitmap).drawRoundRect(rect, 4, 4, paint);
		return new BitmapDrawable(getContext().getResources(), bitmap);
	}

}
