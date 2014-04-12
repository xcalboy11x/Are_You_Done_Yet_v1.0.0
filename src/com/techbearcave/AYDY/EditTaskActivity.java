package com.techbearcave.AYDY;



import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
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
	private boolean checkAlertBox = false;
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
					
					helper.insertTask(taskTitleEditText.getText().toString(), taskEditText.getText().toString(), 
							Calendar.getInstance().getTime().toString(), daySpinner.getSelectedItem().toString(),
							Integer.toString(monthSpinner.getSelectedItemPosition() + 1), alertCheck, Integer.parseInt(userId));	
					
					if(alertBox.isChecked())
						helper.insertAlert(minuteSpinner.getSelectedItem().toString(), hourSpinner.getSelectedItem().toString(),
								Integer.parseInt(userId), taskId, daySpinner.getSelectedItem().toString(), monthSpinner.getSelectedItem().toString());
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
						helper.updateAlert(minuteSpinner.getSelectedItem().toString(), hourSpinner.getSelectedItem().toString(), 
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
		
		TextView textFieldSelected = (TextView) view ;
		Toast.makeText(this, "You Selected " + textFieldSelected.getText(), Toast.LENGTH_LONG).show();
			
			
		}
		
		if(daySpinner.getId() == R.id.daySpinner)
		{
		
		TextView textFieldSelected = (TextView) view ;
		Toast.makeText(this, "You Selected " + textFieldSelected.getText(), Toast.LENGTH_LONG).show();
		
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
			hourSpinner.setVisibility(View.VISIBLE);
			minuteSpinner.setVisibility(View.VISIBLE);
			periodSpinner.setVisibility(View.VISIBLE);
			alertBox.setChecked(true);
		}
		else {
			hourSpinner.setVisibility(View.GONE);
			minuteSpinner.setVisibility(View.GONE);
			periodSpinner.setVisibility(View.GONE);
			alertBox.setChecked(false);
		}
		
		c.close();
	}
}
