package com.techbearcave.notetaker;

import com.techbearcave.AYDY.EditTaskActivity;
import com.techbearcave.AYDY.SQLiteHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateNotification extends Activity {

	private CheckBox alertBox;
	private Spinner monthSpinner;
	private Spinner daySpinner;
	private Spinner hourSpinner;
	private Spinner minuteSpinner;
	private Spinner periodSpinner;
	private Button saveButton;
	private Button cancelButton;
	private EditText taskTitleEditText;
	private EditText taskEditText;
	private TextView taskDateText;
	final int NOTIFY_ID = 1234;
	private SQLiteHelper helper;
	
	private String userId;
	private String taskId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		
		alertBox = (CheckBox)findViewById(R.id.alertBox);
		monthSpinner = (Spinner)findViewById(R.id.monthSpinner);
		daySpinner =(Spinner)findViewById(R.id.daySpinner);
		hourSpinner =(Spinner)findViewById(R.id.hourSpinner);
		minuteSpinner =(Spinner)findViewById(R.id.minuteSpinner);
		periodSpinner =(Spinner)findViewById(R.id.periodSpinner);

		saveButton = (Button)findViewById(R.id.taskSaveButton);
		cancelButton = (Button)findViewById(R.id.taskCancelButton);
		taskTitleEditText = (EditText)findViewById(R.id.taskTitleEditText);
		taskEditText = (EditText)findViewById(R.id.taskEditText);
		taskDateText = (TextView)findViewById(R.id.taskDateText);
		
		helper = new SQLiteHelper(this);
		Bundle bundle = this.getIntent().getExtras();
		userId = (String) bundle.getSerializable ("stringToPassOn");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_notification, menu);
		return true;
	}

public void setNotification (Intent intent){
	System.out.println("Starting Notification");
		
		Bundle bundle = this.getIntent().getExtras();
		taskId = (String) bundle.getSerializable("positionTracker");
		Cursor c = helper.getTaskByTaskId(taskId, userId);
		
		System.out.println("Here I am inside createNotification");
		c = helper.getAlertByTaskId(taskId, userId);
		
		c.moveToFirst();
		
		taskTitleEditText.setText(helper.getTaskname(c));
		String Taskname = helper.getTaskname(c);
		taskEditText.setText(helper.getTaskdescription(c));
		String TaskDesc = helper.getTaskdescription(c);
		taskDateText.setText(helper.getTaskCreatedat(c));
		daySpinner.setSelection(Integer.parseInt(helper.getTaskDay(c)) - 1);
		monthSpinner.setSelection(Integer.parseInt(helper.getTaskMonth(c)) - 1);
	
		int hour = Integer.parseInt(helper.getAlerthour(c));
		int minute = Integer.parseInt(helper.getAlertminute(c));
		

		hourSpinner.setSelection(hour-1);
		minuteSpinner.setSelection(minute);
		
		NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		long when = System.currentTimeMillis();
		Notification task = new Notification(R.drawable.ic_launcher, "Task due!", when);
		task.defaults = Notification.DEFAULT_ALL;
		
		Intent intent2 = new Intent(this, EditTaskActivity.class);
		intent2.putExtra(taskId, userId);
		
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent2, 0);
		 
		 task.setLatestEventInfo(this, "You have a new task to attend to!", "Task - "+ Taskname + '\n' + TaskDesc, pIntent);
		 
		 
		 notifyManager.notify(NOTIFY_ID, task);
		
		c.close();
		finish();
		System.out.println("Finishing notification");
		
		/* NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		 Notification task = new Notification(R.drawable.ic_launcher, "New Task!", System.currentTimeMillis());
		 Intent intent = new Intent(this, ListTasksActivity.class);
		 
		 PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		 
		 task.setLatestEventInfo(this, "You have a new task to attend to", "Task - Finish the project", pIntent);
		 task.defaults = Notification.DEFAULT_ALL;
		 
		 notifyManager.notify(NOTIFY_ID, task);
		// notifyManager.cancel(NOTIFY_ID);
		 finish();*/
		
	}
}
