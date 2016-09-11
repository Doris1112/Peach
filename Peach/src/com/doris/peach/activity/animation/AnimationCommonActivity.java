package com.doris.peach.activity.animation;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 * 2016年5月9日
 */
public class AnimationCommonActivity extends BaseActivity {

	private ImageView iv_anim_image, iv_anim_image_frame;
	private AlphaAnimation alphaAnimation;
	private ScaleAnimation scaleAnimation;
	private TranslateAnimation translateAnimation;
	private RotateAnimation rotateAnimation;
	private AnimationSet animationSet;
	private AnimationSet animationSet2;
	private AnimationDrawable animationDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_common);

		iv_anim_image = (ImageView) findViewById(R.id.iv_anim_image);
		iv_anim_image_frame = (ImageView) findViewById(
				R.id.iv_anim_image_frame);

		// 加载动画
		alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.image_alpha);
		scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.image_scale);
		translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.image_translate);
		rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.image_rotate);
		animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.image_all);
		animationSet2 = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.image_all2);
		
		animationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				iv_anim_image.startAnimation(animationSet2);
			}
		});
	
		animationDrawable = (AnimationDrawable) iv_anim_image_frame.getBackground();
	}

	public void setAnimation(View view) {
		switch (view.getId()) {
		case R.id.b_anim_alpha:
			iv_anim_image.startAnimation(alphaAnimation);
			break;
		case R.id.b_anim_scale:
			iv_anim_image.startAnimation(scaleAnimation);
			break;
		case R.id.b_anim_translate:
			iv_anim_image.startAnimation(translateAnimation);
			break;
		case R.id.b_anim_Rotate:
			iv_anim_image.startAnimation(rotateAnimation);
			break;
		case R.id.b_anim_all:
			iv_anim_image.startAnimation(animationSet);
			break;
		case R.id.b_anim_start_frame:
			animationDrawable.start();
			break;
		}
	}
}
