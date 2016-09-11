package com.doris.peach.activity;

import com.doris.peach.AIDLActivity;
import com.doris.peach.BaiduMapActivity;
import com.doris.peach.ChineseSortActivity;
import com.doris.peach.CircularProgressButtonActivity;
import com.doris.peach.CopyChatPageActivity;
import com.doris.peach.EmojiconActivity;
import com.doris.peach.FullTextActivity;
import com.doris.peach.LauncherActivity;
import com.doris.peach.LoadNetworkImageActivity;
import com.doris.peach.MoveZoomActivity;
import com.doris.peach.PasswordInputActivity;
import com.doris.peach.PickerViewActivity;
import com.doris.peach.RippleTelActivity;
import com.doris.peach.SpinnerActivity;
import com.doris.peach.SwitchButtonActivity;
import com.doris.peach.TelephoneInterceptActivity;
import com.doris.peach.ThreeDESActivity;
import com.doris.peach.TimeLineActivity;
import com.doris.peach.ToastActivity;
import com.doris.peach.activity.animation.AnimationActivity;
import com.doris.peach.activity.bluetooth.BluetoothActivity;
import com.doris.peach.activity.calendar.CalendarActivity;
import com.doris.peach.activity.card.CardActivity;
import com.doris.peach.activity.dialog.DialogActivity;
import com.doris.peach.activity.discrollview.DiscrollViewActivity;
import com.doris.peach.activity.geomap.GaodeMapActivity;
import com.doris.peach.activity.gif.GIFActivity;
import com.doris.peach.activity.listview.ListViewActivity;
import com.doris.peach.activity.loadingview.LoadingViewActivity;
import com.doris.peach.activity.lock.LockActivity;
import com.doris.peach.activity.login.LoginActivity;
import com.doris.peach.activity.menu.AutoMenuActivity;
import com.doris.peach.activity.navigationpage.NavigationPageActivity;
import com.doris.peach.activity.note.NoteActivity;
import com.doris.peach.activity.notebook.NoteBookActivity;
import com.doris.peach.activity.notifications.NotificationsActivity;
import com.doris.peach.activity.notificationsdrag.NotificationsDragActivity;
import com.doris.peach.activity.paperfolding.PaperFoldingActivity;
import com.doris.peach.activity.pulltorefresh.PullToRefreshActivity;
import com.doris.peach.activity.reader.ReaderActivity;
import com.doris.peach.activity.scrawl.ScrawlActivity;
import com.doris.peach.activity.searchforrelated.SearchForRelatedActivity;
import com.doris.peach.activity.sensor.SensorActivity;
import com.doris.peach.activity.step.StepActivity;
import com.doris.peach.activity.tagcloud.TagCloudActivity;
import com.doris.peach.activity.textdrawable.TextDrawableActivity;
import com.doris.peach.activity.threedimensionaloverturn.ThreeDimensionalOverturnActivity;
import com.doris.peach.activity.turn.TurnActivity;
import com.doris.peach.activity.weather.SelectCityActivity;
import com.doris.peach.activity.weather.WeatherActivity;
import com.doris.peach.activity.win8.Win8ButtonActivity;
import com.doris.peach.data.BaseData;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.util.SharedPreferencesUtil;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author Doris
 *
 *         2016年4月12日
 */
public class ListIntentActivity extends BaseIntentActivity {

	public static void goActivity(Context packageContext) {
		Intent intent = new Intent();
		switch (Integer.parseInt(BaseData.item[0])) {
		case 1:
			// 3DES加密
			goActivity(intent, packageContext, ThreeDESActivity.class);
			break;
		case 2:
			// AIDL
			goActivity(intent, packageContext, AIDLActivity.class);
			break;
		case 3:
			// 标签云
			goActivity(intent, packageContext, TagCloudActivity.class);
			break;
		case 4:
			// 传感器
			goActivity(intent, packageContext, SensorActivity.class);
			break;
		case 5:
			// 电话拦截
			goActivity(intent, packageContext, TelephoneInterceptActivity.class);
			break;
		case 6:
			// 动画
			goActivity(intent, packageContext, AnimationActivity.class);
			break;
		case 7:
			// 对话框
			goActivity(intent, packageContext, DialogActivity.class);
			break;
		case 8:
			// 卡片效果
			 goActivity(intent, packageContext, CardActivity.class);
			break;
		case 9:
			// Emoji表情
			goActivity(intent, packageContext, EmojiconActivity.class);
			break;
		case 10:
			// GIF
			goActivity(intent, packageContext, GIFActivity.class);
			break;
		case 11:
			// 汉字排序
			goActivity(intent, packageContext, ChineseSortActivity.class);
			break;
		case 12:
			// 操作步骤提示
			goActivity(intent, packageContext, StepActivity.class);
			break;
		case 13:
			// ListView
			goActivity(intent, packageContext, ListViewActivity.class);
			break;
		case 14:
			// 搜索相关
			goActivity(intent, packageContext, SearchForRelatedActivity.class);
			break;
		case 15:
			// Spinner
			goActivity(intent, packageContext, SpinnerActivity.class);
			break;
		case 16:
			// 索引导航
			DialogUtil dialogUtil = new DialogUtil(packageContext);
			dialogUtil.showAlertDialog1("请参考列表效果！", null);
			break;
		case 17:
			// 天气预报
			String city = (String) SharedPreferencesUtil.getValue(packageContext, "weather_city", "");
			if ("".equals(city)) {
				goActivity(intent, packageContext, SelectCityActivity.class);
			} else {
				goActivity(intent, packageContext, WeatherActivity.class);
			}
			break;
		case 18:
			// Toast
			goActivity(intent, packageContext, ToastActivity.class);
			break;
		case 19:
			// win8按钮
			goActivity(intent, packageContext, Win8ButtonActivity.class);
			break;
		case 20:
			// 下拉刷新
			goActivity(intent, packageContext, PullToRefreshActivity.class);
			break;
		case 21:
			// 加载网络图片
			goActivity(intent, packageContext, LoadNetworkImageActivity.class);
			break;
		case 22:
			// 百度地图
			goActivity(intent, packageContext, BaiduMapActivity.class);
			break;
		case 23:
			// 高德地图
			goActivity(intent, packageContext, GaodeMapActivity.class);
			break;
		case 24:
			// 涂鸦
			goActivity(intent, packageContext, ScrawlActivity.class);
			break;
		case 25:
			// 图片移动缩放
			goActivity(intent, packageContext, MoveZoomActivity.class);
			break;
		case 26:
			// 翻页效果
			goActivity(intent, packageContext, TurnActivity.class);
			break;
		case 27:
			// LoadingView
			goActivity(intent, packageContext, LoadingViewActivity.class);
			break;
		case 28:
			// 蓝牙
			goActivity(intent, packageContext, BluetoothActivity.class);
			break;
		case 29:
			// 自定义开关按钮
			goActivity(intent, packageContext, SwitchButtonActivity.class);
			break;
		case 30:
			// 自定义滚动选择框
			goActivity(intent, packageContext, PickerViewActivity.class);
			break;
		case 31:
			// 拨号效果
			goActivity(intent, packageContext, RippleTelActivity.class);
			break;
		case 32:
			// 首字母图标
			goActivity(intent, packageContext, TextDrawableActivity.class);
			break;
		case 33:
			// 桌面
			goActivity(intent, packageContext, LauncherActivity.class);
			break;
		case 34:
			// 时间轴
			goActivity(intent, packageContext, TimeLineActivity.class);
			break;
		case 35:
			// 日历
			goActivity(intent, packageContext, CalendarActivity.class);
			break;
		case 36:
			// 通知栏
			goActivity(intent, packageContext, NotificationsActivity.class);
			break;
		case 37:
			// 记事本
			goActivity(intent, packageContext, NoteBookActivity.class);
			break;
		case 38:
			// 导航页
			goActivity(intent, packageContext, NavigationPageActivity.class);
			break;
		case 39:
			// 便签
			goActivity(intent, packageContext, NoteActivity.class);
			break;
		case 40:
			// 仿聊天页面
			goActivity(intent, packageContext, CopyChatPageActivity.class);
			break;
		case 41:
			// 自定义菜单
			goActivity(intent, packageContext, AutoMenuActivity.class);
			break;
		case 42:
			// 闹钟
			// goActivity(intent, packageContext, .class);
			break;
		case 43:
			// 3D立体翻转
			goActivity(intent, packageContext, ThreeDimensionalOverturnActivity.class);
			break;
		case 44:
			// DiscrollView
			goActivity(intent, packageContext, DiscrollViewActivity.class);
			break;
		case 45:
			// 进度按钮
			goActivity(intent, packageContext, CircularProgressButtonActivity.class);
			break;
		case 46:
			// 消息提醒可拖动
			goActivity(intent, packageContext, NotificationsDragActivity.class);
			break;
		case 47:
			// 自定义密码输入框
			goActivity(intent, packageContext, PasswordInputActivity.class);
			break;
		case 48:
			// 登陆页面
			goActivity(intent, packageContext, LoginActivity.class);
			break;
		case 49:
			// 3D折纸
			goActivity(intent, packageContext, PaperFoldingActivity.class);
			break;
		case 50:
			// 全文展开收起
			goActivity(intent, packageContext, FullTextActivity.class);
			break;
		case 51:
			// 密码锁
			goActivity(intent, packageContext, LockActivity.class);
			break;
		case 52:
			// 阅读器
			goActivity(intent, packageContext, ReaderActivity.class);
			break;
		}
	}
}
