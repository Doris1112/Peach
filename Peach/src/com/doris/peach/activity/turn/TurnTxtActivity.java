package com.doris.peach.activity.turn;

import java.io.IOException;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.DeviceUtil;
import com.doris.peachlibrary.util.Log;
import com.doris.peachlibrary.view.PageWidget;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.util.BookPageFactory;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 
 * @author Doris
 *
 * 2016年6月29日
 */
@SuppressLint("WrongCall")
public class TurnTxtActivity extends BaseActivity {
	
	private PageWidget mPageWidget;
	private Bitmap mCurPageBitmap, mNextPageBitmap;
	private Canvas mCurPageCanvas, mNextPageCanvas;
	private BookPageFactory pagefactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		int mWidth = DeviceUtil.getScreenWidth(this);
		int mHeight = DeviceUtil.getScreenHeight(this);
		
		mPageWidget = new PageWidget(this, mWidth, mHeight);
		setContentView(mPageWidget);
		
		mCurPageBitmap = Bitmap.createBitmap(mWidth, mHeight,
				Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(mWidth, mHeight,
				Bitmap.Config.ARGB_8888);

		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagefactory = new BookPageFactory(mWidth, mHeight);

		pagefactory.setBgBitmap(BitmapFactory
				.decodeResource(this.getResources(), R.drawable.turntxt_bg));

		try {
			pagefactory.openbook(TurnActivity.TEXTPATH);
			pagefactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			ToastView.showWhiteContentToast(TurnTxtActivity.this, 
					R.drawable.ic_toast_flag_verbose,
					TurnActivity.TEXTPATH + " 文件不存在！");
			Log.getInstance().writeLog("Txt翻页读取文件不存在：", e1);
		}

		mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
		mPageWidget.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub
				boolean ret = false;
				if (v == mPageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPageWidget.abortAnimation();
						mPageWidget.calcCornerXY(e.getX(), e.getY());
						pagefactory.onDraw(mCurPageCanvas);
						if (mPageWidget.DragToRight()) {
							try {
								pagefactory.prePage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (pagefactory.isfirstPage())
								return false;
							pagefactory.onDraw(mNextPageCanvas);
						} else {
							try {
								pagefactory.nextPage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (pagefactory.islastPage())
								return false;
							pagefactory.onDraw(mNextPageCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}
					ret = mPageWidget.doTouchEvent(e);
					return ret;
				}
				return false;
			}
		});
	}
}