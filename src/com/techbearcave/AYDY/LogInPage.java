package com.techbearcave.AYDY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.techbearcave.notetaker.R;

public class LogInPage extends Activity {

	private SQLiteHelper helper;
	private String EXTRA_ID = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in_page);
		
		helper = new SQLiteHelper(this);
		final Button loginButton = (Button)findViewById(R.id.loginButton);
		final Button registerButton = (Button)findViewById(R.id.registerButton);
		final EditText usernamefield = (EditText)findViewById(R.id.usernameField);
		final EditText passwordfield = (EditText)findViewById(R.id.passwordField);
		
		
		
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String username	= usernamefield.getText().toString();
				String password = passwordfield.getText().toString();
				
				if(username.length() >0 & password.length() >0)
				{
					
					
				}
				
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
