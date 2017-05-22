package com.example.bubblebitoey.clock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by bubblebitoey on 5/22/2017 AD.
 */

public class ShowTimeFragment extends Fragment {
	
	private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
	private static ScheduledFuture future = null;
	
	private static final SimpleDateFormat DATE_LONG_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm, zzzz", Locale.ENGLISH);
	private static final SimpleDateFormat DATE_SHORT_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.ENGLISH);
	
	private ClockPresenter presenter;
	
	private Calendar c;
	private TimeZone timeZone;
	
	private EditText iptSearch;
	private TextView mTime, mTimeZone, txtTimeZone;
	private ListView listTime;
	
	private ArrayAdapter availableId;
	
	private long millisec;
	private String[] ids;
	
	private Date date;
	private Menu menu;
	
	public ShowTimeFragment() {
		super();
	}
	
	public ShowTimeFragment setPresenter(ClockPresenter presenter) {
		this.presenter = presenter;
		return this;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_clock, container, false);
		initialize(v);
		initList();
		getTime();
		viewTime();
		
		return v;
	}
	
	public void initialize(View view) {
		listTime = (ListView) view.findViewById(R.id.listView);
		mTime = (TextView) view.findViewById(R.id.current_time);
		mTimeZone = (TextView) view.findViewById(R.id.curr_time_zone);
		txtTimeZone = (TextView) view.findViewById(R.id.time_zone);
		iptSearch = (EditText) view.findViewById(R.id.inputSearch);
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
						
						presenter.runApp(new Runnable() {
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
		availableId = new ArrayAdapter<>(presenter.getContext(), android.R.layout.simple_dropdown_item_1line, ids);
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
						presenter.runApp(new Runnable() {
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
				ShowTimeFragment.this.availableId.getFilter().filter(s);
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
}
