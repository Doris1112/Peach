package com.doris.peach;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.BitmapUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年7月24日
 */
public class CopyChatPageActivity extends BaseActivity {
	
	private Button b_send;
	private EditText et_sendMassge;
	private ListView lv_chat;
	
	private List<Chat> chats = new ArrayList<Chat>();
	private ChatAdapter adapter = new ChatAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_copychatpage);
		
		b_send = (Button) findViewById(R.id.b_send);
		et_sendMassge = (EditText) findViewById(R.id.et_sendMassge);
		lv_chat = (ListView) findViewById(R.id.lv_chat);
		
		lv_chat.setAdapter(adapter);
		b_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String msg = et_sendMassge.getText().toString();
				if(!TextUtils.isEmpty(msg)){
					chats.add(new Chat(1, msg));
					chats.add(new Chat(0, "[系统提示：用户正忙！]"));
					adapter.notifyDataSetChanged();
					lv_chat.setSelection(adapter.getCount() - 1);
					et_sendMassge.setText("");
				}
			}
		});
	}
	
	private class ChatAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return chats.size();
		}

		@Override
		public Chat getItem(int position) {
			// TODO Auto-generated method stub
			return chats.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			Chat chat = chats.get(position);
			
			convertView = View.inflate(CopyChatPageActivity.this, 
					chat.layout == 0 ? R.layout.item_chatform : R.layout.item_chatto, null);
			TextView tv = (TextView) convertView.findViewById(R.id.tv_notepads_item);
			tv.setText(chat.massge);
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv_head);
			String fileName = chat.layout == 0 ? "image/ic_picture08.jpg" : "image/ic_picture07.jpg";
			iv.setImageBitmap(BitmapUtil.toRoundBitmap(CopyChatPageActivity.this, fileName));
			
			return convertView;
		}
	}
	
	private class Chat{
		int layout;
		String massge;
		
		public Chat(int layout, String massge){
			this.layout = layout;
			this.massge = massge;
		}
	}
}
