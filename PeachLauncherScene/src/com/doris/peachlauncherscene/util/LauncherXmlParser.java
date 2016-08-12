package com.doris.peachlauncherscene.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;

/**
 * 应用排序方式配置解析，其他品牌手机请忽视
 * @author Doris
 *
 * 2016年8月11日
 */
public class LauncherXmlParser {
	
	private static final String OEM_ROOT_PATH = "/system/oem";
	private static final String OEM_SORTMM_PATH = OEM_ROOT_PATH
			+ "/hmcfg/sort_mm.xml";
	private static final String OEM_ICONS_PATH = OEM_ROOT_PATH
			+ "/hmcfg/icons/";
	private static final String OEM_SETTINGS_PATH = OEM_ROOT_PATH
			+ "/hmcfg/settings.xml";
	private static final String OEM_MAINMENU_BG_PATH = "ro.oempath"
			+ "/hmcfg/icons/scene_mainmenu_bg.jpg";

	/**
	 * 配置文件 settings.xml
	 * @param key
	 * @param file
	 * @return
	 */
	private static String getPropertiesByKey(String key, String file) {
		if (key == null || file == null) {
			return null;
		}

		String value = null;
		try {
			FileInputStream in = new FileInputStream(file);
			Properties prop = new java.util.Properties();
			prop.load(in);
			value = prop.getProperty(key);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return value;
	}

	// 读Boolean值
	public static boolean getSettingBoolean(String key, boolean defValue) {
		boolean ret = defValue;
		String value = getPropertiesByKey(key, OEM_SETTINGS_PATH);
		if (value != null) {
			ret = Boolean.parseBoolean(value);
		}

		return ret;
	}

	// 读整型
	public static int getSettingInt(String key, int defValue) {
		int ret = defValue;
		String value = getPropertiesByKey(key, OEM_SETTINGS_PATH);
		if (value != null) {
			ret = Integer.parseInt(value);
		}

		return ret;
	}

	// 读string型
	public static String getSettingString(String key, String defValue) {
		String ret = defValue;
		String value = getPropertiesByKey(key, OEM_SETTINGS_PATH);
		if (value != null) {
			ret = value;
		}
		return ret;
	}

	public static class MenuItem {
		private String packageName;
		private String className;
		private String icon;
		private boolean visible;
		private int index;

		public MenuItem(String packageName, String className, String icon,
				boolean visible, int index) {
			this.packageName = packageName;
			this.className = className;
			this.icon = icon;
			this.visible = visible;
			this.index = index;
		}

		public String getPackageName() {
			return packageName;
		}

		public String getClassName() {
			return className;
		}

		public String getIconPath() {
			return OEM_ICONS_PATH + icon + ".png";
		}

		public boolean isVisible() {
			return visible;
		}

		public int getIndex() {
			return index;
		}
	}

	private static class MenuSortComparator implements Comparator<MenuItem> {
		public final int compare(MenuItem a, MenuItem b) {
			String cn1 = a.getPackageName() + a.getClassName();
			String cn2 = b.getPackageName() + b.getClassName();
			return cn1.compareTo(cn2);
		}
	}

	// private static final String TAG_FAVORITES = "favorites";
	private static final String TAG_MAINMENU = "mainmenu";
	private static final String TAG_URIVALUE = "*FUNCTION*";
	private static final String ATTR_URI = "launcher:uri";
	private static final String ATTR_ICONNAME = "launcher:iconName";
	private static final String ATTR_APPNAME = "launcher:appName";
	private static final String ATTR_HIDE = "*HIDE*";
	private static ArrayList<MenuItem> mMainMenuList = null;

	@SuppressLint("NewApi")
	private static void getItemList(final String path, final String tag,
			ArrayList<MenuItem> list) {
		list.clear();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			FileInputStream in = new FileInputStream(path);
			parser.setInput(in, "utf-8");

			// XmlUtils.beginDocument(parser, TAG_FAVORITES);
			final int depth = parser.getDepth();
			int type, index = 0;
			String packageName, className, icon, appName;
			while (((type = parser.next()) != XmlPullParser.END_TAG || parser
					.getDepth() > depth)
					&& (type != XmlPullParser.END_DOCUMENT)) {
				if (type != XmlPullParser.START_TAG) {
					continue;
				}
				String tagName = parser.getName();
				if (tag.equals(tagName)) {
					String uri = parser.getAttributeValue(null, ATTR_URI);
					if (uri.equals(TAG_URIVALUE)) {
						packageName = null;
						className = null;
					} else {
						Intent intent = Intent.parseUri(uri, 0);
						if (intent == null) {
							continue;
						}
						ComponentName cn = intent.getComponent();
						packageName = cn.getPackageName();
						className = cn.getClassName();
					}
					icon = parser.getAttributeValue(null, ATTR_ICONNAME);
					appName = parser.getAttributeValue(null, ATTR_APPNAME);
					boolean visible = !appName.equals(ATTR_HIDE);

					MenuItem item = new MenuItem(packageName, className, icon,
							visible, index++);
					list.add(item);
				}
			}
		} catch (XmlPullParserException e) {
			// TODO: handle exception
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (URISyntaxException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	public static MenuItem findMainMenuItem(ComponentName componentName) {
		return findMainMenuItem(componentName.getPackageName(),
				componentName.getClassName());
	}

	public static MenuItem findMainMenuItem(String packageName, String className) {
		// 检查参数
		ArrayList<MenuItem> list = getMainMenuList();
		if (packageName == null || className == null || list.isEmpty()) {
			return null;
		}
		// 查找
		MenuItem menuItem = new MenuItem(packageName, className, null, true, 0);
		int pos = Collections.binarySearch(list, menuItem,
				new MenuSortComparator());
		if (pos < 0) {
			return null;
		}
		return list.get(pos);
	}

	// 获得主菜单链表
	public static ArrayList<MenuItem> getMainMenuList() {
		if (mMainMenuList == null) {
			mMainMenuList = new ArrayList<MenuItem>();
			getItemList(OEM_SORTMM_PATH, TAG_MAINMENU, mMainMenuList);
			// 按ComponentName排序
			Collections.sort(mMainMenuList, new MenuSortComparator());
		}

		return mMainMenuList;
	}

	// 获取场景桌面主菜单背景文件路径
	public static String getMainmenuBgPath() {
		return OEM_MAINMENU_BG_PATH;
	}

}
