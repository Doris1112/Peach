package com.doris.peach.activity.navigationpage;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseFragmentActivity;
import com.doris.peachlibrary.view.navigationpage.ModelPagerAdapter;
import com.doris.peachlibrary.view.navigationpage.PagerManager;
import com.doris.peachlibrary.view.navigationpage.ScrollerViewPager;
import com.doris.peachlibrary.view.navigationpage.SpringIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 
 * @author Doris
 *
 *         2016年8月2日
 */
public class SpringIndicatorActivity extends BaseFragmentActivity {

	private ScrollerViewPager viewPager;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_navigationpage_springindicator);

		viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
		SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
		
		PagerManager manager = new PagerManager();
        manager.addCommonFragment(getFragments(), getTitles());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        springIndicator.setViewPager(viewPager);
	}
	
	private List<Fragment> getFragments(){
		List<Fragment> list= new ArrayList<Fragment>();
		for(Integer in : getBgRes()){
			Bundle bundle = new Bundle();
			bundle.putInt("data", in);
			GuideFragment fragment = new GuideFragment();
			fragment.setArguments(bundle);
			list.add(fragment);
		}
        return list;
	}
	
	private List<String> getTitles(){
    	List<String> list= new ArrayList<String>();
    	list.add("1");
    	list.add("2");
    	list.add("3");
    	list.add("4");
        return list;
    }

    private List<Integer> getBgRes(){
    	List<Integer> list= new ArrayList<Integer>();
    	list.add(R.drawable.bg1);
    	list.add(R.drawable.bg2);
    	list.add(R.drawable.bg3);
    	list.add(R.drawable.bg4);
        return list;
    }
}
