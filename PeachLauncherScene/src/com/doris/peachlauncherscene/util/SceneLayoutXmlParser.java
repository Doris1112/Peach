package com.doris.peachlauncherscene.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.doris.peachlauncherscene.application.SceneLauncherApplication;
import com.doris.peachlauncherscene.bean.AnalogClockInfo;
import com.doris.peachlauncherscene.bean.CalendarInfo;
import com.doris.peachlauncherscene.bean.FrameAnimInfo;
import com.doris.peachlauncherscene.bean.NumberClockInfo;
import com.doris.peachlauncherscene.bean.SceneHotseatInfo;
import com.doris.peachlauncherscene.bean.ScenePageInfo;
import com.doris.peachlauncherscene.bean.ShortcutInfo;

import android.content.Context;
import android.graphics.Point;
import android.os.Environment;
import android.util.Xml;

/**
 * 解析SD卡根目录下scene目录 或者assets目录下的主题资源
 * @author Doris
 *
 * 2016年8月11日
 */
public class SceneLayoutXmlParser {
	
	public final static String LAYOUT_FILE_PATH = "scene_layout.xml";
	public static final String OEM_ROOT_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	public final static String OEM_LAYOUT_PATH = OEM_ROOT_PATH + "/scene/"
			+ LAYOUT_FILE_PATH;
	public final static String OEM_DRAWABLE_PATH = OEM_ROOT_PATH + "/scene/drawable/";

	public final static String BACKGROUND_PATH = "drawable/";
	final static String ATTR_TYPE = "type";
	final static String ATTR_BACKGROUND = "background";
	final static String ATTR_URI = "uri";
	final static String ATTR_ACCOUNT = "account";
	final static String ATTR_POS_X = "pos_x";
	final static String ATTR_POS_Y = "pos_y";
	final static String ATTR_HAND_HOUR = "hand_hour";
	final static String ATTR_HAND_MINUTE = "hand_minute";
	final static String ATTR_ANIM = "frame_anim";

	final static String ATTR_YEAR_X = "year_x";
	final static String ATTR_YEAR_Y = "year_y";
	final static String ATTR_YEAR_PIC = "year_pic";
	final static String ATTR_MONTH_X = "month_x";
	final static String ATTR_MONTH_Y = "month_y";
	final static String ATTR_MONTH_PIC = "month_pic";
	final static String ATTR_DATE_X = "date_x";
	final static String ATTR_DATE_Y = "date_y";
	final static String ATTR_DATE_PIC = "date_pic";
	final static String ATTR_CALE_DIAN = "cale_dot";
	final static String ATTR_WEEK_X = "week_x";
	final static String ATTR_WEEK_Y = "week_y";
	final static String ATTR_WEEK_PIC = "week_pic";

	final static String ATTR_TIME_COLON = "time_colon";
	final static String ATTR_TIME_PIC = "time_pic";
	final static String ATTR_TIME_APM = "time_apm";

	final static String TAG_PAGE = "page";
	final static String TAG_HOTSEAT = "hotseat";
	final static String TAG_SEAT = "seat";
	final static String TAG_SHORTCUT = "shortcut";

	private static SceneLauncherApplication mApplication;

	public static void setApplication(SceneLauncherApplication application) {
		mApplication = application;
	}

	public static List<ScenePageInfo> getScenePages(Context context) {
		List<ScenePageInfo> ScenePages = null;
		InputStream is = null;

		try {
			is = new FileInputStream(getCustomLayoutPath());
			ScenePages = readXml(is);
			is.close();
		} catch (FileNotFoundException e) {
			try {
				is = context.getAssets().open(LAYOUT_FILE_PATH);
				ScenePages = readXml(is);
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ScenePages;
	}

	public static SceneHotseatInfo getHotseatInfo(Context context) {
		SceneHotseatInfo hotseat = new SceneHotseatInfo();
		InputStream is = null;
		try {
			is = new FileInputStream(getCustomLayoutPath());
			hotseat = getHotseatInfo(is);
			is.close();
		} catch (FileNotFoundException e) {
			try {
				is = context.getAssets().open(LAYOUT_FILE_PATH);
				hotseat = getHotseatInfo(is);
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hotseat;
	}

	public static String getCustomDrawablePath() {
		return mApplication.getSelectScenePath() + "/drawable/";
	}

	private static String getCustomLayoutPath() {
		return mApplication.getSelectScenePath() + "/" + LAYOUT_FILE_PATH;
	}
	//解析scene_layout.xml文件，如果不同的手机需要修改该文件下应用的入口
	private static List<ScenePageInfo> readXml(InputStream is) {
		List<ScenePageInfo> scenePages = new ArrayList<ScenePageInfo>();
		ScenePageInfo scenePage = null;
		ShortcutInfo shortcut = null;
		String tagName;
		String tagValue;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "UTF-8");
			int type = parser.getEventType();
			while (type != XmlPullParser.END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_DOCUMENT:
					break;

				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if (tagName.equals(TAG_PAGE)) {
						scenePage = new ScenePageInfo();
						scenePage.backgroundPath = parser.getAttributeValue(
								null, ATTR_BACKGROUND);

					} else if (tagName.equals(TAG_SHORTCUT)) {
						tagValue = parser.getAttributeValue(null, ATTR_TYPE);
						if (tagValue.equals("AnalogClock")) {
							shortcut = new AnalogClockInfo();
							((AnalogClockInfo) shortcut).handHour = parser
									.getAttributeValue(null, ATTR_HAND_HOUR);
							((AnalogClockInfo) shortcut).handMinute = parser
									.getAttributeValue(null, ATTR_HAND_MINUTE);
						} else if (tagValue.equals("NumberClock")) {
							shortcut = new NumberClockInfo();
							((NumberClockInfo) shortcut).timeColon = parser
									.getAttributeValue(null, ATTR_TIME_COLON);
							String bmpArray = parser.getAttributeValue(null,
									ATTR_TIME_PIC);
							if (bmpArray != null && !bmpArray.isEmpty()) {
								((NumberClockInfo) shortcut).timePic = bmpArray
										.split(";");
							}
							bmpArray = parser.getAttributeValue(null,
									ATTR_TIME_APM);
							if (bmpArray != null && !bmpArray.isEmpty()) {
								((NumberClockInfo) shortcut).timeApm = bmpArray
										.split(";");
							}
						} else if (tagValue.equals("AnimView")) {
							shortcut = new FrameAnimInfo();
							String value = parser.getAttributeValue(null,
									ATTR_ANIM);
							if (value != null && !value.isEmpty()) {
								((FrameAnimInfo) shortcut).anim = value
										.split(";");
							}
						} else if (tagValue.equals("Calendar")) {
							shortcut = new CalendarInfo();

							((CalendarInfo) shortcut).bmp_dot = parser
									.getAttributeValue(null, ATTR_CALE_DIAN);
							String posVal;
							Point pYear = new Point();
							posVal = parser
									.getAttributeValue(null, ATTR_YEAR_X);
							if (posVal == null) {
								pYear.x = 0;
							} else {
								pYear.x = Integer.parseInt(posVal);
							}
							posVal = parser
									.getAttributeValue(null, ATTR_YEAR_Y);
							if (posVal == null) {
								pYear.y = 0;
							} else {
								pYear.y = Integer.parseInt(posVal);
							}
							((CalendarInfo) shortcut).point_year = pYear;
							String bmpArray = parser.getAttributeValue(null,
									ATTR_YEAR_PIC);
							if (bmpArray != null && !bmpArray.isEmpty()) {
								((CalendarInfo) shortcut).bmp_year = bmpArray
										.split(";");
							}

							Point pMonth = new Point();
							posVal = parser.getAttributeValue(null,
									ATTR_MONTH_X);
							if (posVal == null) {
								pMonth.x = 0;
							} else {
								pMonth.x = Integer.parseInt(posVal);
							}
							posVal = parser.getAttributeValue(null,
									ATTR_MONTH_Y);
							if (posVal == null) {
								pMonth.y = 0;
							} else {
								pMonth.y = Integer.parseInt(posVal);
							}
							((CalendarInfo) shortcut).point_month = pMonth;
							bmpArray = parser.getAttributeValue(null,
									ATTR_MONTH_PIC);
							if (bmpArray != null && !bmpArray.isEmpty()) {
								((CalendarInfo) shortcut).bmp_month = bmpArray
										.split(";");
							}

							Point pDate = new Point();
							posVal = parser
									.getAttributeValue(null, ATTR_DATE_X);
							if (posVal == null) {
								pDate.x = 0;
							} else {
								pDate.x = Integer.parseInt(posVal);
							}
							posVal = parser
									.getAttributeValue(null, ATTR_DATE_Y);
							if (posVal == null) {
								pDate.y = 0;
							} else {
								pDate.y = Integer.parseInt(posVal);
							}
							((CalendarInfo) shortcut).point_date = pDate;
							bmpArray = parser.getAttributeValue(null,
									ATTR_DATE_PIC);
							if (bmpArray != null && !bmpArray.isEmpty()) {
								((CalendarInfo) shortcut).bmp_date = bmpArray
										.split(";");
							}

							Point pWeek = new Point();
							posVal = parser
									.getAttributeValue(null, ATTR_WEEK_X);
							if (posVal == null) {
								pWeek.x = 0;
							} else {
								pWeek.x = Integer.parseInt(posVal);
							}
							posVal = parser
									.getAttributeValue(null, ATTR_WEEK_Y);
							if (posVal == null) {
								pWeek.y = 0;
							} else {
								pWeek.y = Integer.parseInt(posVal);
							}
							((CalendarInfo) shortcut).point_week = pWeek;
							bmpArray = parser.getAttributeValue(null,
									ATTR_WEEK_PIC);
							if (bmpArray != null && !bmpArray.isEmpty()) {
								((CalendarInfo) shortcut).bmp_week = bmpArray
										.split(";");
							}

						} else {
							shortcut = new ShortcutInfo();
						}
						shortcut.type = parser.getAttributeValue(null,
								ATTR_TYPE);
						shortcut.backgroundPath = parser.getAttributeValue(
								null, ATTR_BACKGROUND);
						shortcut.uri = parser.getAttributeValue(null, ATTR_URI);
						shortcut.account = parser.getAttributeValue(null, ATTR_ACCOUNT);
						tagValue = parser.getAttributeValue(null, ATTR_POS_X);
						shortcut.x = Integer.parseInt(tagValue);
						tagValue = parser.getAttributeValue(null, ATTR_POS_Y);
						shortcut.y = Integer.parseInt(tagValue);
					}
					break;

				case XmlPullParser.END_TAG:
					tagName = parser.getName();
					if (tagName.equals(TAG_PAGE) && scenePage != null) {
						scenePages.add(scenePage);
						scenePage = null;
					} else if (tagName.equals(TAG_SHORTCUT)
							&& scenePage != null && shortcut != null) {
						scenePage.addChild(shortcut);
						shortcut = null;
					}
					break;
				}
				type = parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scenePages;
	}

	private static SceneHotseatInfo getHotseatInfo(InputStream is) {
		SceneHotseatInfo hotseatInfo = null;
		ShortcutInfo shortcut = null;
		String tagName;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "UTF-8");
			int type = parser.getEventType();
			while (type != XmlPullParser.END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_DOCUMENT:
					break;

				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if (tagName.equals(TAG_HOTSEAT)) {
						hotseatInfo = new SceneHotseatInfo();
						hotseatInfo.backgroundPath = parser.getAttributeValue(
								null, ATTR_BACKGROUND);
						hotseatInfo.x = Integer.parseInt(parser
								.getAttributeValue(null, ATTR_POS_X));
						hotseatInfo.y = Integer.parseInt(parser
								.getAttributeValue(null, ATTR_POS_Y));
					} else if (tagName.equals(TAG_SEAT)) {
						shortcut = new ShortcutInfo();
						shortcut.type = parser.getAttributeValue(null,
								ATTR_TYPE);
						shortcut.backgroundPath = parser.getAttributeValue(
								null, ATTR_BACKGROUND);
						shortcut.uri = parser.getAttributeValue(null, ATTR_URI);
						shortcut.account = parser.getAttributeValue(null, ATTR_ACCOUNT);
						shortcut.x = Integer.parseInt(parser.getAttributeValue(
								null, ATTR_POS_X));
						shortcut.y = Integer.parseInt(parser.getAttributeValue(
								null, ATTR_POS_Y));
					}
					break;

				case XmlPullParser.END_TAG:
					tagName = parser.getName();
					if (tagName.equals(TAG_SEAT) && hotseatInfo != null) {
						hotseatInfo.addHotseat(shortcut);
						shortcut = null;
					} else if (tagName.equals(TAG_HOTSEAT)) {
						return hotseatInfo;
					}
					break;
				}
				type = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return hotseatInfo;
	}
}