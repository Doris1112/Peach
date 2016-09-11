package com.doris.peach.activity.searchforrelated;

import java.util.LinkedList;

import org.json.JSONArray;

import com.doris.peachlibrary.domain.Film;

import android.graphics.Color;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年5月12日
 */
public class SearchConditionStruct {

	/**
	 * 定义查询条件容器
	 * 
	 */
	private LinkedList<SearchConditionGroup> groups;

	/**
	 * 构造函数
	 * 
	 * @param configJson
	 */
	public SearchConditionStruct(String configJson) {
		groups = new LinkedList<SearchConditionStruct.SearchConditionGroup>();
		setJsonToStruct(configJson);
	}

	/**
	 * 将Json数据写入到容器中
	 * 
	 * @param configJson
	 */
	private void setJsonToStruct(String configJson) {
		try {
			JSONArray jsongroup = new JSONArray(configJson);
			for (int i = 0; i < jsongroup.length(); i++) {
				SearchConditionGroup group = new SearchConditionGroup();
				group.name = jsongroup.getJSONObject(i).getString("name");
				group.key = jsongroup.getJSONObject(i).getString("key");
				JSONArray jsonitem = new JSONArray(jsongroup.getJSONObject(i).getString("value"));
				for (int j = 0; j < jsonitem.length(); j++) {
					SearchConditionItem item = new SearchConditionItem();
					item.condition = jsonitem.getJSONObject(j).getString("condition");
					item.value = jsonitem.getJSONObject(j).getString("value");
					item.group = group;
					item.checked = jsonitem.getJSONObject(j).getBoolean("checked");
					group.value.add(item);
				}
				groups.add(group);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 获取查询条件
	 * 
	 * @return
	 */
	public LinkedList<SearchConditionGroup> getSearchConditionGroups() {
		return groups;
	}

	/**
	 * 获取选择的信息
	 * 
	 * @return
	 */
	public Film getSearchCondition() {
		Film film = new Film();
		for (SearchConditionGroup group : groups) {
			for (SearchConditionItem item : group.value) {
				if (item.checked) {
					if(group.key.equals("ReleaseTime")){
						film.setReleaseTime(item.getValue());
					}
					if(group.key.equals("Area")){
						film.setArea(item.getValue());
					}
					if(group.key.equals("Type")){
						film.setType(item.getValue());
					}
					if(group.key.equals("Actor")){
						film.setActor(item.getValue());
					}
				}
			}
		}
		return film;
	}
	
	/**
	 * 获取选择的信息
	 * 
	 * @return
	 */
	public String getSearchConditionString() {
		String result = "";
		String showText = "";
		for (SearchConditionGroup group : groups) {
			result = result + group.key + ":";
			showText = showText + group.name + ":";
			for (SearchConditionItem item : group.value) {
				if (item.checked) {
					result = result + item.value + ";\n";
					showText = showText + item.condition + ";\n";
				}
			}
		}
		return result + "\n\n" + showText;
	}

	/**
	 * 查询条件组结构
	 * 
	 */
	public class SearchConditionGroup {

		private String name;
		private String key;
		private LinkedList<SearchConditionItem> value;

		public SearchConditionGroup() {
			value = new LinkedList<SearchConditionStruct.SearchConditionItem>();
		}

		public String getName() {
			return name;
		}

		public String getKey() {
			return key;
		}

		public LinkedList<SearchConditionItem> getValue() {
			return value;
		}
	}

	/**
	 * 查询条件元素结构
	 * 
	 */
	public class SearchConditionItem {
		private String condition;
		private String value;
		private SearchConditionGroup group; // 保存父节点信息
		private boolean checked;
		private TextView view; // 保存使用控件的信息

		public String getCondition() {
			return condition;
		}

		public String getValue() {
			return value;
		}

		/**
		 * 设置显示的空间
		 * 
		 * @param view
		 */
		public void setView(TextView view) {
			this.view = view;
			if (checked) {
				view.setBackgroundColor(Color.argb(51, 0, 0, 0));
			} else {
				view.setBackgroundColor(Color.argb(0, 0, 0, 0));
			}
		}

		/**
		 * 设置选中
		 */
		public void setChecked() {
			for (SearchConditionItem item : group.value) {
				item.checked = false;
				item.view.setBackgroundColor(Color.argb(0, 0, 0, 0));
			}
			checked = true;
			view.setBackgroundColor(Color.argb(51, 0, 0, 0));
		}
	}
}
