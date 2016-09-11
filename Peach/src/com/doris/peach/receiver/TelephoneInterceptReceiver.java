package com.doris.peach.receiver;

import java.lang.reflect.Method;
import java.util.List;

import com.android.internal.telephony.ITelephony;
import com.doris.peach.TelephoneInterceptActivity;
import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;
import com.doris.peachlibrary.util.Log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

/**
 * 
 * @author Doris
 *
 * 2016年5月4日
 */
public class TelephoneInterceptReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		DBUtil dbUtil = new DBUtil(context);
		List<String[]> telephones = dbUtil.getAllInfo(
				BaseData.TB_TELEPHONE_INTERCEPT);
		
		TelephonyManager manager = (TelephonyManager) 
				context.getSystemService(Context.TELEPHONY_SERVICE);
		int state = manager.getCallState();
		String number=intent.getStringExtra("incoming_number");
		switch (state) {
		case TelephonyManager.CALL_STATE_RINGING:
			//响铃
			for (String[] telephone : telephones) {
				if (telephone[1].equals(number)) {
					Class<TelephonyManager> c = TelephonyManager.class;
					try {
						Method method = c.getDeclaredMethod("getITelephony", null);
						method.setAccessible(true);// 公开方法
						ITelephony miTelephony = (ITelephony) 
								method.invoke(manager, null);
						miTelephony.endCall();// 挂断
						dbUtil.update(BaseData.TB_TELEPHONE_INTERCEPT,
								new String[] { BaseData.TELEPHONE_INTERCEPT_COUNT },
								new String[] { Integer.parseInt(telephone[3]) + 1 + "" },
								new String[] { BaseData.TELEPHONE_ID }, 
								new String[] { telephone[0] });
						((TelephoneInterceptActivity)context).adapterNotifyDataSetChanged();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.getInstance().writeLog("拦截电话出错：", e);
					}
				}
			}
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			//接听
			break;
		case TelephonyManager.CALL_STATE_IDLE:
			//挂断
			break;
		}
	}

}
