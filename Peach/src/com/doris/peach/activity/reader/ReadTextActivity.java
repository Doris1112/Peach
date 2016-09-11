package com.doris.peach.activity.reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.doris.peach.R;
import com.doris.peach.R.id;
import com.doris.peach.R.layout;
import com.doris.peach.activity.BaseActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年7月16日
 */
public class ReadTextActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_text);
		
		String filePath = getIntent().getStringExtra("filePath");
		
		TextView tv_read_text = (TextView) findViewById(R.id.tv_read_text);
		tv_read_text.setText(getFromAssets(filePath));
	}
	
	/**
	 * 获取assets文件夹下面的Txt文档
	 * 
	 * @param fileName
	 * @return
	 */
	private String getFromAssets(String fileName) {
		String Result = "";
		try {
			InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			while ((line = bufReader.readLine()) != null)
				Result += line + "\n";
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result;
	}
}
