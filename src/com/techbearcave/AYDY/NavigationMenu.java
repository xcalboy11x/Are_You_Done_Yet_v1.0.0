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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_menu);
		
		final Button noteButton = (Button)findViewById(R.id.noteButton);
		final Button taskButton = (Button)findViewById(R.id.taskButton);
		
		noteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent launchNotes = new Intent(NavigationMenu.this, ListNotesActivity.class);
				startActivityForResult(launchNotes, 1);
				
			}
		});
		
		taskButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent launchNotes = new Intent(NavigationMenu.this, ListTasksActivity.class);
				startActivityForResult(launchNotes, 1);
				
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
