package com.doris.peachlauncherscene.view;

import java.util.List;
import java.util.TimeZone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.View;

/**
 * 自定义数字时钟的view，代码来自framework下
 * @author Doris
 *
 * 2016年8月11日
 */
public class NumberClock extends View {
	private Time mCalendar;

	private Drawable mTimeBg;
	private Drawable mTimeColon;
	private List<Drawable> dTimeBmp;
	private List<Drawable> dApmBmp;

	private int mTimeBgWidth;
	private int mTimeBgHeight;

	private boolean mAttached;
	private final Handler mHandler = new Handler();
	private String mMinutes;
	private String mHour;

	public NumberClock(Context context, Drawable timeBg, Drawable timeColon,
			List<Drawable> timeBmp, List<Drawable> apmBmp) {
		super(context);
		mTimeBg = timeBg;
		mTimeColon = timeColon;
		dTimeBmp = timeBmp;
		dApmBmp = apmBmp;

		mCalendar = new Time();

		if (mTimeBg != null) {
			mTimeBgWidth = mTimeBg.getIntrinsicWidth();
			mTimeBgHeight = mTimeBg.getIntrinsicHeight();
		} else if (!dApmBmp.isEmpty() && dApmBmp.size() > 0) {
			mTimeBgWidth = mTimeColon.getIntrinsicWidth() + 4
					* dTimeBmp.get(0).getIntrinsicWidth()
					+ dApmBmp.get(0).getIntrinsicWidth();
			mTimeBgHeight = mTimeColon.getIntrinsicHeight() + 4
					* dTimeBmp.get(0).getIntrinsicHeight()
					+ dApmBmp.get(0).getIntrinsicHeight();
		} else {
			mTimeBgWidth = mTimeColon.getIntrinsicWidth() + 4
					* dTimeBmp.get(0).getIntrinsicWidth();
			mTimeBgHeight = mTimeColon.getIntrinsicHeight() + 4
					* dTimeBmp.get(0).getIntrinsicHeight();
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (!mAttached) {
			mAttached = true;
			IntentFilter filter = new IntentFilter();

			filter.addAction(Intent.ACTION_TIME_TICK);
			filter.addAction(Intent.ACTION_TIME_CHANGED);
			filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

			getContext().registerReceiver(mIntentReceiver, filter, null,
					mHandler);
		}

		// NOTE: It's safe to do these after registering the receiver since the
		// receiver always runs
		// in the main thread, therefore the receiver can't run before this
		// method returns.

		// The time zone may have changed while the receiver wasn't registered,
		// so update the Time
		mCalendar = new Time();

		// Make sure we update to the current time
		onTimeChanged();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAttached) {
			getContext().unregisterReceiver(mIntentReceiver);
			mAttached = false;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		float hScale = 1.0f;
		float vScale = 1.0f;

		if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mTimeBgWidth) {
			hScale = (float) widthSize / (float) mTimeBgWidth;
		}

		if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mTimeBgHeight) {
			vScale = (float) heightSize / (float) mTimeBgHeight;
		}

		float scale = Math.min(hScale, vScale);

		setMeasuredDimension(
				resolveSizeAndState((int) (mTimeBgWidth * scale),
						widthMeasureSpec, 0),
				resolveSizeAndState((int) (mTimeBgHeight * scale),
						heightMeasureSpec, 0));
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int availableWidth = getRight() - getLeft();
		int availableHeight = getBottom() - getTop();

		int x = availableWidth / 2;
		int y = availableHeight / 2;
		int w = 0;
		int h = 0;

		if (mTimeBg != null) {
			w = mTimeBg.getIntrinsicWidth();
			h = mTimeBg.getIntrinsicHeight();
		} else if (!dApmBmp.isEmpty() && dApmBmp.size() > 0 && !get24HourMode()) {
			w = mTimeColon.getIntrinsicWidth() + 4
					* dTimeBmp.get(0).getIntrinsicWidth()
					+ dApmBmp.get(0).getIntrinsicWidth();
			h = mTimeColon.getIntrinsicHeight() + 4
					* dTimeBmp.get(0).getIntrinsicHeight()
					+ dApmBmp.get(0).getIntrinsicHeight();
		} else {
			w = mTimeColon.getIntrinsicWidth() + 4
					* dTimeBmp.get(0).getIntrinsicWidth();
			h = mTimeColon.getIntrinsicHeight() + 4
					* dTimeBmp.get(0).getIntrinsicHeight();
		}

		boolean scaled = false;
		if (availableWidth < w || availableHeight < h) {
			scaled = true;
			float scale = Math.min((float) availableWidth / (float) w,
					(float) availableHeight / (float) h);
			canvas.save();
			canvas.scale(scale, scale, x, y);
		}

		int dis_x = x - (w / 2);
		int dis_y = y - (h / 2);
		if (mTimeBg != null) {
			final Drawable timeBg = mTimeBg;
			timeBg.setBounds(dis_x, dis_y, dis_x + w, dis_y + h);
			timeBg.draw(canvas);
			canvas.save();
		}

		int pos = Integer.parseInt(mHour.substring(0, 1));
		Drawable timeBmp = dTimeBmp.get(pos);
		int numW = timeBmp.getIntrinsicWidth();
		int numH = timeBmp.getIntrinsicHeight();
		timeBmp.setBounds(dis_x, dis_y, dis_x + numW, dis_y + numH);
		timeBmp.draw(canvas);
		pos = Integer.parseInt(mHour.substring(1, 2));
		timeBmp = dTimeBmp.get(pos);
		timeBmp.setBounds(dis_x + numW, dis_y, dis_x + 2 * numW, dis_y + numH);
		timeBmp.draw(canvas);

		final Drawable timeColon = mTimeColon;
		int colonW = timeColon.getIntrinsicWidth();
		int colonH = timeColon.getIntrinsicHeight();
		if (colonH < numH) {
			timeColon.setBounds(dis_x + 2 * numW, dis_y + (numH - colonH) / 2,
					dis_x + 2 * numW + colonW, dis_y + (numH - colonH) / 2
							+ colonH);
		} else {
			timeColon.setBounds(dis_x + 2 * numW, dis_y, dis_x + 2 * numW
					+ colonW, dis_y + colonH);
		}
		timeColon.draw(canvas);

		pos = Integer.parseInt(mMinutes.substring(0, 1));
		timeBmp = dTimeBmp.get(pos);
		timeBmp.setBounds(dis_x + 2 * numW + colonW, dis_y, dis_x + 3 * numW
				+ colonW, dis_y + numH);
		timeBmp.draw(canvas);
		pos = Integer.parseInt(mMinutes.substring(1, 2));
		timeBmp = dTimeBmp.get(pos);
		timeBmp.setBounds(dis_x + 3 * numW + colonW, dis_y, dis_x + 4 * numW
				+ colonW, dis_y + numH);
		timeBmp.draw(canvas);

		if (!dApmBmp.isEmpty() && dApmBmp.size() > 0) {
			if (!get24HourMode()) {
				if (mCalendar.hour > 12) {
					pos = 1;
				} else {
					pos = 0;
				}
				timeBmp = dApmBmp.get(pos);
				int apmW = timeBmp.getIntrinsicWidth();
				int apmH = timeBmp.getIntrinsicHeight();
				if (apmH < numH) {
					timeBmp.setBounds(dis_x + 4 * numW + colonW, dis_y
							+ (numH - apmH) / 2, dis_x + 4 * numW + colonW
							+ apmW, dis_y + (numH - apmH) / 2 + apmH);
				} else {
					timeBmp.setBounds(dis_x + 4 * numW + colonW, dis_y, dis_x
							+ 4 * numW + colonW + apmW, dis_y + apmH);
				}
				timeBmp.draw(canvas);
			}
		}

		if (scaled) {
			canvas.restore();
		}
	}

	private boolean get24HourMode() {
		return android.text.format.DateFormat.is24HourFormat(getContext());
	}

	private void onTimeChanged() {
		mCalendar.setToNow();

		if (!get24HourMode() && !dApmBmp.isEmpty() && dApmBmp.size() > 0) {
			if (mCalendar.hour > 12) {
				mHour = String.format("%02d", mCalendar.hour - 12);
			} else {
				mHour = String.format("%02d", mCalendar.hour);
			}
		} else {
			mHour = String.format("%02d", mCalendar.hour);
		}

		mMinutes = String.format("%02d", mCalendar.minute);

		updateContentDescription(mCalendar);
	}

	private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)) {
				String tz = intent.getStringExtra("time-zone");
				mCalendar = new Time(TimeZone.getTimeZone(tz).getID());
			}

			onTimeChanged();
			invalidate();
		}
	};

	private void updateContentDescription(Time time) {
		int flags = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_24HOUR;

		String contentDescription = DateUtils.formatDateTime(this.getContext(),
				time.toMillis(false), flags);
		setContentDescription(contentDescription);
	}

}
