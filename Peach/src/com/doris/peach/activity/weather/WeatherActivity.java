package com.doris.peach.activity.weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年5月5日
 */
public class WeatherActivity extends BaseActivity implements OnClickListener{
	
	private LinearLayout weatherBg, titleBarLayout;
	private ImageView share, weatherIcon, refresh;
	private ProgressBar refreshing;
	private ScrollView scrollView;
	private LinearLayout currentWeatherLayout;
	private TextView currentTemperatureText, currentWeatherText, 
		temperatureText, windText, dateText, cityText, updateTimeText;
	private ListView weatherForecastList;
	
	private String city;
	private String areaId;
	private DialogUtil dialogUtil;
	private Time time = new Time();
	
	private List<Map<String, Object>> weatherForecast = 
			new ArrayList<Map<String,Object>>();
	private String[] todayInfo = new String[6];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		
		weatherBg = (LinearLayout) findViewById(R.id.weather_bg);
		titleBarLayout = (LinearLayout) findViewById(R.id.title_bar_layout);
		cityText = (TextView) findViewById(R.id.city);
		share = (ImageView) findViewById(R.id.share);
		refresh = (ImageView) findViewById(R.id.refresh);
		refreshing = (ProgressBar) findViewById(R.id.refreshing);
		updateTimeText = (TextView) findViewById(R.id.update_time);
		scrollView = (ScrollView) findViewById(R.id.scroll_view);
		currentWeatherLayout = (LinearLayout) findViewById(R.id.current_weather_layout);
		weatherIcon = (ImageView) findViewById(R.id.weather_icon);
		currentTemperatureText = (TextView) findViewById(R.id.current_temperature);
		currentWeatherText = (TextView) findViewById(R.id.current_weather);
		temperatureText = (TextView) findViewById(R.id.temperature);
		windText = (TextView) findViewById(R.id.wind);
		dateText = (TextView) findViewById(R.id.date);
		weatherForecastList = (ListView) findViewById(R.id.weather_forecast_list);
	
		setCurrentWeatherLayoutHight();
		
		city = (String) SharedPreferencesUtil.getValue(this,
				"weather_city", "");
		areaId = (String) SharedPreferencesUtil.getValue(this, 
				"area_id", "");
		
		updateTimeText.setText("— — 更新");
		cityText.setText(city);
		cityText.setOnClickListener(this);
		share.setOnClickListener(this);
		refresh.setOnClickListener(this);
		
		dialogUtil = new DialogUtil(this);
		if(DeviceUtil.isNetworkAvailable(this)){
			refreshing(true);
			queryWeather();
		}else{
			ToastView.showWhiteContentToast(this,
					R.drawable.ic_toast_flag_verbose, 
					"没有连接到网络");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.city:
			intent = new Intent(this, SelectCityActivity.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.share:
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("image/*");
			intent.putExtra(Intent.EXTRA_SUBJECT, "好友分享");
			intent.putExtra(Intent.EXTRA_TEXT,
					"可以随时随地查看天气信息，是您出差、旅行的贴心助手！推荐你也试试~");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(intent, "好友分享"));
			break;
		case R.id.refresh:
			refreshing(true);
			queryWeather();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 刷新时显示进度条
	 * 
	 * @param isRefreshing
	 *            是否正在刷新
	 */
	private void refreshing(boolean isRefreshing) {
		if (isRefreshing) {
			refresh.setVisibility(View.GONE);
			refreshing.setVisibility(View.VISIBLE);
		} else {
			refresh.setVisibility(View.VISIBLE);
			refreshing.setVisibility(View.GONE);
		}
	}
	
	private void queryWeather(){
		Parameters para = new Parameters();
		para.put("cityname", city);
		para.put("cityid", areaId);
		ApiStoreSDK.execute(BaseData.RECENTWEATHERS, ApiStoreSDK.GET,
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
	
	private void parserJson(String jsonValue) {
		try {
			JSONObject jsonObject = new JSONObject(jsonValue);
			int errNum = jsonObject.getInt("errNum");
			if(errNum != 0){
				String errMsg = jsonObject.getString("errMsg");
				dialogUtil.showAlertDialog1(errMsg, null);
			}else{
				JSONObject retData = jsonObject.getJSONObject("retData");
				JSONObject today = retData.getJSONObject("today");
				String hightemp = today.getString("hightemp");
				String lowtemp = today.getString("lowtemp");
				String date = today.getString("date");
				String week = today.getString("week");
				String fengxiang = today.getString("fengxiang");
				String fengli = today.getString("fengli");
				todayInfo[1] = today.getString("curTemp");
				todayInfo[2] = lowtemp + "~" + hightemp;
				todayInfo[3] = today.getString("type");
				todayInfo[4] = fengxiang + fengli;
				todayInfo[5] = week + "  " + date;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				todayInfo[0] = sdf.format(new Date());
				
				weatherForecast.clear();
				JSONArray forecast = retData.getJSONArray("forecast");
				for (int i = 0; i < forecast.length(); i++) {
					JSONObject jObject = forecast.optJSONObject(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("week", jObject.getString("week"));
					map.put("weather", jObject.getString("type"));
					map.put("temperature", jObject.getString("lowtemp") + "~" 
							+ jObject.getString("hightemp"));
					map.put("wind", jObject.getString("fengxiang") 
							+ jObject.getString("fengli"));
					weatherForecast.add(map);
				}
				
				updateWeatherImage(todayInfo[3]);
				updateWeatherInfo();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.getInstance().writeLog("解析天气信息出错：", e);
		}
		refreshing(false);
	}
	
	/**
	 * 更新背景图片和天气图标
	 */
	private void updateWeatherImage(String currentWeather) {
		scrollView.setVisibility(View.VISIBLE);
		if (currentWeather.contains("转")) {
			currentWeather = currentWeather.substring(0,
					currentWeather.indexOf("转"));
		}
		time.setToNow();
		if (currentWeather.contains("晴")) {
			if (time.hour >= 7 && time.hour < 19) {
				weatherBg.setBackgroundResource(R.drawable.bg_fine_day);
				weatherIcon.setImageResource(R.drawable.weather_img_fine_day);
			} else {
				weatherBg.setBackgroundResource(R.drawable.bg_fine_night);
				weatherIcon.setImageResource(R.drawable.weather_img_fine_night);
			}
		} else if (currentWeather.contains("多云")) {
			if (time.hour >= 7 && time.hour < 19) {
				weatherBg.setBackgroundResource(R.drawable.bg_cloudy_day);
				weatherIcon.setImageResource(R.drawable.weather_img_cloudy_day);
			} else {
				weatherBg.setBackgroundResource(R.drawable.bg_cloudy_night);
				weatherIcon
						.setImageResource(R.drawable.weather_img_cloudy_night);
			}
		} else if (currentWeather.contains("阴")) {
			weatherBg.setBackgroundResource(R.drawable.bg_overcast);
			weatherIcon.setImageResource(R.drawable.weather_img_overcast);
		} else if (currentWeather.contains("雷")) {
			weatherBg.setBackgroundResource(R.drawable.bg_thunder_storm);
			weatherIcon.setImageResource(R.drawable.weather_img_thunder_storm);
		} else if (currentWeather.contains("雨")) {
			weatherBg.setBackgroundResource(R.drawable.bg_rain);
			if (currentWeather.contains("小雨")) {
				weatherIcon.setImageResource(R.drawable.weather_img_rain_small);
			} else if (currentWeather.contains("中雨")) {
				weatherIcon
						.setImageResource(R.drawable.weather_img_rain_middle);
			} else if (currentWeather.contains("大雨")) {
				weatherIcon.setImageResource(R.drawable.weather_img_rain_big);
			} else if (currentWeather.contains("暴雨")) {
				weatherIcon.setImageResource(R.drawable.weather_img_rain_storm);
			} else if (currentWeather.contains("雨夹雪")) {
				weatherIcon.setImageResource(R.drawable.weather_img_rain_snow);
			} else if (currentWeather.contains("冻雨")) {
				weatherIcon.setImageResource(R.drawable.weather_img_sleet);
			} else {
				weatherIcon
						.setImageResource(R.drawable.weather_img_rain_middle);
			}
		} else if (currentWeather.contains("雪")
				|| currentWeather.contains("冰雹")) {
			weatherBg.setBackgroundResource(R.drawable.bg_snow);
			if (currentWeather.contains("小雪")) {
				weatherIcon.setImageResource(R.drawable.weather_img_snow_small);
			} else if (currentWeather.contains("中雪")) {
				weatherIcon
						.setImageResource(R.drawable.weather_img_snow_middle);
			} else if (currentWeather.contains("大雪")) {
				weatherIcon.setImageResource(R.drawable.weather_img_snow_big);
			} else if (currentWeather.contains("暴雪")) {
				weatherIcon.setImageResource(R.drawable.weather_img_snow_storm);
			} else if (currentWeather.contains("冰雹")) {
				weatherIcon.setImageResource(R.drawable.weather_img_hail);
			} else {
				weatherIcon
						.setImageResource(R.drawable.weather_img_snow_middle);
			}
		} else if (currentWeather.contains("雾")) {
			weatherBg.setBackgroundResource(R.drawable.bg_fog);
			weatherIcon.setImageResource(R.drawable.weather_img_fog);
		} else if (currentWeather.contains("霾")) {
			weatherBg.setBackgroundResource(R.drawable.bg_haze);
			weatherIcon.setImageResource(R.drawable.weather_img_fog);
		} else if (currentWeather.contains("沙尘暴")
				|| currentWeather.contains("浮尘")
				|| currentWeather.contains("扬沙")) {
			weatherBg.setBackgroundResource(R.drawable.bg_sand_storm);
			weatherIcon.setImageResource(R.drawable.weather_img_sand_storm);
		} else {
			weatherBg.setBackgroundResource(R.drawable.bg_na);
			weatherIcon.setImageResource(R.drawable.weather_img_fine_day);
		}
	}

	/**
	 * 更新界面（天气信息）
	 */
	private void updateWeatherInfo() {
		updateTimeText.setText(todayInfo[0] + " 更新");
		currentTemperatureText.setText(todayInfo[1]);
		temperatureText.setText(todayInfo[2]);
		currentWeatherText.setText(todayInfo[3]);
		windText.setText(todayInfo[4]);
		dateText.setText(todayInfo[5]);
		
		weatherForecastList.setAdapter(new WeatherAdapter());
		setListViewHeightBasedOnChildren(weatherForecastList);
	}
	
	class WeatherAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return weatherForecast.size();
		}

		@Override
		public Object getItem(int position) {
			return weatherForecast.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(WeatherActivity.this)
						.inflate( R.layout.item_weather_forecast, null);
				holder = new ViewHolder();
				holder.date = (TextView) convertView
						.findViewById(R.id.weather_forecast_date);
				holder.img = (ImageView) convertView
						.findViewById(R.id.weather_forecast_img);
				holder.weather = (TextView) convertView
						.findViewById(R.id.weather_forecast_weather);
				holder.temperature = (TextView) convertView
						.findViewById(R.id.weather_forecast_temperature);
				holder.wind = (TextView) convertView
						.findViewById(R.id.weather_forecast_wind);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Typeface face = Typeface.createFromAsset(getAssets(),
					"fonts/fangzhenglantingxianhe_GBK.ttf");
			holder.date.setText(weatherForecast.get(position).get("week").toString());
			holder.img.setImageResource(getWeatherImg(weatherForecast.get(
					position).get("weather") .toString()));
			holder.weather.setText(weatherForecast.get(position).get("weather")
					.toString());
			holder.temperature.setText(weatherForecast.get(position)
					.get("temperature").toString());
			holder.temperature.setTypeface(face);
			holder.wind.setText(weatherForecast.get(position).get("wind").toString());
			return convertView;
		}
		
		class ViewHolder {
			TextView date;
			ImageView img;
			TextView weather;
			TextView temperature;
			TextView wind;
		}

	}
	
	/**
	 * 根据天气信息设置天气图片
	 * 
	 * @param weather
	 *            天气信息
	 * @return 对应的天气图片id
	 */
	private int getWeatherImg(String weather) {
		int img = 0;
		if (weather.contains("转")) {
			weather = weather.substring(0, weather.indexOf("转"));
		}
		if (weather.contains("晴")) {
			img = R.drawable.weather_icon_fine;
		} else if (weather.contains("多云")) {
			img = R.drawable.weather_icon_cloudy;
		} else if (weather.contains("阴")) {
			img = R.drawable.weather_icon_overcast;
		} else if (weather.contains("雷")) {
			img = R.drawable.weather_icon_thunder_storm;
		} else if (weather.contains("小雨")) {
			img = R.drawable.weather_icon_rain_small;
		} else if (weather.contains("中雨")) {
			img = R.drawable.weather_icon_rain_middle;
		} else if (weather.contains("大雨")) {
			img = R.drawable.weather_icon_rain_big;
		} else if (weather.contains("暴雨")) {
			img = R.drawable.weather_icon_rain_storm;
		} else if (weather.contains("雨夹雪")) {
			img = R.drawable.weather_icon_rain_snow;
		} else if (weather.contains("冻雨")) {
			img = R.drawable.weather_icon_sleet;
		} else if (weather.contains("小雪")) {
			img = R.drawable.weather_icon_snow_small;
		} else if (weather.contains("中雪")) {
			img = R.drawable.weather_icon_snow_middle;
		} else if (weather.contains("大雪")) {
			img = R.drawable.weather_icon_snow_big;
		} else if (weather.contains("暴雪")) {
			img = R.drawable.weather_icon_snow_storm;
		} else if (weather.contains("冰雹")) {
			img = R.drawable.weather_icon_hail;
		} else if (weather.contains("雾") || weather.contains("霾")) {
			img = R.drawable.weather_icon_fog;
		} else if (weather.contains("沙尘暴") || weather.contains("浮尘")
				|| weather.contains("扬沙")) {
			img = R.drawable.weather_icon_sand_storm;
		} else {
			img = R.drawable.weather_icon_fine;
		}
		return img;
	}
	
	/**
	 * 设置布局的高度（铺满屏幕）
	 */
	private void setCurrentWeatherLayoutHight() {
		// 通知栏高度
		int statusBarHeight = 0;
		try {
			statusBarHeight = getResources().getDimensionPixelSize(
					Integer.parseInt(Class
							.forName("com.android.internal.R$dimen")
							.getField("status_bar_height")
							.get(Class.forName("com.android.internal.R$dimen")
									.newInstance()).toString()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 屏幕高度
		int displayHeight = ((WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getHeight();
		// title bar LinearLayout高度
		titleBarLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		int titleBarHeight = titleBarLayout.getMeasuredHeight();

		LayoutParams linearParams = (LayoutParams) currentWeatherLayout
				.getLayoutParams();
		linearParams.height = displayHeight - statusBarHeight - titleBarHeight;
		currentWeatherLayout.setLayoutParams(linearParams);
	}
	
	/**
	 * 设置ListView高度
	 * 
	 * @param listView
	 *            要设置高度的ListView
	 */
	private void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
