package com.example.bubblebitoey.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bubblebitoey on 5/15/2017 AD.
 */

public class ShowTime extends AppCompatActivity{
	
	private Calendar c;
	
	private TextView mTextMessage;
	private TextView mTime, mTimeZone, txtTimeZone;
	private ListView listTime;
	
	private FloatingActionButton fab;
	
	private ArrayAdapter<String> availableId;
	
	private long millisec;
	private String[] ids;
	
	private Date date;
	private SimpleDateFormat DATE_FORMAT;
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
			
			Thread t = new Thread() {
			
			  @Override
			  public void run() {
			    try {
			      while (!isInterrupted()) {
			        Thread.sleep(1000);
			        runOnUiThread(new Runnable() {
			          @Override
			          public void run() {
			            // update TextView here!
				          viewTime();
			          }
			        });
			      }
			    } catch (InterruptedException e) {
			    }
			  }
			};
			
			t.start();
			
			initList();
			getTime();
		}
		
		public void initialize() {
			listTime = (ListView) findViewById(R.id.listView);
			mTime = (TextView) findViewById(R.id.current_time);
			mTimeZone = (TextView) findViewById(R.id.curr_time_zone);
			txtTimeZone = (TextView) findViewById(R.id.time_zone);
			fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
		}
		
		public void viewTime() {
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
							//getTime();
							String selectedID = (String) (parent.getItemAtPosition(position));
							
							TimeZone timeZone = TimeZone.getTimeZone(selectedID);
							String TimeZoneName = timeZone.getDisplayName();
							
							int timeZoneOffset = timeZone.getRawOffset() / (60 * 1000);
							int hours = timeZoneOffset / 60;
							int minutes = timeZoneOffset % 60;
							millisec = millisec + timeZone.getRawOffset();
							
							date = new Date(millisec);
							
							txtTimeZone.setText(TimeZoneName + ", " + timeZone.getID() + " : GMT" + hours + ":" + minutes);
							
							
							//							Thread t = new Thread() {
							//
							//							  @Override
							//							  public void run() {
							//							    try {
							//							      while (!isInterrupted()) {
							//							        Thread.sleep(1000);
							//							        runOnUiThread(new Runnable() {
							//							          @Override
							//							          public void run() {
							DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm ");
							mTimeZone.setText(DATE_FORMAT.format(date));
							System.out.println("Date format: " + DATE_FORMAT.format(date));
							//							          }
							//							        });
							//							      }
							//							    } catch (InterruptedException e) {
							//							    }
							//							  }
							//							};
							//
							//							t.start();
							
							millisec = 0;
							//Add this after select item list will disappear
							listTime.setVisibility(View.INVISIBLE);
						}
					});
				}
			});
		}
	
	
	public void initList() {
			ids = TimeZone.getAvailableIDs();
			availableId = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, ids);
			availableId.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
			listTime.setAdapter(availableId);
			listTime.setVisibility(View.INVISIBLE);
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
			          DATE_FORMAT  = new SimpleDateFormat("EEE, d MMM yyyy HH:mm, zzzz");
			          mTime.setText(String.valueOf(c.getInstance().getTime()));
			          millisec = c.getTimeInMillis();
			          TimeZone curr = c.getTimeZone();
			          int offset = curr.getRawOffset();
			          if (curr.inDaylightTime(new Date())) {
				          offset += curr.getDSTSavings();
			          }
			          millisec -= offset;
			          date = new Date(millisec);
			          mTime.setText(DATE_FORMAT.format(c.getTime()) +" , " + curr.getID());
		          }
		        });
		      }
		    } catch (InterruptedException e) {
		    }
		  }
		  
		};
		
		t.start();
		}
}

