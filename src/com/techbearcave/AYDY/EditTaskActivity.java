package com.techbearcave.AYDY;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.techbearcave.notetaker.R;


public class EditTaskActivity extends Activity implements OnItemSelectedListener {
	
	private CheckBox alertBox;
	private Spinner monthSpinner;
	private Spinner daySpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		
		alertBox = (CheckBox)findViewById(R.id.alertBox);
		monthSpinner = (Spinner)findViewById(R.id.monthSpinner);
		daySpinner =(Spinner)findViewById(R.id.daySpinner);
		
		ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.month_array,android.R.layout.simple_spinner_item );
		monthSpinner.setAdapter(monthAdapter);
		monthSpinner.setOnItemSelectedListener(this);
		monthSpinner.setVisibility(View.GONE);
		
		ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(this, R.array.day_array,android.R.layout.simple_spinner_item );
		daySpinner.setAdapter(dayAdapter);
		daySpinner.setOnItemSelectedListener(this);
		daySpinner.setVisibility(View.GONE);
		
		alertBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				monthSpinner.setVisibility(View.VISIBLE);
				daySpinner.setVisibility(View.VISIBLE);
				
				
			}
		});
		
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.list_tasks, menu);
		return true;
      
	}

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
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}


}
