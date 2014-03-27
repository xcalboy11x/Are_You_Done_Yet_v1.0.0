package com.techbearcave.AYDY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
				String dbUsername = helper.getUsername(username);
				String dbPassword = helper.getPassword(password);
				Log.i("username", username);
				
				if(username.length() >0 & password.length() >0)
				{
					try 
					{

						
						if(username.equals(dbUsername) && password.equals(dbPassword))
						{
							System.out.println("Username & Password matches");
							Toast.makeText(getApplicationContext(), "Successful Login!", Toast.LENGTH_LONG).show();
							
							Intent navIntent = new Intent(LogInPage.this, NavigationMenu.class);
							startActivity(navIntent);
						}
							
					}
					
					catch (Exception e) {
						System.out.println("Username is " + username);
						System.out.println("dbUsername is " + dbUsername);
						System.out.println("Password is " + password);
						System.out.println("dbPassword is " + dbPassword);
						
						Toast.makeText(getApplicationContext(), "Unable to login", Toast.LENGTH_LONG).show(); 
						}
					
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
