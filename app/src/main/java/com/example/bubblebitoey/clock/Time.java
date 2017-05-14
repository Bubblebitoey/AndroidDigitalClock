package com.example.bubblebitoey.clock;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bubblebitoey on 5/14/2017 AD.
 */

public class Time {
	
	private Calendar c;
	private TextView currentTime;
	private long millisec;
	private Date date;
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	private TimeZone timeZone;
	
	public void getTime() {
		// Fragment
		currentTime = (TextView) currentTime.findViewById(R.id.current_time);
		c = Calendar.getInstance();
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
		
	}
}
