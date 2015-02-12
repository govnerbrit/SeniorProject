package com.example.workoutapp;

public class Exercise {
	
	private String exerciseName;
	private String recordingPath;
	private int duration;
	
	public Exercise(String exerciseName) {
		this.exerciseName = exerciseName;
		recordingPath = null;
		duration = 0;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public String getRecordingPath() {
		return recordingPath;
	}

	public void setRecordingPath(String recordingPath) {
		if (this.recordingPath != null) {
			deleteRecording(this.recordingPath);
			this.recordingPath = recordingPath;
		}
		else
			this.recordingPath = recordingPath;
	}

	private void deleteRecording(String recordingPath) {
		// Figure out a way to delete audio file from memory.
		this.recordingPath = null;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	

}
