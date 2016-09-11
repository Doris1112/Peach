package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年7月24日
 */
public class GuideViewDoorActivity extends Activity {

	// 定义左右两张图片对象
	private ImageView mLeft, mRight;
	// 定义一个文本对象
	private TextView mText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_guidedoor);

		// 实例化对象
		mLeft = (ImageView) findViewById(R.id.imageLeft);
		mRight = (ImageView) findViewById(R.id.imageRight);
		mText = (TextView) findViewById(R.id.anim_text);

		// 实例化动画对象
		AnimationSet anim = new AnimationSet(true);
		// 实例化位移动画对象
		TranslateAnimation mytranslateanim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
		// 设置动画持续时间
		mytranslateanim.setDuration(2000);
		// 设置启动时间
		anim.setStartOffset(800);
		// 将位移动画添加进动画效果中
		anim.addAnimation(mytranslateanim);
		// 动画结束后，保留在终止位
		anim.setFillAfter(true);
		// 左边图启动该动画效果
		mLeft.startAnimation(anim);

		AnimationSet anim1 = new AnimationSet(true);
		TranslateAnimation mytranslateanim1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, +1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
		mytranslateanim1.setDuration(1500);
		anim1.addAnimation(mytranslateanim1);
		anim1.setStartOffset(800);
		anim1.setFillAfter(true);
		mRight.startAnimation(anim1);

		AnimationSet anim2 = new AnimationSet(true);
		ScaleAnimation myscaleanim = new ScaleAnimation(1f, 3f, 1f, 3f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		myscaleanim.setDuration(1000);
		AlphaAnimation myalphaanim = new AlphaAnimation(1, 0.0001f);
		myalphaanim.setDuration(1500);
		anim2.addAnimation(myscaleanim);
		anim2.addAnimation(myalphaanim);
		anim2.setFillAfter(true);
		mText.startAnimation(anim2);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				GuideViewDoorActivity.this.finish();
			}
		}, 2300);
	}
}
