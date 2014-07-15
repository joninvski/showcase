package com.ubaza.android.ui.common;

import android.app.Activity;
import android.os.Bundle;

import com.ubaza.android.App;

import java.util.List;

import dagger.ObjectGraph;
import timber.log.Timber;
import com.ubaza.android.services.CounterService;
import hugo.weaving.DebugLog;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.os.IBinder;


public abstract class BaseActivity extends Activity {
    private ObjectGraph activityGraph;

    private ServiceConnection mSvcConn = prepareServiceConnection();
    private CounterService mCounterService;

    public ServiceConnection prepareServiceConnection() {
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

        activityGraph = ((App) getApplication()).createScopedGraph(getModules().toArray());
        activityGraph.inject(this);

        startCallListenerService();
    }

    @DebugLog
    public void startCallListenerService() {
        Timber.d("Calling bindService()");
        bindService(CounterService.makeIntent(this), mSvcConn, Context.BIND_AUTO_CREATE);
        startService(CounterService.makeIntent(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityGraph = null;
        unbindService(mSvcConn);
    }

    public CounterService getCounterService() {
        return mCounterService;
    }


    protected abstract List<Object> getModules();

    public void inject(Object object) {
        activityGraph.inject(object);
    }
}
