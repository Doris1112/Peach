package com.doris.peachlauncherscene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.doris.peachlauncherscene.application.SceneLauncherApplication;
import com.doris.peachlauncherscene.bean.AnalogClockInfo;
import com.doris.peachlauncherscene.bean.ApplicationInfo;
import com.doris.peachlauncherscene.bean.CalendarInfo;
import com.doris.peachlauncherscene.bean.FrameAnimInfo;
import com.doris.peachlauncherscene.bean.NumberClockInfo;
import com.doris.peachlauncherscene.bean.SceneHotseatInfo;
import com.doris.peachlauncherscene.bean.ScenePageInfo;
import com.doris.peachlauncherscene.bean.ShortcutInfo;
import com.doris.peachlauncherscene.util.SceneLayoutXmlParser;
import com.doris.peachlauncherscene.view.AnalogClock;
import com.doris.peachlauncherscene.view.Calendar;
import com.doris.peachlauncherscene.view.NumberClock;
import com.doris.peachlauncherscene.view.SceneAllAppsPagedView;
import com.doris.peachlauncherscene.view.SceneWorkspace;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 * 2016年8月11日
 */
public class LauncherScene extends Activity{
	
	private final static int MENU_MAINMENU = Menu.FIRST + 1;
	private final static int MENU_SETTING = MENU_MAINMENU + 1;

	private final static String TYPE_IMAGEBUTTON = "ImageButton";
	private final static String TYPE_MAINMENUBUTTON = "MainmenuButton";
	private final static String TYPE_ANALOGCLOCK = "AnalogClock";
	private final static String TYPE_ANIMVIEW = "AnimView";
	private final static String TYPE_CALENDAR = "Calendar";
	private final static String TYPE_NUMBERCLOCK = "NumberClock";

	private ViewGroup mSceneRoot;
	private ViewGroup mSceneAllAppsLayout;
	private SceneAllAppsPagedView mSceneAllApps;
	private SceneLauncherApplication mSceneLauncherApplication;

	private List<ImageView> mAnimViews;
	
	private ArrayList<ApplicationInfo> systemApp;
	private ArrayList<ApplicationInfo> allApps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher_secene);
		mSceneLauncherApplication = (SceneLauncherApplication) getApplication();
		mSceneLauncherApplication.setLauncher(this);
		String scenePath = getIntent().getStringExtra("scene_path");
		if (scenePath != null) {
			mSceneLauncherApplication.setSelectScenePath(scenePath);
		}
		initAllViews();
		loadAllViews();
		bindAllapps();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		hideAllapps();
		String scenePath = intent.getStringExtra("scene_path");
		if (scenePath != null
				&& !scenePath.equals(mSceneLauncherApplication
						.getSelectScenePath())) {
			mSceneLauncherApplication.setSelectScenePath(scenePath);
			mSceneLauncherApplication.resetScenePageInfoList();
			mSceneLauncherApplication.resetHotseatInfo();
			mSceneRoot.removeAllViews();
			loadAllViews();
			bindAllapps();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		Intent settings = new Intent(android.provider.Settings.ACTION_SETTINGS);
		settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		menu.add(0, MENU_MAINMENU, 0, R.string.menu_mainmenu);
		menu.add(0, MENU_SETTING, 0, R.string.menu_settings)
				.setIntent(settings);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (isAllappsVisible())
			return false;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_MAINMENU:
			showAllapps();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			animViewStart();
		} else {
			animViewStop();
		}
	}

	@Override
	public void onBackPressed() {
		if (isAllappsVisible()) {
			hideAllapps();
		}
	}

	/**
	 *  加载主菜单
	 */
	public void bindAllapps() {
		allApps = mSceneLauncherApplication.getAllApps();
		systemApp = SceneLauncherApplication.systemApp;
		mSceneAllApps.setApps(allApps);
	}

	public void onAppsItemClick(View v) {
		final ApplicationInfo info = (ApplicationInfo) v.getTag();
		if (info != null) {
			mSceneLauncherApplication.startActivitySafely(info.intent);
		}
	}

	private void initAllViews() {
		mSceneRoot = (ViewGroup) findViewById(R.id.scene_root);
	}

	/**
	 *  加载应用快捷图标
	 */
	private void loadAllViews() {
		loadScenePages();
		loadSceneHotseat();
		loadMainmenu();
	}

	/**
	 * 加载场景页面
	 */
	private void loadScenePages() {
		if (mAnimViews == null) {
			mAnimViews = new ArrayList<ImageView>();
		} else {
			mAnimViews.clear();
		}
		List<ScenePageInfo> scenePages = mSceneLauncherApplication
				.getSenePageInfoList();
		if (scenePages != null && !scenePages.isEmpty()) {
			SceneWorkspace sceneWorkspace = new SceneWorkspace(this);
			mSceneRoot.addView(sceneWorkspace);
			for (ScenePageInfo page : scenePages) {
				FrameLayout layout = new FrameLayout(this);
				FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);
				layout.setBackground(getOrignShorcutDrawable(page.backgroundPath));
				sceneWorkspace.addView(layout, layoutParams);
				loadSceneShortcut(layout, page);
			}
		}
	}

	// 加载场景桌面hotseat
	private void loadSceneHotseat() {
		SceneHotseatInfo hotseat = mSceneLauncherApplication.getHotseatInfo();
		if (hotseat != null) {
			ImageView hotseatView = new ImageView(this);
			hotseatView.setBackground(getShortDrawable(hotseat.backgroundPath));
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(getAbsolutePosX(hotseat.x),
					getAbsolutePosY(hotseat.y), 0, 0);
			mSceneRoot.addView(hotseatView, layoutParams);
			if (hotseat.hotseats != null) {
				for (ShortcutInfo seatInfo : hotseat.hotseats) {
					View v = createView(seatInfo);
					layoutParams = new FrameLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
					layoutParams.setMargins(getAbsolutePosX(seatInfo.x),
							getAbsolutePosY(seatInfo.y), 0, 0);
					mSceneRoot.addView(v, layoutParams);
				}
			}
		}
	}

	// 加载场景上的shortcut
	private void loadSceneShortcut(ViewGroup layout, ScenePageInfo info) {
		if (layout == null || info == null || info.children == null)
			return;
		for (ShortcutInfo shortcut : info.children) {
			View v = createView(shortcut);
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(getAbsolutePosX(shortcut.x),
					getAbsolutePosY(shortcut.y), 0, 0);
			layout.addView(v, layoutParams);
		}
	}

	private View createView(final ShortcutInfo info) {
		View view = null;
		if (info.type.equals(TYPE_IMAGEBUTTON)
				|| info.type.equals(TYPE_MAINMENUBUTTON)) {
			view = new ImageButton(this);
			view.setBackground(getShortDrawable(info.backgroundPath));
		} else if (info.type.equals(TYPE_ANALOGCLOCK)) {
			AnalogClockInfo analogClockInfo = (AnalogClockInfo) info;
			Drawable dial = getShortDrawable(analogClockInfo.backgroundPath);
			Drawable hourHand = getShortDrawable(analogClockInfo.handHour);
			Drawable minuteHand = getShortDrawable(analogClockInfo.handMinute);
			view = new AnalogClock(this, dial, hourHand, minuteHand);
		} else if (info.type.equals(TYPE_NUMBERCLOCK)) {
			NumberClockInfo numberClockInfo = (NumberClockInfo) info;
			Drawable timeBg = getShortDrawable(numberClockInfo.backgroundPath);
			Drawable timeColon = getShortDrawable(numberClockInfo.timeColon);
			List<Drawable> dTimeArray = new ArrayList<Drawable>();
			if (numberClockInfo.timePic != null
					&& numberClockInfo.timePic.length != 0) {
				for (String imageName : numberClockInfo.timePic) {
					dTimeArray.add(getShortDrawable(imageName));
				}
			}
			List<Drawable> dApmArray = new ArrayList<Drawable>();
			if (numberClockInfo.timeApm != null
					&& numberClockInfo.timeApm.length != 0) {
				for (String imageName : numberClockInfo.timeApm) {
					dApmArray.add(getShortDrawable(imageName));
				}
			}
			view = new NumberClock(this, timeBg, timeColon, dTimeArray,
					dApmArray);
		} else if (info.type.equals(TYPE_ANIMVIEW)) {
			FrameAnimInfo animInfo = (FrameAnimInfo) info;
			if (animInfo.anim != null && animInfo.anim.length != 0) {
				AnimationDrawable animDrawable = new AnimationDrawable();
				animDrawable.setOneShot(false);
				for (String imageName : animInfo.anim) {
					Drawable d = getShortDrawable(imageName);
					if (d != null)
						animDrawable.addFrame(d, 300);
				}
				view = new ImageView(this);
				view.setBackground(animDrawable);
				mAnimViews.add((ImageView) view);
			}
		} else if (info.type.equals(TYPE_CALENDAR)) {
			CalendarInfo calendarInfo = (CalendarInfo) info;
			Drawable caleBg = getShortDrawable(calendarInfo.backgroundPath);

			Drawable caleDot = getShortDrawable(calendarInfo.bmp_dot);
			Point posYear = new Point();
			posYear = calendarInfo.point_year;
			List<Drawable> dYearArray = new ArrayList<Drawable>();
			if (calendarInfo.bmp_year != null
					&& calendarInfo.bmp_year.length != 0) {
				for (String imageName : calendarInfo.bmp_year) {
					dYearArray.add(getShortDrawable(imageName));
				}
			}
			Point posMonth = new Point();
			posMonth = calendarInfo.point_month;
			List<Drawable> dMonthArray = new ArrayList<Drawable>();
			if (calendarInfo.bmp_month != null
					&& calendarInfo.bmp_month.length != 0) {
				for (String imageName : calendarInfo.bmp_month) {
					dMonthArray.add(getShortDrawable(imageName));
				}
			}
			Point posDate = new Point();
			posDate = calendarInfo.point_date;
			List<Drawable> dDateArray = new ArrayList<Drawable>();
			if (calendarInfo.bmp_date != null
					&& calendarInfo.bmp_date.length != 0) {
				for (String imageName : calendarInfo.bmp_date) {
					dDateArray.add(getShortDrawable(imageName));
				}
			}
			Point posWeek = new Point();
			posWeek = calendarInfo.point_week;
			List<Drawable> dWeekArray = new ArrayList<Drawable>();
			if (calendarInfo.bmp_week != null
					&& calendarInfo.bmp_week.length != 0) {
				for (String imageName : calendarInfo.bmp_week) {
					dWeekArray.add(getShortDrawable(imageName));
				}
			}
			view = new Calendar(this, caleBg, caleDot, posYear, dYearArray,
					posMonth, dMonthArray, posDate, dDateArray, posWeek,
					dWeekArray);
		}

		if (info.uri != null && !info.uri.isEmpty()) {
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = null;
						if(TextUtils.isEmpty(info.account)){
							intent = Intent.parseUri(info.uri, 0);
						}else{
							String uri = null;
							if("DIAL".equals(info.account)){
								uri = getIntentUri("DialtactsActivity");
							}else if("MESSAGE".equals(info.account)){
								uri = getIntentUri("MmsActivity");
							} else if("CONTACTSPEOPLE".equals(info.account)){
								uri = getIntentUri("PeopleActivity");
							} else if ("CONTACT".equals(info.account)) {
								uri = getIntentUri("PeopleActivity");
							} else if ("EMAIL".equals(info.account)) {
								uri = getIntentUri("email");
							} else if ("CALENDAR".equals(info.account)) {
								uri = getIntentUri("calendar");
							} else if ("CAMERA".equals(info.account)) {
								uri = getIntentUri("Camera");
							} else if ("CALCULATOR".equals(info.account)) {
								uri = getIntentUri("Calculator");
							} else if ("NOTE".equals(info.account)) {
								uri = getIntentUri("note");
							} else if ("BROWSER".equals(info.account)) {
								uri = getIntentUri("browser");
							} else if ("MUSIC".equals(info.account)) {
								uri = getIntentUri("music");
							} else if ("GALLERY".equals(info.account)) {
								uri = getIntentUri("Gallery");
							} else if ("ANALOGCLOCK".equals(info.account)) {
								uri = getIntentUri("DeskClock");
							} else if ("VIDEO".equals(info.account)) {
								uri = getIntentUri("video");
							} else if ("EBOOK".equals(info.account)) {
								uri = getIntentUri("iReader");
							} 
							info.uri = TextUtils.isEmpty(uri) ? info.uri : uri;
							intent = Intent.parseUri(info.uri, 0);
						}
						mSceneLauncherApplication.startActivitySafelyForShortcut(intent);
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			});
		} else if (info.type.equals("MainmenuButton")) {
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showAllapps();
				}
			});
		}
		return view;
	}

	private void loadMainmenu() {
		LayoutInflater flater = LayoutInflater.from(this);
		mSceneAllAppsLayout = (ViewGroup) flater.inflate(
				R.layout.launcher_secene_allapps, null);
		mSceneAllAppsLayout.setVisibility(View.INVISIBLE);
		mSceneRoot.addView(mSceneAllAppsLayout);
		mSceneAllApps = (SceneAllAppsPagedView) findViewById(R.id.scene_allapps);
	}

	private void showAllapps() {
		mSceneAllAppsLayout.setVisibility(View.VISIBLE);
		mSceneAllApps.flashScrollingIndicator(true);
		// mSceneAllAppsLayout.setFocusable(true);
		// mSceneAllAppsLayout.requestFocus();
	}

	private void hideAllapps() {
		mSceneAllAppsLayout.setVisibility(View.INVISIBLE);
		// mSceneAllAppsLayout.setFocusable(false);
	}

	private boolean isAllappsVisible() {
		return mSceneAllAppsLayout.getVisibility() == View.VISIBLE;
	}

	private void animViewStart() {
		if (mAnimViews != null && !mAnimViews.isEmpty()) {
			for (ImageView animView : mAnimViews) {
				AnimationDrawable animDrawable = (AnimationDrawable) animView
						.getBackground();
				animDrawable.start();
			}
		}
	}

	private void animViewStop() {
		if (mAnimViews != null && !mAnimViews.isEmpty()) {
			for (ImageView animView : mAnimViews) {
				AnimationDrawable animDrawable = (AnimationDrawable) animView
						.getBackground();
				animDrawable.stop();
			}
		}
	}

	private Drawable getOrignShorcutDrawable(String fileName) {
		Drawable d = null;
		Bitmap b = getShortcutBitmap(fileName);
		if (b != null) {
			d = new BitmapDrawable(getResources(), b);
		}
		return d;
	}

	private Drawable getShortDrawable(String fileName) {
		Drawable d = null;
		Bitmap b = getShortcutBitmap(fileName);
		if (b != null) {
			b = scaleBitmap(b);
		}
		if (b != null) {
			d = new BitmapDrawable(getResources(), b);
		}
		return d;
	}

	private Bitmap getShortcutBitmap(String fileName) {
		// get bm from customer path
		InputStream is = null;
		Bitmap bmp = null;
		try {
			is = new FileInputStream(SceneLayoutXmlParser.getCustomDrawablePath() + fileName);
			bmp = BitmapFactory.decodeStream(is);
			is.close();
		} catch (FileNotFoundException e) {
			try {
				// get bm from assets
				is = getAssets().open(SceneLayoutXmlParser.BACKGROUND_PATH + fileName);
				bmp = BitmapFactory.decodeStream(is);
				is.close();
			} catch (IOException e1) {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bmp;
	}

	private Bitmap scaleBitmap(Bitmap b) {
		Bitmap bmp = b;
		Matrix matrix = new Matrix();
		matrix.postScale(getWidthScale(), getHeightScale());
		bmp = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix,
				true);
		return bmp;
	}

	// 获取宽度缩放比例
	private float getWidthScale() {
		final int contrastWidth = 480;
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		float scale = (float) metrics.widthPixels / contrastWidth;
		return scale;
	}

	// 获取高度缩放比例
	private float getHeightScale() {
		final int contrastHeight = 800;
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// unit pix, 25dp为状态栏的高度
		final int statusbarHeight = (int) (25 * metrics.density + 0.5f); 
		float scale = (float) (metrics.heightPixels - statusbarHeight)
				/ contrastHeight;
		return scale;
	}

	// 从480x800分辨率下的坐标，转化为其他分辨率下的坐标
	private int getAbsolutePosX(int contrastX) {
		return (int) (getWidthScale() * contrastX);
	}

	private int getAbsolutePosY(int constrastY) {
		return (int) (getHeightScale() * constrastY);
	}
	
	private String getIntentUri(String value){
		StringBuffer uri = new StringBuffer();
		for(ApplicationInfo info : systemApp){
			if(info.componentName.getClassName().contains(value)){
				uri.append("intent:#Intent;");
				uri.append("action=android.intent.action.MAIN;");
				uri.append("component=");
				uri.append(info.componentName.getPackageName());
				uri.append("/");
				uri.append(info.componentName.getShortClassName());
				uri.append(";end");
				break;
			}
		}
		if(TextUtils.isEmpty(uri.toString())){
			for(ApplicationInfo info : allApps){
				if(info.componentName.getClassName().contains(value)){
					uri.append("intent:#Intent;");
					uri.append("action=android.intent.action.MAIN;");
					uri.append("component=");
					uri.append(info.componentName.getPackageName());
					uri.append("/");
					uri.append(info.componentName.getShortClassName());
					uri.append(";end");
					break;
				}
			}
		}
		return uri.toString();
	}
	
}
