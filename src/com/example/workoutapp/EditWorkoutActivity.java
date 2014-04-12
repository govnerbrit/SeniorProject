package com.example.workoutapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EditWorkoutActivity extends Activity{

	public static ArrayAdapter<String> ExerciseAdapter;
	public static List<Exercise> ExerciseList = new ArrayList<Exercise>();
	public static Exercise exercise;
	public static ListView exList;
	Button SaveBTN, DeleteBTN, AddNewExerciseBTN;
	EditText titleOfWorkoutET;
	private String passedVar;
	Bundle extras;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_workout);
		extras = getIntent().getExtras();
		SaveBTN = (Button)findViewById(R.id.ewSaveBTN);
		DeleteBTN = (Button)findViewById(R.id.ewDeleteBTN);
		AddNewExerciseBTN = (Button)findViewById(R.id.ewAddNewExerciseBTN);
		passedVar = getIntent().getStringExtra("com.example.workoutapp.WorkoutItemActivity._viewClicked");
		titleOfWorkoutET = (EditText)findViewById(R.id.ewTitleOfWorkoutET);
		titleOfWorkoutET.addTextChangedListener(WorkoutNameChangeListener);
		SetUpOnClickListener();
		exList = (ListView)findViewById(R.id.ewListView);
		SetUpOnItemClickedListener();
	}


	private void SetUpOnItemClickedListener() {
		exList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				Intent intent = new Intent("com.example.workoutapp.EditWorkoutItemActivity");
				intent.putExtra("com.example.workoutapp.EditWorkoutActivity._viewClicked", String.valueOf(position));
				startActivity(intent);	
			}});
	}
	private void SetUpOnClickListener() {
		SaveBTN.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				WorkoutActivity.workoutAdapter.notifyDataSetChanged();
				finish();
			}});
		DeleteBTN.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				WorkoutActivity.workoutList.remove(Integer.parseInt(passedVar));
				WorkoutActivity.workoutAdapter.notifyDataSetChanged();	
				finish();
			}});
		AddNewExerciseBTN.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				exercise = new Exercise("SampleExercise");
				ExerciseList.add(exercise);
				setUpAdapterView();	
			}});
	}
	private void setUpAdapterView() {

		String[] array = new String[ExerciseList.size()];
		for(int i = 0; i < ExerciseList.size(); i++)
			array[i] = ExerciseList.get(i).getExerciseName();
		ExerciseAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array);
		exList.setAdapter(ExerciseAdapter);
		ExerciseAdapter.registerDataSetObserver(new DataSetObserver(){
			@Override
			public void onChanged() {
				// TODO Auto-generated method stub
				setUpAdapterView();
			}});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_workout, menu);
		return true;
	}


	private TextWatcher WorkoutNameChangeListener = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable s) {
			
			//WorkoutActivity.workoutList.get(Integer.parseInt(passedVar)).setWorkoutName(s.toString().toString());
			
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
