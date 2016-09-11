package com.doris.peach.activity.notebook;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.domain.NoteBook;
import com.doris.peachlibrary.util.DialogUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年7月17日
 */
public class NoteBookActivity extends BaseActivity {
	
	public static final String NOTEBOOK_PATH = "/sdcard/peach/notebook/";
	private List<NoteBook> notebooks = new ArrayList<NoteBook>(); 
	private boolean isCheck = false;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				notebooks.clear();
				init();
				isCheck = false;
				adapter.notifyDataSetChanged();
				if(notebooks.size() <= 0){
					tv_notebook_hint.setVisibility(View.VISIBLE);
				}
				dialogUtil.hideDialogLoading();
				break;
			}
		};
	};
	private  DialogUtil dialogUtil;
	private NotebookeAdapter adapter;
	private TextView tv_notebook_hint;
	private ListView lv_notebooks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notebook);
		
		setTitle();
		
		dialogUtil = new DialogUtil(this);
		tv_notebook_hint = (TextView) findViewById(R.id.tv_notebook_hint);
		lv_notebooks = (ListView) findViewById(R.id.lv_notebooks);
		lv_notebooks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NoteBookActivity.this, AddNoteBookActivity.class);
				intent.putExtra("isUpdate", true);
				intent.putExtra("fileName", notebooks.get(position).getFile().getName());
				startActivity(intent);
			}
		});
		adapter = new NotebookeAdapter();
		lv_notebooks.setAdapter(adapter);
		init();
		adapter.notifyDataSetChanged();
	}
	
	private void init() {
		File file = new File(NOTEBOOK_PATH);
		if (file.exists()) {
			String[] listFile = file.list();
			if (listFile.length > 0) {
				for (String name : listFile) {
					File textFile = new File(NOTEBOOK_PATH + name);
					notebooks.add(new NoteBook(textFile, false));
				}
				tv_notebook_hint.setVisibility(View.GONE);
			}
		}
	}
	
	public void doNotebook(View view){
		switch (view.getId()) {
		case R.id.b_add:
			isCheck = false;
			adapter.notifyDataSetChanged();
			startActivity(new Intent(this, AddNoteBookActivity.class));
			break;
		case R.id.b_delete:
			if(isCheck){
				dialogUtil.dialogLoading("正在删除记事本……");
				new Thread(){
					public void run() {
						for (NoteBook i : notebooks) {
							if(i.isCheck()){
								i.getFile().delete();
							}
						}
						handler.sendEmptyMessage(0);
					};
				}.start();
			}else{
				isCheck = true;
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		notebooks.clear();
		init();
		adapter.notifyDataSetChanged();
	}
	
	private class NotebookeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return notebooks.size();
		}

		@Override
		public NoteBook getItem(int position) {
			// TODO Auto-generated method stub
			return notebooks.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Tag tag;
			if(convertView == null){
				convertView = View.inflate(NoteBookActivity.this, R.layout.item_notebook, null);
				tag = new Tag();
				tag.name = (TextView) convertView.findViewById(R.id.tv_notebookName);
				tag.date = (TextView) convertView.findViewById(R.id.tv_notebookDate);
				tag.select = (CheckBox) convertView.findViewById(R.id.cb_notebookSelect);
				convertView.setTag(tag);
			}else{
				tag = (Tag) convertView.getTag();
			}
			final NoteBook notebook = getItem(position);
			tag.name.setText(notebook.getFile().getName());
			tag.date.setText(sdf.format(new Date(notebook.getFile().lastModified())));
			tag.select.setChecked(notebook.isCheck());
			tag.select.setVisibility(isCheck ? View.VISIBLE : View.GONE);
			tag.select.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					notebook.setCheck(isChecked);
					notebooks.set(position, notebook);
				}
			});
			
			return convertView;
		}
		
		class Tag{
			TextView name;
			TextView date;
			CheckBox select;
		}
	}
}
