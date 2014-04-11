package com.example.workoutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EditWorkoutItemActivity extends Activity {
	TextView TitleOfExerciseTV; 
	Button EditBTN;
	private String passedVar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_workout_itemview);
		TitleOfExerciseTV = (TextView)findViewById(R.id.ewivTitleOfExerciseTV);
		passedVar = getIntent().getStringExtra("com.example.workoutapp.EditWorkoutActivity._viewClicked");
		TitleOfExerciseTV.setText((String) EditWorkoutActivity.ExerciseAdapter.getItem(Integer.parseInt(passedVar)));
		EditBTN  = (Button)findViewById(R.id.ewivEditBTN);
		
		OnClickListeners();
	}

	private void OnClickListeners() {
		
		EditBTN.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.workoutapp.EditExerciseActivity");
				intent.putExtra("com.example.workoutapp.EditWorkoutItemActivity._viewClicked", passedVar);
				startActivity(intent);
				finish();
			}});
	}}
