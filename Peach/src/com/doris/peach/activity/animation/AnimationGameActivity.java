package com.doris.peach.activity.animation;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.AnimationGameImageView;
import com.doris.peachlibrary.view.AnimationGameImageView.OnFrameAnimationListener;

import android.os.Bundle;
import android.view.View;
/**
 * 
 * @author Doris
 *
 * 2016年5月9日
 */
public class AnimationGameActivity extends BaseActivity {

	private AnimationGameImageView agiv_anim;
	private OnFrameAnimationListener listener = new OnFrameAnimationListener() {
		
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onEnd() {
			// TODO Auto-generated method stub
			agiv_anim.loadAnimation(R.anim.anim_idle);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_game);
		
		agiv_anim = (AnimationGameImageView) findViewById(R.id.agiv_anim);
		agiv_anim.loadAnimation(R.anim.anim_idle);
	}
	
	public void attack(View view){
		agiv_anim.loadAnimation(R.anim.anim_attack, listener);
	}

	public void defense(View view){
		agiv_anim.loadAnimation(R.anim.anim_defense, listener);
	}
}
