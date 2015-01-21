package com.example.workoutapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title_screen);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(3000);
				} catch (InterruptedException e){
					e.printStackTrace();
				} finally{
					Intent i = new Intent(Splash.this, WorkoutActivity.class);
					startActivity(i);
				}
			}
		};
		timer.start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
}