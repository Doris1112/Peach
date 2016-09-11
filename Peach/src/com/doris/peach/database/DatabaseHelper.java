package com.doris.peach.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Doris
 *
 *         2016年4月9日
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "peach.db";

	/**
	 * @param context
	 *            上下文
	 * @param name
	 *            数据库的名字（文件名）
	 * @param factory
	 *            数据库的工厂 (NULL)
	 * @param version
	 *            数据库的版本
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}