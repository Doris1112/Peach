package com.doris.peach.activity.notifications;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peachlibrary.adapter.ListButtonAdapter;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * 
 * @author Doris
 *
 * 2016年7月16日
 */
public class NotificationsActivity extends BaseActivity {
	
	private NotificationCompat.Builder mBuilder;
	private int notifyId = 100;
	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);
		
		setTitle();
		initList();
		initNotify();
		
		ListView lv_listButton = (ListView) findViewById(R.id.lv_listButton);
		lv_listButton.setAdapter(new ListButtonAdapter(list, listener, context));
	}
	
	private void initList(){
		context = this;
		
		list.add(getResources().getString(R.string.showNotifications));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showNotify();
			}
		});
		list.add(getResources().getString(R.string.showResidentNotifications));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showCzNotify();
			}
		});
		list.add(getResources().getString(R.string.onClickNotificationsGoActivity));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showIntentActivityNotify();
			}
		});
		list.add(getResources().getString(R.string.showAutoNotifications));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, AutoNotificationsActivity.class));
			}
		});
		list.add(getResources().getString(R.string.showPropressBarNotifications));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, PropressNotificationsActivity.class));
			}
		});
		list.add(getResources().getString(R.string.clearAllNotifications));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clearAllNotify();
			}
		});
	}
	
	/**
	 * 初始化通知栏
	 */
	private void initNotify(){
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle("测试标题")
				.setContentText("测试内容")
				.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
				.setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
				.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
				.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				.setSmallIcon(R.drawable.ic_launcher);
	}
	
	/**
	 * 显示通知栏
	 */
	private void showNotify(){
		mBuilder.setContentTitle("测试标题")
				.setContentText("测试内容")
				.setTicker("测试通知来啦");
		mNotificationManager.notify(notifyId, mBuilder.build());
	}
	
	/**
	 * 显示常驻通知栏
	 */
	private void showCzNotify(){
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
		PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, getIntent(), 0);  
		mBuilder.setSmallIcon(R.drawable.ic_launcher)
				.setTicker("常驻通知来了")
				.setContentTitle("常驻测试")
				.setContentText("使用cancel()方法才可以把我去掉哦")
				.setContentIntent(pendingIntent);
		Notification mNotification = mBuilder.build();
		//设置通知  消息  图标  
		mNotification.icon = R.drawable.ic_launcher;
		//FLAG_ONGOING_EVENT 在顶部常驻，
		// 可以调用下面的清除方法去除  FLAG_AUTO_CANCEL
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		//设置显示通知时的默认的发声、震动、Light效果  
		mNotification.defaults = Notification.DEFAULT_VIBRATE;
		//设置发出消息的内容
		mNotification.tickerText = "通知来了";
		//设置发出通知的时间  
		mNotification.when=System.currentTimeMillis(); 
		mNotificationManager.notify(notifyId, mNotification);
	}
	
	/**
	 * 显示通知栏点击跳转到指定Activity
	 */
	private void showIntentActivityNotify(){
		mBuilder.setAutoCancel(true)//点击后让通知将消失  
				.setContentTitle("测试标题")
				.setContentText("点击跳转")
				.setTicker("点我");
		//点击的意图ACTION是跳转到Intent
		Intent resultIntent = new Intent(this, TestActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}
	
}
