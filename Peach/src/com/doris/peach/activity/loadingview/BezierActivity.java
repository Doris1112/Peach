package com.doris.peach.activity.loadingview;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.loadingview.MetaballView;

import android.os.Bundle;
import android.widget.SeekBar;

import com.doris.peachlibrary.view.loadingview.MetaballDebugView;

/**
 * 
 * @author Doris
 *
 *         2016年8月13日
 */
public class BezierActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

	private MetaballView metaballView;
	private MetaballDebugView debugMetaballView;
	private SeekBar seekBar1, seekBar2, seekBar3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loadingview_bezier);

		metaballView = (MetaballView) this.findViewById(R.id.metaball);
		debugMetaballView = (MetaballDebugView) findViewById(R.id.debug_metaball);
		seekBar1 = (SeekBar) findViewById(R.id.sb_mi);
		seekBar2 = (SeekBar) findViewById(R.id.sb_bca);
		seekBar3 = (SeekBar) findViewById(R.id.sb_bclr);
		seekBar1.setOnSeekBarChangeListener(this);
		seekBar2.setOnSeekBarChangeListener(this);
		seekBar3.setOnSeekBarChangeListener(this);

		int mode = getIntent().getIntExtra("mode", 1);
		metaballView.setPaintMode(mode);
		debugMetaballView.setPaintMode(mode);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		switch (seekBar.getId()) {
		case R.id.sb_mi:
			debugMetaballView.setMaxDistance(progress);
			break;
		case R.id.sb_bca:
			debugMetaballView.setMv(progress / 100f);
			break;
		case R.id.sb_bclr:
			debugMetaballView.setHandleLenRate(progress / 100f);
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
}
