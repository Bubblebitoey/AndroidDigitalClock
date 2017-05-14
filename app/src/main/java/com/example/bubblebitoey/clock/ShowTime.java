package com.example.bubblebitoey.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bubblebitoey on 5/15/2017 AD.
 */

public class ShowTime extends AppCompatActivity {
	
	Time time = new Time();
	private Calendar c = Calendar.getInstance();;
	private TextView mTextMessage;
	private TextView currentTime, currTimeZone, txtTimeZone;
	private ListView listTime;
	private Spinner spinner;
	private FloatingActionButton fab;
	private ArrayAdapter<String> availableId;
	private long millisec;
	private Date date;
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm, zzzz", Locale.ENGLISH);
	
	
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
			
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.navigation_alarm:
						mTextMessage.setText(R.string.menu_alarm);
						return true;
					case R.id.navigation_clock:
						mTextMessage.setText(R.string.menu_clock);
						return true;
					case R.id.navigation_timer:
						mTextMessage.setText(R.string.menu_timer);
						return true;
				}
				return false;
			}
		};
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			
			
			listTime = (ListView) findViewById(R.id.listView);
			currentTime = (TextView) findViewById(R.id.current_time);
			currTimeZone = (TextView) findViewById(R.id.curr_time_zone);
			txtTimeZone = (TextView) findViewById(R.id.time_zone);
			spinner = (Spinner) findViewById(R.id.spinner);
			fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
			fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					/**
					 * List part
					 */
					listTime.setVisibility(View.VISIBLE);
					listTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							getTime();
							String selectedID = (String) (parent.getItemAtPosition(position));
							
							TimeZone timeZone = TimeZone.getTimeZone(selectedID);
							String TimeZoneName = timeZone.getDisplayName();
							
							int timeZoneOffset = timeZone.getRawOffset() / (60 * 1000);
							int hours = timeZoneOffset / 60;
							int minutes = timeZoneOffset % 60;
							millisec = millisec + timeZone.getRawOffset();
							
							date = new Date(millisec);
							System.out.println(DATE_FORMAT.format(date));
							
							txtTimeZone.setText(TimeZoneName + " :GMT" + hours + ":" + minutes);
							currTimeZone.setText(DATE_FORMAT.format(date));
							millisec = 0;
						}
					});
					
					/**
					 * Spinner part
					 */
//					spinner.setVisibility(View.VISIBLE);
//					spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//						@Override
//						public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//							//getGMT();
//							getTime();
//							String selectedID = (String) (parent.getItemAtPosition(position));
//
//							TimeZone timeZone = TimeZone.getTimeZone(selectedID);
//							String TimeZoneName = timeZone.getDisplayName();
//
//							int timeZoneOffset = timeZone.getRawOffset() / (60 * 1000);
//							int hours = timeZoneOffset / 60;
//							int minutes = timeZoneOffset % 60;
//							millisec = millisec + timeZone.getRawOffset();
//
//							date = new Date(millisec);
//							System.out.println(DATE_FORMAT.format(date));
//
//							txtTimeZone.setText(TimeZoneName + " :GMT" + hours + ":" + minutes);
//							currTimeZone.setText(DATE_FORMAT.format(date));
//							millisec = 0;
//						}
//
//						@Override
//						public void onNothingSelected(AdapterView<?> parent) {
//
//						}
//					});
				}
			});
			
			
			/**
			 * List part
			 */
			String[] ids = TimeZone.getAvailableIDs();
			availableId = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, ids);
			availableId.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
			listTime.setAdapter(availableId);
			listTime.setVisibility(View.INVISIBLE);

			getTime();
			
			
			/**
			 * Spinner part
			 */
//			String[] ids = TimeZone.getAvailableIDs();
//			availableId = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
//			availableId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			spinner.setAdapter(availableId);
//			spinner.setVisibility(View.INVISIBLE);
//
//			getTime();
			
			
			//		mTextMessage = (TextView) findViewById(R.id.message);
			//		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
			//		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		}
		
		public void getTime() {
			currentTime = (TextView) currentTime.findViewById(R.id.current_time);
			
			currentTime.setText(String.valueOf(c.getTime()));
			currentTime.setText(DATE_FORMAT.format(c.getTime()));
			millisec = c.getTimeInMillis();
			TimeZone curr = c.getTimeZone();
			int offset = curr.getRawOffset();
			if (curr.inDaylightTime(new Date())) {
				offset += curr.getDSTSavings();
			}
			millisec -= offset;
			date = new Date(millisec);
			System.out.println(DATE_FORMAT.format(date));
			//new SimpleDateFormat("EEE, d MMM yyyy HH:mm, zzzz", Locale.ENGLISH);
		}
}
