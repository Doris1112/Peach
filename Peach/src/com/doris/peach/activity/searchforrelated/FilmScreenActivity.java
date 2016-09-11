package com.doris.peach.activity.searchforrelated;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.domain.Film;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年5月12日
 */
public class FilmScreenActivity extends BaseActivity{

	private GridView gv_film_screen;
	private FilmScreenAdapter adapter;
	private List<Film> films = new ArrayList<Film>();
	private final List<Film> filmList = new ArrayList<Film>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_film_screen);
		
		setContentUnderStatus(findViewById(R.id.rl_filmscreen_content));
		
		gv_film_screen = (GridView) findViewById(R.id.gv_film_screen);
		adapter = new FilmScreenAdapter();
		
		initFilms();
		gv_film_screen.setAdapter(adapter);
	}
	
	public void filmScreen(View view){
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		// 获取状态栏高度
		int statusBarHeight = frame.top;
		SearchPanelOperator searchPanelOperator = new SearchPanelOperator(this,
				"text/filmSearch.txt");
		Button btn = (Button) view;
		int[] location = new int[2];
		btn.getLocationOnScreen(location);
		// 显示查询框
		searchPanelOperator.showSearchPanel(location[1] - statusBarHeight + btn.getMeasuredHeight());
		searchPanelOperator.setOnSearchFinished(new SearchPanelOperator.SearchFinishedListener() {
			/**
			 * 点击确定后的事件，返回选择的查询结果
			 */
			@Override
			public void onFinished(Film result) {
				// TODO Auto-generated method stub
				screenFilm(result);
			}
		});
	}
	
	class FilmScreenAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return films.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return films.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Tag tag = null;
			if(convertView == null){
				convertView = LayoutInflater.from(
						FilmScreenActivity.this).inflate(
								R.layout.item_film_screen, null);
				tag = new Tag();
				tag.name = (TextView) convertView.findViewById(
						R.id.tv_itemfilmscreen);
				tag.image = (ImageView) convertView.findViewById(
						R.id.iv_itemfilmscreen);
				convertView.setTag(tag);
			}else{
				tag = (Tag) convertView.getTag();
			}
			
			tag.name.setText(films.get(position).getName());
			tag.image.setImageResource(films.get(position).getImage());
			
			return convertView;
		}
		
		class Tag{
			TextView name;
			ImageView image;
		}
		
	}
	
	private void screenFilm(Film film){
		films.clear();
		for (Film f : filmList) {
			if(f.equals(film)){
				films.add(f);
			}
		}
		adapter.notifyDataSetChanged();
	}
	
	private void initFilms(){
		films.add(new Film("2013", "USA", "KeHuan", "马特·达蒙", 
				"极乐空间", R.drawable.film_01));
		films.add(new Film("2014", "USA", "DongZuo", "安吉丽娜·朱莉", 
				"沉睡魔咒", R.drawable.film_02));
		films.add(new Film("2009", "USA", "ZaiNan", "约翰·库萨克", 
				"2012", R.drawable.film_03));
		films.add(new Film("2013", "China", "AiQing", "白百合", 
				"被偷走的那五年", R.drawable.film_04));
		films.add(new Film("2006", "USA", "JuQing", "安妮·海瑟薇", 
				"穿普拉达的女王", R.drawable.film_05));
		films.add(new Film("2010", "USA", "AiQing", "玛德琳·卡罗尔 ", 
				"怦然心动", R.drawable.film_06));
		films.add(new Film("2010", "USA", "DongZuo", "萨姆·沃辛顿", 
				"阿凡达", R.drawable.film_07));
		films.add(new Film("2015", "China", "JuQing", "多多", 
				"爸爸去哪儿2", R.drawable.film_08));
		films.add(new Film("1988", "USA", "JuQing", "达斯汀·霍夫曼", 
				"雨人", R.drawable.film_09));
		films.add(new Film("2006", "USA", "KeHuan", "艾玛·罗伯茨", 
				"美人鱼", R.drawable.film_10));
		films.add(new Film("1997", "USA", "ZaiNan", "莱昂纳多·迪卡普里奥", 
				"泰坦尼克号", R.drawable.film_11));
		films.add(new Film("1994", "USA", "JuQing", "汤姆·汉克斯", 
				"阿甘正传", R.drawable.film_12));
		films.add(new Film("2004", "USA", "KeHuan", "托比·马奎尔", 
				"蜘蛛侠", R.drawable.film_13));
		films.add(new Film("2013", "USA", "ZaiNan", "布拉德·皮特", 
				"世界僵尸大战", R.drawable.film_14));
		films.add(new Film("2007", "Japan", "AiQing", "新亘结衣", 
				"恋空", R.drawable.film_15));
		films.add(new Film("2006", "USA", "DongZuo", "约翰尼·德普", 
				"加勒比海盗", R.drawable.film_16));
		filmList.addAll(films);
	}
}
