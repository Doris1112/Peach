package com.doris.peach.activity.card;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.domain.Painting;
import com.doris.peachlibrary.view.turn.UnfoldableView;
import com.doris.peachlibrary.view.turn.util.GlideHelper;
import com.doris.peachlibrary.view.turn.util.OnItemPanitingClickListener;
import com.doris.peachlibrary.view.turn.util.PaintingsAdapter;
import com.doris.peachlibrary.view.turn.util.GlanceFoldShading;
import com.doris.peachlibrary.view.turn.util.SpannableBuilder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 *         2016年8月1日
 */
public class UnfoldableDetailsActivity extends BaseActivity {

	private View listTouchInterceptor;
	private View detailsLayout;
	private UnfoldableView unfoldableView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_unfoldabledetails);
		
		PaintingsAdapter adapter = new PaintingsAdapter(this);
		adapter.setOnItemPanitingClickListener(new OnItemPanitingClickListener() {
			
			@Override
			public void onItemPaintingClick(View view, Painting item) {
				// TODO Auto-generated method stub
				openDetails(view, item);
			}
		});
		ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listTouchInterceptor = findViewById(R.id.touch_interceptor_view);
        listTouchInterceptor.setClickable(false);

        detailsLayout = findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.INVISIBLE);
        
        unfoldableView = (UnfoldableView) findViewById(R.id.unfoldable_view);
        
        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (unfoldableView != null
                && (unfoldableView.isUnfolded() || unfoldableView.isUnfolding())) {
            unfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, Painting painting) {
        final ImageView image = (ImageView) detailsLayout.findViewById(R.id.details_image);
        final TextView title = (TextView) detailsLayout.findViewById(R.id.details_title);
        final TextView description = (TextView) detailsLayout.findViewById(R.id.details_text);

        GlideHelper.loadPaintingImage(image, painting);
        title.setText(painting.getTitle());

        SpannableBuilder builder = new SpannableBuilder(this);
        builder
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(R.string.year).append(": ")
                .clearStyle()
                .append(painting.getYear()).append("\n")
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(R.string.location).append(": ")
                .clearStyle()
                .append(painting.getLocation());
        description.setText(builder.build());

        unfoldableView.unfold(coverView, detailsLayout);
    }
}
