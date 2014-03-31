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
	
	private ListView tasksListView;
	private int editingNoteId = -1; 
	private Cursor model;
	private SQLiteHelper helper;
	private NoteAdapter adapter;
	private String userId;
	public static String ID_EXTRA = "";
	public static String ID_EDIT = "";
	public static String ID_NOTE = "";
	public static String isInEditMode;
	
	// Write the code here to delete the note from long press
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		tasksListView = (ListView)findViewById(R.id.tasksListView);
		tasksListView.setAdapter(adapter);

		long test = adapter.getItemId(info.position);
		System.out.println("Here is the note position in the listview: " + test);
		String taskId = String.valueOf(test);

		Cursor c = helper.getNoteByNoteId(taskId, userId);
		c.moveToFirst();
		helper.deleteNote(taskId);

		c.close();

		tasksListView.setAdapter(adapter);
		adapter.notifyDataSetChanged(); //this is suppose to notify adapter of changes and redraw listview


		// 1. Find the position in ListView
		// 2. Find NoteID in Listview
		// 3. Send delete Query to database
		// 4. Redisplay the Listview



		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_tasks);

		tasksListView = (ListView)findViewById(R.id.notesListView);
		helper = new SQLiteHelper(this);
		userId = getIntent().getStringExtra(NavigationMenu.ID_EXTRA);
		
		//generate context menu for long press - "Delete" note
		registerForContextMenu(tasksListView);

		model = helper.getNotesById(userId);
		startManagingCursor(model);
		adapter = new NoteAdapter(model);
		tasksListView.setAdapter(adapter);
		
		tasksListView.setOnItemClickListener(onListClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_tasks, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		
		Intent editTaskIntent = new Intent(this, EditTaskActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("stringToPassOn", userId);
		
		editTaskIntent.putExtras(bundle);
		editTaskIntent.putExtra(isInEditMode, false);
		startActivity(editTaskIntent);
		
		return true; 
	}

	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String hi = helper.getTaskId(model);
			
			Intent editTaskIntent = new Intent(view.getContext(), EditTaskActivity.class);
			Bundle bundle = new Bundle ();
			bundle.putSerializable("stringToPassOn", userId);
			bundle.putSerializable("positionTracker", hi);
			editTaskIntent.putExtras(bundle);
			editTaskIntent.putExtra(isInEditMode, true);
			startActivity(editTaskIntent);
		}
	};
	
	class NoteAdapter extends CursorAdapter {
		NoteAdapter(Cursor c) {
			super(ListTasksActivity.this, c);
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			TaskHolder holder = (TaskHolder)row.getTag();
			holder.populateFrom(c, helper);
		}
		
		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			
			View row = inflater.inflate(R.layout.row, parent, false);
			TaskHolder holder = new TaskHolder(row);
			row.setTag(holder);
			return (row);
		}
	}
	
	class TaskHolder {
		private TextView taskName = null;
		private View row = null;
		
		TaskHolder(View row) {
			this.row = row;
			taskName = (TextView)row.findViewById(R.id.notename);
		}
		
		void populateFrom(Cursor c, SQLiteHelper helper) {
			taskName.setText(helper.getNotename(c));
		}
	}
}
