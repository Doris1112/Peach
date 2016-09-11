package com.doris.peach.activity.searchforrelated;

import com.doris.peach.R;
import com.doris.peach.activity.searchforrelated.SearchConditionStruct.SearchConditionGroup;
import com.doris.peach.activity.searchforrelated.SearchConditionStruct.SearchConditionItem;
import com.doris.peachlibrary.domain.Film;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年5月12日
 */
public class SearchPanelOperator {

	// 上下文
	private Context context;
	private SearchFinishedListener searchFinishedListener;
	private SearchConditionStruct searchConditionStruct;
	private AlertDialog diaAlertDialog;
	private Window window;
	private LinearLayout content;
	private Button btnOK;
	private Button btnCancel;

	/**
	 * @param context
	 * @param configFileName
	 *            查询框配置文件名称
	 */
	public SearchPanelOperator(Context context, String configFileName) {
		this.context = context;
		this.searchConditionStruct = new SearchConditionStruct(
				ReadFilmSearchTxt.readFromAsset(context, configFileName));
		this.diaAlertDialog = new AlertDialog.Builder(context).create();
	}

	/**
	 * 显示检索框
	 * 
	 * @param layoutparams_y，检索框显示位置
	 */
	public void showSearchPanel(int layoutparams_y) {
		diaAlertDialog.show();
		window = diaAlertDialog.getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setContentView(R.layout.layout_search_panel);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 1f;
		lp.dimAmount = 0f;
		lp.gravity = Gravity.TOP;
		lp.screenOrientation = Gravity.TOP;
		if (layoutparams_y != 0) {
			lp.y = layoutparams_y;// 设置y坐标
		}
		window.setAttributes(lp);
		content = (LinearLayout) window.findViewById(R.id.ll_search_panel_content);
		btnOK = (Button) window.findViewById(R.id.b_search_panel_ok);
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (searchFinishedListener != null) {
					searchFinishedListener.onFinished(searchConditionStruct.getSearchCondition());
				}
				diaAlertDialog.cancel();
				diaAlertDialog.dismiss();
			}
		});
		btnCancel = (Button) window.findViewById(R.id.b_search_panel_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				diaAlertDialog.cancel();
				diaAlertDialog.dismiss();
			}
		});
		addSearchItems();
	}

	/**
	 * 增加检索条件
	 */
	private void addSearchItems() {
		for (SearchConditionGroup group : searchConditionStruct.getSearchConditionGroups()) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_search_panel, null);
			TextView txtName = (TextView) view.findViewById(R.id.tv_itemsearchpanel_name);
			txtName.setText(group.getName());
			txtName.setTag(group.getKey());
			LinearLayout linearlayoutValueContent = (LinearLayout) view
					.findViewById(R.id.ll_itemsearchpanel_ValueContent);
			for (SearchConditionItem item : group.getValue()) {
				TextView textView = new TextView(context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(5, 2, 5, 2);
				textView.setLayoutParams(lp);
				textView.setPadding(5, 0, 5, 0);
				textView.setTextColor(Color.BLACK);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
				textView.setText(item.getCondition());
				textView.setTag(item);
				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SearchConditionItem item = (SearchConditionItem) v.getTag();
						item.setChecked();
					}
				});
				item.setView(textView);
				linearlayoutValueContent.addView(textView);
			}
			content.addView(view);
		}
	}

	/**
	 * 定义后完成的Listener
	 * 
	 * @param searchFinishedListener
	 */
	public void setOnSearchFinished(SearchFinishedListener searchFinishedListener) {
		this.searchFinishedListener = searchFinishedListener;
	}

	/**
	 * 一定一个接口
	 */
	public interface SearchFinishedListener {
		public void onFinished(Film result);
	}
}
