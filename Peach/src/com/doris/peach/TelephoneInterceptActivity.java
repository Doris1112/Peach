package com.doris.peach;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;
import com.doris.peach.receiver.TelephoneInterceptReceiver;
import com.doris.peachlibrary.dialog.Interface.DialogButtonClickListener;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.view.ToastView;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 电话拦截
 * @author Doris
 *
 * 2016年5月4日
 */
public class TelephoneInterceptActivity extends BaseActivity {

	private EditText et_telephone_intercept;
	private ListView lv_telephone_intercept;
	
	private TelephoneAdapter adapter;
	
	private List<String[]> telephones;
	private DBUtil dbUtil;
	private DialogUtil dialogUtil;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private TelephoneInterceptReceiver receiver =
			new TelephoneInterceptReceiver();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telephone_intercept);
		
		setTitle();
		dbUtil = new DBUtil(this);
		telephones = dbUtil.getAllInfo(BaseData.TB_TELEPHONE_INTERCEPT);
		dialogUtil = new DialogUtil(this);
		
		et_telephone_intercept = (EditText) findViewById(
				R.id.et_telephone_intercept);
		lv_telephone_intercept = (ListView) findViewById(
				R.id.lv_telephone_intercept);
		adapter = new TelephoneAdapter();
		lv_telephone_intercept.setAdapter(adapter);
	}
	
	public void saveTelephone(View view){
		String telephone = et_telephone_intercept.getText().toString();
		if(telephone != null && telephone.length() <= 0){
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, 
					getResources().getString(R.string.pleaseEnterTheInterceptNumber));
		}else{
			String id = dbUtil.getColumn(BaseData.TB_TELEPHONE_INTERCEPT,
					BaseData.TELEPHONE_ID);
			String[] telephoneItem = new String[]{id, sdf.format(new Date()), telephone, "0"};
			dbUtil.insertData(BaseData.TB_TELEPHONE_INTERCEPT,
					new String[]{BaseData.TELEPHONE_ID, BaseData.TELEPHONE_DATE, 
							BaseData.TELEPHONE_INFO, BaseData.TELEPHONE_INTERCEPT_COUNT},
					telephoneItem);
			telephones.add(telephoneItem);
			adapter.notifyDataSetChanged();
			et_telephone_intercept.setText("");
		}
	}
	
	class TelephoneAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return telephones.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return telephones.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			Tag tag = null;
			if(convertView == null){
				convertView = LayoutInflater.from(
						TelephoneInterceptActivity.this).inflate(
								R.layout.item_telephone_intercept, null);
				tag = new Tag();
				tag.date = (TextView) convertView.findViewById(
						R.id.tv_item_telephone_date);
				tag.telephone = (TextView) convertView.findViewById(
						R.id.tv_item_telephone);
				tag.count = (TextView) convertView.findViewById(
						R.id.tv_item_telephone_count);
				tag.clear = (ImageButton) convertView.findViewById(
						R.id.ib_item_telephone_clear);
				convertView.setTag(tag);
			}
			tag = (Tag) convertView.getTag();
			
			tag.date.setText(telephones.get(position)[1]);
			tag.telephone.setText(telephones.get(position)[2]);
			tag.count.setText("拦截次数：" + telephones.get(position)[3]);
			tag.clear.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialogUtil.showAlertDialog("是否删除该号码", 
							new DialogButtonClickListener() {
						
						@Override
						public void onClickYes() {
							// TODO Auto-generated method stub
							dbUtil.delete(BaseData.TB_TELEPHONE_INTERCEPT,
									new String[]{BaseData.TELEPHONE_ID},
									new String[]{telephones.get(position)[0]});
							telephones.remove(position);
							notifyDataSetChanged();
						}
						
						@Override
						public void onClickNo() {
							// TODO Auto-generated method stub
							
						}
					});
				}
			});
			
			return convertView;
		}
		
		class Tag{
			TextView telephone;
			TextView date;
			TextView count;
			ImageButton clear;
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.PHONE_STATE");
		registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(receiver);
	}
	
	public void adapterNotifyDataSetChanged(){
		telephones = dbUtil.getAllInfo(BaseData.TB_TELEPHONE_INTERCEPT);
		adapter.notifyDataSetChanged();
	}
}
