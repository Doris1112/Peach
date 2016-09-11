package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.navigationpage.ShowcaseView;
import com.doris.peachlibrary.view.navigationpage.util.MultiEventListener;
import com.doris.peachlibrary.view.navigationpage.util.OnShowcaseEventListener;
import com.doris.peachlibrary.view.navigationpage.util.SimpleShowcaseEventListener;
import com.doris.peachlibrary.view.navigationpage.util.ViewTarget;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.SpannableStringBuilder;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年8月16日
 */
public class CircleSignEventsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_circlesign_events);

		TextView eventLog = (TextView) findViewById(R.id.events_log);
		Button customButton = (Button) getLayoutInflater()
				.inflate(R.layout.layout_navigationpage_circlesign_eventsbutton, null);

		MultiEventListener multiEventListener = new MultiEventListener(new LogToTextListener(eventLog),
				new ShakeButtonListener(customButton));
		new ShowcaseView.Builder(this).withMaterialShowcase().setStyle(R.style.CustomShowcaseTheme3)
				.setTarget(new ViewTarget(R.id.imageView, this)).setContentTitle("Events")
				.setContentText("Listening to ShowcaseView events is easy!")
				.setShowcaseEventListener(multiEventListener).replaceEndButton(customButton).build();
	}

	private static class LogToTextListener implements OnShowcaseEventListener {

		private final TextView eventLog;
		private final SpannableStringBuilder stringBuilder;

		public LogToTextListener(TextView eventLog) {
			this.eventLog = eventLog;
			this.stringBuilder = new SpannableStringBuilder();
		}

		@Override
		public void onShowcaseViewHide(ShowcaseView showcaseView) {
			append("Showcase hiding");
		}

		private void append(String text) {
			stringBuilder.append("\n").append(text);
			eventLog.setText(stringBuilder);
		}

		@Override
		public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
			append("Showcase hidden");
		}

		@Override
		public void onShowcaseViewShow(ShowcaseView showcaseView) {
			append("Showcase shown");
		}

		@Override
		public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {
			append("Touch blocked: x: " + motionEvent.getX() + " y: " + motionEvent.getY());
		}

	}

	private class ShakeButtonListener extends SimpleShowcaseEventListener {
		private final Button button;

		public ShakeButtonListener(Button button) {
			this.button = button;
		}

		@Override
		public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {
			int translation = getResources().getDimensionPixelOffset(R.dimen.touch_button_wobble);
			ViewCompat.animate(button).translationXBy(translation).setInterpolator(new WobblyInterpolator(3));
		}
	}

	private class WobblyInterpolator implements Interpolator {

		private final double CONVERT_TO_RADS = 2 * Math.PI;
		private final int cycles;

		public WobblyInterpolator(int cycles) {
			this.cycles = cycles;
		}

		@Override
		public float getInterpolation(float proportion) {
			double sin = Math.sin(cycles * proportion * CONVERT_TO_RADS);
			return (float) sin;
		}
	}

}
