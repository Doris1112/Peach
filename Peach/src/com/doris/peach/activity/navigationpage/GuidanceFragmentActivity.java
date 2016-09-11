package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

/**
 * 
 * @author Doris
 *
 *         2016年7月24日
 */
public class GuidanceFragmentActivity extends BaseActivity {

	private View fl_guidanceFragment;
	private Fragment detailsAssertFragment;
	private boolean showAssert = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigationpage_guidancefragment);

		fl_guidanceFragment = findViewById(R.id.fl_guidanceFragment);
		
		final Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (fl_guidanceFragment.getWidth() > 0) {
					detailsAssertFragment = new MyJobDetailsAssertFragment();
					getFragmentManager().beginTransaction().add(R.id.fl_guidanceFragment, detailsAssertFragment).commit();
				} else {
					mHandler.postDelayed(this, 100);
				}
			}
		}, 500);
	}

	private class MyJobDetailsAssertFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.layout_guidance_fragment, null);
			LayoutParams params = new LayoutParams(fl_guidanceFragment.getWidth(), fl_guidanceFragment.getHeight());
			view.setLayoutParams(params);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					hideTheAssert();
				}
			});
			showAssert = true;
			return view;
		}
	}
	
	private void hideTheAssert() {
		getFragmentManager().beginTransaction().remove(detailsAssertFragment).commit();
		showAssert = false;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(showAssert){
			hideTheAssert();
		}else{
			super.onBackPressed();
		}
	}
	
	public void setting(View view){
		ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_ok, "设置按钮被点击了");
	}
}
