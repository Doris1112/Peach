package com.doris.peach.activity.textdrawable;

import java.util.Arrays;
import java.util.List;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.textdrawable.DrawableProvider;
import com.doris.peachlibrary.util.textdrawable.TextDrawable;
import com.doris.peachlibrary.util.textdrawable.ColorGenerator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年7月17日
 */
public class ListTextDrawableActivity extends BaseActivity {

	private TextDrawable.IBuilder mDrawableBuilder;
	private static final int HIGHLIGHT_COLOR = 0x999be6ff;
    private List<ListData> mDataList = Arrays.asList(
            new ListData("Iron Man"),
            new ListData("Captain America"),
            new ListData("James Bond"),
            new ListData("Harry Potter"),
            new ListData("Sherlock Holmes"),
            new ListData("Black Widow"),
            new ListData("Hawk Eye"),
            new ListData("Iron Man"),
            new ListData("Guava"),
            new ListData("Tomato"),
            new ListData("Pineapple"),
            new ListData("Strawberry"),
            new ListData("Watermelon"),
            new ListData("Pears"),
            new ListData("Kiwi"),
            new ListData("Plums")
    );
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		int type = getIntent().getIntExtra("TYPE", DrawableProvider.SAMPLE_RECT);
		switch (type) {
		case DrawableProvider.SAMPLE_RECT:
			mDrawableBuilder = TextDrawable.builder().rect();
			break;
		case DrawableProvider.SAMPLE_ROUND_RECT:
			mDrawableBuilder = TextDrawable.builder().roundRect(10);
			break;
		case DrawableProvider.SAMPLE_ROUND:
			mDrawableBuilder = TextDrawable.builder().round();
			break;
		case DrawableProvider.SAMPLE_RECT_BORDER:
			mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4).endConfig().rect();
			break;
		case DrawableProvider.SAMPLE_ROUND_RECT_BORDER:
			mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4).endConfig().roundRect(10);
			break;
		case DrawableProvider.SAMPLE_ROUND_BORDER:
			mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4).endConfig().round();
			break;
		}

		ListView lv_textdrawable = (ListView) findViewById(R.id.lv_list);
		lv_textdrawable.setAdapter(new TextDrawableListAdapter());
	}
	
	private class TextDrawableListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDataList.size();
		}

		@Override
		public ListData getItem(int position) {
			// TODO Auto-generated method stub
			return mDataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(ListTextDrawableActivity.this,
						R.layout.item_textdrawable, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			ListData item = getItem(position);
			updateCheckedState(holder, item);
			holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListData data = getItem(position);
                    data.setChecked(!data.isChecked);
                    updateCheckedState(holder, data);
                }
            });
            holder.textView.setText(item.data);

			return convertView;
		}
		
		private void updateCheckedState(ViewHolder holder, ListData item) {
            if (item.isChecked) {
                holder.imageView.setImageDrawable(mDrawableBuilder.build(" ", 0xff616161));
                holder.view.setBackgroundColor(HIGHLIGHT_COLOR);
                holder.checkIcon.setVisibility(View.VISIBLE);
            }
            else {
                TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.data.charAt(0)), mColorGenerator.getColor(item.data));
                holder.imageView.setImageDrawable(drawable);
                holder.view.setBackgroundColor(Color.TRANSPARENT);
                holder.checkIcon.setVisibility(View.GONE);
            }
        }
		
		private class ViewHolder {

			private View view;
	        private ImageView imageView;
	        private TextView textView;
	        private ImageView checkIcon;
	        private ViewHolder(View view) {
	        	this.view = view;
	            imageView = (ImageView) view.findViewById(R.id.iv_image);
	            textView = (TextView) view.findViewById(R.id.tv_text);
	            checkIcon = (ImageView) view.findViewById(R.id.iv_check);
	        }
		}
	}
	
	private class ListData {

        private String data;
        private boolean isChecked;

        public ListData(String data) {
            this.data = data;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }
    }
}
