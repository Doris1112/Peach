package com.doris.peach;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.baidumap.BikingRouteOverlay;
import com.doris.peachlibrary.util.baidumap.BusLineOverlay;
import com.doris.peachlibrary.util.baidumap.DrivingRouteOverlay;
import com.doris.peachlibrary.util.baidumap.PoiOverlay;
import com.doris.peachlibrary.util.baidumap.TransitRouteOverlay;
import com.doris.peachlibrary.util.baidumap.WalkingRouteOverlay;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * 百度地图
 * @author Doris
 *
 *         2016年5月24日
 */
public class BaiduMapActivity extends BaseActivity
		implements OnGetPoiSearchResultListener, OnGetBusLineSearchResultListener, OnGetSuggestionResultListener,
		OnGetRoutePlanResultListener, OnMapClickListener {

	private MapView mv_baidumaps;
	private LinearLayout ll_baidumaps_path;
	private RadioGroup rg_baidumaps;
	private EditText et_baidumaps_end, et_baidumaps_origin, et_baidimaps_search;
	private Button b_baidumaps_pre, b_baidumaps_next;
	private ListView lv_baidumaps;

	private BaiduMap mBaiduMap;
	private LocationClient mLocClient;
	private boolean isFirstLoc = true;
	private MyLocationListenner mListener = new MyLocationListenner();
	private boolean isShowPath = true;
	private int goWay = -1; // 0公交 1驾车 2步行 3骑行
	private int nodeIndex = -2;// 节点索引,供浏览节点时使用
	private BusLineResult route = null;// 保存驾车/步行路线数据的变量，供浏览节点时使用
	private PoiSearch mSearch;
	private List<String> busLineIDList = null;
	private int busLineIndex = 0;
	private BusLineSearch mBusLineSearch = null;
	private BusLineOverlay overlay;// 公交路线绘制对象
	private String city = "上海";
	private String currenPosition = "东方明珠";
	private PoiSearch mPoiSearch = null;
	private SuggestionSearch mSuggestionSearch = null;
	private ArrayAdapter<String> sugAdapter = null;
	// 搜索模块，也可去掉地图模块独立使用
	private RoutePlanSearch mRPSearch = null;
	private RouteLine routeLine = null;
	private int nodeIndexLine = -1; // 节点索引,供浏览节点时使用

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_baidumap);

		mv_baidumaps = (MapView) findViewById(R.id.mv_baidumaps);
		mBaiduMap = mv_baidumaps.getMap();
		mBaiduMap.setOnMapClickListener(this);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(mListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setAddrType("all");
		mLocClient.setLocOption(option);
		mLocClient.start();

		et_baidimaps_search = (EditText) findViewById(R.id.et_baidimaps_search);
		b_baidumaps_pre = (Button) findViewById(R.id.b_baidumaps_pre);
		b_baidumaps_next = (Button) findViewById(R.id.b_baidumaps_next);
		lv_baidumaps = (ListView) findViewById(R.id.lv_baidumaps);

		mSearch = PoiSearch.newInstance();
		mSearch.setOnGetPoiSearchResultListener(this);
		mBusLineSearch = BusLineSearch.newInstance();
		mBusLineSearch.setOnGetBusLineSearchResultListener(this);
		busLineIDList = new ArrayList<String>();
		overlay = new BusLineOverlay(mBaiduMap);
		mBaiduMap.setOnMarkerClickListener(overlay);
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {

			@Override
			public void onGetPoiResult(PoiResult result) {
				// TODO Auto-generated method stub
				if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
					ToastView.showWhiteContentToast(BaiduMapActivity.this, 
							R.drawable.ic_toast_flag_verbose, "抱歉，未找到结果");
					return;
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					mBaiduMap.clear();
					PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
					mBaiduMap.setOnMarkerClickListener(overlay);
					overlay.setData(result);
					overlay.addToMap();
					overlay.zoomToSpan();
					return;
				}
				if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

					// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
					String strInfo = "在";
					for (CityInfo cityInfo : result.getSuggestCityList()) {
						strInfo += cityInfo.city;
						strInfo += ",";
					}
					strInfo += "找到结果";
					ToastView.showWhiteContentToast(BaiduMapActivity.this, 
							R.drawable.ic_toast_flag_verbose, strInfo);
				}
			}

			@Override
			public void onGetPoiDetailResult(PoiDetailResult result) {
				// TODO Auto-generated method stub
				if (result.error != SearchResult.ERRORNO.NO_ERROR) {
					ToastView.showWhiteContentToast(BaiduMapActivity.this, 
							R.drawable.ic_toast_flag_verbose, "抱歉，未找到结果");
				} else {
					ToastView.showWhiteContentToast(BaiduMapActivity.this,
							R.drawable.ic_toast_flag_verbose, result.getName() + ": " + result.getAddress());
				}
			}
		});
		mRPSearch = RoutePlanSearch.newInstance();
		mRPSearch.setOnGetRoutePlanResultListener(this);

		sugAdapter = new ArrayAdapter<String>(this, R.layout.item_text_black);
		lv_baidumaps.setAdapter(sugAdapter);
		lv_baidumaps.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String search = ((TextView) view).getText().toString();
				et_baidimaps_search.setText(search);
				et_baidimaps_search.setSelection(search.length());
				sugAdapter.clear();
				lv_baidumaps.setVisibility(View.GONE);
			}
		});
		et_baidimaps_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() <= 0) {
					lv_baidumaps.setVisibility(View.GONE);
					return;
				}
				/**
				 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
				 */
				mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(s.toString()).city(city));
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		ll_baidumaps_path = (LinearLayout) findViewById(R.id.ll_baidumaps_path);
		rg_baidumaps = (RadioGroup) findViewById(R.id.rg_baidumaps);
		et_baidumaps_origin = (EditText) findViewById(R.id.et_baidumaps_origin);
		et_baidumaps_end = (EditText) findViewById(R.id.et_baidumaps_end);

		rg_baidumaps.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_baidumaps_bus:
					goWay = 0;
					break;
				case R.id.rb_baidumaps_drive:
					goWay = 1;
					break;
				case R.id.rb_baidumaps_walk:
					goWay = 2;
					break;
				case R.id.rb_baidumaps_cycling:
					goWay = 3;
					break;
				}
			}
		});
		et_baidumaps_end.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					searchPath();
					return true;
				}
				return false;
			}
		});
		b_baidumaps_pre.setVisibility(View.GONE);
		b_baidumaps_next.setVisibility(View.GONE);
	}

	/**
	 * 搜索
	 * 
	 * @param view
	 */
	public void search(View view) {
		if (goWay == -1) {
			String searchInfo = et_baidimaps_search.getText().toString();
			if (searchInfo.trim().length() > 0) {
				if (searchInfo.matches("\\d+[\u8def]{0,1}")) {
					busLineIDList.clear();
					busLineIndex = 0;
					b_baidumaps_next.setVisibility(View.GONE);
					b_baidumaps_pre.setVisibility(View.GONE);
					mSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(searchInfo));
					lv_baidumaps.setVisibility(View.GONE);
				} else {
					mPoiSearch.searchInCity((new PoiCitySearchOption()).city(city).keyword(searchInfo).pageNum(0));
					lv_baidumaps.setVisibility(View.GONE);
				}
			} else {
				ToastView.showWhiteContentToast(BaiduMapActivity.this,
						R.drawable.ic_toast_flag_verbose, "请输入地名或公交");
			}
		} else {
			searchPath();
		}
	}

	/**
	 * 搜索线路
	 */
	private void searchPath() {
		String origin = et_baidumaps_origin.getText().toString();
		if (origin.trim().length() > 0) {
			String end = et_baidumaps_end.getText().toString();
			if (end.trim().length() > 0) {
				routeLine = null;
				b_baidumaps_pre.setVisibility(View.GONE);
				b_baidumaps_next.setVisibility(View.GONE);
				mBaiduMap.clear();
				// 设置起终点信息，对于tranist search 来说，城市名无意义
				PlanNode stNode = PlanNode.withCityNameAndPlaceName(city, origin);
				PlanNode enNode = PlanNode.withCityNameAndPlaceName(city, end);
				switch (goWay) {
				case 0:
					mRPSearch.transitSearch((new TransitRoutePlanOption()).from(stNode).city(city).to(enNode));
					break;
				case 1:
					mRPSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(enNode));
					break;
				case 2:
					mRPSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode).to(enNode));
					break;
				case 3:
					mRPSearch.bikingSearch((new BikingRoutePlanOption()).from(stNode).to(enNode));
					break;
				}
			} else {
				ToastView.showWhiteContentToast(BaiduMapActivity.this, 
						R.drawable.ic_toast_flag_verbose, "请输入终点");
			}
		} else {
			ToastView.showWhiteContentToast(BaiduMapActivity.this, R.drawable.ic_toast_flag_verbose,
					"请输入起点");
		}
	}

	/**
	 * 路况
	 * 
	 * @param view
	 */
	public void roadCondition(View view) {
		CheckBox rc = (CheckBox) view;
		mBaiduMap.setTrafficEnabled(rc.isChecked());
	}

	/**
	 * 线路查询
	 * 
	 * @param view
	 */
	public void baidumapPath(View view) {
		if (isShowPath) {
			isShowPath = false;
			rg_baidumaps.check(R.id.rb_baidumaps_bus);
			ll_baidumaps_path.setVisibility(View.VISIBLE);
			rg_baidumaps.setVisibility(View.VISIBLE);
			et_baidumaps_origin.setText(currenPosition);
			((ImageView) view).setImageResource(R.drawable.baidumap_canel);
		} else {
			isShowPath = true;
			goWay = -1;
			nodeIndexLine = -1;
			et_baidumaps_end.setText("");
			rg_baidumaps.clearCheck();
			ll_baidumaps_path.setVisibility(View.GONE);
			rg_baidumaps.setVisibility(View.GONE);
			b_baidumaps_next.setVisibility(View.GONE);
			b_baidumaps_pre.setVisibility(View.GONE);
			((ImageView) view).setImageResource(R.drawable.baidumap_path);
		}
	}

	/**
	 * 节点浏览示例
	 * 
	 * @param v
	 */
	public void nodeClick(View v) {
		if (goWay == -1) {
			if (nodeIndex < -1 || route == null || nodeIndex >= route.getStations().size())
				return;
			TextView popupText = new TextView(this);
			popupText.setBackgroundResource(R.drawable.popup);
			popupText.setTextColor(0xff000000);
			// 上一个节点
			if (b_baidumaps_pre.equals(v) && nodeIndex > 0) {
				// 索引减
				nodeIndex--;
			}
			// 下一个节点
			if (b_baidumaps_next.equals(v) && nodeIndex < (route.getStations().size() - 1)) {
				// 索引加
				nodeIndex++;
			}
			if (nodeIndex >= 0) {
				// 移动到指定索引的坐标
				mBaiduMap.setMapStatus(
						MapStatusUpdateFactory.newLatLng(route.getStations().get(nodeIndex).getLocation()));
				// 弹出泡泡
				popupText.setText(route.getStations().get(nodeIndex).getTitle());
				mBaiduMap
						.showInfoWindow(new InfoWindow(popupText, route.getStations().get(nodeIndex).getLocation(), 0));
			}
		} else {
			if (routeLine == null || routeLine.getAllStep() == null) {
				return;
			}
			if (nodeIndexLine == -1 && v.getId() == R.id.b_baidumaps_pre) {
				return;
			}
			// 设置节点索引
			if (v.getId() == R.id.b_baidumaps_next) {
				if (nodeIndexLine < routeLine.getAllStep().size() - 1) {
					nodeIndexLine++;
				} else {
					return;
				}
			} else if (v.getId() == R.id.b_baidumaps_pre) {
				if (nodeIndexLine > 0) {
					nodeIndexLine--;
				} else {
					return;
				}
			}
			// 获取节结果信息
			LatLng nodeLocation = null;
			String nodeTitle = null;
			Object step = routeLine.getAllStep().get(nodeIndexLine);
			if (step instanceof DrivingRouteLine.DrivingStep) {
				nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
				nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
			} else if (step instanceof WalkingRouteLine.WalkingStep) {
				nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
				nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
			} else if (step instanceof TransitRouteLine.TransitStep) {
				nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
				nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
			} else if (step instanceof BikingRouteLine.BikingStep) {
				nodeLocation = ((BikingRouteLine.BikingStep) step).getEntrance().getLocation();
				nodeTitle = ((BikingRouteLine.BikingStep) step).getInstructions();
			}

			if (nodeLocation == null || nodeTitle == null) {
				return;
			}
			// 移动节点至中心
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
			// show popup
			TextView popupText = new TextView(this);
			popupText.setBackgroundResource(R.drawable.popup);
			popupText.setTextColor(0xFF000000);
			popupText.setText(nodeTitle);
			mBaiduMap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
		}
	}

	class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mv_baidumaps == null) {
				return;
			}
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(18.0f);
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
			}

			city = location.getCity();
			currenPosition = location.getDistrict() + location.getStreet() + location.getStreetNumber();

			// StringBuffer sb = new StringBuffer();
			// sb.append("time : ");
			// sb.append(location.getTime());//获得当前时间
			// sb.append("\nerror code : ");
			// sb.append(location.getLocType());//获得erro code得知定位现状
			// sb.append("\nlatitude : ");
			// sb.append(location.getLatitude());//获得纬度
			// sb.append("\nlontitude : ");
			// sb.append(location.getLongitude());//获得经度
			// sb.append("\nradius : ");
			// sb.append(location.getRadius());
			// if (location.getLocType() ==
			// BDLocation.TypeGpsLocation){//通过GPS定位
			// sb.append("\nspeed : ");
			// sb.append(location.getSpeed());//获得速度
			// sb.append("\nsatellite : ");
			// sb.append(location.getSatelliteNumber());
			// sb.append("\ndirection : ");
			// sb.append("\naddr : ");
			// sb.append(location.getAddrStr());//获得当前地址
			// sb.append(location.getDirection());//获得方位
			// } else if (location.getLocType() ==
			// BDLocation.TypeNetWorkLocation){//通过网络连接定位
			// sb.append("\naddr : ");
			// sb.append(location.getAddrStr());//获得当前地址
			// //运营商信息
			// sb.append("\noperationers : ");
			// sb.append(location.getOperators());//获得经营商？
			// }
			//
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mv_baidumaps.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mv_baidumaps.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mv_baidumaps.onDestroy();
		mv_baidumaps = null;
		mSearch.destroy();
		mSuggestionSearch.destroy();
		mRPSearch.destroy();
		mPoiSearch.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "抱歉，未找到结果");
			return;
		}
		// 遍历所有poi，找到类型为公交线路的poi
		busLineIDList.clear();
		for (PoiInfo poi : result.getAllPoi()) {
			if (poi.type == PoiInfo.POITYPE.BUS_LINE || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
				busLineIDList.add(poi.uid);
			}
		}
		SearchNextBusline();
		route = null;
	}

	public void SearchNextBusline() {
		if (busLineIndex >= busLineIDList.size()) {
			busLineIndex = 0;
		}
		if (busLineIndex >= 0 && busLineIndex < busLineIDList.size() && busLineIDList.size() > 0) {
			mBusLineSearch.searchBusLine((new BusLineSearchOption().city(city).uid(busLineIDList.get(busLineIndex))));

			busLineIndex++;
		}

	}

	@Override
	public void onGetBusLineResult(BusLineResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "抱歉，未找到结果");
			return;
		}
		mBaiduMap.clear();
		route = result;
		nodeIndex = -1;
		overlay.removeFromMap();
		overlay.setData(result);
		overlay.addToMap();
		overlay.zoomToSpan();
		b_baidumaps_pre.setVisibility(View.VISIBLE);
		b_baidumaps_next.setVisibility(View.VISIBLE);
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		// TODO Auto-generated method stub
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		sugAdapter.clear();
		for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
			if (info.key != null)
				sugAdapter.add(info.key);
		}
		if (sugAdapter.getCount() > 1) {
			lv_baidumaps.setVisibility(View.VISIBLE);
			sugAdapter.notifyDataSetChanged();
		} else {
			lv_baidumaps.setVisibility(View.GONE);
		}
	}

	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
			// }
			return true;
		}
	}

	@Override
	public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
		// TODO Auto-generated method stub
		if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
			ToastView.showWhiteContentToast(this,  R.drawable.ic_toast_flag_verbose, "抱歉，未找到结果");
		}
		if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndexLine = -1;
			b_baidumaps_pre.setVisibility(View.VISIBLE);
			b_baidumaps_next.setVisibility(View.VISIBLE);
			routeLine = bikingRouteResult.getRouteLines().get(0);
			BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(bikingRouteResult.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			ToastView.showWhiteContentToast(this,  R.drawable.ic_toast_flag_verbose, "抱歉，未找到结果");
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndexLine = -1;
			b_baidumaps_pre.setVisibility(View.VISIBLE);
			b_baidumaps_next.setVisibility(View.VISIBLE);
			routeLine = result.getRouteLines().get(0);
			DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			ToastView.showWhiteContentToast(this,  R.drawable.ic_toast_flag_verbose, "抱歉，未找到结果");
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndexLine = -1;
			b_baidumaps_pre.setVisibility(View.VISIBLE);
			b_baidumaps_next.setVisibility(View.VISIBLE);
			routeLine = result.getRouteLines().get(0);
			TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			ToastView.showWhiteContentToast(this,  R.drawable.ic_toast_flag_verbose, "抱歉，未找到结果");
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndexLine = -1;
			b_baidumaps_pre.setVisibility(View.VISIBLE);
			b_baidumaps_next.setVisibility(View.VISIBLE);
			routeLine = result.getRouteLines().get(0);
			WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		mBaiduMap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
