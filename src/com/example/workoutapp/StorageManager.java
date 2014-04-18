package com.example.workoutapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import android.content.Context;

import java.lang.reflect.Type;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StorageManager {
	
	private static final String TAG = "StorageManager";
	private static final String FILENAME = "workouts";

	
	public static void saveWorkoutList(Context context){
		Gson gson = new Gson();
		String json = gson.toJson(WorkoutActivity.workoutList);
		Log.d(TAG, "json: " + json);
		writeWorkoutsToFile(context);
		
	}
	
	public static void loadWorkoutList(Context context){
		Log.d(TAG, "enter loadWorkoutList");
		File readFile = new File(context.getFilesDir(), FILENAME);
		FileReader fr = null;
		BufferedReader br = null;
		
		try{
			fr = new FileReader(readFile);
			br = new BufferedReader(fr);
			
			String jsonRead = "";
			for(String nextLine = br.readLine(); nextLine != null; nextLine = br.readLine()){
				jsonRead += nextLine;
			}
			Log.d(TAG, "finished reading");
			Type workoutListType = new TypeToken<ArrayList<Workout>>() {}.getType();
			Gson gson = new Gson();
			WorkoutActivity.workoutList = gson.fromJson(jsonRead, workoutListType);
			
		}catch(Exception e){
			WorkoutActivity.workoutList = new ArrayList<Workout>();
			Log.d(TAG, "loadWorkoutList exception: " + e);
		}
		
	}
	
	public static void writeWorkoutsToFile(Context context){
		Log.d(TAG, "writeStringToFile: enter");
		FileWriter fw = null;
		BufferedWriter bw = null;
		File writeFile = new File(context.getFilesDir(), FILENAME);

		try {
			Log.d(TAG, "writeStringToFile: writing ");
			writeFile.createNewFile();

			// Open the file for writing. Create a stream for writing to it:
			fw = new FileWriter(writeFile);
			bw = new BufferedWriter(fw);

			// Write the data, flush it (so that we don't sit around waiting
			// for it to be written), and close it:
			Type workoutListType = new TypeToken<ArrayList<Workout>>() {}.getType();
			Gson gson = new Gson();
			String jsonOut = gson.toJson(WorkoutActivity.workoutList, workoutListType);
			bw.write(jsonOut);
			bw.flush();

			// We might want to do something like write a Toast, but for now just
			// write an exception:
		} catch(Exception e){
			Log.e(TAG, "writeStringToFile: Unable to save workouts: " + e);
		}

		// Close the streams if they were opened:
		if (null != bw) {
			try {
				bw.close();
			} catch (Exception e) {
				Log.d(TAG, "writeStringToFile: error closing bos: " + e);
			}
		}

		if (null != fw) {
			try {
				fw.close();
			} catch (Exception e) {
				Log.d(TAG, "writeStringToFile: error closing fos: " + e);
			}
		}
		
		Log.d(TAG, "leaving: writeWorkoutsToFile");

	}
}
