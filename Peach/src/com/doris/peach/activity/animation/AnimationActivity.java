package com.doris.peach.activity.animation;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peach.data.BaseData;
import com.doris.peachlibrary.adapter.ListButtonAdapter;
import com.doris.peachlibrary.dialog.Interface.OnClickDialogListViewItemListener;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.util.animation.SwitchAnimationUtil.AnimationType;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * 动画
 * @author Doris
 *
 * 2016年5月9日
 */
public class AnimationActivity extends BaseActivity {
	
	private List<String> list = new ArrayList<String>();
	private List<OnClickListener> listener = new ArrayList<OnClickListener>(); 
	private Context context;
	private DialogUtil dialogUtil;
	private static final AnimationType[] Types = { AnimationType.SCALE, AnimationType.ROTATE,
			AnimationType.FLIP_HORIZON, AnimationType.FLIP_VERTICAL, AnimationType.HORIZION_LEFT,
			AnimationType.HORIZION_RIGHT, AnimationType.ALPHA, AnimationType.HORIZON_CROSS };
	private String[] listAnimation = new String[] { "scale", "rotate", "flip_horizon", "flip_vertial",
			"horizon_left", "horizon_right", "alpha", "cross" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_title);
		
		setTitle();
		initList();
		
		ListView lv_listButton = (ListView) findViewById(R.id.lv_listButton);
		lv_listButton.setAdapter(new ListButtonAdapter(list, listener, context));
	}
	
	private void initList(){
		context = this;
		dialogUtil = new DialogUtil(context);
		
		list.add(getString(R.string.animationGame));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, AnimationGameActivity.class));
			}
		});
		list.add(getString(R.string.animation3D));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, Animation3DActivity.class));
			}
		});
		list.add(getString(R.string.pageSwitchingAnimation));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(getResources().getStringArray(R.array.anim_type),
						getString(R.string.pageSwitchingAnimation),
						new OnClickDialogListViewItemListener() {

							@Override
							public void onClickClickDialogListViewItem(int position) {
								// TODO Auto-generated method stub
								goNext(position);
							}
						}, true);
			}
		});
		list.add(getString(R.string.commonAnimation));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, AnimationCommonActivity.class));
			}
		});
		list.add(getString(R.string.pageViewAnimation));
		listener.add(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(listAnimation, getString(R.string.pageViewAnimation),
						new OnClickDialogListViewItemListener() {

							@Override
							public void onClickClickDialogListViewItem(int position) {
								// TODO Auto-generated method stub
								BaseData.PAGE_VIEW_ANIM = Types[position];
								startActivity(new Intent(context, PageViewActivity.class));
							}
						}, true);
			}
		});
	}	
	
	private void goNext(int index){
		Intent it = new Intent(this, AnimationNextActivity.class);
		startActivityForResult(it, 0);

		switch (index) {
		case 0:
			overridePendingTransition(R.anim.fade, R.anim.hold);
			break;
		case 1:
			overridePendingTransition(R.anim.my_scale_action,
					R.anim.my_alpha_action);
			break;
		case 2:
			overridePendingTransition(R.anim.scale_rotate,
					R.anim.my_alpha_action);
			break;
		case 3:
			overridePendingTransition(R.anim.scale_translate_rotate,
					R.anim.my_alpha_action);
			break;
		case 4:
			overridePendingTransition(R.anim.scale_translate,
					R.anim.my_alpha_action);
			break;
		case 5:
			overridePendingTransition(R.anim.hyperspace_in,
					R.anim.hyperspace_out);
			break;
		case 6:
			overridePendingTransition(R.anim.push_left_in,
					R.anim.push_left_out);
			break;
		case 7:
			overridePendingTransition(R.anim.push_up_in,
					R.anim.push_up_out);
			break;
		case 8:
			overridePendingTransition(R.anim.slide_left,
					R.anim.slide_right);
			break;
		case 9:
			overridePendingTransition(R.anim.wave_scale,
					R.anim.my_alpha_action);
			break;
		case 10:
			overridePendingTransition(R.anim.zoom_enter,
					R.anim.zoom_exit);
			break;
		case 11:
			overridePendingTransition(R.anim.slide_up_in,
					R.anim.slide_down_out);
			break;
		}
	}
}