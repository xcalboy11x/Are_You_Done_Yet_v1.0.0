package com.techbearcave.AYDY;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.techbearcave.notetaker.R;


public class CreateNotification extends BroadcastReceiver {
	String userId, taskId;
	private SQLiteHelper helper;
	String taskName;
	String taskDescription;
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		System.out.println("Alarm recieved.");
		
		helper = new SQLiteHelper(context);
		Bundle bundle = intent.getExtras();
		
		userId = (String)bundle.getSerializable("USERID");
		taskId= (String)bundle.getSerializable("TASKID");
		System.out.println("UserId: " + userId);
		System.out.println("TaskId: " + taskId);

		Cursor c = helper.getTaskByTaskId(taskId, userId);
		c.moveToFirst();
		taskName = helper.getTaskname(c);
		taskDescription = helper.getTaskdescription(c);
		
		NotificationManager nM;
		nM = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		
		Notification task = new Notification(R.drawable.ic_launcher, "Task due!", System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, EditTaskActivity.class), 0);
		task.setLatestEventInfo(context, taskName, taskDescription, contentIntent);
		nM.notify(1234, task);
	}
}
