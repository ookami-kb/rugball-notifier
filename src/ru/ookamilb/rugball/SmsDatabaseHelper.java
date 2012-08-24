package ru.ookamilb.rugball;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmsDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "smstable.db";
	private static final int DATABASE_VERSION = 1;
	
	public SmsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		SmsTable.onCreate(database);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		SmsTable.onUpgrade(database, oldVersion, newVersion);
	}
}
