package com.doris.peach.activity.bluetooth;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.Log;
import com.doris.peachlibrary.view.PeachSwitchButton;
import com.doris.peachlibrary.view.PeachSwitchButton.OnSwitchChangedListener;
import com.doris.peachlibrary.view.ToastView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年7月4日
 */
public class BluetoothBaseActivity extends BaseActivity {
	
	private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

	private PeachSwitchButton psb_bluetoothbase;
	private TextView tv_connect_bluetooth, tv_search_bluetooth, tv_bluetoothbase_my, tv_bluetoothbase_refresh;
	private ListView lv_bluetoothbase_connect, lv_bluetoothbase_search;
	private RelativeLayout rl_search_bluetooth;
	private ProgressBar pb_search_bluetooth;
	
	private BluetoothAdapter adapter;
	private BluetoothSocket socket;
	private Set<BluetoothDevice> bluetoothDevices;
	private List<String> searchDevices = new ArrayList<String>();
	private List<BluetoothDevice> searchBluetoothDevices = new ArrayList<BluetoothDevice>();
	private ArrayAdapter<String> searchs;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(android.content.Context context, Intent intent) {
			String action = intent.getAction();
			// 获得已经搜索到的蓝牙设备
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// 搜索到的不是已经绑定的蓝牙设备
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					// 显示
					searchDevices.add(device.getName());
					searchBluetoothDevices.add(device);
					searchs.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(lv_bluetoothbase_search);
				}
				// 搜索完成
			} else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
				tv_search_bluetooth.setText("附近蓝牙：");
				pb_search_bluetooth.setVisibility(View.GONE);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth_base);

		tv_bluetoothbase_my = (TextView) findViewById(R.id.tv_bluetoothbase_my);
		psb_bluetoothbase = (PeachSwitchButton) findViewById(R.id.psb_bluetoothbase);
		tv_connect_bluetooth = (TextView) findViewById(R.id.tv_connect_bluetooth);
		lv_bluetoothbase_connect = (ListView) findViewById(R.id.lv_bluetoothbase_connect);
		rl_search_bluetooth = (RelativeLayout) findViewById(R.id.rl_search_bluetooth);
		tv_search_bluetooth = (TextView) findViewById(R.id.tv_search_bluetooth);
		pb_search_bluetooth = (ProgressBar) findViewById(R.id.pb_search_bluetooth);
		lv_bluetoothbase_search = (ListView) findViewById(R.id.lv_bluetoothbase_search);
		tv_bluetoothbase_refresh = (TextView) findViewById(R.id.tv_bluetoothbase_refresh);

		adapter = BluetoothAdapter.getDefaultAdapter();
		searchs = new ArrayAdapter<>(this, R.layout.item_text, searchDevices);
		lv_bluetoothbase_search.setAdapter(searchs);
		String address = Settings.Secure.getString(getContentResolver(), "bluetooth_address");
		if(TextUtils.isEmpty(address)){
			address = adapter.getAddress();
		}
		tv_bluetoothbase_my.setText(adapter.getName() + "\n\n" + address);
		tv_bluetoothbase_refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchBluetooth();
			}
		});
		psb_bluetoothbase.setChecked(adapter.isEnabled());
		psb_bluetoothbase.setOnChangeListener(new OnSwitchChangedListener() {
			
			@Override
			public void onSwitchChange(PeachSwitchButton switchView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					// 打开蓝牙
					startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
				}else{
					// 关闭蓝牙
					adapter.disable();
					isCloseBluetoothDoThis();
				}
			}
		});
		lv_bluetoothbase_search.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if(adapter.isDiscovering())
					adapter.cancelDiscovery();
				BluetoothDevice device = searchBluetoothDevices.get(position);
				try {
					if(device.getBondState() == BluetoothDevice.BOND_NONE){
						// 利用反射调用：BluetoothDevice.createBond(BluetoothDevice remoteDevice);
						Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
						createBondMethod.invoke(device);
					}else if(device.getBondState() == BluetoothDevice.BOND_BONDED){
						connect(device);
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.getInstance().writeLog("选择蓝牙设备出错：", e);
					ToastView.showWhiteContentToast(BluetoothBaseActivity.this,
							R.drawable.ic_toast_flag_verbose, "选择蓝牙设备出错！");
				}
			}
		});
		if(adapter.isEnabled()){
			isOpenBluetoothDoThis();
		}else{
			isCloseBluetoothDoThis();
		}
	}
	
	private void getConnectBluetooth() {
		bluetoothDevices = adapter.getBondedDevices();
		List<String> list = new ArrayList<String>();
		for (BluetoothDevice bd : bluetoothDevices) {
			list.add(bd.getName());
		}
		if(list.size() <= 0){
			list.add("无");
		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_text, list);
		lv_bluetoothbase_connect.setAdapter(arrayAdapter);
		setListViewHeightBasedOnChildren(lv_bluetoothbase_connect);
	}

	
	private void searchBluetooth() {
		searchDevices.clear();
		searchs.notifyDataSetChanged();
		setListViewHeightBasedOnChildren(lv_bluetoothbase_search);
		tv_search_bluetooth.setText("正在搜索附近蓝牙");
		pb_search_bluetooth.setVisibility(View.VISIBLE);
		//如果正在搜索，先取消搜索
		if(adapter.isDiscovering()){
			adapter.cancelDiscovery();
		}
		//搜索
		adapter.startDiscovery();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(receiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){
			if(resultCode == RESULT_OK){
				isOpenBluetoothDoThis();
			}
		}
	}
	
	private void isOpenBluetoothDoThis(){
		startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), 0);
		tv_connect_bluetooth.setVisibility(View.VISIBLE);
		lv_bluetoothbase_connect.setVisibility(View.VISIBLE);
		rl_search_bluetooth.setVisibility(View.VISIBLE);
		lv_bluetoothbase_search.setVisibility(View.VISIBLE);
		tv_bluetoothbase_refresh.setVisibility(View.VISIBLE);
		getConnectBluetooth();
		searchBluetooth();
	}
	
	private void isCloseBluetoothDoThis(){
		tv_connect_bluetooth.setVisibility(View.GONE);
		lv_bluetoothbase_connect.setVisibility(View.GONE);
		rl_search_bluetooth.setVisibility(View.GONE);
		lv_bluetoothbase_search.setVisibility(View.GONE);
		tv_bluetoothbase_refresh.setVisibility(View.GONE);
	}
	
	private void connect(BluetoothDevice device){
		UUID uuid = UUID.fromString(SPP_UUID);
		try {
			socket = device.createRfcommSocketToServiceRecord(uuid);
			//开始连接
			socket.connect();
		} catch (Exception e) {
			// TODO: handle exception
			Log.getInstance().writeLog("连接蓝牙错误：", e);
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, 
					"连接蓝牙出错！");
		}
	}
	
	/**
	 * 设置ListView高度 <br/>
	 * ListView与ScrollView共存 <br/>
	 * @param listview
	 */
	public static void setListViewHeightBasedOnChildren(ListView listview){
		if(listview == null){
			return;
		}
		
		ListAdapter adapter = listview.getAdapter();
		if(adapter == null){
			return;
		}
		
		int totalHeight = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View listItem = adapter.getView(i, null, listview);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams params = listview.getLayoutParams();
		params.height = totalHeight + (listview.getDividerHeight() * (adapter.getCount() - 1));
		listview.setLayoutParams(params);
	}
}
