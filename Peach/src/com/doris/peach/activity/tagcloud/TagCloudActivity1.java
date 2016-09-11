package com.doris.peach.activity.tagcloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.doris.peach.R;
import com.doris.peach.R.id;
import com.doris.peach.R.layout;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;
import com.doris.peachlibrary.view.TagCloudView;
import com.doris.peachlibrary.domain.District;
import com.doris.peachlibrary.view.util.Tag;

import android.os.Bundle;
import android.view.Display;
import android.widget.LinearLayout;

/**
 * 
 * @author Doris
 *
 * 2016年4月15日
 */
public class TagCloudActivity1 extends BaseActivity {

	private TagCloudView mTagCloudView;
	private List<District> districtList = new ArrayList<District>();
	private List<Tag> myTagList = new ArrayList<Tag>();
	private DBUtil dbUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tag_cloud1);
		
		dbUtil = new DBUtil(this);
		
		// 得到屏幕分辨率
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		// 创建所需的标记列表:
		// 注意: 所有标签必须有独特的文本字段
		// 如果没有,只有第一次出现将被添加,其余的将被忽略
		initData();
		initTags();

		// 创建TagCloudview MainActivity并将它设置为内容
		// 通过当前上下文
		mTagCloudView = new TagCloudView(this, width, height, myTagList, districtList);
		mTagCloudView.requestFocus();
		mTagCloudView.setFocusableInTouchMode(true);
		
		LinearLayout ll_tag_layout = (LinearLayout) 
				findViewById(R.id.ll_tag_cloud);
		ll_tag_layout.addView(mTagCloudView);
	}

	private void initTags() {
		Random r = new Random();
		for (int i = 0; i < districtList.size(); i++) {
			int n = r.nextInt(4);
			myTagList.add(new Tag(districtList.get(i).
					getDistrict(),n+1, districtList.get(i).getIndex()));
		}
	}

	public void initData() {
		// 获取中国省市区信息
		List<String[]> list = dbUtil.query(BaseData.TB_DISTRICT, 
				new String[] { BaseData.DISTRICT_ID, 
						BaseData.DISTRICT_ITEM },
				new String[] { BaseData.DISTRICT_FLAG },
				new String[] { "0" });
		
		for (String[] item : list) {
			District district = new District(item[1], item[0]);
			districtList.add(district);
		}
	}
}
