package com.ubaza.android.test.ui.fragments;

import com.ubaza.android.ui.activities.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import com.squareup.spoon.Spoon;

public class MainFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public MainFragmentTest() {
        super( MainActivity.class );
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testMainFragmentNormal() {
        Spoon.screenshot(activity, "initial_state");

    }
}
