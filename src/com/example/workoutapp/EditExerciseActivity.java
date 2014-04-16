package com.example.workoutapp;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditExerciseActivity extends Activity{
	
	private Exercise eInfo;
	private EditText eeTitleOfExerciseET, eeSecondsET;
	private Button eePlayBTN, eeRecordBTN, eeDeleteRecordingBTN, eeSaveBTN, eeDeleteBTN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_exercise);

		// eInfo is the Exercise Object passed from the EditWorkoutActivity.
		// We can now use any of the getters & setters on eInfo.
		String eTitle = getIntent().getExtras().getString(EditWorkoutActivity.TITLE_EXTRA);
		eInfo = EditWorkoutActivity.getExerciseInfo(eTitle);
		
		eePlayBTN = (Button)findViewById(R.id.eePlayBTN);
		eePlayBTN.setOnClickListener(playButtonListener);
		eeRecordBTN = (Button)findViewById(R.id.eeRecordBTN);
		eeRecordBTN.setOnClickListener(recordButtonListener);
		eeDeleteRecordingBTN = (Button)findViewById(R.id.eeDeleteRecordingBTN);
		eeDeleteRecordingBTN.setOnClickListener(deleteRecordingButtonListener);
		eeSaveBTN = (Button)findViewById(R.id.eeSaveBTN);
		eeSaveBTN.setOnClickListener(saveButtonListener);
		eeDeleteBTN = (Button)findViewById(R.id.eeDeleteBTN);
		eeDeleteBTN.setOnClickListener(deleteButtonListener);
		
		eeTitleOfExerciseET = (EditText)findViewById(R.id.eeTitleOfExerciseET);
		eeTitleOfExerciseET.setText(eInfo.getExerciseName());
		eeSecondsET = (EditText)findViewById(R.id.eeSecondsET);
		eeSecondsET.setText("" + eInfo.getDuration());
	}

	// Plays current audio.
	private OnClickListener playButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// PLAY RECORDED AUDIO FILE CODE
		}
		
	};
	
	// Records new audio.
	private OnClickListener recordButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// RECORD AUDIO FILE CODE
		}
		
	};
	
	// Deletes audio.
	private OnClickListener deleteRecordingButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// DELETE PREVIOUSLY RECORDED AUDIO FILE CODE
		}
		
	};
	
	// Saves exercise.
	private OnClickListener saveButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			eInfo.setExerciseName(eeTitleOfExerciseET.getText().toString());
			eInfo.setDuration(Integer.parseInt(eeSecondsET.getText().toString()));
			//eInfo.setRecordingPath(recordingPath);
			EditWorkoutActivity.exerciseAdapter.notifyDataSetChanged();
			finish();
		}
		
	};
	
	// Deletes exercise.
	private OnClickListener deleteButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			EditWorkoutActivity.exerciseList.remove(eInfo);
			EditWorkoutActivity.exerciseAdapter.notifyDataSetChanged();
			finish();
		}
		
	};

}
