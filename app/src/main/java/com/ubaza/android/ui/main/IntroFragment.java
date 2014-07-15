package com.ubaza.android.ui.main;


import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ubaza.android.R;
import com.ubaza.android.services.CounterService;
import com.ubaza.android.ui.common.BaseFragment;

import hugo.weaving.DebugLog;

import javax.inject.Inject;

public class IntroFragment extends BaseFragment {

    private static final String TAG = "IntroFragment";
    private View view;
    private TextView mCallsReceived;
    private Button startService;
    private Messenger mReqMessengerRef = null;
    private CounterService mBoundService;


    class ReplyHandler extends Handler {
        /**
         * Callback to handle the reply from the service
         */
        public void handleMessage(Message reply) {
            // Get the unique ID encapsulated in reply Message.
            String callID = CounterService.callID(reply);

            // Display the unique ID.
            mCallsReceived.setText(callID + "\n");
        }
    }

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    public IntroFragment() {
        // Required empty public constructor
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        getCalls();
    }

    @DebugLog
    @Override
    public void onPause() {
        super.onPause();
    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_intro, container, false);
        mCallsReceived = (TextView) view.findViewById(R.id.received_ringtones);
        return view;
    }

    @DebugLog
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize button with listener
        startService = (Button) view.findViewById(R.id.start_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // presenter.getDetails();
                startCallListenerService();
                Log.d(TAG, "Clicked start service view");
            }
        });
    }

    private ServiceConnection mSvcConn = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(TAG, "New service connection");
            mReqMessengerRef = new Messenger(binder);
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "Service has crashed");
            mReqMessengerRef = null;
        }
    };

    public void startCallListenerService() {
        Log.d(TAG, "calling bindService()");
        if (mReqMessengerRef == null) {
            // Bind to the UniqueIDGeneratorService associated with this Intent.
            getActivity().bindService(CounterService.makeIntent(getActivity()),
                    mSvcConn,
                    Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * Called by Android when the user presses the "Generate Unique
     * ID" button to request a new unique ID from the activity
     */
    public void getCalls() {
        // Create a request Message that indicates the Service should
        // send the reply back to ReplyHandler encapsulated by the
        // Messenger.
        Message request = Message.obtain();
        request.replyTo = new Messenger(new ReplyHandler());

        try {
            if (mReqMessengerRef != null) {
                Log.d(TAG, "sending message");
                mReqMessengerRef.send(request);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
