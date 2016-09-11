package com.doris.peach.activity.note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年7月21日
 */
public class AddNoteActivity extends BaseActivity {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DBUtil dbUtil;
	private int index = 0;
	private int[] noteTitle = { 
			R.drawable.edit_title_blue, R.drawable.edit_title_green, R.drawable.edit_title_red, 
			R.drawable.edit_title_white, R.drawable.edit_title_yellow };
	private int[] noteEdit = { 
			R.drawable.edit_blue, R.drawable.edit_green, R.drawable.edit_red, 
			R.drawable.edit_white, R.drawable.edit_yellow };
	private boolean isUpdate;
	private String noteId;
	
	private RelativeLayout rl_titlebg;
	private EditText et_note;
	private Button b_saveNote;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_add);
		
		dbUtil = new DBUtil(this);
		Random random = new Random();
		index = random.nextInt(5);
		
		Intent intent = getIntent();
		isUpdate = intent.getBooleanExtra("isUpdate", false);
		String noteContent = intent.getStringExtra("noteContent");
		noteId = intent.getStringExtra("noteId");
		index = intent.getIntExtra("noteBg", index);
		
		setContentUnderStatus(findViewById(R.id.ll_noteadd_content));
		
		TextView tv_modified_date = (TextView) findViewById(R.id.tv_modified_date);
		tv_modified_date.setText(sdf.format(new Date()));
		
		b_saveNote = (Button) findViewById(R.id.b_saveNote);
		et_note = (EditText) findViewById(R.id.et_note);
		rl_titlebg = (RelativeLayout) findViewById(R.id.rl_titlebg);
		
		b_saveNote.setEnabled(false);
		b_saveNote.setTextColor(Color.parseColor("#808080"));
		rl_titlebg.setBackgroundResource(noteTitle[index]);
		et_note.setBackgroundResource(noteEdit[index]);
		et_note.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(TextUtils.isEmpty(et_note.getText().toString())){
					b_saveNote.setEnabled(false);
					b_saveNote.setTextColor(Color.parseColor("#808080"));
				}else{
					b_saveNote.setEnabled(true);
					b_saveNote.setTextColor(Color.BLACK);
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
			et_note.setText(noteContent);
			et_note.setSelection(noteContent.length());
		}
	}
	
	public void save(View view){
		if(isUpdate){
			dbUtil.update(BaseData.TB_NOTE,
					new String[]{
							BaseData.NOTE_CONTENT,
							BaseData.NOTE_TIME
					}, 
					new String[]{
							et_note.getText().toString(),
							new Date().getTime() + ""
					}, 
					new String[] { BaseData.NOTE_ID },
					new String[]{ noteId });
		}else{
			String[] values ={
					dbUtil.	getColumn(BaseData.TB_NOTE, BaseData.NOTE_ID),
					et_note.getText().toString(),
					new Date().getTime() + "",
					"" + index
			} ;
			dbUtil.insertData(BaseData.TB_NOTE,
					new String[]{
							BaseData.NOTE_ID,
							BaseData.NOTE_CONTENT,
							BaseData.NOTE_TIME,
							BaseData.NOTE_BG_INDEX
			}, values);
		}
		this.finish();
	}
}