package com.doris.peach.activity.loadingview;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.PeachSwitchButton;
import com.doris.peachlibrary.view.PeachSwitchButton.OnSwitchChangedListener;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.loadingview.LiquidButton;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年6月30日
 */
public class LoadingViewActivity2 extends BaseActivity implements OnSwitchChangedListener{

	private LiquidButton liquidButton;
	private PeachSwitchButton psb_visibility, psb_animation;
	private TextView tv_progress;

	private float progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loadingview1);

		setContentUnderStatus(findViewById(R.id.ll_loadingview1_content));

		liquidButton = (LiquidButton) findViewById(R.id.liquid_button);
		psb_visibility = (PeachSwitchButton) findViewById(R.id.psb_visibility);
		psb_animation = (PeachSwitchButton) findViewById(R.id.psb_animation);
		tv_progress = (TextView) findViewById(R.id.tv_progress);
		
		// 事件
		liquidButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				liquidButton.startPour();
			}
		});
		liquidButton.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                ToastView.showWhiteContentToast(LoadingViewActivity2.this,
                		R.drawable.ic_toast_flag_ok, "Finish");
            }

            @Override
            public void onProgressUpdate(float progress) {
                tv_progress.setText(String.format("%.2f", progress * 100) + "%");
            }
        });
		psb_visibility.setOnChangeListener(this);
		psb_animation.setOnChangeListener(this);
		
		// 初始化
		progress = 0;
		liquidButton.finishPour();
		// 开始加载动画
		liquidButton.startPour();
		// 默认自动播放
		liquidButton.setAutoPlay(true);
		// 默认加载完成后显示
		liquidButton.setFillAfter(true);
	}

	public void changeProgress(View view) {
		progress += 0.1f;
		liquidButton.changeProgress(progress);
	}

	@Override
	public void onSwitchChange(PeachSwitchButton switchView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch(switchView.getId()){
		case R.id.psb_visibility:
			liquidButton.setFillAfter(isChecked);
			break;
		case R.id.psb_animation:
			liquidButton.setAutoPlay(isChecked);
			break;
		}
	}

}