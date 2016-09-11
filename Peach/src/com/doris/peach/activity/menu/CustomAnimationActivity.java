package com.doris.peach.activity.menu;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.menu.FloatingActionButton;
import com.doris.peachlibrary.view.menu.SubActionButton;
import com.doris.peachlibrary.view.menu.util.FloatingActionMenu;
import com.doris.peachlibrary.view.menu.util.SlideInAnimationHandler;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 *         2016年7月25日
 */
public class CustomAnimationActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_automenu_fragmentbutton);
		getFragmentManager().beginTransaction().add(R.id.container, new CustomAnimationDemoFragment()).commit();
	}

	public static class CustomAnimationDemoFragment extends Fragment {

		public CustomAnimationDemoFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_automenu_floatingactionbutton, container, false);

			ImageView fabContent = new ImageView(getActivity());
			fabContent.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_settings));

			FloatingActionButton darkButton = new FloatingActionButton.Builder(getActivity())
					.setTheme(FloatingActionButton.THEME_DARK).setContentView(fabContent)
					.setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER).build();

			SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(getActivity())
					.setTheme(SubActionButton.THEME_DARK);
			ImageView rlIcon1 = new ImageView(getActivity());
			ImageView rlIcon2 = new ImageView(getActivity());
			ImageView rlIcon3 = new ImageView(getActivity());
			ImageView rlIcon4 = new ImageView(getActivity());
			ImageView rlIcon5 = new ImageView(getActivity());

			rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_chat));
			rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera));
			rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video));
			rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place));
			rlIcon5.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_headphones));

			// Set 4 SubActionButtons
			// FloatingActionMenu centerBottomMenu = 
			new FloatingActionMenu.Builder(getActivity()).setStartAngle(0)
					.setEndAngle(-180).setAnimationHandler(new SlideInAnimationHandler())
					.addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
					.addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
					.addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
					.addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
					.addSubActionView(rLSubBuilder.setContentView(rlIcon5).build()).attachTo(darkButton).build();

			return rootView;
		}
	}
}
