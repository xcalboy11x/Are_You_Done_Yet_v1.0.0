package com.techbearcave.AYDY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final String USER_ID = "_id";
    private static final String TASK_ID = "_id";
    private static final String NOTE_ID = "_id";
    private static final String FOREIGN_KEY = "Userfk";
    private static final String TASK = "Task";
    private static final String TASK_NAME = "Taskname";
    private static final String NOTE_NAME = "Notename";
    private static final String NOTE_DESCRIPTION = "Notedescription";
  
    
    // Note table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
    		+ "Username TEXT not null unique, Fname TEXT, Lname TEXT, Password TEXT, Email TEXT" + ");";
 
    // Tag table create statement
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASK
            + "(" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
            + TASK_NAME + " TEXT, "
    		+ TASK + " TEXT, " 
    		+ KEY_CREATED_AT + " DATETIME," 
            + FOREIGN_KEY + " integer, FOREIGN KEY (" + FOREIGN_KEY + ") REFERENCES "
            + TABLE_USER + "(" + USER_ID + "));";
 
    // todo_tag table create statement
    private static final String CREATE_TABLE_NOTE = "CREATE TABLE " + TABLE_NOTE 
    		+ "(" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
    		+ NOTE_NAME + " TEXT, "
    		+ NOTE_DESCRIPTION + " TEXT, "
            + KEY_CREATED_AT + " DATETIME," 
    	//	+ FOREIGN_KEY + " integer, FOREIGN KEY (" + FOREIGN_KEY + ") REFERENCES "
    	//	+ TABLE_USER + "(" + USER_ID + "));";
            + "Userfk INTEGER);";
	
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
	
	public void insertUser(String userName, String firstName, String lastName, String password, String email) {
		ContentValues cv = new ContentValues();
		
		cv.put("Username", userName);
		cv.put("Fname", firstName);
		cv.put("Lname", lastName);
		cv.put("Password", password);
		cv.put("Email", email);			

		getWritableDatabase().insert("users", "Fname", cv);
	}
	
	
	
	
	public void insertTask(String taskName, String task, String date, String userId) {
		ContentValues cv = new ContentValues();
		
		cv.put("Taskname", taskName);
		cv.put("Task", task);
		cv.put("Created_at", date);
		cv.put("Userfk", userId);

		getWritableDatabase().insert("tasks", "Task", cv);
	}
	
	public Cursor getTasks () {
		return (getReadableDatabase().rawQuery("SELECT _id, Taskname, Task, Created_at, Userfk FROM notes ORDER BY name", null));
	}
	
	public Cursor getTaskById (String id) {
		return (getReadableDatabase().rawQuery("SELECT _id, Taskname, Task, Created_at, Userfk FROM notes WHERE _id="+ id, null));
	}
	
	
	
	public void insertNote(String noteName, String noteDescription, String date, String userId) {
		ContentValues cv = new ContentValues();
		
		cv.put("Notename", noteName);
		cv.put("Notedescription", noteDescription);
		cv.put("Created_at", date);
		cv.put("Userfk", userId);

		getWritableDatabase().insert("notes", "note", cv);
	}
	
	public Cursor getNotesById (String id) {
		return (getReadableDatabase().rawQuery("SELECT _id, Notename, Notedescription, Created_at, Userfk FROM notes ORDER BY Notename " +
				"WHERE _id ="+ id, null));
	}
	
	public Cursor getNoteById (String id) {
		return (getReadableDatabase().rawQuery("SELECT _id, Notename, Notedescription, Created_at, Userfk FROM notes WHERE _id ="+ id, null));
	}
	
	public String getNotename (Cursor c) {
		return (c.getString(1));
	}
	
	public String getNotedescription (Cursor c) {
		return (c.getString(2));
	}
	
	public String getCreatedat (Cursor c) {
		return (c.getString(3));
	}
	
	public String getUserId (String email) {
		return (getReadableDatabase().rawQuery("SELECT _id FROM users WHERE email ="+ email, null).toString()); 
	}
	
	public String getUsername (String username){
		return (getReadableDatabase().rawQuery("SELECT Username FROM users WHERE Username =" + username, null).toString());
	}
	
	public String getPassword (String password){
		return (getReadableDatabase().rawQuery("SELECT Password FROM users WHERE Password =" + password, null).toString());
	}
}
