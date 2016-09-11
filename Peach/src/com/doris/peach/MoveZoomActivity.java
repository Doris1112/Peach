package com.doris.peach;

import com.doris.peach.activity.BaseActivity;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * 
 * @author Doris
 *
 * 2016年6月27日
 */
public class MoveZoomActivity extends BaseActivity {
	
	private ImageView iv_mz_image;
	
	private static final int NONE=0;//无状态
	private static final int MOVE=1;//移动
	private static final int SCALE=2;//缩放
	private static int STATE=NONE;
	
	private PointF pointf=null;
	private Matrix oldmatrix=new Matrix();//矩阵
	private Matrix newmatrix=new Matrix();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_move_zoom);
		
		iv_mz_image = (ImageView) findViewById(R.id.iv_mz_image);
		iv_mz_image.setOnTouchListener(new OnTouchListener() {
			
			private float d1;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action_code=event.getAction()&0xff;//行为
				if(action_code>=5){
					action_code-=5;
				}
				switch (action_code) {
				case MotionEvent.ACTION_DOWN:
					//如果状态为NONE（无状态），则让图片进行移动的操作，并且修改状态为MOVE
					if(STATE==NONE){
						oldmatrix.set(newmatrix);
						//得到手指的坐标
						pointf=new PointF();
						pointf.x=event.getX();
						pointf.y=event.getY();
						STATE=MOVE;
					}else if(STATE==MOVE){
						oldmatrix.set(newmatrix);
						//得到两手指的坐标
						float x1=event.getX(0);
						float y1=event.getY(0);
						float x2=event.getX(1);
						float y2=event.getY(1);
						d1 = distance(x1, y1, x2, y2);
						STATE=SCALE;
					}
					break;

				case MotionEvent.ACTION_MOVE:
					//如果状态为MOVE，则让图片移动
					if(STATE==MOVE){
						//再次获取手指的坐标
						float x=event.getX();
						float y=event.getY();
						float dx=x-pointf.x;
						float dy=y-pointf.y;
						newmatrix.set(oldmatrix);
						newmatrix.postTranslate(dx, dy);
					}else if(STATE==SCALE){
						//进行缩放
						//再次得到两点坐标
						float x1=event.getX(0);
						float y1=event.getY(0);
						float x2=event.getX(1);
						float y2=event.getY(1);
						float d2 = distance(x1, y1, x2, y2);
						//求出缩放比
						float scale=d2/d1;
						newmatrix.set(oldmatrix);
						newmatrix.postScale(scale, scale,(x2+x1)/2,(y2+y1)/2);
					}
					break;
				case MotionEvent.ACTION_UP:
					//修改状态为NONE
					STATE=NONE;
					break;
				}
				iv_mz_image.setImageMatrix(newmatrix);
				return true;
			}
		});
	}
	
	private float distance(float x1,float y1,float x2,float y2){
		return (float) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
	}
}