package com.doris.peach;

import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.CheckSwitchButton;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

/**
 * 
 * @author Doris
 *
 *         2016年7月9日
 */
public class SwitchButtonActivity extends BaseActivity {

	private ToggleButton tb_switchbutton;
	private CheckSwitchButton csb_switchbutton, csb_switchbutton1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switchbutton);

		tb_switchbutton = (ToggleButton) findViewById(R.id.tb_switchbutton);
		csb_switchbutton = (CheckSwitchButton) findViewById(R.id.csb_switchbutton);
		csb_switchbutton1 = (CheckSwitchButton) findViewById(R.id.csb_switchbutton1);

		tb_switchbutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					// 选中
					ToastView.showWhiteContentToast(SwitchButtonActivity.this, R.drawable.ic_toast_flag_ok, "仿IOS7开启");
				} else {
					// 关闭
					ToastView.showWhiteContentToast(SwitchButtonActivity.this, R.drawable.ic_toast_flag_ok, "仿IOS7关闭");
				}
			}
		});
		csb_switchbutton.setChecked(false);
		csb_switchbutton1.setEnabled(false);
		csb_switchbutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				csb_switchbutton1.setEnabled(isChecked);
			}
		});
	}
}
