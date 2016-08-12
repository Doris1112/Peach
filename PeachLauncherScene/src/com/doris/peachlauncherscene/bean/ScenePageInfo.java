package com.doris.peachlauncherscene.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Doris
 *
 * 2016年8月11日
 */
public class ScenePageInfo {

	public String backgroundPath;
	public List<ShortcutInfo> children;

	public void addChild(ShortcutInfo info) {
		if (children == null) {
			children = new ArrayList<ShortcutInfo>();
		}
		children.add(info);
	}

	@Override
	public String toString() {
		String result = "ScenePageInfo:backgroundPath = " + backgroundPath;
		for (int i = 0; i < children.size(); i++) {
			result += "\n" + "child index = " + i + " ";
			result += children.get(i);
		}
		return result;
	}
	
}
