package com.doris.peach;

import com.doris.peach.activity.BaseActivity;
import com.doris.peachlibrary.util.Log;
import com.doris.peachlibrary.util.MD5Util;
import com.doris.peachlibrary.util.ThreeDESUtil;
import com.doris.peachlibrary.view.ToastView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 3DES加密
 * @author Doris
 *
 * 2016年4月19日
 */
public class ThreeDESActivity extends BaseActivity {

	private EditText et_threedes_key;
	private EditText et_threedes_encrypt_text;
	private TextView tv_threedes_result_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_threedes);
		
		setTitle();
		et_threedes_key = (EditText) findViewById(R.id.et_threedes_key);
		et_threedes_encrypt_text = (EditText) findViewById(R.id.et_threedes_encrypt_text);
		tv_threedes_result_text = (TextView) findViewById(R.id.tv_threedes_result_text);
	}
	
	public void encryptString(View view){
		String key = et_threedes_key.getText().toString();
		String encryptText = et_threedes_encrypt_text.getText().toString();
		if(key != null && key.length() > 0){
			if(encryptText != null && encryptText.length() > 0){
				try {
					ThreeDESUtil.PASSWORD_CRYPT_KEY = MD5Util.getMD5String(key);
					byte[] bytes = ThreeDESUtil.encryptMode(encryptText.getBytes());
					tv_threedes_result_text.setText("加密后" + new String(bytes) );
				} catch (Exception e) {
					ToastView.showWhiteContentToast(this,
							R.drawable.ic_toast_flag_error, 
							"加密出错");
					Log.getInstance().writeLog("ThreeDES加密出错：", e);
				}
			}else{
				ToastView.showWhiteContentToast(this,
						R.drawable.ic_toast_flag_verbose, 
						"加密字符串不能为空");
			}
		}else{
			ToastView.showWhiteContentToast(this, 
					R.drawable.ic_toast_flag_verbose, 
					"密钥不能为空");
		}
	}
}
