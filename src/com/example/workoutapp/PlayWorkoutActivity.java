package com.example.workoutapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
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
	private Button pwPauseResumeBTN, pwSkipBTN;
	private String FILE; //Current playing Exercise Filepath
	private int currentExerciseNum;
	private long exerciseStart;
	private CountDownTimer countDownTimer;
	public static enum PlayState {PLAY, PAUSE};
	private PlayState playBTNState;
	private MediaPlayer player;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Log.d(TAG, "PlayWorkoutActivity.onCreate: enter");
		setContentView(R.layout.play_workout);
		String pwTitle = getIntent().getExtras().getString(WorkoutActivity.TITLE_EXTRA);
		pwInfo = WorkoutActivity.getWorkoutInfo(pwTitle);
		exerciseList = pwInfo.getExerciseList();
		currentExercise = exerciseList.get(0);
		
		pwPauseResumeBTN = (Button)findViewById(R.id.pwPauseResumeBTN);
		pwPauseResumeBTN.setOnClickListener(startButtonListener);
		pwSkipBTN = (Button)findViewById(R.id.pwSkipBTN);
		pwSkipBTN.setOnClickListener(skipButtonListener);
		
		// NEED TO SET THE EXERCISES AND FIGURE A WAY TO RUN IT.
		pwTitleOfWorkoutTV = (TextView)findViewById(R.id.pwTitleOfWorkoutTV);
		pwTitleOfWorkoutTV.setText(pwInfo.getWorkoutName());
		pwExerciseTV = (TextView)findViewById(R.id.pwExerciseTV);
		pwCountdownTV = (TextView)findViewById(R.id.pwCountdownTV);

		player = new MediaPlayer();
		playBTNState = PlayState.PAUSE;
		
		//FILE = ""; // call eInfo.getFilePath()
	}
	
	
	@Override
	protected void onStart(){
		super.onStart();
		createCountdownTimer(0, (exerciseList.get(0).getDuration() * 1000));
	}
	
	@Override
	protected void onStop() {

		super.onStop();
		countDownTimer.cancel();
	}

	private OnClickListener startButtonListener = new OnClickListener() {

		public void onClick(View v) {
			// Resumes timer
			if (playBTNState == PlayState.PLAY){
				playBTNState = PlayState.PAUSE;
				pwPauseResumeBTN.setText("Pause");
				createCountdownTimer(currentExerciseNum, Integer.parseInt(pwCountdownTV.getText().toString()) * 1000);
			}
			// Pauses timer
			else{
				playBTNState = PlayState.PLAY;
				pwPauseResumeBTN.setText("Resume");
				countDownTimer.cancel();
			}
			
		}
		
	};
	
	private OnClickListener skipButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (exerciseList.size() - 1 != currentExerciseNum){
				countDownTimer.cancel();
				currentExerciseNum++;
				createCountdownTimer(currentExerciseNum, exerciseList.get(currentExerciseNum).getDuration() * 1000);
			}
			else {
				countDownTimer.cancel();
				pwCountdownTV.setText("0");
			}
		}
		
	};
	
	public void createCountdownTimer(int exerciseNum, long timeMsec){
		Log.d(TAG, "createCountdownTimer: enter");
		currentExerciseNum = exerciseNum;
		currentExercise = exerciseList.get(currentExerciseNum);
		exerciseStart = System.currentTimeMillis();
		String currentExerciseName = currentExercise.getExerciseName().toString();
		pwExerciseTV.setText(currentExerciseName);
		String currentSec = (int) Math.ceil(timeMsec/1000) + "";
		pwCountdownTV.setText(currentSec);
			
		Log.d(TAG, "createCountdownTimer: creating timer:");
		playVoice();
		countDownTimer = new CountDownTimer(timeMsec, 1000){

			@Override
			public void onFinish() {
				if(currentExerciseNum < exerciseList.size() - 1){
					currentExerciseNum++;
					createCountdownTimer(currentExerciseNum, 
							exerciseList.get(currentExerciseNum).getDuration() * 1000);
					
				}
				else{
					pwCountdownTV.setText("0");
					pwExerciseTV.setText("Workout Complete!");
				}
				
			}

			@Override
			public void onTick(long timeLeft) {
				int roundedTime = (int) Math.ceil(timeLeft/(float)1000);
				Log.d(TAG, "onTick called: " + timeLeft + " msec left, rounded to " + roundedTime);
				pwCountdownTV.setText((int) Math.ceil(timeLeft/(float)1000) + "");
				if(roundedTime <= 2){
					Handler oneSecHandler = new Handler();
					oneSecHandler.postDelayed(new Runnable(){
						public void run(){
							pwCountdownTV.setText("1");
						}
					}, 1000);
				}
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
		try {
			if(currentExercise.getRecordingPath() != null){
				player.reset();
				player.setDataSource(currentExercise.getRecordingPath());
				player.prepare();
				player.start();
			}
		} catch (Exception e) {
			Log.e(TAG, "Failed to play recording", e);
		}
	}
}

