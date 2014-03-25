package com.techbearcave.AYDY;




import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.techbearcave.notetaker.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EditNoteActivity extends Activity {

	


	public static final int RESULT_DELETE = -9000;
	private boolean isInEditMode = true ; 
	private boolean isAddingNote = true; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        
        final Button saveButton = (Button)findViewById(R.id.saveButton);
        final Button cancelButton = (Button)findViewById(R.id.cancelButton);
    	final EditText titleEditText = (EditText)findViewById(R.id.titleEditText);
		final EditText noteEditText = (EditText)findViewById(R.id.noteEditText);
		final TextView dateTextView = (TextView)findViewById(R.id.dateTextView);
		
		// set extra to serializable to send note data via intent
		
		Serializable extra = getIntent().getSerializableExtra("Note");
		if(extra != null) // check here to make sure extra actually has data 
		{
			Note note = (Note)extra;
			titleEditText.setText(note.getTitle());
			noteEditText.setText(note.getNote());
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String date = dateFormat.format(note.getDate());
			
			dateTextView.setText(date);
			
			isInEditMode = false;
			titleEditText.setEnabled(false);
			noteEditText.setEnabled(false);
			saveButton.setText("Edit");
			
			isAddingNote = false;
		}
		
		// cancel method to cancel a new note
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED, new Intent());
				finish();
				
			}
		});
		
		// save method to save a new note
		
        saveButton.setOnClickListener(new OnClickListener() {
			// when the save button is clicked we want to check to see if were "editing" the note
        	// or if it is a brand new note 
        	
			@Override
			public void onClick(View v) {
				
				if(isInEditMode)
				{
					Intent returnIntent  = new Intent();
					Note note = new Note(titleEditText.getText().toString(), noteEditText.getText().toString(), 
							Calendar.getInstance().getTime());
					returnIntent.putExtra("Note", note);
					setResult(RESULT_OK, returnIntent);
					finish();
					
				}
				else
				{
					isInEditMode = true; 
					saveButton.setText("Save");
					titleEditText.setEnabled(true);
					noteEditText.setEnabled(true);
				}
		
				
			}
		});
    }


    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(R.string.confirmMsg);
    	builder.setTitle("Confirm Delete");
    	builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				Intent returnIntent = new Intent();
				
				setResult(RESULT_DELETE, returnIntent);
				finish();
			}
		} );
    	
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
			}
		});
    	
    	builder.create().show();
    	return true;
	}
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete_note_from_menu, menu);
        if(isAddingNote)
        {
        	menu.removeItem(R.id.deleteNote);
        }
        return true;
    }
    
}
