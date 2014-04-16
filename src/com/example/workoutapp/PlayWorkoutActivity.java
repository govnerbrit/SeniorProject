package com.example.workoutapp;

import java.util.ArrayList;
import java.util.List;

import com.example.workoutapp.EditWorkoutActivity.ExerciseAdapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.os.CountDownTimer;

public class PlayWorkoutActivity extends ListActivity {

	private Workout pwInfo;
	public static List<Exercise> exerciseList = new ArrayList<Exercise>();
	private TextView pwExerciseTV, pwCountdownTV;
	private Button pwStartBTN, pwSkipBTN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_workout);
		String pwTitle = getIntent().getExtras().getString(WorkoutActivity.TITLE_EXTRA);
		pwInfo = WorkoutActivity.getWorkoutInfo(pwTitle);
		exerciseList = pwInfo.getExerciseList();
		
		pwStartBTN = (Button)findViewById(R.id.pwStartBTN);
		pwStartBTN.setOnClickListener(startButtonListener);
		pwSkipBTN = (Button)findViewById(R.id.pwSkipBTN);
		pwSkipBTN.setOnClickListener(skipButtonListener);
		
		// NEED TO SET THE EXERCISES AND FIGURE A WAY TO RUN IT.
		//pwExerciseTV = (TextView)findViewById(R.id.pwExerciseTV);
		//pwCountdownTV = (TextView)findViewById(R.id.pwCountdownTV);
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
