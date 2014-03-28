package com.techbearcave.AYDY;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

import com.techbearcave.notetaker.R;

public class ListNotesActivity extends Activity {
	
	private ListView notesListView;
	private int editingNoteId = -1; 
	private Cursor model;
	private SQLiteHelper helper;
	private NoteAdapter adapter;
	private String userId;
	public static String ID_EXTRA = "";
	public static String ID_EDIT = "";
	public static String ID_NOTE = "";
	public static String isInEditMode;

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		// remove stuff
		
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
		setContentView(R.layout.activity_list_notes);
		notesListView = (ListView)findViewById(R.id.notesListView);
		helper = new SQLiteHelper(this);
		userId = getIntent().getStringExtra(NavigationMenu.ID_EXTRA);
		
		registerForContextMenu(notesListView);
		System.out.println("UserID: " + userId);

		model = helper.getNotesById(userId);
		startManagingCursor(model);
		adapter = new NoteAdapter(model);
		notesListView.setAdapter(adapter);
		
		notesListView.setOnItemClickListener(onListClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		
		Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("stringToPassOn", userId);
		
		editNoteIntent.putExtras(bundle);
		editNoteIntent.putExtra(isInEditMode, false);
		startActivity(editNoteIntent);
		
		return true; 
	}

	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String hi = helper.getNoteId(model);
			
			Intent editNoteIntent = new Intent(view.getContext(), EditNoteActivity.class);
			Bundle bundle = new Bundle ();
			bundle.putSerializable("stringToPassOn", userId);
			bundle.putSerializable("positionTracker", hi);
			editNoteIntent.putExtras(bundle);
			editNoteIntent.putExtra(isInEditMode, true);
			startActivity(editNoteIntent);
		}
	};

	class NoteAdapter extends CursorAdapter {
		NoteAdapter(Cursor c) {
			super(ListNotesActivity.this, c);
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			NoteHolder holder = (NoteHolder)row.getTag();
			holder.populateFrom(c, helper);
		}
		
		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			
			View row = inflater.inflate(R.layout.row, parent, false);
			NoteHolder holder = new NoteHolder(row);
			row.setTag(holder);
			return (row);
		}
	}
	
	class NoteHolder {
		private TextView noteName = null;
		private View row = null;
		
		NoteHolder(View row) {
			this.row = row;
			noteName = (TextView)row.findViewById(R.id.notename);
		}
		
		void populateFrom(Cursor c, SQLiteHelper helper) {
			noteName.setText(helper.getNotename(c));
		}
	}
	
}
