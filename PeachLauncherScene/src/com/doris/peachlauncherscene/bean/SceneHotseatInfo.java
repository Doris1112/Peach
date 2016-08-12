package com.doris.peachlauncherscene.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Doris
 *
 * 2016年8月11日
 */
public class SceneHotseatInfo {

	public String backgroundPath;
	public int x;
	public int y;
	public List<ShortcutInfo> hotseats;

	public void addHotseat(ShortcutInfo info) {
		if (hotseats == null) {
			hotseats = new ArrayList<ShortcutInfo>();
		}
		hotseats.add(info);
	}
}
