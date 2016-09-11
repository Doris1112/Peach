package com.doris.peach.activity.step;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peachlibrary.view.stepview.VerticalStepView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author Doris
 *
 * 2016年8月13日
 */
public class VerticalStepViewReverseFragment extends Fragment{
	
	View mView;
	private VerticalStepView mSetpview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = View.inflate(getActivity(), R.layout.fragment_step_vertical, null);
		return mView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mSetpview = (VerticalStepView) mView.findViewById(R.id.vstepview);

		List<String> list0 = new ArrayList<>();
		list0.add("您已提交定单，等待系统确认");
		list0.add("您的商品需要从外地调拨，我们会尽快处理，请耐心等待");
		list0.add("您的订单已经进入亚洲第一仓储中心1号库准备出库");
		list0.add("您的订单预计6月23日送达您的手中，618期间促销火爆，可能影响送货时间，请您谅解，我们会第一时间送到您的手中");
		list0.add("您的订单已打印完毕");
		list0.add("您的订单已拣货完成");
		list0.add("扫描员已经扫描");
		list0.add("打包成功");
		list0.add("您的订单在京东【华东外单分拣中心】发货完成，准备送往京东【北京通州分拣中心】");
		list0.add("您的订单在京东【北京通州分拣中心】分拣完成");
		list0.add("您的订单在京东【北京通州分拣中心】发货完成，准备送往京东【北京中关村大厦站】");
		list0.add("您的订单在京东【北京中关村大厦站】验货完成，正在分配配送员");
		list0.add("配送员【包牙齿】已出发，联系电话【130-0000-0000】，感谢您的耐心等待，参加评价还能赢取好多礼物哦");
		list0.add("感谢你在京东购物，欢迎你下次光临！");
		// 设置完成的步数
		mSetpview.setStepsViewIndicatorComplectingPosition(list0.size() - 2)
				// 总步骤
				.setStepViewTexts(list0)
				// 设置indicator线与线间距的比例系数
				.setTextSize(12).setLinePaddingProportion(0.85f)
				// 设置StepsViewIndicator完成线的颜色
				.setStepsViewIndicatorCompletedLineColor(getActivity().getResources().getColor(android.R.color.white))
				// 设置StepsViewIndicator未完成线的颜色
				.setStepsViewIndicatorUnCompletedLineColor(getActivity().getResources().getColor(R.color.uncompleted_text_color))
				// 设置StepsView文字完成的颜色
				.setStepViewComplectedTextColor(getActivity().getResources().getColor(android.R.color.white))
				// 设置StepsView文字未完成的颜色
				.setStepViewUnComplectedTextColor(getActivity().getResources().getColor(R.color.uncompleted_text_color))
				// 设置StepsViewIndicator完成图标
				.setStepsViewIndicatorCompleteIcon(getActivity().getResources().getDrawable(R.drawable.complted))
				// 设置StepsViewIndicator默认图标
				.setStepsViewIndicatorDefaultIcon(getActivity().getResources().getDrawable(R.drawable.default_icon))
				// 设置StepsViewIndicator正在执行图标
				.setStepsViewIndicatorAttentionIcon(getActivity().getResources().getDrawable(R.drawable.attention));
	}
	
}
