package com.doris.peach.activity.notebook;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.http.util.EncodingUtils;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.dialog.Interface.DialogButtonClickListener;
import com.doris.peachlibrary.dialog.Interface.DialogYesClickListener;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.util.Log;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author Doris
 *
 * 2016年7月20日
 */
public class AddNoteBookActivity extends BaseActivity {
	
	private Button b_save; 
	private EditText et_notebook;
	
	private DialogUtil dialogUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notebook_add);
		
		final boolean isUpdate = getIntent().getBooleanExtra("isUpdate", false);
		final String fileName = getIntent().getStringExtra("fileName");
		
		dialogUtil = new DialogUtil(this);
		et_notebook = (EditText) findViewById(R.id.et_notebook);
		b_save = (Button) findViewById(R.id.b_save);
		b_save.setEnabled(false);
		b_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isUpdate){
					try{
						File file = new File(NoteBookActivity.NOTEBOOK_PATH + fileName);
						if(file.exists()){
							DataOutputStream output = new DataOutputStream(new FileOutputStream(file));
							output.writeUTF(et_notebook.getText().toString());
							output.close();
							ToastView.showWhiteContentToast(AddNoteBookActivity.this,
									R.drawable.ic_toast_flag_ok, 
									"记事本修改成功！" + NoteBookActivity.NOTEBOOK_PATH + fileName);
							AddNoteBookActivity.this.finish();
						}
					}catch(Exception e){
						Log.getInstance().writeLog("修改记事本出错：", e);
						ToastView.showWhiteContentToast(AddNoteBookActivity.this,
								R.drawable.ic_toast_flag_verbose, 
								"记事本修改失败！" + NoteBookActivity.NOTEBOOK_PATH + fileName);
					}
				} else {
					dialogUtil.showEdit("新建记事本", "请输入记事本名称", new DialogYesClickListener() {

						@Override
						public void onClickYes(Object... objects) {
							// TODO Auto-generated method stub
							String fileName = objects[0].toString();
							File file = new File(NoteBookActivity.NOTEBOOK_PATH);
							if (!file.exists()) {
								file.mkdirs();
							}
							if (!fileName.endsWith(".txt")) {
								fileName = fileName + ".txt";
							}
							final File textFile = new File(NoteBookActivity.NOTEBOOK_PATH + fileName);
							if (textFile.exists()) {
								dialogUtil.showAlertDialog("记事本已存在是否替换？", new DialogButtonClickListener() {

									@Override
									public void onClickYes() {
										// TODO Auto-generated method stub
										textFile.delete();
										writeText(textFile);
									}

									@Override
									public void onClickNo() {
										// TODO Auto-generated method stub

									}
								});
							} else {
								writeText(textFile);
							}
						}
					});
				}
			}
		});
		et_notebook.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(!TextUtils.isEmpty(et_notebook.getText().toString())){
					b_save.setEnabled(true);
				}else{
					b_save.setEnabled(false);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		if(isUpdate){
			File file = new File(NoteBookActivity.NOTEBOOK_PATH +fileName );
			if(file.exists()){
				String res = "";
				try{
					DataInputStream input = new DataInputStream(new FileInputStream(file));
					int length = input.available();
					byte[] buffer = new byte[length];
					input.read(buffer);
					res = EncodingUtils.getString(buffer, "UTF-8");
					input.close();
					et_notebook.setText(res);
					et_notebook.setSelection(res.length());
				}catch(Exception e){
					Log.getInstance().writeLog("读取记事本出错：", e);
				}
			}
		}
	}
	
	private void writeText(final File textFile) {
		try {
			textFile.createNewFile();
			DataOutputStream output = new DataOutputStream(new FileOutputStream(textFile));
			output.writeUTF(et_notebook.getText().toString());
			output.close();
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_ok, 
					"记事本保存成功！" + NoteBookActivity.NOTEBOOK_PATH + textFile.getName());
			this.finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.getInstance().writeLog("创建记事本出错：", e);
			ToastView.showWhiteContentToast(this, R.drawable.ic_toast_flag_verbose, 
					"记事本保存失败！");
		}
	}
}
