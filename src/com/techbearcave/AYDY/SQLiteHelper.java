package com.techbearcave.AYDY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SQLiteHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "areyoudoneyet.db";
	private static final int SCHEMA_VERSION = 1;
	
	
	// Table Names
    private static final String TABLE_USER = "users";
    private static final String TABLE_TASK = "tasks";
    private static final String TABLE_NOTE = "notes";
    private static final String TABLE_ALERT = "alerts";
    // Common column names
    private static final String KEY_CREATED_AT = "Created_at";
    
    // Column names
    private static final String USER_ID = "_id";
    private static final String TASK_ID = "_id";
    private static final String NOTE_ID = "_id";
    private static final String ALERT_ID = "_id";
    private static final String FOREIGN_KEY = "Userfk";
    private static final String TASK_KEY = "Taskfk";
    private static final String TASK = "Task";
    private static final String TASK_NAME = "Taskname";
    private static final String NOTE_NAME = "Notename";
    private static final String NOTE_DESCRIPTION = "Notedescription";
    private static final String ALERT_DATE = "Alertdate";
    private static final String ALERT_TASK = "Alerttask";
  
    
    // Users table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
    		+ "Username TEXT NOT NULL UNIQUE, Fname TEXT, Lname TEXT, Password TEXT, Email TEXT" + ");";
 
    // Tasks table create statement
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASK
            + "(" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
            + TASK_NAME + " TEXT, "
    		+ TASK + " TEXT, " 
    		+ KEY_CREATED_AT + " DATETIME, " 
    		+ ALERT_TASK + " TEXT, "
            + FOREIGN_KEY + " INTEGER, FOREIGN KEY (" + FOREIGN_KEY + ") REFERENCES "
            + TABLE_USER + "(" + USER_ID + "));";
 
    // Notes table create statement
    private static final String CREATE_TABLE_NOTE = "CREATE TABLE " + TABLE_NOTE 
    		+ "(" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
    		+ NOTE_NAME + " TEXT, "
    		+ NOTE_DESCRIPTION + " TEXT, "
            + KEY_CREATED_AT + " DATETIME," 
    		+ FOREIGN_KEY + " INTEGER, FOREIGN KEY (" + FOREIGN_KEY + ") REFERENCES "
    		+ TABLE_USER + "(" + USER_ID + "));";
    
 // Alerts table create statement
    private static final String CREATE_TABLE_ALERT = "CREATE TABLE " + TABLE_ALERT 
    		+ "(" + ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
    		+ ALERT_DATE + " TEXT, "
            + KEY_CREATED_AT + " DATETIME," 
    		+ FOREIGN_KEY + " INTEGER, FOREIGN KEY (" + FOREIGN_KEY + ") REFERENCES "
    		+ TABLE_USER + "(" + USER_ID + "));"
    		+ TASK_KEY + " INTEGER, FOREIGN KEY (" + TASK_KEY + ") REFERENCES "
    		+ TABLE_TASK + "(" + TASK_ID + "));";
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_TASKS);
		db.execSQL(CREATE_TABLE_NOTE);
		db.execSQL(CREATE_TABLE_ALERT);
	}
	
	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALERT);
 
        // create new tables
        onCreate(db);
    }
	
	/* =============================================== 
	 * USER METHODS
	 * ===============================================
	*/
	
	public boolean insertUser(String userName, String firstName, String lastName, String password, String email) {
		ContentValues cv = new ContentValues();
		
		cv.put("Username", userName);
		cv.put("Fname", firstName);
		cv.put("Lname", lastName);
		cv.put("Password", password);
		cv.put("Email", email);			
		try{
			getWritableDatabase().insertOrThrow("users", "Fname", cv);
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public String getUserId (String email) {
		return (getReadableDatabase().rawQuery("SELECT _id FROM users WHERE email ='"+email+"'", null).toString()); 
	}
	

	public Cursor getUsername (String username){
		return (getReadableDatabase().rawQuery("SELECT Username, Password, _id FROM users WHERE Username='"+username+"'", null));
	}
	
	public String getPassword (String password){
		return (getReadableDatabase().rawQuery("SELECT Password FROM users WHERE Password ='"+password+"'", null).toString());
	}
	
	
	
	/* =============================================== 
	 * TASK METHODS
	 * ===============================================
	*/
	
	public boolean insertTask(String taskName, String task, String date, String alertTask, String userId) {
		ContentValues cv = new ContentValues();
		
		cv.put("Taskname", taskName);
		cv.put("Task", task);
		cv.put("Created_at", date);
		cv.put("Alerttask", alertTask);
		cv.put("Userfk", userId);

		try{
			getWritableDatabase().insertOrThrow("tasks", "Task", cv);
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public Cursor getTasks () {
		return (getReadableDatabase().rawQuery("SELECT _id, Taskname, Task, Created_at, Userfk FROM tasks ORDER BY name", null));
	}
	
	public void updateTask(String taskName, String taskDescription, int id){
		ContentValues cv = new ContentValues();
		
		cv.put("Taskname", taskName);
		cv.put("Task", taskDescription);
		getWritableDatabase().update("notes", cv, "_id ='" + id + "'", null);
	}
	
	public Cursor getTaskById (String id) {
		return (getReadableDatabase().rawQuery("SELECT _id, Taskname, Task, Created_at, Alertdate, Userfk FROM tasks WHERE _id="+ id, null));
	}
	
	public Cursor getTaskID (String task_id){
		return (getReadableDatabase().rawQuery("SELECT _id FROM tasks WHERE _id ='"+task_id+"'", null));
	}
	
	public Cursor getTasksById (String id) {
		return (getReadableDatabase().rawQuery("SELECT _id, Taskname, Task, Created_at, Userfk FROM tasks " +
				"WHERE Userfk ='"+ id +"'", null));
	}
	
	public Cursor getTaskByTaskId (String taskId, String id) {
		return (getReadableDatabase().rawQuery("SELECT _id, Taskname, Taskdescription, Created_at, Alertdate, Userfk FROM tasks " +
				"WHERE _id ='"+ taskId + "' AND Userfk = '" + id + "'", null));
	}
	
	public void deleteTask (String taskId){
		Log.d("Deleting Task Id: ", taskId + "");
		getWritableDatabase().delete(TABLE_TASK, "_id" + "='" + taskId+ "'", null);
	}
	
	public String getTaskId (Cursor c) {
		return (c.getString(0));
	}
	
	public String getTaskname (Cursor c) {
		return (c.getString(1));
	}
	
	public String getTaskdescription (Cursor c) {
		return (c.getString(2));
	}
	
	
	/* =============================================== 
	 * NOTE METHODS
	 * ===============================================
	*/
	
	public boolean insertNote(String noteName, String noteDescription, String date, int userId) {
		ContentValues cv = new ContentValues();
		
		cv.put("Notename", noteName);
		cv.put("Notedescription", noteDescription);
		cv.put("Created_at", date);
		cv.put("Userfk", userId);

		try{
			getWritableDatabase().insertOrThrow("notes", "Notename", cv);
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void updateNote(String noteName, String noteDescription, int id){
		ContentValues cv = new ContentValues();
		
		cv.put("Notename", noteName);
		cv.put("Notedescription", noteDescription);
		getWritableDatabase().update("notes", cv, "_id ='" + id + "'", null);
	}
	public Cursor getNotesById (String id) {
		return (getReadableDatabase().rawQuery("SELECT _id, Notename, Notedescription, Created_at, Userfk FROM notes " +
				"WHERE Userfk ='"+ id +"'", null));
	}
	
	public Cursor getNoteByNoteId (String noteId, String id) {
		return (getReadableDatabase().rawQuery("SELECT _id, Notename, Notedescription, Created_at, Userfk FROM notes " +
				"WHERE _id ='"+ noteId + "' AND Userfk = '" + id + "'", null));
	}
	
	public Cursor getNoteID (String note_id){
		return (getReadableDatabase().rawQuery("SELECT _id FROM notes WHERE _id ='"+note_id+"'", null));
	}
	
	public void deleteNote (String noteId){
		Log.d("Deleting Note Id: ", noteId + "");
		getWritableDatabase().delete(TABLE_NOTE, "_id" + "='" + noteId+ "'", null);
	}
	
	public String getNoteId (Cursor c) {
		return (c.getString(0));
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

	/* =============================================== 
	 * ALERT METHODS
	 * ===============================================
	*/
	
	public boolean insertAlert(String alertDate,int userId, int taskId) {
		ContentValues cv = new ContentValues();
		
		cv.put("Alertdate", alertDate);
		cv.put("Userfk", userId);
		cv.put("Taskfk", taskId);

		try{
			getWritableDatabase().insertOrThrow("notes", "Notename", cv);
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
}
