package com.techbearcave.AYDY;

import android.content.ContentValues;
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
    private static final String KEY_CREATED_AT = "Created_at";
    
    // Column names
    private static final String USER_ID = "UserId";
    private static final String TASK_ID = "TaskId";
    private static final String NOTE_ID = "NoteId";
    private static final String FOREIGN_KEY = "Userfk";
    private static final String TASK = "Task";
    private static final String DESCRIPTION = "Description";
    
    // Note table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
    		+ "Fname TEXT, Lname TEXT, Password TEXT, Email TEXT" + ");";
 
    // Tag table create statement
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASK
            + "(" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
    		+ TASK + " TEXT, " 
    		+ KEY_CREATED_AT + " DATETIME," 
            + FOREIGN_KEY + " integer, FOREIGN KEY (" + FOREIGN_KEY + ") REFERENCES "
            + TABLE_USER + "(" + USER_ID + "));";
 
    // todo_tag table create statement
    private static final String CREATE_TABLE_NOTE = "CREATE TABLE " + TABLE_NOTE 
    		+ "(" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
    		+ DESCRIPTION + " TEXT, "
            + KEY_CREATED_AT + " DATETIME," 
    		+ FOREIGN_KEY + " integer, FOREIGN KEY (" + FOREIGN_KEY + ") REFERENCES "
    		+ TABLE_USER + "(" + USER_ID + "));";
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_TASKS);
		db.execSQL(CREATE_TABLE_NOTE);

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
	
	public void insertUser(String firstName, String lastName, String password, String email) {
		ContentValues cv = new ContentValues();
		
		cv.put("Fname", firstName);
		cv.put("Lname", lastName);
		cv.put("Password", password);
		cv.put("Email", email);			

		getWritableDatabase().insert("users", "Fname", cv);
	}
	
	public void insertTask(String task, String date, String userId) {
		ContentValues cv = new ContentValues();
		
		cv.put("Task", task);
		cv.put("Created_at", date);
		cv.put("Userfk", userId);

		getWritableDatabase().insert("tasks", "Task", cv);
	}
	
	public void insertNote(String description, String date, String userId) {
		ContentValues cv = new ContentValues();
		
		cv.put("Description", description);
		cv.put("Created_at", date);
		cv.put("Userfk", userId);

		getWritableDatabase().insert("notes", "note", cv);
	}
}
