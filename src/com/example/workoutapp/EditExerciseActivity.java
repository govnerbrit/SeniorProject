package com.example.workoutapp;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditExerciseActivity extends Activity{

	EditText TitleOfExerciseET, SecondsET;
	Button PlayBTN, RecordBTN, DeleteRecordingBTN, SaveBTN, DeleteBTN;
	private String passedVar;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_exercise);
		PlayBTN = (Button)findViewById(R.id.eePlayBTN);
		RecordBTN = (Button)findViewById(R.id.eeRecordBTN);
		DeleteRecordingBTN = (Button)findViewById(R.id.eeDeleteRecordingBTN);
		SaveBTN = (Button)findViewById(R.id.eeSaveBTN);
		DeleteBTN = (Button)findViewById(R.id.eeDeleteBTN);
		passedVar = getIntent().getStringExtra("com.example.workoutapp.EditWorkoutItemActivity._viewClicked");
		TitleOfExerciseET = (EditText)findViewById(R.id.eeTitleOfExerciseET);
		TitleOfExerciseET.setHint((String) EditWorkoutActivity.ExerciseAdapter.getItem(Integer.parseInt(passedVar)));
		TitleOfExerciseET.addTextChangedListener(ExerciseNameChangeListener);
		SecondsET = (EditText)findViewById(R.id.eeSecondsET);
		SecondsET.addTextChangedListener(SecondsChangeListener);
		
		SetUpOnClickListeners();
	}

	private void SetUpOnClickListeners() {
		PlayBTN.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		RecordBTN.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		DeleteRecordingBTN.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		SaveBTN.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				EditWorkoutActivity.ExerciseAdapter.notifyDataSetChanged();
				finish();
				
			}
		});
		DeleteBTN.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				EditWorkoutActivity.ExerciseList.remove(Integer.parseInt(passedVar));
				EditWorkoutActivity.ExerciseAdapter.notifyDataSetChanged();	
				finish();
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_exercise, menu);
		return true;
	}
	
	private TextWatcher ExerciseNameChangeListener = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable s) {
			EditWorkoutActivity.ExerciseList.get(Integer.parseInt(passedVar)).setExerciseName(s.toString().toString());
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private TextWatcher SecondsChangeListener = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable s) {
			EditWorkoutActivity.ExerciseList.get(Integer.parseInt(passedVar)).setDuration(Integer.parseInt(s.toString().toString()));
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}
		
	};

}
