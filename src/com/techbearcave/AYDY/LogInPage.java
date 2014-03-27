package com.techbearcave.AYDY;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techbearcave.notetaker.R;

public class LogInPage extends Activity {

	private SQLiteHelper helper;
	public static String ID_EXTRA = "._ID";
	
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
<<<<<<< HEAD
				
				if(username.length() >0 && password.length() >0){
					Cursor c = helper.getUsername(username);

					if (c != null) {

						if (c.moveToFirst()) {
							do {
								String dbUserName = c.getString(c
										.getColumnIndex("Username"));
								String dbPassword = c.getString(c
										.getColumnIndex("Password"));
								String dbUserId = c.getString(c
										.getColumnIndex("_id"));
								System.out.println("ID: "+dbUserId);
								System.out.println("User: "+dbUserName);
								System.out.println("PW: "+dbPassword);
								if (dbUserName.equals(username)&&dbPassword.equals(password)) {
									Intent intent = new Intent(LogInPage.this, NavigationMenu.class);
									intent.putExtra(ID_EXTRA, String.valueOf(dbUserId));
									startActivity(intent);
								}
								else
									Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();

							} 
							while (c.moveToNext());
=======
				String dbUsername = helper.getUsername(username);
				String dbPassword = helper.getPassword(password);
				
				
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
>>>>>>> b4bd5aa131cf9bcf88cb98d2e76c1d9244357ac2
						}
						else
							Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();

					}
				} else
					Toast.makeText(getApplicationContext(), "Missing field/s", Toast.LENGTH_SHORT).show();
				
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
