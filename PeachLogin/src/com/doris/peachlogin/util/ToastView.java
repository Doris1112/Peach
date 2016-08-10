package com.doris.peachlogin.util;

import com.doris.peachlogin.R;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Doris
 *
 *         2016年4月14日
 */
public class ToastView {
	
	/**
	 * 
	 * @param context
	 * @param bg
	 *            背景
	 * @param flag
	 *            标识
	 * @param content
	 *            内容
	 */
	public static void showToast(Activity context, int bg, int flag, String content) {
		doToast(context, bg, flag, content, Toast.LENGTH_SHORT, Gravity.CENTER);
	}
	
	/**
	 * Toast处理
	 * @param context
	 * @param bg
	 * @param flag
	 * @param content
	 * @param duration
	 * @param gravity
	 */
	private static void doToast(Activity context, int bg, int flag, String content, int duration, int gravity){
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast, null);
		LinearLayout ll_toast = (LinearLayout) layout.findViewById(R.id.ll_toast);
		ll_toast.setBackgroundResource(bg);
		ImageView image = (ImageView) layout.findViewById(R.id.iv_toast_flag);
		image.setImageResource(flag);
		TextView tv_content = (TextView) layout.findViewById(R.id.tv_toast_content);
		tv_content.setText(content);
		Toast toast = new Toast(context);
		toast.setView(layout);
		toast.setGravity(gravity == 0 ? Gravity.BOTTOM : gravity, 0, 0);
		toast.setDuration(duration);
		toast.show();
	}
	
}
