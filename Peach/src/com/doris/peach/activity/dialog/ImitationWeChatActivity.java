package com.doris.peach.activity.dialog;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.dialog.util.ActionItem;
import com.doris.peachlibrary.view.dialog.WeChatPopupWindow;
import com.doris.peachlibrary.view.dialog.WeChatPopupWindow.OnItemOnClickListener;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

/**
 * 
 * @author Doris
 *
 * 2016年4月28日
 */
public class ImitationWeChatActivity extends BaseActivity {
	
	private WeChatPopupWindow wechat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imitation_wechat);
		
		setContentUnderStatus(findViewById(R.id.ll_imitationwechat_content));
		
		wechat = new WeChatPopupWindow(this, 
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		wechat.addAction(new ActionItem(this, "发起聊天",
				R.drawable.ic_wechat_title_btn_compose_normal));
		wechat.addAction(new ActionItem(this, "听筒模式",
				R.drawable.ic_wechat_title_btn_receiver_normal));
		wechat.addAction(new ActionItem(this, "登录网页",
				R.drawable.ic_wechat_title_btn_keyboard_normal));
		wechat.addAction(new ActionItem(this, "扫一扫", 
				R.drawable.ic_wechat_title_btn_qrcode_normal));
		
		wechat.setItemOnClickListener(new OnItemOnClickListener() {
			
			@Override
			public void onItemClick(ActionItem item, int position) {
				// TODO Auto-generated method stub
				ToastView.showToast(ImitationWeChatActivity.this,
						R.drawable.ic_listselector,
						R.drawable.ic_toast_flag_ok,
						item.mTitle + "", 
						Toast.LENGTH_SHORT);
			}
		});
	}
	
	public void showPopupwindow(View view){
		wechat.show(view);
	}
}
