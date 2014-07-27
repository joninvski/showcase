package com.ubaza.android.application;

import android.app.Application;

import com.squareup.otto.Bus;

import static timber.log.Timber.DebugTree;

import timber.log.Timber;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import retrofit.RequestInterceptor;

/**
 * Implements the Ubaza android application
 *
 * Responsible to initializing the otto bus and the timber
 * debugtree for all the app
 */
public class App extends Application {
    // The publish subscribe bus
    private Bus mBus;

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

    public boolean isOnline() {
        ConnectivityManager cm = ( ConnectivityManager ) getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if ( netInfo != null && netInfo.isConnected() ) {
            return true;
        }
        return false;
    }

    public RequestInterceptor createInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept( RequestFacade request ) {
                request.addHeader( "Accept", "application/json;versions=1" );
                if ( isOnline() ) {
                    int maxAge = 30; // read from cache for 0.5 minute
                    request.addHeader( "Cache-Control", "public, max-age=" + maxAge );
                    Timber.d("Using online 30 seconds cache");
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    request.addHeader( "Cache-Control", "public, only-if-cached, max-stale=" + maxStale );
                    Timber.d("Using offline 4 weeks cache");
                }
            }
        };
    }
}
