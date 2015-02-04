package com.example.workoutapp;

import java.io.File;
import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
	private static final String DIR_NAME = Environment.getExternalStorageDirectory() + File.separator + "recordings";
	private static final String TAG = "EditExercise";
	public enum RecordState {RECORD, STOP};
	private RecordState recordBTNState;
	
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
		
		recorder = new MediaRecorder();
		player = new MediaPlayer();
		
		recordBTNState = RecordState.RECORD;
		
		File dir = new File(DIR_NAME);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
	}

	// Plays current audio.
	private OnClickListener playButtonListener = new OnClickListener() {

		/*
		 *When playing media player on emulator there will be static due to it using the computers mic
		 *When played on device no static will be present. 
		 */
		public void onClick(View v) {
			
			try {
				player.reset();
				player.setDataSource(eInfo.getRecordingPath());
				player.prepare();
				player.start();
			} catch (Exception e) {
				Log.d(TAG, "Exception: " + e);

			}
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
			
			if(recordBTNState == RecordState.RECORD){
				recordBTNState = RecordState.STOP;
				eeRecordBTN.setText("Stop");
				String recordFileName = DIR_NAME + File.separator 
						+ "recording" + System.currentTimeMillis() + ".3gpp";
				if(recorder != null){
					recorder.reset();
				}				
				try {
					Log.d(TAG, "Filename: " + recordFileName);
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					recorder.setOutputFile(recordFileName);
					recorder.prepare();
					Log.d(TAG, "Recorder is prepared");
					recorder.start();
					Log.d(TAG, "Recorder is started");
					eInfo.setRecordingPath(recordFileName);
				} catch (Exception e) {
					Log.e(TAG, "Recorder prepare exception: " + e);
					eInfo.setRecordingPath(null);
					eeRecordBTN.setText("Record");
				}
			}
			else if(recordBTNState == RecordState.STOP){
				recordBTNState = RecordState.RECORD;
				eeRecordBTN.setText("Record");
				recorder.stop();
			}
			StorageManager.saveWorkoutList(getApplicationContext());
		}

	};

	// Deletes audio.
	private OnClickListener deleteRecordingButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try{
				Log.d(TAG, "deleting file: " + eInfo.getRecordingPath());
				File deleteFile = new File(eInfo.getRecordingPath());
				deleteFile.delete();
				
			}
			catch(Exception e){
				Log.d(TAG, "Delete failed");
			}
			try{
				eInfo.setRecordingPath(null);
			}
			catch(Exception e){
				Log.d(TAG, "Failed to set recording path to null");
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

	@Override
	protected void onStop() {

		super.onStop();
		if(player != null){
			player.release();
		}
		if(recorder != null){
			recorder.release();
		}
	}

}
