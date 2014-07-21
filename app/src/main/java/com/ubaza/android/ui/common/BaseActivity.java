package com.ubaza.android.ui.common;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import butterknife.ButterKnife;

import com.squareup.otto.Bus;
import com.ubaza.android.App;
import com.ubaza.android.R;
import com.ubaza.android.services.CounterService;

import hugo.weaving.DebugLog;

import java.util.List;

import timber.log.Timber;

public abstract class BaseActivity extends Activity {

    private ServiceConnection mSvcConn = prepareServiceConnection();
    private CounterService mCounterService;

    /**
     * @brief Creates the connection for the counter service
     */
    protected ServiceConnection prepareServiceConnection() {
        return new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                Timber.d("New service connection to:  %s \n", className);
                CounterService.LocalBinder binder = (CounterService.LocalBinder) service;
                mCounterService = binder.getService();
            }

            public void onServiceDisconnected(ComponentName className) {
                Timber.d("Service has crashed", className);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startCallListenerService();
    }

    /**
     * @brief starts service to count the calls
     */
    @DebugLog
    protected void startCallListenerService() {
        Timber.d("Calling bindService()");
        bindService(CounterService.makeIntent(this), mSvcConn, Context.BIND_AUTO_CREATE);
        startService(CounterService.makeIntent(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mSvcConn);
    }

    /**
     * @brief Gets the instance to the Counter Service
     */
    protected CounterService getCounterService() {
        return mCounterService;
    }


    public Bus getBus(){
        return ((App) getApplication()).getBus();
    }
}
