package com.techbearcave.AYDY;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
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

public class ListTasksActivity extends Activity {

	private ListView tasksListView;
	private Cursor model;
	private SQLiteHelper helper;
	private TaskAdapter adapter;
	private String userId;
	//public static String ID_EXTRA = "";
	//public static String ID_EDIT = "";
	//public static String ID_TASK = "";
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

		Cursor c = helper.getTaskByTaskId(taskId, userId);
		c.moveToFirst();
		helper.deleteTask(taskId);

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
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent editTaskIntent = new Intent(this, EditTaskActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("stringToPassOn", userId);

		editTaskIntent.putExtras(bundle);
		editTaskIntent.putExtra(isInEditMode, false);
		startActivity(editTaskIntent);

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

		
		
		tasksListView = (ListView)findViewById(R.id.tasksListView);
		helper = new SQLiteHelper(this);
		userId = getIntent().getStringExtra(NavigationMenu.ID_EXTRA);
		Bundle bundle = this.getIntent().getExtras();

//		generate context menu for long press - "Delete" note
		registerForContextMenu(tasksListView);

		if (getIntent().getBooleanExtra(NavigationMenu.selectByDay, false))
		{	
			int day, month;
			day = (Integer) bundle.getSerializable ("day");
			month = (Integer) bundle.getSerializable ("month");
			System.out.println("Inside Select by day : " + day + " " + month + " " + userId);
			model = helper.getTasksByDay(Integer.toString(day), Integer.toString(month), userId);
		}
		else
			model = helper.getTasksById(userId);
		startManagingCursor(model);
		adapter = new TaskAdapter(model);
		
		// trying to put code here to refresh List View for tasks; especially after notification is selected
		
		
		tasksListView.setAdapter(adapter);

		adapter.notifyDataSetChanged();
		Log.i("Listview", "Adapter was just set");
		//tasksListView.setVisibility(ListView.INVISIBLE);
		//tasksListView.setVisibility(ListView.VISIBLE);
		
		
		
	    

		tasksListView.setOnItemClickListener(onListClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_tasks, menu);

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

	class TaskAdapter extends CursorAdapter {
		TaskAdapter(Cursor c) {
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