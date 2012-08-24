package ru.ookamilb.rugball;

import android.database.sqlite.SQLiteDatabase;

public class SmsTable {
	public static final String TABLE_SMS = "sms";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_TEXT = "text";
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_SMS
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_DATE + " integer not null, "
			+ COLUMN_TEXT + " text not null"
			+ ");";
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
		onCreate(database);
	}
}
