package com.example.workoutapp;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditWorkoutActivity extends ListActivity{

	public static final String TITLE_EXTRA = "title_key";
	
	private Workout wInfo;
	public static List<Exercise> exerciseList = new ArrayList<Exercise>();
	public static ExerciseAdapter exerciseAdapter;
	private EditText ewTitleOfWorkoutET;
	private Button ewSaveBTN, ewDeleteBTN, ewAddNewExerciseBTN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_workout);

		// wInfo is the Workout Object passed from the WorkoutActivity.
		// We can now use any of the getters & setters on wInfo.
		String wTitle = getIntent().getExtras().getString(WorkoutActivity.TITLE_EXTRA);
		wInfo = WorkoutActivity.getWorkoutInfo(wTitle);
		exerciseList = wInfo.getExerciseList();
		exerciseAdapter = new ExerciseAdapter(this, exerciseList);
		getListView().setAdapter(exerciseAdapter);
		
		ewAddNewExerciseBTN = (Button)findViewById(R.id.ewAddNewExerciseBTN);
		ewAddNewExerciseBTN.setOnClickListener(addButtonListener);
		ewSaveBTN = (Button)findViewById(R.id.ewSaveBTN);
		ewSaveBTN.setOnClickListener(saveButtonListener);
		ewDeleteBTN = (Button)findViewById(R.id.ewDeleteBTN);
		ewDeleteBTN.setOnClickListener(deleteButtonListener);
		ewTitleOfWorkoutET = (EditText)findViewById(R.id.ewTitleOfWorkoutET);
		ewTitleOfWorkoutET.setText(wInfo.getWorkoutName());
	
	}
	
	
	// Saves workout.
	private OnClickListener saveButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			wInfo.setWorkoutName(ewTitleOfWorkoutET.getText().toString());
			WorkoutActivity.workoutAdapter.notifyDataSetChanged();
			StorageManager.saveWorkoutList(getApplicationContext());
			finish();
		}
		
	};
	
	// Deletes workout.
	private OnClickListener deleteButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			WorkoutActivity.workoutList.remove(wInfo);
			WorkoutActivity.workoutAdapter.notifyDataSetChanged();
			finish();
		}
		
	};
	
	// Adds new exercise.
	private OnClickListener addButtonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(EditWorkoutActivity.this, EditExerciseActivity.class);
			Exercise exercise = new Exercise("");
			exerciseAdapter.add(exercise);
			String title = exercise.getExerciseName();
			i.putExtra(TITLE_EXTRA, title);
			startActivity(i);
		}
	};
	
	// Edits exercise.
	private OnClickListener editButtonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(EditWorkoutActivity.this, EditExerciseActivity.class);
			Exercise exercise = (Exercise) v.getTag();
			String title = exercise.getExerciseName();
			i.putExtra(TITLE_EXTRA, title);
			startActivity(i);
		}
	};
	
	private static class ViewHolder {
		TextView ewivTitleOfExerciseTV;
		Button ewivEditBTN;
	}
	
	class ExerciseAdapter extends ArrayAdapter<Exercise> {
			
		private List<Exercise> exercises;
		private LayoutInflater inflator;
		
		public ExerciseAdapter(Context ctx, List<Exercise> exercises) {
			super(ctx, -1, exercises);
			this.exercises = exercises;
			inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if (convertView == null){
				convertView = inflator.inflate(R.layout.edit_workout_itemview, null);
				vh = new ViewHolder();
				vh.ewivTitleOfExerciseTV = (TextView) convertView.findViewById(R.id.ewivTitleOfExerciseTV);
				vh.ewivEditBTN = (Button) convertView.findViewById(R.id.ewivEditBTN);
				convertView.setTag(vh);
			}
			else {
				vh = (ViewHolder) convertView.getTag();
			}
			Exercise exercise = exercises.get(position);
			String title = exercise.getExerciseName();
			vh.ewivTitleOfExerciseTV.setText(title);
			vh.ewivEditBTN.setTag(exercise);

			vh.ewivEditBTN.setOnClickListener(editButtonListener);
			return convertView;
		}
			
	}
	
	public static Exercise getExerciseInfo(String eTitle) {
		for(Exercise e : exerciseList) {
			if (eTitle.equals(e.getExerciseName())) {
				return e;
			}
		}
		return null;
	}
	
}
