package com.example.workoutapp;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WorkoutActivity extends ListActivity {

	public static final String NAME_EXTRA = "name_key";
	public static final int EDIT_ID = 0;
	protected static final String adapter = null;
	public static List<Workout> workoutList = new ArrayList<Workout>();
	private ListView workoutListView;
	public static WorkoutAdapter workoutAdapter;
	private Button wAddWorkoutBTN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout);
		workoutListView = getListView();
		workoutList = new ArrayList<Workout>();
		workoutAdapter = new WorkoutAdapter(this, workoutList);
		workoutListView.setAdapter(workoutAdapter);
		wAddWorkoutBTN = (Button)findViewById(R.id.wAddWorkoutBTN);
		wAddWorkoutBTN.setOnClickListener(addButtonListener);
	}
	
	private OnClickListener addButtonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(WorkoutActivity.this, EditWorkoutActivity.class);
			Workout workout = new Workout("");
			workoutAdapter.add(workout);
			String name = workout.getWorkoutName();
			i.putExtra(NAME_EXTRA, name);
			startActivity(i);
		}
	};
	
	private OnClickListener startButtonListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent i = new Intent(WorkoutActivity.this, PlayWorkoutActivity.class);
				Workout workout = new Workout("Workout");
				workoutList.add(workout);
				//startActivity(i);
			}
	};
		
	private OnClickListener editButtonListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WorkoutActivity.this, EditWorkoutActivity.class);
				Workout workout = (Workout) v.getTag();
				String name = workout.getWorkoutName();
				i.putExtra(NAME_EXTRA, name);
				startActivityForResult(i, EDIT_ID);
			}
	};
	
	private static class ViewHolder {
		TextView wivTitleOfWorkoutTV;
		Button wivEditBTN, wivStartBTN;
	}
	
	class WorkoutAdapter extends ArrayAdapter<Workout> {
		
		private List<Workout> workouts;
		private LayoutInflater inflator;
		
		public WorkoutAdapter(Context ctx, List<Workout> workouts) {
			super(ctx, -1, workouts);
			this.workouts = workouts;
			inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if (convertView == null){
				convertView = inflator.inflate(R.layout.workout_itemview, null);
				vh = new ViewHolder();
				vh.wivTitleOfWorkoutTV = (TextView) convertView.findViewById(R.id.wivTitleOfWorkoutTV);
				vh.wivEditBTN = (Button) convertView.findViewById(R.id.wivEditBTN);
				vh.wivStartBTN = (Button) convertView.findViewById(R.id.wivStartBTN);
				convertView.setTag(vh);
			}
			else {
				vh = (ViewHolder) convertView.getTag();
			}
			Workout workout = workouts.get(position);
			String title = workout.getWorkoutName();
			vh.wivTitleOfWorkoutTV.setText(title);
			vh.wivEditBTN.setTag(workout);
			vh.wivStartBTN.setTag(workout);

			vh.wivEditBTN.setOnClickListener(editButtonListener);
			vh.wivStartBTN.setOnClickListener(startButtonListener);
			return convertView;
		}
		
	}

public static Workout getWorkoutInfo(String wTitle) {
	for(Workout w : workoutList) {
		if (wTitle.equals(w.getWorkoutName())) {
			return w;
		}
	}
	return null;
}
}
