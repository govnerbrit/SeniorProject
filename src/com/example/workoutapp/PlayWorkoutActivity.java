package com.example.workoutapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.os.CountDownTimer;

public class PlayWorkoutActivity extends Activity {

	private Workout pwInfo;
	public static List<Exercise> exerciseList = new ArrayList<Exercise>();
	public static Exercise firstExercise;
	private TextView pwExerciseTV, pwCountdownTV, pwTitleOfWorkoutTV;
	private Button pwStartBTN, pwSkipBTN;
	private String FILE; //Current playing Exercise Filepath

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_workout);
		String pwTitle = getIntent().getExtras().getString(WorkoutActivity.TITLE_EXTRA);
		pwInfo = WorkoutActivity.getWorkoutInfo(pwTitle);
		exerciseList = pwInfo.getExerciseList();
		firstExercise = exerciseList.get(0);
		
		pwStartBTN = (Button)findViewById(R.id.pwStartBTN);
		pwStartBTN.setOnClickListener(startButtonListener);
		pwSkipBTN = (Button)findViewById(R.id.pwSkipBTN);
		pwSkipBTN.setOnClickListener(skipButtonListener);
		
		// NEED TO SET THE EXERCISES AND FIGURE A WAY TO RUN IT.
		pwTitleOfWorkoutTV = (TextView)findViewById(R.id.pwTitleOfWorkoutTV);
		pwTitleOfWorkoutTV.setText(pwInfo.getWorkoutName());
		pwExerciseTV = (TextView)findViewById(R.id.pwExerciseTV);
		pwExerciseTV.setText(firstExercise.getExerciseName().toString());
		pwCountdownTV = (TextView)findViewById(R.id.pwCountdownTV);
		pwCountdownTV.setText(firstExercise.getDuration() + "");
		
		FILE = ""; // call eInfo.getFilePath()
	}
	
	private OnClickListener startButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			playVoice();
			// START WORKOUT CODE
		}
		
	};
	
	private OnClickListener skipButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// SKIP CURRENT EXERCISE CODE
		}
		
	};

	public void playVoice()
	{
		/*
		 *When playing media player on emulator there will be static due to it using the computers mic
		 *When played on device no static will be present. 
		 */
		MediaPlayer player = new MediaPlayer();
		try {
			player.setDataSource(FILE);
			player.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				
			}
		});
		
		
	}

}

