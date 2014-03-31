package com.techbearcave.AYDY;

import java.util.Calendar;

import com.techbearcave.notetaker.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditTaskActivity extends Activity {
	
	public static final int RESULT_DELETE = -9000;
	private boolean isInEditMode = true ; 
	private boolean isAddingNote = true; 
	private String userId;
	private String taskId;
	private Button saveButton;
	private Button cancelButton;
	private EditText titleEditText;
	private EditText taskEditText;
	private SQLiteHelper helper;
	private TextView dateTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		
		saveButton = (Button)findViewById(R.id.saveButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
    	titleEditText = (EditText)findViewById(R.id.titleEditText);
		taskEditText = (EditText)findViewById(R.id.taskEditText);
		dateTextView = (TextView)findViewById(R.id.dateTextView);
		helper = new SQLiteHelper(this);
		Bundle bundle = this.getIntent().getExtras();
		userId = (String) bundle.getSerializable ("stringToPassOn");
		isInEditMode = getIntent().getBooleanExtra(ListNotesActivity.isInEditMode, true);
		
		System.out.println("userID: "+ userId);
		
		
		if (isInEditMode)
		{
			taskId = (String) bundle.getSerializable ("positionTracker");
			System.out.println("noteID: "+ taskId);
			load();
		}
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
				System.out.println("Inside click");
				if(!isInEditMode)
				{
					System.out.println("Add mode");
					helper.insertNote(titleEditText.getText().toString(), taskEditText.getText().toString(), 
									Calendar.getInstance().getTime().toString(), Integer.parseInt(userId));	
					finish();
				}
				else
				{
					// update method
					System.out.println("Edit mode");
					helper.updateNote(titleEditText.getText().toString(), taskEditText.getText().toString(), Integer.parseInt(taskId));
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
    	Bundle bundle = this.getIntent().getExtras();
		taskId = (String) bundle.getSerializable("positionTracker");
		Cursor c = helper.getNoteByNoteId(taskId, userId);
		

		System.out.println("Edit mode");
		

		c.moveToFirst();
		
		titleEditText.setText(helper.getNotename(c));
		taskEditText.setText(helper.getNotedescription(c));
		dateTextView.setText(helper.getCreatedat(c));
		
		c.close();
	}
}
