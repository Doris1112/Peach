package com.doris.peach.activity.notificationsdrag;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.notificationsdrag.NotificationsDragView;
import com.doris.peachlibrary.view.notificationsdrag.NotificationsDragView.OnMessageCancelListener;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年8月2日
 */
public class NotificationsDragActivity1 extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificationsdrag1);
		
		NotificationsDragView ndv = (NotificationsDragView) findViewById(R.id.ndv);
		ndv.setImage(R.drawable.skin_tips_new);
		ndv.setOnMessageCancelListener(new OnMessageCancelListener() {
			
			@Override
			public void onMessageCancal() {
				// TODO Auto-generated method stub
				ToastView.showWhiteContentToast(NotificationsDragActivity1.this,
						R.drawable.ic_toast_flag_verbose,
						"消息提示移除成功！");
			}
		});
		
		/*NotificationsDragView ndv1 = (NotificationsDragView) findViewById(R.id.ndv1);
        ndv1.setImage(R.drawable.skin_tips_new);
        
        NotificationsDragView ndv2 = (NotificationsDragView) findViewById(R.id.ndv2);
        bv2.createDrawable(R.drawable.skin_tips_newmessage, "1");
        
        NotificationsDragView ndv3 = (NotificationsDragView) findViewById(R.id.ndv3);
        bv3.createDrawable(R.drawable.skin_tips_newmessage, "34");*/
	}
}
