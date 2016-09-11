package com.doris.peach.activity.geomap;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.AMapUtil;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.util.Log;
import com.doris.peachlibrary.view.ToastView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 高德地图
 * 
 * @author Doris
 *
 *         2016年6月20日
 */
public class GaodeMapActivity extends BaseActivity implements LocationSource, AMapLocationListener,
		OnRouteSearchListener, OnMarkerClickListener, InputtipsListener, InfoWindowAdapter, OnPoiSearchListener {

	private MapView mv_gaodemaps;
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	private GeocodeSearch geocoderSearch;
	private GeocodeSearch geocoderSearchEnd;
	private RouteSearch mRouteSearch;
	private DriveRouteResult mDriveRouteResult;
	private BusRouteResult mBusRouteResult;
	private WalkRouteResult mWalkRouteResult;
	private PoiResult poiResult; // poi返回的结果
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch poiSearch;// POI搜索

	private RadioGroup rg_gaodemaps;
	private LinearLayout ll_gaodemaps_path;
	private EditText et_gaodemaps_origin, et_gaodemaps_end, et_gaodemaps_search;
	private RelativeLayout mBottomLayout;
	private TextView mRotueTimeDes, mRouteDetailDes;
	private LinearLayout mBusResultLayout;
	private ListView mBusResultList, lv_gaodemaps;

	private boolean isShowPath = true;
	private String city = "上海";
	private String currenPosition = "上海虹桥机场";
	private LatLonPoint mStartPoint;// 起点，
	private LatLonPoint mEndPoint;// 终点，
	private int routeType = 0; // 0 驾车 1 公交 2 步行
	private DialogUtil dialogUtil;
	private List<String> listString = new ArrayList<String>();
	private ArrayAdapter<String> aAdapter;
	
	private boolean isFirst = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gaodemap);

		mv_gaodemaps = (MapView) findViewById(R.id.mv_gaodemaps);
		mv_gaodemaps.onCreate(savedInstanceState);
		aMap = mv_gaodemaps.getMap();
		// 设置定位监听
		aMap.setLocationSource(this);
		// 设置默认定位按钮是否显示
		// aMap.getUiSettings().setMyLocationButtonEnabled(true);
		// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		aMap.setMyLocationEnabled(true);
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

		et_gaodemaps_search = (EditText) findViewById(R.id.et_gaodemaps_search);
		lv_gaodemaps = (ListView) findViewById(R.id.lv_gaodemaps);
		rg_gaodemaps = (RadioGroup) findViewById(R.id.rg_gaodemaps);
		ll_gaodemaps_path = (LinearLayout) findViewById(R.id.ll_gaodemaps_path);
		et_gaodemaps_origin = (EditText) findViewById(R.id.et_gaodemaps_origin);
		et_gaodemaps_end = (EditText) findViewById(R.id.et_gaodemaps_end);
		mBusResultList = (ListView) findViewById(R.id.bus_result_list);
		mBusResultLayout = (LinearLayout) findViewById(R.id.bus_result);

		mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
		mRotueTimeDes = (TextView) findViewById(R.id.firstline);
		mRouteDetailDes = (TextView) findViewById(R.id.secondline);

		et_gaodemaps_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String newText = s.toString().trim();
				if (!AMapUtil.IsEmptyOrNullString(newText)) {
					InputtipsQuery inputquery = new InputtipsQuery(newText, city);
					Inputtips inputTips = new Inputtips(GaodeMapActivity.this, inputquery);
					inputTips.setInputtipsListener(GaodeMapActivity.this);
					inputTips.requestInputtipsAsyn();
				} else {
					lv_gaodemaps.setVisibility(View.GONE);
				}
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
		lv_gaodemaps.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String search = ((TextView) view).getText().toString();
				et_gaodemaps_search.setText(search);
				et_gaodemaps_search.setSelection(search.length());
				aAdapter.clear();
				lv_gaodemaps.setVisibility(View.GONE);
			}
		});
		rg_gaodemaps.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_gaodemaps_bus:
					routeType = 1;
					break;
				case R.id.rb_gaodemaps_drive:
					routeType = 0;
					break;
				case R.id.rb_gaodemaps_walk:
					routeType = 2;
					break;
				}
			}
		});

		aAdapter = new ArrayAdapter<String>(this, R.layout.item_text_black, listString);
		lv_gaodemaps.setAdapter(aAdapter);

		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(new MyGeocodeSearchListener(0));
		geocoderSearchEnd = new GeocodeSearch(this);
		geocoderSearchEnd.setOnGeocodeSearchListener(new MyGeocodeSearchListener(1));
		mRouteSearch = new RouteSearch(this);
		mRouteSearch.setRouteSearchListener(this);

		dialogUtil = new DialogUtil(this);
	}

	/**
	 * 搜索
	 * 
	 * @param view
	 */
	public void search(View view) {
		String search = et_gaodemaps_search.getText().toString();
		if (!AMapUtil.IsEmptyOrNullString(search)) {
			aMap.setOnMarkerClickListener(new OnMarkerClickListener() {

				@Override
				public boolean onMarkerClick(Marker marker) {
					// TODO Auto-generated method stub
					marker.showInfoWindow();
					return false;
				}
			});
			aMap.setInfoWindowAdapter(this);
			aMap.setLocationSource(null);
			dialogUtil.dialogLoading("正在搜索……");
			// 第一个参数表示搜索字符串，
			// 第二个参数表示poi搜索类型，
			// 第三个参数表示poi搜索区域（空字符串代表全国）
			query = new PoiSearch.Query(search, "", city);
			query.setPageSize(10);// 设置每页最多返回多少条poiitem
			query.setPageNum(0);// 设置查第一页

			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);
			poiSearch.searchPOIAsyn();
		} else {
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "请输入位置、公交站、地铁站");
		}
	}

	/**
	 * 搜索线路
	 * 
	 * @param view
	 */
	public void searchLine(View view) {
		aMap.setOnMarkerClickListener(this);
		String start = et_gaodemaps_origin.getText().toString();
		String end = et_gaodemaps_end.getText().toString();
		if (start != null && !start.equals("")) {
			if (end != null && !end.equals("")) {
				mStartPoint = null;
				mEndPoint = null;
				dialogUtil.dialogLoading("正在搜索线路…");
				if (!start.equals(currenPosition)) {
					// 第一个参数表示地址，
					// 第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
					GeocodeQuery query = new GeocodeQuery(start, city);
					// 设置同步地理编码请求
					geocoderSearch.getFromLocationNameAsyn(query);
				}
				GeocodeQuery queryEnd = new GeocodeQuery(end, city);
				geocoderSearchEnd.getFromLocationNameAsyn(queryEnd);
			} else {
				ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "请输入终点！");
			}
		} else {
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "请输入起点！");
		}
	}

	/**
	 * 导航
	 * 
	 * @param view
	 */
	public void gaodePath(View view) {
		if (isShowPath) {
			isShowPath = false;
			rg_gaodemaps.check(R.id.rb_gaodemaps_drive);
			rg_gaodemaps.setVisibility(View.VISIBLE);
			ll_gaodemaps_path.setVisibility(View.VISIBLE);
			et_gaodemaps_origin.setText(currenPosition);
			((ImageView) view).setImageResource(R.drawable.gaodemap_canel);
		} else {
			isShowPath = true;
			et_gaodemaps_end.setText("");
			rg_gaodemaps.clearCheck();
			rg_gaodemaps.setVisibility(View.GONE);
			ll_gaodemaps_path.setVisibility(View.GONE);
			((ImageView) view).setImageResource(R.drawable.gaodemap_line);
		}
	}

	/**
	 * 路况
	 * 
	 * @param view
	 */
	public void roadCondition(View view) {
		CheckBox rc = (CheckBox) view;
		aMap.setTrafficEnabled(rc.isChecked());
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mv_gaodemaps.onPause();
		deactivate();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mv_gaodemaps.onDestroy();
		if (null != mlocationClient) {
			mlocationClient.onDestroy();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mv_gaodemaps.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mv_gaodemaps.onSaveInstanceState(outState);
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		// 激活定位
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			// 设置定位监听
			mlocationClient.setLocationListener(this);
			// 设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			// 设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		// 停止定位
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		// 定位成功后回调函数
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null && amapLocation.getErrorCode() == 0) {
				// 显示系统小蓝点
				mListener.onLocationChanged(amapLocation);
				currenPosition = amapLocation.getDistrict() + amapLocation.getStreet() + amapLocation.getStreetNum();
				city = amapLocation.getCity();
				mStartPoint.setLatitude(amapLocation.getLatitude());
				mStartPoint.setLongitude(amapLocation.getLongitude());
			} else {
				if(isFirst){
					String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
					Log.getInstance().writeLog("高德地图定位失败：" + errText);
					ToastView.showToast(GaodeMapActivity.this, R.drawable.ic_listselector, 
							R.drawable.ic_toast_flag_verbose,
							errText, Toast.LENGTH_SHORT);
					isFirst = false;
				}
			}
		}
	}

	private class MyGeocodeSearchListener implements OnGeocodeSearchListener {

		int flag = 0;

		/**
		 * @param flag
		 *            0 起点坐标 <br/>
		 *            1 终点坐标
		 */
		public MyGeocodeSearchListener(int flag) {
			this.flag = flag;
		}

		@Override
		public void onGeocodeSearched(GeocodeResult result, int rCode) {
			// TODO Auto-generated method stub
			dialogUtil.hideDialogLoading();
			if (rCode == 1000) {
				if (result != null && result.getGeocodeAddressList() != null
						&& result.getGeocodeAddressList().size() > 0) {
					GeocodeAddress address = result.getGeocodeAddressList().get(0);
					aMap.animateCamera(
							CameraUpdateFactory.newLatLngZoom(AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
					switch (flag) {
					case 0:
						mStartPoint = address.getLatLonPoint();
						aMap.addMarker(new MarkerOptions().position(AMapUtil.convertToLatLng(mStartPoint))
								.icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
						drawLine();
						break;
					case 1:
						mEndPoint = address.getLatLonPoint();
						aMap.addMarker(new MarkerOptions().position(AMapUtil.convertToLatLng(mEndPoint))
								.icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
						drawLine();
						break;
					}
				} else {
					dialogUtil.hideDialogLoading();
					ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose, "没有搜索到相关数据！");
				}
			} else {
				dialogUtil.hideDialogLoading();
				ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose,
						"搜索出错，错误编号：" + rCode);
			}
		}

		@Override
		public void onRegeocodeSearched(RegeocodeResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		private void drawLine() {
			if (mStartPoint != null && mEndPoint != null) {
				RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);
				if (routeType == 0) {
					// 第一个参数表示路径规划的起点和终点，
					// 第二个参数表示驾车模式，
					// 第三个参数表示途经点，
					// 第四个参数表示避让区域，
					// 第五个参数表示避让道路
					DriveRouteQuery query = new DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null, null, "");
					// 异步路径规划驾车模式查询
					mRouteSearch.calculateDriveRouteAsyn(query);
				} else if (routeType == 1) {
					// 第一个参数表示路径规划的起点和终点，
					// 第二个参数表示公交查询模式，
					// 第三个参数表示公交查询城市区号，
					// 第四个参数表示是否计算夜班车，0表示不计算
					BusRouteQuery query = new BusRouteQuery(fromAndTo, RouteSearch.BusDefault, city, 0);
					// 异步路径规划公交模式查询
					mRouteSearch.calculateBusRouteAsyn(query);
				} else if (routeType == 2) {
					WalkRouteQuery query = new WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
					// 异步路径规划步行模式查询
					mRouteSearch.calculateWalkRouteAsyn(query);
				}
			}
		}
	}

	@Override
	public void onBusRouteSearched(BusRouteResult result, int errorCode) {
		// TODO Auto-generated method stub
		dialogUtil.hideDialogLoading();
		mBusResultLayout.setVisibility(View.VISIBLE);
		mBottomLayout.setVisibility(View.GONE);
		aMap.clear();// 清理地图上的所有覆盖物
		if (errorCode == 1000) {
			if (result != null && result.getPaths() != null) {
				if (result.getPaths().size() > 0) {
					mBusRouteResult = result;
					BusResultListAdapter mBusResultListAdapter = new BusResultListAdapter(GaodeMapActivity.this,
							mBusRouteResult);
					mBusResultList.setAdapter(mBusResultListAdapter);
				} else if (result != null && result.getPaths() == null) {
					ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose, "没有搜索到相关数据！");
				}
			} else {
				ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose, "没有搜索到相关数据！");
			}
		} else {
			ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose,
					"搜索出错，错误编号：" + errorCode);
		}
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
		// TODO Auto-generated method stub
		dialogUtil.hideDialogLoading();
		aMap.clear();// 清理地图上的所有覆盖物
		if (errorCode == 1000) {
			if (result != null && result.getPaths() != null) {
				if (result.getPaths().size() > 0) {
					mDriveRouteResult = result;
					final DrivePath drivePath = mDriveRouteResult.getPaths().get(0);
					DriveRouteColorfulOverLay drivingRouteOverlay = new DriveRouteColorfulOverLay(aMap, drivePath,
							mDriveRouteResult.getStartPos(), mDriveRouteResult.getTargetPos(), null);
					drivingRouteOverlay.setNodeIconVisibility(false);// 设置节点marker是否显示
					drivingRouteOverlay.setIsColorfulline(true);// 是否用颜色展示交通拥堵情况，默认true
					drivingRouteOverlay.removeFromMap();
					drivingRouteOverlay.addToMap();
					drivingRouteOverlay.zoomToSpan();
					mBottomLayout.setVisibility(View.VISIBLE);
					int dis = (int) drivePath.getDistance();
					int dur = (int) drivePath.getDuration();
					String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
					mRotueTimeDes.setText(des);
					mRouteDetailDes.setVisibility(View.VISIBLE);
					int taxiCost = (int) mDriveRouteResult.getTaxiCost();
					mRouteDetailDes.setText("打车约" + taxiCost + "元");
					mBottomLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(GaodeMapActivity.this, DriveRouteDetailActivity.class);
							intent.putExtra("drive_path", drivePath);
							intent.putExtra("drive_result", mDriveRouteResult);
							startActivity(intent);
						}
					});
				} else if (result != null && result.getPaths() == null) {
					ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose, "没有搜索到相关数据！");
				}

			} else {
				ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose, "没有搜索到相关数据！");
			}
		} else {
			ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose,
					"搜索出错，错误编号：" + errorCode);
		}
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
		// TODO Auto-generated method stub
		dialogUtil.hideDialogLoading();
		aMap.clear();// 清理地图上的所有覆盖物
		if (errorCode == 1000) {
			if (result != null && result.getPaths() != null) {
				if (result.getPaths().size() > 0) {
					mWalkRouteResult = result;
					final WalkPath walkPath = mWalkRouteResult.getPaths().get(0);
					WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this, aMap, walkPath,
							mWalkRouteResult.getStartPos(), mWalkRouteResult.getTargetPos());
					walkRouteOverlay.removeFromMap();
					walkRouteOverlay.addToMap();
					walkRouteOverlay.zoomToSpan();
					mBottomLayout.setVisibility(View.VISIBLE);
					int dis = (int) walkPath.getDistance();
					int dur = (int) walkPath.getDuration();
					String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
					mRotueTimeDes.setText(des);
					mRouteDetailDes.setVisibility(View.GONE);
					mBottomLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(GaodeMapActivity.this, WalkRouteDetailActivity.class);
							intent.putExtra("walk_path", walkPath);
							intent.putExtra("walk_result", mWalkRouteResult);
							startActivity(intent);
						}
					});
				} else if (result != null && result.getPaths() == null) {
					ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose, "没有搜索到相关数据！");
				}
			} else {
				ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose, "没有搜索到相关数据！");
			}
		} else {
			ToastView.showWhiteContentToast(GaodeMapActivity.this, R.drawable.ic_toast_flag_verbose,
					"搜索出错，错误编号：" + errorCode);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		aMap.setLocationSource(this);
		if (mBusResultLayout.getVisibility() == View.VISIBLE) {
			mBusResultLayout.setVisibility(View.GONE);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onGetInputtips(List<Tip> tipList, int rCode) {
		// TODO Auto-generated method stub
		if (rCode == 1000) {// 正确返回
			listString.clear();
			for (int i = 0; i < tipList.size(); i++) {
				listString.add(tipList.get(i).getName());
			}
			if (listString.size() > 1) {
				lv_gaodemaps.setVisibility(View.VISIBLE);
				aAdapter.notifyDataSetChanged();
			} else {
				lv_gaodemaps.setVisibility(View.GONE);
			}
		} else {
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "关键字搜索错误，错误编号：" + rCode);
		}
	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(final Marker marker) {
		// TODO Auto-generated method stub
		View view = getLayoutInflater().inflate(R.layout.layout_poikeywordsearch_uri, null);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(marker.getTitle());

		TextView snippet = (TextView) view.findViewById(R.id.snippet);
		snippet.setText(marker.getSnippet());
		ImageButton button = (ImageButton) view.findViewById(R.id.start_amap_app);
		// 调起高德地图app
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAMapNavi(marker);
			}
		});
		return view;
	}

	/**
	 * 调起高德地图导航功能，如果没安装高德地图，会进入异常，可以在异常中处理，调起高德地图app的下载页面
	 */
	public void startAMapNavi(Marker marker) {
		// 构造导航参数
		NaviPara naviPara = new NaviPara();
		// 设置终点位置
		naviPara.setTargetPoint(marker.getPosition());
		// 设置导航策略，这里是避免拥堵
		naviPara.setNaviStyle(NaviPara.DRIVING_AVOID_CONGESTION);

		// 调起高德地图导航
		try {
			AMapUtils.openAMapNavi(naviPara, getApplicationContext());
		} catch (AMapException e) {

			// 如果没安装会进入异常，调起下载页面
			AMapUtils.getLatestAMapApp(getApplicationContext());

		}

	}

	@Override
	public void onPoiItemSearched(PoiItem arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		// TODO Auto-generated method stub
		dialogUtil.hideDialogLoading();
		if (rCode == 1000) {
			if (result != null && result.getQuery() != null) {
				// 搜索poi的结果
				if (result.getQuery().equals(query)) {
					// 是否是同一条
					poiResult = result;
					// 取得搜索到的poiitems有多少页
					// 取得第一页的poiitem数据，页数从数字0开始
					List<PoiItem> poiItems = poiResult.getPois();
					// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
					List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
					if (poiItems != null && poiItems.size() > 0) {
						aMap.clear();// 清理之前的图标
						PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
						poiOverlay.removeFromMap();
						poiOverlay.addToMap();
						poiOverlay.zoomToSpan();
					} else if (suggestionCities != null && suggestionCities.size() > 0) {
						showSuggestCity(suggestionCities);
					} else {
						ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "没有搜索到相关数据！");
					}
				}
			} else {
				ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "没有搜索到相关数据！");
			}
		} else {
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, "搜索错误，错误编号：" + rCode);
		}
	}

	/**
	 * poi没有搜索到数据，返回一些推荐城市的信息
	 */
	private void showSuggestCity(List<SuggestionCity> cities) {
		String infomation = "推荐城市\n";
		for (int i = 0; i < cities.size(); i++) {
			infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:" + cities.get(i).getCityCode() + "城市编码:"
					+ cities.get(i).getAdCode() + "\n";
		}
		ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, infomation);
	}

}
