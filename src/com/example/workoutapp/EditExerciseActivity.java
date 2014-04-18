package com.example.workoutapp;

import java.io.File;
import java.io.IOException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditExerciseActivity extends Activity{
	
	private Exercise eInfo;
	private EditText eeTitleOfExerciseET, eeSecondsET;
	private Button eePlayBTN, eeRecordBTN, eeDeleteRecordingBTN, eeSaveBTN, eeDeleteBTN;
	private MediaPlayer player;
	private MediaRecorder recorder;
	private String FILE;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_exercise);

		// eInfo is the Exercise Object passed from the EditWorkoutActivity.
		// We can now use any of the getters & setters on eInfo.
		String eTitle = getIntent().getExtras().getString(EditWorkoutActivity.TITLE_EXTRA);
		eInfo = EditWorkoutActivity.getExerciseInfo(eTitle);
		FILE = Environment.getExternalStorageDirectory() + "/" + eInfo.getExerciseName() + ".3gpp";
		
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

		/*
		 *When playing media player on emulator there will be static due to it using the computers mic
		 *When played on device no static will be present. 
		 */
		public void onClick(View v) {
			player = new MediaPlayer();
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

	};
	
	// Records new audio.
	private OnClickListener recordButtonListener = new OnClickListener() {
		/*
		 * When record button is pressed text will change so that same button can be used to end recording. 
		 * Not necessary on play due to OnCompletionListener() method. 
		 */
		@Override
		public void onClick(View v) {
			if(eeRecordBTN.getText().toString().equals("Record")){
				if(recorder != null){
					recorder.release();
				}
				File outFile = new File(FILE);
				if(outFile != null){
					outFile.delete();
				}
				
				recorder = new MediaRecorder();
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				recorder.setOutputFile(FILE);
				try {
					recorder.prepare();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				recorder.start();
				eeRecordBTN.setText("Stop");
			}
			else if(eeRecordBTN.getText().toString().equals("Stop")){
				recorder.stop();
				recorder.release();
				eeRecordBTN.setText("Record");
			}
			StorageManager.saveWorkoutList(getApplicationContext());
		}

	};

	// Deletes audio.
	private OnClickListener deleteRecordingButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			File outFile = new File(FILE);
			if(outFile != null){
				outFile.delete();
			}	
			StorageManager.saveWorkoutList(getApplicationContext());
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
			StorageManager.saveWorkoutList(getApplicationContext());
			finish();
		}
		
	};
	
	// Deletes exercise.
	private OnClickListener deleteButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			EditWorkoutActivity.exerciseList.remove(eInfo);
			EditWorkoutActivity.exerciseAdapter.notifyDataSetChanged();
			StorageManager.saveWorkoutList(getApplicationContext());
			finish();
		}
		
	};

}
