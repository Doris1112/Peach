package com.doris.peach.activity.searchforrelated;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

/**
 * 
 * @author Doris
 *
 * 2016年5月12日
 */
public class ReadFilmSearchTxt {
	
	/**
	 * 从asset中获取文件并读取数据（资源文件只能读不能写）
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFromAsset(Context context, String fileName) {
		String res = "";
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			int length = in.available();
			byte[] buffer = new byte[length];
			in.read(buffer);
			if (buffer[0] == -17 && buffer[1] == -69 && buffer[2] == -65) {
				buffer[0] = 32;
				buffer[1] = 32;
				buffer[2] = 32;
			}
			res = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
