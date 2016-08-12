package com.doris.peachlauncherscene.application;

import java.io.File;
import java.io.FileFilter;
import java.lang.ref.WeakReference;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.doris.peachlauncherscene.LauncherScene;
import com.doris.peachlauncherscene.R;
import com.doris.peachlauncherscene.bean.ApplicationInfo;
import com.doris.peachlauncherscene.bean.SceneHotseatInfo;
import com.doris.peachlauncherscene.bean.ScenePageInfo;
import com.doris.peachlauncherscene.util.LauncherXmlParser;
import com.doris.peachlauncherscene.util.SceneLayoutXmlParser;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

/**
 * 
 * @author Doris
 *
 * 2016年8月11日
 */
public class SceneLauncherApplication extends Application{

	static final String PREF_KEY = "com.topwise.scenelauncher.prefs";
	static final String SELECT_SCENE_KEY = "select_scene";

	static final String OEM_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	static final String SCENE_DIR_PATH = OEM_ROOT_PATH + "/scene/";
	static final String SCENE_PREVIEW_NAME = "scene_preview.jpg";

	static final HandlerThread sWorkerThread = new HandlerThread("LoadingApps");
	static {
		sWorkerThread.start();
	}
	static final Handler sWorkerHandler = new Handler(sWorkerThread.getLooper());
	static final Handler sHandler = new Handler();

	public static ArrayList<ApplicationInfo> systemApp;
	static ArrayList<ApplicationInfo> mApps;
	static List<ScenePageInfo> mScenePages;
	static SceneHotseatInfo mHotseat;

	private BroadcastReceiver mLauncherReceiver;
	private WeakReference<LauncherScene> mLauncher;

	private List<String> mScenePathList; // 保存场景资源的路径
	
	@Override
	public void onCreate() {
		super.onCreate();
		initReceiver();
		initScene();

	}
	//注册应用动态变化广播
	private void initReceiver() {
		// register recevier
		mLauncherReceiver = new LauncherReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_ADDED);
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
		filter.addDataScheme("package");
		registerReceiver(mLauncherReceiver, filter);
		filter = new IntentFilter();
		filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
		filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
		registerReceiver(mLauncherReceiver, filter);
		filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
		registerReceiver(mLauncherReceiver, filter);
	}
	//初始化场景桌面路径
	private void initScene() {
		initScenePathList();
		initSelectScenePath();
		SceneLayoutXmlParser.setApplication(this);
	}

	public List<ScenePageInfo> getSenePageInfoList() {
		if (mScenePages == null) {
			mScenePages = SceneLayoutXmlParser.getScenePages(this);
		}
		return mScenePages;
	}

	public SceneHotseatInfo getHotseatInfo() {
		if (mHotseat == null) {
			mHotseat = SceneLayoutXmlParser.getHotseatInfo(this);
		}
		return mHotseat;
	}

	public void resetScenePageInfoList() {
		mScenePages = null;
	}

	public void resetHotseatInfo() {
		mHotseat = null;
	}

	public ArrayList<ApplicationInfo> getAllApps() {
		if (mApps == null) {
			mApps = new ArrayList<ApplicationInfo>();
			if(systemApp == null){
				systemApp = new ArrayList<ApplicationInfo>();
			}
			fillAllapps();
		}
		return mApps;
	}
	//安全启动应用，避免未找到应用程序时崩溃
	public boolean startActivitySafely(Intent intent) {
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			startActivity(intent);
			return true;
		} catch (ActivityNotFoundException e) {
			 Toast.makeText(this, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
		} catch (SecurityException e) {
			Toast.makeText(this, R.string.activity_not_found,
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			
		}
		return false;
	}

	// 和classic launcher一样的方式启动app
	public boolean startActivitySafelyForShortcut(Intent intent) {
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		return startActivitySafely(intent);
	}

	public void setLauncher(LauncherScene launcher) {
		this.mLauncher = new WeakReference<LauncherScene>(launcher);
	}

	/**
	 *  获取scene目录下的子目录名
	 * @return
	 */
	public List<String> getScenePathList() {
		return mScenePathList;
	}

	/**
	 *  获取用户已经选择的场景UI目录
	 * @return
	 */
	public String getSelectScenePath() {
		SharedPreferences prefs = getSharedPreferences(PREF_KEY,
				Context.MODE_PRIVATE);
		return prefs.getString(SELECT_SCENE_KEY, "");
	}

	// 设置用户选择的场景UI目录
	public String setSelectScenePath(String path) {
		if (path != null) {
			Editor editor = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
					.edit();
			editor.putString(SELECT_SCENE_KEY, path);
			editor.commit();
		}
		return path;
	}

	// 获取scene目录下的子目录，并保存起来
	private void initScenePathList() {
		File sceneDir = new File(SCENE_DIR_PATH);
		if (!sceneDir.isDirectory())
			return;
		File[] sceneSubDirs = sceneDir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		if (sceneSubDirs == null)
			return;
		if (mScenePathList == null)
			mScenePathList = new ArrayList<String>();
		mScenePathList.clear();
		for (File dir : sceneSubDirs) {
			mScenePathList.add(dir.getAbsolutePath());
		}
	}

	// 初始化选中的场景UI路径
	private void initSelectScenePath() {
		SharedPreferences prefs = getSharedPreferences(PREF_KEY,
				Context.MODE_PRIVATE);
		if (!prefs.contains(SELECT_SCENE_KEY) && mScenePathList != null) {
			String customScenePath = LauncherXmlParser.getSettingString(
					"default_scene_path", null);
			if (customScenePath != null) {
				setSelectScenePath(SCENE_DIR_PATH + customScenePath);
			} else {
				setSelectScenePath(mScenePathList.get(0));
			}
		}
	}
	
	//找到手机中安装的所有应用程序
	private void fillAllapps() {
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		final PackageManager packageManager = getPackageManager();
		List<ResolveInfo> apps = packageManager.queryIntentActivities(
				mainIntent, 0);
		mApps.clear();
		systemApp.clear();
		for (ResolveInfo app : apps) {
			mApps.add(new ApplicationInfo(this, app));
			if(isSystemApp(app.activityInfo.packageName)){
				systemApp.add(new ApplicationInfo(this, app));
			}
		}
		Collections.sort(mApps, APP_NAME_COMPARATOR);
		sortAppsByCustom(mApps);
	}

	private void refillAllapps() {
		fillAllapps();
	}
	
	//通过包名找到应用程序的信息ResolveInfo
	private List<ResolveInfo> findActivitiesForPackage(String packageName) {
		final PackageManager packageManager = getPackageManager();
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mainIntent.setPackage(packageName);

		final List<ResolveInfo> apps = packageManager.queryIntentActivities(
				mainIntent, 0);
		return apps != null ? apps : new ArrayList<ResolveInfo>();
	}
	//添加应用程序，当用户安装了新应用时
	private void addPackage(String packageName) {
		final List<ResolveInfo> matches = findActivitiesForPackage(packageName);

		if (matches.size() > 0) {
			for (ResolveInfo info : matches) {
				mApps.add(new ApplicationInfo(this, info));
			}
		}
	}
	
	//移除应用程序，当用户删除某一个应用时
	private void removePackage(String packageName) {
		for (int i = mApps.size() - 1; i >= 0; i--) {
			ApplicationInfo info = mApps.get(i);
			final ComponentName component = info.intent.getComponent();
			if (packageName.equals(component.getPackageName())) {
				mApps.remove(i);
			}
		}
	}
	
	//对应用程序重新排序
	private void sortAppsByCustom(List<ApplicationInfo> list) {
		ArrayList<LauncherXmlParser.MenuItem> sortList = LauncherXmlParser
				.getMainMenuList();
		int N = list.size();

		if (sortList.isEmpty()) {
			// 没有自定义排序表，则按名称排序
			for (int i = 0; i < N; i++) {
				ApplicationInfo app = list.get(i);
				app.index = i;
			}
		} else {
			// 有自定义排序，则自定义排序在前，没在自定义表里的，按名称排，整体放在自定义后
			LauncherXmlParser.MenuItem item;
			ApplicationInfo app;
			int custAppIndex = sortList.size();
			for (int i = N - 1; i >= 0; i--) {
				app = list.get(i);
				item = LauncherXmlParser.findMainMenuItem(app.componentName);
				if (item != null) {
					if (item.isVisible()) {
						app.index = item.getIndex();
					} else {
						list.remove(i);
					}
				} else {
					app.index = custAppIndex++;
				}
			}
		}

		Collections.sort(list, APPLICATION_CUST_SORT);
	}

	static final Comparator<ApplicationInfo> APPLICATION_CUST_SORT = new Comparator<ApplicationInfo>() {
		public final int compare(ApplicationInfo a, ApplicationInfo b) {
			return a.index - b.index;
		}
	};

	@SuppressLint("NewApi")
	static final Comparator<ApplicationInfo> APP_NAME_COMPARATOR = new Comparator<ApplicationInfo>() {
		public final int compare(ApplicationInfo a, ApplicationInfo b) {
			int result = Collator.getInstance().compare(a.title.toString(),
					b.title.toString());
			if (result == 0) {
				result = a.componentName.compareTo(b.componentName);
			}
			return result;
		}
	};
	//处理应用动态变化广播的任务
	private class PackageUpdatedTask implements Runnable {
		public static final int OP_NONE = 0;
		public static final int OP_ADD = 1;
		public static final int OP_UPDATE = 2;
		public static final int OP_REMOVED = 3;
		public static final int OP_RELOAD = 4;

		int mOp;
		String[] mPackages;

		public PackageUpdatedTask(int op, String[] packages) {
			mOp = op;
			mPackages = packages;
		}

		public PackageUpdatedTask(int op) {
			mOp = op;
		}

		@Override
		public void run() {
			if (mOp == OP_RELOAD) {
				refillAllapps();
			} else {
				final String[] packages = mPackages;
				final int N = packages.length;
				switch (mOp) {
				case OP_ADD:
					if (N > 0) {
						sHandler.post(new Runnable() {
							@Override
							public void run() {
								for (int i = 0; i < N; i++) {
//									"PackageUpdatedTask add packageName = " + packages[i];
									addPackage(packages[i]);
								}
								sHandler.post(new Runnable() {

									@Override
									public void run() {
										LauncherScene launcher = mLauncher
												.get();
										if (launcher != null) {
											launcher.bindAllapps();
										}
									}
								});
							}
						});
					}
					break;

				case OP_REMOVED:
					if (N > 0) {
						sHandler.post(new Runnable() {

							@Override
							public void run() {
								for (int i = 0; i < N; i++) {
//									"PackageUpdatedTask remove packageName = " + packages[i];
									removePackage(packages[i]);
								}
								sHandler.post(new Runnable() {

									@Override
									public void run() {
										LauncherScene launcher = mLauncher
												.get();
										if (launcher != null) {
											launcher.bindAllapps();
										}
									}
								});
							}
						});
					}
					break;

				case OP_UPDATE:
//					for (int i = 0; i < N; i++) {
//						 "PackageUpdatedTask update packageName = " + packages[i];
//					}
					break;
				}
			}
		}

	}

	private class LauncherReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
//			"LauncherReceiver onRecive action = " + action;

			if (Intent.ACTION_PACKAGE_ADDED.equals(action)
					|| Intent.ACTION_PACKAGE_CHANGED.equals(action)
					|| Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
				final String packageName = intent.getData()
						.getSchemeSpecificPart();
				final boolean replacing = intent.getBooleanExtra(
						Intent.EXTRA_REPLACING, false);
//				"LauncherReceiver onRecive packageName = "
//						+ packageName;
				int op = PackageUpdatedTask.OP_NONE;
				if (Intent.ACTION_PACKAGE_CHANGED.equals(action)) {
					op = PackageUpdatedTask.OP_UPDATE;
				} else if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
					if (!replacing)
						op = PackageUpdatedTask.OP_REMOVED;
				} else if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
					if (!replacing)
						op = PackageUpdatedTask.OP_ADD;
				}
				if (op != PackageUpdatedTask.OP_NONE) {
					sWorkerHandler.post(new PackageUpdatedTask(op,
							new String[] { packageName }));
				}

			} else if (Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE
					.equals(action)) {
				String[] packages = intent
						.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
				sWorkerHandler.post(new PackageUpdatedTask(
						PackageUpdatedTask.OP_ADD, packages));
			} else if (Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE
					.equals(action)) {
				String[] packages = intent
						.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
				sWorkerHandler.post(new PackageUpdatedTask(
						PackageUpdatedTask.OP_REMOVED, packages));
			} else if (Intent.ACTION_LOCALE_CHANGED.equals(action)) {
				sWorkerHandler.post(new PackageUpdatedTask(
						PackageUpdatedTask.OP_RELOAD));
			}
		}

	}
	
	/**
	 * 判断是否是系统应用
	 * @param archiveFilePath
	 * @return
	 */
	private boolean isSystemApp(String archiveFilePath){
		PackageInfo mPackageInfo;
		try {
			mPackageInfo = getPackageManager().getPackageInfo(archiveFilePath, 0);
			if((mPackageInfo.applicationInfo.flags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) > 0 ){
				return true;
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
