package com.example.workoutapp;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class WorkoutActivity extends Activity implements OnClickListener{

	public static List<Workout> workoutList = new ArrayList<Workout>();
	public static Workout workout;
	public static ArrayAdapter<String> adapter;
	private Button addNewWorkoutBTN;
	public static ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout);
		addNewWorkoutBTN = (Button)findViewById(R.id.wAddWorkoutBTN);
		addNewWorkoutBTN.setOnClickListener(this);
		list = (ListView)findViewById(R.id.wListView);
		SetUpOnItemClickedListener();
	}


	private void SetUpOnItemClickedListener() {
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				Intent intent = new Intent("com.example.workoutapp.WorkoutItemActivity");
				intent.putExtra("com.example.workoutapp.WorkoutActivity._viewClicked", String.valueOf(position));
				startActivity(intent);	
			}
		});
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		workout = new Workout("SampleWorkout");
		workoutList.add(workout);
		setUpAdapterView();	
	}


	private void setUpAdapterView() {

		String[] array = new String[workoutList.size()];
		for(int i = 0; i < workoutList.size(); i++)
			array[i] = workoutList.get(i).getWorkoutName();
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array);
		list.setAdapter(adapter);
		adapter.registerDataSetObserver(new DataSetObserver(){

			@Override
			public void onChanged() {
				// TODO Auto-generated method stub
				setUpAdapterView();
			}});
		
	}

	
	
}
