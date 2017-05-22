package com.example.bubblebitoey.clock;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by bubblebitoey on 5/22/2017 AD.
 */

public class ClockPresenter {
	
	private AppCompatActivity appCompatActivity;
	
	ClockPresenter(AppCompatActivity appCompatActivity) {
		this.appCompatActivity = appCompatActivity;
	}
	
	public void runApp(Runnable runnable) {
		appCompatActivity.runOnUiThread(runnable);
	}
	
	public Context getContext() {
		return appCompatActivity;
	}
}
