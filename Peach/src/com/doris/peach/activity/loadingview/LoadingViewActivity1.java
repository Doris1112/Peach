package com.doris.peach.activity.loadingview;

import java.util.Timer;
import java.util.TimerTask;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.loadingview.LVBattery;
import com.doris.peachlibrary.view.loadingview.LVBlock;
import com.doris.peachlibrary.view.loadingview.LVChromeLogo;
import com.doris.peachlibrary.view.loadingview.LVCircular;
import com.doris.peachlibrary.view.loadingview.LVCircularCD;
import com.doris.peachlibrary.view.loadingview.LVCircularJump;
import com.doris.peachlibrary.view.loadingview.LVCircularRing;
import com.doris.peachlibrary.view.loadingview.LVCircularSmile;
import com.doris.peachlibrary.view.loadingview.LVCircularZoom;
import com.doris.peachlibrary.view.loadingview.LVEatBeans;
import com.doris.peachlibrary.view.loadingview.LVFinePoiStar;
import com.doris.peachlibrary.view.loadingview.LVFunnyBar;
import com.doris.peachlibrary.view.loadingview.LVGears;
import com.doris.peachlibrary.view.loadingview.LVGearsTwo;
import com.doris.peachlibrary.view.loadingview.LVGhost;
import com.doris.peachlibrary.view.loadingview.LVLineWithText;
import com.doris.peachlibrary.view.loadingview.LVNews;
import com.doris.peachlibrary.view.loadingview.LVPlayBall;
import com.doris.peachlibrary.view.loadingview.LVWifi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * 
 * @author Doris
 *
 *         2016年6月30日
 */
public class LoadingViewActivity1 extends BaseActivity {

	private LVPlayBall mLVPlayBall;
	private LVCircularRing mLVCircularRing;
	private LVCircular mLVCircular;
	private LVCircularJump mLVCircularJump;
	private LVCircularZoom mLVCircularZoom;
	private LVLineWithText mLVLineWithText;
	private LVEatBeans mLVEatBeans;
	private LVCircularCD mLVCircularCD;
	private LVCircularSmile mLVCircularSmile;
	private LVGears mLVGears;
	private LVGearsTwo mLVGearsTwo;
	private LVFinePoiStar mLVFinePoiStar;
	private LVChromeLogo mLVChromeLogo;
	private LVBattery mLVBattery;
	private LVWifi mLVWifi;
	private LVNews mLVNews;
	private LVBlock mLVBlock;
	private LVGhost mLVGhost;
	private LVFunnyBar mLVFunnyBar;
	
	private int mValueLVLineWithText = 0;
	private int mValueLVNews = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loadingview);
		mLVPlayBall = (LVPlayBall) findViewById(R.id.lv_playball);
		mLVCircularRing = (LVCircularRing) findViewById(R.id.lv_circularring);
		mLVCircular = (LVCircular) findViewById(R.id.lv_circular);
		mLVCircularJump = (LVCircularJump) findViewById(R.id.lv_circularJump);
		mLVCircularZoom = (LVCircularZoom) findViewById(R.id.lv_circularZoom);
		mLVLineWithText = (LVLineWithText) findViewById(R.id.lv_linetext);
		mLVEatBeans = (LVEatBeans) findViewById(R.id.lv_eatBeans);
		mLVCircularCD = (LVCircularCD) findViewById(R.id.lv_circularCD);
		mLVCircularSmile = (LVCircularSmile) findViewById(R.id.lv_circularSmile);
		mLVGears = (LVGears) findViewById(R.id.lv_gears);
		mLVGearsTwo = (LVGearsTwo) findViewById(R.id.lv_gears_two);
		mLVFinePoiStar = (LVFinePoiStar) findViewById(R.id.lv_finePoiStar);
		mLVChromeLogo = (LVChromeLogo) findViewById(R.id.lv_chromeLogo);
		mLVBattery = (LVBattery) findViewById(R.id.lv_battery);
		mLVBattery.setBatteryOrientation(LVBattery.BatteryOrientation.VERTICAL);
		// mLVBattery.setBatteryOrientation(LVBattery.BatteryOrientation.HORIZONTAL);
		mLVBattery.setValue(50);
		mLVBattery.setShowNum(false);
		mLVWifi = (LVWifi) findViewById(R.id.lv_wifi);
		mLVNews = (LVNews) findViewById(R.id.lv_news);
		mLVBlock = (LVBlock) findViewById(R.id.lv_block);
		mLVGhost = (LVGhost) findViewById(R.id.lv_ghost);
		mLVFunnyBar = (LVFunnyBar) findViewById(R.id.lv_funnybar);
	}

	public void startAnim(View v) {
		stopAll();
		if (v instanceof LVCircular) {
			((LVCircular) v).startAnim();
		} else if (v instanceof LVCircularCD) {
			((LVCircularCD) v).startAnim();
		} else if (v instanceof LVCircularSmile) {
			((LVCircularSmile) v).startAnim();
		} else if (v instanceof LVCircularRing) {
			((LVCircularRing) v).startAnim();
		} else if (v instanceof LVCircularZoom) {
			((LVCircularZoom) v).startAnim();
		} else if (v instanceof LVCircularJump) {
			((LVCircularJump) v).startAnim();
		} else if (v instanceof LVEatBeans) {
			((LVEatBeans) v).startAnim();
		} else if (v instanceof LVPlayBall) {
			((LVPlayBall) v).startAnim();
		} else if (v instanceof LVLineWithText) {
			startLVLineWithTextAnim();
		} else if (v instanceof LVGears) {
			((LVGears) v).startAnim();
		} else if (v instanceof LVGearsTwo) {
			((LVGearsTwo) v).startAnim();
		} else if (v instanceof LVFinePoiStar) {
			((LVFinePoiStar) v).setDrawPath(true);
			((LVFinePoiStar) v).startAnim();
		} else if (v instanceof LVChromeLogo) {
			((LVChromeLogo) v).startAnim();
		} else if (v instanceof LVBattery) {
			((LVBattery) v).startAnim();
		} else if (v instanceof LVWifi) {
			((LVWifi) v).startAnim();
		} else if (v instanceof LVNews) {
			startLVNewsAnim();
		} else if (v instanceof LVBlock) {
			((LVBlock) v).startAnim();
		}else if(v instanceof LVGhost){
			((LVGhost) v).startAnim();
		}else  if(v instanceof LVFunnyBar){
			((LVFunnyBar) v).startAnim();
		}
	}

	public void startAnimAll(View v) {
		mLVCircular.startAnim();
		mLVCircularRing.startAnim();
		mLVPlayBall.startAnim();
		mLVCircularJump.startAnim();
		mLVCircularZoom.startAnim();
		startLVLineWithTextAnim();
		mLVEatBeans.startAnim();
		mLVCircularCD.startAnim();
		mLVCircularSmile.startAnim();
		mLVGears.startAnim();
		mLVGearsTwo.startAnim();
		mLVFinePoiStar.setDrawPath(true);
		mLVFinePoiStar.startAnim();
		mLVChromeLogo.startAnim();
		mLVBattery.startAnim();
		mLVWifi.startAnim();
		startLVNewsAnim();
		mLVBlock.startAnim();
		mLVGhost.startAnim();
		mLVFunnyBar.startAnim();
	}

	public void stopAnim(View v) {
		stopAll();
	}

	private void stopAll() {
		mLVCircular.stopAnim();
		mLVPlayBall.stopAnim();
		mLVCircularJump.stopAnim();
		mLVCircularZoom.stopAnim();
		mLVCircularRing.stopAnim();
		mLVEatBeans.stopAnim();
		stopLVLineWithTextAnim();
		mLVCircularCD.stopAnim();
		mLVCircularSmile.stopAnim();
		mLVGears.stopAnim();
		mLVGearsTwo.stopAnim();
		mLVFinePoiStar.stopAnim();
		mLVChromeLogo.stopAnim();
		mLVBattery.stopAnim();
		mLVWifi.stopAnim();
		stopLVNewsAnim();
		mLVBlock.stopAnim();
		mLVGhost.stopAnim();
		mLVFunnyBar.stopAnim();
	}

	private Timer mTimerLVLineWithText = new Timer();// 定时器
	private Timer mTimerLVNews = new Timer();// 定时器

	private void startLVLineWithTextAnim() {
		mValueLVLineWithText = 0;
		if (mTimerLVLineWithText != null) {
			mTimerLVLineWithText.cancel();// 退出之前的mTimer
		}
		mTimerLVLineWithText = new Timer();
		timerTaskLVLineWithText();
	}

	private void stopLVLineWithTextAnim() {
		if (mTimerLVLineWithText != null) {
			mTimerLVLineWithText.cancel();// 退出之前的mTimer
			mLVNews.setValue(mValueLVNews);
		}
	}

	private void startLVNewsAnim() {
		mValueLVNews = 0;
		if (mTimerLVNews != null) {

			mTimerLVNews.cancel();
		}
		mTimerLVNews = new Timer();
		timerTaskLVNews();
	}

	private void stopLVNewsAnim() {
		mLVNews.stopAnim();
		if (mTimerLVNews != null) {
			mTimerLVNews.cancel();
			mLVLineWithText.setValue(mValueLVLineWithText);
		}
	}

	public void timerTaskLVNews() {
		mTimerLVNews.schedule(new TimerTask() {
			@Override
			public void run() {
				if (mValueLVNews < 100) {
					mValueLVNews++;
					Message msg = mHandle.obtainMessage(1);
					msg.arg1 = mValueLVNews;
					mHandle.sendMessage(msg);
				} else {
					mTimerLVNews.cancel();
				}
			}
		}, 0, 10);
	}

	public void timerTaskLVLineWithText() {
		mTimerLVLineWithText.schedule(new TimerTask() {
			@Override
			public void run() {
				if (mValueLVLineWithText < 100) {

					mValueLVLineWithText++;
					Message msg = mHandle.obtainMessage(2);
					msg.arg1 = mValueLVLineWithText;
					mHandle.sendMessage(msg);

				} else {
					mTimerLVLineWithText.cancel();
				}
			}
		}, 0, 50);
	}

	private Handler mHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 2)
				mLVLineWithText.setValue(msg.arg1);
			else if (msg.what == 1) {
				mLVNews.setValue(msg.arg1);
			}
		}
	};
}