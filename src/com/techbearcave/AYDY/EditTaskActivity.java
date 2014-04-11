package com.techbearcave.AYDY;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
	private Button setAlertButton;
	private boolean checkAlertBox = false;
	

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
		setAlertButton = (Button)findViewById(R.id.setAlertButton);
		
		//set the display adapters for spinners & button
		ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.month_array,android.R.layout.simple_spinner_item );
		monthSpinner.setAdapter(monthAdapter);
		monthSpinner.setOnItemSelectedListener(this);
		monthSpinner.setVisibility(View.GONE);
		
		ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(this, R.array.days_31_array,android.R.layout.simple_spinner_item );
		daySpinner.setAdapter(dayAdapter);
		daySpinner.setOnItemSelectedListener(this);
		daySpinner.setVisibility(View.GONE);
		
		ArrayAdapter hourAdapter = ArrayAdapter.createFromResource(this, R.array.hour_array,android.R.layout.simple_spinner_item );
		hourSpinner.setAdapter(hourAdapter);
		hourSpinner.setOnItemSelectedListener(this);
		hourSpinner.setVisibility(View.GONE);
		
		ArrayAdapter minuteAdapter = ArrayAdapter.createFromResource(this, R.array.minute_array,android.R.layout.simple_spinner_item );
		minuteSpinner.setAdapter(minuteAdapter);
		minuteSpinner.setOnItemSelectedListener(this);
		minuteSpinner.setVisibility(View.GONE);
		
		ArrayAdapter periodAdapter = ArrayAdapter.createFromResource(this, R.array.period_array,android.R.layout.simple_spinner_item );
		periodSpinner.setAdapter(periodAdapter);
		periodSpinner.setOnItemSelectedListener(this);
		periodSpinner.setVisibility(View.GONE);
		
		setAlertButton.setVisibility(View.GONE);
		
		//When notification checkbox is clicked due the following code - set spinners to view for data input
		alertBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				checkAlertBox = true;
				monthSpinner.setVisibility(View.VISIBLE);
				daySpinner.setVisibility(View.VISIBLE);
				hourSpinner.setVisibility(View.VISIBLE);
				minuteSpinner.setVisibility(View.VISIBLE);
				periodSpinner.setVisibility(View.VISIBLE);
				setAlertButton.setVisibility(View.VISIBLE);
				
			}
		});
		
		setAlertButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// send alert data to db
				
				monthSpinner.setVisibility(View.GONE);
				daySpinner.setVisibility(View.GONE);
				hourSpinner.setVisibility(View.GONE);
				minuteSpinner.setVisibility(View.GONE);
				periodSpinner.setVisibility(View.GONE);
				setAlertButton.setVisibility(View.GONE);
				alertBox.setChecked(false);
				
			}
		});
		
		
		}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.list_tasks, menu);
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
	

}
