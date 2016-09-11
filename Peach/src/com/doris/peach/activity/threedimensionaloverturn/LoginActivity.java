package com.doris.peach.activity.threedimensionaloverturn;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.threedimensionaloverturn.CustomEdittext;
import com.doris.peachlibrary.view.threedimensionaloverturn.CustomTextView;
import com.doris.peachlibrary.view.threedimensionaloverturn.RippleView;
import com.doris.peachlibrary.view.threedimensionaloverturn.StereoView;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

/**
 * 
 * @author Doris
 *
 *         2016年7月27日
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

	private CustomEdittext etUsername;
	private CustomEdittext etEmail;
	private CustomEdittext etPassword;
	private RippleView rvUsername;
	private RippleView rvEmail;
	private RippleView rvPassword;
	private StereoView stereoView;
	private CustomTextView tvWelcome;
	private int translateY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_threedimensionaloverturn_login);

		stereoView = (StereoView) findViewById(R.id.stereoView);
		etUsername = (CustomEdittext) findViewById(R.id.et_username);
		etEmail = (CustomEdittext) findViewById(R.id.et_email);
		etPassword = (CustomEdittext) findViewById(R.id.et_password);
		rvUsername = (RippleView) findViewById(R.id.rv_username);
		rvEmail = (RippleView) findViewById(R.id.rv_email);
		rvPassword = (RippleView) findViewById(R.id.rv_password);
		tvWelcome = (CustomTextView) findViewById(R.id.tv_welcome);
		rvUsername.setOnClickListener(this);
		rvEmail.setOnClickListener(this);
		rvPassword.setOnClickListener(this);
		tvWelcome.setOnClickListener(this);
		
		stereoView.setStartScreen(2);
		stereoView.post(new Runnable() {
			@Override
			public void run() {
				int[] location = new int[2];
				stereoView.getLocationOnScreen(location);
				translateY = location[1] + 90;
			}
		});
		stereoView.setiStereoListener(new StereoView.IStereoListener() {
			@Override
			public void toPre(int curScreen) {
				// "跳转到前一页 "
			}

			@Override
			public void toNext(int curScreen) {
				// "跳转到下一页 "
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rv_username:
			rvUsername.setiRippleAnimListener(new RippleView.IRippleAnimListener() {
				@Override
				public void onComplete(View view) {
					stereoView.toPre();
				}
			});
			break;
		case R.id.rv_email:
			rvEmail.setiRippleAnimListener(new RippleView.IRippleAnimListener() {
				@Override
				public void onComplete(View view) {
					stereoView.toPre();
				}
			});
			break;
		case R.id.rv_password:
			rvPassword.setiRippleAnimListener(new RippleView.IRippleAnimListener() {
				@Override
				public void onComplete(View view) {
					stereoView.toPre();
				}
			});
			break;
		case R.id.tv_welcome:
			if (TextUtils.isEmpty(etUsername.getText())) {
				ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "请输入用户名!");
				stereoView.setItem(2);
				return;
			}
			if (TextUtils.isEmpty(etEmail.getText())) {
				ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "请输入邮箱!");
				stereoView.setItem(1);
				return;
			}
			if (TextUtils.isEmpty(etPassword.getText())) {
				ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "请输入密码!");
				stereoView.setItem(0);
				return;
			}
			startExitAnim();
			break;
		}
	}

	private void startExitAnim() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(stereoView, "translationY", 0, 100, -translateY);
		animator.setDuration(500).start();
		ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "登录成功 =.=");
	}
}
