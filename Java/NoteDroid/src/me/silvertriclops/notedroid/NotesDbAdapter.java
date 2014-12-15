package me.silvertriclops.notedroid;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDbAdapter {
	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_CREATION_DATE = "creation_date";
	public static final String KEY_MODIFICATION_DATE = "modification_date";
	
	private static final String TAG = "NotesDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_NAME = "NOTEDROID";
	private static final String DATABASE_TABLE = "NOTES";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CREATION_DATE + " TEXT, " + KEY_MODIFICATION_DATE + " TEXT, " + KEY_TITLE + " TEXT NOT NULL, " + KEY_BODY + " TEXT);";
	
	private final Context mCtx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	
	public NotesDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}
	
	public NotesDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		mDbHelper.close();
	}
	
	public long createNote(String title, String body) {
		ContentValues initialValues = new ContentValues();
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSS");
		String current_time = sdf.format(c.getTime());
		
		initialValues.put(KEY_CREATION_DATE, current_time);
		initialValues.put(KEY_MODIFICATION_DATE, current_time);
		initialValues.put(KEY_TITLE,  title);
		initialValues.put(KEY_BODY, body);
		
		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}
}
