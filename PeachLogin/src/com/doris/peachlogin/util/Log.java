package com.doris.peachlogin.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Doris
 *
 * 2016年4月19日
 */
public class Log {

	private static Log _lg;
	//保存路径
	private static final String LOG_SAVE_PATH = "/sdcard/peach/log/";
	
	public static Log getInstance(){
		if(_lg == null){
			_lg = new Log();
		}
		return _lg;
	}
	
	/**
	 * 插入日志
	 * @param msg
	 */
	public void writeLog(String msg){
		if(msg == null){
			return;
		}
		
		File file = checkLogFileIsExist();
		if(file == null){
			return;
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file, true);
			fos.write((new Date().toLocaleString() + "：" + msg).getBytes("gbk"));
			fos.write("\r\n".getBytes("gbk"));
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				if(fos != null){
					fos.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			fos = null;
			file = null;
		}
	}
	
	/**
	 * 检测日志文件是否存在
	 * @return
	 */
	private File checkLogFileIsExist(){
		File file = new File(LOG_SAVE_PATH);
		if(!file.exists()){
			file.mkdirs();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(new Date());
		file = new File(LOG_SAVE_PATH + dateStr + ".txt");
		if(!isLogExist(file)){
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sdf = null;
		return file;
	}
	
	/**
	 * 检测当天日志是否存在
	 * @param file
	 * @return
	 */
	private boolean isLogExist(File file){
		boolean result = false;
		try {
			File tempFile = new File(LOG_SAVE_PATH);
			File[] files = tempFile.listFiles();
			if(files == null){
				return result;
			}
			for (int i = 0; i < files.length; i++) {
				String name = files[i].getName().trim();
				if(name != null && name.equalsIgnoreCase(file.getName())){
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
}
