package com.doris.peach.activity.lock;

import com.doris.peach.R;
import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.dialog.Interface.DialogYesClickListener;
import com.doris.peachlibrary.dialog.Interface.OnClickDialogListViewItemListener;
import com.doris.peachlibrary.util.DialogUtil;
import com.doris.peachlibrary.view.ColorPickerView.OnColorChangedListener;
import com.doris.peachlibrary.view.ToastView;
import com.doris.peachlibrary.view.lock.BlurLockView;
import com.doris.peachlibrary.view.lock.BlurLockView.OnLeftButtonClickListener;
import com.doris.peachlibrary.view.lock.BlurLockView.OnPasswordInputListener;
import com.doris.peachlibrary.view.lock.util.EaseType;
import com.doris.peachlibrary.view.lock.util.HideType;
import com.doris.peachlibrary.view.lock.util.Password;
import com.doris.peachlibrary.view.lock.util.ShowType;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Doris
 *
 * 2016年8月6日
 */
public class NumberLockActivity extends BaseActivity {
	
	private BlurLockView blurLockView;
	private ImageView imageView1;
	
	private String password = "1234";
	private String title = "请输入密码";
	private String leftButton = "操作";
	private String rightButton = "删除";
	private int showDuration = 1000;
	private int showDirection = 0;
	private int showEaseType = 30;
	private int hideDuration = 1000;
	private int hideDirection = 0;
	private int hideEaseType = 30;
	private int radius = 5;
	private int downsamepleFactor = 5;
	private int overlayColor = Color.WHITE;
	
	private DialogUtil dialogUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_lock_number);
		
		dialogUtil = new DialogUtil(this);
		
		imageView1 = (ImageView) findViewById(R.id.image_1);
		blurLockView = (BlurLockView) findViewById(R.id.blurlockview);
		
		blurLockView.setBlurredView(imageView1);
		// 设置密码
		blurLockView.setCorrectPassword(password);
		// 设置标题
		blurLockView.setTitle(title);
		// 设置左边按钮文字
		blurLockView.setLeftButton(leftButton);
		// 设置右边按钮文字
		blurLockView.setRightButton(rightButton);
		// 设置字体
		blurLockView.setTypeface(Typeface.DEFAULT);
		// 设置密码类型
		blurLockView.setType(Password.NUMBER, false);
		// 设置数字密码没有字母
		blurLockView.setSubTextVisibility(View.GONE);
		// 设置左边按钮点击效果
		blurLockView.setOnLeftButtonClickListener(new OnLeftButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				dialogUtil.listViewDialog(getResources().getStringArray(R.array.operations), leftButton,
						new OnClickDialogListViewItemListener() {
							
							@Override
							public void onClickClickDialogListViewItem(int position) {
								// TODO Auto-generated method stub
								switch (position) {
								case 0:
									blurLockView.show(showDuration, getShowType(showDirection),
											getEaseType(showEaseType));
									break;
								case 1:
									blurLockView.hide(hideDuration, getHideType(hideDirection),
											getEaseType(hideEaseType));
									break;
								case 2:
									setBlurRadius();
									break;
								case 3:
									setDownsamepleFactor();
									break;
								case 4:
									setOverlayColor();
									break;
								case 5:
									if (Password.NUMBER.equals(blurLockView.getType())) {
										blurLockView.setType(Password.TEXT, true);
									} else if (Password.TEXT.equals(blurLockView.getType())) {
										blurLockView.setType(Password.NUMBER, true);
									}
									break;
								case 6:
									setPassword();
									break;
								case 7:
									setDuration(true);
									break;
								case 8:
									setDuration(false);
									break;
								case 9:
									setDirection(true);
									break;
								case 10:
									setDirection(false);
									break;
								case 11:
									setEaseType(true);
									break;
								case 12:
									setEaseType(false);
									break;
								case 13:
									setTextType();
									break;
								}
							}
						}, true);
			}
		});
		// 密码输入事件
		blurLockView.setOnPasswordInputListener(new OnPasswordInputListener() {
			
			@Override
			public void input(String inputPassword) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void incorrect(String inputPassword) {
				// TODO Auto-generated method stub
				// 密码错误
				ToastView.showWhiteContentToast(NumberLockActivity.this,
						R.drawable.ic_toast_flag_error,
						"密码错误：" + inputPassword);
			}
			
			@Override
			public void correct(String inputPassword) {
				// TODO Auto-generated method stub
				// 密码正确
				ToastView.showWhiteContentToast(NumberLockActivity.this,
						R.drawable.ic_toast_flag_ok,
						"密码正确：" + inputPassword);
				blurLockView.hide(hideDuration, getHideType(hideDirection), getEaseType(hideEaseType));
			}
		});
		// 图片点击事件
		imageView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 密码锁出现动画
				blurLockView.show(showDuration, getShowType(showDirection), getEaseType(showEaseType));
			}
		});
	}
	
	private void setTextType(){
		final String[] list = {"默认", "fangzhenglantingxianhe_GBK.ttf",
				"San Francisco Regular.ttf", "Satisfy-Regular.ttf", "RobotoSlab-Regular.ttf"};
		dialogUtil.listViewDialogFonts(list,
				getString(R.string.setTextType),
				new OnClickDialogListViewItemListener() {
					
					@Override
					public void onClickClickDialogListViewItem(int position) {
						// TODO Auto-generated method stub
						if(position == 0){
							blurLockView.setTypeface(Typeface.DEFAULT);
						}else{
							blurLockView.setTypeface(Typeface.createFromAsset(getAssets(), 
									"fonts/" + list[position]));
						}
					}
				});
	}
	
	private void setEaseType(final boolean isShow){
		String title = getString(isShow ? R.string.setShowEaseType : R.string.setHideEaseType);
		dialogUtil.listViewDialog(getResources().getStringArray(R.array.ease_type), title, 
				new OnClickDialogListViewItemListener() {
					
					@Override
					public void onClickClickDialogListViewItem(int position) {
						// TODO Auto-generated method stub
						if(isShow){
							showEaseType = position;
						}else{
							hideEaseType = position;
						}
					}
				}, true);
	}
	
	private void setDirection(final boolean isShow){
		String title = getString(isShow ? R.string.setShowDirection : R.string.setHideDirection);
		dialogUtil.listViewDialog(getResources().getStringArray(R.array.numberlock_direction), title,
				new OnClickDialogListViewItemListener() {
					
					@Override
					public void onClickClickDialogListViewItem(int position) {
						// TODO Auto-generated method stub
						if(isShow){
							showDirection = position;
						}else{
							hideDirection = position;
						}
					}
				}, true);
	}
	
	private void setDuration(final boolean isShow){
		String title = getString(isShow ? R.string.setShowDuration : R.string.setHideDuration);
		int progress = isShow ? showDuration : hideDuration;
		dialogUtil.showSeekBar(title, progress/500, 10, progress + "ms", 
				new DialogYesClickListener() {
			
			@Override
			public void onClickYes(Object... objects) {
				// TODO Auto-generated method stub
				int seekbarProgress = Integer.parseInt(objects[0].toString());
				if(isShow){
					showDuration = seekbarProgress * 500;
				}else{
					hideDuration = seekbarProgress * 500;
				}
			}
		}, new DialogYesClickListener() {
			
			@Override
			public void onClickYes(Object... objects) {
				// TODO Auto-generated method stub
				int seekbarProgress = Integer.parseInt(objects[0].toString());
				TextView text = (TextView) objects[1];
				text.setText(seekbarProgress * 500 + "ms");
			}
		});
	}
	
	private void setPassword(){
		dialogUtil.showEdit(getString(R.string.setPassword),
				getString(R.string.password), new DialogYesClickListener() {
					
					@Override
					public void onClickYes(Object... objects) {
						// TODO Auto-generated method stub
						password = objects[0].toString().toUpperCase();
						blurLockView.setCorrectPassword(password);
					}
				}, InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}
	
	private void setBlurRadius() {
		dialogUtil.showEdit(getString(R.string.setBlurRadius),
				"1-25", new DialogYesClickListener() {
			
			@Override
			public void onClickYes(Object... objects) {
				// TODO Auto-generated method stub
				String input = objects[0].toString();  
			    if (!TextUtils.isEmpty(input)) {  
			    	try {
						radius = Integer.parseInt(String.valueOf(input));
					} catch (NumberFormatException n) {
						radius = 5;
					}
			    	if (1 <= radius && radius <= 25){
			    		blurLockView.setBlurRadius(radius);
			    	}
			    } 
			}
		}, InputType.TYPE_CLASS_NUMBER);
	}
	
	private void setDownsamepleFactor(){
		dialogUtil.showEdit(getString(R.string.setDownsampleFactor),
				"1-25", new DialogYesClickListener() {
					
					@Override
					public void onClickYes(Object... objects) {
						// TODO Auto-generated method stub
						String input = objects[0].toString();  
					    if (!TextUtils.isEmpty(input)) {  
					    	try {
					    		downsamepleFactor = Integer.parseInt(String.valueOf(input));
							} catch (NumberFormatException n) {
								downsamepleFactor = 5;
							}
					    	if (1 <= downsamepleFactor && downsamepleFactor <= 25){
					    		blurLockView.setDownsampleFactor(downsamepleFactor);
					    	}
					    } 
					}
				}, InputType.TYPE_CLASS_NUMBER);
	}
	
	private void setOverlayColor(){
		dialogUtil.showColorPicker(overlayColor, new OnColorChangedListener() {
			
			@Override
			public void colorChanged(int color) {
				// TODO Auto-generated method stub
				overlayColor = color;
				blurLockView.setOverlayColor(overlayColor);
			}
		});
	}
	
	private HideType getHideType(int p) {
		HideType hideType = HideType.FROM_TOP_TO_BOTTOM;
		switch (p) {
		case 0:
			hideType = HideType.FROM_TOP_TO_BOTTOM;
			break;
		case 1:
			hideType = HideType.FROM_RIGHT_TO_LEFT;
			break;
		case 2:
			hideType = HideType.FROM_BOTTOM_TO_TOP;
			break;
		case 3:
			hideType = HideType.FROM_LEFT_TO_RIGHT;
			break;
		case 4:
			hideType = HideType.FADE_OUT;
			break;
		}
		return hideType;
	}
	
	private ShowType getShowType(int p) {
		ShowType showType = ShowType.FROM_TOP_TO_BOTTOM;
		switch (p) {
		case 0:
			showType = ShowType.FROM_TOP_TO_BOTTOM;
			break;
		case 1:
			showType = ShowType.FROM_RIGHT_TO_LEFT;
			break;
		case 2:
			showType = ShowType.FROM_BOTTOM_TO_TOP;
			break;
		case 3:
			showType = ShowType.FROM_LEFT_TO_RIGHT;
			break;
		case 4:
			showType = ShowType.FADE_IN;
			break;
		}
		return showType;
	}
	
	private EaseType getEaseType(int p) {
		EaseType easeType = EaseType.Linear;
		switch (p) {
		case 0:
			easeType = EaseType.EaseInSine;
			break;
		case 1:
			easeType = EaseType.EaseOutSine;
			break;
		case 2:
			easeType = EaseType.EaseInOutSine;
			break;
		case 3:
			easeType = EaseType.EaseInQuad;
			break;
		case 4:
			easeType = EaseType.EaseOutQuad;
			break;
		case 5:
			easeType = EaseType.EaseInOutQuad;
			break;
		case 6:
			easeType = EaseType.EaseInCubic;
			break;
		case 7:
			easeType = EaseType.EaseOutCubic;
			break;
		case 8:
			easeType = EaseType.EaseInOutCubic;
			break;
		case 9:
			easeType = EaseType.EaseInQuart;
			break;
		case 10:
			easeType = EaseType.EaseOutQuart;
			break;
		case 11:
			easeType = EaseType.EaseInOutQuart;
			break;
		case 12:
			easeType = EaseType.EaseInQuint;
			break;
		case 13:
			easeType = EaseType.EaseOutQuint;
			break;
		case 14:
			easeType = EaseType.EaseInOutQuint;
			break;
		case 15:
			easeType = EaseType.EaseInExpo;
			break;
		case 16:
			easeType = EaseType.EaseOutExpo;
			break;
		case 17:
			easeType = EaseType.EaseInOutExpo;
			break;
		case 18:
			easeType = EaseType.EaseInCirc;
			break;
		case 19:
			easeType = EaseType.EaseOutCirc;
			break;
		case 20:
			easeType = EaseType.EaseInOutCirc;
			break;
		case 21:
			easeType = EaseType.EaseInBack;
			break;
		case 22:
			easeType = EaseType.EaseOutBack;
			break;
		case 23:
			easeType = EaseType.EaseInOutBack;
			break;
		case 24:
			easeType = EaseType.EaseInElastic;
			break;
		case 25:
			easeType = EaseType.EaseOutElastic;
			break;
		case 26:
			easeType = EaseType.EaseInOutElastic;
			break;
		case 27:
			easeType = EaseType.EaseInBounce;
			break;
		case 28:
			easeType = EaseType.EaseOutBounce;
			break;
		case 29:
			easeType = EaseType.EaseInOutBounce;
			break;
		case 30:
			easeType = EaseType.Linear;
			break;
		}
		return easeType;
	}
	
}
