package com.doris.peach.activity.turn;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.domain.Painting;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.turn.FoldableListLayout;
import com.doris.peachlibrary.view.turn.util.OnItemPanitingClickListener;
import com.doris.peachlibrary.view.turn.util.PaintingsAdapter;

import android.os.Bundle;
import android.view.View;

/**
 * 
 * @author Doris
 *
 * 2016年8月1日
 */
public class TurnPicVActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_turn_picv);
		
		PaintingsAdapter adapter = new PaintingsAdapter(this);
		adapter.setOnItemPanitingClickListener(new OnItemPanitingClickListener(){

			@Override
			public void onItemPaintingClick(View view, Painting item) {
				// TODO Auto-generated method stub
				ToastView.showWhiteContentToast(TurnPicVActivity.this,
						R.drawable.ic_toast_flag_verbose,
						item.getTitle());
			}
			
		});
		FoldableListLayout foldableListLayout = (FoldableListLayout) findViewById(R.id.foldable_list);
		foldableListLayout.setAdapter(adapter);
	}

}
