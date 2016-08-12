package com.doris.peachlauncherscene.bean;

/**
 * 
 * @author Doris
 *
 * 2016年8月11日
 */
public class ShortcutInfo {

	public String type;
	public String backgroundPath;
	public String uri;
	public int x;
	public int y;
	public String account;

	@Override
	public String toString() {
		return "type = " + type + ", backgroundPath = " + backgroundPath
				+ ", uri = " + uri + ", x= " + x + ", y = " + y;
	}
	
}
