package com.example.bubblebitoey.clock;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bubblebitoey on 5/22/2017 AD.
 */

public class StopWatchFragment extends Fragment {
	
	private TextView timeView;
	private Button btnStart;
	private Button btnLap;
	private ImageView imgStart;
	private ImageView imgLap;
	
	private Handler mHandler;
	private boolean mStarted;
	
	private int btnState;
	private long start_time;
	private int lapsCount;
	private String laps;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v =  inflater.inflate(R.layout.fragment_stopwatch, container, false);
		
		timeView = (TextView) v.findViewById(R.id.mTime);
		imgStart = (ImageView) v.findViewById(R.id.img_start);
		imgLap = (ImageView) v.findViewById(R.id.img_lap);
		
		mHandler = new Handler();
		
		imgStart.setEnabled(true);
		btnState = 1;
		
		laps = "";
		lapsCount = 0;
		
		imgStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(btnState == 1) {
					mStarted = true;
					mHandler.postDelayed(mRunnable, 0);
					start_time = System.currentTimeMillis();
					laps ="";
					
					imgStart.setImageResource(R.drawable.pause_icon);
					btnState = 2;
				} else if(btnState == 2) {
					mStarted = false;
					mHandler.removeCallbacks(mRunnable);
					
					imgStart.setImageResource(R.drawable.replay_ic);
					btnState = 3;
				} else if(btnState == 3) {
					timeView.setText(String.format("%02d:%02d:%02d", 0, 0, 0));
					laps = "";
					btnState = 1;
				}
			}
		});
		
		imgLap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mStarted) {
					lapsCount++;
					laps = laps + String.valueOf(lapsCount) + ". " + timeView.getText().toString() + "\n";
					Toast.makeText(StopWatchFragment.super.getContext(), "Lap", Toast.LENGTH_SHORT).show();
					System.out.println("in side if mStarted"+ laps);
				} else {
					if(laps.equalsIgnoreCase("")) {
						Toast.makeText(StopWatchFragment.super.getContext(), "Empty", Toast.LENGTH_SHORT).show();
						System.out.println("in side if mStarted"+ laps);
					} else {
						AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(StopWatchFragment.super.getContext());
						alertDialogBuiler.setMessage(laps);
						alertDialogBuiler.setCancelable(true);
						alertDialogBuiler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						AlertDialog alertDialog = alertDialogBuiler.create();
						alertDialog.show();
					}
				}
			}
		});
		
		return v;
	}
	private final Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			if(mStarted) {
				long milliseconds = (System.currentTimeMillis() - start_time);
				long seconds = milliseconds / 100;
				timeView.setText(String.format("%02d:%02d:%02d", seconds / 60, seconds % 60, milliseconds % 100));
				mHandler.postDelayed(mRunnable, 10L);
			}
			
		}
	};
}
