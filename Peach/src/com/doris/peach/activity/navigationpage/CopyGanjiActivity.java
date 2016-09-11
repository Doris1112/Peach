package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.listener.FeatureAnimationListener;
import com.doris.peachlibrary.listener.OnScrollChangedListener;
import com.doris.peachlibrary.view.ObservableScrollView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 
 * @author Doris
 *
 * 2016年7月24日
 */
public class CopyGanjiActivity extends BaseActivity implements OnGlobalLayoutListener, OnScrollChangedListener  {
	
	private ObservableScrollView osv_scrollview;
	private View ll_anim;
	
	private int mScrollViewHeight;
	private int mStartAnimateTop;
	private boolean hasStart = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_ganji);
		
		osv_scrollview = (ObservableScrollView) findViewById(R.id.osv_scrollview);
		osv_scrollview.getViewTreeObserver().addOnGlobalLayoutListener(this);
		osv_scrollview.setOnScrollChangedListener(this);
		
		ll_anim = findViewById(R.id.ll_anim);
		ll_anim.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onScrollChanged(int top, int oldTop) {
		// TODO Auto-generated method stub
		int animTop = ll_anim.getTop() - top;
		
		if(top > oldTop) {
			if(animTop < mStartAnimateTop && !hasStart) {
				Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.feature_anim2scale_in);
				anim1.setAnimationListener(new FeatureAnimationListener(ll_anim, true));
				
				ll_anim.startAnimation(anim1);
				hasStart = true;
			}
		} else {
			if(animTop > mStartAnimateTop && hasStart) {
				Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.feature_anim2scale_out);
				anim1.setAnimationListener(new FeatureAnimationListener(ll_anim, false));
				
				ll_anim.startAnimation(anim1);
				hasStart = false;
			}
		}
	}

	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub
		mScrollViewHeight = osv_scrollview.getHeight();
		mStartAnimateTop = mScrollViewHeight / 3 * 2;
	}
}
