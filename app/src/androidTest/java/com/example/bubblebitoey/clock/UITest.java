package com.example.bubblebitoey.clock;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UITest {
	
	@Rule
	public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
	
	@Test
	public void useAppContext() throws Exception {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getTargetContext();
		assertEquals("com.example.bubblebitoey.clock", appContext.getPackageName());
	}
	
	@Test
	public void shouldChangePageToTimer() throws Exception {
		onView(withId(R.id.navigation_timer)).perform(click());
		Assert.assertEquals(rule.getActivity().getCurrentFragment().getClass(), StopWatchFragment.class);
	}
	
	@Test
	public void shouldChangePageToShowTime() throws Exception {
		onView(withId(R.id.navigation_clock)).perform(click());
		Assert.assertEquals(rule.getActivity().getCurrentFragment().getClass(), ShowTimeFragment.class);
	}
	
}
