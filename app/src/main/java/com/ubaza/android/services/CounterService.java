package com.ubaza.android.services;

import android.app.Service;
import android.os.Messenger;
import android.widget.Toast;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.IBinder;
import android.content.Intent;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import android.os.Binder;
import java.util.List;
import com.ubaza.domain.Call;
import java.util.ArrayList;

public class CounterService extends Service {

    private List<Call> mCallList;
    private final IBinder mBinder = new LocalBinder();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public CounterService getService() {
            // Return this instance of LocalService so clients can call public methods
            return CounterService.this;
        }
    }

    /**
     * Adds a received call to the list of received calls
     */
    public void addCall(Call call) {
        mCallList.add(call);
    }

    /**
     * Hook method called when the Service is created.
     */
    @Override
    public void onCreate() {
        mCallList = new ArrayList<Call>();
        startTelephonyListener();
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
    }

    /**
     * Factory method to make the desired Intent.
     */
    public static Intent makeIntent(Context context) {
        // Create the Intent that's associated to the CounterService class.
        return new Intent(context, CounterService.class);
    }

    /**
     * Starts the class that will listen to the telephony changes
     * (when calls occur)
     */
    private void startTelephonyListener() {
        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyMgr.listen(new TelephonyListener(this, getApplicationContext()), PhoneStateListener.LISTEN_CALL_STATE);
    }

    public List<Call> getCalls() {
        return mCallList;
    }

    /**
     * Called when the service is destroyed, which is the last call
     * the Service receives informing it to clean up any resources it
     * holds.
     */
    @Override
    public void onDestroy() {
        /* Nothing to on the end of the service */
    }

    /**
     * Factory method that returns the underlying IBinder associated
     * with the Request Messenger.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
