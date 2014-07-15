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
import com.ubaza.domain.Call;
import java.util.List;

public class IntroFragment extends BaseFragment {

    private static final String TAG = "IntroFragment";
    private View view;
    private TextView mCallsReceived;
    private Button startService;
    private Messenger mReqMessengerRef = null;
    private CounterService mCounterService;


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
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "New service connection");
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            CounterService.LocalBinder binder = (CounterService.LocalBinder) service;
            mCounterService = binder.getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "Service has crashed");
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
        if(mCounterService != null) {
            List<Call> calls = mCounterService.getCalls();
            mCallsReceived.setText(Call.toString(calls));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
