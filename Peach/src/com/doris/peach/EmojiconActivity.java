package com.doris.peach;

import com.doris.peach.activity.BaseFragmentActivity;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Emojicon表情
 * @author Doris
 *
 * 2016年5月3日
 */
public class EmojiconActivity extends BaseFragmentActivity 
	implements EmojiconGridFragment.OnEmojiconClickedListener, 
		EmojiconsFragment.OnEmojiconBackspaceClickedListener{

	private EmojiconTextView etv_text;
	private EmojiconEditText eet_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emojicon);
		
		etv_text = (EmojiconTextView) findViewById(R.id.etv_text);
		eet_text = (EmojiconEditText) findViewById(R.id.eet_text);
		
		eet_text.addTextChangedListener(new TextWatcherAdapter());
	}

	@Override
	public void onEmojiconBackspaceClicked(View v) {
		// TODO Auto-generated method stub
		EmojiconsFragment.backspace(eet_text);
	}

	@Override
	public void onEmojiconClicked(Emojicon emojicon) {
		// TODO Auto-generated method stub
		EmojiconsFragment.input(eet_text, emojicon);
	}
	
	class TextWatcherAdapter implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, 
        		int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, 
        		int start, int before, int count) {
        	etv_text.setText(s);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

}
