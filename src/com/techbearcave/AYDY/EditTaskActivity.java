package com.techbearcave.AYDY;



import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.techbearcave.notetaker.R;


public class EditTaskActivity extends Activity implements OnItemSelectedListener {
	
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
	
	private SQLiteHelper helper;
	
	private String userId;
	private String taskId;
	private boolean isInEditMode = true ; 
	ArrayAdapter monthAdapter;
	ArrayAdapter dayAdapter;
	ArrayAdapter hourAdapter;
	ArrayAdapter minuteAdapter;
	ArrayAdapter periodAdapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);

		// set ID's for spinner & checkbox
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
		isInEditMode = getIntent().getBooleanExtra(ListTasksActivity.isInEditMode, true);
		

		//set the display adapters for spinners & button
		monthAdapter = ArrayAdapter.createFromResource(this, R.array.month_array,android.R.layout.simple_spinner_item );
		monthSpinner.setAdapter(monthAdapter);
		monthSpinner.setOnItemSelectedListener(this);

		dayAdapter = ArrayAdapter.createFromResource(this, R.array.days_31_array,android.R.layout.simple_spinner_item );
		daySpinner.setAdapter(dayAdapter);
		daySpinner.setOnItemSelectedListener(this);

		hourAdapter = ArrayAdapter.createFromResource(this, R.array.hour_array,android.R.layout.simple_spinner_item );
		hourSpinner.setAdapter(hourAdapter);
		hourSpinner.setOnItemSelectedListener(this);
		hourSpinner.setVisibility(View.GONE);

		minuteAdapter = ArrayAdapter.createFromResource(this, R.array.minute_array,android.R.layout.simple_spinner_item );
		minuteSpinner.setAdapter(minuteAdapter);
		minuteSpinner.setOnItemSelectedListener(this);
		minuteSpinner.setVisibility(View.GONE);

		periodAdapter = ArrayAdapter.createFromResource(this, R.array.period_array,android.R.layout.simple_spinner_item );
		periodSpinner.setAdapter(periodAdapter);
		periodSpinner.setOnItemSelectedListener(this);
		periodSpinner.setVisibility(View.GONE);


		if (isInEditMode)
		{
			taskId = (String) bundle.getSerializable ("positionTracker");
			System.out.println("taskID: "+ taskId);
			load();
		}
		
		//When notification checkbox is clicked due the following code - set spinners to view for data input
		alertBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (alertBox.isChecked()) {
					hourSpinner.setVisibility(View.VISIBLE);
					minuteSpinner.setVisibility(View.VISIBLE);
					periodSpinner.setVisibility(View.VISIBLE);
				}
				else {
					hourSpinner.setVisibility(View.GONE);
					minuteSpinner.setVisibility(View.GONE);
					periodSpinner.setVisibility(View.GONE);
				}
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int alertCheck;
				if (alertBox.isChecked())
					alertCheck = 1;
				else
					alertCheck = 0;
				
				if(!isInEditMode)
				{
					System.out.println("Add mode");
					
					String time = Calendar.getInstance().getTime().toString();
					helper.insertTask(taskTitleEditText.getText().toString(), taskEditText.getText().toString(), 
							time, daySpinner.getSelectedItem().toString(),
							Integer.toString(monthSpinner.getSelectedItemPosition() + 1), alertCheck, Integer.parseInt(userId));	
					Cursor c = helper.getTaskIdByCreatedAt(time, Integer.parseInt(userId));
					c.moveToFirst();
					taskId = helper.getTaskId(c);
					c.close();
					
					if(alertBox.isChecked())
					{
						helper.insertAlert(minuteSpinner.getSelectedItem().toString(), hourSpinner.getSelectedItem().toString(), periodSpinner.getSelectedItem().toString(), 
								Integer.parseInt(userId), taskId, daySpinner.getSelectedItem().toString(), monthSpinner.getSelectedItem().toString());
								
						String month = monthSpinner.getSelectedItem().toString();
						int monthToPass = 0;
						int day = Integer.parseInt(daySpinner.getSelectedItem().toString());
						
						int hour = Integer.parseInt(hourSpinner.getSelectedItem().toString());
						if (periodSpinner.getSelectedItem().toString().equals("PM")) {
							hour = hour + 12;
							if (hourSpinner.getSelectedItem().toString().equals("12"))
								hour = 12;
							System.out.println("PM selected hour: "+ hour);
						}
						if ((periodSpinner.getSelectedItem().toString().equals("AM")) && (hourSpinner.getSelectedItem().toString().equals("12"))) {
							hour = 0;
							System.out.println("AM selected 12: "+ hour);
						}
						int minute = Integer.parseInt(minuteSpinner.getSelectedItem().toString());
						int year = 2014; // will fix this later
						
						if(month.equals("January"))
							monthToPass = 0;
						else if(month.equals("February"))
							monthToPass = 1;
						else if(month.equals("March"))
							monthToPass = 2;
						else if(month.equals("April"))
							monthToPass = 3;
						else if(month.equals("May"))
							monthToPass = 4;
						else if(month.equals("June"))
							monthToPass = 5;
						else if(month.equals("July"))
							monthToPass = 6;
						else if(month.equals("August"))
							monthToPass = 7;
						else if(month.equals("September"))
							monthToPass = 8;
						else if(month.equals("October"))
							monthToPass = 9;
						else if(month.equals("November"))
							monthToPass = 10;
						else if(month.equals("December"))
							monthToPass = 11;
						
						startAlarm(year, monthToPass, day, hour, minute);
					}
					
				finish();
				}
				else
				{
					// update method
					System.out.println("Edit mode");
					helper.updateTask(taskTitleEditText.getText().toString(), taskEditText.getText().toString(),
							daySpinner.getSelectedItem().toString(), Integer.toString(monthSpinner.getSelectedItemPosition() + 1), 
							alertCheck, Integer.parseInt(taskId));
					if(alertBox.isChecked())
						helper.updateAlert(minuteSpinner.getSelectedItem().toString(), hourSpinner.getSelectedItem().toString(), periodSpinner.getSelectedItem().toString(),
								daySpinner.getSelectedItem().toString(), Integer.toString(monthSpinner.getSelectedItemPosition() + 1), 
								Integer.parseInt(taskId));
					finish();
				}
			}
		});

		// cancel method to cancel a new note
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(R.string.confirmMsgTask);
    	builder.setTitle("Confirm Delete");
    	builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				
				// Implement delete code here
				Cursor c = helper.getAlertByTaskId(taskId,userId);
				c.moveToFirst();
				helper.deleteAlert(taskId);
				
				c = helper.getTaskByTaskId(taskId, userId);
				c.moveToFirst();
				helper.deleteTask(taskId);
				c.close();
				
				finish();
			}
		} );
    	
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				finish();
			}
		});
    	
    	builder.create().show();
    	return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.delete_task_from_menu, menu);
		return true;
      
	}
	

	//when the data is selected per spinner do this:
	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int arg2,
			long arg3) {
	
		if(monthSpinner.getId() == R.id.monthSpinner)
		{
		
		//TextView textFieldSelected = (TextView) view ;
		//Toast.makeText(this, "You Selected " + textFieldSelected.getText(), Toast.LENGTH_LONG).show();
			
			
		}
		
		if(daySpinner.getId() == R.id.daySpinner)
		{
		
		//TextView textFieldSelected = (TextView) view ;
		//Toast.makeText(this, "You Selected " + textFieldSelected.getText(), Toast.LENGTH_LONG).show();
		
		}
		
		if(hourSpinner.getId() == R.id.hourSpinner)
		{
		
		//TextView textFieldSelected = (TextView) view ;
		//Toast.makeText(this, "You Selected " + textFieldSelected.getText(), Toast.LENGTH_LONG).show();
		
		}
		
		if(minuteSpinner.getId() == R.id.minuteSpinner)
		{
		
		//TextView textFieldSelected = (TextView) view ;
		//Toast.makeText(this, "You Selected " + textFieldSelected.getText(), Toast.LENGTH_LONG).show();
		
		}
		
		if(periodSpinner.getId() == R.id.periodSpinner)
		{
		
		//TextView textFieldSelected = (TextView) view ;
		//Toast.makeText(this, "You Selected " + textFieldSelected.getText(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}
	
	private void load() {
    	Bundle bundle = this.getIntent().getExtras();
		taskId = (String) bundle.getSerializable("positionTracker");
		Cursor c = helper.getTaskByTaskId(taskId, userId);
		
		System.out.println("Edit mode");
		
		c.moveToFirst();
		
		taskTitleEditText.setText(helper.getTaskname(c));
		taskEditText.setText(helper.getTaskdescription(c));
		taskDateText.setText(helper.getTaskCreatedat(c));
		
		daySpinner.setSelection(Integer.parseInt(helper.getTaskDay(c)) - 1);
		monthSpinner.setSelection(Integer.parseInt(helper.getTaskMonth(c)) - 1);
		
		if (Integer.parseInt(helper.getTaskHasAlert(c)) ==1)
		{
			System.out.println("inside taskHasAlert");
			c = helper.getAlertByTaskId(taskId, userId);
			c.moveToFirst();
			
			hourSpinner.setVisibility(View.VISIBLE);
			minuteSpinner.setVisibility(View.VISIBLE);
			periodSpinner.setVisibility(View.VISIBLE);
			alertBox.setChecked(true);
			
			int hour = Integer.parseInt(helper.getAlerthour(c));
			int minute = Integer.parseInt(helper.getAlertminute(c));
			int period;
			
			if (helper.getAlertperiod(c).equals("AM"))
				period = 0;
			else
				period = 1;
			
			hourSpinner.setSelection(hour-1);
			minuteSpinner.setSelection(minute);
			periodSpinner.setSelection(period);
			
		}
		else {
			hourSpinner.setVisibility(View.GONE);
			minuteSpinner.setVisibility(View.GONE);
			periodSpinner.setVisibility(View.GONE);
			alertBox.setChecked(false);
		}
		c.close();
		
	}
	
	public void startAlarm(int year, int monthToPass, int day, int hour, int minute){
		
		System.out.println("Starting Alarm");

		AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		Calendar calendar =  Calendar.getInstance();


		calendar.set(year, monthToPass, day, hour, minute );
		System.out.println("Calendar set the following year, month, day, hour, minute: " + year + " " + monthToPass + " " + day + " " + hour + " " + minute);
		long when = calendar.getTimeInMillis();         // notification time
		System.out.println("The value of when = " + when);
		
		Intent intent = new Intent(this, CreateNotification.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("USERID", userId);
		bundle.putSerializable("TASKID", taskId);
		intent.putExtras(bundle);
		intent.setAction("com.techbearcave.AYDY.Action");
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		alarmManager.set(AlarmManager.RTC, when, pendingIntent);
		System.out.println("Finishing Alarm");
	}
	
}
