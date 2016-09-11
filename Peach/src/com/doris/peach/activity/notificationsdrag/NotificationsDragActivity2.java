package com.doris.peach.activity.notificationsdrag;

import java.util.Random;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.notificationsdrag.DraggableFlagView;
import com.doris.peachlibrary.view.notificationsdrag.DraggableFlagView.OnDraggableFlagViewListener;

import android.os.Bundle;
import android.view.View;

/**
 * 
 * @author Doris
 *
 * 2016年8月2日
 */
public class NotificationsDragActivity2 extends BaseActivity {
	
	private DraggableFlagView draggableFlagView;
	private Random rd = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificationsdrag2);
		
		draggableFlagView = (DraggableFlagView) findViewById(R.id.dfv_notificationsbrag);
		draggableFlagView.setOnDraggableFlagViewListener(new OnDraggableFlagViewListener() {
			
			@Override
			public void onFlagDismiss(DraggableFlagView view) {
				// TODO Auto-generated method stub
				ToastView.showWhiteContentToast(NotificationsDragActivity2.this,
						R.drawable.ic_toast_flag_verbose,
						"消息提示移除成功！");
			}
		});
		int count = rd.nextInt(150);
		draggableFlagView.setText(count + 1 + "");
	}
	
	public void showNotificationsDrag(View view){
		int count = rd.nextInt(150);
		draggableFlagView.setText(count + 1 + "");
	}
}
