package com.example.workoutapp;

import java.util.ArrayList;
import java.util.List;


public class Workout {

	private String workoutName;
	private List<Exercise> exerciseList;
	
	public Workout(String workoutName) {
        this.workoutName = workoutName;
        exerciseList = new ArrayList<Exercise>();
    }
    public Workout(String workoutName, List exerciseList) {
        this.workoutName = workoutName;
        this.exerciseList = exerciseList;

    }

	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	public List<Exercise> getExerciseList() {
		return exerciseList;
	}
	
	public void addExercise(Exercise e) {
		exerciseList.add(e);
	}
	
	public void deleteExercise(int index) {
		exerciseList.remove(index);
	}
	
}
