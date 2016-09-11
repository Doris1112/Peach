package com.doris.peach.activity.step;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peachlibrary.view.stepview.HorizontalStepView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author Doris
 *
 *         2016年8月13日
 */
public class HorizontalStepviewFragment extends Fragment {

	View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = View.inflate(container.getContext(), R.layout.fragment_step_horizontal, null);
		showSetpView0();
		showSetpView1();
		showSetpView2();
		showSetpView3();
		showSetpView4();
		showSetpView5();
		showSetpView6();
		return mView;
	}

	private void showSetpView0() {
		HorizontalStepView setpview0 = (HorizontalStepView) mView.findViewById(R.id.step_view0);
		List<String> list0 = new ArrayList<>();
		list0.add("接单");
		list0.add("打包");
		list0.add("出发");
		list0.add("送单");
		list0.add("完成");
		list0.add("支付");
		setpview0.setStepsViewIndicatorComplectingPosition(2).setStepViewTexts(list0).setTextSize(16)
				.setStepsViewIndicatorCompletedLineColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepsViewIndicatorUnCompletedLineColor(
						getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepViewComplectedTextColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepViewUnComplectedTextColor(getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepsViewIndicatorCompleteIcon(getActivity().getResources().getDrawable(R.drawable.complted))
				.setStepsViewIndicatorDefaultIcon(getActivity().getResources().getDrawable(R.drawable.default_icon))
				.setStepsViewIndicatorAttentionIcon(getActivity().getResources().getDrawable(R.drawable.attention));
	}

	private void showSetpView1() {
		List<String> list1 = new ArrayList<>();
		list1.add("接单");
		HorizontalStepView setpview1 = (HorizontalStepView) mView.findViewById(R.id.step_view1);
		setpview1.setStepsViewIndicatorComplectingPosition(0)
				.setStepViewTexts(list1)
				.setTextSize(8)
				.setStepsViewIndicatorCompletedLineColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepsViewIndicatorUnCompletedLineColor(
						getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepViewComplectedTextColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepViewUnComplectedTextColor(getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepsViewIndicatorCompleteIcon(getActivity().getResources().getDrawable(R.drawable.complted))
				.setStepsViewIndicatorDefaultIcon(getActivity().getResources().getDrawable(R.drawable.default_icon))
				.setStepsViewIndicatorAttentionIcon(getActivity().getResources().getDrawable(R.drawable.attention));
	}

	private void showSetpView2() {
		List<String> list2 = new ArrayList<>();
		list2.add("接单");
		list2.add("打包");
		HorizontalStepView setpview2 = (HorizontalStepView) mView.findViewById(R.id.step_view2);
		setpview2.setStepsViewIndicatorComplectingPosition(0)
				.setStepViewTexts(list2)
				.setTextSize(9)
				.setStepsViewIndicatorCompletedLineColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepsViewIndicatorUnCompletedLineColor(
						getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepViewComplectedTextColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepViewUnComplectedTextColor(getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepsViewIndicatorCompleteIcon(getActivity().getResources().getDrawable(R.drawable.complted))
				.setStepsViewIndicatorDefaultIcon(getActivity().getResources().getDrawable(R.drawable.default_icon))
				.setStepsViewIndicatorAttentionIcon(getActivity().getResources().getDrawable(R.drawable.attention));
	}

	private void showSetpView3() {
		List<String> list3 = new ArrayList<>();
		list3.add("接单");
		list3.add("打包");
		list3.add("出发");
		HorizontalStepView setpview3 = (HorizontalStepView) mView.findViewById(R.id.step_view3);
		setpview3.setStepsViewIndicatorComplectingPosition(1)
				.setStepViewTexts(list3)
				.setTextSize(10)
				.setStepsViewIndicatorCompletedLineColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepsViewIndicatorUnCompletedLineColor(
						getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepViewComplectedTextColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepViewUnComplectedTextColor(getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepsViewIndicatorCompleteIcon(getActivity().getResources().getDrawable(R.drawable.complted))
				.setStepsViewIndicatorDefaultIcon(getActivity().getResources().getDrawable(R.drawable.default_icon))
				.setStepsViewIndicatorAttentionIcon(getActivity().getResources().getDrawable(R.drawable.attention));
	}

	private void showSetpView4() {
		List<String> list4 = new ArrayList<>();
		list4.add("接单");
		list4.add("打包");
		list4.add("出发");
		list4.add("送单");
		HorizontalStepView setpview4 = (HorizontalStepView) mView.findViewById(R.id.step_view4);
		setpview4.setStepsViewIndicatorComplectingPosition(2)
				.setStepViewTexts(list4)
				.setTextSize(11)
				.setStepsViewIndicatorCompletedLineColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepsViewIndicatorUnCompletedLineColor(
						getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepViewComplectedTextColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepViewUnComplectedTextColor(getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepsViewIndicatorCompleteIcon(getActivity().getResources().getDrawable(R.drawable.complted))
				.setStepsViewIndicatorDefaultIcon(getActivity().getResources().getDrawable(R.drawable.default_icon))
				.setStepsViewIndicatorAttentionIcon(getActivity().getResources().getDrawable(R.drawable.attention));
	}

	private void showSetpView5() {
		List<String> list5 = new ArrayList<>();
		list5.add("接单");
		list5.add("打包");
		list5.add("出发");
		list5.add("送单");
		list5.add("完成");
		HorizontalStepView setpview5 = (HorizontalStepView) mView.findViewById(R.id.step_view5);
		setpview5.setStepsViewIndicatorComplectingPosition(3)
				.setStepViewTexts(list5)
				.setTextSize(12)
				.setStepsViewIndicatorCompletedLineColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepsViewIndicatorUnCompletedLineColor(
						getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepViewComplectedTextColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepViewUnComplectedTextColor(getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepsViewIndicatorCompleteIcon(getActivity().getResources().getDrawable(R.drawable.complted))
				.setStepsViewIndicatorDefaultIcon(getActivity().getResources().getDrawable(R.drawable.default_icon))
				.setStepsViewIndicatorAttentionIcon(getActivity().getResources().getDrawable(R.drawable.attention));
	}

	private void showSetpView6() {
		HorizontalStepView setpview6 = (HorizontalStepView) mView.findViewById(R.id.step_view6);

		List<String> list6 = new ArrayList<>();
		list6.add("接单");
		list6.add("打包");
		list6.add("出发");
		list6.add("送单");
		list6.add("完成");
		list6.add("支付");
		setpview6.setStepsViewIndicatorComplectingPosition(4)
				.setStepViewTexts(list6)
				.setTextSize(13)
				.setStepsViewIndicatorCompletedLineColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepsViewIndicatorUnCompletedLineColor(
						getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepViewComplectedTextColor(getActivity().getResources().getColor(android.R.color.white))
				.setStepViewUnComplectedTextColor(getActivity().getResources().getColor(R.color.uncompleted_text_color))
				.setStepsViewIndicatorCompleteIcon(getActivity().getResources().getDrawable(R.drawable.complted))
				.setStepsViewIndicatorDefaultIcon(getActivity().getResources().getDrawable(R.drawable.default_icon))
				.setStepsViewIndicatorAttentionIcon(getActivity().getResources().getDrawable(R.drawable.attention));
	}

}
