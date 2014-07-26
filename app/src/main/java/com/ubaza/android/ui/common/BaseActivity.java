package com.ubaza.android.ui.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.ubaza.android.application.App;
import com.ubaza.android.services.CounterService;

import hugo.weaving.DebugLog;

import retrofit.RetrofitError;

import timber.log.Timber;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {

    private ServiceConnection mSvcConn = prepareServiceConnection();
    private CounterService mCounterService;

    /**
     * @brief Creates the connection for the counter service
     */
    protected ServiceConnection prepareServiceConnection() {
        return new ServiceConnection() {
            public void onServiceConnected( ComponentName className, IBinder service ) {
                Timber.d( "New service connection to:  %s \n", className );
                CounterService.LocalBinder binder = ( CounterService.LocalBinder ) service;
                mCounterService = binder.getService();
            }

            public void onServiceDisconnected( ComponentName className ) {
                Timber.d( "Service has crashed", className );
            }
        };
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        getBus().register( this );
        startCallListenerService();
    }

    /**
     * @brief starts service to count the calls
     */
    @DebugLog
    protected void startCallListenerService() {
        Timber.d( "Calling bindService()" );
        bindService( CounterService.makeIntent( this ), mSvcConn, Context.BIND_AUTO_CREATE );
        startService( CounterService.makeIntent( this ) );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService( mSvcConn );
    }

    /**
     * @brief Gets the instance to the Counter Service
     */
    protected CounterService getCounterService() {
        return mCounterService;
    }

    @Subscribe
    public void onNetworkError( RetrofitError error ) {
        Toast.makeText( this, "Could not connect to server", Toast.LENGTH_SHORT ).show();
    }

    public Bus getBus() {
        return ( ( App ) getApplication() ).getBus();
    }
}
