package com.techbearcave.AYDY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.techbearcave.notetaker.R;

public class NavigationMenu extends Activity {
	public static String ID_EXTRA = "._ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_menu);
		
		final Button noteButton = (Button)findViewById(R.id.noteButton);
		final Button taskButton = (Button)findViewById(R.id.taskButton);
		final Button helpButton = (Button)findViewById(R.id.helpButton);
		
		
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

	
		helpButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent launchHelp = new Intent(NavigationMenu.this, activity_help_menu.xml);
				
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
