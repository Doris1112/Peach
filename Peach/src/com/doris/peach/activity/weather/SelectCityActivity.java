package com.doris.peach.activity.weather;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peachlibrary.util.DeviceUtil;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.util.Log;
import com.doris.peachlibrary.util.SharedPreferencesUtil;
import com.doris.peachlibrary.util.UnicodeToString;
import com.doris.peachlibrary.view.ToastView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年5月5日
 */
public class SelectCityActivity extends BaseActivity {
	
	private GridView city_list;
	private EditText inputCity;
	private Button search;
	
	private List<String[]> cityLis = new ArrayList<String[]>();
	private String city;
	private String areaId;
	private DialogUtil dialogUtil;
	private CityAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_city);
		
		city_list = (GridView) findViewById(R.id.city_list);
		inputCity = (EditText) findViewById(R.id.input_city);
		search = (Button) findViewById(R.id.search);
		
		initCityList();
		dialogUtil = new DialogUtil(this);
		adapter = new CityAdapter();
		
		inputCity.addTextChangedListener(new Watcher());
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(DeviceUtil.isNetworkAvailable(SelectCityActivity.this)){
					dialogUtil.dialogLoading1(false);
					city = inputCity.getText().toString();
					getCityListInfo();
				}else{
					ToastView.showWhiteContentToast(SelectCityActivity.this,
							R.drawable.ic_toast_flag_verbose,
							"没有连接到网络");
				}
				//goWeatherActivity();
			}
		});
		city_list.setAdapter(adapter);
		city_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				areaId = cityLis.get(position)[0];
				city = cityLis.get(position)[1];
				SharedPreferencesUtil.setValues(SelectCityActivity.this, 
						new String[] { "weather_city","area_id" },
						new Object[] { city, areaId });
				Intent intent = new Intent(SelectCityActivity.this,
						WeatherActivity.class);
				startActivity(intent);
				SelectCityActivity.this.finish();
			}
		});
	}
	
	class CityAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cityLis.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return cityLis.get(position)[1];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView text = null;
			if(convertView == null){
				convertView = LayoutInflater.from(SelectCityActivity.this)
						.inflate(R.layout.item_select_city, null);
				text = (TextView) convertView.findViewById(R.id.city);
				convertView.setTag(text);
			}else{
				text = (TextView) convertView.getTag();
			}
			
			text.setText(getItem(position)+"");
			
			return convertView;
		}
	}
	
	/**
	 * 监听编辑框内容，输入内容，显示搜索按键
	 */
	class Watcher implements TextWatcher {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (inputCity.getText().toString().length() == 0) {
				search.setVisibility(View.GONE);
			} else {
				search.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private void getCityListInfo() {
		Parameters para = new Parameters();
		para.put("cityname", city);
		ApiStoreSDK.execute(BaseData.CITYLIST, ApiStoreSDK.GET,
				para, new ApiCallBack() {

			@Override
			public void onSuccess(int status, String responseString) {
				String jsonValue = UnicodeToString.decode(responseString);
				parserJson(jsonValue);
			}

			@Override
			public void onComplete() {
			}

			@Override
			public void onError(int status, String responseString, Exception e) {
				String jsonValue = UnicodeToString.decode(responseString);
				parserJson(jsonValue);
			}

		});
	} 
	
	private void parserJson(String jsonValue){
		try {
			JSONObject jsonObject = new JSONObject(jsonValue);
			int errNum = jsonObject.getInt("errNum");
			if(errNum == -1){
				String errMsg = jsonObject.getString("errMsg");
				dialogUtil.showAlertDialog1(errMsg,null);
			}else{
				cityLis.clear();
				JSONArray retData = jsonObject.getJSONArray("retData");
				for (int i = 0; i < retData.length(); i++) {
					JSONObject jObject = retData.optJSONObject(i);
					String nameCn = jObject.getString("name_cn");
					String areaId = jObject.getString("area_id");
					cityLis.add(new String[]{areaId, nameCn});
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.getInstance().writeLog("选择城市解析城市列表出错：", e);
		}
		dialogUtil.hideDialogLoading();
		adapter.notifyDataSetChanged();
	}
	
	private void initCityList(){
		cityLis.add(new String[]{"101010100","北京"});
		cityLis.add(new String[]{"101020100","上海"});
		cityLis.add(new String[]{"101030100","天津"});
		cityLis.add(new String[]{"101040100","重庆"});
		cityLis.add(new String[]{"101320101","香港"});
		cityLis.add(new String[]{"101330101","澳门"});
		cityLis.add(new String[]{"101340102","台北"});
		cityLis.add(new String[]{"101250101","长沙"});
		cityLis.add(new String[]{"101200101","武汉"});
		cityLis.add(new String[]{"101210101","杭州"});
	}
	
}
