package com.example.bubblebitoey.clock;

import android.support.v4.app.Fragment;
	import android.support.v4.app.FragmentManager;
	import android.support.v4.app.FragmentStatePagerAdapter;
	
	import java.util.*;


/**
 * Created by bubblebitoey on 5/22/2017 AD.
 */

public class ViewPagerAdapter  extends FragmentStatePagerAdapter{
	
		private final List<Fragment> fragmentList;
		
		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fragmentList = new ArrayList<>();
		}
		
		public void addFragment(Fragment fragment) {
			fragmentList.add(fragment);
		}
		
		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}
		
		@Override
		public int getCount() {
			return fragmentList.size();
		}
	}

