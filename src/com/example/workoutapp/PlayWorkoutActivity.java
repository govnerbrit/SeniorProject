package com.example.workoutapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.os.CountDownTimer;

public class PlayWorkoutActivity extends Activity {

	private static final String TAG = "PlayWorkoutActivity";
	private Workout pwInfo;
	public static List<Exercise> exerciseList = new ArrayList<Exercise>();
	public static Exercise currentExercise;
	private TextView pwExerciseTV, pwCountdownTV, pwTitleOfWorkoutTV;
	private Button pwStartBTN, pwSkipBTN;
	private String FILE; //Current playing Exercise Filepath
	private int currentExerciseNum;
	private long exerciseStart;
	private CountDownTimer countDownTimer;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_workout);
		String pwTitle = getIntent().getExtras().getString(WorkoutActivity.TITLE_EXTRA);
		pwInfo = WorkoutActivity.getWorkoutInfo(pwTitle);
		exerciseList = pwInfo.getExerciseList();
		currentExercise = exerciseList.get(0);
		
		pwStartBTN = (Button)findViewById(R.id.pwStartBTN);
		pwStartBTN.setOnClickListener(startButtonListener);
		pwSkipBTN = (Button)findViewById(R.id.pwSkipBTN);
		pwSkipBTN.setOnClickListener(skipButtonListener);
		
		// NEED TO SET THE EXERCISES AND FIGURE A WAY TO RUN IT.
		pwTitleOfWorkoutTV = (TextView)findViewById(R.id.pwTitleOfWorkoutTV);
		pwExerciseTV = (TextView)findViewById(R.id.pwExerciseTV);
		pwCountdownTV = (TextView)findViewById(R.id.pwCountdownTV);

		/*for(Exercise e: exerciseList){
			currentExercise = e;
			pwTitleOfWorkoutTV.setText(pwInfo.getWorkoutName());
			pwExerciseTV.setText(currentExercise.getExerciseName().toString());
			pwCountdownTV.setText(currentExercise.getDuration() + "");
			
		}*/
		
		createCountdownTimer(0, exerciseList.get(0).getDuration() * 1000);
		
		//FILE = ""; // call eInfo.getFilePath()
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
	
	public void createCountdownTimer(int exerciseNum, long timeMsec){
		Log.d(TAG, "createCountdownTimer: enter");
		currentExerciseNum = exerciseNum;
		currentExercise = exerciseList.get(currentExerciseNum);
		exerciseStart = System.currentTimeMillis();
		String currentExerciseName = currentExercise.getExerciseName().toString();
		pwExerciseTV.setText(currentExerciseName);
		String currentSec = Math.round(timeMsec/1000) + "";
		pwCountdownTV.setText(currentSec);
			
		Log.d(TAG, "createCountdownTimer: creating timer:");
		countDownTimer = new CountDownTimer(timeMsec, 1000){

			@Override
			public void onFinish() {
				if(currentExerciseNum < exerciseList.size() - 1){
					createCountdownTimer(currentExerciseNum + 1, 
							exerciseList.get(currentExerciseNum + 1).getDuration() * 1000);
				}
				
			}

			@Override
			public void onTick(long timeLeft) {
				Log.d(TAG, "onTick called: " + timeLeft + " msec left");
				pwCountdownTV.setText(Math.round(timeLeft/1000) + "");				
				
			}
			
		};
		
		countDownTimer.start();
	}

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

