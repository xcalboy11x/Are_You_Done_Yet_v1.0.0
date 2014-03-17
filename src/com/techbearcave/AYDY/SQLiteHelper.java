package com.techbearcave.AYDY;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "areyoudoneyet.db";
	private static final int SCHEMA_VERSION = 1;
	
	// Table Names
    private static final String TABLE_USER = "users";
    private static final String TABLE_TASK = "tasks";
    private static final String TABLE_NOTE = "notes";
    
    // Common column names
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_ID = "id";
    
    // Note table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
            		"Fname TEXT, Lname TEXT, Password TEXT, Email TEXT" + ")";
 
    // Tag table create statement
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASK
            + "(" + KEY_ID + " INTEGER PRIMARY KEY, Task TEXT, " 
    		+ KEY_CREATED_AT + " DATETIME, FOREIGN KEY (" + TABLE_USER + ") REFERENCES "
            + TABLE_USER + "(" + KEY_ID + "))";
 
    // todo_tag table create statement
    private static final String CREATE_TABLE_NOTE = "CREATE TABLE "
            + TABLE_NOTE + "(" + KEY_ID + " INTEGER PRIMARY KEY, Description TEXT, "
            + KEY_CREATED_AT + " DATETIME, FOREIGN KEY (" + TABLE_USER + ") REFERENCES "
            + TABLE_USER + "(" + KEY_ID + "))";
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE restaurants (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, type TEXT, notes TEXT);");
	}
	
	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
 
        // create new tables
        onCreate(db);
    }
}
