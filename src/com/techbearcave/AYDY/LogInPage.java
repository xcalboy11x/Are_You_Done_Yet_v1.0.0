package com.techbearcave.AYDY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.techbearcave.notetaker.R;

public class LogInPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in_page);
		
		Button loginButton = (Button)findViewById(R.id.loginButton);
		Button registerButton = (Button)findViewById(R.id.registerButton);
		
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent launchNewAccount = new Intent(LogInPage.this, NewAccount.class);
				startActivity(launchNewAccount);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in_page, menu);
		return true;
	}

}
