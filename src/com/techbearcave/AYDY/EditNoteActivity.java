package com.techbearcave.AYDY;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;



import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.techbearcave.notetaker.R;


public class EditNoteActivity extends Activity {

	public static final int RESULT_DELETE = -9000;
	private boolean isInEditMode = true ; 
	private boolean isAddingNote = true; 
	private String userId;
	private String noteId;
	private Button saveButton;
	private Button cancelButton;
	private EditText titleEditText;
	private EditText noteEditText;
	private SQLiteHelper helper;
	private TextView dateTextView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        
        saveButton = (Button)findViewById(R.id.saveButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
    	titleEditText = (EditText)findViewById(R.id.titleEditText);
		noteEditText = (EditText)findViewById(R.id.noteEditText);
		dateTextView = (TextView)findViewById(R.id.dateTextView);
		helper = new SQLiteHelper(this);

		userId = getIntent().getStringExtra(ListNotesActivity.ID_EXTRA);
		System.out.println("EditNoteID: "+ userId);
		isInEditMode = getIntent().getBooleanExtra(ListNotesActivity.isInEditMode, true);
		
		if (isInEditMode)
				load();
		
		// cancel method to cancel a new note
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		// save method to save a new note
		
        saveButton.setOnClickListener(new OnClickListener() {
			// when the save button is clicked we want to check to see if were "editing" the note
        	// or if it is a brand new note 
        	
			@Override
			public void onClick(View v) {
				
				if(!isInEditMode)
				{
					helper.insertNote(titleEditText.getText().toString(), noteEditText.getText().toString(), 
									Calendar.getInstance().getTime().toString(), Integer.parseInt(userId));
					finish();
				}
				else
				{
					// update method
					finish();
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
    
    private void load() {
    	noteId = getIntent().getStringExtra(ListNotesActivity.ID_NOTE);
		Cursor c = helper.getNoteByNoteId(noteId, userId);
		
		c.moveToFirst();
		titleEditText.setText(helper.getNotename(c));
		noteEditText.setText(helper.getNotedescription(c));
		dateTextView.setText(helper.getCreatedat(c));
		
		c.close();
	}
}
