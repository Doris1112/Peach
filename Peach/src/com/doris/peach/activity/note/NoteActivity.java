package com.doris.peach.activity.note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peach.database.DBUtil;
import com.doris.peachlibrary.domain.Note;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年7月21日
 */
public class NoteActivity extends BaseActivity {

	private ImageButton ib_delete;
	private Button b_addNote;
	private ListView lv_notes;
	private NotesAdapter adapter;

	private List<Note> notes = new ArrayList<Note>();
	private int[] bgs = { R.drawable.list_blue_down, R.drawable.list_blue_middle, R.drawable.list_blue_single,
			R.drawable.list_blue_up, R.drawable.list_green_down, R.drawable.list_green_middle,
			R.drawable.list_green_single, R.drawable.list_green_up, R.drawable.list_red_down,
			R.drawable.list_red_middle, R.drawable.list_red_single, R.drawable.list_red_up, R.drawable.list_white_down,
			R.drawable.list_white_middle, R.drawable.list_white_single, R.drawable.list_white_up,
			R.drawable.list_yellow_down, R.drawable.list_yellow_middle, R.drawable.list_yellow_single,
			R.drawable.list_yellow_up, };
	private DBUtil dbUtil;
	private boolean isCheck = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);

		dbUtil = new DBUtil(this);

		b_addNote = (Button) findViewById(R.id.b_addNote);
		ib_delete = (ImageButton) findViewById(R.id.ib_delete);
		lv_notes = (ListView) findViewById(R.id.lv_notes);
		adapter = new NotesAdapter();
		lv_notes.setAdapter(adapter);
		init();
		adapter.notifyDataSetChanged();

		ib_delete.setVisibility(View.GONE);
		lv_notes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (position < notes.size()) {
					Intent intent = new Intent(NoteActivity.this, AddNoteActivity.class);
					intent.putExtra("isUpdate", true);
					intent.putExtra("noteContent", notes.get(position).getContent());
					intent.putExtra("noteId", notes.get(position).getId() + "");
					intent.putExtra("noteBg", notes.get(position).getBgIndex());
					startActivity(intent);
				}
			}
		});
		lv_notes.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				b_addNote.setEnabled(false);
				isCheck = true;
				init();
				adapter.notifyDataSetChanged();
				Animation anim = AnimationUtils.loadAnimation(NoteActivity.this, R.anim.dialog_in);
				ib_delete.setVisibility(View.VISIBLE);
				ib_delete.startAnimation(anim);
				return true;
			}
		});
		ib_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (Note note : notes) {
					if (note.isCheck()) {
						dbUtil.delete(BaseData.TB_NOTE, new String[] { BaseData.NOTE_ID },
								new String[] { note.getId() + "" });
					}
				}
				init();
				isCheck = false;
				adapter.notifyDataSetChanged();
				exitDelete();
			}
		});
	}

	private void init() {
		notes.clear();
		List<String[]> list = dbUtil.getAllInfo(BaseData.TB_NOTE);
		for (String[] item : list) {
			notes.add(new Note(Integer.parseInt(item[0]), 
					Integer.parseInt(item[3]), item[1], Long.parseLong(item[2]),
					false));
		}
		Collections.sort(notes, new Comparator<Note>() {

			@Override
			public int compare(Note lhs, Note rhs) {
				// TODO Auto-generated method stub
				int returnValue = -1;
				if (lhs.getTime() < rhs.getTime()) {
					returnValue = 1;
				}
				return returnValue;
			}

		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
		adapter.notifyDataSetChanged();
	}

	private class NotesAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return notes.size() + 2;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return notes.get(position);
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
			if (convertView == null) {
				convertView = View.inflate(NoteActivity.this, R.layout.item_note, null);
				tag = new Tag();
				tag.bg = (RelativeLayout) convertView.findViewById(R.id.rl_notebg);
				tag.content = (TextView) convertView.findViewById(R.id.tv_noteContent);
				tag.time = (TextView) convertView.findViewById(R.id.tv_noteTime);
				tag.isCheck = (CheckBox) convertView.findViewById(R.id.cb_selectNote);
				convertView.setTag(tag);
			} else {
				tag = (Tag) convertView.getTag();
			}
			if (position >= notes.size()) {
				tag.bg.setBackgroundColor(Color.TRANSPARENT);
				tag.content.setText("");
				tag.time.setText("");
				tag.isCheck.setVisibility(View.GONE);
			} else {
				final Note note = notes.get(position);

				int bgIndex = 0;
				if (notes.size() == 1) {
					bgIndex = note.getBgIndex() * 4 + 2;
				} else {
					if (position == 0) {
						bgIndex = note.getBgIndex() * 4 + 3;
					} else if (position == notes.size() - 1) {
						bgIndex = note.getBgIndex() * 4;
					} else {
						bgIndex = note.getBgIndex() * 4 + 1;
					}
				}
				tag.bg.setBackgroundResource(bgs[bgIndex]);
				tag.content.setText(note.getContent());
				tag.time.setText(DateUtils.getRelativeTimeSpanString(note.getTime()));
				tag.isCheck.setChecked(note.isCheck());
				tag.isCheck.setVisibility(isCheck ? View.VISIBLE : View.GONE);
				tag.time.setVisibility(isCheck ? View.GONE : View.VISIBLE);
				tag.isCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						note.setCheck(isChecked);
						notes.set(position, note);
					}
				});
			}
			return convertView;
		}

		class Tag {
			RelativeLayout bg;
			TextView content;
			TextView time;
			CheckBox isCheck;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (isCheck) {
			isCheck = false;
			adapter.notifyDataSetChanged();
			exitDelete();
		} else {
			super.onBackPressed();
		}
	}

	public void addNote(View view) {
		startActivity(new Intent(this, AddNoteActivity.class));
	}

	private void exitDelete() {
		Animation anim = AnimationUtils.loadAnimation(NoteActivity.this, R.anim.dialog_out);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				ib_delete.setVisibility(View.GONE);
				b_addNote.setEnabled(true);
			}
		});
		ib_delete.startAnimation(anim);
	}
}
