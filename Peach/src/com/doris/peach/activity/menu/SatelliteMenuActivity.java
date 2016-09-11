package com.doris.peach.activity.menu;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.menu.SatelliteMenu;
import com.doris.peachlibrary.view.menu.SatelliteMenu.SateliteClickedListener;
import com.doris.peachlibrary.view.menu.util.SatelliteMenuItem;

import android.os.Bundle;

/**
 * 
 * @author Doris
 *
 * 2016年7月26日
 */
public class SatelliteMenuActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_automenu_satellite);
		
		SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
		
		List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(1, R.drawable.ic_1));
        items.add(new SatelliteMenuItem(2, R.drawable.ic_2));
        items.add(new SatelliteMenuItem(3, R.drawable.ic_3));
        items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
        items.add(new SatelliteMenuItem(5, R.drawable.ic_5));
        items.add(new SatelliteMenuItem(6, R.drawable.ic_6));
        
        menu.addItems(items);        
        menu.setOnItemClickedListener(new SateliteClickedListener() {
			
			public void eventOccured(int id) {
				ToastView.showWhiteContentToast(SatelliteMenuActivity.this,
						R.drawable.ic_toast_flag_verbose,
						"Clicked on " + id);
			}
		});
	}
	
}
