package com.doris.peach;

import java.util.Arrays;

import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.ComparatorString;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 汉字排序
 * @author Doris
 *
 * 2016年4月12日
 */
public class ChineseSortActivity extends BaseActivity {

	private ListView lv_chinese_sort;
	private String[] list = { "Apple", "AppleA", "AApple", 
			"安吉丽拉", "孙", "1孙", "2孙", "张三", 
			"李四", "王五", "赵六", "Word", "word" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);
		
		setTitle();
		
		ComparatorString com = new ComparatorString(1);
		Arrays.sort(list, com);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				R.layout.item_text, list);
		
		lv_chinese_sort = (ListView) findViewById(R.id.lv_listButton);
		lv_chinese_sort.setAdapter(adapter);
	}

}
