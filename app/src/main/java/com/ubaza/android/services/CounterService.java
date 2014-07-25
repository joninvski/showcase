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
import android.app.Notification;
import com.ubaza.android.R;
import com.ubaza.rest.UbazaRestClient;
import timber.log.Timber;

public class CounterService extends BaseService {

    private List<Call> mCallList;
    private final IBinder mBinder = new LocalBinder();
    private UbazaRestClient ubazaRest;

    /**
     * Factory method to make the desired Intent.
     * Whoever wants to start this service MUST create the intent here.
     */
    public static Intent makeIntent( Context context ) {
        return new Intent( context, CounterService.class );
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     *
     * The binder simply returns a reference to this service.
     *
     * TODO - I have to investigate further but this is problematic if service is killed.
     * Check if problem is real and then maybe use otto sticky events to solve problem.
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
    protected void addCall( Call call ) {
        mCallList.add( call );
        sendCallToServer( call );
    }


    public void sendCallToServer( Call call ) {
        try {
            ubazaRest.pushCallAsync( call );
        } catch ( retrofit.RetrofitError e ) {
            Timber.d( "Exception %s", e );
            Timber.e( "Body %s", e.getBody() );
            Timber.e( "Response %s", e.getResponse() );
            Timber.e( "Success type %s", e.getSuccessType() );
            throw e;
        }
    }

    /**
     * Hook method called when the Service is created.
     */
    @Override
    public void onCreate() {
        mCallList = new ArrayList<Call>();
        startTelephonyListener();

        Notification noti = new Notification.Builder( getApplicationContext() )
        .setContentTitle( getText( R.string.service_notification_title ) )
        .setContentText( getText( R.string.service_notification_desc ) )
        .setSmallIcon( R.drawable.doge )
        .setPriority( Notification.PRIORITY_MIN ) /* To not show the icon in status bar */
        .build();

        ubazaRest = new UbazaRestClient( getBus() );
        startForeground( R.drawable.doge, noti );
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ) {
        // We want this service to continue running until it is explicitly stopped, so return sticky.
        return START_STICKY;
    }


    /**
     * Starts the class that will listen to the telephony changes
     * (when calls occur)
     */
    protected void startTelephonyListener() {
        TelephonyManager mTelephonyMgr = ( TelephonyManager ) getSystemService( Context.TELEPHONY_SERVICE );
        mTelephonyMgr.listen( new TelephonyListener( this, getApplicationContext() ), PhoneStateListener.LISTEN_CALL_STATE );
    }

    /**
     *  Returns the current call list
     */
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
    public IBinder onBind( Intent intent ) {
        return mBinder;
    }
}
