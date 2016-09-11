package com.doris.peach.activity.threedimensionaloverturn;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.threedimensionaloverturn.StereoView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年7月27日
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener{
	
	private StereoView stereoView1;
    private StereoView stereoView2;
    private SeekBar sbAngle;
    private TextView tvAngle;
    private Button btnNext;
    private Button btnPre;
    private Button btnSelect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_threedimensionaloverturn_settings);
		
		initView();
		initStereoView();
	}
	
	private void initView() {
		stereoView1 = (StereoView) findViewById(R.id.stereoView1);
        stereoView2 = (StereoView) findViewById(R.id.stereoView2);
        sbAngle = (SeekBar) findViewById(R.id.sb_angle);
        tvAngle = (TextView) findViewById(R.id.tv_angle);
        btnPre = (Button) findViewById(R.id.btn_pre);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        sbAngle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                stereoView1.setAngle(i);
                tvAngle.setText("设置3D旋转页面夹角,当前夹角 " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_pre:
			stereoView1.toPre();
			break;
		case R.id.btn_next:
			stereoView1.toNext();
			break;
		case R.id.btn_select:
			stereoView1.setItem(3);
			break;
		}
	}
	
	private void initStereoView() {
        stereoView1.setResistance(4f)//设置滑动时阻力
                .setInterpolator(new BounceInterpolator())//设置
                .setStartScreen(2);//设置开始时item，注意不能是开头和结尾
        stereoView2.setCan3D(false);
    }
}
