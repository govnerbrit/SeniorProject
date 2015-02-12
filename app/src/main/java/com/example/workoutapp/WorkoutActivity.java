package com.example.workoutapp;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.os.Parcelable;
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

	public static final String TITLE_EXTRA = "title_key";
	
	public static List<Workout> workoutList = new ArrayList<Workout>();
	private ListView workoutListView;
	public static WorkoutAdapter workoutAdapter;
	private Button wAddWorkoutBTN;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout);
		workoutListView = getListView();
		StorageManager.loadWorkoutList(getApplicationContext());
		workoutAdapter = new WorkoutAdapter(this, workoutList);
		workoutListView.setAdapter(workoutAdapter);
		wAddWorkoutBTN = (Button)findViewById(R.id.wAddWorkoutBTN);
		wAddWorkoutBTN.setOnClickListener(addButtonListener);
	}
	
	// Adds new workout.
	private OnClickListener addButtonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(WorkoutActivity.this, EditWorkoutActivity.class);
			Workout workout = new Workout("");
			workoutAdapter.add(workout);
			String title = workout.getWorkoutName();
			i.putExtra(TITLE_EXTRA, title);
			startActivity(i);
		}
	};
	
	// Starts workout.
	private OnClickListener startButtonListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WorkoutActivity.this, PlayWorkoutActivity.class);
				Workout workout = (Workout) v.getTag();
				String title = workout.getWorkoutName();
				i.putExtra(TITLE_EXTRA, title);
				startActivity(i);
			}
	};
	
	// Edits workout.
	private OnClickListener editButtonListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WorkoutActivity.this, EditWorkoutActivity.class);
				Workout workout = (Workout) v.getTag();
				String title = workout.getWorkoutName();
				i.putExtra(TITLE_EXTRA, title);
				startActivity(i);
			}
	};

    //Copies workout
    private OnClickListener copyButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Workout currentWorkout = (Workout) v.getTag();
            List<Exercise> copiedList = new ArrayList<Exercise>();
            List<Exercise> currentList = currentWorkout.getExerciseList();
            for(Exercise e: currentList){
                copiedList.add(e);
            }
            Workout newWorkout = new Workout(currentWorkout.getWorkoutName(), copiedList);
            workoutList.add(newWorkout);
            workoutAdapter.notifyDataSetChanged();
        }
    };
	
	private static class ViewHolder {
		TextView wivTitleOfWorkoutTV;
		Button wivEditBTN, wivStartBTN, wivCopyBTN;
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
                vh.wivCopyBTN = (Button) convertView.findViewById(R.id.wivCopyBTN);
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
            vh.wivCopyBTN.setTag(workout);
			vh.wivEditBTN.setOnClickListener(editButtonListener);
			vh.wivStartBTN.setOnClickListener(startButtonListener);
            vh.wivCopyBTN.setOnClickListener(copyButtonListener);
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
