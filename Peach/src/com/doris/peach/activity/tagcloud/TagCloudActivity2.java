package com.doris.peach.activity.tagcloud;

import java.util.Random;

import com.doris.peach.R;
import com.doris.peach.R.drawable;
import com.doris.peach.R.id;
import com.doris.peach.R.layout;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.TagCloudView1;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年5月10日
 */
public class TagCloudActivity2 extends BaseActivity implements OnClickListener{
	
	private static final String[] keywords = { "QQ", "Sodino", "APK", "GFW",
			"铅笔", "短信", "桌面精灵", "MacBook Pro", "平板电脑",
			"雅诗兰黛", "卡西欧 TR-100", "笔记本", "SPY Mouse", 
			"Thinkpad E40", "捕鱼达人", "内存清理", "地图", "导航", 
			"闹钟", "主题", "通讯录", "播放器", "CSDN leak", "安全", "3D", 
			"美女", "天气", "4743G", "戴尔", "联想", "欧朋", "浏览器", 
			"愤怒的小鸟", "mmShow", "网易公开课", "iciba", "油水关系", 
			"网游App", "互联网", "365日历", "脸部识别", "Chrome", "Safari", 
			"中国版Siri", "A5处理器", "iPhone4S", "摩托 ME525", "魅族 M9", 
			"尼康 S2500" };
	
	private TagCloudView1 tcv_tag_cloud;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_cloud2);
		
		tcv_tag_cloud = (TagCloudView1) findViewById(R.id.tcv_tag_cloud);
		tcv_tag_cloud.setDuration(800l);
		tcv_tag_cloud.setOnItemClickListener(this);
		
		feedKeywordsFlow();
		tcv_tag_cloud.go2Show(TagCloudView1.ANIMATION_IN);
	}
	
	public void setTagCloud(View view){
		switch (view.getId()) {
		case R.id.b_tagCloudIn:
			tcv_tag_cloud.rubKeywords();
			feedKeywordsFlow();
			tcv_tag_cloud.go2Show(TagCloudView1.ANIMATION_IN);
			break;
		case R.id.b_tagCloudOut:
			tcv_tag_cloud.rubKeywords();
			feedKeywordsFlow();
			tcv_tag_cloud.go2Show(TagCloudView1.ANIMATION_OUT);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		String keyword = ((TextView) v).getText().toString();
		ToastView.showWhiteToast(this,
				R.drawable.ic_toast_flag_verbose,
				keyword);
	}

	private void feedKeywordsFlow() {
		Random random = new Random();
		for (int i = 0; i < TagCloudView1.MAX; i++) {
			int ran = random.nextInt(keywords.length);
			String tmp = keywords[ran];
			tcv_tag_cloud.feedKeyword(tmp);
		}
	}
}
