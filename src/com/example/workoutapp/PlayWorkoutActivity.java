package com.example.workoutapp;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
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
	}
	
	private OnClickListener startButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// START WORKOUT CODE
		}
		
	};
	
	private OnClickListener skipButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// SKIP CURRENT EXERCISE CODE
		}
		
	};

}
