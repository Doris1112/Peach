package com.doris.peach.activity.menu;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.menu.FloatingActionButton;
import com.doris.peachlibrary.view.menu.SubActionButton;
import com.doris.peachlibrary.view.menu.util.FloatingActionMenu;
import com.doris.peachlibrary.view.menu.util.FloatingActionMenu.MenuStateChangeListener;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 * 2016年7月25日
 */
public class FloatingActionButtonActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_automenu_floatingactionbutton);
		
		final ImageView fabIconNew = new ImageView(this);
		fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));
		final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
				.setContentView(fabIconNew).build();
		
		SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
		
		ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);
        
        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_chat_light));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera_light));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video_light));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place_light));
        
		final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
				.addSubActionView(rLSubBuilder.setContentView(rlIcon1).build(), new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ToastView.showWhiteContentToast(FloatingActionButtonActivity.this,
								R.drawable.ic_toast_flag_ok, "chat");
					}
				})
				.addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
				.addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
				.addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
				.attachTo(rightLowerButton).build();
		
		rightLowerMenu.setStateChangeListener(new MenuStateChangeListener() {
			
			@Override
			public void onMenuOpened(FloatingActionMenu menu) {
				// TODO Auto-generated method stub
				fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
			}
			
			@Override
			public void onMenuClosed(FloatingActionMenu menu) {
				// TODO Auto-generated method stub
				fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
			}
		});
		
		int redActionButtonSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_size);
		int redActionButtonMargin = getResources().getDimensionPixelOffset(R.dimen.action_button_margin);
		int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
		int redActionButtonContentMargin = getResources()
				.getDimensionPixelSize(R.dimen.red_action_button_content_margin);
		int redActionMenuRadius = getResources().getDimensionPixelSize(R.dimen.red_action_menu_radius);
		int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);
		int blueSubActionButtonContentMargin = getResources()
				.getDimensionPixelSize(R.dimen.blue_sub_action_button_content_margin);
		
		ImageView fabIconStar = new ImageView(this);
		fabIconStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_important));

		FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(redActionButtonSize,
				redActionButtonSize);
		starParams.setMargins(redActionButtonMargin, redActionButtonMargin, redActionButtonMargin,
				redActionButtonMargin);
		fabIconStar.setLayoutParams(starParams);

		FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(
				redActionButtonContentSize, redActionButtonContentSize);
		fabIconStarParams.setMargins(redActionButtonContentMargin, redActionButtonContentMargin,
				redActionButtonContentMargin, redActionButtonContentMargin);

		final FloatingActionButton leftCenterButton = new FloatingActionButton.Builder(this)
				.setContentView(fabIconStar, fabIconStarParams)
				.setBackgroundDrawable(R.drawable.button_action_red_selector)
				.setPosition(FloatingActionButton.POSITION_LEFT_CENTER).setLayoutParams(starParams).build();
		
		SubActionButton.Builder lCSubBuilder = new SubActionButton.Builder(this);
		lCSubBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue_selector));
		
		FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        blueContentParams.setMargins(blueSubActionButtonContentMargin,
                          blueSubActionButtonContentMargin,
                          blueSubActionButtonContentMargin,
                          blueSubActionButtonContentMargin);
        lCSubBuilder.setLayoutParams(blueContentParams);
        
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
        lCSubBuilder.setLayoutParams(blueParams);

        ImageView lcIcon1 = new ImageView(this);
        ImageView lcIcon2 = new ImageView(this);
        ImageView lcIcon3 = new ImageView(this);
        ImageView lcIcon4 = new ImageView(this);
        ImageView lcIcon5 = new ImageView(this);
        
        lcIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera));
        lcIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_picture));
        lcIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video));
        lcIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_location_found));
        lcIcon5.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_headphones));
	
//     final FloatingActionMenu leftCenterMenu = 
        		new FloatingActionMenu.Builder(this)
                .addSubActionView(lCSubBuilder.setContentView(lcIcon1, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setContentView(lcIcon2, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setContentView(lcIcon3, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setContentView(lcIcon4, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setContentView(lcIcon5, blueContentParams).build())
                .setRadius(redActionMenuRadius)
                .setStartAngle(70)
                .setEndAngle(-70)
                .attachTo(leftCenterButton)
                .build();
	}
	
}
