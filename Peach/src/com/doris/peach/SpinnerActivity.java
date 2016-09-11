package com.doris.peach;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;
import com.doris.peachlibrary.domain.District;
import com.doris.peachlibrary.view.CustomSpinner;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年4月18日
 */
public class SpinnerActivity extends BaseActivity implements OnItemSelectedListener {

	private Spinner s_spinner;
	private Spinner s_spinner_two;
	private Spinner s_spinner_three;
	private CustomSpinner cs_spinner;
	
	private List<District> citys = new ArrayList<District>();
	private List<District> countys = new ArrayList<District>();
	
	private SpinnerAdapter adapter;
	private SpinnerAdapter adapter_city;
	private SpinnerAdapter adapter_county;
	private DBUtil dbUtil;
	
	private String[] addr = new String[3];
	private String[] spinners = {"选项1", "选项2", "选项3" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner);
		
		setTitle();
		
		s_spinner = (Spinner) findViewById(R.id.s_spinner);
		s_spinner_two = (Spinner) findViewById(R.id.s_spinner_two);
		s_spinner_three = (Spinner) findViewById(R.id.s_spinner_three);
		cs_spinner = (CustomSpinner) findViewById(R.id.cs_spinner);
		
		s_spinner.setOnItemSelectedListener(this);
		s_spinner_two.setOnItemSelectedListener(this);
		s_spinner_three.setOnItemSelectedListener(this);
		
		dbUtil = new DBUtil(this);
		BaseData.initProvinces(dbUtil);
		
		adapter = new SpinnerAdapter(BaseData.provinces, 0);
		s_spinner.setAdapter(adapter);
		
		com.doris.peachlibrary.view.util.SpinnerAdapter adapter1 = 
				new com.doris.peachlibrary.view.util.SpinnerAdapter(this,
				android.R.layout.select_dialog_item, spinners);
		cs_spinner.setAdapter(adapter1);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		District district = null;
		switch (parent.getId()) {
		case R.id.s_spinner:
			district = (District) adapter.getItem(position);
			addr[0] = district.getDistrict();
			citys = getDistricts(district.getIndex());
			adapter_city = new SpinnerAdapter(citys, R.drawable.ic_city);
			s_spinner_two.setAdapter(adapter_city);
			break;
		case R.id.s_spinner_two:
			district = (District) adapter_city.getItem(position);
			addr[1] = district.getDistrict();
			countys = getDistricts(district.getIndex());
			if(countys.size() <= 0){
				s_spinner_three.setVisibility(View.GONE);
				ToastView.showWhiteContentToast(this, 
						R.drawable.ic_toast_flag_verbose, 
						addr[0] + "-" + addr[1]);
			}else{
				s_spinner_three.setVisibility(View.VISIBLE);
				adapter_county = new SpinnerAdapter(countys, 0);
				s_spinner_three.setAdapter(adapter_county);
			}
			break;
		case R.id.s_spinner_three:
			district = (District) adapter_county.getItem(position);
			addr[2] = district.getDistrict();
			ToastView.showWhiteContentToast(this,
					R.drawable.ic_toast_flag_verbose, 
					addr[0] + "-" + addr[1] + "-" + addr[2]);
			break;
		}
	}
	
	private List<District> getDistricts(String id){
		List<District> districts = new ArrayList<District>();
		List<String[]> list = dbUtil.query(BaseData.TB_DISTRICT,
				new String[]{BaseData.DISTRICT_ID, 
						BaseData.DISTRICT_ITEM}, 
				new String[]{BaseData.DISTRICT_FLAG}, new String[]{id});
		for(int i = 0; i < list.size(); i ++){
			districts.add(new District(list.get(i)[1],list.get(i)[0]));
		}
		return districts;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		s_spinner.setSelection(0);
		s_spinner_two.setSelection(0);
		s_spinner_three.setSelection(0);
	}
	
	class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter{
		
		private List<District> list;
		private int icon;
		
		public SpinnerAdapter(List<District> list, int icon) {
			// TODO Auto-generated constructor stub
			this.list = list;
			this.icon = icon;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = LayoutInflater.from(SpinnerActivity.this)
						.inflate(R.layout.item_spinner, null);
			}
			
			ImageView icon = (ImageView) convertView.
					findViewById(R.id.iv_spinner_icon);
			TextView text = (TextView) convertView.
					findViewById(R.id.tv_spinner_text);
			
			if(this.icon != 0){
				icon.setImageResource(this.icon);
			}else{
				icon.setVisibility(View.GONE);
			}
			
			text.setText(list.get(position).getDistrict());
			return convertView;
		}
		
	}
}
