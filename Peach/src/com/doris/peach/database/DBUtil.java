package com.doris.peach.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Doris
 *
 * 2016年4月11日
 */
public class DBUtil {
	
	private static DatabaseHelper helper;
	private static SQLiteDatabase db;
	
	public DBUtil(Context context){
		helper = new DatabaseHelper(context);
	}
	
	/**
	 * 打开数据库
	 */
	private static void openDatabase(){
		db = helper.getWritableDatabase();
	}
	
	/**
	 * 关闭数据库
	 */
	private static void closeDatabase(){
		if(db != null ){
			db.close();
			db = null;
		}
	}
	
	/**
	 * 插入数据
	 * @param table 表名
	 * @param columns 列名
	 * @param values 值
	 */
	public void insertData(String table,String[] columns, String[] values){
		openDatabase();
		
		String sql = "insert into " + table + "(";
		for(int i = 0; i < columns.length; i ++){
			if(i != columns.length - 1){
				sql += columns[i] + ",";
			}else{
				sql += columns[i] + ")";
			}
		}
		sql += "values(";
		for(int i = 0; i < values.length; i ++){
			if(i != values.length - 1){
				sql += "'" + values[i] + "',";
			}else{
				sql += "'" + values[i] + "')";
			}
		}
		db.execSQL(sql);
		
		closeDatabase();
	}
	
	/**
	 * 删除数据
	 * @param table 表名
	 * @param wheres 条件
	 * @param whereArgs 条件值
	 */
	public void delete(String table,String[] wheres, String[] whereArgs){
		openDatabase();
		
		String whereClause = "";
		if(wheres != null){
			for(int i = 0; i < wheres.length; i ++){
				if(i == 0){
					whereClause = wheres[i] + "=?";
				}else{
					whereClause += " and " + wheres[i] + "=?"; 
				}
			}
		}
		db.delete(table, whereClause, whereArgs);
		
		closeDatabase();
	}
	
	/**
	 * 修改数据 
	 * @param table 表名
	 * @param columns 列名
	 * @param values 值
	 * @param wheres 条件
	 * @param whereArgs 条件值
	 */
	public void update(String table, String[] columns, String[] values,
			String[] wheres, String[] whereArgs){
		openDatabase();
		
		ContentValues contentValues = new ContentValues();
		if(columns.length == values.length){
			for(int i = 0; i < columns.length; i ++){
				contentValues.put(columns[i], values[i]);
			}
		}
		String whereClause = "";
		if(wheres != null){
			for(int i = 0; i < wheres.length; i ++){
				if(i == 0){
					whereClause = wheres[i] + "=?";
				}else{
					whereClause += " and " + wheres[i] + "=?"; 
				}
			}
		}else{
			whereClause = null;
		}
		db.update(table, contentValues, whereClause, whereArgs);
		
		closeDatabase();
	}
	
	/**
	 * 查询数据
	 * @param table 表名
	 * @param columns 列名
	 * @param selections 条件
	 * @param selectionArgs 条件值
	 * @return
	 */
	public List<String[]> query(String table, String[] columns,
			String[] selections, String[] selectionArgs) {
		List<String[]> list = new ArrayList<String[]>();
		openDatabase();
		//查询条件
		String selection = "";
		if(selections != null){
			for(int i = 0; i< selections.length; i ++){
				if(i == 0){
					selection = selections[i] + "=?";
				}else{
					selection += " and " + selections[i] + "=?";
				}
			}
		}
		Cursor cursor = db.query(table, columns, selection, 
				selectionArgs, null, null, null);
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				String[] item = new String[cursor.getColumnCount()];
				for(int i = 0; i < cursor.getColumnCount(); i ++){
					item[i] = cursor.getString(i);
				}
				list.add(item);
			}
		}
		
		if(cursor != null){
			cursor.close();
			cursor = null;
		}
		closeDatabase();
		return list;
	}

	/**
	 * 查询数据
	 * @param table 表名
	 * @param columns 列名
	 * @param selections 条件
	 * @param selectionArgs 条件值
	 * @param orderBy 根据哪一列排序
	 * @return
	 */
	public List<String[]> query(String table, String[] columns,
			String[] selections, String[] selectionArgs, String orderBy) {
		List<String[]> list = new ArrayList<String[]>();
		openDatabase();
		//查询条件
		String selection = "";
		if(selections != null){
			for(int i = 0; i< selections.length; i ++){
				if(i == 0){
					selection = selections[i] + "=?";
				}else{
					selection += " and " + selections[i] + "=?";
				}
			}
		}
		Cursor cursor = db.query(table, columns, selection, 
				selectionArgs, null, null, null);
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				String[] item = new String[cursor.getColumnCount()];
				for(int i = 0; i < cursor.getColumnCount(); i ++){
					item[i] = cursor.getString(i);
				}
				list.add(item);
			}
		}
		
		if(cursor != null){
			cursor.close();
			cursor = null;
		}
		closeDatabase();
		return list;
	}
	
	/**
	 * 模糊查询数据
	 * @param table 表名
	 * @param columns 列名
	 * @param selections 条件
	 * @param selectionArgs 条件值
	 * @return
	 */
	public List<String[]> fuzzyQuery(String table, String[] columns,
			String[] selections, String[] selectionArgs) {
		List<String[]> list = new ArrayList<String[]>();
		openDatabase();
		String sql = "select ";
		for (int i = 0; i < columns.length; i ++) {
			if(i == columns.length - 1){
				sql += columns[i] + " ";
			}else{
				sql += columns[i] + ",";
			}
		}
		sql += "from " + table + " where ";
		//查询条件
		for (int i = 0; i < selections.length; i++) {
			if (i == 0) {
				sql += selections[i] + " like " + "'%" 
						+ selectionArgs[i] +"%'";
			} else {
				sql += " and " + selections[i] + " like " + "'%" 
						+ selectionArgs[i] +"%'";
			}
		}
		
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				String[] item = new String[cursor.getColumnCount()];
				for(int i = 0; i < cursor.getColumnCount(); i ++){
					item[i] = cursor.getString(i);
				}
				list.add(item);
			}
		}
		
		if(cursor != null){
			cursor.close();
			cursor = null;
		}
		closeDatabase();
		return list;
	}
	
	/**
	 * 查询指定表中的所有数据
	 * @param tbName 表名
	 * @return
	 */
	public List<String[]> getAllInfo(String tbName){
		List<String[]> list = new ArrayList<String[]>();
		openDatabase();
		Cursor cursor = db.rawQuery("select * from " + tbName, null);
		while(cursor.moveToNext()){
			String[] item = new String[cursor.getColumnCount()];
			for(int i = 0; i < cursor.getColumnCount(); i ++){
				item[i] = cursor.getString(i);
			}
			list.add(item);
		}
		closeDatabase();
		return list;
	}
	
	/**
	 * 查询指定表中的所有数据
	 * @param tbName 表名
	 * @param orderBy 根据哪一列排序
	 * @return
	 */
	public List<String[]> getAllInfo(String tbName, String orderBy){
		List<String[]> list = new ArrayList<String[]>();
		openDatabase();
		Cursor cursor = db.rawQuery("select * from " + tbName + "order by" + orderBy, null);
		while(cursor.moveToNext()){
			String[] item = new String[cursor.getColumnCount()];
			for(int i = 0; i < cursor.getColumnCount(); i ++){
				item[i] = cursor.getString(i);
			}
			list.add(item);
		}
		closeDatabase();
		return list;
	}
	
	/**
	 * 获取列值
	 * @param tbName
	 * @param tbColumn
	 * @return
	 */
	public String getColumn(String tbName, String tbColumn) {
		openDatabase();
		Cursor cursor = db.rawQuery("select max(" + tbColumn + ") " +
				tbColumn +" from " + tbName,
				null);
		if (cursor.moveToNext()) {
			String str = cursor.getString(cursor.getColumnIndex(tbColumn)) 
					== null ? "0"
					: cursor.getString(cursor.getColumnIndex(tbColumn));
			int id = Integer.parseInt(str) + 1;
			return id + "";
		}
		closeDatabase();
		return "1";
	}

}
