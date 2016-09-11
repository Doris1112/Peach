package com.doris.peach.activity.step;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年8月13日
 */
public class FragmentStepActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step);
		
		boolean isHorizontal = getIntent().getBooleanExtra("isHorizontal", false);
		
		if(isHorizontal){
			HorizontalStepviewFragment horizontalFragment = new HorizontalStepviewFragment();
			getFragmentManager().beginTransaction().replace(R.id.container, horizontalFragment).commit();
		}else{
			int order = getIntent().getIntExtra("order", 0);
			switch (order) {
			case 0:
				VerticalStepViewReverseFragment verticalReverseFragment = new VerticalStepViewReverseFragment();
				getFragmentManager().beginTransaction().replace(R.id.container, verticalReverseFragment).commit();
				break;
			default:
				VerticalStepViewFrowardFragment verticalFrowardFragment = new VerticalStepViewFrowardFragment();
				getFragmentManager().beginTransaction().replace(R.id.container, verticalFrowardFragment).commit();
				break;
			}
		}
	}
	
}
