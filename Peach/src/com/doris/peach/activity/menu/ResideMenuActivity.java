package com.doris.peach.activity.menu;

import com.doris.peach.R;
import com.doris.peach.activity.BaseFragmentActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.menu.ResideMenu;
import com.doris.peachlibrary.view.menu.ResideMenuItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author Doris
 *
 * 2016年7月26日
 */
public class ResideMenuActivity extends BaseFragmentActivity implements View.OnClickListener {

	private ResideMenu resideMenu;
	private ResideMenuItem itemHome;
	private ResideMenuItem itemProfile;
	private ResideMenuItem itemCalendar;
	private ResideMenuItem itemSettings;
	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        	ToastView.showWhiteContentToast(ResideMenuActivity.this,
        			R.drawable.ic_toast_flag_verbose, "Menu is opened!");
        }

        @Override
        public void closeMenu() {
        	ToastView.showWhiteContentToast(ResideMenuActivity.this,
        			R.drawable.ic_toast_flag_verbose, "Menu is closed!");
        }
    };
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_automenu_reside);
		setUpMenu();
		if(arg0 == null)
			changeFragment(new ResideMenuHomeFragment());
	}
	
	public ResideMenu getResideMenu(){
        return resideMenu;
    }
	
	private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
	
	private void setUpMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "Profile");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "Calendar");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == itemHome){
            changeFragment(new ResideMenuHomeFragment());
        }else if (view == itemProfile){
            changeFragment(new ResideMenuProfileFragment());
        }else if (view == itemCalendar){
            changeFragment(new ResideMenuCalendarFragment());
        }else if (view == itemSettings){
            changeFragment(new ResideMenuSettingsFragment());
        }
        resideMenu.closeMenu();
	}
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

}