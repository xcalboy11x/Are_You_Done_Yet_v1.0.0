package com.techbearcave.AYDY;

import android.app.Activity;
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
				helper.insertUser(userName.toString(), firstName.toString(), lastName.toString(), passWord.toString(), emailAddress.toString());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_account, menu);
		return true;
	}

}
