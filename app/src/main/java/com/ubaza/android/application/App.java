package com.ubaza.android.application;

import android.app.Application;

import com.squareup.otto.Bus;

import static timber.log.Timber.DebugTree;

import timber.log.Timber;

public class App extends Application {
    // The publish subscribe bus
    private Bus mBus;

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * Build object graph on creation so that objects are available
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Timber is a better lop option.
        Timber.plant( new DebugTree() );

        // Construct otto bus
        if( mBus == null ) mBus = new Bus();
    }


    /**
     * @brief Returns the communication bus
     */
    public Bus getBus() {
        return mBus;
    }
}
