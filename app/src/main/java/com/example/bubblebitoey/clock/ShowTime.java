package com.example.bubblebitoey.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by bubblebitoey on 5/15/2017 AD.
 */

public class ShowTime extends AppCompatActivity {
	private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
	private static ScheduledFuture future = null;
	
	private static final SimpleDateFormat DATE_LONG_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm, zzzz", Locale.ENGLISH);
	private static final SimpleDateFormat DATE_SHORT_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.ENGLISH);
	
	
	private Calendar c;
	private TimeZone timeZone;
	
	private TextView mTextMessage;
	private EditText iptSearch;
	private TextView mTime, mTimeZone, txtTimeZone;
	private ListView listTime;
	
	private ArrayAdapter<String> availableId;
	
	private long millisec;
	private String[] ids;
	
	private Date date;
	private Menu menu;
	
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
		
		initialize();
		initList();
		getTime();
		viewTime();
		
		//			Thread t = new Thread() {
		//
		//			  @Override
		//			  public void run() {
		//			    try {
		//			      while (!isInterrupted()) {
		//			        Thread.sleep(1000);
		//			        runOnUiThread(new Runnable() {
		//			          @Override
		//			          public void run() {
		//			            // update TextView here!
		//				         viewTime();
		//			          }
		//			        });
		//			      }
		//			    } catch (InterruptedException e) {
		//			    }
		//			  }
		//			};
		//
		//			t.start();
	}
	
	public void initialize() {
		listTime = (ListView) findViewById(R.id.listView);
		mTime = (TextView) findViewById(R.id.current_time);
		mTimeZone = (TextView) findViewById(R.id.curr_time_zone);
		txtTimeZone = (TextView) findViewById(R.id.time_zone);
		iptSearch = (EditText) findViewById(R.id.inputSearch);
	}
	
	public void viewTime() {
		
		search();
		
		listTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String selectedID = (String) (parent.getItemAtPosition(position));
				timeZone = TimeZone.getTimeZone(selectedID);
				// if (!service.isShutdown()) service.shutdown();
				
				if (future != null) {
					future.cancel(true);
					future = null;
				}
				
				future = service.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						final String TimeZoneName = timeZone.getDisplayName();
						
						int timeZoneOffset = timeZone.getRawOffset() / (60 * 1000);
						int hours = timeZoneOffset / 60;
						int minutes = timeZoneOffset % 60;
						millisec = millisec + timeZone.getRawOffset();
						date = new Date(millisec);
						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mTimeZone.setText(DATE_SHORT_FORMAT.format(date));
								txtTimeZone.setText(TimeZoneName + ", " + timeZone.getID());
							}
						});
						
						Log.d("DATE", "Date format out: " + DATE_SHORT_FORMAT.format(date));
					}
				}, 0, 1, TimeUnit.SECONDS);
			}
		});
	}
	
	
	public void initList() {
		ids = TimeZone.getAvailableIDs();
		availableId = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, ids);
		availableId.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
		listTime.setAdapter(availableId);
	}
	
	public void getTime() {
		Thread t = new Thread() {
			
			@Override
			public void run() {
				try {
					while (!isInterrupted()) {
						Thread.sleep(1000);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								c = Calendar.getInstance();
								mTime = (TextView) mTime.findViewById(R.id.current_time);
								millisec = c.getTimeInMillis();
								TimeZone curr = c.getTimeZone();
								int offset = curr.getRawOffset();
								if (curr.inDaylightTime(new Date())) {
									offset += curr.getDSTSavings();
								}
								millisec -= offset;
								date = new Date(millisec);
								mTime.setText(DATE_LONG_FORMAT.format(c.getTime()) + " , " + curr.getID());
							}
						});
					}
				} catch (InterruptedException ignored) {
				}
			}
			
		};
		t.start();
	}
	
	public void search() {
		iptSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				ShowTime.this.availableId.getFilter().filter(s);
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
}

