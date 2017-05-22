package com.example.bubblebitoey.clock;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by bubblebitoey on 5/21/2017 AD.
 */

public class ViewAdapter extends FragmentStatePagerAdapter{
	
	public ViewAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int position) {
		return null;
	}
	
	@Override
	public int getCount() {
		return 0;
	}
}
