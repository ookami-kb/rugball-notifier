package ru.ookamilb.rugball;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class SmsContentProvider extends ContentProvider {
	private SmsDatabaseHelper database;
	
	private static final int MESSAGES = 10;
	private static final int MESSAGE_ID = 20;
	
	private static final String AUTHORITY = "ru.ookamilb.rugball";
	
	private static final String BASE_PATH = "sms";
	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/messages";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/message";
	
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, MESSAGES);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", MESSAGE_ID);
	}
	
	@Override
	public boolean onCreate() {
		database = new SmsDatabaseHelper(getContext());
		return false;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		checkColumns(projection);
		
		queryBuilder.setTables(SmsTable.TABLE_SMS);
		
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case MESSAGES:
			break;
		case MESSAGE_ID:
			queryBuilder.appendWhere(SmsTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}
	
	@Override
	public String getType(Uri uri) {
		return null;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		switch (uriType) {
		case MESSAGES:
			id = sqlDB.insert(SmsTable.TABLE_SMS, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// в рамках данной статьи этот метод не пригодится...
		return 0;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// ...и этот тоже
		return 0;
	}
	
	private void checkColumns(String[] projection) {
		String[] available = {
				SmsTable.COLUMN_DATE,
				SmsTable.COLUMN_TEXT,
				SmsTable.COLUMN_ID
		};
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}
}
