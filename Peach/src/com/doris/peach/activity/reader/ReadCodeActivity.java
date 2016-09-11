package com.doris.peach.activity.reader;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peachlibrary.view.reader.CodeView;
import com.doris.peachlibrary.view.reader.util.CodeViewTheme;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 *         2016年8月8日
 */
public class ReadCodeActivity extends BaseActivity {

	private CodeView codeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reader_code);
		
		setContentUnderStatus(findViewById(R.id.rl_readcode_content));
		
		int theme = getIntent().getIntExtra("theme", 0);
		boolean isHtml = getIntent().getBooleanExtra("isHtml", false);
		codeView = (CodeView) findViewById(R.id.codeview);
		if(isHtml){
			codeView.setTheme(CodeViewTheme.DRACULA);
			codeView.showCodeHtmlByCssSelect(BaseData.HTML,".code");
		}else{
			codeView.setTheme(CodeViewTheme.listThemes()[theme]);
			codeView.fillColor();
			codeView.showCode(BaseData.CODE);
		}
	}
}
