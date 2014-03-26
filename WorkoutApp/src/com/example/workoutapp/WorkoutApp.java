
package com.example.workoutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class WorkoutApp extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title_screen);
		//Move to workout layout.
		
		Intent intent = new Intent("com.example.workoutapp.WorkoutActivity");
		startActivity(intent);
		this.finish();
		
	}

		

	

}
