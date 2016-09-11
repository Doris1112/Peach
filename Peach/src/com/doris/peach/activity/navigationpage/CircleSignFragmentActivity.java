package com.doris.peach.activity.navigationpage;

import com.doris.peach.R;
import com.doris.peach.activity.BaseFragmentActivity;
import com.doris.peachlibrary.view.navigationpage.ShowcaseView;
import com.doris.peachlibrary.view.navigationpage.util.ViewTarget;
import com.doris.peachlibrary.view.navigationpage.util.SimpleShowcaseEventListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 
 * @author Doris
 *
 *         2016年8月16日
 */
public class CircleSignFragmentActivity extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_navigationpage_circlesign_fragment);
	}

	public void onHiddenFirstShowcase() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_host_two, new SecondDemoFragment())
				.commit();
	}

	public static class FirstDemoFragment extends Fragment {

		private Button button;

		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_navigationpage_circlesign, container, false);
			button = (Button) view.findViewById(R.id.fragment_demo_button);
			return view;
		}

		@Override
		public void onActivityCreated(@Nullable Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			new ShowcaseView.Builder(getActivity()).withMaterialShowcase().setStyle(R.style.CustomShowcaseTheme)
					.setTarget(new ViewTarget(button)).hideOnTouchOutside()
					.setContentTitle(R.string.showcase_fragment_title)
					.setContentText(R.string.showcase_fragment_message)
					.setShowcaseEventListener(new SimpleShowcaseEventListener() {

						@Override
						public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
							((CircleSignFragmentActivity) getActivity()).onHiddenFirstShowcase();
						}

					}).build();
		}

	}

	public static class SecondDemoFragment extends Fragment {

		private Button button;

		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_navigationpage_circlesign, container, false);
			button = (Button) view.findViewById(R.id.fragment_demo_button);
			return view;
		}

		@Override
		public void onActivityCreated(@Nullable Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			new ShowcaseView.Builder(getActivity()).withMaterialShowcase().setStyle(R.style.CustomShowcaseTheme2)
					.setTarget(new ViewTarget(button)).hideOnTouchOutside()
					.setContentTitle(R.string.showcase_fragment_title_2)
					.setContentText(R.string.showcase_fragment_message_2).build();
		}
	}

}
