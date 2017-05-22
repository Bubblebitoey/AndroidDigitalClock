package com.example.bubblebitoey.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
	
	private ViewPager viewPager;
	
	private BottomNavigationView navigation;
	
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
		
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_clock:
					viewPager.setCurrentItem(0);
					item.setChecked(true);
					return true;
				case R.id.navigation_timer:
					viewPager.setCurrentItem(1);
					item.setChecked(true);
					return true;
			}
			return false;
		}
	};
	
	private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}
		
		@Override
		public void onPageSelected(int position) {
			for (int i = 0; i < navigation.getMenu().size(); i++)
				navigation.getMenu().getItem(i).setChecked(false);
			navigation.getMenu().getItem(position).setChecked(true);
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
		}
	};
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.addOnPageChangeListener(mOnPageChangeListener);
		
		navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		
		ShowTimeFragment showTimeFragment = new ShowTimeFragment().setPresenter(new ClockPresenter(this));
		StopWatchFragment stopWatchFragment = new StopWatchFragment();
		
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(showTimeFragment);
		adapter.addFragment(stopWatchFragment);
		viewPager.setAdapter(adapter);
	}
}
