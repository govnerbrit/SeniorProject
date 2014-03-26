package com.example.workoutapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WorkoutItemActivity extends Activity {

	TextView TitleOfWorkoutTV; 
	Button EditBTN, StartBTN;
	private String passedVar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_itemview);
		TitleOfWorkoutTV = (TextView)findViewById(R.id.wivTitleOfWorkoutTV);
		passedVar = getIntent().getStringExtra("com.example.workoutapp.WorkoutActivity._viewClicked");
		TitleOfWorkoutTV.setText((String) WorkoutActivity.adapter.getItem(Integer.parseInt(passedVar)));
		StartBTN = (Button)findViewById(R.id.wivStartBTN);
		EditBTN  = (Button)findViewById(R.id.wivEditBTN);
		
		OnClickListeners();
	}

	private void OnClickListeners() {
		StartBTN.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
		EditBTN.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.workoutapp.EditWorkoutActivity");
				intent.putExtra("com.example.workoutapp.WorkoutItemActivity._viewClicked", passedVar);
				startActivity(intent);
				finish();
			}});
	}
	
}
