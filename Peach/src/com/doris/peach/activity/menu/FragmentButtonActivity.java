package com.doris.peach.activity.menu;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.menu.util.FloatingActionMenu;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年7月25日
 */
public class FragmentButtonActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_automenu_fragmentbutton);
		getFragmentManager().beginTransaction().add(R.id.container, new CustomButtonDemoFragment()).commit();
	}

	public static class CustomButtonDemoFragment extends Fragment {

		public CustomButtonDemoFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.layout_automenu_fragmentbutton, container, false);

			// Our action button is this time just a regular view!
			Button centerActionButton = (Button) rootView.findViewById(R.id.centerActionButton);

			// Add some items to the menu. They are regular views as well!
			TextView a = new TextView(getActivity());
			a.setText("a");
			a.setTextColor(Color.WHITE);
			a.setGravity(Gravity.CENTER);
			a.setBackgroundResource(R.drawable.selector_btn_white_frame);
			TextView b = new TextView(getActivity());
			b.setText("b");
			b.setTextColor(Color.WHITE);
			b.setGravity(Gravity.CENTER);
			b.setBackgroundResource(R.drawable.selector_btn_white_frame);
			TextView c = new TextView(getActivity());
			c.setText("c");
			c.setTextColor(Color.WHITE);
			c.setGravity(Gravity.CENTER);
			c.setBackgroundResource(R.drawable.selector_btn_white_frame);
			TextView d = new TextView(getActivity());
			d.setText("d");
			d.setTextColor(Color.WHITE);
			d.setGravity(Gravity.CENTER);
			d.setBackgroundResource(R.drawable.selector_btn_white_frame);
			TextView e = new TextView(getActivity());
			e.setText("e");
			e.setTextColor(Color.WHITE);
			e.setGravity(Gravity.CENTER);
			e.setBackgroundResource(R.drawable.selector_btn_white_frame);
			TextView f = new TextView(getActivity());
			f.setText("f");
			f.setTextColor(Color.WHITE);
			f.setGravity(Gravity.CENTER);
			f.setBackgroundResource(R.drawable.selector_btn_white_frame);
			TextView g = new TextView(getActivity());
			g.setText("g");
			g.setTextColor(Color.WHITE);
			g.setGravity(Gravity.CENTER);
			g.setBackgroundResource(R.drawable.selector_btn_white_frame);
			TextView h = new TextView(getActivity());
			h.setText("h");
			h.setTextColor(Color.WHITE);
			h.setGravity(Gravity.CENTER);
			h.setBackgroundResource(R.drawable.selector_btn_white_frame);
			FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
			a.setLayoutParams(tvParams);
			b.setLayoutParams(tvParams);
			c.setLayoutParams(tvParams);
			d.setLayoutParams(tvParams);
			e.setLayoutParams(tvParams);
			f.setLayoutParams(tvParams);
			g.setLayoutParams(tvParams);
			h.setLayoutParams(tvParams);

			// SubActionButton.Builder subBuilder = new SubActionButton.Builder(getActivity());

			// FloatingActionMenu circleMenu =
			new FloatingActionMenu.Builder(getActivity()).setStartAngle(0) // A whole circle!
					.setEndAngle(360).setRadius(getResources().getDimensionPixelSize(R.dimen.radius_large))
					.addSubActionView(a, new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ToastView.showWhiteContentToast(getActivity(), R.drawable.ic_toast_flag_ok, "a");
						}
					})
					.addSubActionView(b).addSubActionView(c).addSubActionView(d).addSubActionView(e).addSubActionView(f)
					.addSubActionView(g).addSubActionView(h).attachTo(centerActionButton).build();

			return rootView;
		}
	}
}
