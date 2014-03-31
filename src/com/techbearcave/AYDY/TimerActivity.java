package com.techbearcave.AYDY;

import com.techbearcave.notetaker.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends Activity {
	Button btn_start,btn_stop;
	TextView txt_remaintime,minutes,seconds;
	MyTimer mTimer;
	long remainMilli = 0;
	boolean isRunning=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
		btn_start = (Button) findViewById(R.id.start);
		btn_stop = (Button) findViewById(R.id.stop);
		txt_remaintime = (TextView) findViewById(R.id.text);
		minutes = (TextView) findViewById(R.id.minutes);
		seconds = (TextView) findViewById(R.id.seconds);

		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(isRunning){
					mTimer.cancel();
					mTimer=null;
					isRunning=false;
					btn_start.setText("Resume");
					
				}else{
					if (remainMilli == 0) {
						mTimer = new MyTimer(Integer.parseInt(minutes.getText().toString()) * 1000 *60 + Integer.parseInt(seconds.getText().toString()) * 1000, 1000);
					} else {
						mTimer = new MyTimer(remainMilli, 1000);
					}
					btn_start.setText("Pause");
					mTimer.start();	
					isRunning=true;
				}
				
			}
		});
		
		btn_stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				remainMilli =0;
				if(isRunning){
					mTimer.cancel();
					mTimer = null;
					txt_remaintime.setText("00:00");
					btn_start.setText("Start");
					isRunning=false;
					
				} else {
					/*
					mTimer = new MyTimer(remainMilli, 1000);
					btn_start.setText("Start");
					mTimer.start();	
					*/
					//mTimer = null;
					txt_remaintime.setText("00:00");
					btn_start.setText("Start");
				}
				
				}
				
			
		});
	}

	

	class MyTimer extends CountDownTimer {

		public MyTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			
		}

		@Override
		public void onFinish() {
			  
			new AlertDialog.Builder(TimerActivity.this).setTitle("Timer").setMessage("Countdown has finished!").setNeutralButton("Close", null).show();
			txt_remaintime.setText("00:00");
			btn_start.setText("Start");
			isRunning=false;
			remainMilli=0;
		}

	
		
		@Override
		public void onTick(long millisUntilFinished) {
			
			remainMilli = millisUntilFinished;
			
			//calculate minutes and seconds from milliseconds
			String minute=""+(millisUntilFinished/1000)/60;
			String second=""+(millisUntilFinished/1000)%60;
			
			//apply style to minute and second
			if((millisUntilFinished/1000)/60<10)
				minute="0"+(millisUntilFinished/1000)/60;
			if((millisUntilFinished/1000)%60<10)
				second="0"+(millisUntilFinished/1000)%60;
			
			// update textview with remaining time
			txt_remaintime.setText(minute+":"+second);
		}
		


	}
}