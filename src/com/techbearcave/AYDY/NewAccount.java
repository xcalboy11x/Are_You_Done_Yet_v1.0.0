package com.techbearcave.AYDY;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.techbearcave.notetaker.R;

public class NewAccount extends Activity {
	private EditText userName;
	private EditText firstName;
	private EditText lastName;
	private EditText passWord;
	private EditText passWordConf;
	private EditText emailAddress;
	private Button submitButton;
	private SQLiteHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_account);
		
		userName = (EditText)findViewById(R.id.registerUsernameField);
		firstName = (EditText)findViewById(R.id.registerFirstNameField);
		lastName = (EditText)findViewById(R.id.registerLastNameField);
		passWord = (EditText)findViewById(R.id.registerPasswordField1);
		passWordConf = (EditText)findViewById(R.id.registerPasswordField2);
		emailAddress = (EditText)findViewById(R.id.registerEmailField);
		submitButton = (Button)findViewById(R.id.registerSubmitButton);
		helper = new SQLiteHelper(this);
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(checkAccountFields())
				{

					helper.insertUser(userName.toString(), firstName.toString(), lastName.toString(), passWord.toString(), emailAddress.toString());
					// I swear you need to getText + toString it first
					helper.insertUser(userName.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), 
							passWord.getText().toString(), emailAddress.getText().toString());
					Intent navIntent = new Intent(NewAccount.this, LogInPage.class);
					startActivity(navIntent);
				}
				else
					System.out.println("Wrong");
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_account, menu);
		return true;
	}
	
	public boolean checkAccountFields() {
		System.out.println("user: " + userName.getText().toString());
		System.out.println("first: " + firstName.getText().toString());
		System.out.println("last: "+ lastName.getText().toString());
		System.out.println("p1: " + passWord.getText().toString());
		System.out.println("p2: " + passWordConf.getText().toString());
		
		if(userName.getText().toString().equals("")){
			System.out.println("Blank user");
			return false;
		}
		else if(firstName.getText().toString().equals("")){
			System.out.println("Blank first");
			return false;
		}
		else if(lastName.getText().toString().equals("")){
			System.out.println("Blank last");
			return false;
		}
		else if(passWord.getText().toString().equals("")){
			System.out.println("Blank pass");
			return false;
		}
		else if(passWordConf.getText().toString().equals("")){
			System.out.println("Blank pass conf");
			return false;
		} 
		else if ((passWord.getText().toString().compareTo(passWordConf.getText().toString())!=0)) {
			System.out.println("Password doesn't match");
			return false;
		}
		else if (emailAddress.getText().toString().equals("")) {
			System.out.println("Blank email");
			return false;
		}
		else 
			return true;

	}

}
