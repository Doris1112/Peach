package com.doris.peach.activity.menu;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.menu.FloatingActionButton1;
import com.doris.peachlibrary.view.menu.FloatingActionsMenu;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author Doris
 *
 * 2016年7月25日
 */
public class FloatingActionButtonActivity1 extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_automenu_floatingactionbutton1);
		
		findViewById(R.id.pink_icon).setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  ToastView.showWhiteContentToast(FloatingActionButtonActivity1.this,
		    			  R.drawable.ic_toast_flag_verbose,
		    			  "Clicked pink Floating Action Button");
		      }
		    });

		    FloatingActionButton1 button = (FloatingActionButton1) findViewById(R.id.setter);
		    button.setSize(FloatingActionButton1.SIZE_MINI);
		    button.setColorNormalResId(R.color.pink);
		    button.setColorPressedResId(R.color.pink_pressed);
		    button.setIcon(R.drawable.ic_fab_star);
		    button.setStrokeVisible(false);

		    final View actionB = findViewById(R.id.action_b);

		    FloatingActionButton1 actionC = new FloatingActionButton1(getBaseContext());
		    actionC.setTitle("Hide/Show Action above");
		    actionC.setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
		      }
		    });

		    final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
		    menuMultipleActions.addButton(actionC);

		    final FloatingActionButton1 removeAction = (FloatingActionButton1) findViewById(R.id.button_remove);
		    removeAction.setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
		      }
		    });

		    ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
		    drawable.getPaint().setColor(Color.WHITE);
		    ((FloatingActionButton1) findViewById(R.id.setter_drawable)).setIconDrawable(drawable);

		    final FloatingActionButton1 actionA = (FloatingActionButton1) findViewById(R.id.action_a);
		    actionA.setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View view) {
		        actionA.setTitle("Action A clicked");
		      }
		    });

		    // Test that FAMs containing FABs with visibility GONE do not cause crashes
		    findViewById(R.id.button_gone).setVisibility(View.GONE);

		    final FloatingActionButton1 actionEnable = (FloatingActionButton1) findViewById(R.id.action_enable);
		    actionEnable.setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View view) {
		        menuMultipleActions.setEnabled(!menuMultipleActions.isEnabled());
		      }
		    });

		    FloatingActionsMenu rightLabels = (FloatingActionsMenu) findViewById(R.id.right_labels);
		    FloatingActionButton1 addedOnce = new FloatingActionButton1(this);
		    addedOnce.setTitle("Added once");
		    rightLabels.addButton(addedOnce);

		    FloatingActionButton1 addedTwice = new FloatingActionButton1(this);
		    addedTwice.setTitle("Added twice");
		    rightLabels.addButton(addedTwice);
		    rightLabels.removeButton(addedTwice);
		    rightLabels.addButton(addedTwice);
	}
	
}
