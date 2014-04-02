package com.techbearcave.AYDY;

import com.techbearcave.AYDY.ListNotesActivity.NoteAdapter;
import com.techbearcave.AYDY.ListNotesActivity.NoteHolder;
import com.techbearcave.notetaker.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ListTasksActivity extends Activity {
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent launchTask = new Intent(this, EditTaskActivity.class);
		startActivity(launchTask);
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_tasks);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_tasks, menu);
		
		return true;
		
	}
	
	
}
