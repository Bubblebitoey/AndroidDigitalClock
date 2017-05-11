package com.example.bubblebitoey.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	
	DigitalClock clock;

	private TextView mTextMessage;

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
		clock = (DigitalClock) findViewById(R.id.digitalClockThailand);
		clock.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), clock.getText().toString(), Toast.LENGTH_SHORT).show();
			}
		});

//		mTextMessage = (TextView) findViewById(R.id.message);
//		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
