package com.spengilley.activityfragmentmvp.ui.main;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spengilley.activityfragmentmvp.R;
import com.spengilley.activityfragmentmvp.ui.common.BaseFragment;
import com.spengilley.activityfragmentmvp.ui.main.presenters.IntroPresenterImpl;
import com.spengilley.activityfragmentmvp.ui.main.views.IntroView;

import javax.inject.Inject;
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import com.spengilley.activityfragmentmvp.services.CounterService;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.os.IBinder;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.EditText;

import android.widget.TextView;

public class IntroFragment extends BaseFragment implements IntroView {

    private static final String TAG = "IntroFragment";
    @Inject
    IntroPresenterImpl presenter;
    private FragmentCallback callback;
    private View view;

    private TextView mCallsReceived;

    private BroadcastReceiver onBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent i) {
            mCallsReceived.setText(mCallsReceived.getText().toString() + "Call received\n");
            Toast.makeText(getActivity(), "Received broadcast receiver", 1000);
        }
    };

    private Button startService;

    private Messenger mReqMessengerRef = null;


    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    public IntroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);

        getActivity().registerReceiver(onBroadcast, new IntentFilter("ReceivedCall"));
    }

    @Override
    public void onPause() {

        getActivity().unregisterReceiver(onBroadcast);
        super.onPause();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_intro, container, false);
        mCallsReceived = (TextView) view.findViewById(R.id.received_ringtones);
        return view;
    }


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


        startService = (Button) view.findViewById(R.id.start_service);
    }

    private ServiceConnection mSvcConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Bit naughty but we know it will work!
        callback = (FragmentCallback) activity;
    }

    /**
     * View implementation
     */
    @Override
    public void loadDetailsFragment() {
        // Ask callback to load details fragment
        callback.loadDetailFragment();
    }
}
