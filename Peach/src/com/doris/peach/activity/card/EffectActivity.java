package com.doris.peach.activity.card;

import java.util.Arrays;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.activity.card.util.TestStackAdapter;
import com.doris.peachlibrary.view.card.CardStackView;
import com.doris.peachlibrary.view.card.util.AllMoveDownAnimatorAdapter;
import com.doris.peachlibrary.view.card.util.UpDownAnimatorAdapter;
import com.doris.peachlibrary.view.card.util.UpDownStackAnimatorAdapter;

import android.os.Bundle;
import android.os.Handler;

/**
 * 
 * @author Doris
 *
 * 2016年8月15日
 */
public class EffectActivity extends BaseActivity {
	
	 public static Integer[] TEST_DATAS = new Integer[]{
	            R.color.color_1,
	            R.color.color_2,
	            R.color.color_3,
	            R.color.color_4,
	            R.color.color_5,
	            R.color.color_6,
	            R.color.color_7,
	            R.color.color_8,
	            R.color.color_9,
	            R.color.color_10,
	            R.color.color_11,
	            R.color.color_12,
	            R.color.color_13,
	            R.color.color_14,
	            R.color.color_15,
	            R.color.color_16,
	            R.color.color_17,
	            R.color.color_18,
	            R.color.color_19,
	            R.color.color_20,
	            R.color.color_21,
	            R.color.color_22,
	            R.color.color_23,
	            R.color.color_24,
	            R.color.color_25,
	            android.R.color.black
	    };

	 private CardStackView mStackView;
	 private TestStackAdapter mTestStackAdapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_effect);
		mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mTestStackAdapter = new TestStackAdapter(this);
        mStackView.setAdapter(mTestStackAdapter);


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );
        
        switch (getIntent().getIntExtra("animationAdapter", 0)) {
		case 0:
			mStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(mStackView));
			break;
		case 1:
			mStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(mStackView));
			break;
		case 2:
			mStackView.setAnimatorAdapter(new UpDownStackAnimatorAdapter(mStackView));
			break;
		}
    }
	
}
