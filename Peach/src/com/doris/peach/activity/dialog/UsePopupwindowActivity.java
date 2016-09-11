package com.doris.peach.activity.dialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.activity.DialogIntentActivity;
import com.doris.peachlibrary.dialog.Interface.DialogItemClickListener;
import com.doris.peachlibrary.util.BitmapUtil;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.dialog.ChoosePicturePopupWindow;
import com.doris.peachlibrary.view.dialog.MenuPopupWindow;
import com.doris.peachlibrary.view.dialog.SpinnerPopupWindow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年4月22日
 */
public class UsePopupwindowActivity extends BaseActivity {

	private LinearLayout ll_dialog_popupwindow;
	private ImageView iv_dialog_popupwindow;

	private List<String> titles;
	private List<List<String>> item_names;
	private List<List<Integer>> item_images;
	private MenuPopupWindow menu;

	private ChoosePicturePopupWindow choosePic;
	private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/dotOrderImage");
	private File tempFile;
	private File finalfile;
	private String temppath;
	private Uri tempuri;
	// 用来标识请求照相功能
	private static final int CAMERA_WITH_DATA = 50;
	// 用来标识请求gallery
	private static final int PHOTO_PICKED_WITH_DATA = 60;
	// 用来标识裁剪的返回
	private static final int CUT_PHOTO = 70;
	private OnClickListener itemsOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			choosePic.dismiss();
			Intent intent = null;
			switch (v.getId()) {
			case R.id.b_dp_take_photo:
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 下面这句指定调用相机拍照后的照片存储的路径
				if (!PHOTO_DIR.exists()) {
					PHOTO_DIR.mkdirs();// 创建照片的存储目录
				}
				tempFile = new File(PHOTO_DIR, getPhotoFileName());
				temppath = tempFile.getAbsolutePath();
				tempuri = Uri.fromFile(tempFile);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
				startActivityForResult(intent, CAMERA_WITH_DATA);
				break;
			case R.id.b_dp_pick_photo:
				intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
				break;
			}
		}
	};
	
	private SpinnerPopupWindow spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_usepopupwindow);

		ll_dialog_popupwindow = (LinearLayout) findViewById(R.id.ll_dialog_popupwindow);
		iv_dialog_popupwindow = (ImageView) findViewById(R.id.iv_dialog_popupwindow);

		initMenuBarInfo();
		menu = new MenuPopupWindow(this, titles, item_names, item_images);
		spinner = new SpinnerPopupWindow(this);
		choosePic = new ChoosePicturePopupWindow(this, itemsOnClick, 
				iv_dialog_popupwindow);
	}
	
	public void imitationWeChat(View view){
		DialogIntentActivity.goActivity(4, this);
	}
	
	public void spinner(View view){
		if(spinner.isShowing())
			return;
		
		ListView lv_spinner = spinner.getLv_spinner();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.item_text_black,
				new String[] { "选项1", "选项2", "选项3", "选项4",
						"选项5", "选项6" });
		lv_spinner.setAdapter(adapter);
		lv_spinner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, 
					View view, int position, long id) {
				// TODO Auto-generated method stub
				ToastView.showWhiteContentToast(UsePopupwindowActivity.this,
						R.drawable.ic_toast_flag_ok, 
						((TextView)view).getText().toString());
				spinner.dismiss();
			}
		});
		spinner.showAsDropDown(view);
		spinner.setOutsideTouchable(true);
	}

	public void choosePicture(View view) {
		if(choosePic.isShowing()){
			return;
		}
		choosePic.showAtLocation(ll_dialog_popupwindow, Gravity.BOTTOM, 0, 0);
	}

	public void customMenuBar(View view) {
		if (menu.isShowing()) {
			return;
		}
//		menu.setAnimationStyle(R.style.dialogAnim);
		menu.showAtLocation(ll_dialog_popupwindow, Gravity.BOTTOM, 0, 0);
		menu.setListener(new DialogItemClickListener() {

			@Override
			public void onItemClickListener(int currentIndex, int position) {
				// TODO Auto-generated method stub
				ToastView.showWhiteContentToast(UsePopupwindowActivity.this, 
						R.drawable.ic_toast_flag_verbose, item_names.get(currentIndex).get(position));
			}
		});
		menu.setOutsideTouchable(true);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CUT_PHOTO);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			finalfile = new File(PHOTO_DIR, getPhotoFileName());
			BitmapUtil.saveImg(photo, finalfile);
			Drawable drawable = new BitmapDrawable(photo);
			iv_dialog_popupwindow.setImageDrawable(drawable);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case PHOTO_PICKED_WITH_DATA:
			if (data != null && data.getData() != null) {
				startPhotoZoom(data.getData());
			}
			break;
		// 如果是调用相机拍照时
		case CAMERA_WITH_DATA:
			if (resultCode == 0) {
				return;
			}
			startPhotoZoom(tempuri);
			break;
		// 取得裁剪后的图片
		case CUT_PHOTO:
			if (data != null) {
				setPicToView(data);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		System.out.println("onSaveInstanceState");
		outState.putString("temppath", temppath);
		outState.putSerializable("finalfile", finalfile);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("onRestoreInstanceState");
		temppath = savedInstanceState.getString("temppath");
		finalfile = (File) savedInstanceState.getSerializable("finalfile");
		super.onRestoreInstanceState(savedInstanceState);
	}

	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
 
	private void initMenuBarInfo() {
		titles = addItems(new String[] { "常用", "设置", "工具" });
		item_names = new ArrayList<List<String>>();
		item_names.add(addItems(new String[] { "电话", "相机", "复制", "裁剪", "剪切", "删除", "下载", "编辑" }));
		item_names.add(addItems(new String[] { "邮件", "全屏", "帮助", "收藏", "地图", "语音", "图片", "定位" }));
		item_names.add(addItems(new String[] { "刷新", "保存", "搜索", "分享", "切换", "录像", "浏览器", "旋转屏幕" }));
		item_images = new ArrayList<List<Integer>>();
		item_images.add(addItems(new Integer[] { R.drawable.ic_action_call, R.drawable.ic_action_camera,
				R.drawable.ic_action_copy, R.drawable.ic_action_crop, R.drawable.ic_action_cut,
				R.drawable.ic_action_discard, R.drawable.ic_action_download, R.drawable.ic_action_edit }));
		item_images.add(addItems(new Integer[] { R.drawable.ic_action_email, R.drawable.ic_action_full_screen,
				R.drawable.ic_action_help, R.drawable.ic_action_important, R.drawable.ic_action_map,
				R.drawable.ic_action_mic, R.drawable.ic_action_picture, R.drawable.ic_action_place }));
		item_images.add(addItems(new Integer[] { R.drawable.ic_action_refresh, R.drawable.ic_action_save,
				R.drawable.ic_action_search, R.drawable.ic_action_share, R.drawable.ic_action_switch_camera,
				R.drawable.ic_action_video, R.drawable.ic_action_web_site, R.drawable.ic_action_screen_rotation }));
	}

	private List<Integer> addItems(Integer[] values) {
		List<Integer> list = new ArrayList<Integer>();
		for (Integer var : values) {
			list.add(var);
		}

		return list;
	}

	private List<String> addItems(String[] values) {
		List<String> list = new ArrayList<String>();
		for (String var : values) {
			list.add(var);
		}

		return list;
	}
}
