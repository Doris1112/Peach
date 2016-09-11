package com.doris.peach.activity.menu;

import com.doris.peach.R;
import com.doris.peachlibrary.view.menu.ResideMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 
 * @author Doris
 *
 * 2016年7月26日
 */
public class ResideMenuHomeFragment extends Fragment {

	private View parentView;
    private ResideMenu resideMenu;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	parentView = inflater.inflate(R.layout.fragment_automenu_residehome, container, false);
    	setUpViews();
    	return parentView;
    }
    
    private void setUpViews() {
        ResideMenuActivity parentActivity = (ResideMenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        // add gesture operation's ignored views
        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);
    }
}
