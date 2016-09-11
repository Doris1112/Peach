package com.doris.peach.activity.notifications;

import com.doris.peach.R;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

/**
 * 
 * @author Doris
 *
 * 2016年7月16日
 */
public class PropressNotificationsActivity extends BaseActivity{
	
	/** Notification的ID */
	private int notifyId = 102;
	/** Notification的进度条数值 */
	private int progress = 0;
	/** true:为不确定样式的   false:确定样式*/
	public Boolean indeterminate = false;
	/** 下载线程是否暂停 */
	public boolean isPause = false;
	/** 通知栏内是否是自定义的 */
	public boolean isCustom = false;
	
	private NotificationCompat.Builder mBuilder;
	private DownloadThread downloadThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifications_propress);
		
		initNotify();
	}
	
	public void doNotifications(View view){
		switch (view.getId()) {
		case R.id.b_showProgress:
			downloadThread = null;
			isCustom = false;
			indeterminate = false;
			initNotify();
			showProgressNotify();
			break;
		case R.id.b_showWaitProgress:
			downloadThread = null;
			isCustom = false;
			indeterminate = true;
			initNotify();
			showProgressNotify();
			break;
		case R.id.b_showAutoProgress:
			downloadThread = null;
			isCustom = true;
			indeterminate = false;
			showCustomProgressNotify("等待下载..");
			break;
		case R.id.b_downloadStart:
			startDownloadNotify();
			break;
		case R.id.b_downloadPause:
			pauseDownloadNotify();
			break;
		case R.id.b_downloadCancel:
			stopDownloadNotify();
			break;
		}
	}
	
	/**
	 * 显示带进度条通知栏
	 */
	public void showProgressNotify() {
		mBuilder.setContentTitle("等待下载")
				.setContentText("进度:")
				.setTicker("开始下载");// 通知首次出现在通知栏，带上升动画效果的
		if(indeterminate){
			//不确定进度的
			mBuilder.setProgress(0, 0, true);
		}else{
			//确定进度的
			mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条  设置为true就是不确定的那种进度条效果
		}
		mNotificationManager.notify(notifyId, mBuilder.build());
	}
	
	/**
	 * 显示自定义的带进度条通知栏
	 * @param status
	 */
	private void showCustomProgressNotify(String status) {
		RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notifications_propress);
		mRemoteViews.setImageViewResource(R.id.iv_progress_icon, R.drawable.ic_launcher);
		mRemoteViews.setTextViewText(R.id.tv_progress_title, "今日头条");
		mRemoteViews.setTextViewText(R.id.tv_progress_status, status);
		if(progress >= 100 || downloadThread == null){
			mRemoteViews.setProgressBar(R.id.pb_progressbar, 0, 0, false);
			mRemoteViews.setViewVisibility(R.id.pb_progressbar, View.GONE);
		}else{
			mRemoteViews.setProgressBar(R.id.pb_progressbar, 100, progress, false);
			mRemoteViews.setViewVisibility(R.id.pb_progressbar, View.VISIBLE);
		}
		mBuilder.setContent(mRemoteViews)
				.setContentIntent(getDefalutIntent(0))
				.setTicker("头条更新");
		Notification nitify = mBuilder.build();
		nitify.contentView = mRemoteViews;
		mNotificationManager.notify(notifyId, nitify);
	}
	
	/**
	 * 开始下载
	 */
	private void startDownloadNotify() {
		isPause = false;
		if (downloadThread != null && downloadThread.isAlive()) {
//			downloadThread.start();
		}else{
			downloadThread = new DownloadThread();
			downloadThread.start();
		}
	}

	/**
	 * 暂停下载
	 */
	public void pauseDownloadNotify() {
		isPause = true;
		if(!isCustom){
			mBuilder.setContentTitle("已暂停");
			setNotify(progress);
		}else{
			showCustomProgressNotify("已暂停");
		}
	}

	/**
	 * 取消下载
	 */
	public void stopDownloadNotify() {
		if (downloadThread != null) {
			downloadThread.interrupt();
		}
		downloadThread = null;
		if(!isCustom){
			mBuilder.setContentTitle("下载已取消").setProgress(0, 0, false);
			mNotificationManager.notify(notifyId, mBuilder.build());
		}else{
			showCustomProgressNotify("下载已取消");
		}
	}

	/**
	 * 设置下载进度
	 * @param progress
	 */
	public void setNotify(int progress) {
		mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/**
	 * 下载线程
	 */
	class DownloadThread extends Thread {

		@Override
		public void run() {
			int now_progress = 0;
			// Do the "lengthy" operation 20 times
			while (now_progress <= 100) {
				// Sets the progress indicator to a max value, the
				// current completion percentage, and "determinate"
				if(downloadThread == null){
					break;
				}
				if (!isPause) {
					progress = now_progress;
					if(!isCustom){
						mBuilder.setContentTitle("下载中");
						if(!indeterminate){
							setNotify(progress);
						}
					}else{
						showCustomProgressNotify("下载中");
					}
					now_progress += 10;
				}
				try {
					// Sleep for 1 seconds
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			// When the loop is finished, updates the notification
			if(downloadThread != null){
				if(!isCustom){
					mBuilder.setContentText("下载完成")
					// Removes the progress bar
					.setProgress(0, 0, false);
					mNotificationManager.notify(notifyId, mBuilder.build());
				}else{
					showCustomProgressNotify("下载完成");
				}
			}
		}
	}
	
	/**
	 * 初始化通知栏
	 */
	private void initNotify() {
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setContentIntent(getDefalutIntent(0))
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setOngoing(false)
				.setDefaults(Notification.DEFAULT_VIBRATE)
				.setSmallIcon(R.drawable.ic_launcher);
	}
}
