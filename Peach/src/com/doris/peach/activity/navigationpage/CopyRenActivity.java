package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 *         2016年7月25日
 */
public class CopyRenActivity extends BaseActivity {

	// 显示图片的ImageView组件
	private ImageView ivGuidePicture;
	// 要展示的一组图片资源
	private Drawable[] pictures;
	// 每张展示图片要执行的一组动画效果
	private Animation[] animations;
	// 当前执行的是第几张图片（资源索引）
	private int currentItem = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_ren);
		
		ivGuidePicture = (ImageView) findViewById(R.id.iv_guide_picture);
		//实例化引导图片数组
		pictures = new Drawable[] { getResources().getDrawable(R.drawable.v5_3_0_guide_pic1),
				getResources().getDrawable(R.drawable.v5_3_0_guide_pic2),
				getResources().getDrawable(R.drawable.v5_3_0_guide_pic3) };  
		// 实例化动画效果数组
		animations = new Animation[] { AnimationUtils.loadAnimation(this, R.anim.guide_fade_in),
				AnimationUtils.loadAnimation(this, R.anim.guide_fade_in_scale),
				AnimationUtils.loadAnimation(this, R.anim.guide_fade_out) };
		//给每个动画效果设置播放时间
        animations[0].setDuration(1500);  
        animations[1].setDuration(3000);  
        animations[2].setDuration(1500);  
        //给每个动画效果设置监听事件
        animations[0].setAnimationListener(new GuideAnimationListener(0));  
        animations[1].setAnimationListener(new GuideAnimationListener(1));  
        animations[2].setAnimationListener(new GuideAnimationListener(2));  
        //设置图片动画初始化默认值为0
        ivGuidePicture.setImageDrawable(pictures[currentItem]);  
        ivGuidePicture.startAnimation(animations[0]); 
	}
	
	/**
	 * 实现了动画监听接口，重写里面的方法
	 */
	class GuideAnimationListener implements AnimationListener {  		  
        private int index;  
  
        public GuideAnimationListener(int index) {  
            this.index = index;  
        }  
  
        @Override  
        public void onAnimationStart(Animation animation) {  
        }  
        
        //重写动画结束时的监听事件，实现了动画循环播放的效果
        @Override  
        public void onAnimationEnd(Animation animation) {  
            if (index < (animations.length - 1)) {  
                ivGuidePicture.startAnimation(animations[index + 1]);  
            } else {  
            	currentItem++;  
                if (currentItem > (pictures.length - 1)) {  
                	currentItem = 0;  
                }  
                ivGuidePicture.setImageDrawable(pictures[currentItem]);  
                ivGuidePicture.startAnimation(animations[0]);  
            }  
        }  
  
        @Override  
        public void onAnimationRepeat(Animation animation) {  
  
        }  
    } 
}
