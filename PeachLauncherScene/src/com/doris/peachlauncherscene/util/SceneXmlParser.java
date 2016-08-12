package com.doris.peachlauncherscene.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.text.TextUtils;

/**
 * 
 * @author Doris
 *
 * 2016年8月11日
 */
public class SceneXmlParser {

	public static String getName(String patch) {
		String name = "";
		try {
			File file = new File(patch);
			if (!file.exists())
				return null;
			InputStream is = new FileInputStream(file);
			// if (is == null)
			// return null;
			// 获取XmlPullParser的实例
			XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance()
					.newPullParser();
			// 设置输入流 xml文件
			xmlPullParser.setInput(is, "UTF-8");
			// 开始解析
			int eventType = xmlPullParser.getEventType();
			String enName = "";
			String cnName = "";
			String twName = "";
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String nodeName = xmlPullParser.getName();
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT: // 文档开始
					break;

				case XmlPullParser.START_TAG:// 开始节点
					if (nodeName.equals("en")) {
						enName = xmlPullParser.nextText();
					} else if (nodeName.equals("zh-rTW")) {
						twName = xmlPullParser.nextText();
					} else if (nodeName.equals("zh-rCN")) {
						cnName = xmlPullParser.nextText();
					}
					break;

				case XmlPullParser.END_TAG:// 结束节点
					break;

				default:
					break;
				}
				eventType = xmlPullParser.next();
			}
			if (TextUtils.equals(Locale.getDefault().getLanguage(), "zh")) {
				if (TextUtils.equals(Locale.getDefault().getCountry(), "TW")
						|| TextUtils.equals(Locale.getDefault().getCountry(),
								"HK")) {
					name = twName;
				} else {
					name = cnName;
				}
			} else {
				name = enName;
			}

		} catch (Exception e) {
		}
		return name;
	}
	
}
