package com.techbearcave.AYDY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

import com.techbearcave.notetaker.R;

public class NavigationMenu extends Activity {
	public static String ID_EXTRA = "._ID";
CalendarView calendar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_menu);
		
		final Button noteButton = (Button)findViewById(R.id.noteButton);
		final Button taskButton = (Button)findViewById(R.id.taskButton);
		final Button timerButton = (Button)findViewById(R.id.timerButton);
		final Button helpButton = (Button)findViewById(R.id.helpButton);
		
		calendar = (CalendarView)findViewById(R.id.calendar);
		calendar.setOnDateChangeListener(new OnDateChangeListener(){

			@Override
			public void onSelectedDayChange(CalendarView view,
			int year, int month, int dayOfMonth) {
			Toast.makeText(getApplicationContext(),
			dayOfMonth +"/"+month+"/"+ year,Toast.LENGTH_LONG).show();}});
		
		noteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userId = getIntent().getStringExtra(LogInPage.ID_EXTRA);
				Intent launchNotes = new Intent(NavigationMenu.this, ListNotesActivity.class);
				launchNotes.putExtra(ID_EXTRA, userId);
				startActivityForResult(launchNotes, 1);
				
			}
		});
		
		taskButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent launchNotes = new Intent(NavigationMenu.this, ListTasksActivity.class);
				startActivity(launchNotes);
				
			}
		});

		timerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent launchTimer = new Intent(NavigationMenu.this, TimerActivity.class);
				startActivity(launchTimer);
				
			}
		});
		
		helpButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent launchHelpMenu = new Intent(NavigationMenu.this, HelpMenu.class);
				startActivity(launchHelpMenu);
				
			}
		});
	
	
	
	
	
	
	
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation_menu, menu);
		return true;
	}

}
