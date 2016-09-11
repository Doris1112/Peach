package com.doris.peach.activity;

import com.doris.peach.activity.dialog.DialogSpinnerActivity;
import com.doris.peach.activity.dialog.ImitationWeChatActivity;
import com.doris.peach.activity.dialog.ImitationWeChatActivity2;
import com.doris.peach.activity.dialog.SweetAlertActivity;
import com.doris.peach.activity.dialog.UseActivityActivity;
import com.doris.peach.activity.dialog.UseDialogActivity;
import com.doris.peach.activity.dialog.UsePopupwindowActivity;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author Doris
 *
 * 2016年4月22日
 */
public class DialogIntentActivity extends BaseIntentActivity{

	public static void goActivity(int flag, Context packageContext) {
		Intent intent = new Intent();
		switch (flag) {
		case 1:
			goActivity(intent, packageContext, UseDialogActivity.class);
			break;
		case 2:
			goActivity(intent, packageContext, UsePopupwindowActivity.class);
			break;
		case 3:
			goActivity(intent, packageContext, UseActivityActivity.class);
			break;
		case 4:
			goActivity(intent, packageContext, ImitationWeChatActivity.class);
			break;
		case 5:
			goActivity(intent, packageContext, ImitationWeChatActivity2.class);
			break;
		case 6:
			goActivity(intent, packageContext, DialogSpinnerActivity.class);
			break;
		case 7:
			goActivity(intent, packageContext, SweetAlertActivity.class);
		}
	}
}
