package com.doris.peach.activity.navigationpage;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * 
 * @author Doris
 *
 *         2016年7月25日
 */
public class ViewFlipperActivity extends BaseActivity implements OnGestureListener {

	private int[] imgID = { R.drawable.start01, R.drawable.start02, R.drawable.start03 };
	private ImageView im_1, im_2, im_3;
	private List<ImageView> ivs = new ArrayList<ImageView>();
	private ViewFlipper flipper;
	private GestureDetector detector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_viewflipper);

		im_1 = (ImageView) findViewById(R.id.iv_1);
		im_2 = (ImageView) findViewById(R.id.iv_2);
		im_3 = (ImageView) findViewById(R.id.iv_3);

		ivs.add(im_1);
		ivs.add(im_2);
		ivs.add(im_3);

		detector = new GestureDetector(this);
		flipper = (ViewFlipper) findViewById(R.id.viewflipper);
		for (int i = 0; i < imgID.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imgID[i]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			flipper.addView(imageView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
		ivs.get(0).setEnabled(false);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > 120) {
			// 添加动画
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in_viewflipper));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out_viewflipper));
			View view = flipper.getChildAt(imgID.length - 1);
			View view1 = flipper.getCurrentView();
			// flipper.getDisplayedChild();

			if (view == view1) {
				ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "最后一张");
				// return false;
			} else {
				this.flipper.showNext();
				dotChange(flipper.getDisplayedChild());
			}
			return true;
		} // 从右向左滑动
		else if (e1.getX() - e2.getX() < -120) {
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in_viewflipper));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out_viewflipper));
			if (flipper.getChildAt(0) == flipper.getCurrentView()) {
				ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "第一张");
			} else {
				this.flipper.showPrevious();
				dotChange(flipper.getDisplayedChild());
			}

			return true;
		}
		return true;
	}

	private void dotChange(int index) {
		for (int i = 0; i < ivs.size(); i++) {
			if (i == index) {
				ivs.get(i).setEnabled(false);
			} else {
				ivs.get(i).setEnabled(true);
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}
}
