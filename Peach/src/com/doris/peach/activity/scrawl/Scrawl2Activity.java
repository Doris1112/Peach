package com.doris.peach.activity.scrawl;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.BitmapUtil;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.util.Log;
import com.doris.peachlibrary.view.ToastView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 * 2016年6月24日
 */
public class Scrawl2Activity extends BaseActivity implements OnTouchListener{
	
	private ImageView iv_scrawl2;
	private Button b_scrawl2_save;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final int CHOOSEPICTURE_REQUESTCODE = 0;
	private Bitmap bitmap, alteredBitmap;
	private Canvas canves;
	private Paint paint;
	private Matrix matrix;
	private float downX = 0, downY = 0, upX = 0, upY = 0; 
	
	private DialogUtil dialogUtil;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				dialogUtil.hideDialogLoading();
				Bundle bundle = msg.getData();
				Intent intent = new Intent(Scrawl2Activity.this, ShowScrawlActivity.class);
				intent.putExtra("path", bundle.getString("path"));
				intent.putExtra("save", true);
				startActivity(intent);
				break;
			case 1:
				dialogUtil.hideDialogLoading();
				dialogUtil.showAlertDialog1("保存失败！", null);
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrawl2);
		
		b_scrawl2_save = (Button) findViewById(R.id.b_scrawl2_save);
		b_scrawl2_save.setEnabled(false);
		iv_scrawl2 = (ImageView) findViewById(R.id.iv_scrawl2);
		iv_scrawl2.setOnTouchListener(this);
		
		dialogUtil = new DialogUtil(this);
	}
	
	public void manage(View view){
		switch (view.getId()) {
		case R.id.b_scrawl2_bg:
			if(b_scrawl2_save.isEnabled())
				b_scrawl2_save.setEnabled(false);
			Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, CHOOSEPICTURE_REQUESTCODE);
			break;
		case R.id.b_scrawl2_save:
			if(alteredBitmap != null){
				final String imageName = sdf.format(new Date()) + ".png";
				dialogUtil.dialogLoading("正在保存……");
				new Thread(){
					public void run() {
						try {
							if(BitmapUtil.saveImg(alteredBitmap, ScrawlActivity.path + imageName)){
								Message msg = new Message();
								msg.what = 0;
								Bundle bundle = msg.getData();
								bundle.putString("path", ScrawlActivity.path + imageName);
								handler.sendMessage(msg);
							}else{
								handler.sendEmptyMessage(1);
							}
						} catch (Exception e) {
							// TODO: handle exception
							Log.getInstance().writeLog("在图片上涂鸦保存出错：", e);
						}
					};
				}.start();
			}else{
				ToastView.showWhiteContentToast(this,
						R.drawable.ic_toast_flag_verbose,
						getString(R.string.selectscrawlbg));
			}
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(canves != null){
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = event.getX();
				downY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				upX = event.getX();
				upY = event.getY();
				canves.drawLine(downX, downY, upX, upY, paint);
				iv_scrawl2.invalidate();
				downX = upX;
				downY = upY;
				break;
			case MotionEvent.ACTION_UP:
				upX = event.getX();
				upY = event.getY();
				canves.drawLine(downX, downY, upX, upY, paint);
				iv_scrawl2.invalidate();
				break;
			}
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			Uri uri = data.getData();
			Display currenDisplay = getWindowManager().getDefaultDisplay();
			int dw = currenDisplay.getWidth();
			int dh = currenDisplay.getHeight();
			Options opts = new Options();
			opts.inJustDecodeBounds = false;
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),
						null, opts);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.getInstance().writeLog("在图片上涂鸦选择背景出错：", e);
			}
			if(dh < bitmap.getHeight() || dw < bitmap.getWidth()){
				int hRatio = (int) Math.ceil(opts.outHeight / (float) dh);
				int wRatio = (int) Math.ceil(opts.outWidth / (float) dw);
				if(hRatio > 1 || wRatio > 1){
					if(hRatio > wRatio){
						opts.inSampleSize = hRatio;
					}else{
						opts.inSampleSize = wRatio;
					}
				}
			}
			opts.inJustDecodeBounds = false;
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),
						null, opts);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.getInstance().writeLog("在图片上涂鸦选择背景出错：", e);
			}
			alteredBitmap = Bitmap.createBitmap(bitmap.getWidth(), 
					bitmap.getHeight(), bitmap.getConfig());
			
			canves = new Canvas(alteredBitmap);
			paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(10);
			matrix = new Matrix();
			canves.drawBitmap(bitmap, matrix, paint);
			iv_scrawl2.setImageBitmap(alteredBitmap);
			b_scrawl2_save.setEnabled(true);
		}
	}
}