package com.doris.peach.activity.notifications;

import com.doris.peach.R;
import com.doris.peachlibrary.util.DeviceUtil;
import com.doris.peachlibrary.view.ToastView;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.view.View;
import android.widget.RemoteViews;

/**
 * 
 * @author Doris
 *
 *         2016年7月16日
 */
public class AutoNotificationsActivity extends BaseActivity {

	private int notifyId = 101;
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** 上一首 按钮点击 ID */
	public final static int BUTTON_PREV_ID = 1;
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2;
	/** 下一首 按钮点击 ID */
	public final static int BUTTON_NEXT_ID = 3;
	/** 是否在播放*/
	public boolean isPlay = false;
	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	/** 通知栏按钮广播 */
	public ButtonBroadcastReceiver bReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifications_auto);
	}

	public void doNotifications(View view) {
		switch (view.getId()) {
		case R.id.b_showAutoNotifications:
			shwoNotify();
			break;
		case R.id.b_showNotificationsAndButton:
			showButtonNotify();
			break;
		}
	}

	/**
	 * 自定义通知栏消息
	 */
	private void shwoNotify() {
		// 先设定RemoteViews
		RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.layout_notifications_auto);
		// 设置对应IMAGEVIEW的ID的资源图片
		view_custom.setImageViewResource(R.id.iv_icon, R.drawable.ic_launcher);
		view_custom.setTextViewText(R.id.tv_title, "Peach");
		view_custom.setTextViewText(R.id.tv_content, "自定义通知栏消息……");
		NotificationCompat.Builder mBuilder = new Builder(this);
		mBuilder.setContent(view_custom).setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setTicker("有新消息").setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setOngoing(false)// 不是正在进行的 true为正在进行 效果和.flag一样
				.setSmallIcon(R.drawable.ic_launcher);
		Notification notify = mBuilder.build();
		notify.contentView = view_custom;
		mNotificationManager.notify(notifyId, notify);
	}
	
	/**
	 * 带按钮的通知栏
	 */
	private void showButtonNotify(){
		NotificationCompat.Builder mBuilder = new Builder(this);
		RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notifications_button);
		mRemoteViews.setImageViewResource(R.id.iv_song_icon, R.drawable.ic_launcher);
		//API3.0 以上的时候显示按钮，否则消失
		mRemoteViews.setTextViewText(R.id.tv_song_singer, "周杰伦");
		mRemoteViews.setTextViewText(R.id.tv_song_name, "七里香");
		//如果版本号低于（3.0），那么不显示按钮
		if(DeviceUtil.getSystemVersion() <= 9){
			mRemoteViews.setViewVisibility(R.id.ll_button, View.GONE);
		}else{
			mRemoteViews.setViewVisibility(R.id.ll_button, View.VISIBLE);
			//
			if(isPlay){
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_pause);
			}else{
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_play);
			}
		}
		//点击的事件处理
		Intent buttonIntent = new Intent(ACTION_BUTTON);
		/* 上一首按钮 */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
		//这里加了广播，所及INTENT的必须用getBroadcast方法
		PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_prev, intent_prev);
		/* 播放/暂停  按钮 */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
		/* 下一首 按钮  */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
		PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);
		
		mBuilder.setContent(mRemoteViews)
				.setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setTicker("正在播放")
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setOngoing(true)
				.setSmallIcon(R.drawable.ic_launcher);
		Notification notify = mBuilder.build();
		notify.flags = Notification.FLAG_ONGOING_EVENT;
		mNotificationManager.notify(200, notify);
	}
	
	/**
	 * 广播监听按钮点击时间 
	 * @author Doris
	 *
	 * 2016年7月16日
	 */
	private class ButtonBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(ACTION_BUTTON.equals(intent.getAction())){
				//通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
				switch (buttonId) {
				case BUTTON_PREV_ID:
					ToastView.showWhiteContentToast(AutoNotificationsActivity.this,
							R.drawable.ic_toast_flag_verbose, 
							"上一首");
					break;
				case BUTTON_PALY_ID:
					String play_status = "";
					isPlay = !isPlay;
					if(isPlay){
						play_status = "开始播放";
					}else{
						play_status = "已暂停";
					}
					showButtonNotify();
					ToastView.showWhiteContentToast(AutoNotificationsActivity.this,
							R.drawable.ic_toast_flag_verbose, 
							play_status);
					break;
				case BUTTON_NEXT_ID:
					ToastView.showWhiteContentToast(AutoNotificationsActivity.this,
							R.drawable.ic_toast_flag_verbose, 
							"下一首");
					break;
				}
			}
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		bReceiver = new ButtonBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		registerReceiver(bReceiver, intentFilter);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(bReceiver != null)
			unregisterReceiver(bReceiver);
	}
}